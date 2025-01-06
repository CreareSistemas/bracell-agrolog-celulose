package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import br.com.crearesistemas.sgf.SgfEventsLoading;
import br.com.crearesistemas.util.NumberUtils;

@Repository
public class EventosJdbcDAO extends JdbcDAO implements EventosDAO {

	private static final Logger logger = Logger.getLogger(EventosJdbcDAO.class);
	
	@Override
	public List<SgfEventsLoading> selecionarSgfApontamentosCampoPendentes() {
		List<SgfEventsLoading> sgfEventos = new ArrayList<SgfEventsLoading>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarApontamentosPendentes();
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				sgfEventos.add(sgfEventos(rs));
			}			
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}

		loadEventEndDate(sgfEventos);
		
		return sgfEventos;
	}

	
	private List<SgfEventsLoading> loadEventEndDate(List<SgfEventsLoading> eventos) {
		if  (eventos != null) {			
			for (SgfEventsLoading evento: eventos) {
				loadEventEndDate(evento);
			}
		}
		return eventos;
	}



	private SgfEventsLoading loadEventEndDate(SgfEventsLoading evento) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSelecionarApontamentoFim();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, evento.getVehicleId());
			st.setLong(2, evento.getEventId());
			
			rs = st.executeQuery();
			if (rs.next()) {
				evento.setEndDate(rs.getDate("end_date"));
			}			
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		
		return evento;
	}


	

	
	
	
	private SgfEventsLoading sgfEventos(ResultSet rs) throws SQLException {
		SgfEventsLoading loading = new SgfEventsLoading();
		
		loading.setReportID(String.valueOf(rs.getLong("id")));		
		loading.setInputData(rs.getString("ent_dados"));
		
		
		loading.setTruckPlate(config.inputItem(rs.getString("ent_dados"),1));
		loading.setProjectCode(NumberUtils.parseLong(config.inputItem(rs.getString("ent_dados"),3)));
		loading.setStandId(config.inputItem(rs.getString("ent_dados"),4));		
		loading.setPileCode(NumberUtils.parseLong(config.inputItem(rs.getString("ent_dados"),5)));
		loading.setLocaleCode(NumberUtils.parseLong(config.inputItem(rs.getString("ent_dados"),6)));
		loading.setChipperCode(NumberUtils.parseLong(config.inputItem(rs.getString("ent_dados"),7)));
		loading.setLoadType(NumberUtils.parseInt(config.inputItem(rs.getString("ent_dados"),8)));
		
		loading.setStartDate(rs.getDate("data_hora"));
		loading.setCraneCode(rs.getLong("cranecode"));
		loading.setProductCode(1);
		loading.setCraneOperatorName(rs.getString("driverName"));
		loading.setVehicleId(rs.getLong("vehicle_id"));
		loading.setEventId(rs.getLong("id"));
		
		
		
		
		/*
		 loading.setReportID("1411600520");
		loading.setTruckPlate("RDN2E27");
		loading.setProjectCode(0l);
		loading.setStandId("007");
		loading.setProductCode(1);
		loading.setCraneCode(1106l);
		loading.setStartingDate("2022-01-11T12:00:03");
		loading.setEndingDate("2022-01-11T12:10:50"); 
		
		
		
		loading.setTruckPlate("GCR6F71");
		loading.setReportID("1411525912");			
		loading.setCraneCode(583l); // 5005
		
		loading.setChipperCode(3l);   // chipper n�o pode ser nulo, sen�o volta com erro
		loading.setPileCode(2l);
		
		loading.setCraneOperatorName("Motorista n�o identificado");
		loading.setStartingHorimeterNumber("1");
		loading.setEndingHorimeterNumber("200");
		loading.setStartingDate("2022-01-11T10:08:00");
		loading.setEndingDate("2022-01-11T10:14:20");
		
		 */
		
		
		
		return loading;
	}


	private String sqlSelecionarApontamentoFim() {
		StringBuilder sb = new StringBuilder();		
		sb.append("");
		sb.append("select * from ( \n");
		sb.append("	    select  ev.id, ev.data_hora start_date, ev.cod_evento,  ev.ent_dados, \n");
		sb.append("				LEAD(ev.data_hora) OVER (PARTITION BY ev.vehicle_id ORDER BY ev.vehicle_id, ev.data_hora) as end_date \n");
		sb.append("			from  eventos ev  \n");
		sb.append("			where   \n");
		sb.append("			ev.vehicle_id = ? \n");
		sb.append("	        and ev.DATA_HORA > sysdate - 2 \n");
		sb.append("			order by  ev.data_hora desc \n");
		sb.append("	) t_ev where t_ev.id = ? \n");
		return sb.toString();
	}

	
	private String sqlSelecionarApontamentosPendentes() {
		StringBuilder sb = new StringBuilder();		
		sb.append("select ev.id, v.vehicle_id, d.DRIVER_FIRSTNAME || ' ' || d.DRIVER_LASTNAME driverName, v.vehicle_plates, sc.description, \n");
		sb.append("			sc.cranecode, ev.data_hora, ev.cod_evento, ev.ent_dados, wetr.workflow_event_treatment_id, wetr.integration_error_detail \n");
		sb.append("from eventos ev \n");
		sb.append("		left join workflow_event_treatment_rel wetr on (ev.id = wetr.event_id) \n");
		sb.append("		inner join vehicles v on (ev.vehicle_id = v.vehicle_id) \n");
		sb.append("     left join drivers d ON (d.driver_id = ev.driver_id) \n");
		if (config.isProduction()) {
			sb.append("		left join bracell.sgfcranes sc on (sc.description like '%' || v.vehicle_plates || '%' ) \n");
		} else {
			sb.append("		left join cmpc_hom.sgfcranes sc on (sc.description like '%' || v.vehicle_plates || '%' ) \n");
		}
		sb.append("where ev.data_hora > sysdate - 1 \n");
		sb.append("		and v.customer_child_id in (select customer_child_id from customer_child where customer_id = 6787) \n");
		sb.append("		and v.vehicle_type in (16) and ev.ent_dados is not null and cod_evento in (11, 1, 12, 14)  \n");
		sb.append("		and data_hora between sysdate-1 and sysdate-2/24 and workflow_event_treatment_id is null \n");    	
		return sb.toString();
	}
		
}

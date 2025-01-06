package br.com.crearesistemas.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.GruaFlorestal;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.util.JsonUtils;

@Repository
public class PgGruasJdbcDAO extends JdbcDAO implements PgGruasDAO {
	private static final Logger logger = Logger.getLogger(PgGruasJdbcDAO.class);
	
	@Override
	public SgfCrane selecionarSgfCrane(String plates, Boolean isProd) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfCrane grua = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarSgfCrane(plates, isProd);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				grua = sgfGrua(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return grua;
	}

	
	@Override
	public List<GruaEntity> selecionarGruasFlorestais(Long customerId, Boolean isProd) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarGruasv2(customerId, isProd);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				gruas.add(grua(rs));
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return gruas;
	}

	@Override
	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId, Boolean isProd) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			//sql = sqlSelecionarGruasSiteIndustrial(customerId);
			sql = sqlSelecionarGruasSiteIndustrialv2(customerId, isProd);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				gruas.add(grua(rs));
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return gruas;
	}
	
	@Override
	public Boolean salvarGrua(GruaEntity grua) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;
		
		if (grua.getName().contains("FRJ8591") || grua.getId() == 534334l) {
			logger.info("caminhao carregxx: " + grua.getName() 
			+ "\n data: " + grua.getAvldateTime()
			+ "\n project: " + grua.getProject()
			+ "\n field: " + grua.getField()
			+ "\n plates: " + grua.getPlates()
			
			);	
	    }
		
		try {
			con = open();
			sql = sqlUpdateGrua();
			if (printSql) {
				logger.info(sql);
			}
			
			st = con.prepareStatement(sql); 
			
			Clob clob = con.createClob();
			clob.setString(1, JsonUtils.toJson(grua));
			st.setClob(1, clob);
			
			if (grua.getCarregEstadoOperacional() != null 
					&& grua.getCarregEstadoOperacional() == EstadoOperacional.CARREG_CHEIO.ordinal()) {
				st.setInt(2, 1);	
			} else {
				st.setInt(2, 0);
			}

			st.setLong(3, grua.getId()); 
			result = st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);			
		} finally {
			 //clob.free();
			close(con, st, rs);
		}
		return result;
		
	}

	
	@Override
	public Boolean salvar(GruaEntity grua) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;
		
		try {
			con = open();
			sql = sqlUpdate();
			if (printSql) {
				logger.info(sql);
			}
			
			st = con.prepareStatement(sql); 
			
			Clob clob = con.createClob();
			clob.setString(1, JsonUtils.toJson(grua));
			st.setClob(1, clob);			
			st.setLong(2, grua.getId());
			
			result = st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);			
		} finally {
			 //clob.free();
			close(con, st, rs);
		}
		return result;
		
	}
	private GruaEntity grua(ResultSet rs) throws Exception {
		GruaEntity grua = new GruaEntity();
		grua.setId(rs.getLong("vehicle_id"));
		grua.setName(rs.getString("vehicle_plates"));
		grua.setPrefix(rs.getString("vehicle_prefix"));
		grua.setDescription(rs.getString("description"));
		grua.setCustomerChildId(rs.getLong("customer_child_id"));
		grua.setOperatorId(rs.getLong("driver_id"));
		
		
		grua.setEventCode(rs.getLong("cod_evento"));
		grua.setLatitude(rs.getFloat("latitude"));
		grua.setLongitue(rs.getFloat("longitude"));
		grua.setEventDateTime(rs.getDate("data_hora"));
		grua.setAvldateTime(rs.getDate("date_hour"));
		
		//FRJ8591,0,0,0,0,0,2,2
		grua.setEventReportId(rs.getLong("evento_id"));
		grua.setPlates(inputItem(rs.getString("ent_dados"),1));
		grua.setProject(inputItem(rs.getString("ent_dados"),3));
		grua.setField(inputItem(rs.getString("ent_dados"),4));		
		grua.setPile(inputItem(rs.getString("ent_dados"),5));
		grua.setLocale(inputItem(rs.getString("ent_dados"),6));
		grua.setChipper(inputItem(rs.getString("ent_dados"),7));
		grua.setLoadType(inputItem(rs.getString("ent_dados"),8));
		grua.setOperatorName(rs.getString("operatorName"));
		
		// eucalipto
		//grua.setProjectId(36200l);
		
		grua.setLoading(rs.getInt("operational_state"));
		
		// downtime
		grua.setGroupId(rs.getInt("groupid"));
		grua.setDowntimeCode(rs.getInt("downtimecode"));

		
		Clob clob = rs.getClob("custom_columns");
		if (clob != null) {
			String json=(String)clob.getSubString(1,(int)clob.length());      
			GruaEntity gruaJson = JsonUtils.fromJson(json, GruaEntity.class);
			if (gruaJson != null) {
				
				// fluxo transp
				grua.setTranspEstadoOperacional(gruaJson.getTranspEstadoOperacional());
				grua.setTranspFluxo(gruaJson.getTranspFluxo());
				
				// fluxo carreg
				grua.setCarregEstadoOperacional(gruaJson.getCarregEstadoOperacional());
				grua.setCarregFluxo(gruaJson.getCarregFluxo());
				
				
				// fluxo site
				grua.setSiteEstadoOperacional(gruaJson.getSiteEstadoOperacional());
				grua.setSiteFluxo(gruaJson.getSiteFluxo());

				// fluxo proprio
				grua.setProprioEstadoOperacional(gruaJson.getProprioEstadoOperacional());
				grua.setProprioFluxo(gruaJson.getProprioFluxo());

				// idlocal
				grua.setIdLocal(gruaJson.getIdLocal());
				
				// downtime
				grua.setDowntime(gruaJson.getDowntime());
				
				// carregamento
				grua.setLoadDateTime(gruaJson.getLoadDateTime());
				grua.setLoadEventCode(gruaJson.getLoadEventCode());
				grua.setLoadEventReportId(gruaJson.getLoadEventReportId());

			}							
		}
		
		return grua;
	}
	
	/**
     * bracell crane input data format: plates,pileField,farm,field(stand),pile,pileLocale,chipper,eventType
     *
     * (bracell) 1.placa, 2.pilha fazenda, 3.projeto, 4.talh�o, 5.pilha estoque,
     *           6.local estoque, 7.linha de consumo, 8.tipo evento (1-carreg, 2-descarreg)
     *
     * Profile Crane Forest
     *      - farm
     *      - field
     *      - asset
     *
     * Profile Stockyard:
     *      - Event Type (Loading/Unloading)
     *      - Stock Locale
     *      - Stock Pile
     *      - Asset
     *
     * Profile Line Feed:
     *      - Line Feed (chipper)
     *      - Asset     *
     */
    
	private String inputItem(String string, int position) {
		String result = null;
		if (string != null) {
			String[] split = string.split(",");
			if (split.length >= position) {
				result = split[position-1];
			}	
		}
		
		return result;
	}
	
	
	private String sqlSelecionarGruas(Long customerId) {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(ev.lat_inicial, avl.latitude) latitude, nvl(ev.long_inicial, avl.longitude) longitude, avl.date_hour, ev.evento_id, \n");
		sql.append("	v.operational_state, v.custom_columns, d.driver_firstname||' '||d.driver_lastname as operatorName \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId).append("\n");
		sql.append(") and v.vehicle_type in (16) and v.vehicle_status = 1 \n");
		
		
		return sql.toString();
	}
	
	private String sqlSelecionarGruasv2(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(ev.lat_inicial, avl.latitude) latitude, nvl(ev.long_inicial, avl.longitude) longitude, avl.date_hour, ev.evento_id, \n");
		sql.append("	v.operational_state, v.custom_columns, d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		//sql.append("left join fleet_reg_events_temp cfg on (v.vehicle_type = cfg.assettypeid and ev.cod_evento = cfg.eventcode)  \n");
		if (isProd) {
			//sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
			//sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		}
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId).append("\n");
		sql.append(") and v.vehicle_type in (16) and v.vehicle_status = 1 \n");
		
		
		return sql.toString();
	}
	
	
	private String sqlSelecionarGruasSiteIndustrial(Long customerId) {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(ev.lat_inicial, avl.latitude) latitude, nvl(ev.long_inicial, avl.longitude) longitude, avl.date_hour, ev.evento_id, \n");
		sql.append("	v.operational_state, v.custom_columns, d.driver_firstname||' '||d.driver_lastname as operatorName \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId).append("\n");
		sql.append(") and v.vehicle_type in (61) and v.vehicle_status = 1 \n");
		sql.append("order by  v.vehicle_plates \n");
		
		
		return sql.toString();
	}

	
	private String sqlSelecionarGruasSiteIndustrialv2(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(ev.lat_inicial, avl.latitude) latitude, nvl(ev.long_inicial, avl.longitude) longitude, avl.date_hour, ev.evento_id, \n");
		sql.append("	v.operational_state, v.custom_columns, d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		//sql.append("left join fleet_reg_events_temp cfg on (v.vehicle_type = cfg.assettypeid and ev.cod_evento = cfg.eventcode)  \n");
		if (isProd) {
			//sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
			//sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		}
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId).append("\n");
		sql.append(") and v.vehicle_type in (61) and v.vehicle_status = 1 \n");
		sql.append("order by  v.vehicle_plates \n");
		
		
		return sql.toString();
	}
	
	private String sqlUpdateGrua() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE vehicles\n");
		sql.append("SET\n");
		sql.append("	custom_columns1 = ?, operational_state = ? \n");
		sql.append("WHERE\n");
		sql.append("	vehicle_id = ?");
		return sql.toString();
	}

	private String sqlUpdate() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE vehicles\n");
		sql.append("SET\n");
		sql.append("	custom_columns1 = ? \n");
		sql.append("WHERE\n");
		sql.append("	vehicle_id = ?");
		return sql.toString();
	}
	
	
	private String sqlSelecionarSgfCrane(String plates, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		if (isProd) {
			sb.append("FROM bracell.sgfcranes \n");
		} else {
			sb.append("FROM cmpc_hom.sgfcranes \n");
		}
		sb.append("WHERE  \n");
		sb.append(" description like '%"+ plates +"%' \n");
		return sb.toString();
	}
	
	private SgfCrane sgfGrua(ResultSet rs) throws SQLException {
		SgfCrane grua = new SgfCrane();
		grua.setAbbreviation(rs.getString("abbreviation"));
		grua.setCraneCode(rs.getLong("craneCode"));
		grua.setCraneType(rs.getString("craneType"));
		grua.setDescription(rs.getString("description"));
		grua.setStatus(rs.getString("sstatus"));
		return grua;
	}


	@Override
	public GruaFlorestal selecionarGruaFlorestal(String name, Boolean isProduction) {
		GruaFlorestal grua = null;
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = openRepository();
			st = con.createStatement();
			sql = sqlSelecionarGruas(name);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				grua = pggrua(rs);
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return grua;
	}


	private GruaFlorestal pggrua(ResultSet rs) throws Exception {
		GruaFlorestal gruav = new GruaFlorestal();
		
		gruav.setId(rs.getLong("id"));
		gruav.setIdentificacao(rs.getString("identification"));
		gruav.setIdProjeto(rs.getLong("projectId"));
		gruav.setEstadoOperacional(rs.getInt("state_prop"));
		gruav.setDataUltimoRastreamento(rs.getDate("dataRastreamento"));
		gruav.setDataUltimoApontamento(rs.getDate("dataInicio"));
		gruav.setDataUltimoCarregamento(rs.getDate("date_start_load"));
		gruav.setProjetoUltimoCarregamento(rs.getString("farm"));
		gruav.setPilhaUltimoCarregamento(rs.getString("plate"));
		gruav.setPlacaUltimoCarregamento(rs.getString("field"));
		gruav.setLatitude(rs.getFloat("latitude"));
		gruav.setLongitude(rs.getFloat("longitude"));
		
		return gruav;
	}
		
	private String sqlSelecionarGruas(String plate) {
		StringBuilder sql = new StringBuilder();
		sql.append("select \r\n" + 
				"	ap.id, ap.identification, ap.prefix , 1 tipoImplemento, sp.operational_state_id state_prop,  t.\"date\" dataRastreamento \r\n" + 
				"	,t.latitude , t.longitude , sp.date_start dataInicio,  	  ap.office_id, fl.farm , fl.plate , fl.field , ev.date_start ,\r\n" + 
				"	l.id projectId, evload.date_start date_start_load \r\n" + 
				"from assets ap\r\n" + 
				"left join state_store sp on (ap.state_owner_id = sp.id)\r\n" + 
				"left join trackings t on (ap.tracking_id = t.id)\r\n" + 
				"left join events ev on (ev.id = ap.event_id)\r\n" + 
				"left join events evload on (evload.id = ap.load_event_id)\r\n" + 
				"left join farm_loading fl on (evload.farm_loading_id = fl.id)\r\n" + 
				"left join locales l on (l.id = ap.locale_id)\r\n" + 
				"where ap.asset_type_id = 1 and (ap.identification = '").append(plate).append("'")
				.append(" or name = '").append(plate).append("')");
		return sql.toString();
	}

	
}

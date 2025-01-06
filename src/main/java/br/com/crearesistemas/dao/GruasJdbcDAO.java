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

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.util.JsonUtils;

@Repository
public class GruasJdbcDAO extends JdbcDAO implements GruasDAO {
	private static final Logger logger = Logger.getLogger(GruasJdbcDAO.class);
	Boolean isProduction = config.isProduction();
	
	@Override
	public SgfCrane selecionarSgfCrane(String plates) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfCrane grua = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarSgfCrane(plates, isProduction);
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
	public List<GruaEntity> selecionarGruasFlorestais(Long customerId) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarGruasv2(customerId, isProduction);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				Long vehicleTempId = rs.getLong("vehicle_temp_id");
				if (vehicleTempId <= 0) {
					verifyCustomColumns(rs.getLong("vehicle_id"));	
				}
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
	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarGruasSiteIndustrialv2(customerId, isProduction);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				Long vehicleTempId = rs.getLong("vehicle_temp_id");
				if (vehicleTempId <= 0) {
					verifyCustomColumns(rs.getLong("vehicle_id"));	
				}
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
	public List<GruaEntity> selecionarGruasSiteIndustrialL1(Long customerId, Integer lineId) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		List<GruaEntity> gruasLine1 = new ArrayList<GruaEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarGruasSiteIndustrialv2L1(customerId, isProduction);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			
			
			config.setMapChipperL1Clear();
			
			while (rs.next()) {
				Long vehicleTempId = rs.getLong("vehicle_temp_id");
				if (vehicleTempId <= 0) {
					verifyCustomColumns(rs.getLong("vehicle_id"));	
				}
				
				GruaEntity grua = grua(rs);
				
				if (lineId == Config.LINE1_CODE) {
					if (config.isGruaLinha1(grua)) {
						gruasLine1.add(grua);
					}	
				} else {
					if (!config.isGruaLinha1(grua)) {
						gruas.add(grua);	
					}
				}
			}
			
			if (lineId == Config.LINE1_CODE && gruasLine1.size() > 0) {
				gruas = config.getChipperOrStandByL1(gruasLine1);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return gruas;
	}
	
	private List<GruaEntity> getNewerEventChipperL1(List<GruaEntity> gruasLine1) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		
		for(GruaEntity grua : gruasLine1) {
			
		}
		
		return gruas;
	}


	@Override
	public Boolean salvarGrua(GruaEntity grua) {
		verifyCustomColumns(grua.getId());
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;
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
		verifyCustomColumns(grua.getId());
		
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
		grua.setAvldateTime(rs.getTimestamp("date_hour"));
		
		grua.setEventReportId(rs.getLong("evento_id"));
		grua.setPlates(config.inputItem(rs.getString("ent_dados"),1));
		grua.setProject(config.inputItem(rs.getString("ent_dados"),3));
		grua.setField(config.inputItem(rs.getString("ent_dados"),4));		
		grua.setPile(config.inputItem(rs.getString("ent_dados"),5));
		grua.setLocale(config.inputItem(rs.getString("ent_dados"),6));
		grua.setChipper(config.inputItem(rs.getString("ent_dados"),7));
		grua.setLoadType(config.inputItem(rs.getString("ent_dados"),8));
		grua.setOperatorName(rs.getString("operatorName"));
		
		
		if (isProduction) {
			grua.setLoading(rs.getInt("operational_statep"));	
		} else {
			grua.setLoading(rs.getInt("operational_stateh"));
		}
		
		
		// downtime
		grua.setGroupId(rs.getInt("groupid"));
		grua.setDowntimeCode(rs.getInt("downtimecode"));

		
		Clob clob;
		if (isProduction) {
			clob = rs.getClob("custom_columnsp");
		} else {
			clob = rs.getClob("custom_columnsh");
		}
		
		if (clob != null) {
			String json=(String)clob.getSubString(1,(int)clob.length());      
			GruaEntity gruaJson = JsonUtils.fromJson(json, GruaEntity.class);
			if (gruaJson != null) {
				
				
				// fluxo transp
				grua.setTranspEstadoOperacional(gruaJson.getTranspEstadoOperacional());
				grua.setTranspFluxo(gruaJson.getTranspFluxo());
				grua.setTranspDate(gruaJson.getTranspDate());
				
				// fluxo carreg
				grua.setCarregEstadoOperacional(gruaJson.getCarregEstadoOperacional());
				grua.setCarregFluxo(gruaJson.getCarregFluxo());
				grua.setCarregDate(gruaJson.getCarregDate());
				
				
				// fluxo site
				grua.setSiteEstadoOperacional(gruaJson.getSiteEstadoOperacional());
				grua.setSiteFluxo(gruaJson.getSiteFluxo());
				grua.setSiteDate(gruaJson.getSiteDate());

				// fluxo proprio
				grua.setProprioEstadoOperacional(gruaJson.getProprioEstadoOperacional());
				grua.setProprioFluxo(gruaJson.getProprioFluxo());
				grua.setProprioDate(gruaJson.getProprioDate());

				// idlocal
				grua.setIdLocal(gruaJson.getIdLocal());
				
				// downtime
				grua.setDowntime(gruaJson.getDowntime());
				grua.setLastDateDowntime(gruaJson.getLastDateDowntime());
				
				
				// loading
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
     *           //format: 1.driverId,2.plates,3.pileField,4.farm,5.field(stand),6.pile,7.pileLocale,8.chipper,9.eventType
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
    
	/*
	private String inputItem(String string, int position) {
		String result = null;
		if (string != null) {
			String[] split = string.split(",");
			int offset = 0;
            if (split.length >= 9) {
                offset = 1;
            }
			if (split.length >= position) {
				result = split[position-1+offset];
			}	
		}
		
		return result;
	}
	*/
	
	
	private String sqlSelecionarGruasv2(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(avl.latitude, ev.lat_inicial) latitude, nvl(avl.longitude, ev.long_inicial) longitude, CAST(avl.date_hour AS timestamp) date_hour, ev.evento_id, \n");
		sql.append("	vt.operational_statep, vt.operational_stateh, vt.custom_columnsp, vt.custom_columnsh, d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro, vt.vehicle_id vehicle_temp_id \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("left join bracell.vehicles_temp vt on v.vehicle_id = vt.vehicle_id  \n");
		if (isProd) {
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
			sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		}
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId).append("\n");
		sql.append(") and v.vehicle_type in (16) and v.vehicle_status = 1 \n");
		
		
		return sql.toString();
	}
	
	
	
	private String sqlSelecionarGruasSiteIndustrialv2(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(avl.latitude, ev.lat_inicial) latitude, nvl(avl.longitude, ev.long_inicial) longitude, CAST(avl.date_hour AS date) date_hour, ev.evento_id, \n");
		sql.append("	vt.operational_statep, vt.operational_stateh, vt.custom_columnsp, vt.custom_columnsh, d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro, vt.vehicle_id vehicle_temp_id \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("left join bracell.vehicles_temp vt on v.vehicle_id = vt.vehicle_id  \n");
		if (isProd) {
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
			sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		}
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId).append("\n");
		sql.append(") and v.vehicle_type in (61) and v.vehicle_status = 1 \n");
		sql.append("order by  v.vehicle_plates \n");
		
		
		return sql.toString();
	}
	
	private String sqlSelecionarGruasSiteIndustrialv2L1(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n"); 
		sql.append("	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, \n");
		sql.append("	nvl(avl.latitude, ev.lat_inicial) latitude, nvl(avl.longitude, ev.long_inicial) longitude, CAST(avl.date_hour AS date) date_hour, ev.evento_id, \n");
		sql.append("	vt.operational_statep, vt.operational_stateh, vt.custom_columnsp, vt.custom_columnsh, d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro, vt.vehicle_id vehicle_temp_id \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("left join bracell.vehicles_temp vt on v.vehicle_id = vt.vehicle_id  \n");
		if (isProd) {
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
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
		sql.append("UPDATE bracell.vehicles_temp\n");
		sql.append("SET\n");
		if (isProduction) {
			sql.append("	custom_columnsp = ?, operational_statep = ? \n");	
		} else {
			sql.append("	custom_columnsh = ?, operational_stateh = ? \n");
		}
		sql.append("WHERE\n");
		sql.append("	vehicle_id = ?");
		return sql.toString();
	}

	private String sqlUpdate() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE bracell.vehicles_temp\n");
		sql.append("SET\n");
		if (isProduction) {
			sql.append("	custom_columnsp = ? \n");	
		} else {
			sql.append("	custom_columnsh = ? \n");
		}
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

	private void verifyCustomColumns(Long vehicleId) {		
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			String sqlExist = sqlSelecionarCustomColumn(vehicleId);
			st = con.prepareStatement(sqlExist);
			rs = st.executeQuery();
			if (!rs.next()) {
				// inserir
				sql = sqlInserirVehicleTemp();
				st.close();
				st = con.prepareStatement(sql);
				st.setLong(1, vehicleId);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			exc.printStackTrace();
		} finally {
			close(con, st, rs);
		}
	}
	
	private String sqlSelecionarCustomColumn(Long vehicleId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM bracell.vehicles_temp where vehicle_id = " + vehicleId);
		return sql.toString();
	}

	private String sqlInserirVehicleTemp() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into bracell.vehicles_temp (vehicle_id) VALUES (?)");
		return sql.toString();
	}


}

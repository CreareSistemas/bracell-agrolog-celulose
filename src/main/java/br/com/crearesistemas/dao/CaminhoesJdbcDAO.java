package br.com.crearesistemas.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.util.JsonUtils;

@Repository
public class CaminhoesJdbcDAO extends JdbcDAO implements CaminhoesDAO {
	Gson gson = new Gson();
	private static final Logger logger = Logger.getLogger(CaminhoesJdbcDAO.class);
	Boolean isProduction = config.isProduction();

	
	@Override
	public Boolean salvar(CaminhaoEntity caminhao) {
		verifyCustomColumns(caminhao.getId());
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;
		try {
			con = open();
			sql = sqlUpdateVehicle();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);

			Clob clob = con.createClob();
			clob.setString(1, JsonUtils.toJson(caminhao));
			st.setClob(1, clob);
			st.setLong(2, caminhao.getId());
			result = st.executeUpdate() > 0;

			if (!result) {
				verifyCustomColumns(caminhao.getId());
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			// clob.free();
			close(con, st, rs);
		}
		return result;

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
				st.executeUpdate();
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			exc.printStackTrace();
		} finally {
			close(con, st, rs);
		}
	}

	
	
	@Override
	public Boolean trocaEstadoTransporteSite(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado,
			Date date) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = open();
			sql = sqlUpdateVehicleSite();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);

			Clob clob = con.createClob();
			clob.setString(1, JsonUtils.toJson(caminhao));
			st.setClob(1, clob);
			
			st.setLong(2, caminhao.getId());
			result = st.executeUpdate() > 0;

			if (!result) {
				verifyCustomColumns(caminhao.getId());
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			// clob.free();
			close(con, st, rs);
		}
		return result;
	}

	@Override
	public Boolean trocaEstadoTransporteCarreg(CaminhaoEntity caminhao, FluxoOperacional fluxo,
			EstadoOperacional estado, Date date) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = open();
			sql = sqlUpdateVehicleCarreg();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);

			Clob clob = con.createClob();
			clob.setString(1, JsonUtils.toJson(caminhao));
			st.setClob(1, clob);

			if (estado == EstadoOperacional.CARREG_CHEIO) {
				st.setInt(2, 1);
			} else {
				st.setInt(2, 0);
			}

			st.setLong(3, caminhao.getId());
			result = st.executeUpdate() > 0;

			if (!result) {
				verifyCustomColumns(caminhao.getId());
			}
			
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			// clob.free();
			close(con, st, rs);
		}
		return result;
	}

	@Override
	public Boolean trocaEstadoTransporte(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado,
			Date date) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = open();
			sql = sqlUpdateVehicle();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);

			Clob clob = con.createClob();
			clob.setString(1, JsonUtils.toJson(caminhao));
			st.setClob(1, clob);

			st.setLong(2, caminhao.getId());
			result = st.executeUpdate() > 0;
			
			if (!result) {
				verifyCustomColumns(caminhao.getId());
			}
			
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			// clob.free();
			close(con, st, rs);
		}
		return result;
	}
	
	@Override
	public Boolean saveHistoricalState(FluxoOperacional fluxo, EstadoOperacional estado, Date dataInicio, Date dataFim,
			Long assetId, Integer lineId) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = openBracell();
			sql = sqlInsertHistoricalState();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);

			if (dataInicio != null) {
				st.setDate(1, new java.sql.Date(dataInicio.getTime()));	
			} else {
				st.setNull(1, java.sql.Types.DATE);
			}
			
			if (dataFim != null) {
				st.setDate(2, new java.sql.Date(dataFim.getTime()));	
			} else {
				st.setNull(2, java.sql.Types.DATE);
			}
			st.setInt(3, estado.ordinal());
			st.setInt(4, fluxo.ordinal());
			st.setLong(5, assetId);
			
			if (lineId != null && lineId == Config.LINE1_CODE) {
				st.setInt(6, Config.LINE1_CODE);	
			} else {
				st.setInt(6, Config.LINE2_CODE);
			}
			
			
			
			result = st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return result;
	}


	

	@Override
	public List<CaminhaoEntity> selecionarCaminhoesTransporte(Long customerId) {
		
		
		List<CaminhaoEntity> caminhoes = new ArrayList<CaminhaoEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarCaminhoes(customerId, isProduction);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Long vehicleTempId = rs.getLong("vehicle_temp_id");
				if (vehicleTempId <= 0) {
					verifyCustomColumns(rs.getLong("vehicle_id"));	
				}				
				caminhoes.add(caminhao(rs));				
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			// throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return caminhoes;
	}
	
	@Override
	public List<CaminhaoEntity> selecionarCaminhoesTransporteL1(Long customerId, Integer lineCode) {
		
		List<CaminhaoEntity> caminhoes = new ArrayList<CaminhaoEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarCaminhoesL1(customerId);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Long vehicleTempId = rs.getLong("vehicle_temp_id");
				if (vehicleTempId <= 0) {
					verifyCustomColumns(rs.getLong("vehicle_id"));	
				}
				
				CaminhaoEntity truck = caminhao(rs);
				
				if (lineCode == Config.LINE1_CODE) {
					if (truck.getLineId() == Config.LINE1_CODE) {
						caminhoes.add(truck);	
					}	
				} else {
					if (truck.getLineId() != Config.LINE1_CODE) {
						caminhoes.add(truck);	
					}
				}
								
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			// throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return caminhoes;
	}

	@Override
	public Boolean incrementaEvento(Long vehicleId) {
		Boolean result = false;
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlIncrementaEvento(vehicleId);
			if (printSql) {
				logger.info(sql);
			}
			return st.executeUpdate(sql) > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
			// throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return result;
	}

	
	
	@Override
	public List<Object[]> selecionaPorFluxoSiteIndustrial(Date dataDe, Date dataAte) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		ArrayList<Object[]> res = new ArrayList<Object[]>();
		try {
			con = open();
			sql = sqlCicloInterno();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);
			st.setDate(1, new java.sql.Date(dataDe.getTime()));
			st.setDate(2, new java.sql.Date(dataAte.getTime()));
			rs = st.executeQuery();
			
			while (rs.next()) {
				res.add(cicloInterno(rs));
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return res;
	}

	
	@Override
	public List<Object[]> selecionaPorFluxoSiteIndustrialL1(Date dataDe, Date dataAte) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		ArrayList<Object[]> res = new ArrayList<Object[]>();
		try {
			con = open();
			sql = sqlCicloInternoL1();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);
			st.setDate(1, new java.sql.Date(dataDe.getTime()));
			st.setDate(2, new java.sql.Date(dataAte.getTime()));
			rs = st.executeQuery();
			
			while (rs.next()) {
				res.add(cicloInterno(rs));
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return res;
	}
	

	
	@Override
	public Long selMaxEventos(Long vehicleId) {
		Long result = null;
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlMaxEventos();
			if (printSql) {
				logger.info(sql);
			}

			st = con.prepareStatement(sql);
			st.setLong(1, vehicleId);
			rs = st.executeQuery();

			if (rs.next()) {
				result = rs.getLong("id");
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return result;
	}

	@Override
	public SgfTruck selecionarCaminhaoTransporte(String plates) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfTruck caminhao = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarCaminhaoTransporte(plates, isProduction);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				caminhao = sgfCaminhao(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return caminhao;
	}

	
	private Object[] cicloInterno(ResultSet rs) throws SQLException {
		EstadoOperacional estado = EstadoOperacional.getByCode(rs.getInt("estadoOperacional"));
		Long idPrestador = rs.getLong("idPrestador");
		Double duracao = rs.getDouble("duracao");
		
		Object[] row = {estado, idPrestador, duracao};
		
		return row;
	}

	private SgfTruck sgfCaminhao(ResultSet rs) throws SQLException {
		SgfTruck caminhao = new SgfTruck();
		caminhao.setAbbreviation(rs.getString("abbreviation"));
		caminhao.setTruckPlate(rs.getString("truckPlate"));		
		caminhao.setTruckCode(rs.getLong("truckCode"));
		caminhao.setSerialNumber(rs.getString("serialNumber"));
		caminhao.setTagRFID(rs.getString("tagRFID"));
		caminhao.setStatus(rs.getString("status"));
		return caminhao;
	}

	private String sqlSelecionarCaminhaoTransporte(String plates, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		if (isProd) {
			sb.append("FROM bracell.sgftrucks \n");
		} else {
			sb.append("FROM cmpc_hom.sgftrucks \n");
		}
		sb.append("WHERE  \n");
		sb.append(" truckplate like '%"+ plates +"%' \n");
		return sb.toString();
	}

	private String sqlIncrementaEvento(Long vehicleId) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into eventos (codigo, cod_evento, data_hora, vehicle_id, \n");
		sql.append("       driver_id, source_id, lat_inicial, long_inicial, avl_locations_id, \n");
		sql.append("       velocidade, hodometro, rotacao, ent_dados,   error_id)  \n");
		sql.append("select codigo, cod_evento, data_hora, vehicle_id,   \n");
		sql.append("       driver_id, source_id, lat_inicial, long_inicial, avl_locations_id,  \n");
		sql.append("       velocidade, hodometro, rotacao, ent_dados,   error_id from eventos \n");
		sql.append("       where id in (select max(id) from eventos where vehicle_id = ").append(vehicleId);
		sql.append("       and codigo = 6152) \n");

		return sql.toString();
	}

	
	private String sqlMaxEventos() {
		return "select max(id) as id from eventos where vehicle_id = ? and codigo = 6152";
	}

	private CaminhaoEntity caminhao(ResultSet rs) throws Exception {
		CaminhaoEntity caminhao = new CaminhaoEntity();
		caminhao.setId(rs.getLong("vehicle_id"));
		caminhao.setName(rs.getString("vehicle_plates"));
		caminhao.setPrefix(rs.getString("vehicle_prefix"));
		caminhao.setDescription(rs.getString("description"));
		caminhao.setCustomerChildId(rs.getLong("customer_child_id"));
		caminhao.setOperatorId(rs.getLong("driver_id"));
		
		//caminhao.setProjectId(36200l);
		//caminhao.setProjectIdName("EUCALIPTOS");
		
		
		caminhao.setEventCode(rs.getLong("cod_evento"));
		caminhao.setLatitude(rs.getFloat("latitude"));
		caminhao.setLongitude(rs.getFloat("longitude"));
		caminhao.setEventDateTime(rs.getDate("data_hora"));
		caminhao.setAvldateTime(rs.getDate("date_hour"));
		caminhao.setProject(project(rs.getString("ent_dados")));
		caminhao.setField(field(rs.getString("ent_dados")));
		caminhao.setSpot(spot(rs.getString("ent_dados")));
		caminhao.setPlates(plates(rs.getString("ent_dados")));
		
		caminhao.setSpeed(rs.getInt("vel"));
		
		if (isProduction) {
			caminhao.setLoading(rs.getInt("operational_statep"));	
		} else {
			caminhao.setLoading(rs.getInt("operational_stateh"));
		}
		
		caminhao.setOperatorName(rs.getString("operatorName"));
		caminhao.setEventReportId(rs.getLong("evento_id"));
		caminhao.setHourmeter(rs.getFloat("horimetro"));
		
		// downtime
		caminhao.setGroupId(rs.getInt("groupid"));
		caminhao.setDowntimeCode(rs.getInt("downtimecode"));
		
		
		Clob clob;
		
		if (isProduction) {
			clob = rs.getClob("custom_columnsp");
		} else {
			clob = rs.getClob("custom_columnsh");
		}
		
		if (clob != null) {
			String json = (String) clob.getSubString(1, (int) clob.length());
			CaminhaoEntity caminhaoJson = JsonUtils.fromJson(json, CaminhaoEntity.class);
			if (caminhaoJson != null) {
				
				if (caminhaoJson.getLatitude() != caminhao.getLatitude()) {
					caminhao.setPrevLatitude(caminhaoJson.getLatitude());
				}
				
				if (caminhaoJson.getLongitude() != caminhao.getLongitude()) {
					caminhao.setPrevLongitude(caminhaoJson.getLongitude());
				}
				
				// data pcct
				caminhao.setDataPartidaPcct(caminhaoJson.getDataPartidaPcct());
				
				// data pcsi
				caminhao.setDataChegadaPcsi(caminhaoJson.getDataChegadaPcsi());
				
				// data fila pcsi
				caminhao.setSiteFilaDate(caminhaoJson.getSiteFilaDate());
				
				
				// previsao e distancia fabrica
				caminhao.setDataPrevisaoChegadaFabrica(caminhaoJson.getDataPrevisaoChegadaFabrica());
				caminhao.setLastUpdPrevisaoChegadaFabrica(caminhaoJson.getLastUpdPrevisaoChegadaFabrica());
				caminhao.setDistMtsFabrica(caminhaoJson.getDistMtsFabrica());
				
				// fluxo transp
				caminhao.setTranspEstadoOperacional(caminhaoJson.getTranspEstadoOperacional());
				caminhao.setTranspFluxo(caminhaoJson.getTranspFluxo());
				caminhao.setTranspDate(caminhaoJson.getTranspDate());

				// fluxo carreg
				caminhao.setCarregEstadoOperacional(caminhaoJson.getCarregEstadoOperacional());
				caminhao.setCarregFluxo(caminhaoJson.getCarregFluxo());
				caminhao.setCarregDate(caminhaoJson.getCarregDate());

				// fluxo site
				caminhao.setSiteEstadoOperacional(caminhaoJson.getSiteEstadoOperacional());
				caminhao.setSiteFluxo(caminhaoJson.getSiteFluxo());
				caminhao.setSiteDate(caminhaoJson.getSiteDate());

				// fluxo proprio
				caminhao.setProprioEstadoOperacional(caminhaoJson.getProprioEstadoOperacional());
				caminhao.setProprioFluxo(caminhaoJson.getProprioFluxo());
				caminhao.setProprioDate(caminhaoJson.getProprioDate());

				// idlocal
				caminhao.setIdLocal(caminhaoJson.getIdLocal());
				caminhao.setIdGrua(caminhaoJson.getIdGrua());

				// projeto e distancia
				caminhao.setPercentualConclusao(caminhaoJson.getPercentualConclusao());
				caminhao.setDistMtsProject(caminhaoJson.getDistMtsProject());
				caminhao.setDistMtsTotal(caminhaoJson.getDistMtsTotal());
				
					
				if (caminhaoJson.getProjectId() != null) {
					caminhao.setProjectId(caminhaoJson.getProjectId());
					
				}
				
				if (caminhaoJson.getProjectIdName() != null) {
					caminhao.setProjectIdName(caminhaoJson.getProjectIdName());
				}
				
				// dados carregamento
				caminhao.setEventLoadId(caminhaoJson.getEventLoadId());
				caminhao.setIsPatioInterno(caminhaoJson.getIsPatioInterno());
				caminhao.setEventInternalId(caminhaoJson.getEventInternalId()); // pk de carregamento
				caminhao.setCraneDateTime(caminhaoJson.getCraneDateTime()); // data hora inicio carregamento na grua
				caminhao.setLocale(caminhaoJson.getLocale());
				caminhao.setChipper(caminhaoJson.getChipper()); // patio
				caminhao.setPile(caminhaoJson.getPile()); // patio
				caminhao.setCraneOperatorName(caminhaoJson.getCraneOperatorName());
				caminhao.setLoadType(caminhaoJson.getLoadType()); // 1 - carreg, 2 - descarreg
				caminhao.setPlates(caminhaoJson.getPlates()); // campo - patio
				caminhao.setProject(caminhaoJson.getProject()); // campo - projectCode
				caminhao.setField(caminhaoJson.getField()); // campo - standId

				// linha
				caminhao.setLineId(caminhaoJson.getLineId());
				caminhao.setIsStayInFactory(caminhaoJson.getIsStayInFactory());
				
				// downtime
				caminhao.setDowntime(caminhaoJson.getDowntime());
				caminhao.setLastDateDowntime(caminhaoJson.getLastDateDowntime());
				
				
				// trigger si
				caminhao.setTriggerSI(caminhaoJson.getTriggerSI());
				
			}
		}

		return caminhao;
	}

	private String project(String string) {
		String result = null;
		if (string != null) {
			String[] split = string.split(",");
			if (split.length >= 1) {
				result = split[0];
			}
		}

		return result;
	}

	private String field(String string) {
		String result = null;
		if (string != null) {
			String[] split = string.split(",");
			if (split.length >= 2) {
				result = split[1];
			}
		}
		return result;
	}

	private String spot(String string) {
		String result = null;
		if (string != null) {
			String[] split = string.split(",");
			if (split.length >= 3) {
				result = split[2];
			}
		}
		return result;
	}

	private String plates(String string) {
		String result = null;
		if (string != null) {
			String[] split = string.split(",");
			if (split.length >= 4) {
				result = split[3];
			}
		}
		return result;
	}

	private String sqlSelecionarCaminhoes(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n");
		sql.append(
				"	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, ev.evento_id, \n");
		sql.append(
				"	nvl(avl.latitude, ev.lat_inicial) latitude, nvl(avl.longitude, ev.long_inicial) longitude, avl.vel, vt.operational_statep, vt.operational_stateh, avl.date_hour, vt.custom_columnsp, vt.custom_columnsh, \n");
		sql.append("	d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro, vt.vehicle_id vehicle_temp_id \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("left join bracell.vehicles_temp vt on v.vehicle_id = vt.vehicle_id  \n");
		//sql.append("left join fleet_reg_events_temp cfg on (v.vehicle_type = cfg.assettypeid and ev.cod_evento = cfg.eventcode) \n");
		if (isProd) {
			//sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
			//sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		}
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId)
				.append("\n");
		sql.append(") and v.vehicle_type in (17) and v.vehicle_status = 1 order by v.vehicle_plates \n");

		return sql.toString();
	}
	
	
	private String sqlSelecionarCaminhoesL1(Long customerId) {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"select v.vehicle_id, v.default_driverid, v.customer_child_id, v.vehicle_plates, v.vehicle_prefix, \n");
		sql.append(
				"	v.description , ev.data_hora, ev.cod_evento, ev.ent_dados, nvl(ev.driver_id, avl.driver_id) driver_id, ev.evento_id, \n");
		sql.append(
				"	nvl(avl.latitude, ev.lat_inicial) latitude, nvl(avl.longitude, ev.long_inicial) longitude, avl.vel, vt.operational_statep, vt.operational_stateh, avl.date_hour, vt.custom_columnsp, vt.custom_columnsh, \n");
		sql.append("	d.driver_firstname||' '||d.driver_lastname as operatorName, sgf.groupid, sgf.downtimecode, avl.horimetro, vt.vehicle_id vehicle_temp_id \n");
		sql.append("from vehicles v  \n");
		sql.append("left join current_eventos ev on (v.vehicle_id = ev.vehicle_id) \n");
		sql.append("left join current_avl_locations avl on (v.vehicle_id = avl.vehicle_id) \n");
		sql.append("left join drivers d on d.driver_id = ev.driver_id  \n");
		sql.append("left join bracell.vehicles_temp vt on v.vehicle_id = vt.vehicle_id  \n");
		//sql.append("left join fleet_reg_events_temp cfg on (v.vehicle_type = cfg.assettypeid and ev.cod_evento = cfg.eventcode) \n");
		if (config.isProduction()) {
			//sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join bracell.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		} else {
			//sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento and sgf.groupid = cfg.groupid) \n");
			sql.append("left join cmpc_hom.sgfdowntimesrel sgf on (sgf.assettypeid = v.vehicle_type and sgf.eventcode = ev.cod_evento) \n");
		}
		sql.append("where  v.customer_child_id in ( \n");
		sql.append("    select customer_child_id from customer_child where customer_id = ").append(customerId)
				.append("\n");
		sql.append(") and v.vehicle_type in (17) and v.vehicle_status = 1 order by v.vehicle_plates \n");

		return sql.toString();
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

	
	private String sqlUpdateVehicle() {
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

	private String sqlUpdateVehicleCarreg() {
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

	private String sqlUpdateVehicleSite() {
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

	private String sqlInsertHistoricalState() {
		StringBuilder sql = new StringBuilder();
		if (isProduction) {
			sql.append("insert into bracell.sgfapontamento ");	
		} else {
			sql.append("insert into cmpc_hom.sgfapontamento ");
		}
		sql.append("(id, datainicio, datafim, estadooperacional, fluxooperacional, implemento_id, line_id) ");
		if (isProduction) {
			sql.append("values (bracell.seq_sgfapontamento.nextval, ?, ?, ?, ?, ?, ?)");	
		} else {
			sql.append("values (cmpc_hom.seq_sgfapontamento.nextval, ?, ?, ?, ?, ?, ?)");
		}
		 
		
		return sql.toString();
	}
	
	private String sqlCicloInterno() {
		StringBuilder sql = new StringBuilder();
		sql.append("select  \n");
		sql.append("  	ap.estadooperacional \n"); 
		sql.append("  	, v.customer_child_id as idprestador \n"); 
		sql.append("  	, avg( ");
		sql.append("     	extract (day    from ( ap.datafim - ap.datainicio ))*24*60*60 + \n"); 
		sql.append("      	extract (hour   from ( ap.datafim - ap.datainicio ))*60*60 + \n");
		sql.append("      	extract (minute from ( ap.datafim - ap.datainicio ))*60 + \n");
		sql.append("      	extract (second from ( ap.datafim - ap.datainicio )) \n");
		sql.append("     ) as duracao  \n");
		if (isProduction) {
			sql.append("from bracell.sgfapontamento ap \n");	
		} else {
			sql.append("from cmpc_hom.sgfapontamento ap \n");
		}		 
		sql.append("inner join vehicles v on (v.vehicle_id = ap.implemento_id) \n"); 
		sql.append("where ap.datainicio between ? and ? \n");
		sql.append("and (ap.line_id <> 1 or ap.line_id is null) \n");		
		sql.append("group by ap.estadooperacional, v.customer_child_id");
		return sql.toString();
	}
	
	
	
	private String sqlCicloInternoL1() {
		StringBuilder sql = new StringBuilder();
		sql.append("select  \n");
		sql.append("  	ap.estadooperacional \n"); 
		sql.append("  	, v.customer_child_id as idprestador \n"); 
		sql.append("  	, avg( ");
		sql.append("     	extract (day    from ( ap.datafim - ap.datainicio ))*24*60*60 + \n"); 
		sql.append("      	extract (hour   from ( ap.datafim - ap.datainicio ))*60*60 + \n");
		sql.append("      	extract (minute from ( ap.datafim - ap.datainicio ))*60 + \n");
		sql.append("      	extract (second from ( ap.datafim - ap.datainicio )) \n");
		sql.append("     ) as duracao  \n");
		if (isProduction) {
			sql.append("from bracell.sgfapontamento ap \n");	
		} else {
			sql.append("from cmpc_hom.sgfapontamento ap \n");
		}		 
		sql.append("inner join vehicles v on (v.vehicle_id = ap.implemento_id) \n"); 
		sql.append("where ap.datainicio between ? and ? \n");
		sql.append("and ap.line_id = 1 \n");
		sql.append("group by ap.estadooperacional, v.customer_child_id");
		return sql.toString();
	}
}

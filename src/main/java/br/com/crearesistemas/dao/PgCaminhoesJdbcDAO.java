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
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.OrdemTransporteDto;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.util.JsonUtils;

@Repository
public class PgCaminhoesJdbcDAO extends JdbcDAO implements PgCaminhoesDAO {
	Gson gson = new Gson();
	private static final Logger logger = Logger.getLogger(PgCaminhoesJdbcDAO.class);

	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	Boolean isPg = config.getRepositoryEnable();
	
	@Override
	public OrdemTransporteDto selecionarOrdemSgf(String plates) {
		OrdemTransporteDto ordem = new OrdemTransporteDto();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			if (isPg) {
				con = openRepository();
				st = con.createStatement();
				sql = sqlSelecionarOrdemSgf(plates, isProduction);
				if (printSql) {
					logger.info(sql);
				}
				rs = st.executeQuery(sql);
				if (rs.next()) {
					ordem = ordemTransporteSgf(rs);
				}
				
				
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return ordem;
	}
	
	@Override
	public InterfaceSgfDto selecionarInterfaceSgf(String plates) {
		InterfaceSgfDto interf = new InterfaceSgfDto();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			if (isPg) {
				con = openRepository();
				st = con.createStatement();
				sql = sqlSelecionarInterfaceSgf(plates, isProduction);
				if (printSql) {
					logger.info(sql);
				}
				rs = st.executeQuery(sql);
				if (rs.next()) {
					interf = interfaceSgf(rs);
				}
				
				
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return interf;
	}
	
	
	

	@Override
	public Boolean salvar(CaminhaoEntity caminhao) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = openRepository();
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
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
			// clob.free();
			close(con, st, rs);
		}
		return result;

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
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
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

		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
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

		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return result;
	}

	
	@Override
	public Boolean closeOrder(CaminhaoEntity caminhao) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = openRepository();
			sql = sqlUpdateCloseOrder(caminhao.getName());
			st = con.prepareStatement(sql);
			
			result = st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return result;
		
	}

	@Override
	public Boolean closeOlderOrder() {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		Boolean result = false;

		try {
			con = openRepository();
			sql = sqlUpdateCloseOlderOrder();
			st = con.prepareStatement(sql);
			
			result = st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return result;
		
	}

	
	@Override
	public CaminhaoTransporte selectPgCaminhao(String name) {
		CaminhaoTransporte caminhao = null;
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			if (isPg) {
				con = openRepository();
				st = con.createStatement();
				sql = sqlPgSelecionarCaminhao(name, isProduction);
				if (printSql) {
					logger.info(sql);
				}
				rs = st.executeQuery(sql);
				if (rs.next()) {
					caminhao = pgCaminhao(rs);
				}	
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return caminhao;
	}

	@Override
	public CaminhaoTransporte selectPgCaminhaoByName(String name) {
		CaminhaoTransporte caminhao = null;
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			con = openRepository();			
			sql = sqlPgSelecionarCaminhaoByName();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, name);
			
			rs = st.executeQuery();
			if (rs.next()) {
				caminhao = pgCaminhaoByName(rs);
			}	
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return caminhao;
	}

	
	@Override
	public List<CaminhaoTransporte> selecionarCaminhoesTransporte(Long customerId) {
		List<CaminhaoTransporte> caminhoes = new ArrayList<CaminhaoTransporte>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = openRepository();
			st = con.createStatement();
			sql = pgSqlSelecionarCaminhoes(customerId, isProduction);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				caminhoes.add(pgCaminhao(rs));
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			//throw new RuntimeException(exc);
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
			//throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return result;
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

	
	private CaminhaoTransporte pgCaminhao(ResultSet rs) throws Exception {
		CaminhaoTransporte caminhao = new CaminhaoTransporte();
		caminhao.setId(rs.getLong("id"));
		caminhao.setIdentificacao(rs.getString("identification"));
		caminhao.setTipoImplemento(1);
		
		EstadoOperacional stateTransp = config.convertPgStateToEstadoOperacional(rs.getInt("state_transp"));
		if (stateTransp != null) {
			caminhao.setEstadoOperacionalTransporte(stateTransp.ordinal());	
		}
		EstadoOperacional stateCarreg = config.convertPgStateToEstadoOperacional(rs.getInt("state_load"));
		caminhao.setEstadoOperacionalCarregamento(stateCarreg.ordinal());
		
		caminhao.setDataUltimoRastreamento(rs.getDate("dataRastreamento"));
		caminhao.setLatitude(rs.getFloat("latitude"));
		caminhao.setLongitude(rs.getFloat("longitude"));
		caminhao.setDataInicio(rs.getDate("dataInicio"));
		caminhao.setDataPrevistaConclusao(rs.getDate("dataPrevistaConclusao"));
		caminhao.setVelocidadeMedia(rs.getInt("velocidadeMedia"));
		caminhao.setOrdemTransporte(rs.getString("ordemTransporte"));
		caminhao.setIdProjeto(rs.getLong("idProjeto"));
		caminhao.setProjeto(rs.getString("projeto"));		
		caminhao.setTemApontamentoInvalido(false);
		caminhao.setOrdemSituacao(SituacaoOrdemTransporte.getEntity(rs.getInt("order_status_id")));
		
		if (rs.getLong("load_event_id")>0) {
			caminhao.setSemApontamento(true);	
		} else {
			caminhao.setSemApontamento(false);
		}
		
		caminhao.setPercentualConclusao(rs.getInt("percentage_close"));
		
	
		return caminhao;
	}
	
	
	private CaminhaoTransporte pgCaminhaoByName(ResultSet rs) throws Exception {
		CaminhaoTransporte caminhao = new CaminhaoTransporte();
		caminhao.setId(rs.getLong("id"));
		caminhao.setIdentificacao(rs.getString("name"));
		caminhao.setTipoImplemento(1);
		caminhao.setLineId(getLine(rs.getString("line_id")));
		caminhao.setProjeto(rs.getString("local_name"));
		caminhao.setIdProjeto(rs.getLong("local_id"));
		caminhao.setOrdemTransporte(rs.getString("num_order"));
		caminhao.setOrdemSituacao(SituacaoOrdemTransporte.getEntity(rs.getInt("order_status_id")));
		caminhao.setProjectCode(rs.getLong("project_code"));
		caminhao.setProjectId(rs.getString("project_id"));
		return caminhao;
	}
	
	
	private Integer getLine(String lineStr) {
		Integer lineId = 2;
		
		if (lineStr != null) {
			if (lineStr.toLowerCase().contains("linha 1") || 
					lineStr.toLowerCase().contains("linha1")  ||
					lineStr.toLowerCase().contains("linha  1")
				) {
					lineId = 1;
				}	
		}
		
		return lineId;
	}


	private String sqlSelecionarOrdemSgf(String plates, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append(
			"SELECT ot.id, ot.integration num_ot, ot.order_status_id status_ot, ot.date_departure, ap.identification caminhao, \r\n" + 
			"	ot.date_received , ot.scale_entrance_id , ot.planned_date, ot.plan_stock_unload_id,  \r\n" + 
			"	ot.date_completion, ot.scale_exit_id \r\n" + 
			"FROM orders ot \r\n" + 
			"inner join assets ap on (ap.order_id = ot.id)\r\n" + 
			"WHERE ap.identification = '" + plates + "'"
		);
		return sb.toString();
	}
	
	private OrdemTransporteDto ordemTransporteSgf(ResultSet rs) throws SQLException {		
		OrdemTransporteDto ordem = new OrdemTransporteDto();
		
		
		ordem.setDataFimViagem(rs.getTimestamp("date_completion"));
		ordem.setDataRecebimento(rs.getTimestamp("date_received"));
		ordem.setDataReprogramacao(rs.getTimestamp("planned_date"));
		ordem.setIdBalancaEntrada(rs.getString("scale_entrance_id"));
		ordem.setIdBalancaSaida(rs.getString("scale_exit_id"));
		
		ordem.setOtId(rs.getLong("id"));
		ordem.setOtNumero(rs.getString("num_ot"));
		ordem.setOtSituacao(SituacaoOrdemTransporte.getEntity(rs.getInt("status_ot")));		
		ordem.setOtDataLiberacao(rs.getTimestamp("date_departure"));
		return ordem;
	}


	private String sqlSelecionarInterfaceSgf(String plates, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append(
			"SELECT ot.id, ot.integration num_ot, ot.order_status_id status_ot, ot.date_departure, ap.identification caminhao, \r\n" + 
			"	ot.date_received , ot.scale_entrance_id , ot.planned_date, ot.plan_stock_unload_id, sk.integration stockCode, sk.stock_type, \r\n" + 
			"	ot.date_completion, ot.scale_exit_id \r\n" + 
			"FROM orders ot \r\n" + 
			"inner join assets ap on (ap.order_id = ot.id)\r\n" + 
			"left join stocks sk on (sk.id = ot.plan_stock_unload_id) " +
			"WHERE ap.identification = '" + plates + "'"
		);
		return sb.toString();
	}
	
	private InterfaceSgfDto interfaceSgf(ResultSet rs) throws SQLException {		
		InterfaceSgfDto interfSgf = new InterfaceSgfDto();
		
		
		Integer type = rs.getInt("stock_type");
		// quadra
		if (type == 1) {
			interfSgf.setPileCode(rs.getLong("stockCode"));	
		} else if (type > 0) { 
			// mesa
			interfSgf.setChipperCode(rs.getLong("stockCode"));
		}
		
		interfSgf.setDataFimViagem(rs.getTimestamp("date_completion"));
		interfSgf.setDataRecebimento(rs.getTimestamp("date_received"));
		interfSgf.setDataReprogramacao(rs.getTimestamp("planned_date"));
		interfSgf.setIdBalancaEntrada(rs.getString("scale_entrance_id"));
		interfSgf.setIdBalancaSaida(rs.getString("scale_exit_id"));
		
		interfSgf.setOtId(rs.getLong("id"));
		interfSgf.setOtNumero(rs.getString("num_ot"));
		interfSgf.setOtSituacao(SituacaoOrdemTransporte.getEntity(rs.getInt("status_ot")));		
		interfSgf.setOtDataLiberacao(rs.getTimestamp("date_departure"));
		return interfSgf;
	}


	private String pgSqlSelecionarCaminhoes(Long customerId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();

		sql.append("select \r\n" + 
				"	ap.id, ap.identification, ap.prefix , 1 tipoImplemento, st.operational_state_id state_transp, sl.operational_state_id state_load, t.\"date\" dataRastreamento \r\n" + 
				"	,t.latitude , t.longitude , st.date_start dataInicio, st.estimated_date_close dataPrevistaConclusao, st.mean_speed velocidadeMedia, ot.integration ordemTransporte,\r\n" + 
				"	l.integration idProjeto, 15 localDescarga, ot.load_event_id , ap.office_id , st.percentage_close\r\n" + 
				"from assets ap\r\n" + 
				"inner join state_store st on (ap.state_transp_id = st.id)\r\n" + 
				"inner join state_store sl on (ap.state_load_id = sl.id)\r\n" + 
				"inner join trackings t on (ap.tracking_id = t.id)\r\n" + 
				"inner join orders ot on (ap.order_id = ot.id)\r\n" + 
				"left join locales l on (l.id = ot.locale_load_id)\r\n" + 
				"where ap.asset_type_id = 3 and st.operational_state_id in (201, 202, 203, 207)");
		return sql.toString();
	}

	private String sqlPgSelecionarCaminhao(String plate, Boolean isProd) {
		StringBuilder sql = new StringBuilder();

		sql.append("select \r\n" + 
				"	ap.id, ap.identification, ap.prefix , 1 tipoImplemento, st.operational_state_id state_transp, sl.operational_state_id state_load, t.\"date\" dataRastreamento, \r\n" + 
				"	t.latitude , t.longitude , st.date_start dataInicio, st.estimated_date_close dataPrevistaConclusao, st.mean_speed velocidadeMedia, ot.integration ordemTransporte,\r\n" + 
				"	l.id idProjeto, l.identification projeto, 15 localDescarga, ot.load_event_id , ap.office_id , st.percentage_close,\r\n" +
				"   ot.order_status_id \r\n" +
				"from assets ap\r\n" + 
				"inner join state_store st on (ap.state_transp_id = st.id)\r\n" + 
				"inner join state_store sl on (ap.state_load_id = sl.id)\r\n" + 
				"left join trackings t on (ap.tracking_id = t.id)\r\n" + 
				"inner join orders ot on (ap.order_id = ot.id)\r\n" + 
				"left join locales l on (l.id = ot.locale_load_id)\r\n" + 
				"where ap.asset_type_id = 3 "
				+ "and ap.identification = '").append(plate).append("'");
		return sql.toString();
	}
	
	private String sqlPgSelecionarCaminhaoByName() {
		StringBuilder sql = new StringBuilder();
		sql.append("select ap.id, ap.name, ot.plan_stock_unload_id, sk.stock_type, \r\n" + 
				"	coalesce(cp.identification,pl.integration::text) identification ,\r\n" + 
				"	coalesce(cp.line_integration_id,  pl.line_integration_id) line_id,  \r\n" +
				"   l.identification local_name, l.id local_id, l.integration project_code, l.farm_integration_id project_id, \r\n" +
				"   ot.order_status_id, ot.integration num_order \r\n" +
				"from assets ap\r\n" + 
				"left join orders ot on (ap.order_id = ot.id) \r\n" + 
				"left join stocks sk on (sk.id = ot.plan_stock_unload_id)\r\n" + 
				"left join chippers cp on (sk.chipper_id = cp.id)\r\n" + 
				"left join piles pl on (sk.pile_id = pl.id) \r\n" + 
				"left join locales l on (l.id = ot.locale_load_id) \r\n" +
				"where ap.name = ? or ap.identification = ? ");
		return sql.toString();
	}
	

	
	private String sqlUpdateVehicle() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE vehicles\n");
		sql.append("SET\n");
		sql.append("	custom_columns1 = ? \n");
		sql.append("WHERE\n");
		sql.append("	vehicle_id = ?");
		return sql.toString();
	}

	private String sqlUpdateVehicleCarreg() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE vehicles\n");
		sql.append("SET\n");
		sql.append("	custom_columns1 = ?, operational_state = ? \n");
		sql.append("WHERE\n");
		sql.append("	vehicle_id = ?");
		return sql.toString();
	}

	private String sqlUpdateVehicleSite() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE vehicles\n");
		sql.append("SET\n");
		sql.append("	custom_columns1 = ? \n");
		sql.append("WHERE\n");
		sql.append("	vehicle_id = ?");
		return sql.toString();
	}

	private String sqlUpdateCloseOrder(String plates) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders \n");
		sql.append("SET \n");
		sql.append("	order_status_id = 5 \n");
		sql.append("WHERE \n");
		sql.append(String.format("	id in (select order_id from assets where identification in ('%s')) \n", plates));
		sql.append("	and order_status_id = 4 \n");
		return sql.toString();
	}
	
	private String sqlUpdateCloseOlderOrder() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders \n");
		sql.append("SET \n");
		sql.append("	order_status_id = 5 \n");
		sql.append("WHERE \n");
		sql.append("	order_status_id = 4 and \n");
		sql.append("	(date_received < now() - interval '24 hours' or date_departure < now() - interval '120 hours')");
		return sql.toString();
	}
}

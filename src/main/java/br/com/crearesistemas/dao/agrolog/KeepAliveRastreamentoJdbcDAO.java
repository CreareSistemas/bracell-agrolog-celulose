package br.com.crearesistemas.dao.agrolog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.dao.JdbcDAO;
import br.com.crearesistemas.model.agrolog.KeepAliveRastreamento;
import br.com.crearesistemas.util.NumberUtils;


@Repository("rastreamentoAgrologJdbcDAO")
public class KeepAliveRastreamentoJdbcDAO extends JdbcDAO implements KeepAliveRastreamentoDAO {

	private static final Logger logger = Logger.getLogger(KeepAliveRastreamentoJdbcDAO.class);

	
	private String sqlBuscarRastreamentos(long mId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, numversao, data, latitude, longitude, tiporastreamento, dispositivo_id, implemento_id, \n");
		sql.append("hodometro, horimetro, ignicao, rpm, velocidade, estadoOperacional0, estadoOperacional1, estadoOperacional2, heading_angle \n");
		sql.append("  FROM bracell.keepaliverastreamento \n");
		sql.append("  WHERE id = ?");
		return sql.toString();
	}
	
	
	
	private String sqlInserirRastreamento() {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO bracell.keepaliverastreamento (numversao, datahistorico, data, latitude, longitude, tiporastreamento, dispositivo_id, \n");
		sql.append("implemento_id, hodometro, horimetro, ignicao, rpm, velocidade, estadoOperacional0, estadoOperacional1, estadoOperacional2, heading_angle) \n");
		sql.append("  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		return sql.toString();
	}


	@Override
	public KeepAliveRastreamento buscarRastreamento(long implementoId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlBuscarRastreamentos(implementoId);
			if (printSql) {
				logger.info(sql);
			}
			ps = con.prepareStatement(sql);
			ps.setLong(1, implementoId);
			rs = ps.executeQuery();
			
			KeepAliveRastreamento rastreamento = null;
			if (rs.next()) {
				rastreamento = rastreamento(rs);
			}
			return rastreamento;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, ps, rs);
		}
	}

	/**
	 * Converte uma instancia de um <code>ResultSet</code> em uma instancia de um objeto <code>Rastreamento</code>.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> com os dados selecionados do base.
	 * @return Um objeto do tipo <code>Telemetria</code> com os dados obtidos do <code>ResultSet</code>.
	 * @throws Exception
	 *             Este metodo nao trata suas excecoes: em caso de erro, uma excecao e lancada
	 */
	private KeepAliveRastreamento rastreamento(ResultSet rs) throws Exception {
		KeepAliveRastreamento rastreamento = new KeepAliveRastreamento();
		rastreamento.setNumVersao(rs.getInt("numversao"));
		rastreamento.setData(rs.getTimestamp("dataHistorico"));
		rastreamento.setData(rs.getTimestamp("data"));
		rastreamento.setLatitude(rs.getFloat("latitude"));
		rastreamento.setLongitude(rs.getFloat("longitude"));
		rastreamento.setTipoRastreamento(NumberUtils.parseInt((String) rs.getObject("tiporastreamento")));
		rastreamento.setDispositivo_id(NumberUtils.parseLong((String) rs.getObject("dispositivo_id")));
		rastreamento.setImplemento_id(rs.getLong("implemento_id"));
		rastreamento.setIgnicao(NumberUtils.parseInt((String) rs.getObject("ignicao")));
		rastreamento.setHodometro(NumberUtils.parseLong((String) rs.getObject("hodometro")));
		rastreamento.setHorimetro(NumberUtils.parseLong((String) rs.getObject("horimetro")));
		rastreamento.setRpm(NumberUtils.parseInt((String) rs.getObject("rpm")));
		rastreamento.setVelocidade(NumberUtils.parseInt((String) rs.getObject("velocidade")));
		rastreamento.setEstadoOperacional0(NumberUtils.parseInt((String) rs.getObject("estadoOperacional0")));
		rastreamento.setEstadoOperacional1(NumberUtils.parseInt((String) rs.getObject("estadoOperacional1")));
		rastreamento.setEstadoOperacional2(NumberUtils.parseInt((String) rs.getObject("estadoOperacional2")));
		rastreamento.setAnguloDirecao(NumberUtils.parseObjectDouble((String) rs.getObject("heading_angle")));
		return rastreamento;
	}
	
	
	@Override
	public boolean insereKeepAliveRastreamento(KeepAliveRastreamento rastreamento) {
		boolean result = false;
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirRastreamento();
			if (printSql || true) {
				logger.info(sql);
				logger.info(rastreamento.toString());
			}
			ps = con.prepareStatement(sql);
			result = parametros(ps, rastreamento).execute();
			
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, ps, rs);
		}
		return result;
	}

	private PreparedStatement parametros(PreparedStatement ps,
			KeepAliveRastreamento rastreamento) throws SQLException {
		
		ps.setInt(1, rastreamento.getNumVersao());
		ps.setTimestamp(2, getTimestamp(rastreamento.getDataHistorico()));
		ps.setTimestamp(3, getTimestamp(rastreamento.getData()));
		ps.setFloat(4, rastreamento.getLatitude());
		ps.setFloat(5, rastreamento.getLongitude());
		ps.setInt(6, rastreamento.getTipoRastreamento());
		ps.setLong(7, rastreamento.getDispositivo_id());
		ps.setLong(8, rastreamento.getImplemento_id());
		ps.setObject(9, rastreamento.getHodometro());
		ps.setObject(10, rastreamento.getHorimetro());
		ps.setObject(11, rastreamento.getIgnicao());
		ps.setObject(12, rastreamento.getRpm());
		ps.setObject(13, rastreamento.getVelocidade());
		ps.setObject(14, rastreamento.getEstadoOperacional0()); //Estado Operacional Proprio
		ps.setObject(15, rastreamento.getEstadoOperacional1()); //Estado Operacional Carregamento
		ps.setObject(16, rastreamento.getEstadoOperacional2()); //Estado Opercional Transporte
		ps.setObject(17, rastreamento.getAnguloDirecao()); // angulo de dire��o
		return ps;
	}

	
	
	public boolean destinationHasRecord(Long implementoId) {
	    boolean result = false;
	    
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "select 1 from bracell.keepaliverastreamento where implemento_id = ?";  

		ResultSet rs = null;
		try {
			
			con = open();
			if (printSql) {
				logger.info(sql);
			}
		    ps = con.prepareStatement(sql);
		    ps.setLong(1, implementoId);
		    rs = ps.executeQuery();
		    result = rs.next();
			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, ps, rs);
		}
		return result;
	}

	@Override
	public boolean atualizaRastreamento(KeepAliveRastreamento rastreamento) {
		   boolean result = false;
		    
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "update bracell.keepaliverastreamento "
					+ "set numversao = numversao + 1, "
					+ "dataHistorico = ?, "
					+ "data = ?, "
					+ "latitude = ?, "
					+ "longitude = ?, "
					+ "tiporastreamento = ?, "
					+ "dispositivo_id = ?, "
					+ "hodometro = ?, "
					+ "horimetro = ?, "
					+ "ignicao = ?, "
					+ "rpm = ?, "
					+ "velocidade = ?, "
					+ "estadoOperacional0 = ?, "
					+ "estadoOperacional1 = ?, "
					+ "estadoOperacional2 = ?, "
					+ "heading_angle = ? "
					+ "where implemento_id = ?";  

			ResultSet rs = null;
			try {				
				con = open();
				if (printSql) {
					logger.info(sql);
				}
			    ps = con.prepareStatement(sql);
			    ps.setTimestamp(1, getTimestamp(rastreamento.getDataHistorico()));
			    ps.setTimestamp(2, getTimestamp(rastreamento.getData()));
			    ps.setFloat(3, rastreamento.getLatitude());
			    ps.setFloat(4, rastreamento.getLongitude());
			    ps.setInt(5, rastreamento.getTipoRastreamento());
			    ps.setLong(6, rastreamento.getDispositivo_id());
			    ps.setObject(7, rastreamento.getHodometro());
			    ps.setObject(8, rastreamento.getHorimetro());
			    ps.setObject(9, rastreamento.getIgnicao());
			    ps.setObject(10, rastreamento.getRpm());
			    ps.setObject(11, rastreamento.getVelocidade());
			    ps.setObject(12, rastreamento.getEstadoOperacional0());
			    ps.setObject(13, rastreamento.getEstadoOperacional1());
			    ps.setObject(14, rastreamento.getEstadoOperacional2());
			    ps.setObject(15, rastreamento.getAnguloDirecao());
			    ps.setLong(16, rastreamento.getImplemento_id());			    
			    ps.execute();
			    result = true;
			} catch (Exception exc) {
				logger.error(sql, exc);
				throw new RuntimeException(exc);
			} finally {
				close(con, ps, rs);
			}
			return result;
	}
	
}

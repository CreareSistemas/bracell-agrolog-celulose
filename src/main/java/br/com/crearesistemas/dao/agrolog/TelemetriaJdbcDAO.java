package br.com.crearesistemas.dao.agrolog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.dao.JdbcDAO;
import br.com.crearesistemas.model.agrolog.Telemetria;

@Repository("teletriaAgrologJdbcDAO")
public class TelemetriaJdbcDAO extends JdbcDAO implements TelemetriaDAO {

	private static final Logger logger = Logger.getLogger(TelemetriaJdbcDAO.class);


	private String sqlBuscarTelemetrias(long mId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, numversao, data, hodometro, horimetro, ignicao, rpm, velocidade \n");
		sql.append("  FROM bracell.telemetria \n");
		sql.append("  WHERE id = ?");
		return sql.toString();
	}
	
	private String sqlInserirTelemetria() {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO bracell.telemetria (id, numversao, data, hodometro, horimetro, ignicao, rpm, velocidade) \n");
		sql.append("  values (bracell.SEQ_TELEMETRIA.nextval,?,?,?,?,?,?,?)");
		return sql.toString();
	}


	@Override
	public List<Telemetria> buscarTelemetrias(long mId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlBuscarTelemetrias(mId);
			if (printSql) {
				logger.info(sql);
			}
			ps = con.prepareStatement(sql);
			ps.setLong(1, mId);
			rs = ps.executeQuery();
			
			List<Telemetria> telemetrias = new ArrayList<Telemetria>();
			while (rs.next()) {
				telemetrias.add(telemetria(rs));
			}
			return telemetrias;
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
	private Telemetria telemetria(ResultSet rs) throws Exception {
		Telemetria telemetria = new Telemetria();
		telemetria.setId(rs.getLong("id"));
		telemetria.setNumVersao(rs.getInt("numversao"));
		telemetria.setData(rs.getTimestamp("data"));
		telemetria.setHodometro(rs.getLong("hodometro"));
		telemetria.setHorimetro(rs.getLong("horimetro"));
		telemetria.setIgnicao(rs.getInt("ignicao"));
		telemetria.setRpm(rs.getInt("rpm"));
		telemetria.setVelocidade(rs.getInt("velocidade"));
		return telemetria;
	}

	@Override
	public boolean insereTelemetria(Telemetria telemetria) {
		boolean result = false;
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirTelemetria();
			if (printSql) {
				logger.info(sql);
			}
			ps = con.prepareStatement(sql);
			result = parametros(ps, telemetria).execute();
			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, ps, rs);
		}
		return result;
	}

	private PreparedStatement parametros(PreparedStatement ps,
			Telemetria telemetria) throws SQLException {
		
		//ps.setLong(1, telemetria.getId());
		ps.setInt(1, telemetria.getNumVersao());
		ps.setTimestamp(2, getTimestamp(telemetria.getData()));
		ps.setLong(3, telemetria.getHodometro());
		ps.setLong(4, telemetria.getHorimetro());
		ps.setInt(5, telemetria.getIgnicao());
		ps.setInt(6, telemetria.getRpm());
		ps.setInt(7, telemetria.getVelocidade());
		
		return ps;
	}

	public boolean destinationHasRecord(Long id) {
	    boolean result = false;
	    
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "select 1 from bracell.telemetria where id = ?";  

		ResultSet rs = null;
		try {
			
		    con = open();
			if (printSql) {
				logger.info(sql);
			}
		    ps = con.prepareStatement(sql);
		    ps.setLong(1, id);
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
	public boolean atualizaTelemetria(Telemetria telemetria) {
		   boolean result = false;
		    
			Connection con = null;
			PreparedStatement ps = null;
			String sql = "update bracell.telemetria "
					+ "set numversao = ?, "
					+ "data = ?, "
					+ "hodometro = ?, "
					+ "horimetro = ?, "
					+ "ignicao = ?, "
					+ "rpm = ?, "
					+ "velocidade = ? "
					+ "where id = ?";  

			ResultSet rs = null;
			try {				
			    con = open();
				if (printSql) {
					logger.info(sql);
				}
			    ps = con.prepareStatement(sql);
			    ps.setLong(1, telemetria.getNumVersao());
			    ps.setTimestamp(2, getTimestamp(telemetria.getData()));
			    ps.setLong(3, telemetria.getHodometro());
			    ps.setLong(4, telemetria.getHorimetro());
			    ps.setInt(5, telemetria.getIgnicao());
			    ps.setInt(6, telemetria.getRpm());
			    ps.setInt(7, telemetria.getVelocidade());
			    ps.setLong(8, telemetria.getId());
			    
			    result = ps.execute();
				
			} catch (Exception exc) {
				logger.error(sql, exc);
				throw new RuntimeException(exc);
			} finally {
				close(con, ps, rs);
			}
			return result;
	}
	
}

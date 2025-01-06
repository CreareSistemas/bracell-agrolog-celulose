package br.com.crearesistemas.dao.agrolog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.dao.JdbcDAO;
import br.com.crearesistemas.model.agrolog.Dispositivo;
import br.com.crearesistemas.util.NumberUtils;


@Repository
public class DispositivoJdbcDAO extends JdbcDAO implements DispositivoDAO {

	private static final Logger logger = Logger.getLogger(DispositivoJdbcDAO.class);


	@Override
	public Dispositivo selecionaPorIdentificador(String identificador) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlBuscarDispositivoPorIdentificador();
			if (printSql) {
				logger.info(sql);
			}
			ps = con.prepareStatement(sql);
			ps.setString(1, identificador);
			rs = ps.executeQuery();
			
			Dispositivo dispositivo = null;
			if (rs.next()) {
				dispositivo = dispositivo(rs);
			}
			return dispositivo;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, ps, rs);
		}
	}

	/**
	 * Converte uma instancia de um <code>ResultSet</code> em uma instancia de um objeto <code>Dispositivo</code>.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> com os dados selecionados do base.
	 * @return Um objeto do tipo <code>Dispositivo</code> com os dados obtidos do <code>ResultSet</code>.
	 * @throws Exception
	 *             Este metodo nao trata suas excecoes: em caso de erro, uma excecao e lancada
	 */
	private Dispositivo dispositivo(ResultSet rs) throws Exception {
		Dispositivo dispositivo = new Dispositivo();
		dispositivo.setEquipamento_id(NumberUtils.parseLong(rs.getObject("equipamento_id").toString()));
		dispositivo.setId(rs.getLong("id"));
		dispositivo.setNumVersao(rs.getInt("numVersao"));
		dispositivo.setTipoDispositivo(rs.getInt("tipoDispositivo"));
		return dispositivo;
	}


	private String sqlBuscarDispositivoPorIdentificador() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, numversao, identificador, tipodispositivo, equipamento_id \n");
		sql.append("  FROM bracell.dispositivo \n");
		sql.append("  WHERE identificador = ?");
		return sql.toString();
	}
	
}

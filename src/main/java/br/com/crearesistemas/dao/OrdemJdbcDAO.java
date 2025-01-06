package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import br.com.crearesistemas.model.Ordem;

@Repository("ordemJdbcDAO")
public class OrdemJdbcDAO extends JdbcDAO implements OrdemDAO {

	private static final Logger logger = Logger.getLogger(OrdemJdbcDAO.class);

	@Override
	public List<Ordem> buscarOrdens() {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = openRepository();
			st = con.createStatement();
			sql = sqlBuscarOrdens();
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			List<Ordem> ordens = new ArrayList<Ordem>();
			while (rs.next()) {
				ordens.add(ordem(rs));
			}
			return ordens;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	private String sqlBuscarOrdens() {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM orders ");
		return sql.toString();
	}


	
	private Ordem ordem(ResultSet rs) throws Exception {
		Ordem ordem = new Ordem();
		ordem.setId(rs.getLong("id"));
		ordem.setIntegration(rs.getString("integration"));
		ordem.setAssetId(rs.getLong("asset_id"));
		ordem.setRegistration(rs.getString("registration"));
		return ordem;
	}

}

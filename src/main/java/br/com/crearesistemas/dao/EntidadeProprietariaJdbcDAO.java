package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.model.EntidadeProprietaria;

@Repository
public class EntidadeProprietariaJdbcDAO extends JdbcDAO implements EntidadeProprietariaDAO {
	private static final Logger logger = Logger.getLogger(EntidadeProprietariaJdbcDAO.class);

	private String sqlBuscarEntidade(long customerId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT cc.customer_child_name,\n");
		sb.append("       cp.customer_cnpj,\n");
		sb.append("       cp.customer_insc_est\n");
		sb.append("FROM   customer_profile cp,\n");
		sb.append("       customer_child   cc\n");
		sb.append("WHERE  cc.customer_child_id = cp.customer_child_id\n");
		sb.append("AND    cc.customer_child_id = ").append(customerId).append("\n");
		sb.append(" \n");
		return sb.toString();
	}

	@Override
	public EntidadeProprietaria buscarEntidadeProprietaria(long customerId) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		EntidadeProprietaria entidade = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlBuscarEntidade(customerId);
			rs = st.executeQuery(sql);
			if (rs.next()) {
				entidade = entidade(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return entidade;
	}

	private EntidadeProprietaria entidade(ResultSet rs) throws Exception {
		EntidadeProprietaria entidade = new EntidadeProprietaria();
		entidade.setCustomer_child_name(rs.getString("customer_child_name"));
		entidade.setCustomer_cnpj(rs.getString("customer_cnpj").replaceAll("\\D", ""));
		entidade.setCustomer_insc_est(rs.getString("customer_insc_est"));
		return entidade;

	}

}

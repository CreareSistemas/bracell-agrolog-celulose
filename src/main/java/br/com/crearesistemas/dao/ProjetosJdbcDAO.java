package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;

@Repository
public class ProjetosJdbcDAO extends JdbcDAO implements ProjetosDAO {

	private static final Logger logger = Logger.getLogger(ProjetosJdbcDAO.class);

	
	@Override
	public List<ProjetoEntity> selecionarProjetos(Long customerId) {
		List<ProjetoEntity> projetos = new ArrayList<ProjetoEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarProjetos(customerId);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);			
			while (rs.next()) {
				projetos.add(projeto(rs));
			}			
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return projetos;
	}

	@Override
	public SgfProject selecionarProjetoByProjectCode(Long projectCode) {
		SgfProject projeto = null;
		Connection con = null;		
		String sql = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			con = open();			
			sql = sqlSelecionarProjetoByProjectCode();
			st = con.prepareStatement(sql);
			if (printSql) {
				logger.info(sql);
			}
			st.setLong(1, projectCode);
			rs = st.executeQuery();
			
			if(rs.next()) {
				projeto = sgfProject(rs);
			}			
		} catch (Exception exc) {
			exc.printStackTrace();
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return projeto;
	}


	private String sqlSelecionarProjetoByProjectCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		if (config.isProduction()) {
			sb.append("FROM bracell.sgfprojects \n");
		} else {
			sb.append("FROM cmpc_hom.sgfprojects \n");
		}
		sb.append("WHERE  \n");
		sb.append(" projectcode = ? \n");
		return sb.toString();
	}

	private SgfProject sgfProject(ResultSet rs) throws Exception {
		SgfProject projeto = new SgfProject();
		projeto.setProjectCode(rs.getLong("projectcode"));
		projeto.setDescription(rs.getString("description"));
		projeto.setProjectId(rs.getString("projectid"));
		projeto.setStatus(rs.getString("status"));
		return projeto;
	}
	
	private ProjetoEntity projeto(ResultSet rs) throws Exception {
		ProjetoEntity projeto = new ProjetoEntity();
		projeto.setId(rs.getLong("customer_project_id"));
		projeto.setName(rs.getString("project_name"));
		return projeto;
	}
	
	
	private String sqlSelecionarProjetos(Long customerId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT customer_project_id, customer_child_id, project_id, project_name, geometry,\n"); 
		sql.append("	object_id, region_name, import_origin, status, inserted_on, \n");
		sql.append("	update_datetime, center_id \n");
		sql.append("FROM customer_projects WHERE customer_child_id IN ( \n");
		sql.append("    SELECT customer_child_id FROM customer_child WHERE customer_id = ").append(customerId).append("\n");
		sql.append(")");
		
		return sql.toString();
	}



	

	
	
}

package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Geometry;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.util.GeometryOracleUtil;

@Repository
public class PgProjetosJdbcDAO extends JdbcDAO implements PgProjetosDAO {

	private static final Logger logger = Logger.getLogger(PgProjetosJdbcDAO.class);
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	Boolean isPg = config.getRepositoryEnable();
	
	
	@Override
	public ProjetoEntity selecionarProjetoProximos(Float latitude, Float longitude, Integer limiteMetros) {
		ProjetoEntity projeto = null;
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				st = con.createStatement();
				sql = sqlSelecionarProjetosProximos(latitude, longitude, limiteMetros);
				if (printSql) {
					logger.info(sql);
				}
				rs = st.executeQuery(sql);			
				if (rs.next()) {
					projeto = projetoDist(rs);
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return projeto;
	}
	
	
	@Override
	public ProjetoEntity selecionarProjetoById(Float latitude, Float longitude, Long localId) {
		ProjetoEntity projeto = null;
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				st = con.createStatement();
				sql = sqlSelecionarProjetoById(latitude, longitude, localId);
				if (printSql) {
					logger.info(sql);
				}
				rs = st.executeQuery(sql);			
				if (rs.next()) {
					projeto = projetoDist(rs);
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return projeto;
	}
	
	
		@Override
	public ProjetoEntity selecionarProjetoPrevisao(Long projectId, Float latitude, Float longitude) {
			ProjetoEntity projeto = null;
			Connection con = null;
			Statement st = null;
			String sql = null;
			ResultSet rs = null;
			try {
				if (isPg) {
					con = openRepository();
					st = con.createStatement();
					sql = sqlSelecionarProjetoPrevisao(projectId, config.getCoordFabrica()[0], config.getCoordFabrica()[1], latitude, longitude);
					if (printSql) {
						logger.info(sql);
					}
					rs = st.executeQuery(sql);			
					if (rs.next()) {
						projeto = projetoDistPrevisao(rs);
					}	
				}			
			} catch (Exception exc) {
				logger.error(sql, exc);
				throw new RuntimeException(exc);
			} finally {
				close(con, st, rs);
			}
			return projeto;
	}

	
	@Override
	public List<ProjetoEntity> selecionarProjetos(Long customerId) {
		List<ProjetoEntity> projetos = new ArrayList<ProjetoEntity>();
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				st = con.createStatement();
				sql = sqlSelecionarProjetos(customerId);
				if (printSql) {
					logger.info(sql);
				}
				rs = st.executeQuery(sql);			
				while (rs.next()) {
					projetos.add(projeto(rs));
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
		return projetos;
	}


	private ProjetoEntity projeto(ResultSet rs) throws Exception {
		ProjetoEntity projeto = new ProjetoEntity();
		projeto.setId(rs.getLong("id"));
		projeto.setName(rs.getString("identification"));
		return projeto;
	}
	
	private ProjetoEntity projetoDist(ResultSet rs) throws Exception {
		ProjetoEntity projeto = new ProjetoEntity();
		projeto.setId(rs.getLong("id"));
		projeto.setName(rs.getString("identification"));
		projeto.setDistancia(rs.getDouble("dist_meters"));
		projeto.setProjectCode(rs.getLong("project_code"));
		projeto.setProjectId(rs.getString("project_id"));
		return projeto;
	}
	
	private ProjetoEntity projetoDistPrevisao(ResultSet rs) throws Exception {
		ProjetoEntity projeto = new ProjetoEntity();
		projeto.setId(rs.getLong("localId"));
		projeto.setName(rs.getString("project"));
		projeto.setDistMtsProject(rs.getDouble("dist_proj_meters"));
		projeto.setDistMtsTotal(rs.getDouble("dist_total"));
		return projeto;
	}
	
	private String sqlSelecionarProjetos(Long customerId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("select  distinct l.id, l.integration, l.identification from orders ot \r\n" + 
				"inner join locales l on (ot.locale_load_id = l.id)\r\n" + 
				"where ot.order_status_id = 4 and ot.updated_at > now() - interval '240 hours' "); 
		
		sql.append("union select distinct l.id, l.integration, l.identification from assets a "
				+ "inner join locales l on (a.locale_id = l.id) where a.asset_type_id in (1)");
		
		return sql.toString();
	}

	
	private String sqlSelecionarProjetosProximos(Float latitude, Float longitue, Integer limiteMetros) {
		StringBuilder sql = new StringBuilder();
		sql.append(
			String.format(
			"select id, identification, project_code, project_id, dist_meters from (SELECT id, identification, integration project_code, farm_integration_id project_id, round(CAST(ST_DistanceSphere(locales.geometry, "
			+ "ST_GeomFromText('POINT(%s %s)',4326)) As numeric),2) As dist_meters  from locales where locale_type_id IN (6,7)) t "
			+ "where t.dist_meters < %d order by t.dist_meters asc",  String.valueOf(longitue).replaceAll(",", "."), String.valueOf(latitude).replaceAll(",", "."),limiteMetros)				
		);
		return sql.toString();
	}
	
	
	private String sqlSelecionarProjetoById(Float latitude, Float longitue, Long localeId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
			String.format(
			"SELECT id, identification, farm_integration_id project_id, integration project_code, round(CAST(ST_DistanceSphere(locales.geometry, "
			+ "ST_GeomFromText('POINT(%s %s)',4326)) As numeric),2) As dist_meters  from locales where locale_type_id IN (6,7) "
			+ "and id = %d ",  String.valueOf(longitue).replaceAll(",", "."), String.valueOf(latitude).replaceAll(",", "."), localeId)				
		);
		return sql.toString();
	}

	private String sqlSelecionarProjetoPrevisao(Long projectId, Double latFab, Double longFab, Float latitude,
			Float longitude) {
		StringBuilder sql = new StringBuilder();
		sql.append(
			String.format(
				"SELECT id localId, identification project, \n" + 
				"round(CAST(ST_DistanceSphere(locales.geometry, ST_GeomFromText('POINT(%s %s)',4326)) As numeric),2) As dist_proj_meters, \n" + 
				"round(CAST(ST_DistanceSphere(locales.geometry, ST_GeomFromText('POINT(%s %s)',4326)) As numeric),2) As dist_total \n" + 
				"from locales where id = %s",  
				String.valueOf(longitude).replaceAll(",", "."), 
				String.valueOf(latitude).replaceAll(",", "."),
				String.valueOf(longFab).replaceAll(",", "."), 
				String.valueOf(latFab).replaceAll(",", "."),
				projectId
			));
		return sql.toString();
	}




	public Boolean saveGeoLocation(String identification, Geometry geometry) {
		Connection con = null;
		PreparedStatement pst = null;
		String sql = null;
		ResultSet rs = null;
		Boolean res  = false;
		try {
			con = openRepository();
			
			sql = sqlPgUpdateGeoLocale();
			if (printSql) {
				logger.info(sql);
			}
			pst = con.prepareStatement(sql);
			
			pst.setObject(1, GeometryOracleUtil.geometryToOracleStruct(geometry, con, 4326)); //geometry
			pst.setString(2, identification);
			
			res = pst.executeUpdate(sql) > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, pst, rs);
		}
		return res;
		
	}




	private String sqlPgUpdateGeoLocale() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into locales (id, identification, geometry) values");
		sql.append("(nextval('locales_id_seq'), ?, ?)");		
		return sql.toString();
	}

	
	
}

package br.com.crearesistemas.dao;

import java.awt.geom.Point2D;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.model.Cerca;
import br.com.crearesistemas.util.NumberUtils;

@Repository
public class CercaJdbcDAO extends JdbcDAO implements CercaDAO {

	private static final Logger logger = Logger.getLogger(CercaJdbcDAO.class);

	private String sqlExistsCerca() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM fences f\n");
		sql.append("WHERE\n");
		sql.append("	f.customer_child_id = ? AND\n");
		sql.append("	f.project_id = ? AND\n");
		sql.append("	f.cd_road = ? AND\n");
		sql.append("	f.objectId = ?");
		return sql.toString();
	}

	private String sqlInsertCerca() {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO fences (\n");
		sql.append("	customer_child_id, project_id, cd_road, objectId, description,\n");
		sql.append("	latitude_sup, longitude_sup, latitude_inf, longitude_inf,\n");
		sql.append("	liv, liv_evto,\n");
		sql.append("	polygon,\n");
		sql.append("	status, fences_type_id\n");
		sql.append(") VALUES (\n");
		sql.append("	?, ?, ?, ?, ?,\n");
		sql.append("	?, ?, ?, ?,\n");
		sql.append("	?, ?,\n");
		sql.append("	?,\n");
		sql.append("	?, ?\n");
		sql.append(")");
		return sql.toString();
	}

	private String sqlUpdateCerca() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE fences\n");
		sql.append("SET\n");
		sql.append("	description = ?,\n");
		sql.append("	latitude_sup = ?, longitude_sup = ?, latitude_inf = ?, longitude_inf = ?,\n");
		sql.append("	liv = ?, liv_evto = ?,\n");
		sql.append("	polygon = ?\n");
		sql.append("WHERE\n");
		sql.append("	customer_child_id = ? AND project_id = ? AND cd_road = ? AND objectId = ?");
		return sql.toString();
	}

	private String sqlEmbarcaCercas() {
		StringBuilder sql = new StringBuilder();
		sql.append("BEGIN\n");
		sql.append("	pkg_cpmc.embarcaCercas (?, ?, ?, ?, ?, ?);\n");
		sql.append("END;");
		return sql.toString();
	}

	@Override
	public boolean exists(Long clienteId, String idProjeto, String cdRota, Long objectId) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlExistsCerca();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, clienteId);
			st.setString(2, idProjeto);
			st.setLong(3, NumberUtils.parseLong(cdRota));
			st.setLong(4, objectId);
			rs = st.executeQuery();
			return rs.next() && rs.getInt(1) > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
			RuntimeException rexc = new RuntimeException(exc);
			rexc.setStackTrace(exc.getStackTrace());
			throw rexc;
		} finally {
			close(con, st, rs);
		}
	}

	@Override
	public void update(Cerca cerca, Long clienteId) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		try {
			con = open();
			sql = sqlUpdateCerca();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setString(1, cerca.getDescricao());
			Point2D ptSup = wkt2geometry(cerca.getPontoSuperior()).getJavaPoint();
			Point2D ptInf = wkt2geometry(cerca.getPontoInferior()).getJavaPoint();
			st.setString(2, NumberUtils.double2string(ptSup.getX()));
			st.setString(3, NumberUtils.double2string(ptSup.getY()));
			st.setString(4, NumberUtils.double2string(ptInf.getX()));
			st.setString(5, NumberUtils.double2string(ptInf.getY()));
			set(st, 6, cerca.getVelocidadePadrao());
			set(st, 7, cerca.getVelocidadeEvento());
			set(st, 8, cerca.getGeometria(), MDSYS_SDO_GEOMETRY, con);
			st.setLong(9, clienteId);
			st.setString(10, cerca.getIdProjeto());
			st.setLong(11, NumberUtils.parseLong(cerca.getCdRota()));
			st.setLong(12, cerca.getObjectId());
			st.executeUpdate();
		} catch (Exception exc) {
			logger.error(sql, exc);
			RuntimeException rexc = new RuntimeException(exc);
			rexc.setStackTrace(exc.getStackTrace());
			throw rexc;
		} finally {
			close(con, st, null);
		}
	}

	@Override
	public long insert(Cerca cerca, Long clienteId) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInsertCerca();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql, GENERATED_KEYS);
			st.setLong(1, clienteId);
			st.setString(2, cerca.getIdProjeto());
			st.setLong(3, NumberUtils.parseLong(cerca.getCdRota()));
			st.setLong(4, cerca.getObjectId());
			st.setString(5, cerca.getDescricao());
			Point2D ptSup = wkt2geometry(cerca.getPontoSuperior()).getJavaPoint();
			Point2D ptInf = wkt2geometry(cerca.getPontoInferior()).getJavaPoint();
			st.setString(6, NumberUtils.double2string(ptSup.getX()));
			st.setString(7, NumberUtils.double2string(ptSup.getY()));
			st.setString(8, NumberUtils.double2string(ptInf.getX()));
			st.setString(9, NumberUtils.double2string(ptInf.getY()));
			set(st, 10, cerca.getVelocidadePadrao());
			set(st, 11, cerca.getVelocidadeEvento());
			set(st, 12, cerca.getGeometria(), MDSYS_SDO_GEOMETRY, con);
			st.setInt(13, 1);
			st.setInt(14, 3);
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (Exception exc) {
			logger.error(sql, exc);
			RuntimeException rexc = new RuntimeException(exc);
			rexc.setStackTrace(exc.getStackTrace());
			throw rexc;
		} finally {
			close(con, st, rs);
		}
	}

	@Override
	public void embarcaCerca(Long numOt, String idProjeto, String cdRota, String placa, String idDispositivo, Date dataLiberacao) {
		Connection con = null;
		CallableStatement callStmt = null;
		String sql = null;
		try {
			con = open();
			sql = sqlEmbarcaCercas();
			if (printSql) {
				logger.info(sql);
			}
			callStmt = con.prepareCall(sql);
			callStmt.setLong(1, numOt);
			callStmt.setString(2, idProjeto);
			callStmt.setString(3, cdRota);
			callStmt.setString(4, placa);
			callStmt.setString(5, idDispositivo);
			callStmt.setDate(6, new java.sql.Date(dataLiberacao.getTime()));
			callStmt.execute();
		} catch (Exception exc) {
			logger.error(sql, exc);
			RuntimeException rexc = new RuntimeException(exc);
			rexc.setStackTrace(exc.getStackTrace());
			throw rexc;
		} finally {
			close(con, callStmt, null);
		}
	}

}

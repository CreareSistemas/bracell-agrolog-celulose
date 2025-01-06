package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import oracle.jdbc.OracleConnection;
import oracle.spatial.geometry.JGeometry;
import oracle.spatial.util.WKT;
import br.com.crearesistemas.config.Config;

public abstract class JdbcDAO {

	protected final Config config = Config.getInstance();

	protected final boolean printSql = false;

	protected static final String[] GENERATED_KEYS = new String[] { "id" };

	protected static final String MDSYS_SDO_GEOMETRY = "MDSYS.SDO_GEOMETRY";

	private  String dataSourceName;
	private  DataSource dataSource;
	private DataSource dataSourceRepository;

	private  String dataSourceNameBracell;
	private  DataSource dataSourceBracell;
	
	public JdbcDAO() {
		try {
			Context context = new InitialContext();
			this.dataSourceName = config.getDataSourceName();
			this.dataSource = (DataSource) context.lookup("java:/comp/env/" + dataSourceName);
		} catch (Exception exc) {
		}
		
		if (this.dataSourceRepository == null){
			try {
				this.dataSourceRepository = dataSourceDBCPRepository();
			} catch (Exception exc) {
				RuntimeException rexc = new RuntimeException("Erro ao inicializar a classe " + this.getClass().getName(), exc);
				rexc.setStackTrace(exc.getStackTrace());
				throw rexc;
			}
		}
		
		
		try {
			Context context = new InitialContext();
			this.dataSourceNameBracell = config.getDataSourceNameBracell();
			this.dataSourceBracell = (DataSource) context.lookup("java:/comp/env/" + dataSourceNameBracell);
		} catch (Exception exc) {
		}

	}

	
	
	protected Connection open() throws Exception {
		return dataSource.getConnection();
	}
	
	
	protected Connection openBracell() throws Exception {
		return dataSourceBracell.getConnection();
	}
	
	protected Connection openRepository() throws Exception {
		return dataSourceRepository.getConnection();
	}

	
	 private DataSource dataSourceDBCPRepository() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(config.getRepositoryDriverClassName());
		ds.setUsername(config.getRepositoryUserName());
		ds.setPassword(config.getRepositoryPassword());
		ds.setUrl(config.getRepositoryUrl());
		return ds;			
	}

	 

	protected void close(Connection con, Statement st, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception exc) {
			// nada a fazer!
		}
	}

	protected void set(PreparedStatement st, int index, Integer value) throws Exception {
		if (value == null) {
			st.setNull(index, Types.NUMERIC);
		} else {
			st.setInt(index, value);
		}
	}

	protected void set(PreparedStatement st, int index, Double value) throws Exception {
		if (value == null) {
			st.setNull(index, Types.NUMERIC);
		} else {
			st.setDouble(index, value);
		}
	}

	protected void set(PreparedStatement st, int index, String wkt, String type, Connection con) throws Exception {
		if (wkt == null) {
			st.setNull(index, Types.STRUCT, type);
		} else {
			st.setObject(index, wkt2object(wkt, con));
		}
	}

	protected JGeometry wkt2geometry(String wkt) {
		try {
			return new WKT().toJGeometry(wkt.getBytes());
		} catch (Exception exc) {
			RuntimeException rexc = new RuntimeException(exc);
			rexc.setStackTrace(exc.getStackTrace());
			throw rexc;
		}
	}

	protected Object wkt2object(String wkt, Connection con) {
		try {
			JGeometry geom = new WKT().toJGeometry(wkt.getBytes());
			OracleConnection ocon = con.unwrap(OracleConnection.class);
			return JGeometry.store(geom, ocon);
		} catch (Exception exc) {
			RuntimeException rexc = new RuntimeException(exc);
			rexc.setStackTrace(exc.getStackTrace());
			throw rexc;
		}
	}
	
	protected Timestamp getTimestamp(Date data) {
		return (data==null)?null:new java.sql.Timestamp(data.getTime());
	}

}

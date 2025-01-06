package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;
import br.com.crearesistemas.model.agrolog.OrdemTransporte;
import br.com.crearesistemas.util.DateUtils;

@Repository
public class PgOrdemTransporteJdbcDAO extends JdbcDAO implements PgOrdemTransporteDAO {

	private static final Logger logger = Logger.getLogger(PgOrdemTransporteJdbcDAO.class);
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	Boolean isPg = config.getRepositoryEnable();
	
	
	@Override
	public Long selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(Date dataInicio, Date dataFim) {
		Long quantidade = 0l;
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				sql = sqlQtdDataProgramadaChegada(dataInicio, dataFim);
				
				if (printSql) {
					logger.info(sql);
				}
				st = con.prepareStatement(sql);
				
				rs = st.executeQuery();
								
				if (rs.next()) {
					quantidade = Long.valueOf(rs.getInt("qtd"));
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}

		return quantidade;
	}


	@Override
	public long selecionaQuantidadePorDataEstimadaChegada(Date dataInicio, Date dataFim, String idLocalDeDescarga) {
		Long quantidade = 0l;
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				sql = sqlQtdDataEstimadaChegada(dataInicio, dataFim);
				
				if (printSql) {
					logger.info(sql);
				}
				st = con.prepareStatement(sql);
				rs = st.executeQuery();
								
				if (rs.next()) {
					quantidade = rs.getLong("qtd");
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}

		return quantidade;
	}

	
	private String sqlQtdDataProgramadaChegada(Date dataInicio, Date dataFim) {
		StringBuilder sql = new StringBuilder();
		String dataIni = DateUtils.formatDate(dataInicio, "yyyy-MM-dd HH:mm:ss");
		String dataFin = DateUtils.formatDate(dataFim, "yyyy-MM-dd HH:mm:ss");
		
		sql.append("select count(*) as qtd from orders ot \r\n" + 
				"where ot.order_status_id not in (3, 5) and ot.planned_date_arrival between to_timestamp('"+dataIni+"', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('"+dataFin+"', 'yyyy-mm-dd hh24:mi:ss') \r\n" +
				"and ot.locale_load_id is not null");
		return sql.toString();
	}
	
	private String sqlQtdDataEstimadaChegada(Date dataInicio, Date dataFim) {
		StringBuilder sql = new StringBuilder();
		
		String dataIni = DateUtils.formatDate(dataInicio, "yyyy-MM-dd HH:mm:ss");
		String dataFin = DateUtils.formatDate(dataFim, "yyyy-MM-dd HH:mm:ss");

		
		sql.append("select count(*) qtd from orders ot \r\n" + 
				"where ot.order_status_id not in (3, 5) and ot.estimated_date_arrival between to_timestamp('"+dataIni+"', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('"+dataFin+"', 'yyyy-mm-dd hh24:mi:ss') \r\n" +
				"and ot.date_received is null \r\n" +
				"and ot.locale_load_id is not null");
		return sql.toString();
	}
	
	private String sqlQtdDataRecebimento(Date dataInicio, Date dataFim) {
		StringBuilder sql = new StringBuilder();
		
		String dataIni = DateUtils.formatDate(dataInicio, "yyyy-MM-dd HH:mm:ss");
		String dataFin = DateUtils.formatDate(dataFim, "yyyy-MM-dd HH:mm:ss");

		sql.append("select count(*) qtd from orders ot \r\n" + 
				"where ot.order_status_id not in (3, 5) and ot.date_received between to_timestamp('"+dataIni+"', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('"+dataFin+"', 'yyyy-mm-dd hh24:mi:ss') \r\n" +
				"and ot.locale_load_id is not null"
		);		
		return sql.toString();
	}


	
	
	@Override
	public long selecionaQuantidadePorDataRecebimento(Date dataInicio, Date dataFim, String idLocalDeDescarga) {
		Long quantidade = 0l;
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				sql = sqlQtdDataRecebimento(dataInicio,dataFim);
				
				if (printSql) {
					logger.info(sql);
				}
				st = con.prepareStatement(sql);
				rs = st.executeQuery();
								
				if (rs.next()) {
					quantidade = rs.getLong("qtd");
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}

		return quantidade;
	}


	@Override
	public List<OrdemTransporte> selecionaPorDataPrevistaChegadaOUDataRecebimento(Date dataInicio, Date dataFim) {
		List<OrdemTransporte> result = new ArrayList<OrdemTransporte>();
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			if (isPg) {
				con = openRepository();
				sql = sqlDataPrevistaOuDataReceb(dataInicio, dataFim);
				
				if (printSql) {
					logger.info(sql);
				}
				st = con.prepareStatement(sql);
				rs = st.executeQuery();
								
				while (rs.next()) {
					result.add(ordem(rs));
				}	
			}			
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}

		return result;
	}


	private OrdemTransporte ordem(ResultSet rs) throws SQLException {
		OrdemTransporte ordem = new OrdemTransporte();
		ordem.setId(rs.getLong("id"));
		ordem.setIdProjeto(rs.getLong("idprojeto"));
		ordem.setProjeto(rs.getString("projeto"));
		ordem.setIdPrestador(rs.getLong("idprestador"));
		ordem.setPrestador(rs.getString("prestador"));
		ordem.setDataProgramadaPartida(rs.getTimestamp("dataprogramadapartida"));
		ordem.setDataProgramadaChegada(rs.getTimestamp("dataProgramadaChegada"));
		ordem.setDataPrevistaChegada(rs.getTimestamp("dataprevistachegada"));
		ordem.setDataEstimadaChegada(rs.getTimestamp("dataestimadachegada"));
		ordem.setSituacao(SituacaoOrdemTransporte.getEntity(rs.getInt("situacao")));
		ordem.setDataRecebimento(rs.getTimestamp("datarecebimento"));
		ordem.setVolumeApurado(rs.getFloat("volumeapurado"));
		ordem.setVolumePrevisto(rs.getFloat("volumeprevisto"));
		ordem.setDataLiberacao(rs.getTimestamp("dataliberacao"));
		ordem.setDataConclusao(rs.getTimestamp("dataconclusao"));
		ordem.setPesoLiquido(rs.getFloat("pesoliquido"));
		
		return ordem;
	}


	String sqlAcompanhamento = "select ot.id, l.id idProjeto, l.identification projeto, \r\n" + 
				"   o.office_id idPrestador, o.description prestador, ot.planned_date_departure dataProgramadaPartida, \r\n" + 
				"   ot.planned_date_arrival dataProgramadaChegada, ot.forecast_volume volumePrevisto, coalesce(ot.estimated_date_arrival, ot.forecast_date_arrival) dataprevistachegada, \r\n" + 
				"   ot.order_status_id situacao, ot.date_received dataRecebimento, ot.measured_volume volumeApurado, ot.estimated_date_arrival dataestimadachegada, \r\n" + 
				"   ot.date_departure dataLiberacao, ot.date_completion dataConclusao, \r\n" + 
				"   ot.net_weight pesoLiquido\r\n" + 
				"from orders ot \r\n" + 
				"inner join locales l on (l.id = ot.locale_load_id)\r\n" + 
				"inner join assets a on (a.id = ot.asset_id)\r\n" + 
				"left join offices o on (a.office_id = o.id)\r\n";

	private String sqlDataPrevistaOuDataReceb(Date dataInicio, Date dataFim) {
		StringBuilder sql = new StringBuilder();
		
		String dataIni = DateUtils.formatDate(dataInicio, "yyyy-MM-dd HH:mm:ss");
		String dataFin = DateUtils.formatDate(dataFim, "yyyy-MM-dd HH:mm:ss");
		
		sql.append( sqlAcompanhamento +				
				"where ot.order_status_id not in (3, 5) and (" +
				"	ot.date_received between to_timestamp('"+dataIni+"', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('"+dataFin+"', 'yyyy-mm-dd hh24:mi:ss')" +
				"	or ot.estimated_date_arrival between to_timestamp('"+dataIni+"', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('"+dataFin+"', 'yyyy-mm-dd hh24:mi:ss')" +
				"	or ot.planned_date_arrival between to_timestamp('"+dataIni+"', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('"+dataFin+"', 'yyyy-mm-dd hh24:mi:ss'))");		
		return sql.toString();
	}
	
	
}

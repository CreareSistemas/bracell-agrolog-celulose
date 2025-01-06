package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.model.Infracao;
import br.com.crearesistemas.model.Infracao.EventoInfracao;
import br.com.crearesistemas.util.StringUtils;

/**
 * @author cneves, 28-Set-2015
 */
@Repository
public class InfracaoJdbcDAO extends JdbcDAO implements InfracaoDAO {

	private static final Logger logger = Logger.getLogger(InfracaoJdbcDAO.class);

	private String sqlBuscarInfracoesVelocidade(Infracao.EventoInfracao evento, Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes) {
		boolean fim = evento == Infracao.EventoInfracao.FIM;
		//
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT q.*, rownum FROM (\n");
		sb.append("  SELECT\n");
		sb.append("    i.id                                  AS id,\n");
		sb.append("    'CREARE_DISPOSITIVO_' || i.vehicle_id AS id_dispositivo,\n");
		sb.append("    i.VALOR_LIMITE_UTILIZADO              AS limite,\n");
		sb.append("    ri.velocidade                         AS excesso,\n");
		sb.append(fim ? "    i.tempo_excesso" : "    (sysdate - i.data_hora)*24*60*60").append("      AS tempo_excesso,\n");
		sb.append("    ri.data_hora                          AS data,\n");
		sb.append("    DEGREEMINUTE_TO_DEGREE(ri.latitude)   AS latitude,\n");
		sb.append("    DEGREEMINUTE_TO_DEGREE(ri.longitude)  AS longitude,\n");
		sb.append("    1                                     AS ignicao,\n");
		sb.append("    ri.velocidade                         AS velocidade,\n");
		sb.append("    ri.rotacao                            AS rotacao,\n");
		sb.append("    ri.hodometro                          AS hodometro,\n");
		sb.append("    0                                     AS horimetro\n");
		sb.append("FROM\n");
		sb.append(fim ? "    infraction i\n" : "    partial_infraction i\n");
		sb.append("    LEFT OUTER JOIN registro_gps ri ON (ri.id = i.registro_gps_id)\n");
		sb.append("    WHERE\n");
		sb.append("      i.codigo = 4098 AND i.vehicle_id IN (\n");
		sb.append("        SELECT v.vehicle_id FROM customer_child cc INNER JOIN vehicles v\n");
		sb.append("        ON (v.customer_child_id = cc.customer_child_id)\n");
		sb.append("        WHERE cc.customer_id IN (").append(StringUtils.join(clientes)).append(")\n");
		sb.append("      ) AND\n");
		sb.append("      i.data_hora > sysdate - ").append(idadeEmHoras).append("/24\n");
		sb.append(") q WHERE rownum <= ").append(quantidadeLinhas);
		return sb.toString();
	}

	@Override
	public List<Infracao> buscarInfracoesVelocidadeInicio(Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes) {
		return buscarInfracoesVelocidade(Infracao.EventoInfracao.INICIO, mId, idadeEmHoras, quantidadeLinhas, clientes);
	}

	@Override
	public List<Infracao> buscarInfracoesVelocidadeFim(Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes) {
		return buscarInfracoesVelocidade(Infracao.EventoInfracao.FIM, mId, idadeEmHoras, quantidadeLinhas, clientes);
	}

	private List<Infracao> buscarInfracoesVelocidade(EventoInfracao evento, Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlBuscarInfracoesVelocidade(evento, mId, idadeEmHoras, quantidadeLinhas, clientes);
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			List<Infracao> infracoes = new ArrayList<Infracao>();
			while (rs.next()) {
				infracoes.add(infracao(rs, evento));
			}
			return infracoes;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	private Infracao infracao(ResultSet rs, EventoInfracao evento) throws Exception {
		Infracao infracao = new Infracao();
		{
			infracao.setIntegracao(rs.getLong("id"));
			infracao.setIdDispositivo(rs.getLong("id_dispositivo"));
			infracao.setLimite(rs.getFloat("limite"));
			infracao.setExcesso(rs.getFloat("excesso"));
			infracao.setTempoExcesso(rs.getFloat("tempo_excesso"));
			infracao.setEvento(evento);
		}
		infracao.setRastreamento(new Infracao.Rastreamento());
		{
			infracao.getRastreamento().setData(rs.getDate("data"));
			infracao.getRastreamento().setLatitude(rs.getFloat("latitude"));
			infracao.getRastreamento().setLongitude(rs.getFloat("longitude"));
			infracao.getRastreamento().setIgnicao(rs.getInt("ignicao"));
			infracao.getRastreamento().setVelocidade(rs.getInt("velocidade"));
			infracao.getRastreamento().setRotacao(rs.getInt("rotacao"));
			infracao.getRastreamento().setHodometro(rs.getInt("hodometro"));
			infracao.getRastreamento().setHorimetro(rs.getInt("horimetro"));
		}
		return infracao;
	}

}

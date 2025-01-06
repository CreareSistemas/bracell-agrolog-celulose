package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.model.Apontamento;
import br.com.crearesistemas.util.StringUtils;

@Repository("apontamentoJdbcDAO")
public class ApontamentoJdbcDAO extends JdbcDAO implements ApontamentoDAO {

	private static final Logger logger = Logger.getLogger(ApontamentoJdbcDAO.class);


	private String sqlBuscarApontamentos(long mId, int codigo, List<String> identificadores, float janelaHoras, int janelaMaxRows) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM\n");
		sql.append("  (SELECT ev.*,\n");
		sql.append("          v.vehicle_plates,\n");
		sql.append("          TO_CHAR(ev.data_hora,'DD/MM/YYYY HH24:MI:SS') AS datahora\n");
		sql.append("  FROM eventos ev\n");
		sql.append("  INNER JOIN vehicles v ON (v.vehicle_id = ev.vehicle_id)\n");
		sql.append("  WHERE v.vehicle_plates  IN (").append(StringUtils.joinString(identificadores)).append(")\n");
		sql.append("  AND   v.vehicle_status = 1\n");
		sql.append("  AND   ev.id        > ").append(mId).append("\n");
		
		if (janelaHoras > 0) {
			sql.append("  AND ev.data_hora > sysdate - ").append(janelaHoras).append("/24\n");
		}
		
		if (codigo > 0) {
			sql.append("  AND ev.codigo = ").append(codigo).append("\n");
		}
		
		sql.append(") WHERE rownum <= ").append(janelaMaxRows).append("  ORDER BY ID ASC");
		return sql.toString();
	}


	@Override
	public List<Apontamento> buscarApontamentos(long mId, List<String> identificadores) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlBuscarApontamentos(mId, config.getCodigoApontamento(), identificadores, config.getJanelaHorasApontamento(), config.getJanelaMaxRowsApontamento());
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			List<Apontamento> apontamentos = new ArrayList<Apontamento>();
			while (rs.next()) {
				apontamentos.add(apontamento(rs));
			}
			return apontamentos;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	/**
	 * Converte uma inst\u00E2ncia de um <code>ResultSet</code> em uma inst\u00E2ncia¿½ncia de um objeto <code>Apontamento</code>.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> com os dados selecionados do base.
	 * @return Um objeto do tipo <code>Apontamento</code> com os dados obtidos do <code>ResultSet</code>.
	 * @throws Exception
	 *             Este m\u00E9todo n\u00E3o trara suas exce\u00E7\u00F5es: em caso de erro, uma exce\u00E7\u00E3o pe lan\u00E7ada.
	 */
	private Apontamento apontamento(ResultSet rs) throws Exception {
		Apontamento apontamento = new Apontamento();
		apontamento.setMId(rs.getLong("id"));
		apontamento.setLatitude(rs.getString("lat_inicial"));
		apontamento.setLongitude(rs.getString("long_inicial"));
		apontamento.setVehicle_id(rs.getLong("vehicle_id"));
		apontamento.setDispositivoId(config.getDispositivoId(apontamento.getVehicle_id()));
		apontamento.setDataHora(rs.getString("datahora"));
		apontamento.setEstadoOperacional(config.getEstadoOperacionalByCodEvento(rs.getInt("cod_evento")));		
		//EstoqueHortoFlorestal estoque = config.getEstoqueHortoFlorestal(rs.getString("ent_dados"));
		//apontamento.setProjeto(estoque.projeto);
		//apontamento.setTalhao(estoque.talhao);
		//apontamento.setPilha(estoque.pilha);
		//apontamento.setPlaca(estoque.placa);
		return apontamento;
	}

}

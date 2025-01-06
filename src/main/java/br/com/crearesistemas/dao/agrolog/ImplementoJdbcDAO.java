package br.com.crearesistemas.dao.agrolog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.dao.JdbcDAO;
import br.com.crearesistemas.model.agrolog.Implemento;


@Repository
public class ImplementoJdbcDAO extends JdbcDAO implements ImplementoDAO {

	private static final Logger logger = Logger.getLogger(ImplementoJdbcDAO.class);


	@Override
	public Implemento selecionaPorIdEquipamento(Long equipamentoId) {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlBuscarImplementoPorIdEquipamento();
			if (printSql) {
				logger.info(sql);
			}
			ps = con.prepareStatement(sql);
			ps.setLong(1, equipamentoId);
			rs = ps.executeQuery();
			
			Implemento implemento = null;
			if (rs.next()) {
				implemento = implemento(rs);
			}
			return implemento;
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
	private Implemento implemento(ResultSet rs) throws Exception {
		Implemento implemento = new Implemento();
		implemento.setId(rs.getLong("id"));
		implemento.setNumVersao(rs.getInt("numVersao"));
		return implemento;
	}


	private String sqlBuscarImplementoPorIdEquipamento() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id, i.numversao, i.abreviacao, i.descricao, \n");
		sql.append("i.identificacao, i.integracao, i.tipoimplemento, i.entidadeproprietaria_id, \n");
		sql.append("i.gruamaisproxima_id, i.ultimorastreamento_id, i.datahistorico, i.apontamentoanterior0_id, \n");
		sql.append("i.apontamentoanterior1_id, i.apontamentoanterior2_id, i.apontamentoanterior3_id, \n");
		sql.append("i.apontamentoatual0_id, i.apontamentoatual1_id, i.apontamentoatual2_id, i.apontamentoatual3_id, \n");
		sql.append("i.rastreamentoatual_id, i.rastreamentoanterior_id, i.local_id, i.localanterior_id, \n");
		sql.append("i.ordemtransporteatual_id, i.ordemtransporteanterior_id, i.situacaoimplemento, i.datalocal, \n");
		sql.append("i.rastreamentoultimo_id, i.rastreamentokeepalive_id  \n");
		sql.append("  FROM bracell.implemento i \n");
		sql.append("  inner join bracell.equipamento e on (e.implemento_id = i.id) \n");
		sql.append("  WHERE e.id = ?");
		return sql.toString();
	}
	
}

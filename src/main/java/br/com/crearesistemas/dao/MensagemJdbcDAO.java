package br.com.crearesistemas.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.model.Mensagem;
import br.com.crearesistemas.util.StringUtils;

@Repository
public class MensagemJdbcDAO extends JdbcDAO implements MensagemDAO {

	private static final Logger logger = Logger.getLogger(MensagemJdbcDAO.class);

	private String sqlBuscarMensagems(Long mId, List<Long> clientes, float janelaHoras, int janelaMaxRows) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM (\n");
		sql.append("  SELECT r.* FROM register_msgs r\n");
		sql.append("  WHERE\n");
		sql.append("    r.id_pergunta IS NOT NULL AND\n");
		sql.append("    r.status = ").append(Mensagem.STATUS_PENDENTE_DE_ENVIO).append(" AND\n");
		sql.append("    r.customer_child_id IN (").append(StringUtils.join(clientes)).append(") AND\n");
		sql.append("    r.id > ").append(mId).append("\n");
		if (janelaHoras > 0) {
			sql.append("    AND r.REGISTER_DATE > sysdate - ").append(janelaHoras).append("/24\n");
		}
		sql.append(") WHERE rownum < ").append(janelaMaxRows).append(" ORDER BY id ASC");
		return sql.toString();
	}

	private String sqlSalvarMensagem() {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO register_msgs (\n");
		sql.append("	vehicle_id,\n");
		sql.append("	customer_id,\n");
		sql.append("	customer_child_id,\n");
		sql.append("	msg, status, id_grp_msg, id_msg_predef, id_message, register_date, msg_seq, id_pergunta\n");
		sql.append(") VALUES (\n");
		sql.append("	?,\n");
		sql.append("	(SELECT cc.customer_id FROM vehicles v, customer_child cc WHERE v.customer_child_id = cc.customer_child_id AND v.vehicle_id = ?),\n");
		sql.append("	(SELECT cc.customer_child_id FROM vehicles v, customer_child cc WHERE v.customer_child_id = cc.customer_child_id AND v.vehicle_id = ?),\n");
		sql.append("	?, ?, ?, ?, ?, ?, ?, ?\n");
		sql.append(")");
		return sql.toString();
	}

	private String sqlAtualizarStatusMensagem() {
		return "UPDATE register_msgs r SET r.update_date = ?, r.status = ? WHERE r.id = ?";
	}

	private String sqlSendMessage(long vehicleId) {
		StringBuilder sql = new StringBuilder();
		sql.append("BEGIN pkg_message_service.send_message(").append(vehicleId).append("); END;");
		return sql.toString();
	}

	@Override
	public List<Mensagem> buscarMensagens(Long mId, List<Long> clientes) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			Config config = Config.getInstance();
			sql = sqlBuscarMensagems(mId, clientes, config.getJanelaHoras(), config.getJanelaMaxRows());
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			List<Mensagem> mensagems = new ArrayList<Mensagem>();
			while (rs.next()) {
				mensagems.add(mensagem(rs));
			}
			return mensagems;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	public void salvarMensagem(Mensagem mensagem) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarMensagem();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql, GENERATED_KEYS);
			st.setLong(1, mensagem.getVehicleId());
			st.setLong(2, mensagem.getVehicleId());
			st.setLong(3, mensagem.getVehicleId());
			st.setString(4, mensagem.getMsg());
			st.setInt(5, mensagem.getStatus());
			set(st, 6, mensagem.getGrpRespPredefinidas());
			set(st, 7, mensagem.getIdMensagemPredefinida());
			st.setLong(8, mensagem.getIdMensagem());
			st.setTimestamp(9, new Timestamp(mensagem.getRegisterDate().getTime()));
			set(st, 10, mensagem.getSequencial());
			set(st, 11, mensagem.getSequencialPergunta());
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			rs.next();
			mensagem.setId(rs.getInt(1));
			sendMessage(con, mensagem.getVehicleId());
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	private void sendMessage(Connection con, long vehicleId) throws Exception {
		String sql = sqlSendMessage(vehicleId);
		if (printSql) {
			logger.info(sql);
		}
		CallableStatement callStmt = con.prepareCall(sql);
		callStmt.execute();
	}

	@Override
	public void atualizarStatusMensagem(Long id, int status) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		try {
			con = open();
			sql = sqlAtualizarStatusMensagem();
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setTimestamp(1, new Timestamp(new Date().getTime()));
			st.setInt(2, status);
			st.setLong(3, id);
			st.executeUpdate();
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, null);
		}
	}

	/**
	 * Converte uma inst�ncia de um <code>ResultSet</code> em uma inst�ncia de um objeto <code>Mensagem</code>.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> com os dados selecionados do base.
	 * @return Um objeto do tipo <code>Mensagem</code> com os dados obtidos do <code>ResultSet</code>.
	 * @throws Exception
	 *             Este m�todo n�o trata suas exce��es: em caso de erro, uma exce��o ser� lan�ada.
	 */
	private Mensagem mensagem(ResultSet rs) throws Exception {
		Mensagem mensagem = new Mensagem();
		mensagem.setId(rs.getLong("id"));
		mensagem.setRegisterDate(rs.getTimestamp("register_date"));
		mensagem.setUpdateDate(rs.getTimestamp("update_date"));
		mensagem.setVehicleId(rs.getLong("vehicle_id"));
		mensagem.setCustomerId(rs.getLong("customer_id"));
		mensagem.setCustomerChildId(rs.getLong("customer_child_id"));
		mensagem.setPrefix(rs.getString("prefix"));
		mensagem.setMsg(rs.getString("msg"));
		mensagem.setStatus(rs.getInt("status"));
		mensagem.setSequencial(rs.getInt("msg_seq"));
		mensagem.setSequencialPergunta(rs.getInt("id_pergunta"));
		return mensagem;
	}
}

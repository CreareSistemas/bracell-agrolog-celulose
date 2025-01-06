package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.model.Equipamento;

@Repository
public class EquipamentoJdbcDAO extends JdbcDAO implements EquipamentoDAO {
	private static final Logger logger = Logger.getLogger(EquipamentoJdbcDAO.class);

	private String sqlBuscarEquipamentos(long mId, long customerId, int janelaMaxRows) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		sb.append("FROM\n");
		sb.append("  (SELECT v.vehicle_id,\n");
		sb.append("    vi.imei,\n");
		sb.append("    v.vehicle_plates,\n");
		sb.append("    v.vehicle_prefix,\n");
		sb.append("    v.vehicle_hourmeter\n");
		sb.append("  FROM vehicles v,\n");
		sb.append("    customer_child cc,\n");
		sb.append("    vehicle_imei vi\n");
		sb.append("  WHERE v.vehicle_id       = vi.vehicle_id\n");
		sb.append("  AND v.customer_child_id  = cc.customer_child_id\n");
		sb.append("  AND cc.customer_child_id = ").append(customerId).append("\n");
		sb.append("  AND (vi.imei LIKE 'VIRLOC10_%' OR vi.imei LIKE 'RADIO_%')\n");
		sb.append("  AND v.vehicle_id  > ").append(mId).append("\n");
		sb.append("  ORDER BY v.vehicle_id ASC\n");
		sb.append("  )\n");
		sb.append("WHERE rownum < ").append(janelaMaxRows);
		return sb.toString();
	}

	@Override
	public List<Equipamento> buscarEquipamentos(long mId, long customerId) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			Config config = Config.getInstance();
			sql = sqlBuscarEquipamentos(mId, customerId, config.getJanelaMaxRows());
			rs = st.executeQuery(sql);
			List<Equipamento> equipamentos = new ArrayList<Equipamento>();
			while (rs.next()) {
				equipamentos.add(equipamento(rs));
			}
			return equipamentos;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	private Equipamento equipamento(ResultSet rs) throws Exception {
		Equipamento equipamento = new Equipamento();
		equipamento.setDevice_id("");
		equipamento.setmId(rs.getLong("vehicle_id"));
		equipamento.setVehicle_id(rs.getLong("vehicle_id"));
		equipamento.setVehicle_plates(rs.getString("vehicle_plates"));
		equipamento.setVehicle_prefix(rs.getString("vehicle_prefix"));
		equipamento.setDevice_id(rs.getString("imei"));
		return equipamento;
	}

}

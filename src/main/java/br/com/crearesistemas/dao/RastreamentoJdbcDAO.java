package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.model.Discretas;
import br.com.crearesistemas.model.Rastreamento;
import br.com.crearesistemas.util.NumberUtils;
import br.com.crearesistemas.util.StringUtils;

@Repository("rastreamentoJdbcDAO")
public class RastreamentoJdbcDAO extends JdbcDAO implements RastreamentoDAO {

	private static final Logger logger = Logger.getLogger(RastreamentoJdbcDAO.class);

	private static final int POSICAO_IGNICAO = 3;

//	private String sqlBuscarRastreamentos(long mId, List<Long> clientes, float janelaHoras, int janelaMaxRows) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT * FROM\n");
//		sql.append("  (SELECT avl.*,\n");
//		sql.append("    vi.imei,\n");
//		sql.append("    v.vehicle_plates,\n");
//		sql.append("    v.vehicle_prefix,\n");
//		sql.append("    v.vehicle_hourmeter,\n");
//		sql.append("    TO_CHAR(avl.date_hour,'DD/MM/YYYY HH24:MI:SS') AS datahora\n");
//		sql.append("  FROM avl_locations avl,\n");
//		sql.append("    vehicles v,\n");
//		//sql.append("    customer_child cc,\n");
//		sql.append("    vehicle_imei vi\n");
//		sql.append("  WHERE avl.vehicle_id    = v.vehicle_id\n");
//		sql.append("  AND v.vehicle_id        = vi.vehicle_id\n");
//		sql.append("  AND v.customer_child_id  IN (").append(StringUtils.join(clientes)).append(")\n");
//		//sql.append("  AND v.customer_child_id = cc.customer_child_id\n");
//		//sql.append("  AND cc.customer_child_id      IN (").append(StringUtils.join(clientes)).append(")\n");
//		sql.append("  AND vi.imei LIKE 'VIRLOC10_%'\n");
//		sql.append("  AND avl.id        > ").append(mId).append("\n");
//		if (janelaHoras > 0) {
//			sql.append("  AND avl.date_hour > sysdate - ").append(janelaHoras).append("/24\n");
//		}
//		sql.append(") WHERE rownum <= ").append(janelaMaxRows).append("  ORDER BY date_hour ASC");
//		return sql.toString();
//	}

	private String sqlBuscarRastreamentos(long mId, List<Long> clientes, float janelaHoras, int janelaMaxRows) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM (\n");
		sql.append(" SELECT  * FROM (\n");
		sql.append("  SELECT 2 as origem, avl.id, avl.date_hour, avl.driver_id, avl.latitude, avl.longitude, avl.discretas, avl.vel, avl.rot, avl.hodometro,\n");
		sql.append("    vi.imei,\n");
		sql.append("    v.vehicle_id,\n");
		sql.append("    v.vehicle_plates,\n");
		sql.append("    v.vehicle_prefix,\n");
		sql.append("    v.vehicle_hourmeter,\n");
		sql.append("    TO_CHAR(avl.date_hour,'DD/MM/YYYY HH24:MI:SS') AS datahora\n");
		sql.append("  FROM current_avl_locations avl,\n");
		sql.append("    vehicles v,\n");
		sql.append("    vehicle_imei vi\n");
		sql.append("  WHERE avl.vehicle_id    = v.vehicle_id\n");
		sql.append("  AND v.vehicle_id        = vi.vehicle_id\n");
		sql.append("  AND v.customer_child_id  IN (").append(StringUtils.join(clientes)).append(")\n");
		sql.append("  AND vi.imei LIKE 'VIRLOC10_%'\n");		
		if (janelaHoras > 0) {
			sql.append("  AND avl.date_hour > sysdate - ").append(janelaHoras).append("/24\n");
		}
		sql.append("  ORDER BY date_hour DESC\n");
		sql.append(") WHERE rownum <= ").append(janelaMaxRows);		
		sql.append("  UNION\n");
		sql.append(" SELECT  * FROM (\n");
		sql.append("  SELECT 1 as origem, avl.id, avl.date_hour, avl.driver_id, avl.latitude, avl.longitude, avl.discretas, avl.vel, avl.rot, avl.hodometro,\n");
		sql.append("    vi.imei,\n");
		sql.append("    v.vehicle_id,\n");
		sql.append("    v.vehicle_plates,\n");
		sql.append("    v.vehicle_prefix,\n");
		sql.append("    v.vehicle_hourmeter,\n");
		sql.append("    TO_CHAR(avl.date_hour,'DD/MM/YYYY HH24:MI:SS') AS datahora\n");
		sql.append("  FROM avl_locations avl,\n");
		sql.append("    vehicles v,\n");
		sql.append("    vehicle_imei vi\n");
		sql.append("  WHERE avl.vehicle_id    = v.vehicle_id\n");
		sql.append("  AND v.vehicle_id        = vi.vehicle_id\n");
		sql.append("  AND v.customer_child_id  IN (").append(StringUtils.join(clientes)).append(")\n");
		sql.append("  AND vi.imei LIKE 'VIRLOC10_%'\n");
		sql.append("  AND avl.id        > ").append(mId).append("\n");
		if (janelaHoras > 0) {
			sql.append("  AND avl.date_hour > sysdate - ").append(janelaHoras).append("/24\n");
		}
		sql.append("  ORDER BY id ASC\n");
		sql.append(" ) WHERE rownum <= ").append(janelaMaxRows);
		sql.append(") WHERE rownum <= ").append(janelaMaxRows).append("  ORDER BY origem ASC, id ASC");
		
		return sql.toString();
	}

	@Override
	public List<Rastreamento> buscarRastreamentos(long mId, List<Long> clientes) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlBuscarRastreamentos(mId, clientes, config.getJanelaHoras(), config.getJanelaMaxRows());
			if (printSql) {
				logger.info(sql);
			}
			rs = st.executeQuery(sql);
			List<Rastreamento> rastreamentos = new ArrayList<Rastreamento>();
			while (rs.next()) {
				rastreamentos.add(rastreamento(rs));
			}
			return rastreamentos;
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	/**
	 * Converte uma inst�ncia de um <code>ResultSet</code> em uma inst�ncia de um objeto <code>Rastreamento</code>.
	 * 
	 * @param rs
	 *            <code>ResultSet</code> com os dados selecionados do base.
	 * @return Um objeto do tipo <code>Rastreamento</code> com os dados obtidos do <code>ResultSet</code>.
	 * @throws Exception
	 *             Este m�todo n�o trata suas exce��es: em caso de erro, uma exce��o ser� lan�ada.
	 */
	private Rastreamento rastreamento(ResultSet rs) throws Exception {
		Rastreamento rastreamento = new Rastreamento();
		rastreamento.setMId(rs.getLong("id"));
		rastreamento.setLatitude(rs.getString("latitude"));
		rastreamento.setLongitude(rs.getString("longitude"));
		rastreamento.setVehicle_id(rs.getLong("vehicle_id"));
		rastreamento.setDispositivoId(config.getDispositivoId(rastreamento.getVehicle_id()));
		rastreamento.setDatehour(rs.getString("datahora"));
		rastreamento.setDriverId(rs.getLong("driver_id"));
		rastreamento.setSpeed(rs.getFloat("vel"));
		rastreamento.setRpm(rs.getInt("rot"));
		rastreamento.setHodometer(rs.getInt("hodometro"));
		rastreamento.setImei(rs.getString("imei"));
		rastreamento.setVehicle_plates(rs.getString("vehicle_plates"));
		rastreamento.setVehicle_prefix(rs.getString("vehicle_prefix"));
		rastreamento.setHorimetro(horimetro(rs.getString("vehicle_hourmeter")));
		rastreamento.setIgnicao(ignicao(rs.getString("discretas")));
		rastreamento.setDiscretas(discretas(rs));
		rastreamento.setOrigem(rs.getInt("origem"));
		return rastreamento;
	}

	private int ignicao(String discretas) {
		if (discretas == null) {
			return 0;
		}
		if (discretas.length() <= POSICAO_IGNICAO) {
			return 0;
		}
		char ignicao = discretas.charAt(POSICAO_IGNICAO);
		return ignicao == '0' ? 0 : 1;
	}

	private long horimetro(String horimetro) {
		try {
			return NumberUtils.parseLong(horimetro);
		} catch (NumberFormatException exc) {
			return 0;
		}
	}

	private List<Discretas> discretas(ResultSet rs) {
		List<Discretas> discretas = new ArrayList<Discretas>();
		discretas.add(discreta(1l));
		discretas.add(discreta(2l));
		return discretas;
	}

	private Discretas discreta(long id) {
		Discretas discretas = new Discretas();
		discretas.setId(id);
		return discretas;
	}

}

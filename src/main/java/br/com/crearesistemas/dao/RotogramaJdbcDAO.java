package br.com.crearesistemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.iam.rotograma.cadastra.CadastraRotograma;
import br.com.crearesistemas.model.Coordenada;
import br.com.crearesistemas.model.Geometria;
import br.com.crearesistemas.model.Rota;
import br.com.crearesistemas.util.StringUtils;

@Repository
public class RotogramaJdbcDAO extends JdbcDAO implements RotogramaDAO {

	private static final Logger logger = Logger.getLogger(RotogramaJdbcDAO.class);

	private static final int STATUS_CERCA_ATIVA = 1;
	private static final int TIPO_CERCA_EMBARCADA = 3;

	private static final int TWO_DIMENSIONAL_POLYGON = 2003;

	// private String sql() {
	// StringBuilder sql = new StringBuilder();
	// return sql.toString();
	// }

	private String sqlSalvarRota(Rota rota) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO fences (\n");
		sql.append("	data_hora, customer_child_id, status, description,\n");
		sql.append("	latitude_sup, longitude_sup, latitude_inf, longitude_inf,\n");
		sql.append("	liv, liv_evto,\n");
		sql.append("	polygon,\n");
		sql.append("	fences_type_id\n");
		sql.append(") VALUES (\n");
		sql.append("	?, ?, ?, ?,\n");
		sql.append("	?, ?, ?, ?,\n");
		sql.append("	?, ?,\n");
		if (rota.getGeometria() != null && rota.getGeometria().getPontos() != null) {
			sql.append("	(SELECT SDO_GEOMETRY('POLYGON((").append(join(rota.getGeometria())).append("))', ").append(TWO_DIMENSIONAL_POLYGON).append(") from dual),\n");
		} else {
			sql.append("	NULL,\n");
		}
		sql.append("	?\n");
		sql.append(")");
		return sql.toString();
	}

	private long salvarRota(long codCliente, Rota rota) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarRota(rota);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql, GENERATED_KEYS);
			st.setTimestamp(1, new Timestamp(rota.getData_registro().getTime()));
			st.setLong(2, codCliente);
			st.setInt(3, STATUS_CERCA_ATIVA);
			st.setString(4, rota.getDescricao());
			setLatitude(st, 5, rota.getCoordenadas().getSuperior());
			setLongitude(st, 6, rota.getCoordenadas().getSuperior());
			setLatitude(st, 7, rota.getCoordenadas().getInferior());
			setLongitude(st, 8, rota.getCoordenadas().getInferior());
			st.setInt(9, rota.getVelocPadrao());
			st.setInt(10, rota.getVelocEvento());
			st.setInt(11, TIPO_CERCA_EMBARCADA);
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (Exception exc) {
			logger.error(sql, exc);
			throw new RuntimeException(exc);
		} finally {
			close(con, st, rs);
		}
	}

	@Override
	public void salvarRotograma(CadastraRotograma rotograma) {
		for (Long codCliente : rotograma.getCliente()) {
			for (Rota rota : rotograma.getRotogramas()) {
				salvarRota(codCliente, rota);
			}
		}
	}

	private void setLatitude(PreparedStatement st, int index, Coordenada coordenada) throws Exception {
		if (coordenada == null) {
			set(st, index, (Double) null);
		} else {
			set(st, index, coordenada.getLatitude());
		}
	}

	private void setLongitude(PreparedStatement st, int index, Coordenada coordenada) throws Exception {
		if (coordenada == null) {
			set(st, index, (Double) null);
		} else {
			set(st, index, coordenada.getLongitude());
		}
	}

	private String join(Geometria geometria) {
		List<String> list = new ArrayList<String>();
		List<Coordenada> pontos = geometria.getPontos();
		for (Coordenada coordenada : pontos) {
			String coordenadas = String.format("%s %s", coordenada.getLatitude(), coordenada.getLongitude());
			list.add(coordenadas);
		}
		return StringUtils.join(list);
	}

}

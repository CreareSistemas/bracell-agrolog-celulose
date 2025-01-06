package br.com.crearesistemas.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.dto.WorkflowEvent;
import br.com.crearesistemas.dao.dto.WorkflowIntegrationStatus;
import br.com.crearesistemas.sgf.wsgetchippers.SgfChipper;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.sgf.wsgetdowntimes.GetDownTimes;
import br.com.crearesistemas.sgf.wsgetlocales.SgfLocale;
import br.com.crearesistemas.sgf.wsgetpiles.SgfPile;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;
import br.com.crearesistemas.sgf.wsgetstands.SgfStand;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;

@Repository
public class DadosJdbcDAO extends JdbcDAO implements DadosDAO {

	private static final Logger logger = Logger.getLogger(DadosJdbcDAO.class);

	@Override
	public Boolean salvarProjeto(SgfProject projeto, Boolean isProd) {
		if (atualizarProjeto(projeto, isProd) != true)  {
			return inserirProjeto(projeto, isProd);
		} else {
			return true;
		}
	}
	
	
	@Override
	public Boolean salvarTalhao(SgfStand talhao, Boolean isProd) {
		if (atualizarTalhao(talhao, isProd) != true)  {
			return inserirTalhao(talhao, isProd);
		} else {
			return true;
		}
	}
	
	@Override
	public Boolean salvarGrua(SgfCrane grua, Boolean isProd) {
		if (atualizarGrua(grua, isProd) != true)  {
			return inserirGrua(grua, isProd);
		} else {
			return true;
		}
	}
	
	@Override
	public Boolean salvarCaminhao(SgfTruck caminhao, Boolean isProd) {
		if (atualizarCaminhao(caminhao, isProd) != true)  {
			return inserirCaminhao(caminhao, isProd);
		} else {
			return true;
		}
	}

	@Override
	public Boolean salvarMesa(SgfChipper chipper, Boolean isProd) {
		if (atualizarMesa(chipper, isProd) != true)  {
			return inserirMesa(chipper, isProd);
		} else {
			return true;
		}
	}

	@Override
	public Boolean salvarLocal(SgfLocale locale, Boolean isProd) {
		if (atualizarLocal(locale, isProd) != true)  {
			return inserirLocal(locale, isProd);
		} else {
			return true;
		}
	}

	@Override
	public Boolean salvarPilhas(SgfPile pile, Boolean isProd) {
		if (atualizarPilha(pile, isProd) != true)  {
			return inserirPilha(pile, isProd);
		} else {
			return true;
		}
	}
	
	@Override
	public Boolean salvarParadas(GetDownTimes parada, 
			Boolean isProd) {
		if (atualizarParada(parada, isProd) != true)  {
			return inserirParada(parada, isProd);
		} else {
			return true;
		}
	}

	
	
	private Boolean atualizarParada(GetDownTimes parada, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarParada(parada, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setString(1, parada.getAbbreviation());
			st.setString(2, parada.getDescription());
			st.setString(3, parada.getStatus());
			st.setLong(4,parada.getDowntimeCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}




	private Boolean atualizarProjeto(SgfProject projeto, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarProjeto(projeto, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setString(1, projeto.getProjectId());
			st.setString(2, projeto.getDescription());
			st.setString(3, projeto.getStatus());
			st.setLong(4, projeto.getProjectCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private Boolean atualizarTalhao(SgfStand talhao, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarTalhao(talhao, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setLong(1, talhao.getProjectCode());
			st.setInt(2, talhao.getDensity());
			st.setString(3, talhao.getDensityClass());
			st.setInt(4, talhao.getTimeAfterCutting());
			st.setString(5, talhao.getStatus());
			st.setString(6, talhao.getStandId());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	

	private boolean atualizarGrua(SgfCrane grua, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarGrua(grua, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setString(1, grua.getDescription());
			st.setString(2, grua.getAbbreviation());
			st.setString(3, grua.getCraneType());
			st.setString(4, grua.getStatus());
			st.setLong(5, grua.getCraneCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private boolean atualizarCaminhao(SgfTruck caminhao, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarCaminhao(caminhao, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setString(1, caminhao.getTruckPlate());
			st.setString(2, caminhao.getAbbreviation());
			st.setString(3, caminhao.getSerialNumber());
			st.setString(4, caminhao.getTagRFID());
			st.setString(5, caminhao.getStatus());
			st.setLong(6, caminhao.getTruckCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private boolean atualizarMesa(SgfChipper mesa, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarMesa(mesa, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setString(1, mesa.getDescription());
			st.setString(2, mesa.getAbbreviation());
			st.setLong(3, mesa.getLocaleCode());
			st.setString(4, mesa.getLineId());
			st.setString(5, mesa.getStatus());
			st.setLong(6, mesa.getChipperCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}


	
	private boolean atualizarLocal(SgfLocale local, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarLocal(local, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			st.setString(1, local.getDescription());
			st.setString(2, local.getAbbreviation());
			st.setString(3, local.getStatus());
			st.setLong(4, local.getLocaleCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private boolean atualizarPilha(SgfPile pilha, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlSalvarPilha(pilha, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);			
			
			st.setString(1, pilha.getDescription());
			st.setLong(2, pilha.getLocaleCode());
			st.setInt(3, pilha.getProductCode());
			st.setString(4, pilha.getFirstMovementDate());
			st.setString(5, pilha.getLineId());
			st.setLong(6, pilha.getDensity());
			st.setString(7, pilha.getDensityClass());
			st.setLong(8, pilha.getTimeAfterCutting());
			st.setString(9, pilha.getStatus());
			st.setLong(10, pilha.getPileCode());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}

	
	private String sqlSalvarParada(GetDownTimes parada, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgfdowntimes SET \n");	
		} else {
			sql.append("UPDATE cmpc_hom.sgfdowntimes SET \n");
		}
		sql.append("	abbreviation = ?, \n");
		sql.append("	description = ? , status = ? \n");
		sql.append("WHERE \n");
		sql.append("	downtimeCode = ? \n");
		return sql.toString();
	}

	
	private String sqlSalvarProjeto(SgfProject projeto, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgfprojects SET \n");	
		} else {
			sql.append("UPDATE cmpc_hom.sgfprojects SET \n");
		}
		
		sql.append("	projectId = ?, \n");
		sql.append("	description = ? , status = ? \n");
		sql.append("WHERE \n");
		sql.append("	projectCode = ? \n");
		return sql.toString();
	}
	
	private String sqlSalvarTalhao(SgfStand talhao, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgfstands SET \n");
		} else {
			sql.append("UPDATE cmpc_hom.sgfstands SET \n");
		}
		sql.append("	projectCode = ?, \n");
		sql.append("	density = ?, densityClass = ?, \n");
		sql.append("	timeAfterCutting = ?, status = ? \n");
		sql.append("WHERE \n");
		sql.append("	standId = ? \n");
		return sql.toString();
	}
	
	
	private String sqlSalvarGrua(SgfCrane grua, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgfcranes SET \n");
		} else {
			sql.append("UPDATE cmpc_hom.sgfcranes SET \n");
		}
		sql.append("	description = ?, \n");
		sql.append("	abbreviation = ?, craneType = ?, \n");
		sql.append("	sstatus = ? \n");
		sql.append("WHERE \n");
		sql.append("	craneCode = ? \n");
		return sql.toString();
	}
	
	
	private String sqlSalvarCaminhao(SgfTruck caminhao, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgftrucks SET \n");
		} else {
			sql.append("UPDATE cmpc_hom.sgftrucks SET \n");
		}
		sql.append("	truckPlate = ?, \n");
		sql.append("	abbreviation = ?, serialNumber = ?, \n");
		sql.append("	tagRFID = ?, status = ? \n");
		sql.append("WHERE \n");
		sql.append("	truckCode = ? \n");
		return sql.toString();
	}
	
	
	private String sqlSalvarMesa(SgfChipper mesa, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgfchippers SET \n");
		} else {
			sql.append("UPDATE cmpc_hom.sgfchippers SET \n");
		}
		sql.append("	description = ?, \n");
		sql.append("	abbreviation = ?, localeCode = ?, \n");
		sql.append("	lineId = ?, status = ? \n");
		sql.append("WHERE \n");
		sql.append("	chipperCode = ? \n");
		return sql.toString();
	}
	
	private String sqlSalvarLocal(SgfLocale local, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgflocales SET \n");
		} else {
			sql.append("UPDATE cmpc_hom.sgflocales SET \n");
		}
		sql.append("	description = ?, \n");
		sql.append("	abbreviation = ?, status = ? \n");
		sql.append("WHERE \n");
		sql.append("	localeCode = ? \n");
		return sql.toString();
	}
	
	
	private String sqlSalvarPilha(SgfPile pilha, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE bracell.sgfpiles SET \n");
		} else {
			sql.append("UPDATE cmpc_hom.sgfpiles SET \n");
		}
		sql.append("	description = ?, localeCode = ?, productCode = ?,  \n");
		sql.append("	firstMovementDate = ?, lineId = ?, density = ?,  \n");
		sql.append("	densityClass = ?, timeAfterCutting = ?, status = ?  \n");
		sql.append("WHERE \n");
		sql.append("	pileCode = ? \n");
		return sql.toString();
	}


	private Boolean inserirParada(GetDownTimes parada, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirParada(parada, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, parada.getDowntimeCode());
			st.setString(2, parada.getAbbreviation());
			st.setString(3, parada.getDescription());
			st.setString(4, parada.getStatus());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	

	private Boolean inserirProjeto(SgfProject projeto, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirProjeto(projeto, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, projeto.getProjectCode());
			st.setString(2, projeto.getProjectId());
			st.setString(3, projeto.getDescription());
			st.setString(4, projeto.getStatus());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}


	private Boolean inserirTalhao(SgfStand stand, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirTalhao(stand, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setString(1, stand.getStandId());
			st.setLong(2, stand.getProjectCode());
			st.setInt(3, stand.getDensity());
			st.setString(4, stand.getDensityClass());
			st.setInt(5, stand.getTimeAfterCutting());
			st.setString(6, stand.getStatus());
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private Boolean inserirGrua(SgfCrane grua, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirGrua(grua, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, grua.getCraneCode());
			st.setString(2, grua.getDescription());
			st.setString(3, grua.getAbbreviation());
			st.setString(4, grua.getCraneType());
			st.setString(5, grua.getStatus());
			
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}


	private Boolean inserirCaminhao(SgfTruck caminhao, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirCaminhao(caminhao, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, caminhao.getTruckCode());
			st.setString(2, caminhao.getTruckPlate());
			st.setString(3, caminhao.getAbbreviation());
			st.setString(4, caminhao.getSerialNumber());
			st.setString(5, caminhao.getTagRFID());
			st.setString(6, caminhao.getStatus());
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	
	private Boolean inserirMesa(SgfChipper mesa, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirMesa(mesa, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			
			st.setLong(1, mesa.getChipperCode());
			st.setString(2, mesa.getDescription());
			st.setString(3, mesa.getAbbreviation());
			st.setLong(4, mesa.getLocaleCode());
			st.setString(5, mesa.getLineId());
			st.setString(6, mesa.getStatus());
			
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private Boolean inserirLocal(SgfLocale local, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirLocal(local, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			
			st.setLong(1, local.getLocaleCode());
			st.setString(2, local.getDescription());
			st.setString(3, local.getAbbreviation());
			st.setString(4, local.getStatus());
			
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	private Boolean inserirPilha(SgfPile pilha, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirPilha(pilha, isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, pilha.getPileCode());
			st.setString(2, pilha.getDescription());
			st.setLong(3, pilha.getLocaleCode());
			st.setInt(4, pilha.getProductCode());
			st.setString(5, pilha.getFirstMovementDate());
			st.setString(6, pilha.getLineId());
			st.setLong(7, pilha.getDensity());
			st.setString(8, pilha.getDensityClass());
			st.setLong(9, pilha.getTimeAfterCutting());
			st.setString(10, pilha.getStatus());
			
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	
	@Override
	public SgfCrane selecionarGruaPatio(String plates, Boolean isProd) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfCrane grua = null;
		try {
			con = open();
			st = con.createStatement();
			Config config = Config.getInstance();
			sql = sqlSelecionarGruaPatio(plates, isProd);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				grua = grua(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return grua;
	}
	
	
	@Override
	public SgfCrane selecionarGruaFlorestal(String plates, Boolean isProd) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfCrane grua = null;
		try {
			con = open();
			st = con.createStatement();
			Config config = Config.getInstance();
			sql = sqlSelecionarGruaFlorestal(plates, isProd);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				grua = grua(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return grua;
	}
	
	@Override
	public SgfLocale selecionarLocal(String locale, Boolean isProd) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfLocale sgfLocale = null;
		try {
			con = open();
			st = con.createStatement();
			Config config = Config.getInstance();
			sql = sqlSelecionarLocale(locale, isProd);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				sgfLocale = locale(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return sgfLocale;
	}


	@Override
	public SgfChipper selecionarMesaCode(Long chipperCode, Boolean isProd) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		SgfChipper sgfChipper = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlSelecionarChipper(chipperCode, isProd);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				sgfChipper = chipper(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return sgfChipper;
	}


	

    @Override
	public void salvarEventWorkflow(Long eventId,  Boolean valid, String message, Boolean isProduction) {
		Boolean atualizar = true;
		Long eventTreatmentValid = 1l; // valid
		Long eventTreatmentInvalid = 2l; // invalid
		WorkflowEvent workflow = selecionarWorkflow(eventId, isProduction);
		
		if (workflow == null) {
			atualizar = false;
			workflow = new 	WorkflowEvent();
			workflow.setCreated(new Date());
		}
		
		if (valid) {
			workflow.setEventTreatment(eventTreatmentValid);
			workflow.setIntegration(WorkflowIntegrationStatus.VALIDO.getCode().longValue());			
		} else {
			workflow.setEventTreatment(eventTreatmentInvalid);
			workflow.setIntegration(WorkflowIntegrationStatus.ERROR.getCode().longValue());			
		}
		workflow.setEventId(eventId);
		workflow.setUpdated(new Date());
		workflow.setMessage(message);
		
		if (atualizar) {			
			atualizarWorkflow(workflow, isProduction);
		} else {
			inserirWorkflow(workflow, isProduction);
		}
	}
	
	@Override
	public void salvarParadas(Long eventId, SetEquipmentDowntime downtime, SetEquipmentDowntimeResponse response, 
			String infoLog, Boolean valid) {
		Boolean atualizar = true;
		Long eventTreatmentValid = 1l; // valid
		Long eventTreatmentInvalid = 2l; // invalid
		WorkflowEvent workflow = selecionarWorkflow(eventId, config.isProduction());
		
		if (workflow == null) {
			atualizar = false;
			workflow = new 	WorkflowEvent();
			workflow.setCreated(new Date());
		}
		
		if (valid) {
			workflow.setEventTreatment(eventTreatmentValid);
			workflow.setIntegration(WorkflowIntegrationStatus.VALIDO.getCode().longValue());			
		} else {
			workflow.setEventTreatment(eventTreatmentInvalid);
			workflow.setIntegration(WorkflowIntegrationStatus.ERROR.getCode().longValue());			
		}
		workflow.setEventId(eventId);
		workflow.setUpdated(new Date());
		
		if (atualizar) {			
			atualizarWorkflow(workflow, config.isProduction());
		} else {
			inserirWorkflow(workflow, config.isProduction());
		}
		
		// registrar logs, comentar quando n�o mais necessario
		inserirDonwtimeLogs(eventId, downtime, response,infoLog, valid);
		
	}

	public WorkflowEvent selecionarWorkflow(Long eventId, Boolean isProd) {
		Connection con = null;
		Statement st = null;
		String sql = null;
		ResultSet rs = null;
		WorkflowEvent workflow = null;
		try {
			con = open();
			st = con.createStatement();
			sql = sqlWorkflow(eventId, isProd);
			rs = st.executeQuery(sql);
			
			if (rs.next()) {
				workflow = workflow(rs);
			}
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return workflow;
	}
	
	private Boolean atualizarWorkflow(WorkflowEvent workflow, Boolean isProd) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlUpdateWorkflow(isProd);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, workflow.getEventTreatment());
			st.setLong(2, workflow.getIntegration());
			if (workflow.getUpdated() != null) {
				st.setDate(3,  new java.sql.Date(workflow.getUpdated().getTime()));	
			} else {
				st.setDate(3,  new java.sql.Date(new Date().getTime()));
			}
			
			Clob clob = con.createClob();
			clob.setString(1, workflow.getMessage());
			st.setClob(4, clob);
			
			
			st.setLong(5, workflow.getEventId());
			
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
	}
	
	
	private Boolean inserirDonwtimeLogs(Long eventId, SetEquipmentDowntime downtime, 
			SetEquipmentDowntimeResponse response, String logInfo, Boolean valid) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirParadas(config.isProduction());
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setString(1, downtime.getEquipmentPlate()); //equipment_plate
			st.setInt(2, downtime.getDowntimeCode()); //downtime_code
			st.setString(3, downtime.getStartDownTime()); //start_downtime
			st.setString(4, downtime.getEndDownTime()); //end_downtime
			
			Date startDate = DateUtils.getDate(downtime.getStartDownTime());
			if (startDate != null) { //start_date
				st.setDate(5,  new java.sql.Date(startDate.getTime()));	
			} else {
				st.setDate(5,  null);
			}
			
			
			Date endDate = DateUtils.getDate(downtime.getEndDownTime());
			if (endDate != null) { //end_date
				st.setDate(6,  new java.sql.Date(endDate.getTime()));	
			} else {
				st.setDate(6,  new java.sql.Date(new Date().getTime()));
			}
			
			st.setString(7, downtime.getOperatorName()); //operator_name
			st.setInt(8, NumberUtils.parseInt(downtime.getLocaleCode())); //locale_code
			st.setDouble(9, downtime.getStartingHorimeterNumber()); //starting_horimeter
			st.setDouble(10, downtime.getEndingHorimeterNumber()); //ending_horimeter
			
			st.setLong(11, eventId); //event_id
			st.setBoolean(12, valid); //error
			
			
			String responseMsg = null;
			
			if (response != null) {
				responseMsg = flatMap(response.getReturnMessage());
				if (response.getReturnId() != null) {
					responseMsg = response.getReturnId() + ": " + responseMsg;
				}
			}
			
			Clob clob = con.createClob(); //response
			clob.setString(1, responseMsg);
			st.setClob(13, clob);
			
			
			Clob clob2 = con.createClob(); //response
			clob2.setString(1, logInfo);
			st.setClob(14, clob2);
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
		
	}
	
	private String flatMap(String[] array) {
		String result = null;
		if (array != null) {
			for (String str: array) {
				if (result == null) {
					result = str;
				} else {
					result = result + ", " + str;
				}
			}
		}
		return result;
	}


	private Boolean inserirWorkflow(WorkflowEvent workflow, Boolean isProduction) {
		Connection con = null;
		PreparedStatement st = null;
		String sql = null;
		ResultSet rs = null;
		try {
			con = open();
			sql = sqlInserirWorkflow(isProduction);
			if (printSql) {
				logger.info(sql);
			}
			st = con.prepareStatement(sql);
			st.setLong(1, workflow.getEventId());
			st.setLong(2, workflow.getEventTreatment());
			st.setLong(3, workflow.getIntegration());			
			if (workflow.getUpdated() != null) {
				st.setDate(4,  new java.sql.Date(workflow.getUpdated().getTime()));	
			} else {
				st.setDate(4,  new java.sql.Date(new Date().getTime()));
			}
			
			if (workflow.getCreated() != null) {
				st.setDate(5,  new java.sql.Date(workflow.getCreated().getTime()));	
			} else {
				st.setDate(5,  new java.sql.Date(new Date().getTime()));
			}
			
			Clob clob = con.createClob();
			clob.setString(1, workflow.getMessage());
			st.setClob(6, clob);
			
			
			return st.executeUpdate() > 0;
		} catch (Exception exc) {
			logger.error(sql, exc);
		} finally {
			close(con, st, rs);
		}
		return false;
		
	}

	

	private String sqlSelecionarChipper(Long chipperCode, Boolean isProd) {
		StringBuilder sb = new StringBuilder();		
		if (isProd) {
			sb.append("select * from bracell.sgfchippers where chippercode = ").append(chipperCode);	
		} else {
			sb.append("select * from cmpc_hom.sgfchippers where chippercode = ").append(chipperCode);
		}		
		return sb.toString();
	}


	@Override
	public SgfPile selecionarPilha(String pile, Boolean isProd) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SgfChipper selecionarMesa(String chipper, Boolean isProd) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private WorkflowEvent workflow(ResultSet rs) throws SQLException {
		WorkflowEvent workflow = new WorkflowEvent();
		workflow.setEventId(rs.getLong("event_id"));
		workflow.setEventTreatment(rs.getLong("workflow_event_treatment_id"));
		workflow.setIntegration(rs.getLong("event_treatment_integration"));
		workflow.setCreated(rs.getDate("created_at"));
		workflow.setUpdated(rs.getDate("updated_at"));
		return workflow;
	}
	
	private SgfLocale locale(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private SgfCrane grua(ResultSet rs) throws Exception{
		SgfCrane grua = new SgfCrane();
		grua.setAbbreviation(rs.getString("abbreviation"));
		grua.setCraneCode(rs.getLong("craneCode"));
		grua.setCraneType(rs.getString("craneType"));
		grua.setDescription(rs.getString("description"));
		grua.setStatus(rs.getString("sstatus"));
		return grua;
	}

	private SgfChipper chipper(ResultSet rs)  throws Exception {
		SgfChipper chipper = new SgfChipper();
		
		chipper.setAbbreviation(rs.getString("abbreviation"));
		chipper.setChipperCode(rs.getLong("chipperCode"));
		chipper.setDescription(rs.getString("description"));
		chipper.setLocaleCode(rs.getLong("localeCode"));
		chipper.setLineId(rs.getString("lineid"));
		chipper.setStatus(rs.getString("status"));
		
		return chipper;
	}

	

	private String sqlSelecionarGruaPatio(String plates, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		if (isProd) {
			sb.append("FROM bracell.sgfcranes \n");
		} else {
			sb.append("FROM cmpc_hom.sgfcranes \n");
		}
		sb.append("WHERE  \n");
		sb.append(" description like '%"+ plates +"%' \n");
		sb.append(" and cranetype = 2 \n"); // 2 = grua de patio
		return sb.toString();
	}
	
	
	private String sqlSelecionarGruaFlorestal(String plates, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		if (isProd) {
			sb.append("FROM bracell.sgfcranes \n");
		} else {
			sb.append("FROM cmpc_hom.sgfcranes \n");
		}
		sb.append("WHERE  \n");
		sb.append(" description like '%"+ plates +"%' \n");
		sb.append(" and cranetype = 1 \n"); // 1 = grua de campo
		return sb.toString();
	}
	
	private String sqlSelecionarLocale(String locale, Boolean isProd) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *\n");
		if (isProd) {
			sb.append("FROM bracell.sgflocales \n");
		} else {
			sb.append("FROM cmpc_hom.sgflocales \n");
		}
		sb.append("WHERE  \n");
		sb.append(" description like '%"+ locale +"%' \n");
		sb.append(" and cranetype = 1 \n");
		return sb.toString();
	}

	private String sqlInserirParada(GetDownTimes parada, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfdowntimes (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfdowntimes (\n");
		}
		sql.append("	downtimeCode, abbreviation, \n");
		sql.append("	description, status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}

	
	private String sqlInserirProjeto(SgfProject projeto, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfprojects (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfprojects (\n");
		}
		sql.append("	projectCode, projectId, \n");
		sql.append("	description, status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	private String sqlInserirTalhao(SgfStand stand, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfstands (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfstands (\n");
		}
		sql.append("	standId, projectCode, density, \n");
		sql.append("	densityClass, timeAfterCutting, status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	private String sqlInserirGrua(SgfCrane crane, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfcranes (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfcranes (\n");
		}
		sql.append("	craneCode, description, abbreviation, \n");
		sql.append("	craneType, sstatus \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	
	private String sqlInserirCaminhao(SgfTruck caminhao, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgftrucks (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgftrucks (\n");
		}
		sql.append("	truckCode, truckPlate, abbreviation, \n");
		sql.append("	serialNumber, tagRFID, status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	private String sqlInserirMesa(SgfChipper mesa, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfchippers (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfchippers (\n");
		}
		sql.append("	chipperCode, description, abbreviation, \n");
		sql.append("	localeCode, lineId, status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	private String sqlInserirLocal(SgfLocale local, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgflocales (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgflocales (\n");
		}
		sql.append("	localeCode, description, abbreviation, \n");
		sql.append("	status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	private String sqlInserirPilha(SgfPile pilha, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfpiles (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfpiles (\n");
		}
		sql.append("	pileCode, description, localeCode, \n");
		sql.append("	productCode, firstMovementDate, lineId, \n");
		sql.append("	density, densityClass, timeAfterCutting, status \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}

	
	private String sqlWorkflow(Long eventId, Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("SELECT * FROM workflow_event_treatment_rel \n");
		} else {
			sql.append("SELECT * FROM workflow_event_treatment_rel \n");
		}
		sql.append("	WHERE event_id = ").append(eventId);		
		return sql.toString();
	}
	
	private String sqlUpdateWorkflow(Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("UPDATE workflow_event_treatment_rel SET \n");
		} else {
			sql.append("UPDATE workflow_event_treatment_rel SET \n");
		}
		sql.append("	workflow_event_treatment_id = ?, \n");
		sql.append("	event_treatment_integration = ?, updated_at = ?, \n");
		sql.append("	integration_error_detail = ? \n");
		sql.append(" WHERE \n");
		sql.append(" 	event_id = ? \n");
		return sql.toString();
	}
	
	private String sqlInserirWorkflow(Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO workflow_event_treatment_rel (\n");
		} else {
			sql.append("INSERT INTO workflow_event_treatment_rel (\n");
		}
		sql.append("	event_id, workflow_event_treatment_id, \n");
		sql.append("	event_treatment_integration, updated_at, created_at, \n");
		sql.append("	integration_error_detail \n");
		sql.append(") VALUES (\n");
		sql.append(" ?, ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}

	
	private String sqlInserirParadas(Boolean isProd) {
		StringBuilder sql = new StringBuilder();
		if (isProd) {
			sql.append("INSERT INTO bracell.sgfparadas (\n");
		} else {
			sql.append("INSERT INTO cmpc_hom.sgfparadas (\n");
		}
		sql.append("created_at, 	equipment_plate, 	downtime_code, 	start_downtime, \r\n" + 
				"	 end_downtime, 	start_date, 		end_date, 		operator_name, \r\n" + 
				"	 locale_code, 	starting_horimeter, ending_horimeter, event_id, \r\n" +
				"	 error, 	response, custom_data \r\n");
		sql.append(") VALUES (\n");
		sql.append(" SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? \n");
		sql.append(")");
		return sql.toString();
	}
	
	
}

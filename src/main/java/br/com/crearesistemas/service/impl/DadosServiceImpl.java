package br.com.crearesistemas.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.DadosDAO;
import br.com.crearesistemas.service.DadosService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.sgf.wsgetchippers.ChippersResponse;
import br.com.crearesistemas.sgf.wsgetchippers.SgfChipper;
import br.com.crearesistemas.sgf.wsgetchippers.WsGetChippers;
import br.com.crearesistemas.sgf.wsgetcranes.CranesResponse;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.sgf.wsgetcranes.WsGetCranes;
import br.com.crearesistemas.sgf.wsgetdowntimes.GetDownTimes;
import br.com.crearesistemas.sgf.wsgetdowntimes.WsGetDownTimes;
import br.com.crearesistemas.sgf.wsgetlocales.LocalesResponse;
import br.com.crearesistemas.sgf.wsgetlocales.SgfLocale;
import br.com.crearesistemas.sgf.wsgetlocales.WsGetLocales;
import br.com.crearesistemas.sgf.wsgetpiles.PilesResponse;
import br.com.crearesistemas.sgf.wsgetpiles.SgfPile;
import br.com.crearesistemas.sgf.wsgetpiles.WsGetPiles;
import br.com.crearesistemas.sgf.wsgetprojects.ProjectsResponse;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;
import br.com.crearesistemas.sgf.wsgetprojects.WsGetProjects;
import br.com.crearesistemas.sgf.wsgetstands.SgfStand;
import br.com.crearesistemas.sgf.wsgetstands.StandsResponse;
import br.com.crearesistemas.sgf.wsgetstands.WsGetStands;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.sgf.wsgettrucks.TrucksResponse;
import br.com.crearesistemas.sgf.wsgettrucks.WsGetTrucks;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;

@Service
public class DadosServiceImpl extends ServiceImpl<DadosDAO> implements DadosService {
	private static final Logger logger = Logger.getLogger(DadosServiceImpl.class);
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	
	@Override
	@Resource(name = "dadosJdbcDAO")
	public void setDao(DadosDAO dao) {
		super.setDao(dao);
	}

	
	
	@Override
	public void consumirProjetos() {
		ProjectsResponse projetos = WsGetProjects.getProjects(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		
		if (projetos != null) {
			logger.info("Projetos: " + projetos.size() );
			for (SgfProject projeto : projetos) {
				getDao().salvarProjeto(projeto, isProduction);
			}	
		}
		
				
	}

	@Override
	public void consumirTalhoes() {
		StandsResponse stands = WsGetStands.getStands(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		if (stands != null) {
			logger.info("Talhoes: " + stands.size() );
			for (SgfStand stand : stands) {
				getDao().salvarTalhao(stand, isProduction);
			}
		}
		
	}
	
	@Override
	public void consumirGruas() {		
		
		CranesResponse cranes = WsGetCranes.getCranes(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		
		if (cranes != null) {
			logger.info("Gruas: " + cranes.size() );
			
			for (SgfCrane crane : cranes) {
				getDao().salvarGrua(crane, isProduction);
			}	
		}
	}
	
	@Override
	public void consumirCaminhoes() {
		TrucksResponse trucks = WsGetTrucks.getTrucks(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		
		if (trucks != null) {
			logger.info("Caminhoes: " + trucks.size() );
			for (SgfTruck truck : trucks) {
				getDao().salvarCaminhao(truck, isProduction);
			}	
		}
		
	}
	
	
	@Override
	public void consumirMesas() {
		ChippersResponse chippers = WsGetChippers.getChippers(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		if (chippers != null) {
			logger.info("Mesas: " + chippers.size() );
			for (SgfChipper chipper : chippers) {
				getDao().salvarMesa(chipper, isProduction);
			}	
		}		
	}
	
	@Override
	public void consumirLocais() {
		LocalesResponse locales = WsGetLocales.getLocales(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		if (locales != null) {
			logger.info("Locais: " + locales.size() );
			for (SgfLocale locale : locales) {
				getDao().salvarLocal(locale, isProduction);
			}	
		}		
	}
	
	
	@Override
	public void consumirPilhas() {
		PilesResponse piles = WsGetPiles.getPiles(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		if (piles!= null) {
			logger.info("Pilhas: " + piles.size() );
			for (SgfPile pile : piles) {
				getDao().salvarPilhas(pile, isProduction);
			}	
		}		
	}
	
	
	@Override
	public void consumirEventosParada() {
		List<GetDownTimes> paradas = WsGetDownTimes.getDownTimes(config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret());
		
		if (paradas != null) {
			logger.info("Paradas: " + paradas.size() );
			for (GetDownTimes parada : paradas) {
				getDao().salvarParadas(parada, isProduction);
			}	
		}
	}


	@Override
	public SgfCrane selecionarGruaPatio(String plates) {
		return getDao().selecionarGruaPatio(plates, isProduction);
	}
	
	@Override
	public SgfCrane selecionarGruaFlorestal(String plates) {
		return getDao().selecionarGruaFlorestal(plates, isProduction);
	}
	
	@Override
	public SgfLocale selecionarLocal(String locale) {
		return getDao().selecionarLocal(locale, isProduction);
	}


	@Override
	public SgfPile selecionarPilha(String pile) {
		return getDao().selecionarPilha(pile, isProduction);
	}


	@Override
	public SgfChipper selecionarMesa(String chipper) {
		return getDao().selecionarMesa(chipper, isProduction);
	}

	
	@Override
	public SgfChipper selecionarMesaCode(Long chipperCode) {
		return getDao().selecionarMesaCode(chipperCode, isProduction);
	}
	
	@Override
	public void registrarStatusParada(Long eventId, SetEquipmentDowntime downtime, SetEquipmentDowntimeResponse response, String infoLog, Boolean valid) {
		if (downtime != null) {
			if (config.printLogDowntimeSgf()) {
				logger.info("====> Status Workflow da Parada Valid: " + valid);
				logger.info("====> Status Workflow da Parada eventId: " + eventId);
				logger.info("====> Status Workflow Parada: " + downtime.toString() );	
			}
			
			getDao().salvarParadas(eventId, downtime, response, infoLog, valid);	
			
				
		}
	};
	
	
	@Override
	public void salvarEventWorkflow(Long eventId,  Boolean valid, String message, Boolean isProduction) {
			getDao().salvarEventWorkflow(eventId, valid, message, isProduction);	
	};
	
}

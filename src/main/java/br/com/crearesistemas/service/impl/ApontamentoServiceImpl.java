package br.com.crearesistemas.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.ApontamentoDAO;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.EstagioOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.Apontamento;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.service.ApontamentoService;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.DadosService;
import br.com.crearesistemas.service.EventosService;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.service.PgCaminhoesService;
import br.com.crearesistemas.service.PgProjetosService;
import br.com.crearesistemas.service.ProjetosService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.service.dto.ApontamentoDto;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.RastreamentoDto;
import br.com.crearesistemas.service.dto.TriggerEventosDto;
import br.com.crearesistemas.service.impl.helper.GeoLocalizacao;
import br.com.crearesistemas.service.impl.helper.LocalizacaoCerca;
import br.com.crearesistemas.service.impl.helper.CERCAS;
import br.com.crearesistemas.sgf.BaseAuth;
import br.com.crearesistemas.sgf.SgfEventsLoading;
import br.com.crearesistemas.sgf.wsgetchippers.SgfChipper;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;
import br.com.crearesistemas.sgf.wssetinternalmovement.SetInternalMovement;
import br.com.crearesistemas.sgf.wssetinternalmovement.WsSetInternalMovement;
import br.com.crearesistemas.sgf.wssetloading.SetLoadingResponse;
import br.com.crearesistemas.sgf.wssetloading.WsSetLoading;
import br.com.crearesistemas.sgf.wssetunloading.SetUnloading;
import br.com.crearesistemas.sgf.wssetunloading.SetUnloadingResponse;
import br.com.crearesistemas.sgf.wssetunloading.WsSetUnloading;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;
import br.com.crearesistemas.util.StringUtils;
import br.com.crearesistemas.enumeration.LoadType;
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;

@Service
public class ApontamentoServiceImpl extends ServiceImpl<ApontamentoDAO> implements ApontamentoService {
	private static final Logger logger = Logger.getLogger(ApontamentoServiceImpl.class);
	Config config = Config.getInstance();
	
	@Autowired  private CaminhoesService caminhoesService;
	
	@Autowired  private GruasService gruasService;
	
	@Autowired  private DadosService sgfService;
	
	@Resource private ProjetosService projetoService;
	
	@Resource private EventosService eventosService;
	
	@Resource private PgProjetosService pgProjetoService;
	
	@Resource private PgCaminhoesService pgCaminhoesService;
	
	@Autowired  private DadosService dadosService;

	@Override
	@Resource(name = "apontamentoJdbcDAO")
	public void setDao(ApontamentoDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<Apontamento> buscarApontamentos(long mId, List<String> identificadores) {
		return super.getDao().buscarApontamentos(mId, identificadores);
	}

	@Override
	public void processarApontamentosGruasCampo() {
		try {
			LocalizacaoCerca helper = LocalizacaoCerca.getInstance();
			List<CaminhaoEntity> caminhoes = caminhoesService.selecionarCaminhoesTransporte(config.getCustomerId());
			List<GruaEntity> gruas = gruasService.selecionarGruasFlorestais(config.getCustomerId());
			
			for (CaminhaoEntity caminhao : caminhoes) {
				CaminhaoTransporte pgCaminhao = pgCaminhoesService.selectPgCaminhaoByName(caminhao.getName());
				if (pgCaminhao != null) {
					Boolean hasChange = caminhao.hasChangeLine(pgCaminhao.getLineId());
					caminhao.setLineId(pgCaminhao.getLineId());
					if (hasChange) {
						caminhoesService.salvar(caminhao);
					}
				} else {
					caminhao.setLineId(config.getDefaultLineId());
				}
				
				RastreamentoDto rastreamento = new RastreamentoDto(caminhao.getLatitude(), caminhao.getLongitude());
				Boolean implementoEstaNaFabrica = helper.implementoEstaNoLocal(rastreamento, CERCAS.FABRICA) || helper.isStayInCheckpoint(rastreamento);				
				
				Boolean isMesaOuQuadra = caminhoesService.isStayInState(caminhao, 
						Arrays.asList(EstadoOperacional.SI_MESAS, EstadoOperacional.SI_QUADRAS, EstadoOperacional.SI_FILA));

				caminhao.setIsStayInFactory(implementoEstaNaFabrica);
				
				if (!implementoEstaNaFabrica && !isMesaOuQuadra) {
					if (caminhao.getDataPartidaPcct() == null || caminhao.getDataChegadaPcsi() != null || caminhao.getSiteFilaDate() != null ) {
						caminhao.setDataPartidaPcct(new Date());						
						caminhao.setDataChegadaPcsi(null);
						caminhao.setSiteFilaDate(null);
						caminhoesService.salvar(caminhao);
						caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_VIAGEM, caminhao, new Date());
						caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_VIAGEM, caminhao,  new Date());
						caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhao,  new Date());
					}
					
					nearestQueueFarm(pgCaminhao, caminhao);
					
					
					if (caminhao.getTranspEstadoOperacional() == null) {
						caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_VIAGEM, caminhao, null, new Date());
					} else
					if (caminhao.getTranspEstadoOperacional() != null && caminhao.getTranspEstadoOperacional() != EstadoOperacional.TRANSP_CARREGAMENTO.ordinal()) {
						GruaEntity grua = gruaMaisProxima(caminhao, gruas, config.getMaxDistanceToCrane());
						if (grua != null 
								&& EstadoOperacional.stayNotInEstado(caminhao.getCarregEstadoOperacional(),EstadoOperacional.CARREG_CHEIO)) {
							if (config.hasOptOrderOnQueueEnabled && truckHasOpenOrder(pgCaminhao)) {								
								Long projectCode = grua.getSgfProjectCode();
								
								if (projectCode != null  && pgCaminhao.getProjectCode() != null && pgCaminhao.getProjectCode().longValue() == projectCode.longValue()) {
									caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FILA_EM_CAMPO, caminhao, grua, new Date());
								}
							} else {
								caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FILA_EM_CAMPO, caminhao, grua, new Date());
							}
						} else {
							if (caminhao.getSiteEstadoOperacional() != null && caminhao.getSiteEstadoOperacional() != EstadoOperacional.SI_MESAS.ordinal() 
									&& caminhao.getSiteEstadoOperacional() != EstadoOperacional.SI_QUADRAS.ordinal()) {
								caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_VIAGEM, caminhao, null, new Date());	
							}						
						}
					}
					
					// carregamento campo
					carregamentoCampo(gruas, caminhao);
					
					// calcular previsoes chegada
					previsaoChegada(caminhao);
				} else {
					if (caminhao.getDataChegadaPcsi() == null || caminhao.getDataPartidaPcct() != null) {												
						caminhao.setDataChegadaPcsi(new Date());
						caminhao.setDataPartidaPcct(null);
						caminhoesService.salvar(caminhao);
					}
					
					factoryStages(caminhao, implementoEstaNaFabrica);
				
					checkCloseOrder(caminhao);
					
									
				}			
			}		
		} catch (Exception e) {			
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}


	

	
	/*
	 * defines the nearest queue loading on the farm
	 */
	private void nearestQueueFarm(
			CaminhaoTransporte pgCaminhao, CaminhaoEntity caminhao) {
		ProjetoEntity pgProjetoProx;
		if (truckHasOpenOrder(pgCaminhao) && pgCaminhao.getIdProjeto() != null) {
			pgProjetoProx = pgProjetoService.selecionarProjetoById(caminhao.getLatitude(), caminhao.getLongitude(), pgCaminhao.getIdProjeto());
		} else {
			pgProjetoProx = pgProjetoService.selecionarProjetoProximo(caminhao.getLatitude(), caminhao.getLongitude(), 1000);							
		}
		
		if (pgProjetoProx != null) {
			caminhao.setProjectId(pgProjetoProx.getId()); // localId
			caminhao.setProjectIdName(pgProjetoProx.getName());
			if  (!projetoPrevisao(caminhao)) {
				// perc not calculated
				if (caminhao.getPercentualConclusao() != null) {
					caminhao.setPercentualConclusao(null);
				}	
			}
			caminhoesService.salvar(caminhao);
		} else {
			if (projetoPrevisao(caminhao)) {
				caminhoesService.salvar(caminhao);	
			} else {
				// perc not calculated
				if (caminhao.getPercentualConclusao() != null) {
					caminhao.setPercentualConclusao(null);
					caminhoesService.salvar(caminhao);
				}
			}
		};
	}

	/*
		check wether we must close order if limit time is reach out
	*/
	private void checkCloseOrder(CaminhaoEntity caminhao) {
		
		if (isLimitHasGone(caminhao)) {
			pgCaminhoesService.closeOrder(caminhao);			
		}
		
	}


	private boolean isLimitHasGone(CaminhaoEntity caminhao) {
		
		Date startDate = caminhao.getDataLiberacao();
		//Date startDate = caminhao.getDataChegadaPcsi();
		
		if (startDate != null) {
			int elapsed = DateUtils.diferencaEmSegundos(startDate, new Date());
			
			if (elapsed > config.getLimitOrderInSeconds()) {
				return true;
			}
		}
		
		
		return false;
	}

	private void previsaoChegada(CaminhaoEntity caminhao) {
		if (caminhao.getLongitude() != null &&  caminhao.getLatitude() != null) {
			if (caminhao.getDataPrevisaoChegadaFabrica() == null || isExpired(caminhao.getLastUpdPrevisaoChegadaFabrica(),120)) {				
	 			Double distMtsFabrica = GeoLocalizacao.distanciaEmMetrosFabrica(caminhao.getLongitude(), caminhao.getLatitude());				
	 			
				if (distMtsFabrica>0) {
					Long estimateTimeSecs = Math.round(((distMtsFabrica/1000)/60) * 60 * 60);			
					caminhao.setDataPrevisaoChegadaFabrica(DateUtils.somarSegundos(new Date(), estimateTimeSecs.intValue()));
					caminhao.setDistMtsFabrica(distMtsFabrica);
					caminhao.setLastUpdPrevisaoChegadaFabrica(new Date());
					caminhoesService.salvar(caminhao);
				}	
			}	
		}
	}

	private boolean isExpired(Date data, Integer seconds) {
		if (data != null) {
			Date limitDate = DateUtils.somarSegundos(data, seconds);		
			
			return limitDate.before(new Date());	
		}
		return true;
	}

	private Boolean projetoPrevisao(CaminhaoEntity caminhao) {
		Boolean res = false;
		ProjetoEntity pgProjeto = null;
		if (caminhao.getProjectId() != null) {
			pgProjeto = pgProjetoService.selecionarProjetoPrevisao(caminhao.getProjectId(), caminhao.getLatitude(), caminhao.getLongitude());
			
			if (pgProjeto != null) {
				caminhao.setDistMtsProject(pgProjeto.getDistMtsProject());
				caminhao.setDistMtsTotal(pgProjeto.getDistMtsTotal());
				if (pgProjeto.getDistMtsProject()!= null && pgProjeto.getDistMtsTotal()!= null) {
					Integer percentualConclusao;
					if (caminhao.getLoading() == 1) {						
						percentualConclusao = NumberUtils.parseInt(Math.round(100-((pgProjeto.getDistMtsTotal()-pgProjeto.getDistMtsProject())*100)/pgProjeto.getDistMtsTotal()));	
					} else {
						percentualConclusao = NumberUtils.parseInt(Math.round(100-(pgProjeto.getDistMtsProject()*100)/pgProjeto.getDistMtsTotal()));
					}
					if (percentualConclusao != null && percentualConclusao>=0 && percentualConclusao<=100) {
						caminhao.setPercentualConclusao(percentualConclusao);
						res = true;
					}	
				}	
			}
			
		}
		return res;
	}

	// handling farm loadings
	private void carregamentoCampo(List<GruaEntity> gruas, CaminhaoEntity caminhao) {		
		for (GruaEntity grua : gruas) {
			geoFenceFarm(grua);
			
			// loading
			String placa = grua.getPlates();							
			if (caminhao.isEqualsPlates(placa)) {
				Double distanceMts = distanceInMts(grua, caminhao);
				if (distanceMts == null || distanceMts <= config.getMaxDistanceToCrane()) {
					setCarregando(grua, caminhao);	
				}
			} else {							
				setFinalizaCarregamento(grua, caminhao);							
			}
		}
	}

	// calculate the distance in meters between crane and truck
	private Double distanceInMts(GruaEntity grua, CaminhaoEntity caminhao) {		
		if (grua.validateCoords() && caminhao.validateCoords()) {
			return GeoLocalizacao.distanciaEmMetros(grua.getLongitue(), grua.getLatitude(), 
					caminhao.getLongitude(), caminhao.getLatitude());	
		} else {
			return null;	
		}
	}

	private GruaEntity gruaMaisProxima(CaminhaoEntity caminhao, List<GruaEntity> gruas, double limite) {		
		GruaEntity result = null;
		double menor = limite;
		for (GruaEntity grua : gruas) {
			Double distancia = GeoLocalizacao.distanciaEmMetros(grua.getLongitue(), grua.getLatitude(), caminhao.getLongitude(), caminhao.getLatitude());
			if (distancia < limite && distancia < menor) {				
				result = grua;
				menor = distancia;
			}
		}
		return result;
	}

	private void setFinalizaCarregamento(GruaEntity grua, CaminhaoEntity caminhao) {
		if (caminhao.getIdGrua() != null && grua.getId() != null
				&& caminhao.getIdGrua().longValue() == grua.getId().longValue() && 
				caminhao.getTranspEstadoOperacional() == EstadoOperacional.TRANSP_CARREGAMENTO.ordinal()) {
			caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_VIAGEM, caminhao, grua, new Date());
			caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_CHEIO, caminhao, grua, new Date());
			caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_VIAGEM, caminhao, grua, new Date());
			
			SgfCrane sgfGrua = sgfService.selecionarGruaFlorestal(grua.getName());
			SetLoadingResponse result = WsSetLoading.enviaApontamentoSgf(grua, caminhao, sgfGrua);
			salvarWorkflow(caminhao, result);
			if (caminhao.getApontCampoInvalido() != null) {
				caminhoesService.salvar(caminhao);
			}
		}
		
	}

	private void salvarWorkflow(CaminhaoEntity caminhao, SetLoadingResponse result) {
		if (caminhao.getEventLoadId() != null) {
			Boolean valid = false;
			if (result != null) {
				if (result.getReturnId() != null) {
					Integer res = NumberUtils.parseInt(result.getReturnId());
					if (res != null) {
						valid = (res >= 0);
					}
				}
			}
			
			String message = null;
			if (result != null) {
				message = result.getReturnId();
				if (result.getReturnMessage() != null) {
					if (message != null) {
						message += " - "  + result.getReturnMessage();
					} else {
						message = result.getReturnMessage();	
					}	
				}
			}
			
			logger.info(caminhao.getName() + " Salvando Worflow EventId: " + caminhao.getEventLoadId());
			dadosService.salvarEventWorkflow(caminhao.getEventLoadId(), valid, message, config.isProduction());	
		}
	}

	
	private void setCarregando(GruaEntity grua, CaminhaoEntity caminhao) {
		// verifica o nome do projeto apontado
		dadosProjetoSgf(grua);
		
		setLoadingData(grua);
		
		caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_CARREGAMENTO, caminhao, grua, new Date());
		caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhao, grua, new Date());
		caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_VIAGEM, caminhao, grua, new Date());
	}
	
	
	private void setLoadingData(GruaEntity grua) {
		grua.setLoadDateTime(grua.getEventDateTime());
		grua.setLoadEventCode(grua.getEventCode());
		grua.setLoadEventReportId(grua.getEventReportId());		
	}
	
	// farm reverse geofence location
	private void geoFenceFarm(GruaEntity grua) {
		if (grua.getSgfProjectCode() == null || isExpired(grua.getSgfProjectDate(), 3 * 60)) {
			ProjetoEntity pgProjetoProx = pgProjetoService.selecionarProjetoProximo(grua.getLatitude(), grua.getLongitue(), 500);
			if (pgProjetoProx != null) {
				grua.setSgfProjectCode(pgProjetoProx.getProjectCode());
				grua.setSgfProjectId(pgProjetoProx.getProjectId());
				grua.setSgfProjectDate(new Date());
			}	
		}
	}
	
	private void dadosProjetoSgf(GruaEntity grua) {		
		// enriquece dados projeto sgf
		if (grua.getProject() != null) {
			Long projectCode = NumberUtils.parseLong(grua.getProject());			
			SgfProject projeto = projetoService.selecionarProjetoByProjectCode(projectCode);
			if (projeto != null) {
				grua.setProjectName(projeto.getDescription());
			}
		}
	}

	
	
	private void setCarregandoFabrica(GruaEntity grua, CaminhaoEntity caminhao) {		
		Date eventDate;
		
		eventDate = new Date();
		
		if (grua.isMesa()) {
			caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhao, grua, eventDate);
			caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhao, grua, eventDate);
			caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_MESAS, caminhao, grua, eventDate);	
		} else {
			caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhao, grua, eventDate);
			caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhao, grua, eventDate);
			
			if (alocaQuadraConjunto(grua, caminhao))  {
				caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_QUADRAS, caminhao, grua, eventDate);	
			}
		}
	}

	
	private void setFinalizaCarregamentoFabrica(GruaEntity grua, CaminhaoEntity caminhao) {		
		if (config.truckIsAloneInStockyard(caminhao)) {
			caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhao, new Date());
			caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhao, new Date());
			caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_FILA_BALANCA_SAIDA, caminhao, new Date());
		} else
		if (caminhao.getIdLocal() != null && grua.getIdLocal() != null &&
				caminhao.getIdLocal().longValue() == grua.getIdLocal().longValue() &&
				(		caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_MESAS.ordinal() ||
						caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_QUADRAS.ordinal()
				)) {
			
			Boolean isLocalMesa = false;
			if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_MESAS.ordinal()) {
				isLocalMesa = true;				
			}
			
			caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhao, grua, new Date());
			caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhao, grua, new Date());
			caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_FILA_BALANCA_SAIDA, caminhao, grua, new Date());
			
			
			try {
				if (truckHasOpenOrder(caminhao)) {
					// madeira externa
					enviaApontamentoPatioSgfDescarga(grua, caminhao);
				} else				
				if (isLocalMesa) {
					if (isCarregamentoPatioInterno(caminhao)) {
						enviaApontamentoPatioInternoSgf(grua, caminhao, "D");
					} else {
						enviaApontamentoPatioSgfDescarga(grua, caminhao);
					}
				} else 
				if (LoadType.isCarregamento(caminhao.getLoadType())) {
					// carregamento origem no patio
					enviaApontamentoPatioInternoSgf(grua, caminhao, "C");
				} else {
					if (isCarregamentoPatioInterno(caminhao)) {
						enviaApontamentoPatioInternoSgf(grua, caminhao, "D");
					} else {
						// madeira externa
						enviaApontamentoPatioSgfDescarga(grua, caminhao);	
					}
					
				}
					
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
		}  
	}
	
	
	private boolean truckHasOpenOrder(CaminhaoTransporte pgCaminhao) {		
		if (pgCaminhao != null && pgCaminhao.getOrdemSituacao() != null && pgCaminhao.getOrdemSituacao() == SituacaoOrdemTransporte.LIBERADA) {
			return true;
		}
		return false;
	}


	private boolean truckHasOpenOrder(CaminhaoEntity caminhao) {
		if (caminhao != null && caminhao.getName() != null) {
			InterfaceSgfDto interfaceSgf =  pgCaminhoesService.selecionarInterfaceSgf(caminhao.getName());			
			if (interfaceSgf.getOtDataLiberacao() != null && interfaceSgf.getOtSituacao() == SituacaoOrdemTransporte.LIBERADA) {
				return true;
			}	
		}
		return false;
	}

	private boolean isCarregamentoPatioInterno(CaminhaoEntity caminhao) {
		// neste caso somente se  não possuir OT, ou o apontamento anterior for carregamento na fabrica
		Boolean isPationInterno = caminhao.getIsPatioInterno();
		
		return  isPationInterno != null && isPationInterno;
	}

	
	
	private void informaPatioInterno(CaminhaoEntity caminhao, String tipReport) {
		if (tipReport == "C") {
			caminhao.setIsPatioInterno(true);
			caminhao.setEventInternalId(caminhao.getEventReportId());
		} else {
			caminhao.setIsPatioInterno(false);
			caminhao.setEventInternalId(null);
		}
		caminhoesService.salvar(caminhao);		
	}
	
	private void enviaApontamentoPatioInternoSgf(
			GruaEntity grua, CaminhaoEntity caminhao, String tipReport) {
		
		logger.info("Sgf SetInternalMovement enviaApontamentoPatioInternoSgf: " + caminhao.getName());
		
		SetInternalMovement loading = new SetInternalMovement();
		SgfCrane sgfGrua = sgfService.selecionarGruaPatio(grua.getName());
		
		
		loading.setTipReport(tipReport);
		if (tipReport == "C") {
			// informa a pk da eventos
			if (caminhao.getEventReportId() != null) {
				loading.setReportId(caminhao.getEventReportId());	
			}			
		} else {
			// informa a pk do corregamento anterior
			if (caminhao.getEventInternalId() != null) {
				loading.setReportId(caminhao.getEventInternalId());	
			}	
		}
		
		Long reportId = loading.getReportId();
		if (reportId == null || reportId < 1) {
			reportId = caminhoesService.selMaxEventos(caminhao.getId());
			if (reportId != null) {				
				caminhao.setEventReportId(reportId);
				loading.setReportId(reportId);	
			}
		}
		
		
		loading.setTruckPlate(caminhao.getPlates());
		
		if (caminhao.getLocale() != null)  {
			loading.setLocaleCode(NumberUtils.parseLong(caminhao.getLocale()));
		}
		
		if (caminhao.getPile() != null)  {
			loading.setPileCode(NumberUtils.parseLong(caminhao.getPile()));
		}
		
		
		loading.setChipperCode(0l);
		if (caminhao.getChipper() != null)  {
			Long chipperCode = NumberUtils.parseLong(caminhao.getChipper());
			if (chipperCode > 0) {
				loading.setChipperCode(chipperCode);
				// pesquisa a mesa
				SgfChipper sgfChipper = sgfService.selecionarMesaCode(chipperCode);				
				if (sgfChipper != null 
						&& sgfChipper.getLocaleCode() != null) {					
					loading.setLocaleCode(sgfChipper.getLocaleCode());					
				}
			}
		}
		
		
		loading.setCraneOperatorName(caminhao.getCraneOperatorName());
		
		if (sgfGrua != null) {
			loading.setCraneId(sgfGrua.getAbbreviation());	
		} else {
			loading.setCraneId(grua.getName());
		}
		
		
		Date dataInicio;
		if (caminhao.getCraneDateTime() != null) {
			loading.setStartOperation(DateUtils.formatDate(caminhao.getCraneDateTime())); //"2021-07-21T14:10:00"
			dataInicio = caminhao.getCraneDateTime();
		} else {
			loading.setStartOperation(DateUtils.formatDate(grua.getEventDateTime())); //"2021-07-21T14:10:00"
			dataInicio = grua.getEventDateTime();
		}
		
		
		Date dataFim = new Date();		
		if (dataInicio != null && dataFim != null) {
			if (dataFim.after(dataInicio)) {
				loading.setEndOperation(DateUtils.formatDate(dataFim));
			} else {
				loading.setEndOperation(DateUtils.formatDate(DateUtils.somarSegundos(dataInicio, 65)));
			}
		} else {
			loading.setEndOperation(DateUtils.formatDate(new Date()));	
		}
		
		//logger.info("Dados Carregamento Patio Sgf: " + loading.toString());
		
		if (config.printLogSgf()) {
			logger.info("");
			logger.info("");
			logger.info("");
			logger.info("================= Dados Movimento Interno (SgfSetInternalMovement) Patio Sgf: ============== " );
			logger.info("SgfSetIntMov url: " + config.getSgfUrlBase() );
			logger.info("SgfSetIntMov url follow: " + BaseAuth.followRedirectURL(config.getSgfUrlBase()));
			logger.info("SgfSetIntMov tipReport: " + loading.getTipReport());
			logger.info("SgfSetIntMov reportId: " + loading.getReportId() );
			logger.info("SgfSetIntMov truckPlate: " + loading.getTruckPlate()  );
			logger.info("SgfSetIntMov craneId: " + loading.getCraneId()  );
			logger.info("SgfSetIntMov localeCode: " + loading.getLocaleCode()  );
			logger.info("SgfSetIntMov pileCode: " + loading.getPileCode()  );
			logger.info("SgfSetIntMov chipperCode: " + loading.getChipperCode()  );
			logger.info("SgfSetIntMov craneOperatorName: " + loading.getCraneOperatorName()  );
			logger.info("SgfSetIntMov startingDate: " + loading.getStartOperation()  );
			logger.info("SgfSetIntMov endingDate: " + loading.getEndOperation()  );
			logger.info("");
			logger.info("");
			logger.info("");
		}
		
		
		
		informaPatioInterno(caminhao, tipReport);
		
		
		incrementaEvento(caminhao, tipReport);
		
		String result = WsSetInternalMovement.setLoading(
				config.getSgfUrlBase(), 
				config.getSgfAppId(),
				config.getSgfSecret(), 
				loading);
		
		
		Boolean valid = false;
		if (result != null) {
			if (config.printLogSgf()) {
				logger.info(" **************************** Result Sgf Patio Interno (SgfSetInternalMovement): *********************************** ");
				logger.info("SgfSetIntMov Patio Interno: " + result.toString());	
			}
			
			if (result != null) {
				valid = true;
			}
		}
		
		
		if (loading.getReportId() != null && loading.getReportId()>0) {			
			logger.info(">>> SgfSetIntMov salvando situaçao no workflow. eventId: " + loading.getReportId());
			dadosService.salvarEventWorkflow(loading.getReportId(), valid , result, config.isProduction());	
		}
	}
	
	
	

	private void incrementaEvento(CaminhaoEntity caminhao, String tipReport) {
		if (tipReport != "C") {
			caminhoesService.incrementaEvento(caminhao.getId());
		}
	}

	private void enviaApontamentoPatioSgfDescarga(
			GruaEntity grua, CaminhaoEntity caminhao) {
		
		logger.info("Sgf enviaApontamentoPatioSgfDescarga: " + caminhao.getName());
		
		SetUnloading loading = new SetUnloading();
		SgfCrane sgfGrua = sgfService.selecionarGruaPatio(grua.getName());
		if (sgfGrua != null) {
			loading.setCraneCode(sgfGrua.getCraneCode());	
		}
		
		
		loading.setReportID(Long.toString(grua.getEventReportId()));
		loading.setTruckPlate(caminhao.getName());
		
		
		if (caminhao.getChipper() != null)  {
			loading.setChipperCode(NumberUtils.parseLong(caminhao.getChipper()));
		} else {
			logger.info("SgfSetUnloading !!!!!!!!!!! O campo chipper é obrigatorio e não foi informado");
			logger.info("SgfSetUnloading !!!!!!!!!!! gruaName: " + grua.getName()  + " caminhao: " + caminhao.getPlates());
			return;
		}
		
		if (caminhao.getPile() != null)  {
			loading.setPileCode(NumberUtils.parseLong(caminhao.getPile()));
		}
		
		loading.setStartingHorimeterNumber("0");
		loading.setEndingHorimeterNumber("10");
		
		
		Date dataInicio;
		if (caminhao.getCraneDateTime() != null) {
			loading.setStartingDate(DateUtils.formatDate(caminhao.getCraneDateTime())); //"2021-07-21T14:10:00"
			dataInicio = caminhao.getCraneDateTime();
		} else {
			loading.setStartingDate(DateUtils.formatDate(grua.getEventDateTime())); //"2021-07-21T14:10:00"
			dataInicio = grua.getEventDateTime();
		}
		
		
		Date dataFim = new Date();
		
		if (dataInicio != null && dataFim != null) {
			if (dataFim.after(dataInicio)) {
				loading.setEndingDate(DateUtils.formatDate(dataFim));
			} else {
				loading.setEndingDate(DateUtils.formatDate(DateUtils.somarSegundos(dataInicio, 65)));
			}
		} else {
			loading.setEndingDate(DateUtils.formatDate(new Date()));	
		}
		
		
		loading.setCraneOperatorName(caminhao.getCraneOperatorName());
		
		
		logger.info("================= Dados Descarregamento (SgfSetUnloading) Patio Sgf: ============== " );
		logger.info("SgfSetUnload url: " + config.getSgfUrlBase() );
		logger.info("SgfSetUnload url follow: " + BaseAuth.followRedirectURL(config.getSgfUrlBase()));
		logger.info("SgfSetUnload gruaName: " + grua.getName() );
		logger.info("SgfSetUnload reportID: " + loading.getReportID() );
		logger.info("SgfSetUnload truckPlate: " + loading.getTruckPlate()  );
		logger.info("SgfSetUnload craneCode: " + loading.getCraneCode()  );
		logger.info("SgfSetUnload pileCode: " + loading.getPileCode()  );
		logger.info("SgfSetUnload chipperCode: " + loading.getChipperCode()  );
		logger.info("SgfSetUnload craneOperatorName: " + loading.getCraneOperatorName()  );
		logger.info("SgfSetUnload startingHorimeterNumber: " + loading.getStartingHorimeterNumber()  );
		logger.info("SgfSetUnload endingHorimeterNumber: " + loading.getEndingHorimeterNumber()  );
		logger.info("SgfSetUnload startingDate: " + loading.getStartingDate()  );
		logger.info("SgfSetUnload endingDate: " + loading.getEndingDate()  );
		logger.info("SgfSetUnload Dados Descarregamento Patio Sgf: " + loading.toString());
		
		informaPatioInterno(caminhao, "D");
		
		SetUnloadingResponse result = WsSetUnloading.setUnloading(
				config.getSgfUrlBase(), 
				config.getSgfAppId(),
				config.getSgfSecret(), 
				loading);
		
		logger.info(" **************************** Result Sgf Descarga Patio Interno (SgfSetUnloading) : *********************************** ");
		Boolean valid = false;
		
		if (result != null) {
			logger.info("===> RESULT Sgf Descarga Patio: " + result.toString());		
			logger.info("SgfSetUnload returnId: " + result.getReturnId());
			logger.info("SgfSetUnload returnMessage: " + result.getReturnMessage());
			if (loading.getReportID() != null) {
				Integer resultId = NumberUtils.parseInt(result.getReturnId());
				if (resultId != null) {					
					valid = (resultId>=0);
				}					
			}
		} else {
			logger.info("SgfSetUnload: Nenhum dado retornado da interface SetUnloading");
		}
		
		Long eventId = NumberUtils.parseLong(loading.getReportID());
		if (eventId != null && eventId>0) {
			logger.info(">>> SgfSetUnload salvando situaçao no workflow. eventId: " + eventId);
			String message = null;
			if (result != null) {
				message = result.getReturnId();
				if (result.getReturnMessage() != null) {
					if (message != null) {
						message += " - "  + result.getReturnMessage();
					} else {
						message = result.getReturnMessage();	
					}	
				}
			}
			dadosService.salvarEventWorkflow(eventId, valid , message, config.isProduction());	
		}
		
		
	}
	

	
	@Override
	public void processarApontamentosFabrica() {
		List<CaminhaoEntity> caminhoes = new ArrayList<CaminhaoEntity>();
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();

		try {
				gruas 
						= gruasService.selecionarGruasSiteIndustrial(config.getCustomerId());
				
				caminhoes 
						= caminhoesService.selecionarCaminhoesTransporte(config.getCustomerId());
				
				// mapeamento gruas nas quadras
				config.mapeiaLocaisGruas(gruas);
				
				
				for (CaminhaoEntity caminhao : caminhoes) {
					for (GruaEntity grua : gruas) {
						// alocacao grua
						alocacaoGrua(grua);
						
						// carregamento
						carregamento(grua, caminhao, caminhoes);
						
						// fila
						filaFabrica(caminhao);
					}	
				}
				
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	
	private void filaFabrica(CaminhaoEntity caminhao) {

		RastreamentoDto rastre = new RastreamentoDto(caminhao);
		LocalizacaoCerca helper = LocalizacaoCerca.getInstance();
		
		Boolean implementoEstaNaFabrica = helper.implementoEstaNoLocal(rastre, CERCAS.FABRICA) || helper.isStayInCheckpoint(rastre);				
		
		if (!implementoEstaNaFabrica) {
			InterfaceSgfDto interfaceSgf =  pgCaminhoesService.selecionarInterfaceSgf(caminhao.getName());			
			if (truckHasInterfaceReceived39(caminhao, interfaceSgf)) {
				estagioFilaFabrica(caminhao, interfaceSgf);
			}
		}
		
		
	}

	private boolean truckHasInterfaceReceived39(CaminhaoEntity caminhao, InterfaceSgfDto interfaceSgf) {
		if (interfaceSgf != null 
				&& interfaceSgf.getDataRecebimento() != null 
				&& interfaceSgf.getOtSituacao() == SituacaoOrdemTransporte.LIBERADA) {
			
			TriggerEventosDto triggerSI = caminhao.getTriggerSI();
			
			if (triggerSI == null ) {
				return true;
			} else {
				if (triggerSI.getTr06DataMesa() == null && triggerSI.getTr16DataQuadra() == null) {
					return true;
				} else
				if (triggerSI.getTr06DataMesa() != null && DateUtils.compareWithPrevious(triggerSI.getTr06DataMesa(), interfaceSgf.getDataRecebimento())) {
					return true;
				} else if (triggerSI.getTr16DataQuadra() != null && DateUtils.compareWithPrevious(triggerSI.getTr16DataQuadra(), interfaceSgf.getDataRecebimento())) {
					return true;
				}
			}
		}
		
		return false;
	}

	private void estagioFilaFabrica(CaminhaoEntity caminhao, InterfaceSgfDto interfaceSgf) {
		
		EstagioOperacional estagio;
		if (interfaceSgf.getChipperCode() != null) {
			estagio = config.getEstagioFilaByChipperCode(interfaceSgf.getChipperCode());
		} else					
		if (interfaceSgf.getPileCode() != null) {
			estagio = EstagioOperacional.FILA_QUADRAS;
		} else {
			estagio = EstagioOperacional.FILA_MESA1;
		}
		
		caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhao, new Date());
		caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_FILA, estagio, caminhao, new Date());

		
	}

	// alocacoes nos estagios
	private void alocacaoGrua(GruaEntity grua) {
		if (grua.isMesa()) {
			if (config.isGruaChangeMesa(grua)) {
				gruasService.salvarGrua(grua);
			}
		} else {
			if (config.isGruaChangeQuadraOuStandBy(grua)) {
				gruasService.salvarGrua(grua);
			}
		}
		
		

		// salvar o ultimo carregamento
		String placa = grua.getPlates();
		if (placa != null && !placa.trim().isEmpty() && placa.length() > 3) {
			if (grua.getEventDateTime() != null && grua.getLoadDateTime() != grua.getEventDateTime()) {					
					setLoadingData(grua);
					gruasService.salvarGrua(grua);
					
			}
		}
		
	}

	// trata carregamentos
	private void carregamento(GruaEntity grua, CaminhaoEntity caminhao, List<CaminhaoEntity> caminhoes) {
		String placa = grua.getPlates();
		if (placa != null) {
			if (!placa.trim().isEmpty() && placa.length() > 3 && caminhao.getName().contains(placa.trim())) {
				setCarregandoFabrica(grua, caminhao);
				liberaCaminhoes(grua, caminhao,caminhoes);
			} else {
				setFinalizaCarregamentoFabrica(grua, caminhao);						
			}
		} else {
			setFinalizaCarregamentoFabrica(grua, caminhao);
		}
		
		// verifica se grua patio foi alocada
		if (!grua.isMesa()) {
			if (config.isGruaPatioCarregamento(grua.getEventCode()) && !config.isLocalQuadras(grua.getIdLocal())) {
				alocaGruaQuadra(grua);
			}
		}
		
		
	}

	private void liberaCaminhoes(GruaEntity grua, CaminhaoEntity caminhao, List<CaminhaoEntity> caminhoes) {
		
		for (CaminhaoEntity caminhaoh : caminhoes) {
			if (caminhaoh.getIdLocal() != null && caminhaoh.getSiteEstadoOperacional() != null) {
				if (caminhaoh.getSiteEstadoOperacional() == EstadoOperacional.SI_QUADRAS.ordinal() 
						&& grua.getIdLocal().longValue() == caminhaoh.getIdLocal().longValue()
						&& !caminhao.getName().contains(caminhaoh.getName())
					) {
					caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhaoh,new Date());
					caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_VAZIO, caminhaoh, new Date());
					caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL, EstadoOperacional.SI_FILA_BALANCA_SAIDA, caminhaoh,  new Date());	
				}	
			}
		}
		
	}

	

	Boolean caminhaoAlocado = false;
	private Boolean alocaQuadraConjunto(GruaEntity grua, CaminhaoEntity caminhao) {
		caminhaoAlocado = false;
		if (config.hashLocaisQuadras.containsKey(grua.getIdLocal())) {
			caminhao.setIdLocal(grua.getIdLocal());
			caminhoesService.salvar(caminhao);
			
			if (grua.getSiteEstadoOperacional() == null || grua.getSiteEstadoOperacional() != EstadoOperacional.SI_QUADRAS.ordinal()) {
				grua.setSiteEstadoOperacional(EstadoOperacional.SI_QUADRAS.ordinal());
				gruasService.salvarGrua(grua);	
			}
			caminhaoAlocado = true;
		} else {
			config.hashLocaisQuadras.keySet().forEach(key -> {
				if (!caminhaoAlocado && config.hashLocaisQuadras.get(key) == null) {
					config.hashLocaisQuadras.put(key, grua);
					caminhao.setIdLocal(key);
					caminhoesService.salvar(caminhao);
					
					grua.setSiteEstadoOperacional(EstadoOperacional.SI_QUADRAS.ordinal());
					grua.setIdLocal(key);
					gruasService.salvarGrua(grua);
					caminhaoAlocado = true;
				}
			});
		}
		return caminhaoAlocado;
	}

	private void alocaGruaQuadra(GruaEntity grua) {
		if (config.hashLocaisQuadras.containsKey(grua.getIdLocal())) {			
			if (grua.getSiteEstadoOperacional() == null || grua.getSiteEstadoOperacional() != EstadoOperacional.SI_QUADRAS.ordinal()) {
				grua.setSiteEstadoOperacional(EstadoOperacional.SI_QUADRAS.ordinal());
				gruasService.salvarGrua(grua);	
			}
		} else {
			config.hashLocaisQuadras.keySet().forEach(key -> {
				if (config.hashLocaisQuadras.get(key) == null) {
					config.hashLocaisQuadras.put(key, grua);
					
					grua.setSiteEstadoOperacional(EstadoOperacional.SI_QUADRAS.ordinal());
					grua.setIdLocal(key);
					gruasService.salvarGrua(grua);
				}
			});
		}
	}
	
	
	
	@Override
	public void encerrarOrdens() {
		
		pgCaminhoesService.closeOlderOrder();
		
	}

	
	/*
	 * WWX1A49,0,0,002,0,0,0,1
		RTI9E84,0,2,030,0,0,0,1
		RTI9E84,0,2,030,0,0,0,1
		RTI9E76,0,2,030,0,0,0,1
		RTI9E76,0,2,030,0,0,0,1
		RTI9E75,0,2,030,0,0,0,1
		RTI9E75,0,2,030,0,0,0,1
		RND8G50,0,0,006,0,0,0,1
		RF4106240519,GBU1B94,0,2,9,0,0,0,1
		RF4106240519,GAC9G74,0,2,9,0,0,0,1
		RF4106240519,FCQ2B84,0,2,9,0,0,0,1
		RF4106240519,FCC1A03,0,2,9,0,0,0,1
		RF4106240519,FAO8B95,0,2,9,0,0,0,1
		RF4106240519,EBU6E16,0,2,9,0,0,0,1
		RF4106240519,COI7E52,0,2,9,0,0,0,1
		RF4104881031,BYI1F44,0,2,9,0,0,0,1
		RF3436627590,EHVOF07,0,0,4,0,0,0,1
	 */
	@Override
	public void processarApontamentosCampoNaoTratados() {
		
		List<SgfEventsLoading> sgfNonTreatedEvents= eventosService.selecionarSgfApontamentosCampoPendentes();
		
		
		for (SgfEventsLoading nonTreatedEvent: sgfNonTreatedEvents) {
			
			SetLoadingResponse result = WsSetLoading.enviaApontamentoSgf(nonTreatedEvent);
			
			salvarWorkflow(nonTreatedEvent, result);
		}
		
	}
	
	private void salvarWorkflow(SgfEventsLoading nonTreatedEvent, SetLoadingResponse result) {
		if (nonTreatedEvent.getEventId() != null) {
			Boolean valid = false;
			if (result != null) {
				if (result.getReturnId() != null) {
					Integer res = NumberUtils.parseInt(result.getReturnId());
					if (res != null) {
						valid = (res >= 0);
					}
				}
			}
			
			String message = null;
			if (result != null) {
				message = result.getReturnId();
				if (result.getReturnMessage() != null) {
					if (message != null) {
						message += " - "  + result.getReturnMessage();
					} else {
						message = result.getReturnMessage();	
					}	
				}
			}
			
			logger.info("Salvando Worflow EventId: " + nonTreatedEvent.getEventId());
			dadosService.salvarEventWorkflow(nonTreatedEvent.getEventId(), valid, message, config.isProduction());	
		}
	}
	
	
	@Override
	public void processarApontamentosParada() {
		List<CaminhaoEntity> caminhoes = new ArrayList<CaminhaoEntity>();
		List<GruaEntity> gruasSite = new ArrayList<GruaEntity>();

		try {
			gruasSite 
			= gruasService.selecionarGruasSiteIndustrial(config.getCustomerId());

			caminhoes 
			= caminhoesService.selecionarCaminhoesTransporte(config.getCustomerId());
	
			

			try {
				processarApontamentosParadaCaminhao(caminhoes);	
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
			
			try {
				processarApontamentosParadaGruas(gruasSite);	
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
			
			try {
				List<GruaEntity> gruasCampo = gruasService.selecionarGruasFlorestais(config.getCustomerId());
				processarApontamentosParadaGruas(gruasCampo);	
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
    // metodo para processar paradas de caminhoes
	private void processarApontamentosParadaCaminhao(List<CaminhaoEntity> caminhoes) {
		try {
			for (CaminhaoEntity caminhao : caminhoes) {
				if (caminhao.getName() != "teste" ) {
					
					if (caminhao.isDowntime()) {
						if (caminhao.isDowntimeSameCode()) {
							// salva data inicial
							openDowntime(caminhao);	
						} else {
							// finaliza o downtime anterior
							if (closeDowntime(caminhao) == true) {
								// abre o downtime novo
								openDowntime(caminhao);	
							}
						}
					} else {
						// finaliza o downtime
						closeDowntime(caminhao);
					}	
				}
			}
	
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private Boolean closeDowntime(CaminhaoEntity caminhao) {
		return caminhoesService.closeDowntime(caminhao);		
	}

	private void openDowntime(CaminhaoEntity caminhao) {		
		caminhoesService.openDowntime(caminhao);
	}

	
	private void processarApontamentosParadaGruas(List<GruaEntity> gruas) {
		try {
			for (GruaEntity grua : gruas) {
				// somente para testes exclusivo
				if (grua.isDowntime()) {
					if (grua.isDowntimeSameCode()) {
						// salva data inicial
						openDowntime(grua);	
					} else {
						// finaliza o downtime anterior
						if (closeDowntime(grua) == true) {
							// abre o downtime novo
							openDowntime(grua);	
						}
					}
				} else {
					// finaliza o downtime
					closeDowntime(grua);
				}				
			}
	
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private Boolean closeDowntime(GruaEntity grua) {
		return gruasService.closeDowntime(grua);		
	}

	private void openDowntime(GruaEntity grua) {		
		gruasService.openDowntime(grua);
	}

	private TriggerEventosDto updateTriggerEvents(CaminhaoEntity truck) {
		Boolean isDataChange = false;
		InterfaceSgfDto interfaceSgf = pgCaminhoesService.selecionarInterfaceSgf(truck.getName());
		truck.setDataLiberacao(interfaceSgf.getOtDataLiberacao());
		
		TriggerEventosDto triggerRes = truck.getTriggerSI();
		
		LocalizacaoCerca helperGeo = LocalizacaoCerca.getInstance();
		
		RastreamentoDto rastre = new RastreamentoDto(truck);
		
		Integer loading = truck.getLoading();
		if (loading == null) {
			loading = 0;
		}
		
		// states
		Boolean isMesaOuQuadra = caminhoesService.isStayInState(truck, 
				Arrays.asList(EstadoOperacional.SI_MESAS, EstadoOperacional.SI_QUADRAS));

		triggerRes.setIsMesaOuQuadra(isMesaOuQuadra);
		
		if (truck.getTranspEstadoOperacional() != null) {
			EstadoOperacional estado = config.getEstadoOperacionalById(truck.getTranspEstadoOperacional());
			if (estado != null) {
				triggerRes.setEstadoTransp(estado);
			}
		}
		
		if (truck.getSiteEstadoOperacional() != null) {
			EstadoOperacional estado = config.getEstadoOperacionalById(truck.getSiteEstadoOperacional());
			if (estado != null) {
				triggerRes.setEstadoSite(estado);
			}
		}
		
		if (truck.getCarregEstadoOperacional() != null) {
			EstadoOperacional estado = config.getEstadoOperacionalById(truck.getCarregEstadoOperacional());
			if (estado != null) {
				triggerRes.setEstadoCarreg(estado);
			}
		}
		
		if (truck.getLoading() != null && truck.getLoading() == 1) {
			triggerRes.setEstadoCarreg(EstadoOperacional.CARREG_CHEIO);
			truck.setCarregEstadoOperacional(EstadoOperacional.CARREG_CHEIO.ordinal());
		} else {
			triggerRes.setEstadoCarreg(EstadoOperacional.CARREG_VAZIO);
			truck.setCarregEstadoOperacional(EstadoOperacional.CARREG_VAZIO.ordinal());
		}
		
		
		if (truck.getProprioEstadoOperacional() != null) {
			EstadoOperacional estado = config.getEstadoOperacionalById(truck.getProprioEstadoOperacional());
			if (estado != null) {
				triggerRes.setEstadoProprio(estado);
			}
		}
		
		// order		
		if (interfaceSgf.getOtId() != null) {
			if (triggerRes.getOtId() == null 
					|| interfaceSgf.getOtId().longValue() != triggerRes.getOtId().longValue()
					|| !StringUtils.isEquals(interfaceSgf.getOtNumero(), triggerRes.getOtNumero())
					|| !SituacaoOrdemTransporte.isEquals(interfaceSgf.getOtSituacao(), triggerRes.getOtSituacao())
				) {
				isDataChange = true;
			}
			triggerRes.setOtId(interfaceSgf.getOtId());
			triggerRes.setOtDataLiberacao(interfaceSgf.getOtDataLiberacao());
			triggerRes.setOtNumero(interfaceSgf.getOtNumero());
			triggerRes.setOtSituacao(interfaceSgf.getOtSituacao());	
		} else {
			if (triggerRes.getOtId() != null) {
				isDataChange = true;
			}
			triggerRes.setOtId(null);
			triggerRes.setOtDataLiberacao(null);
			triggerRes.setOtNumero(null);
			triggerRes.setOtSituacao(null);	
		}
		
		if (truck.getAvldateTime() != null && loading == 1 && 
				(helperGeo.isStayInCheckpoint(rastre) || helperGeo.isStayInCheckpointEmbedding(rastre))
		) {
			if (triggerRes.getTr01DataCercaSF() == null || !triggerRes.getTr01DataCercaSF().equals(truck.getAvldateTime())) {
				isDataChange = true;
				triggerRes.setTr01DataCercaSF(truck.getAvldateTime());
				triggerRes.setTr01RastreamentoSF(new RastreamentoDto(truck));	
			}
		};
		
		// evento ao sair do estagio
		if (truck.getSiteEstadoOperacional() != null && truck.getSiteEstadoOperacional() == EstadoOperacional.SI_SENTIDO_FABRICA.ordinal()) {
			if (truck.getAvldateTime() != null && loading == 1 && 
					!helperGeo.isStayInCheckpoint(rastre) && !helperGeo.isStayInCheckpointEmbedding(rastre) 
			) {
				triggerRes.setTr02DataCercaAE(truck.getAvldateTime());
				triggerRes.setTr02RastreamentoAE(new RastreamentoDto(truck));
			}
		}
		
		if (helperGeo.isStayInEntrance(rastre)) {
			triggerRes.setTr02DataCercaAE(truck.getAvldateTime());
			triggerRes.setTr02RastreamentoAE(new RastreamentoDto(truck));
		}
		
		
		if (config.isEventoCaminhaoFilaBalanca(truck.getEventCode())) {
			triggerRes.setTr03ApontamentoFB(new ApontamentoDto(truck));
			triggerRes.setTr03dataAptFB(truck.getEventDateTime());
		}
		
		// obs: deve informar o estado atual da interface, mesmo se não possuir a informação (null)
		//if (interfaceSgf.getDataRecebimento() != null) {
			triggerRes.setTr04DataRecebimentoBE(interfaceSgf.getDataRecebimento());
			triggerRes.setTr04IdBalancaEntradaBE(interfaceSgf.getIdBalancaEntrada());				
		//}
		
		
		// obs: deve informar o estado atual da interface, mesmo se não possuir a informação (null)
		//if (interfaceSgf.getDataReprogramacao() != null) {
			triggerRes.setTr05ChipperCodeFI(interfaceSgf.getChipperCode());
			triggerRes.setTr05DataReprogramacaoFI(interfaceSgf.getDataReprogramacao());
			triggerRes.setTr05PileCodeFI(interfaceSgf.getPileCode());
		//}
		
		
		
		if ((helperGeo.isStayInCheckpoint(rastre) || helperGeo.isStayInCheckpointExitEmbedding(rastre)) && loading == 0) {
			triggerRes.setTr11DataCercaCS(truck.getAvldateTime());
			triggerRes.setTr11RastreamentoCS(new RastreamentoDto(truck));	
		}
		
		
		if (config.isEventoCaminhaoVarricao(truck.getEventCode())) {
			triggerRes.setTr12ApontamentoVarricao(new ApontamentoDto(truck));
			triggerRes.setTr12DataVarricao(truck.getEventDateTime());				
		}
		
		// obs: deve informar o estado atual da interface, mesmo se não possuir a informação (null)
		//if (interfaceSgf.getDataFimViagem() != null) {
			triggerRes.setTr13DataFimViagem(interfaceSgf.getDataFimViagem());
			triggerRes.setTr13IdBalancaSaida(interfaceSgf.getIdBalancaSaida());	
		//}
		
		
		if (config.isEventoGruaPatioStandyBy(truck.getEventCode())) {
			triggerRes.setTr15ApontamentoStandBy(new ApontamentoDto(truck));
			triggerRes.setTr15StandBy(truck.getEventDateTime());
		}
		
		/*
		if (config.isApontamentoQuadra(caminhao.getEventCode())) {
			trigger.setTr16ApontamentoQuadra(new ApontamentoDto(caminhao));
			trigger.setTr16DataQuadra(caminhao.getEventDateTime());
		}
		*/
		if (isDataChange) {
			caminhoesService.salvar(truck);
		}
		
		return triggerRes;
	}

	private void factoryStages(CaminhaoEntity caminhao, Boolean isStayInFactory) {		
		
		
		TriggerEventosDto triggerEvents = updateTriggerEvents(caminhao);
		
		
		Pair<EstadoOperacional, EstagioOperacional> statePair = triggerEvents.getStateStoreResult(isStayInFactory);
		
		if (caminhao.getName().contains("NONE")) {
			if (triggerEvents.getOtSituacao() == SituacaoOrdemTransporte.LIBERADA || statePair != null) {
				logger.info("");
				logger.info("--------------------------------------------");
				logger.info("Caminhao:" + caminhao.getName());
				logger.info("estado si atual:" + triggerEvents.getEstadoSite().name());
				logger.info("estado carreg:" + triggerEvents.getEstadoCarreg());
				logger.info("ot:" + triggerEvents.getOtNumero());
				logger.info("ot sit:" + triggerEvents.getOtSituacao());
				logger.info("isStayFactory: " + isStayInFactory);
				logger.info("01 - Dt sentido farbrica:" + DateUtils.formatDateDef(triggerEvents.getTr01DataCercaSF()));
				logger.info("02 - Dt acesso entrada:" + DateUtils.formatDateDef(triggerEvents.getTr02DataCercaAE()));
				logger.info("03 - Dt apontamento FB:" + DateUtils.formatDateDef(triggerEvents.getTr03dataAptFB()));
				logger.info("04 - Dt recebimento:" + DateUtils.formatDateDef(triggerEvents.getTr04DataRecebimentoBE()));
				logger.info("04 - Dt reprogramação:" + DateUtils.formatDateDef(triggerEvents.getTr05DataReprogramacaoFI()));
				logger.info("05 - Dt mesa:" + DateUtils.formatDateDef(triggerEvents.getTr06DataMesa()));
				logger.info("05 - Dt quadra:" + DateUtils.formatDateDef(triggerEvents.getTr16DataQuadra()));
				logger.info("06 - Dt fim descarga:" + DateUtils.formatDateDef(triggerEvents.getTr14DataFimDescarga()));
				logger.info("07 - Dt cerca saida:" + DateUtils.formatDateDef(triggerEvents.getTr11DataCercaCS()));
				logger.info("08 - Dt varrição:" + DateUtils.formatDateDef(triggerEvents.getTr12DataVarricao()));
				logger.info("09 - Dt fim viagem:" + DateUtils.formatDateDef(triggerEvents.getTr13DataFimViagem()));
				if (statePair == null) {
					logger.info("Estado: null");
				} else {
					logger.info("Estado: " + statePair.getLeft());
					logger.info("Estagio: " + statePair.getRight());	
				}
				logger.info("--------------------------------------------");
				logger.info("");
			} else {
				logger.info("==>" + caminhao.getName() + " - Está na Fabrica:" + isStayInFactory);
			}
		}
		
		if (statePair != null && statePair.getLeft() != null && statePair.getLeft().getFluxoOperacional() == FluxoOperacional.SITE_INDUSTRIAL) {			
			setStateStoreRepository(statePair, caminhao);
		} else {
			setTranspSemOT(caminhao);
		}
	}

	private void setTranspSemOT(CaminhaoEntity caminhao) {
		caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_VIAGEM, caminhao,  new Date());
		caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL,  EstadoOperacional.SI_VIAGEM, caminhao,  new Date());
	}

	private void setStateStoreRepository(Pair<EstadoOperacional, EstagioOperacional> statePair, CaminhaoEntity caminhao) {
		caminhoesService.trocaEstado(FluxoOperacional.TRANSPORTE, EstadoOperacional.TRANSP_FABRICA, caminhao,  new Date());
		if (statePair.getLeft() == EstadoOperacional.SI_FILA) {
			caminhoesService.trocaEstado(FluxoOperacional.CARREGAMENTO, EstadoOperacional.CARREG_CHEIO, caminhao,  new Date());
		}
		if (statePair.getRight() != null) {
			caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL,  statePair.getLeft(), statePair.getRight(), caminhao,  new Date());
		} else {
			caminhoesService.trocaEstado(FluxoOperacional.SITE_INDUSTRIAL,  statePair.getLeft(), caminhao,  new Date());	
		}
	}
	

}

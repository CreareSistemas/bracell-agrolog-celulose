package br.com.crearesistemas.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.CaminhoesDAO;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.EstagioOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.DowntimeDto;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.CaminhaoSiteIndustrial;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.DadosService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.service.dto.ApontamentoDto;
import br.com.crearesistemas.sgf.BaseAuth;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.WsSetEquipmentDowntime;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;

@Service
public class CaminhoesServiceImpl extends ServiceImpl<CaminhoesDAO> implements CaminhoesService {
	Config config = Config.getInstance();
	
	
	private static final Logger logger = Logger.getLogger(CaminhoesServiceImpl.class);
	
	@Autowired  private DadosService dadosService;
	
	@Override
	@Resource(name = "caminhoesJdbcDAO")
	public void setDao(CaminhoesDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<CaminhaoEntity> selecionarCaminhoesTransporte(Long customerId) {
		 return getDao().selecionarCaminhoesTransporte(customerId);
	}
	
	
	@Override
	public List<CaminhaoEntity> selecionarCaminhoesTransporteL1(Long customerId) {
		 return getDao().selecionarCaminhoesTransporteL1(customerId, Config.LINE1_CODE);
	}
	
	@Override
	public List<CaminhaoEntity> selecionarCaminhoesTransporteL2(Long customerId) {
		 return getDao().selecionarCaminhoesTransporteL1(customerId, Config.LINE2_CODE);
	}
	
	@Override
	public void informaPatioInterno(GruaEntity grua, CaminhaoEntity caminhao, String tipReport) {
		getDao().salvar(caminhao);
	}
	
	@Override
	public void salvar(CaminhaoEntity caminhao) {
		getDao().salvar(caminhao);
	}
	
	@Override
	public Long selMaxEventos(Long vehicleId) {
		return getDao().selMaxEventos(vehicleId);
	}
	
	public Boolean incrementaEvento(Long vehicleId) {
		return getDao().incrementaEvento(vehicleId);
	}
	
	
	@Override
	public SgfTruck selecionarCaminhaoTransporte(String plates) {
		return getDao().selecionarCaminhaoTransporte(plates);
	}

	@Override
	public List<Object[]> selecionaPorFluxoSiteIndustrial(Date dataDe, Date dataAte) {
		if (dataDe != null && dataAte != null) {
			return getDao().selecionaPorFluxoSiteIndustrial(dataDe, dataAte);	
		}
		return null;
	}
	
	
	@Override
	public List<Object[]> selecionaPorFluxoSiteIndustrialL1(Date dataDe, Date dataAte) {
		if (dataDe != null && dataAte != null) {
			return getDao().selecionaPorFluxoSiteIndustrialL1(dataDe, dataAte);	
		}
		return null;
	}

	
	@Override
	public void openDowntime(CaminhaoEntity caminhao) {
		
		 
		
		
		DowntimeDto downtime;
		Boolean save = false;
		
		if (caminhao.getDowntime() != null) {
			if (!validOpenDowntime(caminhao)) {
				return;
			}
			 
			downtime = caminhao.getDowntime();
			
			if (downtime.getStartDowntime() == null) {
				startDowntime(caminhao, downtime);				
				save = true;
			}
			
			if (downtime.getEndDowntime() != null) {
				startDowntime(caminhao, downtime);
				save = true;
			}
			
			if (downtime.getSgfDowntime() == null 
					|| downtime.getSgfDowntime() != null && downtime.getSgfDowntime()) {
				startDowntime(caminhao, downtime);
				save = true;
			}
			
			
		} else {
			downtime = new DowntimeDto();
			startDowntime(caminhao, downtime);
			caminhao.setDowntime(downtime);
			save = true;
		}
		
		if (save) {
			getDao().salvar(caminhao);
		}
	}

	private boolean validOpenDowntime(CaminhaoEntity caminhao) {
		DowntimeDto downtime = caminhao.getDowntime();
		
		if (downtime != null) {
			Integer lastCode = downtime.getDowntimeCode();
			Integer currCode = caminhao.getDowntimeCode();
			
			if (lastCode != null && currCode != null && lastCode == currCode) {
				if (downtime.getRetries() < config.getLimitRetriesDowntimes()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Boolean closeDowntime(CaminhaoEntity caminhao) {
		DowntimeDto downtime;
		Boolean result = false;
		Boolean save = false;
		
		if (caminhao.getDowntime() != null) {
			String infoLog = new Gson().toJson(caminhao);
			downtime = caminhao.getDowntime();
			
			Boolean isValid = consisteEnvio(caminhao.getLastDateDowntime(), downtime);
			
			if (downtime.getEndDowntime() == null) {
				Date agora = new Date();
				if (downtime.getStartDowntime() != null) {
					if (downtime.getStartDowntime().after(agora)) {
						agora = DateUtils.somarSegundos(downtime.getStartDowntime(), 1);
						downtime.setEndDowntime(agora);	
					} else {
						downtime.setEndDowntime(agora);
					}						
				} else {
					downtime.setStartDowntime(agora);
					downtime.setEndDowntime(DateUtils.somarSegundos(agora, 1));
				}
				
				downtime.setEndHourmeter(caminhao.getHourmeter());
				save = true;
			}
			
			
						
			if (isValid && downtime.getSgfDowntime() != null 
					&& !downtime.getSgfDowntime()
					&& downtime.getRetries() < config.getLimitRetriesDowntimes()) {
				Integer retries = downtime.getRetries() + 1;
				downtime.setRetries(retries);
				if (caminhao.consisteLastDate(downtime.getEndDowntime())) {
					caminhao.setLastDateDowntime(downtime.getEndDowntime());
					getDao().salvar(caminhao);
				}
				
				if (sgfEnviarSetDowntime(caminhao, infoLog)) {
					downtime.setSgfDowntime(true);
					result = true;
				}
				save = true;				
			} else {
				result = true;
			}
			
			if (save) {
				getDao().salvar(caminhao);
			}	
		}
		
		return result;
	}
		
	private Boolean consisteEnvio(Date lastDate, DowntimeDto downtime) {
		
		if (lastDate != null && downtime != null && downtime.getStartDowntime() != null)  {
			if (lastDate.after(downtime.getStartDowntime())) {
				return false;
			} else {
				return true;
			}
		} else if (lastDate == null) {
			return true;
		}
		
		return false;
	}

	private void startDowntime(CaminhaoEntity caminhao, DowntimeDto downtime) {
		downtime.setEventId(caminhao.getEventReportId());
		downtime.setDowntimeCode(caminhao.getDowntimeCode());
		
		if (caminhao.getEventDateTime() != null) {
			downtime.setStartDowntime(DateUtils.somarSegundos(DateUtils.getLastDate(caminhao.getEventDateTime(), caminhao.getLastDateDowntime()),1));
		} else {
			downtime.setStartDowntime(DateUtils.somarSegundos(DateUtils.getLastDate(new Date(), caminhao.getLastDateDowntime()),1));	
		}		
		
		downtime.setEndDowntime(null);
		downtime.setSgfDowntime(false);
		downtime.setRetries(0);
		downtime.setOperatorName(caminhao.getOperatorName());
		downtime.setStartHourmeter(caminhao.getHourmeter());
	}

	

	public void setEstagioCaminhaoTransporte(CaminhaoEntity caminhao, CaminhaoSiteIndustrial caminhaoTransporte) {

		// ---------------------------------01 - FACTORY DIRECTION -------------------------------------
		if (caminhao.getSiteEstadoOperacional() == null) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_VIAGEM.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_VIAGEM);
			caminhaoTransporte.setIdLocal(null);
		} else
		if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_SENTIDO_FABRICA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_SENTIDO_FABRICA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_SENTIDO_FABRICA);
			caminhaoTransporte.setIdLocal(config.getLocalId01SentidoFabrica().longValue());
		
		// ---------------------------------02 - ACCESS ENTRANCE -------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_ACESSO_ENTRADA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_ACESSO_ENTRADA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_ACESSO_ENTRADA);
			caminhaoTransporte.setIdLocal(config.getLocalId02AcessoEntrada().longValue());
		
		// ---------------------------------03 - SCALE QUEUE ENTRANCE-------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_FILA_BALANCA_ENTRADA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_FILA_BALANCA_ENTRADA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_FILA_BALANCA_ENTRADA);
			caminhaoTransporte.setIdLocal(config.getLocalId03FilaBalancaEnt().longValue());
			caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());

				
		// ---------------------------------04 - SCALE ENTRANCE -------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_BALANCA_ENTRADAB.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_BALANCA_ENTRADAB.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_BALANCA_ENTRADAB);
			caminhaoTransporte.setIdLocal(config.getLocalId04BalancaEnt().longValue());

		// ---------------------------------05 - QUEUE FACTORY -------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_FILA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_FILA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_FILA);
			
			if (config.isLocalFilaFabrica(caminhao.getIdLocal())) {
				caminhaoTransporte.setIdLocal(caminhao.getIdLocal());
			} else {
				caminhaoTransporte.setIdLocal(config.getLocalFilaFabrica(EstagioOperacional.FILA_MESA1).longValue());	
			}
			caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());
			

		// ---------------------------------06 - CHIPPER-------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_MESAS.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_MESAS.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_MESAS);

			if (config.isLocalMesas(caminhao.getIdLocal())) {
				caminhaoTransporte.setIdLocal(caminhao.getIdLocal());
			} else {
				caminhaoTransporte.setIdLocal(config.getLocalMesa(EstagioOperacional.MESA_L1).longValue());	
			}
			
			
		// ---------------------------------11 - CHECKPOINT EXIT-------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_CHECKPOINT_SAIDA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_CHECKPOINT_SAIDA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_CHECKPOINT_SAIDA);
			caminhaoTransporte.setIdLocal(config.getLocalId11CheckpointSaida().longValue());
			caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());

		// ---------------------------------12 - SWEEPING -------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_VARRICAO.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_VARRICAO.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_VARRICAO);
			caminhaoTransporte.setIdLocal(config.getLocalId12Varricao().longValue());
			caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());

		// ---------------------------------13 - SCALE EXIT-------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_BALANCA_SAIDA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_BALANCA_SAIDA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_BALANCA_SAIDA);
			caminhaoTransporte.setIdLocal(config.getLocalId13BalancaSai().longValue());
			caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());			

		// ---------------------------------14 - SCALE QUEUE EXIT-------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_FILA_BALANCA_SAIDA.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_FILA_BALANCA_SAIDA.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_FILA_BALANCA_SAIDA);
			caminhaoTransporte.setIdLocal(config.getLocalId14FilaBalancaSai().longValue());
			caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());

		// ---------------------------------15 - STANDBY ASSETS -------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_STANDBY.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_STANDBY.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_STANDBY);
			caminhaoTransporte.setIdLocal(config.getLocalId15StandBy().longValue());

		// ---------------------------------16 - STOCKYARD-------------------------------------	
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_QUADRAS.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_QUADRAS.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_QUADRAS);
			
			if (config.isLocalQuadras(caminhao.getIdLocal())) {
				caminhaoTransporte.setIdLocal(caminhao.getIdLocal());
			} else {
				caminhaoTransporte.setIdLocal(config.getLocalMesa(EstagioOperacional.QUADRA001).longValue());	
			}
		// ---------------------------------VIAGEM -------------------------------------
		} else if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_VIAGEM.ordinal()) {
			caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_VIAGEM.ordinal());
			caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_VIAGEM);
			caminhaoTransporte.setIdLocal(null);
		}

		
	}

	public Boolean isStayInState(CaminhaoEntity caminhao, List<EstadoOperacional> asList) {
		if (caminhao.getSiteEstadoOperacional() != null) {
			for (EstadoOperacional estado: asList) {
				if (caminhao.getSiteEstadoOperacional() == estado.ordinal()) {
					return true;
				}
			}
		}
		
		return false;
	}

	
	public void trocaEstado(FluxoOperacional fluxo, EstadoOperacional estado, EstagioOperacional estagio, CaminhaoEntity caminhao,
			Date dataTroca) {
		switch (fluxo) {
		case PROPRIO:
			break;
		case CARREGAMENTO:
			if (caminhao.getCarregEstadoOperacional() != null && caminhao.getCarregDate() != null && (caminhao.getCarregEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getCarregDate()))==false) {
				return;
			}
			caminhao.setCarregFluxo(fluxo.ordinal());
			caminhao.setCarregEstadoOperacional(estado.ordinal());
			caminhao.setCarregDate(dataTroca);
			getDao().trocaEstadoTransporteCarreg(caminhao, fluxo, estado, dataTroca);
			break;
		case TRANSPORTE:
			if (caminhao.getTranspEstadoOperacional() != null && caminhao.getTranspDate() != null && (caminhao.getTranspEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getTranspDate()))==false) {
				return;
			}
			caminhao.setTranspFluxo(fluxo.ordinal());
			caminhao.setTranspEstadoOperacional(estado.ordinal());
			caminhao.setTranspDate(dataTroca);
			getDao().trocaEstadoTransporte(caminhao, fluxo, estado, dataTroca);
			break;
		case SITE_INDUSTRIAL:
			if (caminhao.getSiteEstadoOperacional() != null && caminhao.getSiteDate() != null && (caminhao.getSiteEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getSiteDate()))==false) {
				return;
			}
			saveHistoricalState(fluxo, estado,caminhao.getSiteDate(), dataTroca, caminhao.getId(), caminhao.getLineId());
			if (estado == EstadoOperacional.SI_FILA) {
				caminhao.setSiteFilaDate(dataTroca);
			}
			caminhao.setSiteFluxo(fluxo.ordinal());
			caminhao.setSiteEstadoOperacional(estado.ordinal());
			caminhao.setSiteDate(dataTroca);
			setCaminhaoSite(caminhao, estado, estagio);
			getDao().trocaEstadoTransporteSite(caminhao, fluxo, estado, dataTroca);
			break;
		case PORTO_MARITIMO:				
			break;

		default:
			break;
		}
	}
	
	
	public void trocaEstado(FluxoOperacional fluxo, EstadoOperacional estado, CaminhaoEntity caminhao,
			Date dataTroca) {
		
		switch (fluxo) {
		case PROPRIO:
			break;
		case CARREGAMENTO:
			if (caminhao.getCarregEstadoOperacional() != null && caminhao.getCarregDate() != null && ( caminhao.getCarregEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getCarregDate()))==false) {
				return;
			}
			caminhao.setCarregFluxo(fluxo.ordinal());
			caminhao.setCarregEstadoOperacional(estado.ordinal());
			caminhao.setCarregDate(dataTroca);
			getDao().trocaEstadoTransporteCarreg(caminhao, fluxo, estado, dataTroca);
			break;
		case TRANSPORTE:
			if (caminhao.getTranspEstadoOperacional() != null && caminhao.getTranspDate() != null && (caminhao.getTranspEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getTranspDate()))==false) {
				return;
			}
			caminhao.setTranspFluxo(fluxo.ordinal());
			caminhao.setTranspEstadoOperacional(estado.ordinal());
			caminhao.setTranspDate(dataTroca);
			getDao().trocaEstadoTransporte(caminhao, fluxo, estado, dataTroca);
			break;
		case SITE_INDUSTRIAL:
			if (caminhao.getSiteEstadoOperacional() != null &&  caminhao.getSiteDate() != null && (caminhao.getSiteEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getSiteDate()))==false) {
				return;
			}
			saveHistoricalState(fluxo, estado,caminhao.getSiteDate(), dataTroca, caminhao.getId(), caminhao.getLineId());
			if (estado == EstadoOperacional.SI_FILA) {
				caminhao.setSiteFilaDate(dataTroca);
			}
			caminhao.setSiteFluxo(fluxo.ordinal());
			caminhao.setSiteEstadoOperacional(estado.ordinal());
			caminhao.setSiteDate(dataTroca);
			setCaminhaoSite(caminhao, estado, EstagioOperacional.DESCONHECIDO);
			getDao().trocaEstadoTransporteSite(caminhao, fluxo, estado, dataTroca);
			break;
		case PORTO_MARITIMO:				
			break;

		default:
			break;
		}
	};
	
	@Override
	public void trocaEstado(FluxoOperacional fluxo, 
			EstadoOperacional estado, 
			CaminhaoEntity caminhao, 
			GruaEntity grua, Date dataTroca) {
		if (grua != null) {
			caminhao.setIdGrua(grua.getId());	
		} else {
			caminhao.setIdGrua(null);
		}
		
		switch (fluxo) {
		
		case PROPRIO:
			break;
		case CARREGAMENTO:
			
			if (caminhao.getCarregDate() != null && caminhao.getCarregEstadoOperacional() != null && (caminhao.getCarregEstadoOperacional().intValue() != estado.ordinal() 
					&& dataTroca.after(caminhao.getCarregDate()))==false) {
					return;	
			}
			
			
			caminhao.setCarregFluxo(fluxo.ordinal());
			caminhao.setCarregEstadoOperacional(estado.ordinal());
			caminhao.setCarregDate(dataTroca);
			getDao().trocaEstadoTransporteCarreg(caminhao, fluxo, estado, dataTroca);
			break;
		case TRANSPORTE:
			if (caminhao.getTranspDate() != null && caminhao.getTranspEstadoOperacional() != null && (caminhao.getTranspEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getTranspDate()))==false) {
				return;
			}
			caminhao.setTranspFluxo(fluxo.ordinal());
			caminhao.setTranspEstadoOperacional(estado.ordinal());
			caminhao.setTranspDate(dataTroca);
			
			if (estado != null && (estado == EstadoOperacional.TRANSP_CARREGAMENTO)) {
				if (caminhao.getName().contains("EFO4336")) {
					logger.info(caminhao.getName() + " está carregando na grua " + grua.getEventReportId());
				}
				caminhao.setEventLoadId(grua.getEventReportId()); // registra no caminhao o pk de carregamento da grua no campo
				caminhao.setCraneDateTime(grua.getEventDateTime());  // data inicial do carregamento no campo
				caminhao.setProjectName(grua.getProjectName()); // nome do projeto apontamento
				caminhao.setProject(grua.getProject()); // projectCode (registra o projeto)
				caminhao.setField(grua.getField()); // standId (Talhão)
				caminhao.setPlates(grua.getPlates()); // placa
				caminhao.setCraneOperatorName(grua.getOperatorName());
			}
			getDao().trocaEstadoTransporte(caminhao, fluxo, estado, dataTroca);
			break;
		case SITE_INDUSTRIAL:
			if (caminhao.getSiteDate() != null && caminhao.getSiteEstadoOperacional() != null && (caminhao.getSiteEstadoOperacional().intValue() != estado.ordinal() &&
					 dataTroca.after(caminhao.getSiteDate()))==false) {
				return;
			}
			saveHistoricalState(fluxo, estado,caminhao.getSiteDate(), dataTroca, caminhao.getId(), caminhao.getLineId() );
			if (estado == EstadoOperacional.SI_FILA) {
				caminhao.setSiteFilaDate(dataTroca);
			}
			caminhao.setSiteFluxo(fluxo.ordinal());
			caminhao.setSiteEstadoOperacional(estado.ordinal());
			caminhao.setSiteDate(dataTroca);
			
			setCaminhaoSite(caminhao, estado, EstagioOperacional.DESCONHECIDO);
			
			if (estado != null && (estado == EstadoOperacional.SI_FILA_BALANCA_SAIDA)) {
				caminhao.getTriggerSI().setTr14DataFimDescarga(dataTroca);
				caminhao.getTriggerSI().setTr14Grua(grua.getName());
			}
			
			
			if (estado != null && (estado == EstadoOperacional.SI_MESAS || estado == EstadoOperacional.SI_QUADRAS)) {
				
				if (estado == EstadoOperacional.SI_MESAS) {
					caminhao.getTriggerSI().setTr06ApontamentoMesa(new ApontamentoDto(grua));
					caminhao.getTriggerSI().setTr06DataMesa(grua.getEventdateTime());
					caminhao.getTriggerSI().setTr14DataFimDescarga(null);
					caminhao.getTriggerSI().setTr14Grua(null);
				}
				
				if (estado == EstadoOperacional.SI_QUADRAS) {
					caminhao.getTriggerSI().setTr16ApontamentoQuadra(new ApontamentoDto(grua));
					caminhao.getTriggerSI().setTr16DataQuadra(grua.getEventdateTime());
					caminhao.getTriggerSI().setTr14DataFimDescarga(null);
					caminhao.getTriggerSI().setTr14Grua(null);
				}
				
				
				// gravar local
				caminhao.setIdLocal(grua.getIdLocal());
				caminhao.setLoadType(grua.getLoadType());
				caminhao.setPlates(grua.getPlates());
				
				// gravar dados do carregamento
				caminhao.setCraneDateTime(grua.getEventDateTime());  // data inicial do carregamento no patio
				caminhao.setPile(grua.getPile());
				caminhao.setChipper(grua.getChipper());
				caminhao.setLocale(grua.getLocale()); // locale code do tablete
				caminhao.setCraneOperatorName(grua.getOperatorName());
			}
			
			getDao().trocaEstadoTransporteSite(caminhao, fluxo, estado, dataTroca);
			break;
		case PORTO_MARITIMO:				
			break;

		default:
			break;
		}
	}
	
	private void saveHistoricalState(FluxoOperacional fluxo, EstadoOperacional estado, Date dataInicio, Date dataFim, Long assetId, Integer lineId) {
		getDao().saveHistoricalState(fluxo, estado, dataInicio, dataFim, assetId, lineId);
	}

	private void setCaminhaoSite(CaminhaoEntity caminhaoTransporte, EstadoOperacional estado, EstagioOperacional estagio) {
		// 01
		if (estado == EstadoOperacional.SI_SENTIDO_FABRICA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_SENTIDO_FABRICA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId01SentidoFabrica().longValue());
		// 02	
		} else if (estado == EstadoOperacional.SI_ACESSO_ENTRADA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_ACESSO_ENTRADA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId02AcessoEntrada().longValue());
			
		} else if (estado == EstadoOperacional.SI_FILA_BALANCA_ENTRADA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_FILA_BALANCA_ENTRADA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId03FilaBalancaEnt().longValue());
			
		} else if (estado == EstadoOperacional.SI_BALANCA_ENTRADAB) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_BALANCA_ENTRADAB.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId04BalancaEnt().longValue());
		
		} else if (estado == EstadoOperacional.SI_FILA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_FILA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalFilaFabrica(estagio).longValue());
	
		} else if (estado== EstadoOperacional.SI_MESAS) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_MESAS.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalMesa(estagio).longValue());

		} else if (estado == EstadoOperacional.SI_CHECKPOINT_SAIDA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_CHECKPOINT_SAIDA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId11CheckpointSaida().longValue());

		} else if (estado== EstadoOperacional.SI_VARRICAO) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_VARRICAO.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId12Varricao().longValue());

			
		} else if (estado == EstadoOperacional.SI_BALANCA_SAIDA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_BALANCA_SAIDA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId13BalancaSai().longValue());
			
		} else if (estado == EstadoOperacional.SI_FILA_BALANCA_SAIDA) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_FILA_BALANCA_SAIDA.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId14FilaBalancaSai().longValue());
			
		} else if (estado == EstadoOperacional.SI_STANDBY) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_STANDBY.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalId15StandBy().longValue());
			
		}  else if (estado == EstadoOperacional.SI_QUADRAS) {
			caminhaoTransporte.setSiteEstadoOperacional(EstadoOperacional.SI_QUADRAS.ordinal());
			caminhaoTransporte.setIdLocal(config.getLocalQuadra(estagio).longValue());
		}
	}

	

	private Boolean sgfEnviarSetDowntime(CaminhaoEntity caminhao, String infoLog) {
		SetEquipmentDowntime downtime = new SetEquipmentDowntime();
		
		// parametros opcionais
		downtime.setLocaleCode(0l);
		downtime.setStartingHorimeterNumber(0d);
		downtime.setEndingHorimeterNumber(0d);
		
		/*
		 
		 Indica se é uma parada de 
		 	- Caminhão (“E”), 
		 	- Grua de Pátio (“P”) ou 
		 	- Grua de Campo (”C”).
		 
		 */
		
		downtime.setTipReport("E");
		
		SgfTruck sgfTruck = selecionarCaminhaoTransporte(caminhao.getName());
		
		if (sgfTruck != null) {
			downtime.setEquipmentPlate(sgfTruck.getTruckPlate());
		} else {
			downtime.setEquipmentPlate(caminhao.getName());
		}
		
		downtime.setOperatorName(caminhao.getOperatorName());
		if (caminhao.getDowntime() != null) {
			downtime.setStartDownTime(
					DateUtils.formatDate(caminhao.getDowntime().getStartDowntime())
				);
			
			downtime.setEndDownTime(
					DateUtils.formatDate(caminhao.getDowntime().getEndDowntime())
				);
			
			downtime.setDowntimeCode(caminhao.getDowntime().getDowntimeCode());
			
			if (caminhao.getDowntime().getOperatorName() != null) {
				downtime.setOperatorName(caminhao.getDowntime().getOperatorName());	
			}
			
			
			if (caminhao.getDowntime().getStartHourmeter() != null 
					&& caminhao.getDowntime().getEndHourmeter() != null) {
				//double duracao = (double)duracaoEmSegundos.getSeconds() / 3600;
				//DecimalFormat decimalFormat = new DecimalFormat("0.00");
				//setDescargaPatio.setQtdDuracao(decimalFormat.format(duracao).replace(",", "."));

				
				downtime.setStartingHorimeterNumber(
						NumberUtils.round(caminhao.getDowntime().getStartHourmeter().doubleValue(), 2)
				);
				downtime.setEndingHorimeterNumber(
						NumberUtils.round(caminhao.getDowntime().getEndHourmeter().doubleValue(), 2)
				);
			}
		}
		
		
		
		Integer returnId = enviarWsSetEquipmentDowntime(caminhao, downtime, infoLog);
			
		return returnId > -1;
	}

	private Integer enviarWsSetEquipmentDowntime(CaminhaoEntity caminhao, SetEquipmentDowntime downtime, String infoLog) {
		Integer returnId = null;
		if (config.printLogDowntimeSgf()) {
			logger.info("============================ Dados Paradas (Downtime) Sgf: ========================= " );
			logger.info("url: " + config.getSgfUrlBase() );
			logger.info("url: " + BaseAuth.followRedirectURL(config.getSgfUrlBase()) );		
			logger.info("EquipmentPlate: " + downtime.getEquipmentPlate() );
			logger.info("OperatorName: " + downtime.getOperatorName() );
			logger.info("TipReport: " + downtime.getTipReport() );
			logger.info("DowntimeCode: " + downtime.getDowntimeCode() );
			logger.info("StartingHorimeter: " + downtime.getStartingHorimeterNumber() );
			logger.info("EndingHorimeter: " + downtime.getEndingHorimeterNumber() );
			logger.info("StartDownTime: " + downtime.getStartDownTime() );
			logger.info("EndDownTime: " + downtime.getEndDownTime() );
			logger.info("LocaleCode: " + downtime.getLocaleCode() );
		}
		
		
		SetEquipmentDowntimeResponse response = WsSetEquipmentDowntime.setEquipmentDowntime(
				config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret(),
				downtime);
		
		
				
		if (response != null) {
			if (config.printLogDowntimeSgf()) {
				logger.info(" **************************** Result Sgf (Downtime): *********************************** ");
				logger.info(" ReturnId: " + response.getReturnId());
				System.out.println("ReturnId: " 
						+ response.getReturnId() 
						+ " \n Message:" 
						+ ((response.getReturnMessage() != null)?response.getReturnMessage()[0]:""));	
			}
			returnId = NumberUtils.parseInt(response.getReturnId());				
		}
		
		if (returnId == null) {
			returnId = -1;
			dadosService.registrarStatusParada(caminhao.getDowntime().getEventId(), downtime, response, infoLog, false);
		} else if (returnId > 0) {
			dadosService.registrarStatusParada(caminhao.getDowntime().getEventId(), downtime, response, infoLog, true);
		} else {
			dadosService.registrarStatusParada(caminhao.getDowntime().getEventId(), downtime, response, infoLog, false);
		}
		
		return returnId;		
	}

	

	
}

package br.com.crearesistemas.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.PgCaminhoesDAO;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.enumeration.InterfaceSgf;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.DowntimeDto;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.service.DadosService;
import br.com.crearesistemas.service.PgCaminhoesService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.OrdemTransporteDto;
import br.com.crearesistemas.sgf.BaseAuth;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.WsSetEquipmentDowntime;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;

@Service
public class PgCaminhoesServiceImpl extends ServiceImpl<PgCaminhoesDAO> implements PgCaminhoesService {
	Config config = Config.getInstance();
	
	
	private static final Logger logger = Logger.getLogger(PgCaminhoesServiceImpl.class);
	
	@Autowired  private DadosService dadosService;
	
	@Override
	@Resource(name = "pgCaminhoesJdbcDAO")
	public void setDao(PgCaminhoesDAO dao) {
		super.setDao(dao);
	}
	
	@Override
	public SgfTruck selecionarSgfTruck(String plates) {
		return getDao().selecionarCaminhaoTransporte(plates);
	}


	@Override
	public OrdemTransporteDto selecionarOrdemSgf(String plates) {
		return getDao().selecionarOrdemSgf(plates);
	}
	

	@Override
	public InterfaceSgfDto selecionarInterfaceSgf(String plates) {
		InterfaceSgf interf = InterfaceSgf.NENHUM;
		InterfaceSgfDto interfaceSgf =  getDao().selecionarInterfaceSgf(plates);
		Date dataAnterior = null;
		
		if (interfaceSgf.getDataReprogramacao() != null) {
			interf = InterfaceSgf.INT35_REPROGRAMACAO;
			dataAnterior = interfaceSgf.getDataReprogramacao();
		}
		 
		if (interfaceSgf.getDataRecebimento() != null) {			
			if (dataAnterior != null && dataAnterior.after(interfaceSgf.getDataRecebimento())) {
				// nada a fazer
			} else {
				interf = InterfaceSgf.INT39_RECEBIMENTO;
				dataAnterior = interfaceSgf.getDataRecebimento();
			}			
		}
		
		if (interfaceSgf.getDataFimViagem() != null) {
			if (dataAnterior != null && dataAnterior.after(interfaceSgf.getDataFimViagem())) {
				// nada a fazer
			} else {
				interf = InterfaceSgf.INT40_FIMVIAGEM;
				dataAnterior = interfaceSgf.getDataFimViagem();
			}
		}
		
		
		interfaceSgf.setInterfaceTipo(interf);
		
		
		return interfaceSgf;
	}

	@Override
	public Boolean closeOrder(CaminhaoEntity caminhao) {
		return getDao().closeOrder(caminhao);
	}
	
	@Override
	public Boolean closeOlderOrder() {
		return getDao().closeOlderOrder();
	}
	
	@Override
	public CaminhaoTransporte selectPgCaminhao(String name) {
		return getDao().selectPgCaminhao(name);
	}

	@Override
	public CaminhaoTransporte selectPgCaminhaoByName(String name) {
		return getDao().selectPgCaminhaoByName(name);
	}

	@Override
	public List<CaminhaoTransporte> selecionarCaminhoesTransporte(Long customerId) {
		 return getDao().selecionarCaminhoesTransporte(customerId);
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
	public void openDowntime(CaminhaoEntity caminhao) {
		DowntimeDto downtime;
		Boolean save = false;
		
		if (caminhao.getDowntime() != null) {
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

	@Override
	public void trocaEstado(FluxoOperacional fluxo, 
			EstadoOperacional estado, 
			CaminhaoEntity caminhao, 
			GruaEntity grua, Date date) {
		if (grua != null) {
			caminhao.setIdGrua(grua.getId());	
		} else {
			caminhao.setIdGrua(null);
		}
		
		switch (fluxo) {
		case PROPRIO:
			break;
		case CARREGAMENTO:
			caminhao.setCarregFluxo(fluxo.ordinal());
			caminhao.setCarregEstadoOperacional(estado.ordinal());
			getDao().trocaEstadoTransporteCarreg(caminhao, fluxo, estado, date);
			break;
		case TRANSPORTE:
			caminhao.setTranspFluxo(fluxo.ordinal());
			caminhao.setTranspEstadoOperacional(estado.ordinal());
			
			
			if (estado != null && (estado == EstadoOperacional.TRANSP_CARREGAMENTO)) {
				caminhao.setEventInternalId(grua.getEventReportId()); // registra no caminhao o pk de carregamento da grua no campo
				caminhao.setCraneDateTime(grua.getEventDateTime());  // data inicial do carregamento no campo
				caminhao.setProject(grua.getProject()); // registra o projeto
				caminhao.setField(grua.getField());
				caminhao.setPlates(grua.getPlates());
				caminhao.setCraneOperatorName(grua.getOperatorName());
			}
			getDao().trocaEstadoTransporte(caminhao, fluxo, estado, date);
			break;
		case SITE_INDUSTRIAL:
			caminhao.setSiteFluxo(fluxo.ordinal());
			caminhao.setSiteEstadoOperacional(estado.ordinal());
			
			if (estado != null && (estado == EstadoOperacional.SI_MESAS || estado == EstadoOperacional.SI_QUADRAS)) {
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
			
			getDao().trocaEstadoTransporteSite(caminhao, fluxo, estado, date);
			break;
		case PORTO_MARITIMO:				
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("unused")
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
		
		SgfTruck sgfTruck = selecionarSgfTruck(caminhao.getName());
		
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
		Integer returnId;
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
		
		
		SetEquipmentDowntimeResponse response = WsSetEquipmentDowntime.setEquipmentDowntime(
				config.getSgfUrlBase(),
				config.getSgfAppId(),
				config.getSgfSecret(),
				downtime);
		
		
		logger.info(" **************************** Result Sgf (Downtime): *********************************** ");
		logger.info(" ReturnId: " + response.getReturnId());
		System.out.println("ReturnId: " 
				+ response.getReturnId() 
				+ " \n Message:" 
				+ ((response.getReturnMessage() != null)?response.getReturnMessage()[0]:""));
		
		returnId = NumberUtils.parseInt(response.getReturnId());
		
		if (returnId == null) {
			returnId = -1;
			dadosService.registrarStatusParada(caminhao.getDowntime().getEventId(), downtime,response, infoLog, false);
		} else if (returnId > 0) {
			dadosService.registrarStatusParada(caminhao.getDowntime().getEventId(), downtime,response, infoLog, true);
		} else {
			dadosService.registrarStatusParada(caminhao.getDowntime().getEventId(), downtime,response, infoLog, false);
		}
		
		return returnId;		
	}





	
}

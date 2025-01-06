package br.com.crearesistemas.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.GruasDAO;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.DowntimeDto;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.service.DadosService;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.WsSetEquipmentDowntime;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;

@Service
public class GruasServiceImpl extends ServiceImpl<GruasDAO> implements GruasService {
	private static final Logger logger = Logger.getLogger(GruasServiceImpl.class);
	
	
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	
	@Autowired  private DadosService dadosService;
	
	@Override
	@Resource(name = "gruasJdbcDAO")
	public void setDao(GruasDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<GruaEntity> selecionarGruasFlorestais(Long customerId) {		
		return getDao().selecionarGruasFlorestais(customerId);		
	}

	@Override
	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId) {
		return getDao().selecionarGruasSiteIndustrial(customerId);
	}
	
	@Override
	public List<GruaEntity> selecionarGruasSiteIndustrialL1(Long customerId) {
		return getDao().selecionarGruasSiteIndustrialL1(customerId, Config.LINE1_CODE);
	}
	
	
	@Override
	public List<GruaEntity> selecionarGruasSiteIndustrialL2(Long customerId) {
		return getDao().selecionarGruasSiteIndustrialL1(customerId, Config.LINE2_CODE);
	}

	@Override
	public Boolean salvarGrua(GruaEntity grua) {
		return getDao().salvarGrua(grua);
	}

	
	@Override
	public Boolean closeDowntime(GruaEntity grua) {
		DowntimeDto downtime;
		Boolean result = false;
		Boolean save = false;
		
		if (grua.getDowntime() != null) {
			String logInfo =  new Gson().toJson(grua);
			downtime = grua.getDowntime();
			
			Boolean isValid = consisteEnvio(grua.getLastDateDowntime(), downtime);
			
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
					downtime.setEndDowntime(DateUtils.somarSegundos(downtime.getStartDowntime(), 1));
				}
				
				
								
				downtime.setEndHourmeter(grua.getHourmeter());
				save = true;
			}
			
			if (isValid && downtime.getSgfDowntime() != null 
					&& !downtime.getSgfDowntime()
					&& downtime.getRetries() < 1) {
				Integer retries = downtime.getRetries() + 1;
				downtime.setRetries(retries);
				if (grua.consisteLastDate(downtime.getEndDowntime())) {
					grua.setLastDateDowntime(downtime.getEndDowntime());
					getDao().salvar(grua);
				}
				SgfCrane sgfCrane = selecionarSgfCrane(grua.getName());
				if (sgfEnviarSetDowntime(grua, sgfCrane, logInfo)) {
					downtime.setSgfDowntime(true);
					result = true;
				} 
				
				save = true;				
			} else {
				result = true;
			}
			
			if (save) {
				getDao().salvar(grua);
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


	@Override
	public void openDowntime(GruaEntity grua) {
		DowntimeDto downtime;
		Boolean save = false;
		
		if (grua.getDowntime() != null) {
			if (!validOpenDowntime(grua)) {
				return;
			}
			
			downtime = grua.getDowntime();
			
			if (downtime.getStartDowntime() == null) {
				startDowntime(grua, downtime);				
				save = true;
			}
			
			if (downtime.getEndDowntime() != null) {
				startDowntime(grua, downtime);
				save = true;
			}
			
			if (downtime.getSgfDowntime() == null 
					|| downtime.getSgfDowntime() != null && downtime.getSgfDowntime()) {
				startDowntime(grua, downtime);
				save = true;
			}
			
			
		} else {
			downtime = new DowntimeDto();
			startDowntime(grua, downtime);
			grua.setDowntime(downtime);
			save = true;
		}
		
		if (save) {
			getDao().salvar(grua);
		}
	}
	
	private boolean validOpenDowntime(GruaEntity grua) {
		DowntimeDto downtime = grua.getDowntime();
		
		if (downtime != null) {
			Integer lastCode = downtime.getDowntimeCode();
			Integer currCode = grua.getDowntimeCode();
			
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
	
	private void startDowntime(GruaEntity grua, DowntimeDto downtime) {
		downtime.setEventId(grua.getEventReportId());
		downtime.setDowntimeCode(grua.getDowntimeCode());
		
		if (grua.getEventDateTime() != null) {
			downtime.setStartDowntime(DateUtils.somarSegundos(DateUtils.getLastDate(grua.getEventDateTime(), grua.getLastDateDowntime()),1));
		} else {
			downtime.setStartDowntime(DateUtils.somarSegundos(DateUtils.getLastDate(new Date(), grua.getLastDateDowntime()),1));	
		}
		
		downtime.setEndDowntime(null);
		downtime.setSgfDowntime(false);
		downtime.setRetries(0);
		downtime.setOperatorName(grua.getOperatorName());
		downtime.setStartHourmeter(grua.getHourmeter());
	}
	
	
	private SgfCrane selecionarSgfCrane(String plates) {
		return getDao().selecionarSgfCrane(plates);
	}

	// enviar parada da grua de patio ou campo
	public  Boolean sgfEnviarSetDowntime(GruaEntity grua, SgfCrane sgfCrane, String infoLog) {
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
			
			downtime.setTipReport("P");
			
			
			
			if (sgfCrane != null) {
				downtime.setEquipmentPlate(sgfCrane.getAbbreviation());
				
				Integer type = NumberUtils.parseInt(sgfCrane.getCraneType());
				
				if (type != null) {
					if (type == 1) {
						downtime.setTipReport("C");
					}
				}
				
			} else {
				downtime.setEquipmentPlate(grua.getName());
			}
			
			downtime.setOperatorName(grua.getOperatorName());
			if (grua.getDowntime() != null) {
				downtime.setStartDownTime(
						DateUtils.formatDate(grua.getDowntime().getStartDowntime())
					);
				
				downtime.setEndDownTime(
						DateUtils.formatDate(grua.getDowntime().getEndDowntime())
					);
				
				downtime.setDowntimeCode(grua.getDowntime().getDowntimeCode());
				
				if (grua.getDowntime().getOperatorName() != null) {
					downtime.setOperatorName(grua.getDowntime().getOperatorName());	
				}
				
				
				if (grua.getDowntime().getStartHourmeter() != null 
						&& grua.getDowntime().getEndHourmeter() != null) {
					
					downtime.setStartingHorimeterNumber(
							NumberUtils.round(grua.getDowntime().getStartHourmeter().doubleValue(), 2)
					);
					downtime.setEndingHorimeterNumber(
							NumberUtils.round(grua.getDowntime().getEndHourmeter().doubleValue(), 2)
					);
				}
			}
			
			
			
			Integer returnId = enviarWsSetEquipmentDowntime(grua, downtime, infoLog);
				
			return returnId > -1;
		}

			
		private  Integer enviarWsSetEquipmentDowntime(GruaEntity grua, SetEquipmentDowntime downtime, String infoLog) {
			Integer returnId = null;
			if (config.printLogDowntimeSgf()) {
				logger.info("============================ Dados Paradas (Downtime) Sgf: ========================= " );
				logger.info("url: " + config.getSgfUrlBase() );
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
				dadosService.registrarStatusParada(grua.getDowntime().getEventId(), downtime, response, infoLog, false);
			} else if (returnId > 0) {
				dadosService.registrarStatusParada(grua.getDowntime().getEventId(), downtime, response, infoLog, true);
			} else {
				dadosService.registrarStatusParada(grua.getDowntime().getEventId(), downtime, response, infoLog, false);
			}
			
			return returnId;		
		}
}

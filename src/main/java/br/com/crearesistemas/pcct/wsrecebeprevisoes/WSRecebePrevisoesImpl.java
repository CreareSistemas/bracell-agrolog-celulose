package br.com.crearesistemas.pcct.wsrecebeprevisoes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.pcct.wsrecebeprevisoes.dto.Previsao;
import br.com.crearesistemas.pcct.wsrecebeprevisoes.dto.RecebePrevisoes;
import br.com.crearesistemas.pcct.wsrecebeprevisoes.dto.RecebePrevisoesResponse;
import br.com.crearesistemas.service.PgOrdemTransporteService;


@WebService(serviceName = "PCCT/WSRecebePrevisoes", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebePrevisoesImpl extends WSBase implements WSRecebePrevisoes {
	Config config = Config.getInstance();
	
	private static final Logger logger = Logger.getLogger(WSRecebePrevisoesImpl.class);

	private static final String IDENTIFICACAO_HORAS = "%tk~%tk"; // exemplo: 21~22
	private static final int HORAS_NO_PASSADO = 0;
	private static final int HORAS_NO_FUTURO = 23;
	//private static final int HORAS_BASE = 0;
	private int HORAS_BASE = 0;

	@Resource
	private PgOrdemTransporteService pgOtService;


	public RecebePrevisoesResponse recebePrevisoes(
			RecebePrevisoes recebePrevisoes, Credencial credencial) {
		RecebePrevisoesResponse response = new RecebePrevisoesResponse();
		try {
			DateTime base = new DateTime(truncHour(recebePrevisoes.getDataBase()));
			
			List<Previsao> previsoes;
				previsoes = selecionaPrevisoes(base, recebePrevisoes.getIdLocalDeDescarga());
			
			response.setListaDePrevisoes(previsoes);
			response.setErro(STATUS_NENHUM_ERRO_ENCONTRADO);
		} catch (Exception e) {
			response.setErro(STATUS_ERRO);
			response.setMensagem(e.getMessage());
			logger.error("Erro ", e);
		}
		return response;
	}
	
	private Date truncHour(Date dataBase) {
		return DateUtils.truncate(dataBase, Calendar.HOUR_OF_DAY);
	}

	private List<Previsao> selecionaPrevisoes(DateTime base, String idLocalDeDescarga) {
		List<CaminhaoTransporte> caminhoes = config.getCaminhaoTransp();
		Boolean optCaminhoes = true;
		List<Previsao> listaDePrevisoes = new ArrayList<Previsao>();
		HORAS_BASE = base.getHourOfDay();
		for (int hours = HORAS_NO_PASSADO; hours <= HORAS_NO_FUTURO; hours++) {
			DateTime min = new DateTime(base.getYear(), base.getMonthOfYear(), base.getDayOfMonth(),hours,0,0);
			DateTime maxExcl = min.plusHours(1);
			DateTime max = maxExcl.plusMillis(-1);
			Previsao previsao = new Previsao();
			previsao.setIndexador((long) hours);
			previsao.setIdentificacao(String.format(IDENTIFICACAO_HORAS, min.toDate(), maxExcl.toDate()));
			if (hours > HORAS_BASE - 1) {
				if (idLocalDeDescarga == null){
					idLocalDeDescarga = config.getLocalIdFabricaDescarga(); // fabrica (contingencia)	
				}
				
				if (optCaminhoes) {
					Long ots = pgOtService.selecionaQuantidadePorDataEstimadaChegada(min.toDate(), max.toDate(), idLocalDeDescarga);
					ots = ots + previsto(caminhoes, min.toDate(), max.toDate());
					previsao.setPrevisto(ots);   // roxo (estimado)
				} else {
					previsao.setPrevisto(pgOtService.selecionaQuantidadePorDataEstimadaChegada(min.toDate(), max.toDate(), idLocalDeDescarga));   // roxo (estimado)	
				}
				
				if (hours == HORAS_BASE) {
					if (optCaminhoes) {
						Long ots = pgOtService.selecionaQuantidadePorDataRecebimento(min.toDate(), max.toDate(), idLocalDeDescarga);
						ots = ots + realizado(caminhoes, min.toDate(), max.toDate());
						previsao.setRealizado(ots); // verde (realizado)
					} else {
						previsao.setRealizado(pgOtService.selecionaQuantidadePorDataRecebimento(min.toDate(), max.toDate(), idLocalDeDescarga));	
					}
					
				}
			}else{
				if (optCaminhoes) {
					Long ots = pgOtService.selecionaQuantidadePorDataRecebimento(min.toDate(), max.toDate(), idLocalDeDescarga);
					ots = ots + realizado(caminhoes, min.toDate(), max.toDate());
					previsao.setRealizado(ots);      // verde (realizado)
				} else {
					previsao.setRealizado(pgOtService.selecionaQuantidadePorDataRecebimento(min.toDate(), max.toDate(), idLocalDeDescarga));      // verde (realizado)	
				}				
			}

			// considerar o modo de programacao das ots
			if (optCaminhoes) {
				Long ots = pgOtService.selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(min.toDate(), max.toDate());
				ots = ots + programado(caminhoes, min.toDate(), max.toDate());
				previsao.setProgramado(ots); // barra azul (programado)	
			} else {
				previsao.setProgramado(pgOtService.selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(min.toDate(), max.toDate())); // barra azul (programado)
			}
			
			listaDePrevisoes.add(previsao);
		}
		return listaDePrevisoes;
	}

	private Long programado(List<CaminhaoTransporte> caminhoes, Date dataInicio, Date dataFim) {
		Long result = 0l;
		if (caminhoes != null) {
			for (CaminhaoTransporte caminhao: caminhoes) {
				if (caminhao.getDataPartidaPcct() != null) {
					if (br.com.crearesistemas.util.DateUtils.estaEntreDatas(dataInicio, dataFim, caminhao.getDataPartidaPcct())){
						result++;
					}	
				}
			}	
		}
		
		return result;
	}
	
	

	private Long realizado(List<CaminhaoTransporte> caminhoes, Date dataInicio, Date dataFim) {
		Long result = 0l;
		return result;
	}

	private Long previsto(List<CaminhaoTransporte> caminhoes, Date dataInicio, Date dataFim) {
		Long result = 0l;
		if (caminhoes != null) {
			for (CaminhaoTransporte caminhao: caminhoes) {
				if (caminhao.getDataPartidaPcct() != null) {
					if (br.com.crearesistemas.util.DateUtils.estaEntreDatas(dataInicio, dataFim, caminhao.getDataPartidaPcct())){
						result++;
					}	
				}
			}	
		}
		return result;
	}

	
}

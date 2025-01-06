package br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.jboss.resteasy.util.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import br.com.crearesistemas.config.Logistica;
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.agrolog.OrdemTransporte;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.Acompanhamento;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.CicloRealizadoDto;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.RecebeAcompanhamentoTransporte;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.RecebeAcompanhamentoTransporteResponse;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.service.PgOrdemTransporteService;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;


@WebService(serviceName = "PCCT/WSRecebeAcompanhamentoTransporte", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeAcompanhamentoTransporteImpl extends WSBase implements WSRecebeAcompanhamentoTransporte {

	private static final Logger logger = Logger.getLogger(WSRecebeAcompanhamentoTransporteImpl.class);


	@Resource
	private PgOrdemTransporteService pgOrdemTransporteService;


	public RecebeAcompanhamentoTransporteResponse recebeAcompanhamentoTransporte(
			RecebeAcompanhamentoTransporte recebeAcompanhamentoTransporte, 
			Credencial credencial) {
		RecebeAcompanhamentoTransporteResponse response = new RecebeAcompanhamentoTransporteResponse();

		credencial.autenticacao();
		
		try {
			Date dateTime = new Date();
			logger.info("DA: "+ DateUtil.formatDate(dateTime));
			
			if (recebeAcompanhamentoTransporte.getDataBase() != null) {
				dateTime = recebeAcompanhamentoTransporte.getDataBase();
			}
			
			TreeMap<Pair<Long, Long>, Acompanhamento> mapaAcompanhamentos = new TreeMap<Pair<Long, Long>, Acompanhamento>();
			
			TreeMap<Long, CicloRealizadoDto> mapaCicloRealizado = new TreeMap<Long, CicloRealizadoDto>();

			logger.info("DI: "+ DateUtil.formatDate(Logistica.getDataInicio(dateTime)));
			logger.info("DF: "+ DateUtil.formatDate(Logistica.getDataFim(dateTime)));
			
			List<OrdemTransporte> listaDeOTs = pgOrdemTransporteService.selecionaPorDataPrevistaChegadaOUDataRecebimento(
					Logistica.getDataInicio(dateTime), Logistica.getDataFim(dateTime)
			);
			
			
			for (OrdemTransporte ot : listaDeOTs) {			
				mapShipmentTransport(dateTime, ot, mapaAcompanhamentos, mapaCicloRealizado);
			}
			
			for (OrdemTransporte ot : listaDeOTs) {			
				volumeExpected(dateTime, ot, mapaAcompanhamentos, mapaCicloRealizado);
			}
			
			
			// ----- PROGRAMADO -----
			mapaAcompanhamentos = pgAtualizaAcompanhamento(mapaAcompanhamentos);	
			
			
			for (Acompanhamento acompanhamento : mapaAcompanhamentos.values()) {
				// calcula medias
				
				Integer cicloRealizado = acompanhamento.getViagemConcluido()!=0?acompanhamento.getCicloRealizado() / acompanhamento.getViagemConcluido():0;
						
				acompanhamento.setCicloPrevisto(cicloRealizado);
				acompanhamento.setCicloRealizado(cicloRealizado);
				
				acompanhamento.setVolumeCaminhaoPrevisto(acompanhamento.getViagemPrevisto()!=0?acompanhamento.getVolumePrevisto() / acompanhamento.getViagemPrevisto():0);
				acompanhamento.setVolumeCaminhaoRealizado(acompanhamento.getViagemRealizado()!=0?acompanhamento.getVolumeRealizado() / acompanhamento.getViagemRealizado():0);
				
				acompanhamento.setPesoMadeira(acompanhamento.getPesoMadeira() / 1000);
				acompanhamento.setProjecaoFinalGrauAtendimento(acompanhamento.getVolumePrevisto()!=0?acompanhamento.getProjecaoFinalVolume() * 100 / acompanhamento.getVolumePrevisto():0);
			
				if (validaAcompanhamento(acompanhamento)) {
					response.getListaDeAcompanhamentos().add(acompanhamento);	
				}
				
			}
			
			config.setListAcompanhamentos(response.getListaDeAcompanhamentos());
			
        
			response.setErro(STATUS_NENHUM_ERRO_ENCONTRADO);
		} catch (Exception e) {
			response.setErro(STATUS_ERRO);
			response.setMensagem(e.getMessage());
			logger.error("Erro ", e);
		}
		
		return response;
	}


	private void volumeExpected(Date dateTime, OrdemTransporte ot,
			TreeMap<Pair<Long, Long>, Acompanhamento> mapaAcompanhamentos, 
			TreeMap<Long, CicloRealizadoDto> mapaCicloRealizado) {
		Acompanhamento acompanhamento = null;
		long prestadorId = config.getPrestador(ot.getIdPrestador()).longValue();
		long projetoId = config.getProjetoId(ot.getIdProjeto()).longValue();
		if (mapaAcompanhamentos.containsKey(Pair.of(projetoId, prestadorId))) {
			acompanhamento = mapaAcompanhamentos.get(Pair.of(projetoId, prestadorId));
		} else {
			acompanhamento = new Acompanhamento();
			acompanhamento.setIdProjeto(ot.getIdProjeto());
			acompanhamento.setProjeto(ot.getProjeto());
			acompanhamento.setIdPrestador(prestadorId);
			if (ot.getPrestador() == null) {
				acompanhamento.setPrestador(config.getCustomerName());
			} else {
				acompanhamento.setPrestador(ot.getPrestador());	
			}
			
			mapaAcompanhamentos.put(Pair.of(projetoId, prestadorId), acompanhamento);	
		}
		
		CicloRealizadoDto cicloRealizadoDto = null;
		if (mapaCicloRealizado.containsKey(projetoId)) {
			cicloRealizadoDto = mapaCicloRealizado.get(projetoId);
		} else {
			cicloRealizadoDto = new CicloRealizadoDto();
			cicloRealizadoDto.setIdProjeto(projetoId);
			mapaCicloRealizado.put(projetoId, cicloRealizadoDto);			
		}
		
		
		// ----- VOLUME PREVISTO -----		
		if (ot.getSituacao() != SituacaoOrdemTransporte.CANCELADA && ot.getSituacao() != SituacaoOrdemTransporte.INTERROMPIDA) {
			
			Date dataPrevistaChegada = null;
			if (ot.getDataLiberacao() != null && cicloRealizadoDto.getMediaCicloRealizadoSegundos() > 0) {
				dataPrevistaChegada = DateUtils.somarSegundos(ot.getDataLiberacao(), cicloRealizadoDto.getMediaCicloRealizadoSegundos()); 
			}
			
			if (dataPrevistaChegada != null) {
				if (DateUtils.estaEntreDatas(Logistica.getDataInicio(dateTime), Logistica.getDataFim(dateTime), dataPrevistaChegada)) {
					acompanhamento.somaViagemPrevisto(1);				
					if (cicloRealizadoDto.getMediaCicloRealizadoSegundos()>0) {					
						acompanhamento.somaCicloPrevisto(Seconds.secondsBetween(new DateTime(ot.getDataProgramadaPartida()), new DateTime(ot.getDataProgramadaChegada())).getSeconds());
					}
					if (ot.getVolumePrevisto() != null) {
						acompanhamento.somaVolumePrevisto(ot.getVolumePrevisto().intValue());					
					}					
				}	
			}
		}
		
	}


	private void mapShipmentTransport(Date dateTime, OrdemTransporte ot, TreeMap<Pair<Long, Long>, Acompanhamento> mapaAcompanhamentos, TreeMap<Long, CicloRealizadoDto> mapaCicloRealizado) {
		Acompanhamento acompanhamento = null;
		long prestadorId = config.getPrestador(ot.getIdPrestador()).longValue();
		long projetoId = config.getProjetoId(ot.getIdProjeto()).longValue();
		if (mapaAcompanhamentos.containsKey(Pair.of(projetoId, prestadorId))) {
			acompanhamento = mapaAcompanhamentos.get(Pair.of(projetoId, prestadorId));
		} else {
			acompanhamento = new Acompanhamento();
			acompanhamento.setIdProjeto(ot.getIdProjeto());
			acompanhamento.setProjeto(ot.getProjeto());
			acompanhamento.setIdPrestador(prestadorId);
			if (ot.getPrestador() == null) {
				acompanhamento.setPrestador(config.getCustomerName());
			} else {
				acompanhamento.setPrestador(ot.getPrestador());	
			}
			
			mapaAcompanhamentos.put(Pair.of(projetoId, prestadorId), acompanhamento);	
		}
		
		
		CicloRealizadoDto cicloRealizadoDto = null;
		if (mapaCicloRealizado.containsKey(projetoId)) {
			cicloRealizadoDto = mapaCicloRealizado.get(projetoId);
		} else {
			cicloRealizadoDto = new CicloRealizadoDto();
			cicloRealizadoDto.setIdProjeto(projetoId);
			mapaCicloRealizado.put(projetoId, cicloRealizadoDto);			
		}
		
		

		// ----- REALIZADO/CONCLUIDO -----		
		if (DateUtils.estaEntreDatas(Logistica.getDataInicio(dateTime), Logistica.getDataFim(dateTime), ot.getDataRecebimento())){
			Integer volumeRealizado = ot.getVolumeApurado()!=null?ot.getVolumeApurado().intValue():0;
			Integer cicloRealizado = 0;

			if (ot.getDataLiberacao() != null && ot.getDataConclusao() != null) {
				cicloRealizado = Seconds.secondsBetween(new DateTime(ot.getDataLiberacao()), new DateTime(ot.getDataConclusao())).getSeconds();		
				
				cicloRealizadoDto.somaCicloRealizado(cicloRealizado);
				cicloRealizadoDto.somaCicloRealizadoQtd(1);
			}
			acompanhamento.somaVolumeRealizado(volumeRealizado);
			acompanhamento.somaViagemRealizado(1);
				
			acompanhamento.somaProjecaoFinalVolume(volumeRealizado);
			acompanhamento.somaProjecaoFinalViagem(1);
				
			acompanhamento.somaCicloRealizado(cicloRealizado);
			acompanhamento.somaPesoMadeira(ot.getPesoLiquido()!=null?ot.getPesoLiquido().intValue():0);
			acompanhamento.somaVolumeConcluido(volumeRealizado);
			acompanhamento.somaViagemConcluido(1);	

		}
		
		// ----- ESTIMADO -----
		Date dataEstimadaChegada = (ot.getDataEstimadaChegada()!=null?ot.getDataEstimadaChegada():ot.getDataPrevistaChegada());
		
		if (ot.getDataRecebimento() == null && dataEstimadaChegada != null 
			&& DateUtils.estaEntreDatas(Logistica.getDataInicio(dateTime), Logistica.getDataFim(dateTime), dataEstimadaChegada)){
			if (ot.getSituacao() != SituacaoOrdemTransporte.CANCELADA && ot.getSituacao() != SituacaoOrdemTransporte.INTERROMPIDA) {
				acompanhamento.somaProjecaoFinalVolume(ot.getVolumePrevisto().intValue());
				acompanhamento.somaProjecaoFinalViagem(1);							
			}
		}		
		
	}


	/**
	 * the cycle must be consider the expected order transport arrival at the shift end
	 * 
	 * @param order
	 * @return boolean 
	 */
	private boolean isCycleInCurrentShift(Date dateTime, OrdemTransporte ot) {				
		if (ot.getDataLiberacao() != null) {			
			if (ot.getDataLiberacao().before(Logistica.getDataFim(dateTime))){
				Integer cycleInSeconds = Seconds.secondsBetween(new DateTime(ot.getDataProgramadaPartida()), new DateTime(ot.getDataProgramadaChegada())).getSeconds();
				Date limitDate = DateUtils.somarSegundos(ot.getDataLiberacao(), cycleInSeconds);
				
				return DateUtils.estaEntreDatas(Logistica.getDataInicio(dateTime), Logistica.getDataFim(dateTime), limitDate);
			} else {
				return false;
			}
		}		
		return true;
	}


	private TreeMap<Pair<Long, Long>, Acompanhamento> pgAtualizaAcompanhamento(TreeMap<Pair<Long, Long>, Acompanhamento> mapa) {
		List<CaminhaoTransporte> caminhoes = config.getCaminhaoTransp();
		Pair<Long, Long> key;
		
		if (caminhoes != null) {
			Acompanhamento acompanhamento;
			for(CaminhaoTransporte caminhao: caminhoes) {
				if (caminhao.getIdProjeto() != null) {
					key = Pair.of(caminhao.getIdProjeto(), caminhao.getIdPrestador());
					
					if (mapa.containsKey(key)) {
						acompanhamento = mapa.get(key);
					} else {
						acompanhamento = new Acompanhamento();
						acompanhamento.setAjustado(true);
						acompanhamento.setIdProjeto(caminhao.getIdProjeto());
						acompanhamento.setProjeto(caminhao.getProjeto());
						acompanhamento.setIdPrestador(caminhao.getIdPrestador());
						acompanhamento.setPrestador(caminhao.getPrestador());
						mapa.put(key, acompanhamento);
					}
					
					if (acompanhamento.getAjustado()) {
						// ----- PROGRAMADO -----
						/*
						Date agora = new Date();
						if (caminhao.getDataPartidaPcct() != null && agora != null && agora.after(caminhao.getDataPartidaPcct())) {
							acompanhamento.somaCicloPrevisto(Seconds.secondsBetween(new DateTime(caminhao.getDataPartidaPcct()), new DateTime(agora)).getSeconds());	
						}				
						acompanhamento.somaVolumePrevisto(60);
						acompanhamento.somaViagemPrevisto(1);
						*/
						

						// ----- projecao final dia -----
						acompanhamento.somaProjecaoFinalVolume(60);
						acompanhamento.somaProjecaoFinalViagem(1);
					}
				}							
			}
		}
		return mapa;
	}


	private boolean validaPrevisaoOtsNaoSairamCirculacao(OrdemTransporte ordemTransporte) {
		if ( (ordemTransporte.getDataRecebimento() == null) && (ordemTransporte.getSituacao() == SituacaoOrdemTransporte.LIBERADA)) {
			// somente liberadas sem recebimento
			int segundosCiclosPrevistos = DateUtils.diferencaEmSegundos(
					DateUtils.getDate(ordemTransporte.getDataProgramadaPartida(), new Date()), 
					DateUtils.getDate(ordemTransporte.getDataProgramadaChegada(), new Date()));

			if (segundosCiclosPrevistos > 0 && ordemTransporte.getDataLiberacao() != null) {
				Date dataEstimadaPrevistaChegada = DateUtils.somarSegundos(ordemTransporte.getDataLiberacao(), segundosCiclosPrevistos);
				
				Date agora = new Date();
				if (DateUtils.estaEntreDatas(Logistica.getDataInicio(agora), Logistica.getDataFim(agora), dataEstimadaPrevistaChegada)){
					return false;
				}
			}
		}
		
		return false;
	}
	
	
	private boolean validaAcompanhamento(Acompanhamento acompanhamento) {

		return (NumberUtils.parseInt(acompanhamento.getCicloPrevisto()) > 0) 
				|| (NumberUtils.parseInt(acompanhamento.getCicloRealizado()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getVolumePrevisto()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getVolumeRealizado()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getViagemPrevisto()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getViagemRealizado()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getVolumeCaminhaoPrevisto()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getVolumeCaminhaoRealizado()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getPesoMadeira()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getProjecaoFinalVolume()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getProjecaoFinalViagem()) > 0)
				|| (NumberUtils.parseInt(acompanhamento.getProjecaoFinalGrauAtendimento()) > 0);
	}


}

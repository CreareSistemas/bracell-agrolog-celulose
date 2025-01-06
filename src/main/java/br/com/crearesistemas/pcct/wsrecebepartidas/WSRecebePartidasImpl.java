package br.com.crearesistemas.pcct.wsrecebepartidas;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.pcct.wsrecebepartidas.dto.Partida;
import br.com.crearesistemas.pcct.wsrecebepartidas.dto.RecebePartidas;
import br.com.crearesistemas.pcct.wsrecebepartidas.dto.RecebePartidasResponse;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.PgCaminhoesService;


@WebService(serviceName = "PCCT/WSRecebePartidas", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebePartidasImpl extends WSBase implements WSRecebePartidas {
	
	private static final Logger logger = Logger.getLogger(WSRecebePartidasImpl.class);
	
	@Resource private CaminhoesService caminhaoService;
	@Resource private PgCaminhoesService pgCaminhoesService;
	
	public RecebePartidasResponse recebePartidas(RecebePartidas recebePartidas, Credencial credencial) {
		RecebePartidasResponse response = new RecebePartidasResponse();
	
		credencial.autenticacao();
		
		try {
			
			List<CaminhaoEntity> caminhoes = caminhaoService.selecionarCaminhoesTransporte(config.getCustomerId());
			
			
			for (CaminhaoEntity caminhao : caminhoes) {
				if (caminhao.getDataPartidaPcct() != null && EstadoOperacional.stayInEstado(
						caminhao.getTranspEstadoOperacional(), 
						EstadoOperacional.TRANSP_CARREGAMENTO, EstadoOperacional.TRANSP_FILA_EM_CAMPO, EstadoOperacional.TRANSP_VIAGEM
				)) {
					CaminhaoTransporte pgCaminhao = pgCaminhoesService.selectPgCaminhaoByName(caminhao.getName());
					
					if (truckHasOpenOrder(pgCaminhao)) {
						Partida partida = new Partida();
						partida.setIdOrdemTransporte(null);
						partida.setIdentificacao(caminhao.getName());
						if (caminhao.getId() != null) {
							partida.setIdCaminhao(String.valueOf(caminhao.getId()));	
						}
						partida.setDataChegada(caminhao.getDataPartidaPcct());
						partida.setDataJornada(null);
						response.getListaDePartidas().add(partida);	
					}
				}						
			}
			
			
		}
		catch (Exception e) {
			logger.error("Erro ", e);
		}

		return response;
	}


	private boolean truckHasOpenOrder(CaminhaoTransporte pgCaminhao) {		
		if (pgCaminhao != null && pgCaminhao.getOrdemSituacao() != null && pgCaminhao.getOrdemSituacao() == SituacaoOrdemTransporte.LIBERADA) {
			return true;
		}
		return false;
	}

}

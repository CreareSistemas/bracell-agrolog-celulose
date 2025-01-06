package br.com.crearesistemas.pcct.wsrecebechegadas;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebechegadas.dto.Chegada;
import br.com.crearesistemas.pcct.wsrecebechegadas.dto.RecebeChegadas;
import br.com.crearesistemas.pcct.wsrecebechegadas.dto.RecebeChegadasResponse;
import br.com.crearesistemas.service.CaminhoesService;

@WebService(serviceName = "PCCT/WSRecebeChegadas", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeChegadasImpl extends WSBase implements WSRecebeChegadas 
{
	private static final Logger logger = Logger.getLogger(WSRecebeChegadasImpl.class);

	@Resource private CaminhoesService caminhaoService;
	
	
	public RecebeChegadasResponse recebeChegadas(RecebeChegadas recebeChegadas, Credencial credencial) {
		RecebeChegadasResponse response = new RecebeChegadasResponse();
		
		credencial.autenticacao();
		
		try {				
			List<CaminhaoEntity> caminhoes = caminhaoService.selecionarCaminhoesTransporte(config.getCustomerId());
			
			for (CaminhaoEntity caminhao : caminhoes) {
				
				if (caminhao.getDataPartidaPcct() != null && EstadoOperacional.stayInEstado(
						caminhao.getTranspEstadoOperacional(), 
						EstadoOperacional.TRANSP_CARREGAMENTO, EstadoOperacional.TRANSP_FILA_EM_CAMPO, EstadoOperacional.TRANSP_VIAGEM
				)) {	
					Date agora = new Date();
					if (caminhao.getDataPrevisaoChegadaFabrica() != null && caminhao.getDataPrevisaoChegadaFabrica().after(agora)) {
						Chegada chegada = new Chegada();
						
						chegada.setIdOrdemTransporte(null);
						chegada.setIdentificacao(caminhao.getName());
						if (caminhao.getId() != null) {
							chegada.setIdCaminhao(String.valueOf(caminhao.getId()));	
						}
						chegada.setDataPrevistaChegada(caminhao.getDataPrevisaoChegadaFabrica());
						response.getListaDeChegadas().add(chegada);
					}
				}
					
			}
			
		}
		catch (Exception e) {
			logger.error("Erro ", e);
		}

		return response;
	}
}


package br.com.crearesistemas.pcct.wsrecebecaminhoestransporte;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.RecebeCaminhoesTransporte;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.RecebeCaminhoesTransporteResponse;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.service.PgCaminhoesService;
import br.com.crearesistemas.service.PgProjetosService;
import br.com.crearesistemas.service.ProjetosService;
import br.com.crearesistemas.service.ServiceException;
import br.com.crearesistemas.service.dto.RastreamentoDto;
import br.com.crearesistemas.service.impl.helper.CERCAS;
import br.com.crearesistemas.service.impl.helper.LocalizacaoCerca;


@WebService(serviceName = "PCCT/WSRecebeCaminhoesTransporte", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeCaminhoesTransporteImpl extends WSBase implements WSRecebeCaminhoesTransporte {
	Config config = Config.getInstance();
	
	private static final Logger logger = Logger.getLogger(WSRecebeCaminhoesTransporteImpl.class);

	@Resource private CaminhoesService service;
	@Resource private PgCaminhoesService pgService;
	@Resource private PgProjetosService pgProjetosService;
	@Resource private ProjetosService projetosService;
	@Resource private GruasService gruasService;
		
	
	public RecebeCaminhoesTransporteResponse recebeCaminhoesTransporte(
			RecebeCaminhoesTransporte recebeCaminhoesTransportes, Credencial credencial) {
		RecebeCaminhoesTransporteResponse response = new RecebeCaminhoesTransporteResponse();
		LocalizacaoCerca helper = LocalizacaoCerca.getInstance();
		
		credencial.autenticacao();
		HashMap<Long, String> hashProjetosCaminhoesTransp = new HashMap<>();
		
		try {
			
			List<CaminhaoEntity> caminhoes = service.selecionarCaminhoesTransporte(config.getCustomerId());
			
			for (CaminhaoEntity caminhao : caminhoes) {
				Boolean addAsset = false;
				RastreamentoDto rastreamento = new RastreamentoDto(caminhao.getLatitude(), caminhao.getLongitude());
				
				Boolean implementoEstaNaFabrica = helper.implementoEstaNoLocal(rastreamento, CERCAS.FABRICA) || helper.isStayInCheckpoint(rastreamento);
				
				if (!implementoEstaNaFabrica && config.isTransport(caminhao)) {
					CaminhaoTransporte pgCaminhao = pgService.selectPgCaminhao(caminhao.getName());
					CaminhaoTransporte caminhaoTransp = null;
					
					if (caminhao.getLoading() == Config.CARREG_CHEIO) {
						caminhaoTransp = response.addCaminhao(caminhao, pgCaminhao, config.getMapProjetos());
						addAsset = true;
					} else {
						// empty truck + unload lane
						if (EstadoOperacional.stayInEstado(caminhao.getTranspEstadoOperacional(),EstadoOperacional.TRANSP_VIAGEM)) {
							if (pgCaminhao != null && pgCaminhao.hasOpenOrder()) {
								caminhaoTransp = response.addCaminhao(caminhao, pgCaminhao, config.getMapProjetos());
								addAsset = true;
							}	
						} else {
							caminhaoTransp = response.addCaminhao(caminhao, pgCaminhao, config.getMapProjetos());
							addAsset = true;
						}
					}
					
					
					if (addAsset &&  caminhaoTransp != null && caminhaoTransp.getIdProjeto() != null && !hashProjetosCaminhoesTransp.containsKey(caminhaoTransp.getIdProjeto())) {
						hashProjetosCaminhoesTransp.put(caminhaoTransp.getIdProjeto(), caminhaoTransp.getIdentificacao());	
					}
					
					if (addAsset && pgCaminhao != null && pgCaminhao.getIdProjeto() != null && !hashProjetosCaminhoesTransp.containsKey(pgCaminhao.getIdProjeto())) {
						hashProjetosCaminhoesTransp.put(pgCaminhao.getIdProjeto(), pgCaminhao.getIdentificacao());	
					}
				}
					
			}
		} catch (ServiceException e) {
			logger.error("Erro ", e);
		} catch (Exception e) {
			logger.error("Erro ", e);
		}
		
		config.setMapProjetosCaminhaoTransp(hashProjetosCaminhoesTransp);
		config.setCaminhaoTransp(response.getListaDeCaminhoesTransporte());

		return response;
	}


	
	
}

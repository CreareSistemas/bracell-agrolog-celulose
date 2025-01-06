package br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.EstagioOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcsil1.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.CaminhaoSiteIndustrial;
import br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial.dto.RecebeCaminhoesSiteIndustrial;
import br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial.dto.RecebeCaminhoesSiteIndustrialResponse;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.PgCaminhoesService;
import br.com.crearesistemas.service.ServiceException;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.RastreamentoDto;
import br.com.crearesistemas.service.impl.helper.CERCAS;
import br.com.crearesistemas.service.impl.helper.LocalizacaoCerca;


@WebService(serviceName = "PCSIL1/WSRecebeCaminhoesSiteIndustrial", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeCaminhoesSiteIndustrialImpl extends WSBase implements WSRecebeCaminhoesSiteIndustrial {
	Config config = Config.getInstance();
	private static final Logger logger = Logger.getLogger(WSRecebeCaminhoesSiteIndustrialImpl.class);

	@Resource private CaminhoesService caminhaoService;
	
	@Resource private PgCaminhoesService pgCaminhaoService;



	public RecebeCaminhoesSiteIndustrialResponse recebeCaminhoesSiteIndustrial(
			RecebeCaminhoesSiteIndustrial recebeCaminhoesSiteIndustrial, Credencial credencial) {

		logger.info("PCSIL1/WSRecebeCaminhoesSiteIndustrial ");
		
		RecebeCaminhoesSiteIndustrialResponse response = new RecebeCaminhoesSiteIndustrialResponse();

		credencial.autenticacao();

		try {
			logger.info("DataHistorico: " + recebeCaminhoesSiteIndustrial.getDataHistorico());

			setCaminhoesEntity(response, caminhaoService
					.selecionarCaminhoesTransporteL1(config.getCustomerId()));
						
		} catch (ServiceException e) {
			logger.error("Erro ", e);
		} catch (Exception e) {
			logger.error("Erro ", e);
		}

		return response;
	}

	
	public void setCaminhoesEntity(RecebeCaminhoesSiteIndustrialResponse response, List<CaminhaoEntity> caminhoes) {
		LocalizacaoCerca helper = LocalizacaoCerca.getInstance();
		
		
		
		config.mapeiaLocaisMesaL1Caminhoes(caminhoes);
		
		for (CaminhaoEntity caminhaoAero : caminhoes) {
			RastreamentoDto rastreamento = new RastreamentoDto(caminhaoAero.getLatitude(), caminhaoAero.getLongitude());
			boolean implementoEstaNaFabrica = helper.implementoEstaNoLocal(rastreamento, CERCAS.FABRICA) || helper.isStayInCheckpoint(rastreamento);
			
			boolean isMesaOuQuadra = caminhaoService.isStayInState(caminhaoAero, 
					Arrays.asList(EstadoOperacional.SI_MESAS, EstadoOperacional.SI_QUADRAS, EstadoOperacional.SI_FILA));
					
			InterfaceSgfDto pgSgfInterface = pgCaminhaoService.selecionarInterfaceSgf(caminhaoAero.getName());
			
			if (implementoEstaNaFabrica || isMesaOuQuadra) {
				CaminhaoSiteIndustrial caminhaoTransporte = new CaminhaoSiteIndustrial();
				caminhaoTransporte.setId(caminhaoAero.getId());
				caminhaoTransporte.setIdentificacao(caminhaoAero.getName());
				caminhaoTransporte.setIdTipoImplemento(TipoImplemento.CAMINHAO_TRANSPORTE.ordinal());
				caminhaoTransporte.setDataInicio(caminhaoAero.getDataChegadaPcsi());
				caminhaoTransporte.setDataChegada(caminhaoAero.getDataChegadaPcsi());
				
				if (caminhaoAero.getLoading() == 1) {
					caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());	
				} else {
					caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());
				}
				
				caminhaoService.setEstagioCaminhaoTransporte(caminhaoAero, caminhaoTransporte);
				
				if (caminhaoAero.getSiteEstadoOperacional() == EstadoOperacional.SI_MESAS.ordinal()) {
					Long localMesa = config.localMesaL1Caminhao(caminhaoAero);					
					if (localMesa != null) {
						caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_MESAS.ordinal());
						caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_MESAS);
						caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());
						caminhaoTransporte.setIdLocal(localMesa.longValue());	
					} else {
						caminhaoTransporte.setIdEstadoOperacionalSI(EstadoOperacional.SI_FILA_BALANCA_SAIDA.ordinal());
						caminhaoTransporte.setEstadoOperacionalSI(EstadoOperacional.SI_FILA_BALANCA_SAIDA);						
						caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());
						caminhaoTransporte.setIdLocal(config.getLocalId14FilaBalancaSai().longValue());
					}
				}
				
				// controle de agendamentos
				caminhaoTransporte.setMostrarDetalhes(caminhaoTransporte.getAgendamento() != null);
				caminhaoTransporte.setDataUltimoRastreamento(caminhaoAero.getAvldateTime());
				caminhaoTransporte.setLatitude(caminhaoAero.getLatitude());
				caminhaoTransporte.setLongitude(caminhaoAero.getLongitude());
				caminhaoTransporte.setIdGrua(null);
				caminhaoTransporte.setIdPrestador(config.getPrestador(caminhaoAero.getCustomerChildId()).longValue());
				caminhaoTransporte.setCalibracao(false);

				if (pgSgfInterface != null) {
					String situacao = null;
					if (pgSgfInterface.getOtSituacao() != null) {
						situacao = pgSgfInterface.getOtSituacao().name();
					}
					if (pgSgfInterface.getOtId() != null) {
						caminhaoTransporte.setOrdemTransporte(
								String.format("%s - id: %s - %s", 
										pgSgfInterface.getOtNumero(),
										pgSgfInterface.getOtId(),
										situacao
										)
						);	
					} else {
						caminhaoTransporte.setOrdemTransporte(null);
					}
				}
				
				
				response.getListaDeCaminhoesSiteIndustrial().add(caminhaoTransporte);			
			}
			
		}
	}
	
	

		

}

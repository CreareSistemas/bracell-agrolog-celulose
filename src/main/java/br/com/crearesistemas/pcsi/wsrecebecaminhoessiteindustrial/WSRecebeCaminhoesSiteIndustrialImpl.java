package br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.pcsi.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.CaminhaoSiteIndustrial;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.RecebeCaminhoesSiteIndustrial;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.RecebeCaminhoesSiteIndustrialResponse;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.PgCaminhoesService;
import br.com.crearesistemas.service.ServiceException;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.RastreamentoDto;
import br.com.crearesistemas.service.impl.helper.CERCAS;
import br.com.crearesistemas.service.impl.helper.LocalizacaoCerca;
import br.com.crearesistemas.util.DateUtils;


@WebService(serviceName = "PCSI/WSRecebeCaminhoesSiteIndustrial", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeCaminhoesSiteIndustrialImpl extends WSBase implements WSRecebeCaminhoesSiteIndustrial {
	Config config = Config.getInstance();
	private static final Logger logger = Logger.getLogger(WSRecebeCaminhoesSiteIndustrialImpl.class);

	@Resource private CaminhoesService caminhaoService;
	
	@Resource private PgCaminhoesService pgCaminhaoService;



	public RecebeCaminhoesSiteIndustrialResponse recebeCaminhoesSiteIndustrial(
			RecebeCaminhoesSiteIndustrial recebeCaminhoesSiteIndustrial, Credencial credencial) {

		RecebeCaminhoesSiteIndustrialResponse response = new RecebeCaminhoesSiteIndustrialResponse();

		credencial.autenticacao();

		try {
			logger.info("DataHistorico: " + recebeCaminhoesSiteIndustrial.getDataHistorico());

			setCaminhoesEntity(response, caminhaoService
					.selecionarCaminhoesTransporteL2(config.getCustomerId()));
						
		} catch (ServiceException e) {
			logger.error("Erro ", e);
		} catch (Exception e) {
			logger.error("Erro ", e);
		}

		return response;
	}

	
	public void setCaminhoesEntity(RecebeCaminhoesSiteIndustrialResponse response, List<CaminhaoEntity> caminhoes) {
		LocalizacaoCerca helper = LocalizacaoCerca.getInstance();
		
		for (CaminhaoEntity caminhaoAero : caminhoes) {
			RastreamentoDto rastreamento = new RastreamentoDto(caminhaoAero.getLatitude(), caminhaoAero.getLongitude());
			boolean implementoEstaNaFabrica = helper.implementoEstaNoLocal(rastreamento, CERCAS.FABRICA) || helper.isStayInCheckpoint(rastreamento);
			
			boolean isMesaOuQuadra = caminhaoService.isStayInState(caminhaoAero, 
					Arrays.asList(EstadoOperacional.SI_MESAS, EstadoOperacional.SI_QUADRAS, EstadoOperacional.SI_FILA));
			
			boolean isBetweenFilaAndBalancaSaida = caminhaoService.isStayInState(caminhaoAero, 
					Arrays.asList(EstadoOperacional.SI_MESAS, EstadoOperacional.SI_QUADRAS, EstadoOperacional.SI_FILA, EstadoOperacional.SI_BALANCA_SAIDA));
			
			InterfaceSgfDto pgSgfInterface = pgCaminhaoService.selecionarInterfaceSgf(caminhaoAero.getName());
			
			if (implementoEstaNaFabrica || isMesaOuQuadra) {
				CaminhaoSiteIndustrial caminhaoTransporte = new CaminhaoSiteIndustrial();
				caminhaoTransporte.setId(caminhaoAero.getId());
				caminhaoTransporte.setIdentificacao(caminhaoAero.getName());
				caminhaoTransporte.setIdTipoImplemento(TipoImplemento.CAMINHAO_TRANSPORTE.ordinal());
				
				
				if (isBetweenFilaAndBalancaSaida) {
					if (caminhaoAero.getSiteFilaDate() != null) {
						caminhaoTransporte.setDataInicio(caminhaoAero.getSiteFilaDate());
						caminhaoTransporte.setDataChegada(caminhaoAero.getSiteFilaDate());	
					} else {
						caminhaoTransporte.setDataInicio(caminhaoAero.getSiteDate());
						caminhaoTransporte.setDataChegada(caminhaoAero.getSiteDate());
					}						
				} else
				if (caminhaoAero.getSiteDate() != null) {
					caminhaoTransporte.setDataInicio(caminhaoAero.getSiteDate());
					caminhaoTransporte.setDataChegada(caminhaoAero.getSiteDate());						
				}
				
				if (caminhaoAero.getLoading() == 1) {
					caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());	
				} else {
					caminhaoTransporte.setIdEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());
				}
				
				caminhaoService.setEstagioCaminhaoTransporte(caminhaoAero, caminhaoTransporte);
				
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
				
				
				if (caminhaoTransporte.getIdEstadoOperacionalSI() != null && 
					(caminhaoTransporte.getIdEstadoOperacionalSI() == EstadoOperacional.SI_VARRICAO.ordinal() ||
					caminhaoTransporte.getIdEstadoOperacionalSI() == EstadoOperacional.SI_CHECKPOINT_SAIDA.ordinal())
				) {
					Boolean isExpired = false;
					if (caminhaoTransporte.getDataInicio() != null) {
						Integer segundos = DateUtils.diferencaEmSegundos(caminhaoTransporte.getDataInicio(), new Date());
						if (segundos > 3 * 60 * 60) {
							isExpired = true;
						}
					}
					if (!isExpired) {
						response.getListaDeCaminhoesSiteIndustrial().add(caminhaoTransporte);	
					}
				} else {
					response.getListaDeCaminhoesSiteIndustrial().add(caminhaoTransporte);	
				}
				
							
			}
			
		}
	}
	
	

		

}

package br.com.crearesistemas.pcsi.wsrecebeindisponiveis;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.log4j.Logger;

import br.com.crearesistemas.config.Logistica;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcsi.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto.ImplementoIndisponivel;
import br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto.RecebeImplementosIndisponiveis;
import br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto.RecebeImplementosIndisponiveisResponse;
import br.com.crearesistemas.service.CaminhoesService;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.util.DateUtils;


@WebService(serviceName = "PCSI/WSRecebeImplementosIndisponiveis", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeImplementosIndisponiveisImpl extends WSBase implements WSRecebeImplementosIndisponiveis {
	
	private static final Logger logger = Logger.getLogger(WSRecebeImplementosIndisponiveisImpl.class);
	
	@Resource private CaminhoesService caminhaoService;
	
	@Resource private GruasService gruaService;
	
	
	public RecebeImplementosIndisponiveisResponse recebeImplementosIndisponiveis(RecebeImplementosIndisponiveis recebeImplementosIndisponiveis, Credencial credencial) {
		
		RecebeImplementosIndisponiveisResponse response = new RecebeImplementosIndisponiveisResponse();
		
		credencial.autenticacao();
		
		List<GruaEntity> gruasFlorestais = gruaService.selecionarGruasFlorestais(config.getCustomerId());
		List<GruaEntity> gruasSite = gruaService.selecionarGruasSiteIndustrial(config.getCustomerId());
		List<CaminhaoEntity> caminhoes = caminhaoService.selecionarCaminhoesTransporte(config.getCustomerId());
		
		try {
			
			
			gruasFlorestais(response, gruasFlorestais);
			
			//gruasSite(response, gruasSite);
					
			caminhoesTransporte(response, caminhoes);
			
		}
		catch (Exception e) 	{
			logger.error("Erro ", e);
		}
		
		return response;
	}


	private void caminhoesTransporte(RecebeImplementosIndisponiveisResponse response, List<CaminhaoEntity> caminhoes) {
		for(CaminhaoEntity caminhao : caminhoes) {
			if (!EstadoOperacional.stayInEstado(caminhao.getTranspEstadoOperacional(), 
					EstadoOperacional.TRANSP_CARREGAMENTO, EstadoOperacional.TRANSP_FABRICA, EstadoOperacional.TRANSP_FILA_EM_CAMPO
					, EstadoOperacional.TRANSP_VIAGEM
			)) {
				response.getListaDeImplementosIndisponiveis().add(
						new ImplementoIndisponivel(caminhao, caminhao.getProprioEstadoOperacional(), TipoImplemento.CAMINHAO_TRANSPORTE)
				);
			} else {
				Integer duracaoRastreamentoEmsegundos = DateUtils.diferencaEmSegundos(DateUtils.getDate(caminhao.getAvldateTime(), new Date()), new Date());
				Integer duracaoApontamentoEmsegundos = DateUtils.diferencaEmSegundos(DateUtils.getDate(caminhao.getEventDateTime(), new Date()), new Date());
				
				Integer duracaoColetaEmsegundos = duracaoRastreamentoEmsegundos;							
				if (duracaoApontamentoEmsegundos < duracaoRastreamentoEmsegundos){
					duracaoColetaEmsegundos = duracaoApontamentoEmsegundos;
				}
				
				if (duracaoColetaEmsegundos > Logistica.LIMITE_COLETA_EM_SEGUNDOS){
					if (caminhao.getTranspEstadoOperacional() != null){
						response.getListaDeImplementosIndisponiveis().add(new ImplementoIndisponivel(caminhao, caminhao.getTranspEstadoOperacional(), TipoImplemento.CAMINHAO_TRANSPORTE));	
					}else{
						response.getListaDeImplementosIndisponiveis().add(new ImplementoIndisponivel(caminhao, EstadoOperacional.RESERVA.ordinal(), TipoImplemento.CAMINHAO_TRANSPORTE));
					}
				}
			}
		}
		
	}


	private void gruasSite(RecebeImplementosIndisponiveisResponse response, List<GruaEntity> gruasSite) {
		for(GruaEntity grua : gruasSite) {
			if (EstadoOperacional.stayInEstado(grua.getProprioEstadoOperacional(), 
					EstadoOperacional.MANUTENCAO_ABASTECIMENTO, EstadoOperacional.MANUTENCAO_ELETRICA, EstadoOperacional.MANUTENCAO_HIDRAULICA, EstadoOperacional.MANUTENCAO_MECANICA
					, EstadoOperacional.RESERVA, EstadoOperacional.DESLOCAMENTO_PRANCHA, EstadoOperacional.DESLOCAMENTO_INTERNO
			)) {
				
				TipoImplemento tipo;
				if (config.isMesa(grua.getName()) || config.isMesaL1(grua.getName())) {
					tipo = TipoImplemento.GRUA_FIXA;
				} else {
					tipo = TipoImplemento.GRUA_MOVEL;
				}
				response.getListaDeImplementosIndisponiveis().add(
						new ImplementoIndisponivel(grua, grua.getProprioEstadoOperacional(), tipo)
				);
			}	
		}
		
	}


	private void gruasFlorestais(RecebeImplementosIndisponiveisResponse response, List<GruaEntity> gruasFlorestais) {
		double duracaoColetaEmSegundos = 0;
		
		for(GruaEntity grua : gruasFlorestais) {
			
			
			EstadoOperacional gruaEstadoOperacionalProprio = config.eventoEstadoMap(TipoImplemento.GRUA_FLORESTAL, grua.getEventCode());
			
			if (grua.getName().contains("15015")) {
				System.out.println("");
			}
			
			if (gruaEstadoOperacionalProprio != null && EstadoOperacional.stayInEstado(gruaEstadoOperacionalProprio.ordinal(), 
					EstadoOperacional.DISPONIVEL, EstadoOperacional.CARREGAMENTO, EstadoOperacional.DESIGNADO, EstadoOperacional.FILA, EstadoOperacional.INTERVALO_OUTROS
			)) {
				
				Integer duracaoRastreamentoEmSegundos = DateUtils.diferencaEmSegundos(
						DateUtils.getDate(grua.getAvldateTime(), new Date()), new Date()
				);
				
				Integer duracaoApontamentoEmSegundos = DateUtils.diferencaEmSegundos(
						DateUtils.getDate(grua.getEventdateTime(), new Date()), new Date());
				
				if (duracaoApontamentoEmSegundos < duracaoRastreamentoEmSegundos){
					duracaoColetaEmSegundos = duracaoApontamentoEmSegundos;
				} else {
					duracaoColetaEmSegundos = duracaoRastreamentoEmSegundos;
				}
				
				if (duracaoColetaEmSegundos > Logistica.LIMITE_COLETA_EM_SEGUNDOS){
					response.getListaDeImplementosIndisponiveis().add(
							new ImplementoIndisponivel(grua, grua.getProprioEstadoOperacional(), TipoImplemento.GRUA_FLORESTAL)
					);								
				}
			} else {
				response.getListaDeImplementosIndisponiveis().add(
						new ImplementoIndisponivel(grua, grua.getProprioEstadoOperacional(), TipoImplemento.GRUA_FLORESTAL)
				);
			}
					
		}
		
	}

}

package br.com.crearesistemas.pcsi.wsrecebegruassiteindustrial;

import java.util.TreeMap;

import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.log4j.Logger;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Local;
import br.com.crearesistemas.pcsi.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebegruassiteindustrial.dto.RecebeGruasSiteIndustrial;
import br.com.crearesistemas.pcsi.wsrecebegruassiteindustrial.dto.RecebeGruasSiteIndustrialResponse;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.service.ServiceException;


@WebService(serviceName = "PCSI/WSRecebeGruasSiteIndustrial", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeGruasSiteIndustrialImpl extends WSBase implements WSRecebeGruasSiteIndustrial {
	
	private static final Logger logger = Logger.getLogger(WSRecebeGruasSiteIndustrialImpl.class);
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();

	
	@Resource private GruasService service;

	TreeMap<Long, Local> mapaLocaisUtilizadosGruaMovel;
	
	public RecebeGruasSiteIndustrialResponse recebeGruasSiteIndustrial(
			RecebeGruasSiteIndustrial recebeGruasSiteIndustrial, 
			Credencial credencial) {
		
		RecebeGruasSiteIndustrialResponse response = new RecebeGruasSiteIndustrialResponse();
		credencial.autenticacao();
		
		try {
			response.setGruasEntity(config, service.selecionarGruasSiteIndustrialL2(config.getCustomerId()), service);
		}
		catch (ServiceException e) {
			logger.error("Erro ", e);
		}
		catch (Exception e) 	{
			logger.error("Erro ", e);
		}
		
		return response;
	}
	

	public static enum TipoCarregamento {
		DESCONHECIDO("Desconhecido"), CARREGAMENTO("Carregamento"), CARGA("Carga"), DESCARGA("Descarga");
		
		private String carregamento;
		
		private TipoCarregamento(String carregamento){
			this.carregamento = carregamento;
		}

		public String getCarregamento() {
			return carregamento;
		}

		public void setCarregamento(String carregamento) {
			this.carregamento = carregamento;
		}
		
	}
	

}

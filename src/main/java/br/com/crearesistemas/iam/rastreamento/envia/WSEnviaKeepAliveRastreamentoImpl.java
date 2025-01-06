package br.com.crearesistemas.iam.rastreamento.envia;


import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.KeepAliveRastreamentoAgrologService;
import br.com.crearesistemas.util.IntrospectionUtils;

/**
 * @author eninomia
 */
public class WSEnviaKeepAliveRastreamentoImpl extends WSBase implements WSEnviaKeepAliveRastreamento {

	private static final Logger logger = Logger.getLogger(WSEnviaKeepAliveRastreamentoImpl.class);

	@Resource
	private KeepAliveRastreamentoAgrologService rastreamentoService;

	
	public ResponseEnviaKeepAliveRastreamento enviaRastreamento(EnviaKeepAliveRastreamento entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de rastreamento recebido: " + IntrospectionUtils.toString(entrada) + ".");
		}
		
		ResponseEnviaKeepAliveRastreamento saida = new ResponseEnviaKeepAliveRastreamento();
		
		if (enviarKeepAliveRastreamento(entrada)) {
			saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
			saida.setMensagem(WSBase.MSG_RASTREAMENTO_RECEBIDO);
			logger.info("Rastreamento enviado com sucesso. /n");
		} else {
			saida.setErro(WSBase.STATUS_ERRO);
			saida.setMensagem(WSBase.MSG_RASTREAMENTO_ERRO);
			logger.info("Algum erro no envio do Rastreamento. /n");
		}
		
		return saida;
	}

	private boolean enviarKeepAliveRastreamento(EnviaKeepAliveRastreamento entrada) {
		return rastreamentoService.enviarKeepAliveRastreamento(entrada);
	}

	

}

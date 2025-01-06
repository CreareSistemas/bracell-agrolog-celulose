package br.com.crearesistemas.iam.rotograma.cadastra;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.RotogramaService;

/**
 * @author cneves, 13-Jul-2015
 */
public class WSCadastraRotogramaImpl extends WSBase implements WSCadastraRotograma {

	private static final Logger logger = Logger.getLogger(WSCadastraRotogramaImpl.class);

	@Resource
	private RotogramaService service;

	public ResponseCadastraRotograma cadastraRotograma(CadastraRotograma entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de cadastro de rotograma recebido: " + entrada + ".");
		}
		ResponseCadastraRotograma saida = new ResponseCadastraRotograma();
		salvarRotograma(rotograma(entrada));
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		saida.setMensagem(WSBase.MSG_ROTOGRAMA_CADASTRADO);
		return saida;
	}

	private CadastraRotograma rotograma(CadastraRotograma rotograma) {
		return rotograma;
	}

	private void salvarRotograma(CadastraRotograma rotograma) {
		service.salvarRotograma(rotograma);
	}

}

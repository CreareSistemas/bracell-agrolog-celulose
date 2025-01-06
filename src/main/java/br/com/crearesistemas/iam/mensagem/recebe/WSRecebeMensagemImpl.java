package br.com.crearesistemas.iam.mensagem.recebe;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Mensagem;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.MensagemService;

/**
 * @author cneves, 19-Jun-2015
 */
public class WSRecebeMensagemImpl extends WSBase implements WSRecebeMensagem {

	private static final Logger logger = Logger.getLogger(WSRecebeMensagemImpl.class);

	@Resource
	private MensagemService service;

	public ResponseRecebeMensagem recebeMensagem(RecebeMensagem entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de mensagens recebido: [ mId=" + entrada.getmId() + " cliente=" + entrada.getCliente() + " ].");
		}
		ResponseRecebeMensagem saida = new ResponseRecebeMensagem();
		List<Mensagem> mensagens = buscarMensagens(entrada.getmId(), entrada.getCliente());
		saida.setMensagens(mensagens);
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		{
			logger.info("Total de mensagens a serem enviadas: " + mensagens.size() + ".\n");
		}
		return saida;
	}

	private List<Mensagem> buscarMensagens(Long mId, List<Long> clientes) {
		return service.buscarMensagens(mId, clientes);
	}

}

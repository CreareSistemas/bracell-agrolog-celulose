package br.com.crearesistemas.iam.mensagem.confirma;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Mensagem;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.MensagemService;

/**
 * @author cneves, 26-Jun-2015
 */
public class WSConfirmaRecebimentoMensagemImpl extends WSBase implements WSConfirmaRecebimentoMensagem {

	private static final Logger logger = Logger.getLogger(WSConfirmaRecebimentoMensagemImpl.class);

	@Resource
	private MensagemService service;

	public ResponseConfirmaRecebimentoMensagem confirmaRecebimentoMensagem(ConfirmaRecebimentoMensagem entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de confirmação de recebimento de mensagem recebido: " + entrada + ".");
		}
		ResponseConfirmaRecebimentoMensagem saida = new ResponseConfirmaRecebimentoMensagem();
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		atualizarStatusMensagem(entrada.getId(), Mensagem.STATUS_ENVIADO_PARA_EQUIPAMENTO);
		return saida;
	}

	private void atualizarStatusMensagem(Long id, int status) {
		service.atualizarStatusMensagens(id, status);
		{
			logger.info(String.format("Mensagem [ id=%d ] atualizada para o status '%s'.\n", id, status));
		}
	}

}

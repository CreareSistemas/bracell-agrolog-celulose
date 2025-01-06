package br.com.crearesistemas.iam.mensagem.envia;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Mensagem;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.MensagemService;

/**
 * @author cneves, 19-Jun-2015
 */
public class WSEnviaMensagemImpl extends WSBase implements WSEnviaMensagem {

	private static final Logger logger = Logger.getLogger(WSEnviaMensagemImpl.class);

	@Resource
	private MensagemService service;

	public ResponseEnviaMensagem enviaMensagem(EnviaMensagem entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Mensagem recebida: " + entrada + ".");
		}
		salvarMensagem(mensagem(entrada));
		ResponseEnviaMensagem saida = new ResponseEnviaMensagem();
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		saida.setMensagem(WSBase.MSG_MENSAGEM_RECEBIDA);
		return saida;
	}

	private Mensagem mensagem(EnviaMensagem entrada) {
		Mensagem mensagem = new Mensagem();
		mensagem.setMsg(entrada.getMensagemLivre());
		mensagem.setStatus(Mensagem.STATUS_PENDENTE_DE_ENVIO);
		mensagem.setGrpRespPredefinidas(entrada.getGrpRespPredefinidas());
		mensagem.setRegisterDate(entrada.getData());
		mensagem.setVehicleId(config.getVeiculoId(entrada.getIdDispositivo()));
		mensagem.setIdMensagemPredefinida(entrada.getIdMensagemPredefinida());
		mensagem.setIdMensagem(entrada.getIdMensagem());
		mensagem.setSequencialPergunta(null);
		return mensagem;
	}

	private void salvarMensagem(Mensagem mensagemRecebida) {
		service.salvarMensagem(mensagemRecebida);
		{
			logger.info("Mensagem salva: " + mensagemRecebida.getId() + ".\n");
		}
	}

}

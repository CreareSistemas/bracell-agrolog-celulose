package br.com.crearesistemas.iam;

import br.com.crearesistemas.config.Config;

public abstract class WSBase {

	protected final Config config = Config.getInstance();
	protected static final int STATUS_NENHUM_ERRO_ENCONTRADO = 0;
	protected static final int STATUS_ERRO = 1;

	protected static final String MSG_RASTREAMENTO_RECEBIDO = "Rastreamento recebido com sucesso.";
	protected static final String MSG_RASTREAMENTO_ERRO = "Algum erro no Rastreamento.";

	protected static final String MSG_MENSAGEM_RECEBIDA = "Mensagem recebida com sucesso.";

	protected static final String MSG_ROTOGRAMA_CADASTRADO = "Rotograma cadastrado com sucesso.";
	protected static final String MSG_ROTOGRAMA_EMBARCADO = "Rotograma embarcado com sucesso.";

}

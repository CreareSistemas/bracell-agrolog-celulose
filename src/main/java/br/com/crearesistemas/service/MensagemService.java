package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.Mensagem;

public interface MensagemService {

	public List<Mensagem> buscarMensagens(Long mId, List<Long> clientes);

	public void salvarMensagem(Mensagem mensagemRecebida);

	public void atualizarStatusMensagens(Long id, int status);

}

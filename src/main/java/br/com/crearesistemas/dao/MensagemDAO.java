package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.Mensagem;

public interface MensagemDAO {

	List<Mensagem> buscarMensagens(Long mId, List<Long> clientes);

	void salvarMensagem(Mensagem mensagem);

	void atualizarStatusMensagem(Long id, int status);

}

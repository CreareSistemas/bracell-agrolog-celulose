package br.com.crearesistemas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.MensagemDAO;
import br.com.crearesistemas.model.Mensagem;
import br.com.crearesistemas.service.MensagemService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class MensagemServiceImpl extends ServiceImpl<MensagemDAO> implements MensagemService {

	@Override
	@Resource(name = "mensagemJdbcDAO")
	public void setDao(MensagemDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<Mensagem> buscarMensagens(Long mId, List<Long> clientes) {
		return super.getDao().buscarMensagens(mId, clientes);
	}

	@Override
	public void salvarMensagem(Mensagem mensagem) {
		super.getDao().salvarMensagem(mensagem);
	}

	@Override
	public void atualizarStatusMensagens(Long id, int status) {
		super.getDao().atualizarStatusMensagem(id, status);
	}

}

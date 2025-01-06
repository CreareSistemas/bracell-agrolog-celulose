package br.com.crearesistemas.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.EntidadeProprietariaDAO;
import br.com.crearesistemas.model.EntidadeProprietaria;
import br.com.crearesistemas.service.EntidadeProprietariaService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class EntidadeProprietariaServiceImpl extends ServiceImpl<EntidadeProprietariaDAO> implements EntidadeProprietariaService {
	@Override
	@Resource(name = "entidadeProprietariaJdbcDAO")
	public void setDao(EntidadeProprietariaDAO dao) {
		super.setDao(dao);
	}

	@Override
	public EntidadeProprietaria buscarEntidadeProprietaria(long customerId) {
		return super.getDao().buscarEntidadeProprietaria(customerId);
	}

}

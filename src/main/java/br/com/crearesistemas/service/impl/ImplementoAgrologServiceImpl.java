package br.com.crearesistemas.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.agrolog.ImplementoDAO;
import br.com.crearesistemas.model.agrolog.Implemento;
import br.com.crearesistemas.service.ImplementoAgrologService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class ImplementoAgrologServiceImpl extends ServiceImpl<ImplementoDAO> implements ImplementoAgrologService {
	
	@Override
	@Resource(name = "implementoJdbcDAO")
	public void setDao(ImplementoDAO dao) {
		super.setDao(dao);
	}

	@Override
	public Implemento selecionaPorIdEquipamento(Long equipamentoId) {
		return super.getDao().selecionaPorIdEquipamento(equipamentoId);
	}

}

package br.com.crearesistemas.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.agrolog.DispositivoDAO;
import br.com.crearesistemas.model.agrolog.Dispositivo;
import br.com.crearesistemas.service.DispositivoAgrologService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class DispositivoAgrologServiceImpl extends ServiceImpl<DispositivoDAO> implements DispositivoAgrologService {
	
	@Override
	@Resource(name = "dispositivoJdbcDAO")
	public void setDao(DispositivoDAO dao) {
		super.setDao(dao);
	}

	@Override
	public Dispositivo selecionaPorIdentificador(String identificador) {
		return super.getDao().selecionaPorIdentificador(identificador);
	}

}

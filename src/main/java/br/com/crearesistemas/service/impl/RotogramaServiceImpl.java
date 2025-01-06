package br.com.crearesistemas.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.RotogramaDAO;
import br.com.crearesistemas.iam.rotograma.cadastra.CadastraRotograma;
import br.com.crearesistemas.service.RotogramaService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class RotogramaServiceImpl extends ServiceImpl<RotogramaDAO> implements RotogramaService {

	@Override
	@Resource(name = "rotogramaJdbcDAO")
	public void setDao(RotogramaDAO dao) {
		super.setDao(dao);
	}

	@Override
	public void salvarRotograma(CadastraRotograma rotograma) {
		super.getDao().salvarRotograma(rotograma);
	}

}

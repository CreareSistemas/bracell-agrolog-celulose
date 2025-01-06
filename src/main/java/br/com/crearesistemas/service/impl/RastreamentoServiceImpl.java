package br.com.crearesistemas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.RastreamentoDAO;
import br.com.crearesistemas.model.Rastreamento;
import br.com.crearesistemas.service.RastreamentoService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class RastreamentoServiceImpl extends ServiceImpl<RastreamentoDAO> implements RastreamentoService {

	@Override
	@Resource(name = "rastreamentoJdbcDAO")
	public void setDao(RastreamentoDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<Rastreamento> buscarRastreamentos(long mId, List<Long> clientes) {
		return super.getDao().buscarRastreamentos(mId, clientes);
	}

}

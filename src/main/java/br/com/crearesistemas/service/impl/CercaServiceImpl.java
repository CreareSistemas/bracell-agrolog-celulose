package br.com.crearesistemas.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.CercaDAO;
import br.com.crearesistemas.model.Cerca;
import br.com.crearesistemas.service.CercaService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class CercaServiceImpl extends ServiceImpl<CercaDAO> implements CercaService {

	@Override
	@Resource(name = "cercaJdbcDAO")
	public void setDao(CercaDAO dao) {
		super.setDao(dao);
	}

	@Override
	public void salvar(List<Cerca> cercas, List<Long> clientes) {
		if (clientes != null && cercas != null) {
			for (Long clienteId : clientes) {
				for (Cerca cerca : cercas) {
					if (getDao().exists(clienteId, cerca.getIdProjeto(), cerca.getCdRota(), cerca.getObjectId())) {
						getDao().update(cerca, clienteId);
					} else {
						getDao().insert(cerca, clienteId);
					}
				}
			}
		}
	}

	@Override
	public void embarcaCerca(Long numOt, String idProjeto, String cdRota, String placa, String idDispositivo, Date dataLiberacao) {
		getDao().embarcaCerca(numOt, idProjeto, cdRota, placa, idDispositivo, dataLiberacao);
	}
}

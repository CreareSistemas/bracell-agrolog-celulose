package br.com.crearesistemas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.EquipamentoDAO;
import br.com.crearesistemas.model.Equipamento;
import br.com.crearesistemas.service.EquipamentoService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class EquipamentoServiceImpl extends ServiceImpl<EquipamentoDAO> implements EquipamentoService {
	@Override
	@Resource(name = "equipamentoJdbcDAO")
	public void setDao(EquipamentoDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<Equipamento> buscarEquipamentos(long mId, long customerId) {
		return super.getDao().buscarEquipamentos(mId, customerId);
	}
}

package br.com.crearesistemas.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import br.com.crearesistemas.dao.EventosDAO;
import br.com.crearesistemas.service.EventosService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.sgf.SgfEventsLoading;

@Service
public class EventosServiceImpl extends ServiceImpl<EventosDAO> implements EventosService {

	@Override
	@Resource(name = "eventosJdbcDAO")
	public void setDao(EventosDAO dao) {
		super.setDao(dao);
	}

	
		@Override
	public List<SgfEventsLoading> selecionarSgfApontamentosCampoPendentes() {
		return getDao().selecionarSgfApontamentosCampoPendentes();
	}

}

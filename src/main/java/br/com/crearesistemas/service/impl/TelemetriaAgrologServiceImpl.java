package br.com.crearesistemas.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.agrolog.TelemetriaDAO;
import br.com.crearesistemas.model.agrolog.Telemetria;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.service.TelemetriaAgrologService;

@Service
public class TelemetriaAgrologServiceImpl extends ServiceImpl<TelemetriaDAO> implements TelemetriaAgrologService {
	
	@Override
	@Resource(name = "teletriaAgrologJdbcDAO")
	public void setDao(TelemetriaDAO dao) {
		super.setDao(dao);
	}

	@Override
	public boolean inserirTelemetria(Telemetria telemetria) {
		// TODO Auto-generated method stub
		return false;
	}

}

package br.com.crearesistemas.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.PgOrdemTransporteDAO;
import br.com.crearesistemas.model.agrolog.OrdemTransporte;
import br.com.crearesistemas.service.PgOrdemTransporteService;
import br.com.crearesistemas.service.ServiceImpl;

@Service
public class PgOrdemTransporteServiceImpl extends ServiceImpl<PgOrdemTransporteDAO> implements PgOrdemTransporteService {
															  
	private static final Logger logger = Logger.getLogger(PgOrdemTransporteServiceImpl.class);
	
	
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	
	
	@Override
	@Resource(name = "pgOrdemTransporteJdbcDAO")
	public void setDao(PgOrdemTransporteDAO dao) {
		super.setDao(dao);
	}

	@Override
	public long selecionaQuantidadePorDataEstimadaChegada(Date dataInicio, Date dataFim, String idLocalDeDescarga) {
		return getDao().selecionaQuantidadePorDataEstimadaChegada(dataInicio, dataFim, idLocalDeDescarga);
	}

	@Override
	public long selecionaQuantidadePorDataRecebimento(Date dataInicio, Date dataFim, String idLocalDeDescarga) {
		return getDao().selecionaQuantidadePorDataRecebimento(dataInicio, dataFim, idLocalDeDescarga);
	}

	@Override
	public Long selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(Date dataInicio, Date dataFim) {
		return getDao().selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(dataInicio, dataFim);
	}

	@Override
	public List<OrdemTransporte> selecionaPorDataPrevistaChegadaOUDataRecebimento(Date dataInicio, Date dataFim) {
		return getDao().selecionaPorDataPrevistaChegadaOUDataRecebimento(dataInicio, dataFim) ;
	}

	
	
	
	

}
package br.com.crearesistemas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.InfracaoDAO;
import br.com.crearesistemas.model.Infracao;
import br.com.crearesistemas.service.InfracaoService;
import br.com.crearesistemas.service.ServiceImpl;

/**
 * @author cneves, 28-Set-2015
 */
@Service
public class InfracaoServiceImpl extends ServiceImpl<InfracaoDAO> implements InfracaoService {

	@Override
	@Resource(name = "infracaoJdbcDAO")
	public void setDao(InfracaoDAO dao) {
		super.setDao(dao);
	}

	public List<Infracao> buscarInfracoesVelocidadeInicio(Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes) {
		return getDao().buscarInfracoesVelocidadeInicio(mId, idadeEmHoras, quantidadeLinhas, clientes);
	}

	public List<Infracao> buscarInfracoesVelocidadeFim(Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes) {
		return getDao().buscarInfracoesVelocidadeFim(mId, idadeEmHoras, quantidadeLinhas, clientes);
	}

}

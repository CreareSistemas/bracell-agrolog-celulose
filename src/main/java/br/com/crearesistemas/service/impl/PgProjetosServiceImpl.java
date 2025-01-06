package br.com.crearesistemas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.dao.PgProjetosDAO;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.service.PgProjetosService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.service.dto.SelecionarProjetosDto;

@Service
public class PgProjetosServiceImpl extends ServiceImpl<PgProjetosDAO> implements PgProjetosService {

	@Override
	@Resource(name = "pgProjetosJdbcDAO")
	public void setDao(PgProjetosDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<ProjetoEntity> selecionarProjetos(SelecionarProjetosDto selecionar) {		
		return getDao().selecionarProjetos(selecionar.getCustomerId());		
	}

	@Override
	public ProjetoEntity selecionarProjetoProximo(Float latitude, Float longitude, Integer limiteMetros) {
		return getDao().selecionarProjetoProximos(latitude, longitude, limiteMetros);
	}

	@Override
	public ProjetoEntity selecionarProjetoById(Float latitude, Float longitude, Long localId) {
		return getDao().selecionarProjetoById(latitude, longitude, localId);
	}

	
	@Override
	public ProjetoEntity selecionarProjetoPrevisao(Long projectId, Float latitude, Float longitude) {
		return getDao().selecionarProjetoPrevisao(projectId, latitude, longitude);
	}

	@Override
	public List<ProjetoEntity> selecionarProjetosProximos(List<GruaEntity> gruas) {
		List<ProjetoEntity> result = new ArrayList<ProjetoEntity>();
		for(GruaEntity grua: gruas) {
			if (grua.getLatitude() != null &&  grua.getLongitue() != null) {
				ProjetoEntity projeto = getDao().selecionarProjetoProximos(grua.getLatitude(), grua.getLongitue(), 500);
				if (projeto != null) {
					result.add(projeto);
				}	
			}
		}
		
		return result;
	}

	/*
	 * @Override public void salvarRotograma(CadastraRotograma rotograma) {
	 * super.getDao().salvarRotograma(rotograma); }
	 */

}

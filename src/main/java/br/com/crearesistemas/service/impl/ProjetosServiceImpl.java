package br.com.crearesistemas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.dao.ProjetosDAO;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.service.ProjetosService;
import br.com.crearesistemas.service.ServiceImpl;
import br.com.crearesistemas.service.dto.SelecionarProjetosDto;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;

@Service
public class ProjetosServiceImpl extends ServiceImpl<ProjetosDAO> implements ProjetosService {

	@Override
	@Resource(name = "projetosJdbcDAO")
	public void setDao(ProjetosDAO dao) {
		super.setDao(dao);
	}

	@Override
	public List<ProjetoEntity> selecionarProjetos(SelecionarProjetosDto selecionar) {
		List<ProjetoEntity> result = new ArrayList<ProjetoEntity>();
		ProjetoEntity projeto = new ProjetoEntity();
		Config config = Config.getInstance();
		projeto.setId(config.getProjGenericoLocalId().longValue());
		projeto.setName(config.getProjGenerico());
		result.add(projeto);
		return result;
	}

	@Override
	public SgfProject selecionarProjetoByProjectCode(Long projectCode) {		
		return getDao().selecionarProjetoByProjectCode(projectCode);
	}


}

package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.service.dto.SelecionarProjetosDto;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;

public interface ProjetosService {

	public List<ProjetoEntity> selecionarProjetos(SelecionarProjetosDto selecionar);

	public SgfProject selecionarProjetoByProjectCode(Long projectCode);

}

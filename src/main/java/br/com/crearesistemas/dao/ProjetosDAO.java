package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;

public interface ProjetosDAO {

	public List<ProjetoEntity> selecionarProjetos(Long customerId);

	public SgfProject selecionarProjetoByProjectCode(Long projectCode);

}

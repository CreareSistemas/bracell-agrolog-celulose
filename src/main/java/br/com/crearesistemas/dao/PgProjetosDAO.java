package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.ProjetoEntity;

public interface PgProjetosDAO {

	public ProjetoEntity selecionarProjetoProximos(Float latitude, Float longitude, Integer limiteMetros);
	public List<ProjetoEntity> selecionarProjetos(Long customerId);
	public ProjetoEntity selecionarProjetoPrevisao(Long projectId, Float latitude, Float longitude);
	ProjetoEntity selecionarProjetoById(Float latitude, Float longitude, Long localId);

}

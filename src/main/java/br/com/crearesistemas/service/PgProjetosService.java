package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.service.dto.SelecionarProjetosDto;

public interface PgProjetosService {

	public List<ProjetoEntity> selecionarProjetos(SelecionarProjetosDto selecionar);
	public List<ProjetoEntity> selecionarProjetosProximos(List<GruaEntity> gruas);
	public ProjetoEntity selecionarProjetoProximo(Float latitude, Float longitude, Integer limiteMetros);
	public ProjetoEntity selecionarProjetoPrevisao(Long projectId, Float latitude, Float longitude);
	public ProjetoEntity selecionarProjetoById(Float latitude, Float longitude, Long localId);

}

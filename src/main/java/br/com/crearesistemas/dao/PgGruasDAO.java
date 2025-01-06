package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.GruaFlorestal;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;

public interface PgGruasDAO {

	public List<GruaEntity> selecionarGruasFlorestais(Long customerId, Boolean isProd);

	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId, Boolean isProd);

	public Boolean salvarGrua(GruaEntity grua);

	public Boolean salvar(GruaEntity grua);

	public SgfCrane selecionarSgfCrane(String plates, Boolean isProduction);

	public GruaFlorestal selecionarGruaFlorestal(String name, Boolean isProduction);
	
	
}

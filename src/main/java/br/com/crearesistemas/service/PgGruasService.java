package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.GruaFlorestal;

public interface PgGruasService {

	public List<GruaEntity> selecionarGruasFlorestais(Long customerId, Boolean isProd);

	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId, Boolean isProd);

	public Boolean salvarGrua(GruaEntity grua);

	public Boolean closeDowntime(GruaEntity grua);

	public void openDowntime(GruaEntity grua);

	public GruaFlorestal selecionarGruaFlorestal(String name, Boolean isProduction);

	

}

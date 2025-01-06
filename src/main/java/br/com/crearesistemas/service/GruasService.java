package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.GruaEntity;

public interface GruasService {

	public List<GruaEntity> selecionarGruasFlorestais(Long customerId);

	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId);
	
	public List<GruaEntity> selecionarGruasSiteIndustrialL1(Long customerId);
	
	public List<GruaEntity> selecionarGruasSiteIndustrialL2(Long customerId);

	public Boolean salvarGrua(GruaEntity grua);

	public Boolean closeDowntime(GruaEntity grua);

	public void openDowntime(GruaEntity grua);

	

}

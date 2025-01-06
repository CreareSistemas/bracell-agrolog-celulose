package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;

public interface GruasDAO {

	public List<GruaEntity> selecionarGruasFlorestais(Long customerId);

	public List<GruaEntity> selecionarGruasSiteIndustrial(Long customerId);
	
	public List<GruaEntity> selecionarGruasSiteIndustrialL1(Long customerId, Integer lineId);

	public Boolean salvarGrua(GruaEntity grua);

	public Boolean salvar(GruaEntity grua);

	public SgfCrane selecionarSgfCrane(String plates);
	
	
}

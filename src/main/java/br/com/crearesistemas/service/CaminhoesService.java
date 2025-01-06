package br.com.crearesistemas.service;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.EstagioOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.CaminhaoSiteIndustrial;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;

public interface CaminhoesService {

	public List<CaminhaoEntity> selecionarCaminhoesTransporte(Long customerId);
	
	public List<CaminhaoEntity> selecionarCaminhoesTransporteL1(Long customerId);
	
	public List<CaminhaoEntity> selecionarCaminhoesTransporteL2(Long customerId);

	public void trocaEstado(FluxoOperacional transporte, 
			EstadoOperacional transpFilaEmCampo, 
			CaminhaoEntity caminhao, 
			GruaEntity grua, Date date);

	public void trocaEstado(FluxoOperacional transporte, EstadoOperacional estado, CaminhaoEntity caminhao,
			Date date);
	
	public void trocaEstado(FluxoOperacional transporte, EstadoOperacional estado, EstagioOperacional estagio, CaminhaoEntity caminhao,
			Date date);
	
	public void informaPatioInterno(
			GruaEntity grua, CaminhaoEntity caminhao, String tipReport);

	public void salvar(CaminhaoEntity caminhao);

	public Long selMaxEventos(Long vehicleId);

	public Boolean incrementaEvento(Long vehicleId);

	public void openDowntime(CaminhaoEntity caminhao);

	public Boolean closeDowntime(CaminhaoEntity caminhao);

	SgfTruck selecionarCaminhaoTransporte(String plates);

	public Boolean isStayInState(CaminhaoEntity caminhao, List<EstadoOperacional> asList);

	public void setEstagioCaminhaoTransporte(CaminhaoEntity caminhao, CaminhaoSiteIndustrial caminhaoTransporte);

	public List<Object[]> selecionaPorFluxoSiteIndustrial(Date dataDe, Date dataAte);
	
	public List<Object[]> selecionaPorFluxoSiteIndustrialL1(Date dataDe, Date dataAte);

	

}

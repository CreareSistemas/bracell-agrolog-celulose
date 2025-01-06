package br.com.crearesistemas.dao;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;

public interface CaminhoesDAO {

	public List<CaminhaoEntity> selecionarCaminhoesTransporte(Long customerId);
	
	public List<CaminhaoEntity> selecionarCaminhoesTransporteL1(Long customerId, Integer lineCode);

	public Boolean trocaEstadoTransporte(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado, Date date);

	public Boolean trocaEstadoTransporteCarreg(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado, Date date);
	
	public Boolean trocaEstadoTransporteSite(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado, Date date);

	public Boolean salvar(CaminhaoEntity caminhao);

	public Long selMaxEventos(Long vehicleId);

	public Boolean incrementaEvento(Long vehicleId);

	public SgfTruck selecionarCaminhaoTransporte(String plates);

	public Boolean saveHistoricalState(FluxoOperacional fluxo, EstadoOperacional estado, Date dataInicio, Date dataFim, Long assetId, Integer lineId);

	public List<Object[]> selecionaPorFluxoSiteIndustrial(Date dataDe, Date dataAte);
	
	public List<Object[]> selecionaPorFluxoSiteIndustrialL1(Date dataDe, Date dataAte);
	
}

package br.com.crearesistemas.dao;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.OrdemTransporteDto;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;

public interface PgCaminhoesDAO {

	public List<CaminhaoTransporte> selecionarCaminhoesTransporte(Long customerId);

	public Boolean trocaEstadoTransporte(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado, Date date);

	public Boolean trocaEstadoTransporteCarreg(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado, Date date);
	
	public Boolean trocaEstadoTransporteSite(CaminhaoEntity caminhao, FluxoOperacional fluxo, EstadoOperacional estado, Date date);

	public Boolean salvar(CaminhaoEntity caminhao);

	public Long selMaxEventos(Long vehicleId);

	public Boolean incrementaEvento(Long vehicleId);

	public SgfTruck selecionarCaminhaoTransporte(String plates);

	public CaminhaoTransporte selectPgCaminhao(String name);

	public InterfaceSgfDto selecionarInterfaceSgf(String plates);

	public CaminhaoTransporte selectPgCaminhaoByName(String name);

	public Boolean closeOrder(CaminhaoEntity caminhao);
	
	public Boolean closeOlderOrder();

	public OrdemTransporteDto selecionarOrdemSgf(String plates);
	
}

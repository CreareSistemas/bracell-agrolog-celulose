package br.com.crearesistemas.service;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.FluxoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.service.dto.InterfaceSgfDto;
import br.com.crearesistemas.service.dto.OrdemTransporteDto;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;

public interface PgCaminhoesService {

	public List<CaminhaoTransporte> selecionarCaminhoesTransporte(Long customerId);

	public void trocaEstado(FluxoOperacional transporte, 
			EstadoOperacional transpFilaEmCampo, 
			CaminhaoEntity caminhao, 
			GruaEntity grua, Date date);

	public void informaPatioInterno(
			GruaEntity grua, CaminhaoEntity caminhao, String tipReport);

	public void salvar(CaminhaoEntity caminhao);

	public Long selMaxEventos(Long vehicleId);

	public Boolean incrementaEvento(Long vehicleId);

	public void openDowntime(CaminhaoEntity caminhao);


	SgfTruck selecionarSgfTruck(String plates);

	public CaminhaoTransporte selectPgCaminhao(String name);

	public InterfaceSgfDto selecionarInterfaceSgf(String plates);

	public CaminhaoTransporte selectPgCaminhaoByName(String name);

	public Boolean closeOrder(CaminhaoEntity caminhao);
	
	public Boolean closeOlderOrder();

	public OrdemTransporteDto selecionarOrdemSgf(String plates);
	
}

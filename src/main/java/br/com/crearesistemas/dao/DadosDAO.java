package br.com.crearesistemas.dao;

import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.sgf.wsgetchippers.SgfChipper;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.sgf.wsgetdowntimes.GetDownTimes;
import br.com.crearesistemas.sgf.wsgetlocales.SgfLocale;
import br.com.crearesistemas.sgf.wsgetpiles.SgfPile;
import br.com.crearesistemas.sgf.wsgetprojects.SgfProject;
import br.com.crearesistemas.sgf.wsgetstands.SgfStand;
import br.com.crearesistemas.sgf.wsgettrucks.SgfTruck;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;

public interface DadosDAO {


	public Boolean salvarProjeto(SgfProject projeto, Boolean isProd);

	public Boolean salvarTalhao(SgfStand stand, Boolean isProd);

	public Boolean salvarGrua(SgfCrane crane, Boolean isProd);

	public Boolean salvarCaminhao(SgfTruck truck, Boolean isProd);

	public Boolean salvarMesa(SgfChipper chipper, Boolean isProd);

	public Boolean salvarLocal(SgfLocale locale, Boolean isProd);

	public Boolean salvarPilhas(SgfPile pile, Boolean isProd);

	public Boolean salvarParadas(GetDownTimes parada, Boolean isProd);

	public SgfLocale selecionarLocal(String locale, Boolean isProd);

	public SgfPile selecionarPilha(String pile, Boolean isProd);

	public SgfChipper selecionarMesa(String chipper, Boolean isProd);

	public SgfCrane selecionarGruaPatio(String plates, Boolean isProd);

	public SgfCrane selecionarGruaFlorestal(String plates, Boolean isProd);

	public SgfChipper selecionarMesaCode(Long chipperCode, Boolean isProduction);

	public void salvarParadas(Long eventId, SetEquipmentDowntime downtime, SetEquipmentDowntimeResponse response, String infoLog, Boolean valid);
	
	public void salvarEventWorkflow(Long eventId,  Boolean valid, String message,  Boolean isProduction);
	
}

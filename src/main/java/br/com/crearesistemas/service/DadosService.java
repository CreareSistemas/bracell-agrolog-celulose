package br.com.crearesistemas.service;

import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.sgf.wsgetchippers.SgfChipper;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.sgf.wsgetlocales.SgfLocale;
import br.com.crearesistemas.sgf.wsgetpiles.SgfPile;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntime;
import br.com.crearesistemas.sgf.wssetequipmentdowntime.SetEquipmentDowntimeResponse;

public interface DadosService {
		
		public void consumirProjetos();

		public void consumirTalhoes();
		
		public void consumirGruas();

		public void consumirCaminhoes();
		
		public void consumirMesas();
		
		public void consumirLocais();
		
		public void consumirPilhas();

		public void consumirEventosParada();
		
		public SgfCrane selecionarGruaFlorestal(String plates);
		
		public SgfCrane selecionarGruaPatio(String plates);

		public SgfLocale selecionarLocal(String locale);

		public SgfPile selecionarPilha(String pile);

		public SgfChipper selecionarMesa(String chipper);
		
		public SgfChipper selecionarMesaCode(Long chipperCode);

		public void registrarStatusParada(Long eventId, SetEquipmentDowntime downtime, SetEquipmentDowntimeResponse response, String infoLog, Boolean valid);

		public void salvarEventWorkflow(Long eventId,  Boolean valid, String message, Boolean isProduction);
		
}

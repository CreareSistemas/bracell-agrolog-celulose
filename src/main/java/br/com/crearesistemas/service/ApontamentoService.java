package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.Apontamento;

public interface ApontamentoService {

		public List<Apontamento> buscarApontamentos(long mId, List<String> identificadores);

		public void processarApontamentosGruasCampo();

		public void processarApontamentosFabrica();

		public void processarApontamentosParada();

		public void encerrarOrdens();

		public void processarApontamentosCampoNaoTratados();

}

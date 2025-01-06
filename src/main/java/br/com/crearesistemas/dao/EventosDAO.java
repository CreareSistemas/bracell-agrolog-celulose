package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.sgf.SgfEventsLoading;

public interface EventosDAO {


	public List<SgfEventsLoading> selecionarSgfApontamentosCampoPendentes();

}

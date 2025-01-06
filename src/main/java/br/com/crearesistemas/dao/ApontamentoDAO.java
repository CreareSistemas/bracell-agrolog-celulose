package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.Apontamento;


public interface ApontamentoDAO {

	List<Apontamento> buscarApontamentos(long mId, List<String> identificadores);

}

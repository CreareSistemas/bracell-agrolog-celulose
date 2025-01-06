package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.Rastreamento;

public interface RastreamentoDAO {

	List<Rastreamento> buscarRastreamentos(long mId, List<Long> cliente);

}

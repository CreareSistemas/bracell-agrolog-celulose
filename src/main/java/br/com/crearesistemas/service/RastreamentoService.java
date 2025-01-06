package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.Rastreamento;

public interface RastreamentoService {

	public List<Rastreamento> buscarRastreamentos(long mId, List<Long> clientes);

}

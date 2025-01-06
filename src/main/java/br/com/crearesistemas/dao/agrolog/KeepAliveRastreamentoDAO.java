package br.com.crearesistemas.dao.agrolog;


import br.com.crearesistemas.model.agrolog.KeepAliveRastreamento;


public interface KeepAliveRastreamentoDAO {

	public KeepAliveRastreamento buscarRastreamento(long implementoId);

	public boolean insereKeepAliveRastreamento(KeepAliveRastreamento rastreamento);

	public boolean destinationHasRecord(Long implementoId);

	public boolean atualizaRastreamento(KeepAliveRastreamento rastreamento);
	

}

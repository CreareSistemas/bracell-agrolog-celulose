package br.com.crearesistemas.service;


import br.com.crearesistemas.iam.rastreamento.envia.EnviaKeepAliveRastreamento;
import br.com.crearesistemas.model.agrolog.KeepAliveRastreamento;

public interface KeepAliveRastreamentoAgrologService {

	public boolean enviarKeepAliveRastreamento(EnviaKeepAliveRastreamento entrada);
	
	public boolean inserirRastreamento(KeepAliveRastreamento rastreamento);

}

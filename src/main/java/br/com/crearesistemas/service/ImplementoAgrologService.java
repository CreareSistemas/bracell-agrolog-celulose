package br.com.crearesistemas.service;

import br.com.crearesistemas.model.agrolog.Dispositivo;
import br.com.crearesistemas.model.agrolog.Implemento;


public interface ImplementoAgrologService {
	
	public Implemento selecionaPorIdEquipamento(Long equipamentoId);

}

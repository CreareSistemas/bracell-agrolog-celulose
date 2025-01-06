package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.Equipamento;

public interface EquipamentoService {

	public List<Equipamento> buscarEquipamentos(long mId, long customerId);

}

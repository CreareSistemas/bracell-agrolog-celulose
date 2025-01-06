package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.Equipamento;

public interface EquipamentoDAO {
	List<Equipamento> buscarEquipamentos(long mId, long customerId);
}

package br.com.crearesistemas.dao;

import java.util.Date;

import br.com.crearesistemas.model.Cerca;

public interface CercaDAO {

	public boolean exists(Long clienteId, String idProjeto, String cdRota, Long objectId);

	public void update(Cerca cerca, Long clienteId);

	public long insert(Cerca cerca, Long clienteId);

	public void embarcaCerca(Long numOt, String idProjeto, String cdRota, String placa, String idDispositivo, Date dataLiberacao);

}

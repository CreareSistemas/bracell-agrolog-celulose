package br.com.crearesistemas.service;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.model.Cerca;

public interface CercaService {

	public void salvar(List<Cerca> cerca, List<Long> cliente);

	public void embarcaCerca(Long numOt, String idProjeto, String cdRota, String placa, String idDispositivo, Date dataLiberacao);

}

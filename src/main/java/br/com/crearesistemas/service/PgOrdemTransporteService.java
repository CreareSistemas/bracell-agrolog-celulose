package br.com.crearesistemas.service;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.model.agrolog.OrdemTransporte;


public interface PgOrdemTransporteService  {

	
	public long selecionaQuantidadePorDataEstimadaChegada(Date dataInicio, Date dataFim, String idLocalDeDescarga);

	public long selecionaQuantidadePorDataRecebimento(Date dataInicio, Date dataFim, String idLocalDeDescarga);

	public Long selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(Date dataInicio, Date dataFim);

	public List<OrdemTransporte> selecionaPorDataPrevistaChegadaOUDataRecebimento(Date dataInicio, Date dataFim);
	
	

}

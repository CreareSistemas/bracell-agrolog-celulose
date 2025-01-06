package br.com.crearesistemas.dao;

import java.util.Date;
import java.util.List;

import br.com.crearesistemas.model.agrolog.OrdemTransporte;

public interface PgOrdemTransporteDAO {

	long selecionaQuantidadePorDataEstimadaChegada(Date dataInicio, Date dataFim, String idLocalDeDescarga);

	long selecionaQuantidadePorDataRecebimento(Date dataInicio, Date dataFim, String idLocalDeDescarga);

	Long selecionaQuantidadePorDataProgramadaChegadaEModoProgramacao(Date dataInicio, Date dataFim);

	List<OrdemTransporte> selecionaPorDataPrevistaChegadaOUDataRecebimento(Date dataInicio, Date dataFim);

	
	
}

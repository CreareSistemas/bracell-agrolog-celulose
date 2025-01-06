package br.com.crearesistemas.service;

import java.util.List;

import br.com.crearesistemas.model.Infracao;

/**
 * @author cneves, 28-Set-2015
 */
public interface InfracaoService {

	public List<Infracao> buscarInfracoesVelocidadeInicio(Long getmId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> cliente);

	public List<Infracao> buscarInfracoesVelocidadeFim(Long getmId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> cliente);

}

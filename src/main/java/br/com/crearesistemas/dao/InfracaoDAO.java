package br.com.crearesistemas.dao;

import java.util.List;

import br.com.crearesistemas.model.Infracao;

/**
 * @author cneves, 28-Set-2015
 */
public interface InfracaoDAO {

	public List<Infracao> buscarInfracoesVelocidadeInicio(Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes);

	public List<Infracao> buscarInfracoesVelocidadeFim(Long mId, Integer idadeEmHoras, Integer quantidadeLinhas, List<Long> clientes);

}

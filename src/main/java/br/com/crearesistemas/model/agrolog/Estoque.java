package br.com.crearesistemas.model.agrolog;

import br.com.crearesistemas.enumeration.TipoEstoque;
import br.com.crearesistemas.model.Local;

public class Estoque   {
	public static enum EstadoEstoque {
		DESCONHECIDO, ATIVO, INATIVO  
	}
	
	private Long id;
	
	private Local local;
	
	// local do site (fabrica, pelotas)
	private Local localSite;
	
	private int produto;
	
	private TipoEstoque tipoEstoque = TipoEstoque.DESCONHECIDO;
	
	private String identificacao;
	
	private String descricao;
	
	private String abreviacao;
	
	private String integracao;
	
	private EstadoEstoque estado = EstadoEstoque.DESCONHECIDO;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
	
	public Local getLocalSite() {
		return localSite;
	}

	public void setLocalSite(Local localSite) {
		this.localSite = localSite;
	}

	public TipoEstoque getTipoEstoque() {
		return tipoEstoque;
	}

	public void setTipoEstoque(TipoEstoque tipoEstoque) {
		this.tipoEstoque = tipoEstoque;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}

	public String getIntegracao() {
		return integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}
	
	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public EstadoEstoque getEstado() {
		return estado;
	}

	public void setEstado(EstadoEstoque estado) {
		this.estado = estado;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Estoque) {
			if (((Estoque) obj).getId() == null)
				return super.equals(obj);
			if (this.getId() == null)
				return super.equals(obj);
			return ((Estoque) obj).getId().equals(this.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		return "Estoque:" + getIdentificacao() + " - Descrição: " + getDescricao() + " - tipoEstoque="
				+ tipoEstoque + " integracao=" + integracao ;		
	}
	
	
}
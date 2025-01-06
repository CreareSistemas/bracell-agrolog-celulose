package br.com.crearesistemas.model.agrolog;

import java.util.Date;

public class Implemento  {
	private Long id;

	private Integer numVersao;
	
	private Date dataHistorico;
	
	private String abreviacao;
	
	private String descricao;
	
	private String identificacao;
	
	private Integer tipoImplemento;
	
	private String integracao;
	
	private Long entidadeProprietaria_id;
	
	private Long rastreamentoAtual_id;
	
	private Long rastreamentoAnterior_id;
	
	private Long rastreamentoUltimo_id;
	
	private Long rastreamentoKeepAlive_id;

	//apontamentoAtualProprio
	private Long apontamentoAtual0_id;

	//apontamentoAnteriorProprio
	private Long apontamentoAnterior0_id;
	
	//apontamentoAtualCarregamento
	private Long apontamentoAtual1_id;

	//apontamentoAnteriorCarregamento
	private Long apontamentoAnterior1_id;

	//apontamentoAtualTransporte	
	private Long apontamentoAtual2_id;
	
	//apontamentoAnteriorTransporte
	private Long apontamentoAnterior2_id;

	//apontamentoAtualSiteIndustrial
	private Long apontamentoAtual3_id;

	//apontamentoAnteriorSiteIndustrial
	private Long apontamentoAnterior3_id;
	
	private Long ordemTransporteAtual_id;

	private Long ordemTransporteAnterior_id;
	
	private Long gruaMaisProxima_id;

	private Long gruaMaisDistante_id;
	
	private Long local_id;
	
	private Long localAnterior_id;
	
	private Date dataLocal;
	
	private Integer situacaoImplemento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumVersao() {
		return numVersao;
	}

	public void setNumVersao(Integer numVersao) {
		this.numVersao = numVersao;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Integer getTipoImplemento() {
		return tipoImplemento;
	}

	public void setTipoImplemento(Integer tipoImplemento) {
		this.tipoImplemento = tipoImplemento;
	}

	public String getIntegracao() {
		return integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}

	public Long getEntidadeProprietaria_id() {
		return entidadeProprietaria_id;
	}

	public void setEntidadeProprietaria_id(Long entidadeProprietaria_id) {
		this.entidadeProprietaria_id = entidadeProprietaria_id;
	}

	public Long getRastreamentoAtual_id() {
		return rastreamentoAtual_id;
	}

	public void setRastreamentoAtual_id(Long rastreamentoAtual_id) {
		this.rastreamentoAtual_id = rastreamentoAtual_id;
	}

	public Long getRastreamentoAnterior_id() {
		return rastreamentoAnterior_id;
	}

	public void setRastreamentoAnterior_id(Long rastreamentoAnterior_id) {
		this.rastreamentoAnterior_id = rastreamentoAnterior_id;
	}

	public Long getRastreamentoUltimo_id() {
		return rastreamentoUltimo_id;
	}

	public void setRastreamentoUltimo_id(Long rastreamentoUltimo_id) {
		this.rastreamentoUltimo_id = rastreamentoUltimo_id;
	}

	public Long getRastreamentoKeepAlive_id() {
		return rastreamentoKeepAlive_id;
	}

	public void setRastreamentoKeepAlive_id(Long rastreamentoKeepAlive_id) {
		this.rastreamentoKeepAlive_id = rastreamentoKeepAlive_id;
	}

	public Long getApontamentoAtual0_id() {
		return apontamentoAtual0_id;
	}

	public void setApontamentoAtual0_id(Long apontamentoAtual0_id) {
		this.apontamentoAtual0_id = apontamentoAtual0_id;
	}

	public Long getApontamentoAnterior0_id() {
		return apontamentoAnterior0_id;
	}

	public void setApontamentoAnterior0_id(Long apontamentoAnterior0_id) {
		this.apontamentoAnterior0_id = apontamentoAnterior0_id;
	}

	public Long getApontamentoAtual1_id() {
		return apontamentoAtual1_id;
	}

	public void setApontamentoAtual1_id(Long apontamentoAtual1_id) {
		this.apontamentoAtual1_id = apontamentoAtual1_id;
	}

	public Long getApontamentoAnterior1_id() {
		return apontamentoAnterior1_id;
	}

	public void setApontamentoAnterior1_id(Long apontamentoAnterior1_id) {
		this.apontamentoAnterior1_id = apontamentoAnterior1_id;
	}

	public Long getApontamentoAtual2_id() {
		return apontamentoAtual2_id;
	}

	public void setApontamentoAtual2_id(Long apontamentoAtual2_id) {
		this.apontamentoAtual2_id = apontamentoAtual2_id;
	}

	public Long getApontamentoAnterior2_id() {
		return apontamentoAnterior2_id;
	}

	public void setApontamentoAnterior2_id(Long apontamentoAnterior2_id) {
		this.apontamentoAnterior2_id = apontamentoAnterior2_id;
	}

	public Long getApontamentoAtual3_id() {
		return apontamentoAtual3_id;
	}

	public void setApontamentoAtual3_id(Long apontamentoAtual3_id) {
		this.apontamentoAtual3_id = apontamentoAtual3_id;
	}

	public Long getApontamentoAnterior3_id() {
		return apontamentoAnterior3_id;
	}

	public void setApontamentoAnterior3_id(Long apontamentoAnterior3_id) {
		this.apontamentoAnterior3_id = apontamentoAnterior3_id;
	}

	public Long getOrdemTransporteAtual_id() {
		return ordemTransporteAtual_id;
	}

	public void setOrdemTransporteAtual_id(Long ordemTransporteAtual_id) {
		this.ordemTransporteAtual_id = ordemTransporteAtual_id;
	}

	public Long getOrdemTransporteAnterior_id() {
		return ordemTransporteAnterior_id;
	}

	public void setOrdemTransporteAnterior_id(Long ordemTransporteAnterior_id) {
		this.ordemTransporteAnterior_id = ordemTransporteAnterior_id;
	}

	public Long getGruaMaisProxima_id() {
		return gruaMaisProxima_id;
	}

	public void setGruaMaisProxima_id(Long gruaMaisProxima_id) {
		this.gruaMaisProxima_id = gruaMaisProxima_id;
	}

	public Long getGruaMaisDistante_id() {
		return gruaMaisDistante_id;
	}

	public void setGruaMaisDistante_id(Long gruaMaisDistante_id) {
		this.gruaMaisDistante_id = gruaMaisDistante_id;
	}

	public Long getLocal_id() {
		return local_id;
	}

	public void setLocal_id(Long local_id) {
		this.local_id = local_id;
	}

	public Long getLocalAnterior_id() {
		return localAnterior_id;
	}

	public void setLocalAnterior_id(Long localAnterior_id) {
		this.localAnterior_id = localAnterior_id;
	}

	public Date getDataLocal() {
		return dataLocal;
	}

	public void setDataLocal(Date dataLocal) {
		this.dataLocal = dataLocal;
	}

	public Integer getSituacaoImplemento() {
		return situacaoImplemento;
	}

	public void setSituacaoImplemento(Integer situacaoImplemento) {
		this.situacaoImplemento = situacaoImplemento;
	}
	
		
}

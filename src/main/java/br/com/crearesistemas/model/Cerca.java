package br.com.crearesistemas.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Cerca {

	private Long objectId;
	private String idProjeto;
	private String cdRota;
	private Date dataAlteracao;
	private String descricao;
	private Integer velocidadePadrao;
	private Integer velocidadeEvento; // padrao - 10

	private String pontoSuperior;
	private String pontoInferior;
	private String geometria;

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(String idProjeto) {
		this.idProjeto = idProjeto;
	}

	public String getCdRota() {
		return cdRota;
	}

	public void setCdRota(String cdRota) {
		this.cdRota = cdRota;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getVelocidadePadrao() {
		return velocidadePadrao;
	}

	public void setVelocidadePadrao(Integer velocidadePadrao) {
		this.velocidadePadrao = velocidadePadrao;
	}

	public Integer getVelocidadeEvento() {
		return velocidadeEvento;
	}

	public void setVelocidadeEvento(Integer velocidadeEvento) {
		this.velocidadeEvento = velocidadeEvento;
	}

	public String getPontoSuperior() {
		return pontoSuperior;
	}

	public void setPontoSuperior(String pontoSuperior) {
		this.pontoSuperior = pontoSuperior;
	}

	public String getPontoInferior() {
		return pontoInferior;
	}

	public void setPontoInferior(String pontoInferior) {
		this.pontoInferior = pontoInferior;
	}

	public String getGeometria() {
		return geometria;
	}

	public void setGeometria(String geometria) {
		this.geometria = geometria;
	}

}

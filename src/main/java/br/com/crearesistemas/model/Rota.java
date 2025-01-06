package br.com.crearesistemas.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author cneves, 13-Jul-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "model.crearesistemas.com.br")
public final class Rota extends ModelBase {

	private long projeto;
	private long cod_rota;
	private Date data_registro;
	private String descricao;
	private int velocPadrao;
	private int velocEvento;
	private Coordenadas coordenadas;
	private Geometria geometria;

	public long getProjeto() {
		return projeto;
	}

	public void setProjeto(long projeto) {
		this.projeto = projeto;
	}

	public long getCod_rota() {
		return cod_rota;
	}

	public void setCod_rota(long cod_rota) {
		this.cod_rota = cod_rota;
	}

	public Date getData_registro() {
		return data_registro;
	}

	public void setData_registro(Date data_registro) {
		this.data_registro = data_registro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getVelocPadrao() {
		return velocPadrao;
	}

	public void setVelocPadrao(int velocPadrao) {
		this.velocPadrao = velocPadrao;
	}

	public int getVelocEvento() {
		return velocEvento;
	}

	public void setVelocEvento(int velocEvento) {
		this.velocEvento = velocEvento;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Geometria getGeometria() {
		return geometria;
	}

	public void setGeometria(Geometria geometria) {
		this.geometria = geometria;
	}

}

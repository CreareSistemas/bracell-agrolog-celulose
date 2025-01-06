package br.com.crearesistemas.pcct.wsrecebeprevisoes.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Previsao {

	@XmlElement(required = true)
	private Long indexador;

	@XmlElement(required = true)
	private String identificacao;

	@XmlElement(required = true)
	private Long programado;

	@XmlElement(required = true)
	private Long realizado;

	@XmlElement(required = true)
	private Long previsto;

	public void setIndexador(Long indexador) {
		this.indexador = indexador;
	}

	public Long getIndexador() {
		return indexador;
	}

	public void setId(Long id) {
		this.indexador = id;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Long getProgramado() {
		return programado;
	}

	public void setProgramado(Long programado) {
		this.programado = programado;
	}

	public Long getRealizado() {
		return realizado;
	}

	public void setRealizado(Long realizado) {
		this.realizado = realizado;
	}

	public Long getPrevisto() {
		return previsto;
	}

	public void setPrevisto(Long previsto) {
		this.previsto = previsto;
	}

}

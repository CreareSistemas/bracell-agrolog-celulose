package br.com.crearesistemas.pcct.wsrecebepartidas.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Partida 
{
	@XmlElement(required = true)
	private Long idOrdemTransporte;

	@XmlElement(required = true)
	private String idCaminhao;
	
	@XmlElement(required = true)
	private String identificacao;
	
	@XmlElement(required = true)
	private Date dataChegada;
	
	@XmlElement(required = true)
	private Date dataJornada;

	public Long getIdOrdemTransporte() {
		return idOrdemTransporte;
	}

	public void setIdOrdemTransporte(Long idOrdemTransporte) {
		this.idOrdemTransporte = idOrdemTransporte;
	}

	public String getIdCaminhao() {
		return idCaminhao;
	}

	public void setIdCaminhao(String idCaminhao) {
		this.idCaminhao = idCaminhao;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public Date getDataJornada() {
		return dataJornada;
	}

	public void setDataJornada(Date dataJornada) {
		this.dataJornada = dataJornada;
	}
}

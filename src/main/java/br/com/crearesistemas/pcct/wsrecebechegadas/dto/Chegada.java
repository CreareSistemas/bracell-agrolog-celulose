package br.com.crearesistemas.pcct.wsrecebechegadas.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Chegada 
{
	@XmlElement(required = true)
	private Long idOrdemTransporte;

	@XmlElement(required = true)
	private String idCaminhao;
	
	@XmlElement(required = true)
	private String identificacao;
	
	@XmlElement(required = true)
	private Date dataPrevistaChegada;

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

	public Date getDataPrevistaChegada() {
		return dataPrevistaChegada;
	}

	public void setDataPrevistaChegada(Date dataPrevistaChegada) {
		this.dataPrevistaChegada = dataPrevistaChegada;
	}
	
}

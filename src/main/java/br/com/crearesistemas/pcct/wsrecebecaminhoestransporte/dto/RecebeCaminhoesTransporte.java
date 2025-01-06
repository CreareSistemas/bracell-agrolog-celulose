package br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeCaminhoesTransporte {

	private Date dataHistorico;
	
	private String idLocalDeDescarga;

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public String getIdLocalDeDescarga() {
		return idLocalDeDescarga;
	}

	public void setIdLocalDeDescarga(String idLocalDeDescarga) {
		this.idLocalDeDescarga = idLocalDeDescarga;
	}

	
}

package br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeTemposMediosCicloInterno {
	@XmlElement(required=true)
	private Date dataDe;
	
	@XmlElement(required=true)
	private Date dataAte;
	
	private String localDeDescarga;

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}

	public String getLocalDeDescarga() {
		return this.localDeDescarga;
	}

	public void setLocalDeDescarga(String localDeDescarga) {
		this.localDeDescarga = localDeDescarga;
	}
	
}

package br.com.crearesistemas.pcct.wsrecebeprevisoes.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebePrevisoes {
	
	@XmlElement(required = true)
	private Date dataBase;
	
	private String idLocalDeDescarga;

	public Date getDataBase() {
		return dataBase;
	}

	public void setDataBase(Date data) {
		this.dataBase = data;
	}

	public String getIdLocalDeDescarga() {
		return idLocalDeDescarga;
	}

	public void setIdLocalDeDescarga(String idLocalDeDescarga) {
		this.idLocalDeDescarga = idLocalDeDescarga;
	}
	
	
}

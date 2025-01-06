package br.com.crearesistemas.pcct.wsrecebepartidas.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebePartidas {

	private String localDeDescarga;

	public String getLocalDeDescarga() {
		return localDeDescarga;
	}

	public void setLocalDeDescarga(String localDeDescarga) {
		this.localDeDescarga = localDeDescarga;
	}
	
}

package br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeCaminhoesSiteIndustrial {

	private Date dataHistorico;
	
	private String fluxoOperacional;

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public String getFluxoOperacional() {
		return fluxoOperacional;
	}

	public void setFluxoOperacional(String fluxoOperacional) {
		this.fluxoOperacional = fluxoOperacional;
	}

}

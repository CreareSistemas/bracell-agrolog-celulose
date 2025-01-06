package br.com.crearesistemas.pcsil1.wsrecebegruassiteindustrial.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeGruasSiteIndustrial {

	private Date dataHistorico;
	
	private String localDeOperacao;
	
	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public String getLocalDeOperacao() {
		return localDeOperacao;
	}

	public void setLocalDeOperacao(String localDeOperacao) {
		this.localDeOperacao = localDeOperacao;
	}
	
	

}

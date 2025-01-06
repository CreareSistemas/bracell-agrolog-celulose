package br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeGruasFlorestais {

	private Date dataHistorico;
	
	private String idLocalDeDescarga;
	
	private Boolean incluirGruasSemOT;
	

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

	public Boolean getIncluirGruasSemOT() {
		return incluirGruasSemOT;
	}

	public void setIncluirGruasSemOT(Boolean incluirGruasSemOT) {
		this.incluirGruasSemOT = incluirGruasSemOT;
	}
	
}

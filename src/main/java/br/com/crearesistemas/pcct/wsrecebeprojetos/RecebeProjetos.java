package br.com.crearesistemas.pcct.wsrecebeprojetos;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeProjetos  {

	@XmlElement(required = true)
    private Date dataHistorico;
    
	
	// filtra somente o destino
	private String idLocalDeDescarga;
	
	// considera projetos sem ot programada
	private Boolean incluirProjetosSemOT;

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

	public Boolean getIncluirProjetosSemOT() {
		return incluirProjetosSemOT;
	}

	public void setIncluirProjetosSemOT(Boolean incluirProjetosSemOT) {
		this.incluirProjetosSemOT = incluirProjetosSemOT;
	}

}

package br.com.crearesistemas.iam.rotograma.embarca;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author cneves, 21-Set-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RotogramaRequest {

	private Long numOt;
	private String idProjeto;
	private String cdRota;
	private String placa;
	private String idDispositivo;
	private Date dataLiberacao;

	public Long getNumOt() {
		return numOt;
	}

	public void setNumOt(Long numOt) {
		this.numOt = numOt;
	}

	public String getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(String idProjeto) {
		this.idProjeto = idProjeto;
	}

	public String getCdRota() {
		return cdRota;
	}

	public void setCdRota(String cdRota) {
		this.cdRota = cdRota;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(String idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

}

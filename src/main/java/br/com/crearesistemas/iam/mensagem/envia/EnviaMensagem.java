package br.com.crearesistemas.iam.mensagem.envia;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.DTOBase;

/**
 * @author cneves, 19-Jun-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EnviaMensagem extends DTOBase {

	private int sequencial;

	@XmlElement(required = true)
	private Date data;

	@XmlElement(required = true)
	private String idDispositivo;

	private String mensagemLivre;

	private Integer idMensagemPredefinida;

	private Integer idMensagem;

	private Integer grpRespPredefinidas;

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(String idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public String getMensagemLivre() {
		return mensagemLivre;
	}

	public void setMensagemLivre(String mensagemLivre) {
		this.mensagemLivre = mensagemLivre;
	}

	public Integer getIdMensagemPredefinida() {
		return idMensagemPredefinida;
	}

	public void setIdMensagemPredefinida(Integer idMensagemPredefinida) {
		this.idMensagemPredefinida = idMensagemPredefinida;
	}

	public Integer getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(Integer idMensagem) {
		this.idMensagem = idMensagem;
	}

	public Integer getGrpRespPredefinidas() {
		return grpRespPredefinidas;
	}

	public void setGrpRespPredefinidas(Integer grpRespPredefinidas) {
		this.grpRespPredefinidas = grpRespPredefinidas;
	}

}

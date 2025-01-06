package br.com.crearesistemas.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author cneves; 19-Jun-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "model.crearesistemas.com.br")
public class Mensagem extends ModelBase {

	public static final int STATUS_PENDENTE_DE_ENVIO = 2;
	public static final int STATUS_LEITURA_CONFIRMADA = 3;
	public static final int STATUS_ENVIADO_PARA_EQUIPAMENTO = 4;
	public static final int STATUS_ENVIADO_PARA_AGROLOG = 5;

	private long id;
	private Date registerDate;
	private Date updateDate;
	private long vehicleId;
	private long customerId;
	private long customerChildId;
	private String prefix;
	private String msg;
	private int status;
	private Integer grpRespPredefinidas;
	private Integer idMensagemPredefinida;
	private int idMensagem;
	private int sequencial;
	private Integer sequencialPergunta;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCustomerChildId() {
		return customerChildId;
	}

	public void setCustomerChildId(long customerChildId) {
		this.customerChildId = customerChildId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getGrpRespPredefinidas() {
		return grpRespPredefinidas;
	}

	public void setGrpRespPredefinidas(Integer grpRespPredefinidas) {
		this.grpRespPredefinidas = grpRespPredefinidas;
	}

	public Integer getIdMensagemPredefinida() {
		return idMensagemPredefinida;
	}

	public void setIdMensagemPredefinida(Integer idMensagemPredefinida) {
		this.idMensagemPredefinida = idMensagemPredefinida;
	}

	public int getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(int idMensagem) {
		this.idMensagem = idMensagem;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public Integer getSequencialPergunta() {
		return sequencialPergunta;
	}

	public void setSequencialPergunta(Integer sequencialPergunta) {
		this.sequencialPergunta = sequencialPergunta;
	}

}

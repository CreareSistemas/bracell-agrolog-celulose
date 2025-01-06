package br.com.crearesistemas.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(namespace = "model.crearesistemas.com.br")
public class Apontamento extends ModelBase {

	private Long vehicle_id;

	private Long mId;
	
	private String dispositivoId;
	
	private String estadoOperacional;

	private String dataHora;

	private String latitude;

	private String longitude;

	private String projeto;
	
	private String talhao;
	
	private String pilha;
	
	private String placa;


	public Long getMId() {
		return mId;
	}

	public void setMId(Long mId) {
		this.mId = mId;
	}
	

	@XmlAttribute(name = "vehicleid", required = true)
	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public String getEstadoOperacional() {
		return estadoOperacional;
	}

	public void setEstadoOperacional(String estadoOperacional) {
		this.estadoOperacional = estadoOperacional;
	}

	@XmlElement(name = "datahora", required = true)
	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	@XmlElement(name = "latitude", required = true)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@XmlElement(name = "longitude", required = true)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(String dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

	public String getTalhao() {
		return talhao;
	}

	public void setTalhao(String talhao) {
		this.talhao = talhao;
	}

	public String getPilha() {
		return pilha;
	}

	public void setPilha(String pilha) {
		this.pilha = pilha;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}


}

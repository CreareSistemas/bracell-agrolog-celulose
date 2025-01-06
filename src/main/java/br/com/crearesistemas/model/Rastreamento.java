package br.com.crearesistemas.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(namespace = "model.crearesistemas.com.br")
public class Rastreamento extends ModelBase {

	private Long vehicle_id;

	private String dispositivoId;

	private Long mId;

	private String datehour;

	private String latitude;

	private String longitude;

	private Long driverId;

	private float speed;

	private int rpm;

	private long hodometer;

	private String imei;

	private String vehicle_plates;

	private String vehicle_prefix;

	private long horimetro;

	private int ignicao;
	
	private int origem;

	private List<Discretas> discretas = new ArrayList<Discretas>();

	@XmlElementWrapper(name = "sensoresList")
	@XmlElement(name = "sensores")
	public List<Discretas> getDiscretas() {
		return discretas;
	}

	public void setDiscretas(List<Discretas> discretas) {
		this.discretas = discretas;
	}

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

	@XmlElement(name = "datahora", required = true)
	public String getDatehour() {
		return datehour;
	}

	public void setDatehour(String datehour) {
		this.datehour = datehour;
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

	@XmlElement(name = "driverid", required = true)
	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	@XmlElement(name = "velocidade", required = true)
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@XmlElement(name = "rotacao", required = true)
	public int getRpm() {
		return rpm;
	}

	public void setRpm(int rpm) {
		this.rpm = rpm;
	}

	@XmlElement(name = "hodometro", required = true)
	public long getHodometer() {
		return hodometer;
	}

	public void setHodometer(long hodometer) {
		this.hodometer = hodometer;
	}

	@XmlElement(name = "dispositivo", required = true)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@XmlElement(name = "placa", required = true)
	public String getVehicle_plates() {
		return vehicle_plates;
	}

	public void setVehicle_plates(String vehicle_plates) {
		this.vehicle_plates = vehicle_plates;
	}

	@XmlElement(name = "prefixo", required = true)
	public String getVehicle_prefix() {
		return vehicle_prefix;
	}

	public void setVehicle_prefix(String vehicle_prefix) {
		this.vehicle_prefix = vehicle_prefix;
	}

	public String getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(String dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	public long getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(long horimetro) {
		this.horimetro = horimetro;
	}

	public int getIgnicao() {
		return ignicao;
	}

	public void setIgnicao(int ignicao) {
		this.ignicao = ignicao;
	}

	public int getOrigem() {
		return origem;
	}

	public void setOrigem(int origem) {
		this.origem = origem;
	}

}

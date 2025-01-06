package br.com.crearesistemas.model;

import java.util.Date;

public class RastreamentoEntity {
	private Long vehicle_id;

	private String dispositivoId;

	private Long mId;

	private Date datehour;

	private Double latitude;

	private Double longitude;

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

	public RastreamentoEntity(Float latitude, Float longitude) {
		this.latitude = latitude.doubleValue();
		this.longitude = longitude.doubleValue();
		
	}

	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public String getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(String dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}	

	public Date getDatehour() {
		return datehour;
	}

	public void setDatehour(Date datehour) {
		this.datehour = datehour;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getRpm() {
		return rpm;
	}

	public void setRpm(int rpm) {
		this.rpm = rpm;
	}

	public long getHodometer() {
		return hodometer;
	}

	public void setHodometer(long hodometer) {
		this.hodometer = hodometer;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getVehicle_plates() {
		return vehicle_plates;
	}

	public void setVehicle_plates(String vehicle_plates) {
		this.vehicle_plates = vehicle_plates;
	}

	public String getVehicle_prefix() {
		return vehicle_prefix;
	}

	public void setVehicle_prefix(String vehicle_prefix) {
		this.vehicle_prefix = vehicle_prefix;
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

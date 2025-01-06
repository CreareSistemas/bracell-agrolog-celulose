package br.com.crearesistemas.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(namespace = "model.crearesistemas.com.br")
public class Equipamento extends ModelBase {
	private Long mId;

	private Long vehicle_id;

	private String vehicle_prefix;

	private String vehicle_plates;

	private String device_id;

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	@XmlAttribute(name = "vehicleid", required = true)
	public Long getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(Long vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	@XmlAttribute(name = "veiculoprefixo", required = true)
	public String getVehicle_prefix() {
		return vehicle_prefix;
	}

	public void setVehicle_prefix(String vehicle_prefix) {
		this.vehicle_prefix = vehicle_prefix;
	}

	@XmlAttribute(name = "veiculoplaca", required = true)
	public String getVehicle_plates() {
		return vehicle_plates;
	}

	public void setVehicle_plates(String vehicle_plates) {
		this.vehicle_plates = vehicle_plates;
	}

	@XmlAttribute(name = "dispositivo", required = true)
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

}

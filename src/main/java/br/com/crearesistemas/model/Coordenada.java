package br.com.crearesistemas.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author cneves, 13-Jul-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "model.crearesistemas.com.br")
public class Coordenada extends ModelBase {

	private double latitude;
	private double longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}

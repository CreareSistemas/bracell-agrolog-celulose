package br.com.crearesistemas.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author cneves, 13-Jul-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "model.crearesistemas.com.br")
public final class Coordenadas extends ModelBase {

	private Coordenada superior;
	private Coordenada inferior;

	public Coordenada getSuperior() {
		return superior;
	}

	public void setSuperior(Coordenada superior) {
		this.superior = superior;
	}

	public Coordenada getInferior() {
		return inferior;
	}

	public void setInferior(Coordenada inferior) {
		this.inferior = inferior;
	}

}

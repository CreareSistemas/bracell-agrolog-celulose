package br.com.crearesistemas.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author cneves, 13-Jul-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "model.crearesistemas.com.br")
public final class Geometria extends ModelBase {

	@XmlElement(name = "ponto")
	private List<Coordenada> pontos;

	public List<Coordenada> getPontos() {
		return pontos;
	}

	public void setPontos(List<Coordenada> pontos) {
		this.pontos = pontos;
	}
}

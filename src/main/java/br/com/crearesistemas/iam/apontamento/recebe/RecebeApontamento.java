package br.com.crearesistemas.iam.apontamento.recebe;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import br.com.crearesistemas.iam.DTOBase;

/**
 * @author eninomia
 */
public class RecebeApontamento extends DTOBase {

	@XmlElementWrapper
	@XmlElement(name = "codIdentificador")
	private List<String> identificadores;

	public List<String> getIdentificadores() {
		return identificadores;
	}

	public void setIdentificadores(List<String> identificadores) {
		this.identificadores = identificadores;
	}

	@Override
	public String toString() {
		return "RecebeApontamento [identificadores=" + identificadores + "]";
	}

	
	
}

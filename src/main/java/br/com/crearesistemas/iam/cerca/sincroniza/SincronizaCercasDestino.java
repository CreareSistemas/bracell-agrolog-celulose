package br.com.crearesistemas.iam.cerca.sincroniza;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;

import br.com.crearesistemas.iam.DTOBase;
import br.com.crearesistemas.model.Cerca;

/**
 * @author cneves
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SincronizaCercasDestino extends DTOBase {

	@XmlElementWrapper(name = "cercas")
	private List<Cerca> cerca;

	public List<Cerca> getCerca() {
		return cerca;
	}

	public void setCerca(List<Cerca> cerca) {
		this.cerca = cerca;
	}

}

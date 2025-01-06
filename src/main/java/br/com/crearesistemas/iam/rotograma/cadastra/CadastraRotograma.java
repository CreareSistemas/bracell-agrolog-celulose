package br.com.crearesistemas.iam.rotograma.cadastra;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import br.com.crearesistemas.iam.DTOBase;
import br.com.crearesistemas.model.Rota;

/**
 * @author cneves
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CadastraRotograma extends DTOBase {

	@XmlElementWrapper
	@XmlElement(name = "rota")
	private List<Rota> rotogramas;

	public List<Rota> getRotogramas() {
		return rotogramas;
	}

	public void setRotogramas(List<Rota> rotogramas) {
		this.rotogramas = rotogramas;
	}

}

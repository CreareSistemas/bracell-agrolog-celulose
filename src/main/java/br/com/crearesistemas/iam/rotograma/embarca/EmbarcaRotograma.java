package br.com.crearesistemas.iam.rotograma.embarca;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author cneves, 21-Set-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EmbarcaRotograma {

	@XmlElementWrapper(name = "rotogramas", required = true)
	private List<RotogramaRequest> rotograma;

	public List<RotogramaRequest> getRotograma() {
		return rotograma;
	}

	public void setRotograma(List<RotogramaRequest> rotograma) {
		this.rotograma = rotograma;
	}

}

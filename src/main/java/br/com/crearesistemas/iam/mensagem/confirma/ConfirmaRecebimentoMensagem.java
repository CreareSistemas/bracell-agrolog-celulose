package br.com.crearesistemas.iam.mensagem.confirma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.DTOBase;

/**
 * @author cneves, 26-Jun-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmaRecebimentoMensagem extends DTOBase {

	@XmlElement(required = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

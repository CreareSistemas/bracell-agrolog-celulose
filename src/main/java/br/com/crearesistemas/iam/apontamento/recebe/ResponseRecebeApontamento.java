package br.com.crearesistemas.iam.apontamento.recebe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.ResponseBase;
import br.com.crearesistemas.model.Apontamento;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseRecebeApontamento extends ResponseBase {

	@XmlElement(required = true)
	private List<Apontamento> apontamentos;

	public List<Apontamento> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<Apontamento> apontamento) {
		this.apontamentos = apontamento;
	}

}

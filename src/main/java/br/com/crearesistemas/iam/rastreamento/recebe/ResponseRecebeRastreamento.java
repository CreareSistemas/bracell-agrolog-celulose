package br.com.crearesistemas.iam.rastreamento.recebe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.ResponseBase;
import br.com.crearesistemas.model.Rastreamento;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseRecebeRastreamento extends ResponseBase {

	@XmlElement(required = true)
	private List<Rastreamento> rastreamento;

	public List<Rastreamento> getRastreamento() {
		return rastreamento;
	}

	public void setRastreamento(List<Rastreamento> rastreamento) {
		this.rastreamento = rastreamento;
	}

}

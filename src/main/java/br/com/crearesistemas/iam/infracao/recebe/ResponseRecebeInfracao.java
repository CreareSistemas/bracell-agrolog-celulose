package br.com.crearesistemas.iam.infracao.recebe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.ResponseBase;
import br.com.crearesistemas.model.Infracao;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseRecebeInfracao extends ResponseBase {

	@XmlElement(required = true)
	private List<Infracao> infracoes;

	public List<Infracao> getInfracoes() {
		return infracoes;
	}

	public void setInfracoes(List<Infracao> infracoes) {
		this.infracoes = infracoes;
	}

}
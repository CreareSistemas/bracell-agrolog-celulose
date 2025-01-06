package br.com.crearesistemas.iam.infracao.recebe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.DTOBase;

@XmlAccessorType(XmlAccessType.FIELD)
public class RecebeInfracao extends DTOBase {

	@XmlElement(required = true)
	private Integer idadeEmHoras;

	@XmlElement(required = true)
	private Integer quantidadeLinhas;

	public Integer getIdadeEmHoras() {
		return idadeEmHoras;
	}

	public void setIdadeEmHoras(Integer idadeEmHoras) {
		this.idadeEmHoras = idadeEmHoras;
	}

	public Integer getQuantidadeLinhas() {
		return quantidadeLinhas;
	}

	public void setQuantidadeLinhas(Integer quantidadeLinhas) {
		this.quantidadeLinhas = quantidadeLinhas;
	}

}

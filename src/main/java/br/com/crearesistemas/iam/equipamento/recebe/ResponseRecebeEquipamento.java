package br.com.crearesistemas.iam.equipamento.recebe;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import br.com.crearesistemas.iam.ResponseBase;
import br.com.crearesistemas.model.EntidadeProprietaria;
import br.com.crearesistemas.model.Equipamento;


@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseRecebeEquipamento extends ResponseBase {

	@XmlElement(required = true)
	private List<Equipamento> Equipamento;
	
	@XmlElement(required = true)
	private EntidadeProprietaria EntidadeProprietaria;
	
	public List<Equipamento> getEquipamento() {
		return Equipamento;
	}

	public void setEquipamento(List<Equipamento> Equipamento) {
		this.Equipamento = Equipamento;
	}

	public EntidadeProprietaria getEntidadeProprietaria() {
		return EntidadeProprietaria;
	}

	public void setEntidadeProprietaria(EntidadeProprietaria entidadeProprietaria) {
		EntidadeProprietaria = entidadeProprietaria;
	}

}
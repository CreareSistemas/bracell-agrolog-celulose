package br.com.crearesistemas.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(namespace = "model.crearesistemas.com.br")
public class EntidadeProprietaria extends ModelBase {

	private Long id;
	
	private String descricao;
	
	private String customer_child_name;

	private String customer_cnpj;

	private String customer_insc_est;

	@XmlAttribute(name = "nomecliente", required = true)
	public String getCustomer_child_name() {
		return customer_child_name;
	}

	public void setCustomer_child_name(String customer_child_name) {
		this.customer_child_name = customer_child_name;
	}

	@XmlAttribute(name = "cnpj", required = true)
	public String getCustomer_cnpj() {
		return customer_cnpj;
	}

	public void setCustomer_cnpj(String customer_cnpj) {
		this.customer_cnpj = customer_cnpj;
	}

	@XmlAttribute(name = "inscricaoestadual", required = true)
	public String getCustomer_insc_est() {
		return customer_insc_est;
	}

	public void setCustomer_insc_est(String customer_insc_est) {
		this.customer_insc_est = customer_insc_est;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}

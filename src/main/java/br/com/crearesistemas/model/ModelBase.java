package br.com.crearesistemas.model;

import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.util.IntrospectionUtils;

@XmlType(namespace = "model.crearesistemas.com.br")
public abstract class ModelBase {

	public String toString() {
		return IntrospectionUtils.toString(this);
	}

}

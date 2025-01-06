package br.com.crearesistemas.iam;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.util.IntrospectionUtils;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "iam.crearesistemas.com.br")
public abstract class DTOBase {

	@XmlElement
	private Long mId;

	@XmlElementWrapper
	@XmlElement(name = "codCliente")
	private List<Long> cliente;

	public Long getmId() {
		return mId;
	}

	public void setmId(Long mId) {
		this.mId = mId;
	}

	public List<Long> getCliente() {
		return cliente;
	}

	public void setCliente(List<Long> cliente) {
		this.cliente = cliente;
	}

	public String toString() {
		return IntrospectionUtils.toString(this);
	}

}

package br.com.crearesistemas.model.credencial;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.model.ModelBase;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "credencial.model.crearesistemas.com.br")
public class Credencial extends ModelBase {

	@XmlElement(required = true)
	private String usuario;

	@XmlElement(required = true)
	private String senha;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void autenticacao() {
		if (!usuario.equals("creare") || !senha.equals("Creare4455")) {
			throw new RuntimeException("Usu\u00a0rio n\u00c6o autenticado");
		}
	}

}

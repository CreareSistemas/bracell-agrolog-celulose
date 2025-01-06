package br.com.crearesistemas.pcsil1.credencial;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Credencial {

    @XmlElement(required = true)
    protected String usuario;
   
    @XmlElement(required = true)
    protected String senha;
 
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
		if(!getUsuario().equals("PCSI") || !getSenha().equals("PCSI1234")){
			SOAPFault soapFault = null;
			try {
				SOAPFactory sf = SOAPFactory.newInstance();
				soapFault = sf.createFault();
				soapFault.setFaultString("Usuário não autenticado");
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new SOAPFaultException(soapFault);
		}
	}
	
}

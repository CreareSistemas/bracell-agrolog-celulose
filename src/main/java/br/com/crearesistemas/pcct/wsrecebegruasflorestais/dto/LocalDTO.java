package br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class LocalDTO {
	// informação do local de destino
	private Long idLocal;
	
	// identificacao do local de destino
	@XmlTransient
	private String identificacao;

	@XmlTransient
	private String integracao;


	public LocalDTO mock() {
		
		setIdLocal(15L);
		
		return this;
		
	}
	
	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getIntegracao() {
		return integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}

	/*
	 * public static LocalDTO getLocalDto(Local local) { LocalDTO localDto = new
	 * LocalDTO(); localDto.setIdentificacao(local.getIdentificacao());
	 * localDto.setIdLocal(local.getId());
	 * localDto.setIntegracao(local.getIntegracao());
	 * 
	 * return localDto; }
	 */	
}

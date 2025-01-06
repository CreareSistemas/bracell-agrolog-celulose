package br.com.crearesistemas.dao.dto;

public enum WorkflowIntegrationStatus {
		UNKNOWN(5),
		VALIDO(1),
		ERROR(2),
		PENDENTE(3),
		INEXISTENTE(4);
	
	Integer code;
	
	WorkflowIntegrationStatus(Integer code){
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}

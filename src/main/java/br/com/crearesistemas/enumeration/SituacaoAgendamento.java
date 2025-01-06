package br.com.crearesistemas.enumeration;

public enum SituacaoAgendamento {
	DESCONHECIDA("Desconhecida"),
	PENDENTE("Pendente"),
	APROVADA("Aprovada"),
	CANCELADO("Cancelado"),
	EXPIRADO("Expirado"),
	FINALIZADO("Finalizada");
	
	private String value;

	private SituacaoAgendamento(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

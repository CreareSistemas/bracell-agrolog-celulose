package br.com.crearesistemas.enumeration;

public enum SentidoLocal {
	DESCONHECIDO("Desconhecido"),
	ENTRADA("entrada"),
	NEUTRO("neutro"),
	SAIDA("saida");
	
	private String value;

	private SentidoLocal(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

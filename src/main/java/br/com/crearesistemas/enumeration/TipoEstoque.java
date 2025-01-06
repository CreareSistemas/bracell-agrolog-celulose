package br.com.crearesistemas.enumeration;

public enum TipoEstoque {
	DESCONHECIDO("Desconhecido"),
	PILHA("Pilha"),
	MESA("Mesa"),
	SILO("Silo"),
	BARCACA("Barcaca");
	
	private String value;

	private TipoEstoque(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

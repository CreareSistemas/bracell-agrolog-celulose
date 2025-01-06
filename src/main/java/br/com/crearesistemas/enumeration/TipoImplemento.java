package br.com.crearesistemas.enumeration;

public enum TipoImplemento {
	DESCONHECIDO("Desconhecido"),
	CAMINHAO_TRANSPORTE("Caminhão de Transporte"),
	GRUA_FLORESTAL("Grua Florestal"),
	GRUA_MOVEL("Grua Móvel"),
	GRUA_FIXA("Grua Fixa"),
	CAMINHAO_MOVIMENTACAO("Caminhão de Movimentação"),
	BARCACA("Barcaça"),
	GRUA_MULTIDOCKER("Grua Multidocker"), 
	CARRO("Carro");
	
	private String value;

	private TipoImplemento(String value) {
		this.value = value;
	}
	
	public static TipoImplemento valueOf(int i) {
		return values()[i];
	} 

	@Override
	public String toString() {
		return value;
	}
}

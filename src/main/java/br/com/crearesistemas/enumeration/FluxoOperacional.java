package br.com.crearesistemas.enumeration;

public enum FluxoOperacional {
	
	PROPRIO("Próprio"),
	CARREGAMENTO("Carregamento"),
	TRANSPORTE("Transporte"),
	SITE_INDUSTRIAL("Site Industrial"), 
	PORTO_MARITIMO("Porto Maritimo");
	
	private String value;

	private FluxoOperacional(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}

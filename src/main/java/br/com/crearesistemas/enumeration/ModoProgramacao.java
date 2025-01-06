package br.com.crearesistemas.enumeration;

public enum ModoProgramacao {
	DESCONHECIDO("Desconhecido", -1),
	AUTOMATICO("Gerado diretamente pelo ASICAM", 0),
	MANUAL("Gerado Manualmente no SGF", 1),
	REPROGRAMADO("Reprogramado no SGF", 2);
	
	private String value;
	private int codigo;

	public static ModoProgramacao getByCodigo(Integer codigo) {
		if (codigo != null){
			for(ModoProgramacao m : values()) {
				if(m.codigo == codigo.intValue()) return m;
			}
		}	
	    return ModoProgramacao.DESCONHECIDO;
	 }
	
	private ModoProgramacao(String value, int codigo) {
		this.value = value;
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
}

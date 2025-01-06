package br.com.crearesistemas.enumeration;

public enum SituacaoOrdemTransporte {
	DESCONHECIDA("Desconhecida"),
	PROGRAMADA("Programada", "P"),
	REPROGRAMADA("Reprogramada", "R"),
	CANCELADA("Cancelada"),
	LIBERADA("Liberada"),
	INTERROMPIDA("Interrompida"),
	EXECUTADA("Executada");
	
	private String value;
	private String codigo;

	private SituacaoOrdemTransporte(String value) {
		this.value = value;
	}
	
	private SituacaoOrdemTransporte(String value, String codigo) {
		this.value = value;
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	

	@Override
	public String toString() {
		return value;
	}

	public static SituacaoOrdemTransporte getEntity(int code) {
		SituacaoOrdemTransporte result = SituacaoOrdemTransporte.DESCONHECIDA;
		for(SituacaoOrdemTransporte entity : values()) {
			if (entity.ordinal() == code) {
				result = entity; 
			}
		}
		return result;
	}

	public static boolean isEquals(SituacaoOrdemTransporte sit1, SituacaoOrdemTransporte sit2) {
		if (sit1 != null) {
			if (sit2 != null) {
				return sit1 == sit2;
			} else {
				return false;
			}
		} else {
			if (sit2 != null) {
				return false;
			} else {
				return true;
			}
		}
	}
}

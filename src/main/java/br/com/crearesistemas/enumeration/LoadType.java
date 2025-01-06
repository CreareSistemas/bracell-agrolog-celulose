package br.com.crearesistemas.enumeration;

public enum LoadType {	
	CARREGAMENTO("1"),
	DESCARREGAMENTO("2");
	
	String code;
	
	LoadType(String code){
		this.code = code;
	}

	public static boolean isCarregamento(String loadType) {
		Boolean result = false;
		LoadType[] values = values();
		
		if (CARREGAMENTO.code.contains(loadType)) {
			result = true;
		}
		return result;
	}
}

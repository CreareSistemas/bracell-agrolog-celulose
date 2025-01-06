package br.com.crearesistemas.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EstagioOperacional {
	DESCONHECIDO(0, null, null),
	
	FILA_MESA1(1, null, null),
	FILA_MESA2(2, null, null),
	FILA_MESA3(3, null, null),
	FILA_MESA4(4, null, null),
	FILA_QUADRAS(5, null, null),
	
	MESA_L1(10, 1000l,3),
	MESA_L2(11, 1001l,4),
	MESA_L3(12, 1002l,5),
	MESA_L4(13, 1008l,6),	
	
	
	QUADRA001(31, null, null),
	QUADRA002(32, null, null),
	QUADRA003(33, null, null),
	QUADRA004(34, null, null),
	QUADRA005(35, null, null),
	QUADRA006(36, null, null),
	QUADRA007(37, null, null),
	QUADRA008(38, null, null),
	QUADRA009(39, null, null),
	QUADRA010(40, null, null),
	QUADRA011(41, null, null),
	QUADRA012(42, null, null),
	QUADRA013(43, null, null),
	QUADRA014(44, null, null),
	QUADRA015(45, null, null),
	QUADRA016(46, null, null),
	QUADRA017(47, null, null),
	QUADRA018(48, null, null),
	QUADRA019(49, null, null),
	QUADRA020(50, null, null)
	;
	
	public Integer integrationCode;
	public Integer code;
	public Long localId;
	
	EstagioOperacional(Integer code, Long localId, Integer entidadeCode){
		this.code = code;
		this.localId = localId;
		this.integrationCode = entidadeCode;
	}

	public static EstagioOperacional getFilaLocalIdByIntegrationCode(Long integrationCode) {
		EstagioOperacional result = FILA_MESA1;
		for (EstagioOperacional estagio: values()) {
			if (estagio.isFila()) {
				if (integrationCode.longValue() == estagio.integrationCode.longValue()) {
					return estagio;
				}
			}
		}		
		return result;
	}
	
	public static EstagioOperacional getLocalIdByChipperCode(Long chipperCode) {
		EstagioOperacional result = MESA_L1;
		for (EstagioOperacional estagio: values()) {
			if (estagio.isMesa()) {
				if (chipperCode.longValue() == estagio.integrationCode.longValue()) {
					return estagio;
				}
			}
		}		
		return result;
	}
	
	public  Boolean isFila() {
		Boolean result = false;
		List<Integer> lista = new ArrayList<Integer>(
			    Arrays.asList(1,2,3,4,5));		
		for (Integer item : lista) {
			if (item == code) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public  Boolean isMesa() {
		Boolean result = false;
		List<Integer> lista = new ArrayList<Integer>(
			    Arrays.asList(10,11,12,13));
		
		for (Integer item : lista) {
			if (item == code) {
				result = true;
				break;
			}
		}
		return result;
	}
	


}

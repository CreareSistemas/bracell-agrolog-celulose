package br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto;

public class CicloRealizadoDto {

	private Long idProjeto;
	
	Integer totalCicloRealizadoSegundos = 0;
	Integer qtdCicloRealizado = 0;
	

	public void somaCicloRealizado(Integer cicloRealizado) {
		this.totalCicloRealizadoSegundos = this.totalCicloRealizadoSegundos + cicloRealizado;
		
	}

	public void somaCicloRealizadoQtd(int qtd) {
		qtdCicloRealizado = qtdCicloRealizado + qtd;		
	}

	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public int getMediaCicloRealizadoSegundos() {
		int result = 0; 
		if (qtdCicloRealizado > 0) {
			result = Math.round(totalCicloRealizadoSegundos / qtdCicloRealizado);
		}
		return result;
	}
	
}

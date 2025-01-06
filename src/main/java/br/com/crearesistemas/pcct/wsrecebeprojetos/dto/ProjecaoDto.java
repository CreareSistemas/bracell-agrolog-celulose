package br.com.crearesistemas.pcct.wsrecebeprojetos.dto;

import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.Acompanhamento;
import br.com.crearesistemas.util.NumberUtils;

public class ProjecaoDto {

	private Long projetoId;
	private Double somaVolumePrevisto = 0d;
	private Double somaProjecaoFinalVolume = 0d;
	private Float percentualAtendimento = 0f;

	
	
	
	public Long getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(Long projetoId) {
		this.projetoId = projetoId;
	}

	public Float getPercentualAtendimento() {
		return percentualAtendimento;
	}

	public void setPercentualAtendimento(Float percentualAtendimento) {
		this.percentualAtendimento = percentualAtendimento;
	}

	public Double getSomaVolumePrevisto() {
		return somaVolumePrevisto;
	}

	public void setSomaVolumePrevisto(Double somaVolumePrevisto) {
		this.somaVolumePrevisto = somaVolumePrevisto;
	}

	public Double getSomaProjecaoFinalVolume() {
		return somaProjecaoFinalVolume;
	}

	public void setSomaProjecaoFinalVolume(Double somaProjecaoFinalVolume) {
		this.somaProjecaoFinalVolume = somaProjecaoFinalVolume;
	}

	public void soma(Acompanhamento acompanhamento) {
		 
		somaProjecaoFinalVolume += NumberUtils.parseInt(acompanhamento.getVolumeRealizado());
		somaVolumePrevisto += NumberUtils.parseInt(acompanhamento.getVolumePrevisto());
		
		if (somaVolumePrevisto != 0) {
			percentualAtendimento =  (float) (Math.round((somaProjecaoFinalVolume / somaVolumePrevisto) * 10000.0)/100.0);	
		}
		  
	}
	
	
	
	
}

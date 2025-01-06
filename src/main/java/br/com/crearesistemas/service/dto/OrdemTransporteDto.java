package br.com.crearesistemas.service.dto;

import java.util.Date;

import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;

public class OrdemTransporteDto {

	Long otId;
	String otNumero;
	SituacaoOrdemTransporte otSituacao;
	Date otDataLiberacao;
	
	
	// interface 39 (recebimento)
	Date dataRecebimento;
	String idBalancaEntrada;
	
	// interface 35 (reprogramação)
	Date dataReprogramacao;
	Long pileCode;
	Long ChipperCode;
	
	// interface 40 (saida)
	Date dataFimViagem;
	String idBalancaSaida;
	public Long getOtId() {
		return otId;
	}
	public void setOtId(Long otId) {
		this.otId = otId;
	}
	public String getOtNumero() {
		return otNumero;
	}
	public void setOtNumero(String otNumero) {
		this.otNumero = otNumero;
	}
	public SituacaoOrdemTransporte getOtSituacao() {
		return otSituacao;
	}
	public void setOtSituacao(SituacaoOrdemTransporte otSituacao) {
		this.otSituacao = otSituacao;
	}
	public Date getOtDataLiberacao() {
		return otDataLiberacao;
	}
	public void setOtDataLiberacao(Date otDataLiberacao) {
		this.otDataLiberacao = otDataLiberacao;
	}
	public Date getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public String getIdBalancaEntrada() {
		return idBalancaEntrada;
	}
	public void setIdBalancaEntrada(String idBalancaEntrada) {
		this.idBalancaEntrada = idBalancaEntrada;
	}
	public Date getDataReprogramacao() {
		return dataReprogramacao;
	}
	public void setDataReprogramacao(Date dataReprogramacao) {
		this.dataReprogramacao = dataReprogramacao;
	}
	public Long getPileCode() {
		return pileCode;
	}
	public void setPileCode(Long pileCode) {
		this.pileCode = pileCode;
	}
	public Long getChipperCode() {
		return ChipperCode;
	}
	public void setChipperCode(Long chipperCode) {
		ChipperCode = chipperCode;
	}
	public Date getDataFimViagem() {
		return dataFimViagem;
	}
	public void setDataFimViagem(Date dataFimViagem) {
		this.dataFimViagem = dataFimViagem;
	}
	public String getIdBalancaSaida() {
		return idBalancaSaida;
	}
	public void setIdBalancaSaida(String idBalancaSaida) {
		this.idBalancaSaida = idBalancaSaida;
	}

	
	
}

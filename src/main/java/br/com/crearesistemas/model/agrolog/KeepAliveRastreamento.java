package br.com.crearesistemas.model.agrolog;

import java.util.Date;

public class KeepAliveRastreamento {
	
	private Integer numVersao;
	
	private Date data;
	
	private Date dataHistorico;
	
	private Float latitude;
	
	private Float longitude;
	
	private Integer  tipoRastreamento;
	
	private Long implemento_id;
	
	private Long dispositivo_id;
	
	private Integer ignicao;
	
	private Integer velocidade;
	
	private Integer rpm;
	
	private Long hodometro;
	
	private Long horimetro;
	
	private Double anguloDirecao;
	
	// proprio
	private Integer estadoOperacional0;
	
	// carregamento
	private Integer estadoOperacional1;

	// transporte
	private Integer estadoOperacional2;
	

	public Integer getNumVersao() {
		return numVersao;
	}

	public void setNumVersao(Integer numVersao) {
		this.numVersao = numVersao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Integer getTipoRastreamento() {
		return tipoRastreamento;
	}

	public void setTipoRastreamento(Integer tipoRastreamento) {
		this.tipoRastreamento = tipoRastreamento;
	}

	public Long getImplemento_id() {
		return implemento_id;
	}

	public void setImplemento_id(Long implemento_id) {
		this.implemento_id = implemento_id;
	}

	public Long getDispositivo_id() {
		return dispositivo_id;
	}

	public void setDispositivo_id(Long dispositivo_id) {
		this.dispositivo_id = dispositivo_id;
	}

	public Integer getIgnicao() {
		return ignicao;
	}

	public void setIgnicao(Integer ignicao) {
		this.ignicao = ignicao;
	}

	public Integer getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(Integer velocidade) {
		this.velocidade = velocidade;
	}

	public Integer getRpm() {
		return rpm;
	}

	public void setRpm(Integer rpm) {
		this.rpm = rpm;
	}

	public Long getHodometro() {
		return hodometro;
	}

	public void setHodometro(Long hodometro) {
		this.hodometro = hodometro;
	}

	public Long getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(Long horimetro) {
		this.horimetro = horimetro;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public Integer getEstadoOperacional0() {
		return estadoOperacional0;
	}

	public void setEstadoOperacional0(Integer estadoOperacional0) {
		this.estadoOperacional0 = estadoOperacional0;
	}

	public Integer getEstadoOperacional1() {
		return estadoOperacional1;
	}

	public void setEstadoOperacional1(Integer estadoOperacional1) {
		this.estadoOperacional1 = estadoOperacional1;
	}

	public Integer getEstadoOperacional2() {
		return estadoOperacional2;
	}

	public void setEstadoOperacional2(Integer estadoOperacional2) {
		this.estadoOperacional2 = estadoOperacional2;
	}

	public Double getAnguloDirecao() {
		return anguloDirecao;
	}

	public void setAnguloDirecao(Double anguloDirecao) {
		this.anguloDirecao = anguloDirecao;
	}

	@Override
	public String toString() {
		return "KeepAliveRastreamento [numVersao=" + numVersao + ", data=" + data + ", dataHistorico=" + dataHistorico
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", tipoRastreamento=" + tipoRastreamento
				+ ", implemento_id=" + implemento_id + ", dispositivo_id=" + dispositivo_id + ", ignicao=" + ignicao
				+ ", velocidade=" + velocidade + ", rpm=" + rpm + ", hodometro=" + hodometro + ", horimetro="
				+ horimetro + ", anguloDirecao=" + anguloDirecao + ", estadoOperacional0=" + estadoOperacional0
				+ ", estadoOperacional1=" + estadoOperacional1 + ", estadoOperacional2=" + estadoOperacional2 + "]";
	}


	
}

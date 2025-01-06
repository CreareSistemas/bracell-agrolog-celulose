package br.com.crearesistemas.service.dto;

import java.util.Date;

import br.com.crearesistemas.model.CaminhaoEntity;

public class RastreamentoDto {
	Float latitude;
	Float longitude;
	private Date datahora;
	
	public RastreamentoDto(CaminhaoEntity caminhao) {
		this(caminhao.getLatitude(), caminhao.getLongitude());
		this.datahora = caminhao.getAvldateTime();		
	}
	
	public RastreamentoDto(Float latitude, Float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}
	
	

}

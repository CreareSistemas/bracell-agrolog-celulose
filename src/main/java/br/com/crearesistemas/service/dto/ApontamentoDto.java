package br.com.crearesistemas.service.dto;

import java.util.Date;

import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;

public class ApontamentoDto {

	private Long eventCode;
	private Date eventDate;
	private Long eventLocalId;

	public ApontamentoDto(CaminhaoEntity caminhao) {
		eventCode = caminhao.getEventCode();
		eventDate = caminhao.getEventDateTime();
	}

	public ApontamentoDto(GruaEntity grua) {
		eventLocalId = grua.getIdLocal();
		eventDate = grua.getEventDateTime();
		eventCode = grua.getEventCode();
	}

	public Long getEventCode() {
		return eventCode;
	}

	public void setEventCode(Long eventCode) {
		this.eventCode = eventCode;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Long getEventLocalId() {
		return eventLocalId;
	}

	public void setEventLocalId(Long eventLocalId) {
		this.eventLocalId = eventLocalId;
	}

	
}

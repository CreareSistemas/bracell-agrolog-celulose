package br.com.crearesistemas.dao.dto;

import java.util.Date;

public class WorkflowEvent {

	Long eventId;
	Long eventTreatment;
	Long integration;
	Date created;
	Date updated;
	String message;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getEventTreatment() {
		return eventTreatment;
	}
	public void setEventTreatment(Long eventTreatment) {
		this.eventTreatment = eventTreatment;
	}
	public Long getIntegration() {
		return integration;
	}
	public void setIntegration(Long integration) {
		this.integration = integration;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	


	
}

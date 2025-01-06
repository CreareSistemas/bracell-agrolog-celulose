package br.com.crearesistemas.model;

import java.util.Date;

public class DowntimeDto {
	
	Long eventId;					// id do evento
	Integer downtimeCode;           // cod parada (sgf)
	Date startDowntime;
	Date endDowntime;
	Boolean sgfDowntime = false;    // foi enviado para o SGF?
	Integer retries = 0;			// quantidade de tentativas envio.
	Float startHourmeter;
	Float endHourmeter;
	String operatorName;
	
	
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getDowntimeCode() {
		return downtimeCode;
	}
	public void setDowntimeCode(Integer downtimeCode) {
		this.downtimeCode = downtimeCode;
	}
	public Date getStartDowntime() {
		return startDowntime;
	}
	public void setStartDowntime(Date startDowntime) {
		this.startDowntime = startDowntime;
	}
	public Date getEndDowntime() {
		return endDowntime;
	}
	public void setEndDowntime(Date endDowntime) {
		this.endDowntime = endDowntime;
	}
	public Boolean getSgfDowntime() {
		return sgfDowntime;
	}
	public void setSgfDowntime(Boolean sgfDowntime) {
		this.sgfDowntime = sgfDowntime;
	}
	public Integer getRetries() {
		return retries;
	}
	public void setRetries(Integer retries) {
		this.retries = retries;
	}
	public Float getStartHourmeter() {
		return startHourmeter;
	}
	public void setStartHourmeter(Float startHourmeter) {
		this.startHourmeter = startHourmeter;
	}
	public Float getEndHourmeter() {
		return endHourmeter;
	}
	public void setEndHourmeter(Float endHourmeter) {
		this.endHourmeter = endHourmeter;
	}
	
	
	
}

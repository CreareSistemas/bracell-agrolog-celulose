package br.com.crearesistemas.sgf.wsgetdowntimes;

public class GetDownTimes {
	Long downtimeCode;
	String description;
	String abbreviation;
	String status;
	
	public Long getDowntimeCode() {
		return downtimeCode;
	}
	public void setDowntimeCode(Long downtimeCode) {
		this.downtimeCode = downtimeCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "GetDownTimesResponse [downtimeCode=" + downtimeCode 
				+ ", description=" + description 
				+ ", abbreviation=" + abbreviation 
				+ ", status=" + status + "]";
	}
	
	
}

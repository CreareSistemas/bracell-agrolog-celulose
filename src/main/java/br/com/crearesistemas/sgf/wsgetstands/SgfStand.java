package br.com.crearesistemas.sgf.wsgetstands;

public class SgfStand {
	String standId;
    Long projectCode;
    Integer density;
    String densityClass;
    Integer timeAfterCutting;
    String status;
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	public Long getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(Long projectCode) {
		this.projectCode = projectCode;
	}
	public Integer getDensity() {
		return density;
	}
	public void setDensity(Integer density) {
		this.density = density;
	}
	public String getDensityClass() {
		return densityClass;
	}
	public void setDensityClass(String densityClass) {
		this.densityClass = densityClass;
	}
	public Integer getTimeAfterCutting() {
		return timeAfterCutting;
	}
	public void setTimeAfterCutting(Integer timeAfterCutting) {
		this.timeAfterCutting = timeAfterCutting;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}

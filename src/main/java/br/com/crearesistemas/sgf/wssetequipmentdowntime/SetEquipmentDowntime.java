package br.com.crearesistemas.sgf.wssetequipmentdowntime;

public class SetEquipmentDowntime   {
	String tipReport;
	/*
	 	E - parada de caminhão
	 	P - Grua de patio
	 	C - Grua de campo
	 */
	String equipmentPlate;
	Integer downtimeCode;	
	String startDownTime;
	String endDownTime;
	
	// opcionais
	String operatorName;
	
	
	/*
	 Código do local onde foi realizada a parada. 
	 Considerada apenas para as gruas de pátio. Caso não informado irá considerar o local da Fábrica.
	 */
	Long localeCode;
	Double startingHorimeterNumber;
	Double endingHorimeterNumber;
	
	
	public String getTipReport() {
		return tipReport;
	}
	public void setTipReport(String tipReport) {
		this.tipReport = tipReport;
	}
	public String getEquipmentPlate() {
		return equipmentPlate;
	}
	public void setEquipmentPlate(String equipmentPlate) {
		this.equipmentPlate = equipmentPlate;
	}
	
	public Integer getDowntimeCode() {
		return downtimeCode;
	}
	public void setDowntimeCode(Integer downtimeCode) {
		this.downtimeCode = downtimeCode;
	}
	public String getStartDownTime() {
		return startDownTime;
	}
	public void setStartDownTime(String startDownTime) {
		this.startDownTime = startDownTime;
	}
	public String getEndDownTime() {
		return endDownTime;
	}
	public void setEndDownTime(String endDownTime) {
		this.endDownTime = endDownTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Long getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(Long localeCode) {
		this.localeCode = localeCode;
	}
	public Double getStartingHorimeterNumber() {
		return startingHorimeterNumber;
	}
	public void setStartingHorimeterNumber(Double startingHorimeterNumber) {
		this.startingHorimeterNumber = startingHorimeterNumber;
	}
	public Double getEndingHorimeterNumber() {
		return endingHorimeterNumber;
	}
	public void setEndingHorimeterNumber(Double endingHorimeterNumber) {
		this.endingHorimeterNumber = endingHorimeterNumber;
	}
	@Override
	public String toString() {
		return "SetEquipmentDowntime [tipReport=" + tipReport + ", equipmentPlate=" + equipmentPlate + ", downtimeCode="
				+ downtimeCode + ", startDownTime=" + startDownTime + ", endDownTime=" + endDownTime + ", operatorName="
				+ operatorName + ", localeCode=" + localeCode + ", startingHorimeterNumber=" + startingHorimeterNumber
				+ ", endingHorimeterNumber=" + endingHorimeterNumber + "]";
	}

	
}

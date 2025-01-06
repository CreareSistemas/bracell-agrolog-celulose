package br.com.crearesistemas.sgf;

import java.util.Date;

public class SgfEventsLoading {

	String inputData;
	String reportID;
	String truckPlate; 				// obrigatorio
	Long projectCode;
	String standId;
	Long localeCode = 0l;
	Long pileCode = 0l;	
	Integer productCode; 			// obrigatorio
	Long craneCode; 				// obrigatorio
	String craneOperatorName;
	String startingHorimeterNumber = "0"; // obrigatorio
	String endingHorimeterNumber = "10"; 	// obrigatorio
	Integer scaleCraneWeight = 0;
	Integer loadType;
	
	Long chipperCode;
	
	Date startDate;
	Date endDate;
	Long vehicleId;
	Long eventId;
	
	
	public String getReportID() {
		return reportID;
	}
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	public String getTruckPlate() {
		return truckPlate;
	}
	public void setTruckPlate(String truckPlate) {
		this.truckPlate = truckPlate;
	}
	public Long getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(Long projectCode) {
		this.projectCode = projectCode;
	}
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	public Long getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(Long localeCode) {
		this.localeCode = localeCode;
	}
	public Long getPileCode() {
		return pileCode;
	}
	public void setPileCode(Long pileCode) {
		this.pileCode = pileCode;
	}
	public Integer getProductCode() {
		return productCode;
	}
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}
	public Long getCraneCode() {
		return craneCode;
	}
	public void setCraneCode(Long craneCode) {
		this.craneCode = craneCode;
	}
	public String getCraneOperatorName() {
		return craneOperatorName;
	}
	public void setCraneOperatorName(String craneOperatorName) {
		this.craneOperatorName = craneOperatorName;
	}
	public String getStartingHorimeterNumber() {
		return startingHorimeterNumber;
	}
	public void setStartingHorimeterNumber(String startingHorimeterNumber) {
		this.startingHorimeterNumber = startingHorimeterNumber;
	}
	public String getEndingHorimeterNumber() {
		return endingHorimeterNumber;
	}
	public void setEndingHorimeterNumber(String endingHorimeterNumber) {
		this.endingHorimeterNumber = endingHorimeterNumber;
	}
	public Integer getScaleCraneWeight() {
		return scaleCraneWeight;
	}
	public void setScaleCraneWeight(Integer scaleCraneWeight) {
		this.scaleCraneWeight = scaleCraneWeight;
	}
	public Long getChipperCode() {
		return chipperCode;
	}
	public void setChipperCode(Long chipperCode) {
		this.chipperCode = chipperCode;
	}
	public Integer getLoadType() {
		return loadType;
	}
	public void setLoadType(Integer loadType) {
		this.loadType = loadType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public String getInputData() {
		return inputData;
	}
	public void setInputData(String inputData) {
		this.inputData = inputData;
	}
	@Override
	public String toString() {
		return "SgfEventsLoading [inputData = " + inputData + ", reportID=" + reportID + ", truckPlate=" + truckPlate + ", projectCode=" + projectCode
				+ ", standId=" + standId + ", localeCode=" + localeCode + ", pileCode=" + pileCode + ", productCode="
				+ productCode + ", craneCode=" + craneCode + ", craneOperatorName=" + craneOperatorName
				+ ", startingHorimeterNumber=" + startingHorimeterNumber + ", endingHorimeterNumber="
				+ endingHorimeterNumber + ", scaleCraneWeight=" + scaleCraneWeight + ", loadType=" + loadType
				+ ", chipperCode=" + chipperCode + ", startDate=" + startDate + ", endDate=" + endDate + ", vehicleId="
				+ vehicleId + ", eventId=" + eventId + "]";
	}

	
	
	
	

}

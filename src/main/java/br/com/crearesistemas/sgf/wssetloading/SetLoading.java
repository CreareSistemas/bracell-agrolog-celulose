package br.com.crearesistemas.sgf.wssetloading;

public class SetLoading   {
	String reportID;
	String truckPlate; 				// obrigatorio
	Long projectCode;
	String standId;
	Long localeCode = 0l;
	Long pileCode = 0l;	
	Integer productCode; 			// obrigatorio
	Long craneCode; 				// obrigatorio
	String craneOperatorName;
	String startingHorimeterNumber; // obrigatorio
	String endingHorimeterNumber; 	// obrigatorio
	Integer scaleCraneWeight = 0;
	
	String startingDate; // "2021-07-21T14:10:00"
	String endingDate;
	
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
	public String getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}
	public String getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
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
	@Override
	public String toString() {
		return "SetLoading [reportID=" + reportID + ", truckPlate=" + truckPlate + ", projectCode=" + projectCode
				+ ", standId=" + standId + ", localeCode=" + localeCode + ", pileCode=" + pileCode + ", productCode="
				+ productCode + ", craneCode=" + craneCode + ", craneOperatorName=" + craneOperatorName
				+ ", startingHorimeterNumber=" + startingHorimeterNumber + ", endingHorimeterNumber="
				+ endingHorimeterNumber + ", scaleCraneWeight=" + scaleCraneWeight + ", startingDate=" + startingDate
				+ ", endingDate=" + endingDate + "]";
	}

	
	
	
}

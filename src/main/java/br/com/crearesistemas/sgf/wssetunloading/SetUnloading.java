package br.com.crearesistemas.sgf.wssetunloading;

public class SetUnloading   {
	String reportID;
	String truckPlate; 				// obrigatorio	
	Long craneCode;  				// obrigatorio
	Long pileCode=0l;
	Long chipperCode;
	String craneOperatorName;
	String startingHorimeterNumber;
	String endingHorimeterNumber;
	String startingDate;   			// obrigatorio "2021-07-21T14:37:00",
	String endingDate;    			// obrigatorio
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
	
	public Long getCraneCode() {
		return craneCode;
	}
	public void setCraneCode(Long craneCode) {
		this.craneCode = craneCode;
	}
	
	public Long getPileCode() {
		return pileCode;
	}
	public void setPileCode(Long pileCode) {
		this.pileCode = pileCode;
	}
	public Long getChipperCode() {
		return chipperCode;
	}
	public void setChipperCode(Long chipperCode) {
		this.chipperCode = chipperCode;
	}
	@Override
	public String toString() {
		return "SetUnloading [reportID=" + reportID + ", truckPlate=" + truckPlate + ", craneCode=" + craneCode
				+ ", pileCode=" + pileCode + ", chipperCode=" + chipperCode + ", craneOperatorName=" + craneOperatorName
				+ ", startingHorimeterNumber=" + startingHorimeterNumber + ", endingHorimeterNumber="
				+ endingHorimeterNumber + ", startingDate=" + startingDate + ", endingDate=" + endingDate + "]";
	}
	
	
	
	
}

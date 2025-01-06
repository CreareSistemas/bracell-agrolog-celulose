package br.com.crearesistemas.sgf.wssetinternalmovement;

public class SetInternalMovement   {
	String tipReport;
	Long reportId;
	Long localeCode;
	String truckPlate; 				// obrigatorio
	Long pileCode;	
	String craneId; 				// obrigatorio
	String craneOperatorName;
	Long chipperCode;
	//String startingHorimeterNumber; // obrigatorio
	//String endingHorimeterNumber; 	// obrigatorio
	String startOperation; // "2021-07-21T14:10:00"
	String endOperation;
	
	
	
	/*
	 * public Long getCraneCode() { return craneCode; } public void
	 * setCraneCode(Long craneCode) { this.craneCode = craneCode; }
	 */
	
	
	public String getTipReport() {
		return tipReport;
	}
	public Long getChipperCode() {
		return chipperCode;
	}
	public void setChipperCode(Long chipperCode) {
		this.chipperCode = chipperCode;
	}
	public String getCraneId() {
		return craneId;
	}
	public void setCraneId(String craneId) {
		this.craneId = craneId;
	}
	public void setTipReport(String tipReport) {
		this.tipReport = tipReport;
	}
	
	
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(Long localeCode) {
		this.localeCode = localeCode;
	}
	public String getTruckPlate() {
		return truckPlate;
	}
	public void setTruckPlate(String truckPlate) {
		this.truckPlate = truckPlate;
	}
	public Long getPileCode() {
		return pileCode;
	}
	public void setPileCode(Long pileCode) {
		this.pileCode = pileCode;
	}

	/*
	 * public Long getCraneId() { return craneId; } public void setCraneId(Long
	 * craneId) { this.craneId = craneId; }
	 */
	public String getCraneOperatorName() {
		return craneOperatorName;
	}
	public void setCraneOperatorName(String craneOperatorName) {
		this.craneOperatorName = craneOperatorName;
	}

	/*
	 * public String getStartingHorimeterNumber() { return startingHorimeterNumber;
	 * } public void setStartingHorimeterNumber(String startingHorimeterNumber) {
	 * this.startingHorimeterNumber = startingHorimeterNumber; } public String
	 * getEndingHorimeterNumber() { return endingHorimeterNumber; } public void
	 * setEndingHorimeterNumber(String endingHorimeterNumber) {
	 * this.endingHorimeterNumber = endingHorimeterNumber; }
	 */
	public String getStartOperation() {
		return startOperation;
	}
	public void setStartOperation(String startOperation) {
		this.startOperation = startOperation;
	}
	public String getEndOperation() {
		return endOperation;
	}
	public void setEndOperation(String endOperation) {
		this.endOperation = endOperation;
	}
	
	
}

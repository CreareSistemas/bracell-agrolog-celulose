package br.com.crearesistemas.sgf.wsgettrucks;

public class SgfTruck {

	
	Long truckCode;
    String truckPlate;
    String abbreviation;
    String serialNumber;
    String tagRFID;
    String status;
	public Long getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(Long truckCode) {
		this.truckCode = truckCode;
	}
	public String getTruckPlate() {
		return truckPlate;
	}
	public void setTruckPlate(String truckPlate) {
		this.truckPlate = truckPlate;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTagRFID() {
		return tagRFID;
	}
	public void setTagRFID(String tagRFID) {
		this.tagRFID = tagRFID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
	
}

package br.com.crearesistemas.sgf.wssetunloading;

public class SetUnloadingResponse {

	String returnId;
	String returnMessage;
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	@Override
	public String toString() {
		return "SetUnloadingResponse [returnId=" + returnId + ", returnMessage=" + returnMessage + "]";
	}
	
	
	
}

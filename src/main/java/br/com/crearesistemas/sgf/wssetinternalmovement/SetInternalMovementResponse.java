package br.com.crearesistemas.sgf.wssetinternalmovement;

public class SetInternalMovementResponse {

	//{"returnId":"-1","returnMessage":["IdViagemInterna já informado para uma Carga"]}
	String returnId;
	String[] returnMessage;
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	
	public String[] getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String[] returnMessage) {
		this.returnMessage = returnMessage;
	}
	@Override
	public String toString() {
		return "SetLoadingResponse [returnId=" + returnId + ", returnMessage=" + returnMessage + "]";
	}
	
	
	
	
}

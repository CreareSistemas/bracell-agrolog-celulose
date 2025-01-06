package br.com.crearesistemas.sgf.soap.wsgetparadas;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.ws.BindingType;

@WebService(name = "WS_InflorHubSoap", targetNamespace="http://tempuri.org/")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING) 
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public interface GetParadasInterface {

	/*
	 * @WebMethod(operationName = "getParadas")
	 * 
	 * @WebResult(name = "getParadasResponse") public GetParadasResponse
	 * getParadasInflor(
	 * 
	 * @WebParam GetParadas getParadas,
	 * 
	 * @WebParam(name = "Seguranca", header = true, mode = WebParam.Mode.IN,
	 * partName = "Seguranca") Seguranca seguranca );
	 */
    
}

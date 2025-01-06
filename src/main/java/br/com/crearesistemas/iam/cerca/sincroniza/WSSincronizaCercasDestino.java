package br.com.crearesistemas.iam.cerca.sincroniza;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author cneves, 14-Set-2015
 */
@WebService
public interface WSSincronizaCercasDestino {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseSincronizaCercasDestino sincronizaCercasDestino(@WebParam SincronizaCercasDestino request, @WebParam(header = true, name = "credencial") Credencial credencial);

}

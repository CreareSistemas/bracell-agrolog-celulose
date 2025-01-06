package br.com.crearesistemas.iam.apontamento.recebe;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author eninomia
 */
@WebService
public interface WSRecebeApontamento {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseRecebeApontamento recebeApontamento(@WebParam RecebeApontamento recebeApontamento, @WebParam(header = true, name = "credencial") Credencial credencial);

}

package br.com.crearesistemas.iam.infracao.recebe;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

@WebService
public interface WSRecebeInfracao {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseRecebeInfracao recebeInfracao(@WebParam RecebeInfracao recebeInfracao, @WebParam(header = true, name = "credencial") Credencial credencial);

}
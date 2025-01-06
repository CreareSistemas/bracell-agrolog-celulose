package br.com.crearesistemas.iam.rastreamento.envia;

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
public interface WSEnviaKeepAliveRastreamento {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseEnviaKeepAliveRastreamento enviaRastreamento(
			@WebParam EnviaKeepAliveRastreamento enviaRastreamento,
			@WebParam(header = true, name = "credencial") Credencial credencial);

}

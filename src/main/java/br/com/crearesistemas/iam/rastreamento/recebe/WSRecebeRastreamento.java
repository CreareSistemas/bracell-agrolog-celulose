package br.com.crearesistemas.iam.rastreamento.recebe;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author eninomia, cneves
 */
@WebService
public interface WSRecebeRastreamento {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseRecebeRastreamento recebeRastreamento(@WebParam RecebeRastreamento recebeRastreamento, @WebParam(header = true, name = "credencial") Credencial credencial);

}

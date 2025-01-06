package br.com.crearesistemas.iam.mensagem.recebe;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author cneves, 19-Jun-2015
 */
@WebService
public interface WSRecebeMensagem {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseRecebeMensagem recebeMensagem(@WebParam RecebeMensagem recebeMensagem, @WebParam(header = true, name = "credencial") Credencial credencial);

}

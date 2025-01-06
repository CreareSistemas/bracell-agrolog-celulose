package br.com.crearesistemas.iam.mensagem.confirma;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author cneves, 26-Jun-2015
 */
@WebService
public interface WSConfirmaRecebimentoMensagem {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseConfirmaRecebimentoMensagem confirmaRecebimentoMensagem(@WebParam ConfirmaRecebimentoMensagem recebeMensagem, @WebParam(header = true, name = "credencial") Credencial credencial);

}

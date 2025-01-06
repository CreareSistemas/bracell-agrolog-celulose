package br.com.crearesistemas.pcct.wsrecebeprevisoes;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebeprevisoes.dto.RecebePrevisoes;
import br.com.crearesistemas.pcct.wsrecebeprevisoes.dto.RecebePrevisoesResponse;


@WebService(name = "PCCT/WSRecebePrevisoes", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebePrevisoes 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebePrevisoesResponse recebePrevisoes (
		@WebParam
		RecebePrevisoes recebePrevisoes,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

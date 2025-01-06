package br.com.crearesistemas.pcsi.wsrecebeindisponiveis;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcsi.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto.RecebeImplementosIndisponiveis;
import br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto.RecebeImplementosIndisponiveisResponse;


@WebService(name = "PCSI/WSRecebeImplementosIndisponiveis", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeImplementosIndisponiveis 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeImplementosIndisponiveisResponse recebeImplementosIndisponiveis(
		@WebParam
		RecebeImplementosIndisponiveis recebeImplementosIndisponiveis,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}
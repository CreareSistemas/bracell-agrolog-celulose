package br.com.crearesistemas.pcct.wsrecebechegadas;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebechegadas.dto.RecebeChegadas;
import br.com.crearesistemas.pcct.wsrecebechegadas.dto.RecebeChegadasResponse;


@WebService(name = "PCCT/WSRecebeChegadas",
	targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeChegadas 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeChegadasResponse recebeChegadas (
		@WebParam
		RecebeChegadas recebeChegadas,
		
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

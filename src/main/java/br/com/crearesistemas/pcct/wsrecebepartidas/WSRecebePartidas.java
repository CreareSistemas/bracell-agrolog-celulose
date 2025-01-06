package br.com.crearesistemas.pcct.wsrecebepartidas;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebepartidas.dto.RecebePartidas;
import br.com.crearesistemas.pcct.wsrecebepartidas.dto.RecebePartidasResponse;


@WebService(name = "PCCT/WSRecebePartidas",
	targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebePartidas 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebePartidasResponse recebePartidas (
		@WebParam
		RecebePartidas recebePartidas,
		
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

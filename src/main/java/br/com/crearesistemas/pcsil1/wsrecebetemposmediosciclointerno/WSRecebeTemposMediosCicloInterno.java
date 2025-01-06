package br.com.crearesistemas.pcsil1.wsrecebetemposmediosciclointerno;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcsi.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto.RecebeTemposMediosCicloInterno;
import br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto.RecebeTemposMediosCicloInternoResponse;


@WebService(name = "PCSIL1/WSRecebeTemposMediosCicloInterno", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeTemposMediosCicloInterno 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeTemposMediosCicloInternoResponse recebeTemposMediosCicloInterno(
		@WebParam
		RecebeTemposMediosCicloInterno recebeTemposMediosCicloInterno,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

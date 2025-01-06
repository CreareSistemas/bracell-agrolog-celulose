package br.com.crearesistemas.pcsil1.wsrecebegruassiteindustrial;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcsil1.credencial.Credencial;
import br.com.crearesistemas.pcsil1.wsrecebegruassiteindustrial.dto.RecebeGruasSiteIndustrial;
import br.com.crearesistemas.pcsil1.wsrecebegruassiteindustrial.dto.RecebeGruasSiteIndustrialResponse;


@WebService(name = "PCSIL1/WSRecebeGruasSiteIndustrial", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeGruasSiteIndustrial 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeGruasSiteIndustrialResponse recebeGruasSiteIndustrial(
		@WebParam
		RecebeGruasSiteIndustrial recebeGruasSiteIndustrial,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

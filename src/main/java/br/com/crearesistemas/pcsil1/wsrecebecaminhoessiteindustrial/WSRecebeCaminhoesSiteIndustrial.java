package br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcsil1.credencial.Credencial;
import br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial.dto.RecebeCaminhoesSiteIndustrial;
import br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial.dto.RecebeCaminhoesSiteIndustrialResponse;


@WebService(name = "PCSIL1/WSRecebeCaminhoesSiteIndustrial", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeCaminhoesSiteIndustrial 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeCaminhoesSiteIndustrialResponse recebeCaminhoesSiteIndustrial(
		@WebParam
		RecebeCaminhoesSiteIndustrial recebeCaminhoesSiteIndustrial,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

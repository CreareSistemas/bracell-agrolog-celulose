package br.com.crearesistemas.pcct.wsrecebeprojetos;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;


@WebService(name = "PCCT/WSRecebeProjetos", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeProjetos {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeProjetosResponse recebeProjetos(
		@WebParam
		RecebeProjetos recebeProjetos,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
	
}






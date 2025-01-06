package br.com.crearesistemas.pcct.wsrecebecaminhoestransporte;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.RecebeCaminhoesTransporte;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.RecebeCaminhoesTransporteResponse;



@WebService(name = "PCCT/WSRecebeCaminhoesTransporte", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeCaminhoesTransporte {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeCaminhoesTransporteResponse recebeCaminhoesTransporte(
		@WebParam
		RecebeCaminhoesTransporte recebeCaminhoesTransporte,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

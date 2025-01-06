package br.com.crearesistemas.iam.rotograma.embarca;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author cneves, 21-Set-2015
 */
@WebService
public interface WSEmbarcaRotograma {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseEmbarcaRotograma embarcaRotograma(@WebParam EmbarcaRotograma embarcaRotograma, @WebParam(header = true, name = "credencial") Credencial credencial);

}

package br.com.crearesistemas.iam.rotograma.cadastra;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

/**
 * @author cneves, 13-Jul-2015
 */
@WebService
public interface WSCadastraRotograma {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseCadastraRotograma cadastraRotograma(@WebParam CadastraRotograma cadastraRotograma, @WebParam(header = true, name = "credencial") Credencial credencial);

}

package br.com.crearesistemas.iam.equipamento.recebe;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.model.credencial.Credencial;

@WebService
public interface WSRecebeEquipamento {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public ResponseRecebeEquipamento recebeEquipamento(@WebParam RecebeEquipamento recebeEquipamento, @WebParam(header = true, name = "credencial") Credencial credencial);

}
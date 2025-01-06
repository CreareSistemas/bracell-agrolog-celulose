package br.com.crearesistemas.pcct.wsrecebegruasflorestais;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.RecebeGruasFlorestais;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.RecebeGruasFlorestaisResponse;

@WebService(name = "PCCT/WSRecebeGruasFlorestais", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeGruasFlorestais {

	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeGruasFlorestaisResponse recebeGruasFlorestais(
		@WebParam
		RecebeGruasFlorestais recebeGruasFlorestais,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

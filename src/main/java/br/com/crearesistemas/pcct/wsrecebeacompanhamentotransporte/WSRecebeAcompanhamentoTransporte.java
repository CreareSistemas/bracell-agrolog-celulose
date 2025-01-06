package br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.RecebeAcompanhamentoTransporte;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.RecebeAcompanhamentoTransporteResponse;


@WebService(name = "PCCT/WSRecebeAcompanhamentoTransporte", targetNamespace = "http://crearesistemas.com.br/")
public interface WSRecebeAcompanhamentoTransporte 
{
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod
	@WebResult
	public RecebeAcompanhamentoTransporteResponse recebeAcompanhamentoTransporte (
		@WebParam
		RecebeAcompanhamentoTransporte recebeAcompanhamentoTransporte,
		@WebParam(header = true, name = "credencial")
		Credencial credencial
	);
}

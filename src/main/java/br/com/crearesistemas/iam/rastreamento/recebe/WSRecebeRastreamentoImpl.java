package br.com.crearesistemas.iam.rastreamento.recebe;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Rastreamento;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.RastreamentoService;

/**
 * @author cneves, 26-Jun-2015
 */
public class WSRecebeRastreamentoImpl extends WSBase implements WSRecebeRastreamento {

	private static final Logger logger = Logger.getLogger(WSRecebeRastreamentoImpl.class);

	@Resource
	private RastreamentoService service;

	
	public ResponseRecebeRastreamento recebeRastreamento(RecebeRastreamento entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de rastreamentos recebido: " + entrada + ".");
		}
		ResponseRecebeRastreamento saida = new ResponseRecebeRastreamento();
		List<Rastreamento> rastreamentos = buscarRastreamentos(entrada.getmId(), entrada.getCliente());
		saida.setRastreamento(rastreamentos);
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		{
			logger.info("Total de rastreamentos a serem enviados: " + rastreamentos.size() + ".\n");
		}
		return saida;
	}

	private List<Rastreamento> buscarRastreamentos(long mId, List<Long> clientes) {
		return service.buscarRastreamentos(mId, clientes);
	}

}

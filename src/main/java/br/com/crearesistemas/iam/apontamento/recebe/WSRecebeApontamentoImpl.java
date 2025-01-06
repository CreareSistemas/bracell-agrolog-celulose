package br.com.crearesistemas.iam.apontamento.recebe;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Apontamento;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.ApontamentoService;

/**
 * @author eninomia
 */
public class WSRecebeApontamentoImpl extends WSBase implements WSRecebeApontamento {

	private static final Logger logger = Logger.getLogger(WSRecebeApontamentoImpl.class);

	@Resource
	private ApontamentoService service;

	
	public ResponseRecebeApontamento recebeApontamento(RecebeApontamento entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de apontamentos recebido: " + entrada.toString() + ".");
		}
		ResponseRecebeApontamento saida = new ResponseRecebeApontamento();
		List<Apontamento> apontamentos = buscarApontamentos(entrada.getmId(), entrada.getIdentificadores());
		saida.setApontamentos(apontamentos);
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		{
			logger.info("Total de apontamentos a serem enviados: " + apontamentos.size() + ".\n");
		}
		return saida;
	}

	private List<Apontamento> buscarApontamentos(long mId, List<String> identificadores) {
		return service.buscarApontamentos(mId, identificadores);
	}

}

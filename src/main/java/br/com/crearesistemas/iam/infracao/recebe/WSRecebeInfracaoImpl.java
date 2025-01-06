package br.com.crearesistemas.iam.infracao.recebe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.Infracao;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.InfracaoService;

public class WSRecebeInfracaoImpl extends WSBase implements WSRecebeInfracao {

	private static final Logger logger = Logger.getLogger(WSRecebeInfracaoImpl.class);

	@Resource
	private InfracaoService infracaoService;

	@Override
	public ResponseRecebeInfracao recebeInfracao(RecebeInfracao request, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de envio de infra\u00E7\u00F5es recebido: " + request + ".");
		}
		ResponseRecebeInfracao response = new ResponseRecebeInfracao();
		response.setInfracoes(infracoes(request));
		response.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		{
			logger.info("Total de infra\u00E7\u00F5s a serem enviadas: " + response.getInfracoes().size() + ".\n");
		}
		return response;
	}

	private List<Infracao> infracoes(RecebeInfracao request) {
		List<Infracao> infracoes = new ArrayList<Infracao>();
		infracoes.addAll(infracaoService.buscarInfracoesVelocidadeInicio(request.getmId(), request.getIdadeEmHoras(), request.getQuantidadeLinhas(), request.getCliente()));
		infracoes.addAll(infracaoService.buscarInfracoesVelocidadeFim(request.getmId(), request.getIdadeEmHoras(), request.getQuantidadeLinhas(), request.getCliente()));
		return infracoes;
	}

}

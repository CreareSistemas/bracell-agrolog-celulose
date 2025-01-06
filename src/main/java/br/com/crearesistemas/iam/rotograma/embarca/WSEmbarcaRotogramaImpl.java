package br.com.crearesistemas.iam.rotograma.embarca;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.CercaService;

/**
 * @author cneves, 21-Set-2015
 */
public class WSEmbarcaRotogramaImpl extends WSBase implements WSEmbarcaRotograma {

	private static final Logger logger = Logger.getLogger(WSEmbarcaRotogramaImpl.class);

	@Resource
	private CercaService cercaService;

	public ResponseEmbarcaRotograma embarcaRotograma(EmbarcaRotograma entrada, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info("Pedido de embarque de rotograma recebido: " + entrada + ".");
		}
		ResponseEmbarcaRotograma saida = new ResponseEmbarcaRotograma();
		//
		List<RotogramaRequest> rotogramas = entrada.getRotograma();
		embarcaCercas(rotogramas);
		//
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		saida.setMensagem(WSBase.MSG_ROTOGRAMA_EMBARCADO);
		return saida;
	}

	private void embarcaCercas(List<RotogramaRequest> rotogramas) {
		for (RotogramaRequest rotograma : rotogramas) {
			embarcaCerca(rotograma);
		}
	}

	private void embarcaCerca(RotogramaRequest rotograma) {
		cercaService.embarcaCerca(rotograma.getNumOt(), rotograma.getIdProjeto(), rotograma.getCdRota(), rotograma.getPlaca(), rotograma.getIdDispositivo(), rotograma.getDataLiberacao());
	}

}

package br.com.crearesistemas.iam.cerca.sincroniza;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.credencial.Credencial;
import br.com.crearesistemas.service.CercaService;

/**
 * @author cneves, 13-Jul-2015
 */
public class WSSincronizaCercasDestinoImpl extends WSBase implements WSSincronizaCercasDestino {

	private static final Logger logger = Logger.getLogger(WSSincronizaCercasDestinoImpl.class);

	@Resource
	private CercaService service;

	@Override
	public ResponseSincronizaCercasDestino sincronizaCercasDestino(SincronizaCercasDestino request, Credencial credencial) {
		credencial.autenticacao();
		{
			logger.info(request);
		}
		ResponseSincronizaCercasDestino saida = new ResponseSincronizaCercasDestino();
		service.salvar(request.getCerca(), request.getCliente());
		saida.setErro(WSBase.STATUS_NENHUM_ERRO_ENCONTRADO);
		saida.setMensagem("");
		return saida;
	}
}

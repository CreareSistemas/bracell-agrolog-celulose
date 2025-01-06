package br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.EntidadeProprietaria;
import br.com.crearesistemas.pcsi.credencial.Credencial;
import br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto.RecebeTemposMediosCicloInterno;
import br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto.RecebeTemposMediosCicloInternoResponse;
import br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto.TempoMedioCicloInterno;
import br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto.TempoMedioCicloInterno.TipoCalculo;
import br.com.crearesistemas.service.CaminhoesService;

@WebService(serviceName = "PCSI/WSRecebeTemposMediosCicloInterno", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeTemposMediosCicloInternoImpl extends WSBase 
	implements WSRecebeTemposMediosCicloInterno 
{

	private static final Logger logger = Logger
			.getLogger(WSRecebeTemposMediosCicloInternoImpl.class);

	@Autowired  private CaminhoesService caminhoesService;

	
	
	public RecebeTemposMediosCicloInternoResponse recebeTemposMediosCicloInterno(
			RecebeTemposMediosCicloInterno recebeTemposMediosCicloInterno,
			Credencial credencial
	) 
	{
		
		RecebeTemposMediosCicloInternoResponse response = 
				new RecebeTemposMediosCicloInternoResponse();
		
		credencial.autenticacao();
		
		try {
			
			Date de = recebeTemposMediosCicloInterno.getDataDe();
			Date ate = recebeTemposMediosCicloInterno.getDataAte();
			
			logger.info("dataIni: " + de.toLocaleString());
			logger.info("dataFim: " + ate.toLocaleString());
			
			List<Object[]> responseNamedQuery;		
			Map<Long, HashMap<TipoCalculo, Integer>> calcDestinoDictByEntity;
			
			responseNamedQuery	= caminhoesService.selecionaPorFluxoSiteIndustrial(de, ate);
			
			
			// --------------------------------------------------------------------------------------------
			Map<Long, HashMap<EstadoOperacional, Integer>> apontamentoDictByEntity = 
					new LinkedHashMap<Long, HashMap<EstadoOperacional, Integer>>();

			for (Object[] item : responseNamedQuery) 
			{
				final EstadoOperacional estado = (EstadoOperacional)item[0];
				Long idPrestador = (Long)item[1];
				final Integer duracao = (int)Math.round((Double)item[2]);
				
				EntidadeProprietaria entidade = config.buscarPrestadorPorId(idPrestador);
				
				Long clientId = entidade.getId();
				
				if (clientId < 1) {
					idPrestador = (long) 0;
				}
				
				if( ! apontamentoDictByEntity.containsKey(idPrestador) )
				{
					apontamentoDictByEntity.put(idPrestador, new HashMap<EstadoOperacional, Integer>() {{
						put(estado, duracao);
					}});
				}
				else
				{
					apontamentoDictByEntity.get(idPrestador).put(estado, duracao);
				}
			}
			
			for(Map.Entry<Long, HashMap<EstadoOperacional, Integer>> entry : 
				apontamentoDictByEntity.entrySet())
			{
				Long idPrestador = entry.getKey();
				EntidadeProprietaria entidade = config.buscarPrestadorPorId(idPrestador);
				
				TempoMedioCicloInterno _t = TempoMedioCicloInterno.convertFromMapEntry(entry, entidade);
				
				response.getListaDeTemposMediosCicloInterno().add(_t);
								
			}
		} catch (Exception e) 
		{
			response.setErro(STATUS_ERRO);
			response.setMensagem(e.getMessage());
			logger.error("Erro ", e);
		}
		
		return response;
	}


	

		
	
}

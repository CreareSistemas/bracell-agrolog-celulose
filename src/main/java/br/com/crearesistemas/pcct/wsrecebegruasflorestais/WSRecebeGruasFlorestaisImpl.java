package br.com.crearesistemas.pcct.wsrecebegruasflorestais;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.GruaFlorestal;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.RecebeGruasFlorestais;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.RecebeGruasFlorestaisResponse;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.service.PgGruasService;
import br.com.crearesistemas.service.PgProjetosService;
import br.com.crearesistemas.service.ServiceException;

@WebService(serviceName = "PCCT/WSRecebeGruasFlorestais", targetNamespace = "http://crearesistemas.com.br/")
public class WSRecebeGruasFlorestaisImpl extends WSBase implements WSRecebeGruasFlorestais {

	private static final Logger logger = Logger.getLogger(WSRecebeGruasFlorestaisImpl.class);
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	
	@Resource private GruasService service;
	
	@Resource private PgGruasService pgService;
	
	@Resource private PgProjetosService pgProjetoService;
	
	
	

	@Override
	public RecebeGruasFlorestaisResponse recebeGruasFlorestais(
			RecebeGruasFlorestais recebeGruasFlorestais, Credencial credencial) {
		RecebeGruasFlorestaisResponse response = new RecebeGruasFlorestaisResponse();
	
		HashMap<Long, String> hashProjetosGruasFlorestais = new HashMap<>();
		
		try {
			
			List<GruaEntity> gruas = service.selecionarGruasFlorestais(config.getCustomerId());
			for (GruaEntity grua : gruas) {
				GruaFlorestal gruaFlorestal = new GruaFlorestal();
				
				gruaFlorestal.setId(grua.getId());		
				gruaFlorestal.setIdentificacao(grua.getPrefix());
				gruaFlorestal.setEstadoOperacional(config.eventoEstadoMap(TipoImplemento.GRUA_FLORESTAL, grua.getEventCode()).ordinal());
				
				gruaFlorestal.setDataUltimoRastreamento(grua.getAvldateTime());
				gruaFlorestal.setDataUltimoApontamento(grua.getEventDateTime());
				if (grua.getProject() != null) {
					if (grua.getLoadDateTime() != null) {
						gruaFlorestal.setDataUltimoCarregamento(grua.getLoadDateTime());	
					} else {
						gruaFlorestal.setDataUltimoCarregamento(grua.getEventDateTime());
					}
				}
				gruaFlorestal.setProjetoUltimoCarregamento(grua.getProject());
				gruaFlorestal.setTalhaoUltimoCarregamento(grua.getField());
				gruaFlorestal.setPilhaUltimoCarregamento(grua.getPile());
				gruaFlorestal.setPlacaUltimoCarregamento(grua.getPlates());
				gruaFlorestal.setDesligada(false);
				gruaFlorestal.setTemApontamentoInvalido(false);
				gruaFlorestal.setIsInconsistente(false);
				gruaFlorestal.setLatitude(grua.getLatitude());
				gruaFlorestal.setLongitude(grua.getLongitue());
				gruaFlorestal.setTipoImplemento(2);
				
				
				
				ProjetoEntity pgProjetoProx = pgProjetoService.selecionarProjetoProximo(grua.getLatitude(), grua.getLongitue(), 500);
				if (pgProjetoProx != null) {
					gruaFlorestal.setIdProjeto(pgProjetoProx.getId());	
				}
				
				if (!hashProjetosGruasFlorestais.containsKey(gruaFlorestal.getIdProjeto())) {
					hashProjetosGruasFlorestais.put(gruaFlorestal.getIdProjeto(), gruaFlorestal.getIdentificacao());	
				}
				
				response.getListaDeGruasFlorestais().add(gruaFlorestal);	
			}
			
			config.setMapProjetosGruaFlorestal(hashProjetosGruasFlorestais);
			
			
		} catch (ServiceException e) {
			logger.error("Erro ", e);
		} catch (Exception e) {
			logger.error("Erro ", e);
		}

		return response;
	}


	
}

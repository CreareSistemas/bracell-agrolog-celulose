package br.com.crearesistemas.pcct.wsrecebeprojetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.iam.WSBase;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.model.ProjetoEntity;
import br.com.crearesistemas.pcct.credencial.Credencial;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.Acompanhamento;
import br.com.crearesistemas.pcct.wsrecebeprojetos.dto.ProjecaoDto;
import br.com.crearesistemas.pcct.wsrecebeprojetos.dto.Projeto;
import br.com.crearesistemas.service.GruasService;
import br.com.crearesistemas.service.PgProjetosService;
import br.com.crearesistemas.service.ProjetosService;
import br.com.crearesistemas.service.dto.SelecionarProjetosDto;

public class WSRecebeProjetosImpl extends WSBase implements WSRecebeProjetos {

	private static final Logger logger = Logger.getLogger(WSRecebeProjetosImpl.class);
	Config config = Config.getInstance();
	Boolean isProduction = config.isProduction();
	
	@Resource private ProjetosService projectService;
	
	@Resource private GruasService gruasService;
	
	@Resource private PgProjetosService pgProjectService;
	
	@Override
	public RecebeProjetosResponse recebeProjetos(RecebeProjetos recebeProjetos, Credencial credencial) {
		HashMap<Long, String> hashLocaisGruas = new HashMap<>();
		
		RecebeProjetosResponse response = new RecebeProjetosResponse();
		SelecionarProjetosDto selecionar = new SelecionarProjetosDto();
		selecionar.setCustomerId(config.getCustomerId());
		List<ProjetoEntity> projetos = new ArrayList<ProjetoEntity>();
		
		for (ProjetoEntity projetoEntity : projetos) {
			if (projetoHasAssets(projetoEntity)) {
				Projeto projeto = new Projeto();
		        projeto.setId(projetoEntity.getId());
		        projeto.setIdentificacao(projetoEntity.getName());
		        projeto.setPercentualAtendimento(Float.valueOf(0));
		        response.getListaDeProjetos().add(projeto);
		        hashLocaisGruas.put(projeto.getId(), projeto.getIdentificacao());	
			}
		}
		
		List<ProjetoEntity> pgProjetos = pgProjectService.selecionarProjetos(selecionar);
		for (ProjetoEntity projetoEntity : pgProjetos) {
			if (projetoHasAssets(projetoEntity)) {
				if (!hashLocaisGruas.containsKey(projetoEntity.getId())) {
					Projeto projeto = new Projeto();
			        projeto.setId(projetoEntity.getId());
			        projeto.setIdentificacao(projetoEntity.getName());
			        projeto.setPercentualAtendimento(Float.valueOf(0));
			        response.getListaDeProjetos().add(projeto);
			        hashLocaisGruas.put(projeto.getId(), projeto.getIdentificacao());
				}	
			}
		}
		
		
		List<GruaEntity> gruas = gruasService.selecionarGruasFlorestais(config.getCustomerId());
		if (gruas != null) {
			List<ProjetoEntity> pgProjetosProx = pgProjectService.selecionarProjetosProximos(gruas);	
			for (ProjetoEntity projetoEntity : pgProjetosProx) {
				
				if (projetoHasAssets(projetoEntity)) {
					if (!hashLocaisGruas.containsKey(projetoEntity.getId())) {
						Projeto projeto = new Projeto();
				        projeto.setId(projetoEntity.getId());
				        projeto.setIdentificacao(projetoEntity.getName());
				        projeto.setPercentualAtendimento(Float.valueOf(0));
				        response.getListaDeProjetos().add(projeto);	
				        hashLocaisGruas.put(projeto.getId(), projeto.getIdentificacao());
					}	
				}
			}	
		}
		
		setGrauAtendimento(response);
        
		config.setProjetos(hashLocaisGruas);
		
		return response;
	}

	private void setGrauAtendimento(RecebeProjetosResponse response) {
		ProjecaoDto projecao;
		HashMap<Long, ProjecaoDto> hashProjecao = new HashMap<>();
		
		List<Acompanhamento> acompanhamentos = config.getListAcompanhamentos();
		
		if (acompanhamentos != null) {
			 for (Acompanhamento acompanhamento: acompanhamentos) {				 
				if (hashProjecao.containsKey(acompanhamento.getIdProjeto())) {
					 projecao = hashProjecao.get(acompanhamento.getIdProjeto()); 
				 } else {
					 projecao = new ProjecaoDto();
					 hashProjecao.put(acompanhamento.getIdProjeto(), projecao);
				 }
				
				
				projecao.setProjetoId(acompanhamento.getIdProjeto());
				projecao.soma(acompanhamento);
			 }
		}
		
		
		if (response != null) {
			for (Projeto projeto : response.getListaDeProjetos()) {
				if (projeto.getId() != null && hashProjecao.containsKey(projeto.getId())) {
					ProjecaoDto _projecao = hashProjecao.get(projeto.getId());
					if (_projecao.getPercentualAtendimento() != null) {
						if (_projecao.getPercentualAtendimento() < 0) {
							projeto.setPercentualAtendimento(0f);
						} else {
							projeto.setPercentualAtendimento(_projecao.getPercentualAtendimento());	
						}
					} else {
						projeto.setPercentualAtendimento(null);
					}					
				}
			}
		}
		
		
	}

	private boolean projetoHasAssets(ProjetoEntity projetoEntity) {
		Boolean result = false;
		
		if (config.getMapProjetosCaminhaoTransp() == null || config.getMapProjetosGruaFlorestal() == null) {
			result = true;
		} else {
			if (!result && config.getMapProjetosCaminhaoTransp().containsKey(projetoEntity.getId())) {
				result = true;
			}
			
			if (!result && config.getMapProjetosGruaFlorestal().containsKey(projetoEntity.getId())) {
				result = true;
			}
		}
		
		return result;
	}


	
}

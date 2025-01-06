package br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.Infracao;
import br.com.crearesistemas.service.impl.helper.LocalizacaoCerca;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.service.dto.RastreamentoDto;
import br.com.crearesistemas.service.impl.helper.CERCAS;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeCaminhoesTransporteResponse {
	private static final Logger logger = Logger.getLogger(RecebeCaminhoesTransporteResponse.class);

	@XmlElementWrapper
	@XmlElement(name = "caminhaoTransporte")
	private List<CaminhaoTransporte> listaDeCaminhoesTransporte = new ArrayList<CaminhaoTransporte>();

	private Date dataHistorico;

	public List<CaminhaoTransporte> getListaDeCaminhoesTransporte() {
		return listaDeCaminhoesTransporte;
	}

	public void setListaDeCaminhoesTransporte(
			List<CaminhaoTransporte> listaDeCaminhoesTransporte) {
		this.listaDeCaminhoesTransporte = listaDeCaminhoesTransporte;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public void filtroPorIdLocalDeDescarga(String idLocalDeDescarga) {
		List<CaminhaoTransporte> caminhoes = this.getListaDeCaminhoesTransporte();
		
		List<CaminhaoTransporte> filtro = new ArrayList<CaminhaoTransporte>();
		
		for (CaminhaoTransporte caminhao : caminhoes){			
			if (caminhao.getIdLocalDeDescarga() != null && caminhao.getIdLocalDeDescarga().indexOf(idLocalDeDescarga) > -1 )
					filtro.add(caminhao);
		}
		
		this.setListaDeCaminhoesTransporte(filtro);
				
	}

		
	public void setCaminhoesEntity(List<CaminhaoEntity> caminhoes) {
		for (CaminhaoEntity caminhao : caminhoes) {
			
			RastreamentoDto rastreamento = new RastreamentoDto(caminhao.getLatitude(), caminhao.getLongitude());
			boolean implementoEstaNaFabrica = LocalizacaoCerca.getInstance().implementoEstaNoLocal(rastreamento, CERCAS.FABRICA);

			if (!implementoEstaNaFabrica) {
				addCaminhaoVazio(caminhao);	
			}
			
			
				
		}
		
	}

	public void addCaminhaoVazio(CaminhaoEntity caminhao) {
		// em viagem vazio ou carregado
		CaminhaoTransporte caminhaoTransporte = new CaminhaoTransporte();
		caminhaoTransporte.setId(caminhao.getId());
		caminhaoTransporte.setIdentificacao(caminhao.getName());
		caminhaoTransporte.setTipoImplemento(1);
		
		
		
		// estado de transporte
		if (caminhao.getTranspEstadoOperacional() != null 
				&& caminhao.getTranspEstadoOperacional() == EstadoOperacional.TRANSP_CARREGAMENTO.ordinal()) {
			caminhaoTransporte.setIdGruaFlorestal(caminhao.getIdGrua());
			caminhaoTransporte.setEstadoOperacionalTransporte(EstadoOperacional.TRANSP_CARREGAMENTO.ordinal());	
		} else 
		if (caminhao.getTranspEstadoOperacional() != null 
				&& caminhao.getTranspEstadoOperacional() == EstadoOperacional.TRANSP_FILA_EM_CAMPO.ordinal()) {
			caminhaoTransporte.setIdGruaFlorestal(caminhao.getIdGrua());
			caminhaoTransporte.setEstadoOperacionalTransporte(EstadoOperacional.TRANSP_FILA_EM_CAMPO.ordinal());	
		} else
    	if (caminhao.getTranspEstadoOperacional() != null) {
    		caminhaoTransporte.setIdGruaFlorestal(null);
			caminhaoTransporte.setEstadoOperacionalTransporte(caminhao.getTranspEstadoOperacional());
    	}
		else {
			caminhaoTransporte.setIdGruaFlorestal(null);
			caminhaoTransporte.setEstadoOperacionalTransporte(EstadoOperacional.TRANSP_VIAGEM.ordinal());
		}
		
		
        caminhaoTransporte.setDataUltimoRastreamento(caminhao.getAvldateTime());
        caminhaoTransporte.setDataPrevistaConclusao(new Date());
        
		caminhaoTransporte.setLatitude(caminhao.getLatitude());
		caminhaoTransporte.setLongitude(caminhao.getLongitude());
		caminhaoTransporte.setDataInicio(caminhao.getAvldateTime());
		
		caminhaoTransporte.setVelocidadeMedia(caminhao.getSpeed());
		caminhaoTransporte.setOrdemTransporte("OT0001");
		//caminhaoTransporte.setIdProjeto(36200l);
		if (caminhao.getLoading() == 1) {
			caminhaoTransporte.setEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());
		} else {
			caminhaoTransporte.setEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());		
		}
		

		Config config = Config.getInstance();
		caminhaoTransporte.setIdLocalDeDescarga(config.getLocalIdFabricaDescarga());							
		caminhaoTransporte.setSemApontamento(true);
		caminhaoTransporte.setTemApontamentoInvalido(false);		
		caminhaoTransporte.setPercentualConclusao(99);
		caminhaoTransporte.setIdPrestador(41l);
		caminhaoTransporte.setEmDesvio(false);
		caminhaoTransporte.setInfracaoDesvio(null);
		List<Infracao> listaDeInfracoesEmExcessoVelocidade = new ArrayList<Infracao>();
		caminhaoTransporte.setEmExcesso(!listaDeInfracoesEmExcessoVelocidade.isEmpty());
		caminhaoTransporte.setInfracaoVelocidade(null);
		List<Infracao> listaDeInfracoesEmComboio = new ArrayList<Infracao>();
		caminhaoTransporte.setEmComboio(!listaDeInfracoesEmComboio.isEmpty());
		caminhaoTransporte.setInfracaoComboio(null);            
        
		
		getListaDeCaminhoesTransporte().add(caminhaoTransporte);
		
	}

	public CaminhaoTransporte addCaminhao(CaminhaoEntity caminhao, CaminhaoTransporte pgCaminhao, HashMap<Long, String> projetos) {
		CaminhaoTransporte caminhaoTransp = new CaminhaoTransporte();
		// em viagem vazio ou carregado
		CaminhaoTransporte caminhaoTransporte = new CaminhaoTransporte();
		caminhaoTransporte.setId(caminhao.getId());
		caminhaoTransporte.setIdentificacao(caminhao.getName());
		caminhaoTransporte.setTipoImplemento(1);
		
		// estado de transporte
		if (caminhao.getTranspEstadoOperacional() != null 
				&& caminhao.getTranspEstadoOperacional() == EstadoOperacional.TRANSP_CARREGAMENTO.ordinal()) {
			caminhaoTransporte.setIdGruaFlorestal(caminhao.getIdGrua());
			caminhaoTransporte.setEstadoOperacionalTransporte(EstadoOperacional.TRANSP_CARREGAMENTO.ordinal());	
		} else 
		if (caminhao.getTranspEstadoOperacional() != null 
				&& caminhao.getTranspEstadoOperacional() == EstadoOperacional.TRANSP_FILA_EM_CAMPO.ordinal()) {
			caminhaoTransporte.setIdGruaFlorestal(caminhao.getIdGrua());
			caminhaoTransporte.setEstadoOperacionalTransporte(EstadoOperacional.TRANSP_FILA_EM_CAMPO.ordinal());	
		} else
    	if (caminhao.getTranspEstadoOperacional() != null) {
    		caminhaoTransporte.setIdGruaFlorestal(null);
			caminhaoTransporte.setEstadoOperacionalTransporte(caminhao.getTranspEstadoOperacional());
    	}
		else {
			caminhaoTransporte.setIdGruaFlorestal(null);
			caminhaoTransporte.setEstadoOperacionalTransporte(EstadoOperacional.TRANSP_VIAGEM.ordinal());
		}
		
		
        caminhaoTransporte.setDataUltimoRastreamento(caminhao.getAvldateTime());
        caminhaoTransporte.setDataPrevistaConclusao(new Date());
        
        Date agora = new Date();
        Date dataInicio;
        
        if (caminhao.getPercentualConclusao() != null) {
        	dataInicio = DateUtils.diminuirSegundos(agora, caminhao.getPercentualConclusao());	
        } else {
        	dataInicio = caminhao.getAvldateTime();
        }
        caminhaoTransporte.setDataInicio(dataInicio);
		caminhaoTransporte.setLatitude(caminhao.getLatitude());
		caminhaoTransporte.setLongitude(caminhao.getLongitude());
		
		caminhaoTransporte.setDataPartidaPcct(caminhao.getDataPartidaPcct());
		caminhaoTransporte.setDataChegadaPcsi(caminhao.getDataChegadaPcsi());
		
		
		Config config = Config.getInstance();
		caminhaoTransporte.setVelocidadeMedia(caminhao.getSpeed());
		if (caminhao.getProjectId() != null && projetos.containsKey(caminhao.getProjectId())) {
			caminhaoTransporte.setIdProjeto(caminhao.getProjectId());
			caminhaoTransporte.setProjeto(caminhao.getProjectIdName());
		}
		
		if (caminhao.getLoading() == Config.CARREG_CHEIO) {
			caminhaoTransporte.setEstadoOperacionalCarregamento(EstadoOperacional.CARREG_CHEIO.ordinal());
			
			if (caminhao.getApontCampoInvalido() != null) {
				caminhaoTransporte.setTemApontamentoInvalido(caminhao.getApontCampoInvalido());	
			}
			
			if (caminhao.getEventLoadId() != null) {
				caminhaoTransporte.setSemApontamento(false);	
			} else {
				caminhaoTransporte.setSemApontamento(true);
			}
			
		} else {
			caminhaoTransporte.setEstadoOperacionalCarregamento(EstadoOperacional.CARREG_VAZIO.ordinal());
			caminhaoTransporte.setSemApontamento(false);
			caminhaoTransporte.setTemApontamentoInvalido(false);
		}
		

		caminhaoTransporte.setIdLocalDeDescarga(config.getLocalIdFabricaDescarga());
		caminhaoTransporte.setPercentualConclusao(caminhao.getPercentualConclusao());
		caminhaoTransporte.setIdPrestador(config.getPrestador(caminhao.getCustomerChildId()).longValue());
		caminhaoTransporte.setPrestador(config.getPrestadorName(caminhao.getCustomerChildId()));
		caminhaoTransporte.setEmDesvio(false);
		caminhaoTransporte.setInfracaoDesvio(null);
		List<Infracao> listaDeInfracoesEmExcessoVelocidade = new ArrayList<Infracao>();
		caminhaoTransporte.setEmExcesso(!listaDeInfracoesEmExcessoVelocidade.isEmpty());
		caminhaoTransporte.setInfracaoVelocidade(null);
		List<Infracao> listaDeInfracoesEmComboio = new ArrayList<Infracao>();
		caminhaoTransporte.setEmComboio(!listaDeInfracoesEmComboio.isEmpty());
		caminhaoTransporte.setInfracaoComboio(null);            
        
		if (pgCaminhao != null) {
			if (pgCaminhao.getIdProjeto() != null && projetos.containsKey(pgCaminhao.getIdProjeto())) {
				caminhaoTransporte.setIdProjeto(pgCaminhao.getIdProjeto());
				caminhaoTransporte.setProjeto(pgCaminhao.getProjeto());
			}
			
			if (pgCaminhao.getOrdemTransporte() != null) {
				caminhaoTransporte.setOrdemTransporte(pgCaminhao.getOrdemTransporte());
			}
			
			if (pgCaminhao.getIdPrestador() != null) {
				caminhaoTransporte.setIdPrestador(pgCaminhao.getIdPrestador());
				caminhaoTransporte.setPrestador(pgCaminhao.getPrestador());
			}
			
			if (pgCaminhao.getPercentualConclusao() != null) {
				caminhaoTransporte.setPercentualConclusao(pgCaminhao.getPercentualConclusao());
			}
			
			if (pgCaminhao.getDataPrevistaConclusao() != null) {
				caminhaoTransporte.setDataPrevistaConclusao(pgCaminhao.getDataPrevistaConclusao());
			}
		}
		
		getListaDeCaminhoesTransporte().add(caminhaoTransporte);
		
		return caminhaoTransp;
	}

	
	

	
}

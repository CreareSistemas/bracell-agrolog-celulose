package br.com.crearesistemas.pcsil1.wsrecebegruassiteindustrial.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.service.GruasService;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeGruasSiteIndustrialResponse {
	private static final Logger logger = Logger.getLogger(RecebeGruasSiteIndustrialResponse.class);
	@XmlElementWrapper
	@XmlElement(name = "gruaSiteIndustrial")
	private List<GruaSiteIndustrial> listaDeGruasSiteIndustrial = new ArrayList<GruaSiteIndustrial>();

	private Date dataHistorico;

	
	public List<GruaSiteIndustrial> getListaDeGruasSiteIndustrial() {
		return listaDeGruasSiteIndustrial;
	}

	public void setListaDeGruasSiteIndustrial(
			List<GruaSiteIndustrial> listaDeGruasSiteIndustrial) {
		this.listaDeGruasSiteIndustrial = listaDeGruasSiteIndustrial;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public void setGruasEntity(Config config,List<GruaEntity> gruas, GruasService gruasService) {
		
		for (GruaEntity grua : gruas) {
			if (grua.isMesaL1() && config.isLocalMesas(grua.getIdLocal())) {
				addGruaMesa(config, grua);				
			} else {
				addGruaPatio(config, grua);
			}
		}
	}

	

	private Boolean addGruaPatio(Config config, GruaEntity grua) {
		Boolean result = false;
		
		GruaSiteIndustrial gruaMovel = new GruaSiteIndustrial();
		gruaMovel.setId(grua.getId());
		gruaMovel.setIdTipoImplemento(TipoImplemento.GRUA_MOVEL.ordinal());
		gruaMovel.setIdentificacao(grua.getName());
				
		
		if (grua.getEventCode() != null) {
			gruaMovel.setIdEstadoOperacionalProprio(config.eventoEstadoMap(TipoImplemento.GRUA_MOVEL, grua.getEventCode()).ordinal());
			grua.setProprioEstadoOperacional(gruaMovel.getIdEstadoOperacionalProprio());
		} else
		if (grua.getProprioEstadoOperacional() != null) {
			gruaMovel.setIdEstadoOperacionalProprio(grua.getProprioEstadoOperacional());
			grua.setProprioEstadoOperacional(grua.getProprioEstadoOperacional());
		} else {
			result = true;
			gruaMovel.setIdEstadoOperacionalProprio(EstadoOperacional.DISPONIVEL.ordinal());
			grua.setProprioEstadoOperacional(EstadoOperacional.DISPONIVEL.ordinal());				
		}
		
				
		if (grua.getSiteEstadoOperacional() != null) {			
			if (grua.getSiteEstadoOperacional() == EstadoOperacional.SI_QUADRAS.ordinal() && config.isLocalQuadras(grua.getIdLocal())) {
				gruaMovel.setIdLocal(grua.getIdLocal());
				gruaMovel.setIdEstadoOperacionalSI(grua.getSiteEstadoOperacional());				
			} else {
				gruaMovel.setIdEstadoOperacionalSI(EstadoOperacional.SI_STANDBY.ordinal());
				gruaMovel.setIdLocal(config.getLocalId15StandBy().longValue());
			}				
		} else {
			result = true;
			gruaMovel.setIdEstadoOperacionalSI(EstadoOperacional.SI_STANDBY.ordinal());
			gruaMovel.setIdLocal(config.getLocalId15StandBy().longValue());
			
			grua.setSiteEstadoOperacional(EstadoOperacional.SI_STANDBY.ordinal());			
			grua.setIdLocal(config.getLocalId15StandBy().longValue());
		}
		
		
		gruaMovel.setDataUltimoApontamento(grua.getEventDateTime());
		
		if (grua.getEventdateTime() != null) {
			gruaMovel.setDataInicio(grua.getEventdateTime());
		} else {
			gruaMovel.setDataInicio(new Date());	
		}
		
		
		
		gruaMovel.setDataUltimoRastreamento(grua.getAvldateTime());
		gruaMovel.setDataUltimoCarregamento(grua.getEventDateTime());
		gruaMovel.setCarregamento(null);					
		getListaDeGruasSiteIndustrial().add(gruaMovel);
		
		return result;
	}

	private Boolean addGruaMesa(Config config,GruaEntity grua) {
		Boolean result = false;
		// gruas fixas e multidocker			
		GruaSiteIndustrial gruaFixa = new GruaSiteIndustrial();
		gruaFixa.setId(grua.getId());
		gruaFixa.setIdTipoImplemento(TipoImplemento.GRUA_FIXA.ordinal());
		gruaFixa.setIdentificacao(grua.getName());

		// proprio
		if (grua.getEventCode() != null) {
			gruaFixa.setIdEstadoOperacionalProprio(config.eventoEstadoMap(TipoImplemento.GRUA_FIXA, grua.getEventCode()).ordinal());
			grua.setProprioEstadoOperacional(gruaFixa.getIdEstadoOperacionalProprio());
		} else
		if (grua.getProprioEstadoOperacional() != null) {
			gruaFixa.setIdEstadoOperacionalProprio(grua.getProprioEstadoOperacional());
			grua.setProprioEstadoOperacional(grua.getProprioEstadoOperacional());
		} else {
			result = true;
			gruaFixa.setIdEstadoOperacionalProprio(EstadoOperacional.DISPONIVEL.ordinal());
			grua.setProprioEstadoOperacional(EstadoOperacional.DISPONIVEL.ordinal());				
		}
		
		// site
		result = true;
		gruaFixa.setIdEstadoOperacionalSI(EstadoOperacional.SI_MESAS.ordinal());
		grua.setSiteEstadoOperacional(EstadoOperacional.SI_MESAS.ordinal());
		/*
		if (grua.getSiteEstadoOperacional() != null) {
			gruaFixa.setIdEstadoOperacionalSI(grua.getSiteEstadoOperacional());
			grua.setSiteEstadoOperacional(grua.getSiteEstadoOperacional());
		} else {
			result = true;
			gruaFixa.setIdEstadoOperacionalSI(EstadoOperacional.SI_MESAS.ordinal());
			grua.setSiteEstadoOperacional(EstadoOperacional.SI_MESAS.ordinal());
		}
		*/
		
		// local
		if (config.isLocalMesas(grua.getIdLocal())  && !config.configCraneFix()) {
			gruaFixa.setIdLocal(grua.getIdLocal());				
		} else {			
			gruaFixa.setIdLocal(config.getLocalIdMesaByCraneL1(grua.getName()));
			grua.setIdLocal(config.getLocalIdMesaByCraneL1(grua.getName()));
		}
		
		if (grua.getEventdateTime() != null) {
			gruaFixa.setDataInicio(grua.getEventdateTime());
		} else {
			gruaFixa.setDataInicio(new Date());	
		}
		
		gruaFixa.setDataUltimoApontamento(grua.getEventDateTime());
		gruaFixa.setDataUltimoRastreamento(grua.getAvldateTime());
		gruaFixa.setDataUltimoCarregamento(grua.getEventDateTime());
		gruaFixa.setCarregamento(null);
		gruaFixa.setMostrarDetalhes(false);
		getListaDeGruasSiteIndustrial().add(gruaFixa);
		
		return result;
	}
	


	
	
}

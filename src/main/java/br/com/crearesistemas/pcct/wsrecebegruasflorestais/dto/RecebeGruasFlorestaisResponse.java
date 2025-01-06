package br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.model.GruaEntity;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeGruasFlorestaisResponse {
	
	@XmlElementWrapper
	@XmlElement(name = "gruaFlorestal")
	private List<GruaFlorestal> listaDeGruasFlorestais = new ArrayList<GruaFlorestal>();

	private Date dataHistorico;
	
	public List<GruaFlorestal> getListaDeGruasFlorestais() {
		return listaDeGruasFlorestais;
	}

	public void setListaDeGruasFlorestais(List<GruaFlorestal> listaDeGruasFlorestais) {
		this.listaDeGruasFlorestais = listaDeGruasFlorestais;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	/*
	 * public void informaLocaisDeCargaEDescarga(Local localCarga, Local
	 * localDescarga) { List<GruaFlorestal> listaDeGruasFlorestais =
	 * this.getListaDeGruasFlorestais();
	 * 
	 * for (GruaFlorestal grua : listaDeGruasFlorestais){ if (grua.getIdProjeto() ==
	 * localCarga.getId()) { if
	 * (!grua.locaisDeDescargaContainsId(localDescarga.getId())) {
	 * grua.getLocaisDeDescarga().add(LocalDTO.getLocalDto(localDescarga)); } } }
	 * 
	 * 
	 * }
	 */
	public void filtroPorIdLocalDeDescarga(String idLocalDeDescarga,	Boolean incluirGruasSemOT) {
		List<GruaFlorestal> gruas = this.getListaDeGruasFlorestais();
		
		List<GruaFlorestal> filtro = new ArrayList<GruaFlorestal>();
		
		for (GruaFlorestal grua : gruas){
			if (grua.locaisDeDescargaContains(idLocalDeDescarga)) {
				filtro.add(grua);
			}
			
			if (incluirGruasSemOT != null && incluirGruasSemOT) {
				if (grua.getLocaisDeDescarga().size() < 1) {
					filtro.add(grua);
				}	
			}
			
		}
		
		this.setListaDeGruasFlorestais(filtro);
		
	}

	public void setGruasEntity(List<GruaEntity> gruas) {
		
		for (GruaEntity grua : gruas) {
			GruaFlorestal gruaFlorestal = new GruaFlorestal();
			
			gruaFlorestal.setId(grua.getId());		
			gruaFlorestal.setIdentificacao(grua.getPrefix());
			//gruaFlorestal.setIdProjeto(36200l);
			gruaFlorestal.setEstadoOperacional(0);
			gruaFlorestal.setDataUltimoRastreamento(grua.getAvldateTime());
			gruaFlorestal.setDataUltimoApontamento(grua.getEventDateTime());
			gruaFlorestal.setDataUltimoCarregamento(grua.getEventDateTime());
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
			
			getListaDeGruasFlorestais().add(gruaFlorestal);	
		}
		
	}
}

package br.com.crearesistemas.pcsil1.wsrecebegruassiteindustrial.dto;


import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.AgendamentoDTO;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class GruaSiteIndustrial {

	@XmlElement(required = true)
	private Long id;	
	
	@XmlElement(required = true)
	private String identificacao;
		
	@XmlElement(required = true)
	private Integer idTipoImplemento;

	@XmlElement(required = true)
	private Integer idEstadoOperacionalSI;
	
	@XmlElement(required = true)
	private Date dataInicio;
	
	@XmlElement(required = true)
	private Long idLocal;

	@XmlElement(required = false)
	private Integer idPileCode;	
	private String pileName;
	
	//@XmlTransient
	//private EstadoOperacional estadoOperacionalSI;	
	
	@XmlTransient
	private EstadoOperacional estadoOperacionalPM;
	
	//@XmlTransient
	//private Local local;
	
	private Integer idEstadoOperacionalProprio;

	private Date dataUltimoRastreamento;

	private Date dataUltimoApontamento;
	
	private Date dataUltimoCarregamento;
	
	private AgendamentoDTO agendamento = new AgendamentoDTO().mock();
	
	private boolean mostrarDetalhes = false;

	private String carregamento;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Integer getIdTipoImplemento() {
		return idTipoImplemento;
	}

	public void setIdTipoImplemento(Integer idTipoImplemento) {
		this.idTipoImplemento = idTipoImplemento;
	}

	public Integer getIdEstadoOperacionalSI() {
		return idEstadoOperacionalSI;
	}

	public void setIdEstadoOperacionalSI(Integer idEstadoOperacionalSI) {
		this.idEstadoOperacionalSI = idEstadoOperacionalSI;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdEstadoOperacionalProprio() {
		return idEstadoOperacionalProprio;
	}

	public void setIdEstadoOperacionalProprio(Integer idEstadoOperacionalProprio) {
		this.idEstadoOperacionalProprio = idEstadoOperacionalProprio;
	}

	public Date getDataUltimoRastreamento() {
		return dataUltimoRastreamento;
	}

	public void setDataUltimoRastreamento(Date dataUltimoRastreamento) {
		this.dataUltimoRastreamento = dataUltimoRastreamento;
	}

	public Date getDataUltimoApontamento() {
		return dataUltimoApontamento;
	}

	public void setDataUltimoApontamento(Date dataUltimoApontamento) {
		this.dataUltimoApontamento = dataUltimoApontamento;
	}

	public Date getDataUltimoCarregamento() {
		return dataUltimoCarregamento;
	}

	public void setDataUltimoCarregamento(Date dataUltimoCarregamento) {
		this.dataUltimoCarregamento = dataUltimoCarregamento;
	}

	
  public AgendamentoDTO getAgendamento() { return agendamento; }
  
  public void setAgendamento(AgendamentoDTO agendamento) { 
	  this.agendamento =  agendamento; 
  }
	 

	public boolean isMostrarDetalhes() {
		return mostrarDetalhes;
	}

	public void setMostrarDetalhes(boolean mostrarDetalhes) {
		this.mostrarDetalhes = mostrarDetalhes;
	}

	public String getCarregamento() {
		return carregamento;
	}

	public void setCarregamento(String carregamento) {
		this.carregamento = carregamento;
	}

//	public EstadoOperacional getEstadoOperacionalSI() {
//		return estadoOperacionalSI;
//	}
//
//	public void setEstadoOperacionalSI(EstadoOperacional estadoOperacionalSI) {
//		this.estadoOperacionalSI = estadoOperacionalSI;
//	}
//	
	public EstadoOperacional getEstadoOperacionalPM() {
		return estadoOperacionalPM;
	}

	public void setEstadoOperacionalPM(EstadoOperacional estadoOperacionalPM) {
		this.estadoOperacionalPM = estadoOperacionalPM;
	}

	public Integer getIdPileCode() {
		return idPileCode;
	}

	public void setIdPileCode(Integer idPileCode) {
		this.idPileCode = idPileCode;
	}

	public String getPileName() {
		return pileName;
	}

	public void setPileName(String pileName) {
		this.pileName = pileName;
	}

	
	
	/*
	 * public Local getLocal() { return local; }
	 * 
	 * public void setLocal(Local local) { this.local = local; }
	 */
	
	
}

package br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.model.GruaEntity;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class GruaFlorestal {

	@XmlElement(required=true)
	private Long id;
	
	@XmlElement(required=true)
	private String identificacao;
	
	private Long idProjeto;

	@XmlElement(required=true)
	private Integer estadoOperacional;
	
	@XmlElement(required=true)
	private Date dataUltimoRastreamento;

	@XmlElement(required=true)
	private Date dataUltimoApontamento;
	
	private Date dataUltimoCarregamento;
	
	private String projetoUltimoCarregamento;
	
	private String talhaoUltimoCarregamento;
	
	private String pilhaUltimoCarregamento;
	
	private String placaUltimoCarregamento;
		
	@XmlElement(required = true)
	private Boolean desligada = false;

	private Long idPrestador;
	
	private Boolean temApontamentoInvalido = false;

	private Integer tempoMedioFilaEmMinutos;
	
	private Integer tempoMedioCarregamentoEmMinutos;
	
	//@XmlElement(required = true)
	private Boolean isInconsistente = false;
	
	private Float latitude;
	
	private Float longitude;
	
	private Integer tipoImplemento;
	
	// possiveis locais de descarga das ots do dia
	@XmlTransient
	private List<LocalDTO> locaisDeDescarga = new ArrayList<LocalDTO>();

	public Boolean getIsInconsistente() {
		return isInconsistente;
	}

	public void setIsInconsistente(Boolean isInconsistente) {
		this.isInconsistente = isInconsistente;
	}

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

	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public Integer getEstadoOperacional() {
		return estadoOperacional;
	}

	public void setEstadoOperacional(Integer estadoOperacional) {
		this.estadoOperacional = estadoOperacional;
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

	public String getProjetoUltimoCarregamento() {
		return projetoUltimoCarregamento;
	}

	public void setProjetoUltimoCarregamento(String projetoUltimoCarregamento) {
		this.projetoUltimoCarregamento = projetoUltimoCarregamento;
	}

	public String getTalhaoUltimoCarregamento() {
		return talhaoUltimoCarregamento;
	}

	public void setTalhaoUltimoCarregamento(String talhaoUltimoCarregamento) {
		this.talhaoUltimoCarregamento = talhaoUltimoCarregamento;
	}

	public String getPilhaUltimoCarregamento() {
		return pilhaUltimoCarregamento;
	}

	public void setPilhaUltimoCarregamento(String pilhaUltimoCarregamento) {
		this.pilhaUltimoCarregamento = pilhaUltimoCarregamento;
	}

	public String getPlacaUltimoCarregamento() {
		return placaUltimoCarregamento;
	}

	public void setPlacaUltimoCarregamento(String placaUltimoCarregamento) {
		this.placaUltimoCarregamento = placaUltimoCarregamento;
	}

	public Boolean getDesligada() {
		return desligada;
	}

	public void setDesligada(Boolean desligada) {
		this.desligada = desligada;
	}

	public Long getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Long idPrestador) {
		this.idPrestador = idPrestador;
	}

	public Boolean getTemApontamentoInvalido() {
		return temApontamentoInvalido;
	}

	public void setTemApontamentoInvalido(Boolean temApontamentoInvalido) {
		this.temApontamentoInvalido = temApontamentoInvalido;
	}

	public Integer getTempoMedioFilaEmMinutos() {
		return tempoMedioFilaEmMinutos;
	}

	public void setTempoMedioFilaEmMinutos(Integer tempoMedioFilaEmMinutos) {
		this.tempoMedioFilaEmMinutos = tempoMedioFilaEmMinutos;
	}

	public Integer getTempoMedioCarregamentoEmMinutos() {
		return tempoMedioCarregamentoEmMinutos;
	}

	public void setTempoMedioCarregamentoEmMinutos(
			Integer tempoMedioCarregamentoEmMinutos) {
		this.tempoMedioCarregamentoEmMinutos = tempoMedioCarregamentoEmMinutos;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Integer getTipoImplemento() {
		return tipoImplemento;
	}

	public void setTipoImplemento(Integer tipoImplemento) {
		this.tipoImplemento = tipoImplemento;
	}

	public List<LocalDTO> getLocaisDeDescarga() {
		return locaisDeDescarga;
	}

	public void setLocaisDeDescarga(List<LocalDTO> locaisDeDescarga) {
		this.locaisDeDescarga = locaisDeDescarga;
	}

	public boolean locaisDeDescargaContains(String idLocalIntegracao) {
		// verifica se tem o local de descarga		
		for(LocalDTO local : this.getLocaisDeDescarga()){
			if (local.getIntegracao() != null && (local.getIntegracao().indexOf(idLocalIntegracao) > -1)) return true;
		}
		
		return false;
	}

	public boolean locaisDeDescargaContainsId(Long idLocal) {
		// verifica se tem o local de descarga		
		for(LocalDTO local : this.getLocaisDeDescarga()){
			if (local.getIdLocal() == idLocal) return true;
		}
		
		return false;
	}

	
		
}

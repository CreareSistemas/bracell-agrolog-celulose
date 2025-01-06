package br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.enumeration.EstadoOperacional;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CaminhaoSiteIndustrial {

	@XmlElement(required = true)
	private Long id;	
	
	@XmlElement(required = true)
	private String identificacao;
		
	@XmlElement(required = true)
	private Integer idTipoImplemento;

	@XmlElement(required = true)
	private Integer idEstadoOperacionalSI;
	
	@XmlElement(required = true)
	private Integer idEstadoOperacionalCarregamento;

	@XmlElement(required = true)
	private Date dataInicio;
	
	private Date dataChegada;

	@XmlElement(required = true)
	private Long idLocal;
		
	private Long idGrua;

	private Long idPrestador;
	
	private Long idEstoque;

	private Date dataUltimoRastreamento;
	
	private AgendamentoDTO agendamento = new AgendamentoDTO().mock();
	
	private boolean mostrarDetalhes = false;
	
	private Float latitude;

	private Float longitude;
	
	private String ordemTransporte;
	
	private Integer idEstadoOperacionalProprio;
	
	private Boolean calibracao = false;
	
	@XmlTransient
	private EstadoOperacional estadoOperacionalSI;

	@XmlTransient
	private EstadoOperacional estadoOperacionalPM;
	
	@XmlTransient
	private Date dataInicioPM;
	

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

	public Integer getIdEstadoOperacionalCarregamento() {
		return idEstadoOperacionalCarregamento;
	}

	public void setIdEstadoOperacionalCarregamento(
			Integer idEstadoOperacionalCarregamento) {
		this.idEstadoOperacionalCarregamento = idEstadoOperacionalCarregamento;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public Long getIdGrua() {
		return idGrua;
	}

	public void setIdGrua(Long idGrua) {
		this.idGrua = idGrua;
	}

	public Long getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Long idPrestador) {
		this.idPrestador = idPrestador;
	}

	public Long getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Long idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Date getDataUltimoRastreamento() {
		return dataUltimoRastreamento;
	}

	public void setDataUltimoRastreamento(Date dataUltimoRastreamento) {
		this.dataUltimoRastreamento = dataUltimoRastreamento;
	}

	public AgendamentoDTO getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(AgendamentoDTO agendamento) {
		this.agendamento = agendamento;
	}

	public boolean isMostrarDetalhes() {
		return mostrarDetalhes;
	}

	public void setMostrarDetalhes(boolean mostrarDetalhes) {
		this.mostrarDetalhes = mostrarDetalhes;
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

	public String getOrdemTransporte() {
		return ordemTransporte;
	}

	public void setOrdemTransporte(String ordemTransporte) {
		this.ordemTransporte = ordemTransporte;
	}

	public Integer getIdEstadoOperacionalProprio() {
		return idEstadoOperacionalProprio;
	}

	public void setIdEstadoOperacionalProprio(Integer idEstadoOperacionalProprio) {
		this.idEstadoOperacionalProprio = idEstadoOperacionalProprio;
	}

	public EstadoOperacional getEstadoOperacionalSI() {
		return estadoOperacionalSI;
	}

	public void setEstadoOperacionalSI(EstadoOperacional estadoOperacionalSI) {
		this.estadoOperacionalSI = estadoOperacionalSI;
	}

	public EstadoOperacional getEstadoOperacionalPM() {
		return estadoOperacionalPM;
	}

	public void setEstadoOperacionalPM(EstadoOperacional estadoOperacionalPM) {
		this.estadoOperacionalPM = estadoOperacionalPM;
	}

	public Date getDataInicioPM() {
		return dataInicioPM;
	}

	public void setDataInicioPM(Date dataInicioPM) {
		this.dataInicioPM = dataInicioPM;
	}

	public Boolean getCalibracao() {
		return calibracao;
	}

	public void setCalibracao(Boolean calibracao) {
		this.calibracao = calibracao;
	}
	
}

package br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CaminhaoTransporte {

	@XmlElement(required = true)
	private Long id;
	
	@XmlElement(required = true)
	private String identificacao;
	
	// TODO [mendes]: @XmlElement(required = true)
	private Long idProjeto;   // field(locales.id)
	
	private Long projectCode; // field(locales.integration)
	private String projectId;    // field(locales.farm_integration_id)

	
	private String projeto;
	
	// linha 1 ou 2
	private Integer lineId;

	@XmlElement(required = true)
	private Long idGruaFlorestal;

	@XmlElement(required = true)
	private Integer estadoOperacionalTransporte;

	@XmlElement(required = true)
	private Integer estadoOperacionalCarregamento;

	@XmlElement(required = true)
	private Date dataInicio;

	private Date dataPartidaPcct;
	private Date dataChegadaPcsi;
	
	@XmlElement(required = true)
	private Date dataPrevistaConclusao;
	
	@XmlElement(required = true)
	private Date dataUltimoRastreamento;
	
	@XmlElement(required = true)
	private Integer percentualConclusao;

	@XmlElement(required = true)
	private Integer velocidadeMedia;

	private Long idPrestador;
	private String prestador;
	
	@XmlElement(required = true)
	private Boolean emExcesso = false;
	
	private ItemInfracao infracaoVelocidade;
	
	@XmlElement(required = true)
	private Boolean emComboio = false;
	
	private ItemInfracao infracaoComboio;
	
	@XmlElement(required = true)
	private Boolean emDesvio = false;
	
	private ItemInfracao infracaoDesvio;
	
	@XmlElement(required = true)
	private Boolean semApontamento = false;

	@XmlElement(required = true)
	private Boolean temApontamentoInvalido = false;
	
	@XmlElement(required = true)
	private Float latitude;

	@XmlElement(required = true)
	private Float longitude;
	
	private String ordemTransporte;
	private SituacaoOrdemTransporte ordemSituacao;
	
	private Integer tipoImplemento;
	
	private String idLocalDeDescarga;
	
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
	
	

	public Long getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(Long projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDataChegadaPcsi() {
		return dataChegadaPcsi;
	}

	public void setDataChegadaPcsi(Date dataChegadaPcsi) {
		this.dataChegadaPcsi = dataChegadaPcsi;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

	public Long getIdGruaFlorestal() {
		return idGruaFlorestal;
	}

	public void setIdGruaFlorestal(Long idGruaFlorestal) {
		this.idGruaFlorestal = idGruaFlorestal;
	}

	public Integer getEstadoOperacionalTransporte() {
		return estadoOperacionalTransporte;
	}

	public void setEstadoOperacionalTransporte(Integer estadoOperacionalTransporte) {
		this.estadoOperacionalTransporte = estadoOperacionalTransporte;
	}

	public Integer getEstadoOperacionalCarregamento() {
		return estadoOperacionalCarregamento;
	}

	public void setEstadoOperacionalCarregamento(
			Integer estadoOperacionalCarregamento) {
		this.estadoOperacionalCarregamento = estadoOperacionalCarregamento;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataPrevistaConclusao() {
		return dataPrevistaConclusao;
	}

	public void setDataPrevistaConclusao(Date dataPrevistaConclusao) {
		this.dataPrevistaConclusao = dataPrevistaConclusao;
	}
	
	public Date getDataUltimoRastreamento() {
		return dataUltimoRastreamento;
	}

	public void setDataUltimoRastreamento(Date dataUltimoRastreamento) {
		this.dataUltimoRastreamento = dataUltimoRastreamento;
	}

	public Integer getPercentualConclusao() {
		return percentualConclusao;
	}

	public void setPercentualConclusao(Integer percentualConclusao) {
		this.percentualConclusao = percentualConclusao;
	}

	public Integer getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(Integer velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}
	
	public Long getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Long idPrestador) {
		this.idPrestador = idPrestador;
	}

	public Boolean getEmExcesso() {
		return emExcesso;
	}

	public void setEmExcesso(Boolean emExcesso) {
		this.emExcesso = emExcesso;
	}

	public Boolean getEmComboio() {
		return emComboio;
	}

	public void setEmComboio(Boolean emComboio) {
		this.emComboio = emComboio;
	}

	public Boolean getEmDesvio() {
		return emDesvio;
	}

	public void setEmDesvio(Boolean emDesvio) {
		this.emDesvio = emDesvio;
	}

	public Boolean getSemApontamento() {
		return semApontamento;
	}

	
	public String getPrestador() {
		return prestador;
	}

	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}

	public void setSemApontamento(Boolean semApontamento) {
		this.semApontamento = semApontamento;
	}
	
	
	public Date getDataPartidaPcct() {
		return dataPartidaPcct;
	}

	public void setDataPartidaPcct(Date dataPartidaPcct) {
		this.dataPartidaPcct = dataPartidaPcct;
	}


	public Boolean getTemApontamentoInvalido() {
		return temApontamentoInvalido;
	}

	public void setTemApontamentoInvalido(Boolean temApontamentoInvalido) {
		this.temApontamentoInvalido = temApontamentoInvalido;
	}

	public String getOrdemTransporte() {
		return ordemTransporte;
	}

	public void setOrdemTransporte(String ordemTransporte) {
		this.ordemTransporte = ordemTransporte;
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

	public ItemInfracao getInfracaoVelocidade() {
		return infracaoVelocidade;
	}

	public void setInfracaoVelocidade(ItemInfracao infracaoVelocidade) {
		this.infracaoVelocidade = infracaoVelocidade;
	}

	public ItemInfracao getInfracaoComboio() {
		return infracaoComboio;
	}

	public void setInfracaoComboio(ItemInfracao infracaoComboio) {
		this.infracaoComboio = infracaoComboio;
	}

	public ItemInfracao getInfracaoDesvio() {
		return infracaoDesvio;
	}

	public void setInfracaoDesvio(ItemInfracao infracaoDesvio) {
		this.infracaoDesvio = infracaoDesvio;
	}

	public String getIdLocalDeDescarga() {
		return idLocalDeDescarga;
	}

	public void setIdLocalDeDescarga(String idLocalDeDescarga) {
		this.idLocalDeDescarga = idLocalDeDescarga;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public SituacaoOrdemTransporte getOrdemSituacao() {
		return ordemSituacao;
	}

	public void setOrdemSituacao(SituacaoOrdemTransporte ordemSituacao) {
		this.ordemSituacao = ordemSituacao;
	}
	
	public boolean hasOpenOrder() {		
		if (getOrdemSituacao() != null && getOrdemSituacao() == SituacaoOrdemTransporte.LIBERADA) {
			return true;
		}
		return false;
	}


}

package br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto;


import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ImplementoIndisponivel {

	@XmlElement(required = true)
	private Long id;	

	@XmlElement(required = true)
	private String identificacao;

	@XmlElement(required = true)
	private Integer idEstadoOperacional;
	
	@XmlElement(required=true)
	private Integer estadoOperacional;
	
	
	@XmlElement(required = true)
	private Integer idTipoImplemento;
	
	private Long idProjeto;
	
	private Integer estadoOperacionalTransporte;

	private Integer estadoOperacionalCarregamento;

	private Long idGruaFlorestal;

	private Date dataInicio;

	private Date dataPrevistaConclusao;
	
	private Date dataUltimoRastreamento;
	
	private Integer percentualConclusao;

	private Integer velocidadeMedia;

	private Long idPrestador;
	
	private Boolean emExcesso = false;
	
	private Boolean emComboio = false;
	
	private Boolean emDesvio = false;
	
	private Boolean semApontamento = false;

	private Boolean temApontamentoInvalido = false;
	
	private Float latitude;

	private Float longitude;

	private String ordemTransporte;
	
	
	// Grua Florestal
	private Date dataUltimoApontamento;
	
	private Date dataUltimoCarregamento;
	
	private String projetoUltimoCarregamento;
	
	private String talhaoUltimoCarregamento;
	
	private String pilhaUltimoCarregamento;
	
	private String placaUltimoCarregamento;
		
	private Boolean desligada = false;

	private Integer tempoMedioFilaEmMinutos;
	
	private Integer tempoMedioCarregamentoEmMinutos;
	
	private Boolean isInconsistente = false;

	public Integer getEstadoOperacional() {
		return estadoOperacional;
	}


	public void setEstadoOperacional(Integer estadoOperacional) {
		this.estadoOperacional = estadoOperacional;
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


	public Boolean getIsInconsistente() {
		return isInconsistente;
	}


	public void setIsInconsistente(Boolean isInconsistente) {
		this.isInconsistente = isInconsistente;
	}


	public ImplementoIndisponivel() {
		super();
	}

	public ImplementoIndisponivel(GruaEntity grua, Integer proprioEstadoOperacional, TipoImplemento tipo) {
		setId(grua.getId());
		setIdentificacao(grua.getName());
		setIdEstadoOperacional(proprioEstadoOperacional);
		
		if (tipo == TipoImplemento.GRUA_FLORESTAL) {
			gruaFlorestal(grua);	
		} else {
			gruaSite(grua);	
		}
		
	}
	
	private void gruaSite(GruaEntity grua) {
		Config config = Config.getInstance();
		
		if (config.isMesa(grua.getName()) || config.isMesaL1(grua.getName())) {
			setIdTipoImplemento(TipoImplemento.GRUA_FIXA.ordinal());	
		} else {
			setIdTipoImplemento(TipoImplemento.GRUA_MOVEL.ordinal());
		}
		
		setEmComboio(null);
		setEmDesvio(null);
		setEmExcesso(null);
		setEstadoOperacionalCarregamento(null);
		setEstadoOperacionalTransporte(null);
		setVelocidadeMedia(null);
		
		setEstadoOperacional(grua.getProprioEstadoOperacional());
		setDataUltimoApontamento(grua.getEventdateTime());
		
		setDataUltimoRastreamento(grua.getAvldateTime());
		setLatitude(grua.getLatitude());
		setLongitude(grua.getLongitue());
		setIdProjeto(grua.getIdLocal());
		setIdPrestador(Config.getInstance().buscarPrestadorPorId(grua.getCustomerChildId()).getId());
		setTemApontamentoInvalido(false);
	}

	public ImplementoIndisponivel(CaminhaoEntity caminhao, Integer proprioEstadoOperacional,TipoImplemento caminhaoTransporte) {
		setId(caminhao.getId());
		setIdentificacao(caminhao.getName());
		setIdEstadoOperacional(proprioEstadoOperacional);
		setIdTipoImplemento(TipoImplemento.CAMINHAO_TRANSPORTE.ordinal());
		
		setDataInicio(caminhao.getDataPartidaPcct());
		setDataPrevistaConclusao(caminhao.getDataPrevisaoChegadaFabrica());
		setVelocidadeMedia(caminhao.getSpeed());
		
		setPercentualConclusao(caminhao.getPercentualConclusao());
		setEstadoOperacionalCarregamento(caminhao.getCarregEstadoOperacional());
		setDataUltimoRastreamento(caminhao.getAvldateTime());
		setLatitude(caminhao.getLatitude());
		setLongitude(caminhao.getLongitude());
		//setOrdemTransporte("OT");
		setIdProjeto(caminhao.getProjectId());
		if (caminhao.getCarregEstadoOperacional() != null && caminhao.getCarregEstadoOperacional() == EstadoOperacional.CARREG_CHEIO.ordinal()) {
			setSemApontamento(caminhao.getEventLoadId() == null);			
			setTemApontamentoInvalido(false);
		}
		
		setIdPrestador(Config.getInstance().buscarPrestadorPorId(caminhao.getCustomerChildId()).getId());
		
	}


	private void gruaFlorestal(GruaEntity grua) {
		setIdTipoImplemento(TipoImplemento.GRUA_FLORESTAL.ordinal());
		setEmComboio(null);
		setEmDesvio(null);
		setEmExcesso(null);
		setEstadoOperacionalCarregamento(null);
		setEstadoOperacionalTransporte(null);
		setVelocidadeMedia(null);
		
		setEstadoOperacional(grua.getProprioEstadoOperacional());
		setDataUltimoApontamento(grua.getEventdateTime());
		
		setDataUltimoRastreamento(grua.getAvldateTime());
		setLatitude(grua.getLatitude());
		setLongitude(grua.getLongitue());
		setIdProjeto(grua.getIdLocal());
		setIdPrestador(Config.getInstance().buscarPrestadorPorId(grua.getCustomerChildId()).getId());
		
		setDataUltimoCarregamento(grua.getCarregDate());
		setProjetoUltimoCarregamento(grua.getProject());
		setTalhaoUltimoCarregamento(grua.getField());
		setPlacaUltimoCarregamento(grua.getPlates());
		setTemApontamentoInvalido(false);
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

	public Integer getIdEstadoOperacional() {
		return idEstadoOperacional;
	}

	public void setIdEstadoOperacional(Integer idEstadoOperacional) {
		this.idEstadoOperacional = idEstadoOperacional;
	}

	public Integer getIdTipoImplemento() {
		return idTipoImplemento;
	}

	public void setIdTipoImplemento(Integer idTipoImplemento) {
		this.idTipoImplemento = idTipoImplemento;
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

	public Long getIdGruaFlorestal() {
		return idGruaFlorestal;
	}

	public void setIdGruaFlorestal(Long idGruaFlorestal) {
		this.idGruaFlorestal = idGruaFlorestal;
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

	public void setSemApontamento(Boolean semApontamento) {
		this.semApontamento = semApontamento;
	}

	public Boolean getTemApontamentoInvalido() {
		return temApontamentoInvalido;
	}

	public void setTemApontamentoInvalido(Boolean temApontamentoInvalido) {
		this.temApontamentoInvalido = temApontamentoInvalido;
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


	public Long getIdProjeto() {
		return idProjeto;
	}


	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}


	public String getOrdemTransporte() {
		return ordemTransporte;
	}


	public void setOrdemTransporte(String ordemTransporte) {
		this.ordemTransporte = ordemTransporte;
	}


}

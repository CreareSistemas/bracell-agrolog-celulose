package br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Acompanhamento {

	@XmlElement(required = true)
	private Long idProjeto;
	
	@XmlElement(required = true)
	private String projeto;
	
	@XmlElement(required = true)
    private Long idPrestador;
    
	@XmlElement(required = true)
    private String prestador;
    
	@XmlElement(required = true)
    private Integer cicloPrevisto = 0;
    
	@XmlElement(required = true)
    private Integer cicloRealizado = 0;
    
	@XmlElement(required = true)
    private Integer volumePrevisto = 0;
    
	@XmlElement(required = true)
    private Integer volumeRealizado = 0;
    
    private Integer volumeConcluido = 0;

	@XmlElement(required = true)
    private Integer viagemPrevisto = 0;
    
	@XmlElement(required = true)
    private Integer viagemRealizado = 0;
    
    private Integer viagemConcluido = 0;

	@XmlElement(required = true)
    private Integer volumeCaminhaoPrevisto = 0;
    
	@XmlElement(required = true)
    private Integer volumeCaminhaoRealizado = 0;
    
	@XmlElement(required = true)
    private Integer pesoMadeira = 0;
    
	@XmlElement(required = true)
    private Integer projecaoFinalVolume = 0;
    
	@XmlElement(required = true)
    private Integer projecaoFinalViagem = 0;
    
	@XmlElement(required = true)
    private Integer projecaoFinalGrauAtendimento = 0;
	
	@XmlTransient
	private Boolean ajustado = false; 
	
	
	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

	public Long getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Long idPrestador) {
		this.idPrestador = idPrestador;
	}

	public String getPrestador() {
		return prestador;
	}

	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}

	public Integer getCicloPrevisto() {
		return cicloPrevisto;
	}

	public void setCicloPrevisto(Integer cicloPrevisto) {
		this.cicloPrevisto = cicloPrevisto;
	}

	public Integer getCicloRealizado() {
		return cicloRealizado;
	}

	public void setCicloRealizado(Integer cicloRealizado) {
		this.cicloRealizado = cicloRealizado;
	}

	public Integer getVolumePrevisto() {
		return volumePrevisto;
	}

	public void setVolumePrevisto(Integer volumePrevisto) {
		this.volumePrevisto = volumePrevisto;
	}

	public Integer getVolumeRealizado() {
		return volumeRealizado;
	}

	public void setVolumeRealizado(Integer volumeRealizado) {
		this.volumeRealizado = volumeRealizado;
	}

	public Integer getVolumeConcluido() {
		return volumeConcluido;
	}

	public void setVolumeConcluido(Integer volumeConcluido) {
		this.volumeConcluido = volumeConcluido;
	}

	public Integer getViagemPrevisto() {
		return viagemPrevisto;
	}

	public void setViagemPrevisto(Integer viagemPrevisto) {
		this.viagemPrevisto = viagemPrevisto;
	}

	public Integer getViagemRealizado() {
		return viagemRealizado;
	}

	public void setViagemRealizado(Integer viagemRealizado) {
		this.viagemRealizado = viagemRealizado;
	}

	public Integer getViagemConcluido() {
		return viagemConcluido;
	}

	public void setViagemConcluido(Integer viagemConcluido) {
		this.viagemConcluido = viagemConcluido;
	}

	public Integer getVolumeCaminhaoPrevisto() {
		return volumeCaminhaoPrevisto;
	}

	public void setVolumeCaminhaoPrevisto(Integer volumeCaminhaoPrevisto) {
		this.volumeCaminhaoPrevisto = volumeCaminhaoPrevisto;
	}

	public Integer getVolumeCaminhaoRealizado() {
		return volumeCaminhaoRealizado;
	}

	public void setVolumeCaminhaoRealizado(Integer volumeCaminhaoRealizado) {
		this.volumeCaminhaoRealizado = volumeCaminhaoRealizado;
	}

	public Integer getPesoMadeira() {
		return pesoMadeira;
	}

	public void setPesoMadeira(Integer pesoMadeira) {
		this.pesoMadeira = pesoMadeira;
	}

	public Integer getProjecaoFinalVolume() {
		return projecaoFinalVolume;
	}

	public void setProjecaoFinalVolume(Integer projecaoFinalVolume) {
		this.projecaoFinalVolume = projecaoFinalVolume;
	}

	public Integer getProjecaoFinalViagem() {
		return projecaoFinalViagem;
	}

	public void setProjecaoFinalViagem(Integer projecaoFinalViagem) {
		this.projecaoFinalViagem = projecaoFinalViagem;
	}

	public Integer getProjecaoFinalGrauAtendimento() {
		return projecaoFinalGrauAtendimento;
	}

	public void setProjecaoFinalGrauAtendimento(Integer projecaoFinalGrauAtendimento) {
		this.projecaoFinalGrauAtendimento = projecaoFinalGrauAtendimento;
	}

	//-----------

	public void somaCicloPrevisto(Integer cicloPrevisto) {
		this.cicloPrevisto += cicloPrevisto;
	}

	public void somaCicloRealizado(Integer cicloRealizado) {
		this.cicloRealizado += cicloRealizado;
	}

	public void somaVolumePrevisto(Integer volumePrevisto) {
		this.volumePrevisto += volumePrevisto;
	}

	public void somaVolumeRealizado(Integer volumeRealizado) {
		this.volumeRealizado += volumeRealizado;
	}

	public void somaVolumeConcluido(Integer volumeConcluido) {
		this.volumeConcluido += volumeConcluido;
	}

	public void somaViagemPrevisto(Integer viagemPrevisto) {
		this.viagemPrevisto += viagemPrevisto;
	}

	public void somaViagemRealizado(Integer viagemRealizado) {
		this.viagemRealizado += viagemRealizado;
	}

	public void somaViagemConcluido(Integer viagemConcluido) {
		this.viagemConcluido += viagemConcluido;
	}
	
	public void somaVolumeCaminhaoPrevisto(Integer volumeCaminhaoPrevisto) {
		this.volumeCaminhaoPrevisto += volumeCaminhaoPrevisto;
	}

	public void somaVolumeCaminhaoRealizado(Integer volumeCaminhaoRealizado) {
		this.volumeCaminhaoRealizado += volumeCaminhaoRealizado;
	}

	public void somaPesoMadeira(Integer pesoMadeira) {
		this.pesoMadeira += pesoMadeira;
	}

	public void somaProjecaoFinalVolume(Integer projecaoFinalVolume) {
		this.projecaoFinalVolume += projecaoFinalVolume;
	}

	public void somaProjecaoFinalViagem(Integer projecaoFinalViagem) {
		this.projecaoFinalViagem += projecaoFinalViagem;
	}

	public void somaProjecaoFinalGrauAtendimento(Integer projecaoFinalGrauAtendimento) {
		this.projecaoFinalGrauAtendimento += projecaoFinalGrauAtendimento;
	}

	public Boolean getAjustado() {
		return ajustado;
	}

	public void setAjustado(Boolean ajustado) {
		this.ajustado = ajustado;
	}

	
}

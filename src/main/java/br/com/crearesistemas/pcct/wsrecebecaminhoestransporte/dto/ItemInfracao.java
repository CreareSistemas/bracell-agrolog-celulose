package br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ItemInfracao {
	@XmlElement(required = true)
	private Long id;
	
	@XmlElement(required = true)
	private Date dataInicio;
	
	private Integer tipoInfracao;
	
	private Float limite;

	private Float excesso;
	
	private Long idCaminhao; 
	
	private Float latitude;

	private Float longitude;
	
	private String ordemTransporte;
	
	private Integer duracaoEmSegundos; // fim - inicio

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Integer getTipoInfracao() {
		return tipoInfracao;
	}

	public void setTipoInfracao(Integer tipoInfracao) {
		this.tipoInfracao = tipoInfracao;
	}

	public Float getLimite() {
		return limite;
	}

	public void setLimite(Float limite) {
		this.limite = limite;
	}

	public Float getExcesso() {
		return excesso;
	}

	public void setExcesso(Float excesso) {
		this.excesso = excesso;
	}

	public Long getIdCaminhao() {
		return idCaminhao;
	}

	public void setIdCaminhao(Long idCaminhao) {
		this.idCaminhao = idCaminhao;
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

	public Integer getDuracaoEmSegundos() {
		return duracaoEmSegundos;
	}

	public void setDuracaoEmSegundos(Integer duracaoEmSegundos) {
		this.duracaoEmSegundos = duracaoEmSegundos;
	}

	
}

package br.com.crearesistemas.pcct.wsrecebeprojetos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Projeto {

	@XmlElement(required=true)
	private Long id;
	
	@XmlElement(required=true)
	private String identificacao;
	
	private Float percentualAtendimento;

	private Float volumePrevisto;

	private Float volumeRealizado;
	
	private Float horasPrevistasCiclo;

	private Float mediaHorasRealizadasCiclo;

	private Integer viagensPrevistas;
	
	private Integer viagensRealizadas;
	
	@XmlTransient
	private List<LocalDTO> locaisDeDescarga = new ArrayList<LocalDTO>();
		
	public Projeto() {
		super();
	}

	public Projeto(Long id, String identificacao) {
		super();
		this.id = id;
		this.identificacao = identificacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projeto other = (Projeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public Float getPercentualAtendimento() {
		return percentualAtendimento;
	}

	public void setPercentualAtendimento(Float percentualAtendimento) {
		this.percentualAtendimento = percentualAtendimento;
	}

	public Float getVolumePrevisto() {
		return volumePrevisto;
	}

	public void setVolumePrevisto(Float volumePrevisto) {
		this.volumePrevisto = volumePrevisto;
	}

	public Float getVolumeRealizado() {
		return volumeRealizado;
	}

	public void setVolumeRealizado(Float volumeRealizado) {
		this.volumeRealizado = volumeRealizado;
	}

	public Float getHorasPrevistasCiclo() {
		return horasPrevistasCiclo;
	}

	public void setHorasPrevistasCiclo(Float horasPrevistasCiclo) {
		this.horasPrevistasCiclo = horasPrevistasCiclo;
	}

	public Float getMediaHorasRealizadasCiclo() {
		return mediaHorasRealizadasCiclo;
	}

	public void setMediaHorasRealizadasCiclo(Float mediaHorasRealizadasCiclo) {
		this.mediaHorasRealizadasCiclo = mediaHorasRealizadasCiclo;
	}

	public Integer getViagensPrevistas() {
		return viagensPrevistas;
	}

	public void setViagensPrevistas(Integer viagensPrevistas) {
		this.viagensPrevistas = viagensPrevistas;
	}

	public Integer getViagensRealizadas() {
		return viagensRealizadas;
	}

	public void setViagensRealizadas(Integer viagensRealizadas) {
		this.viagensRealizadas = viagensRealizadas;
	}
	
	public List<LocalDTO> getLocaisDeDescarga() {
		return locaisDeDescarga;
	}

	public void setLocaisDeDescarga(List<LocalDTO> locaisDeDescarga) {
		this.locaisDeDescarga = locaisDeDescarga;
	}

	public boolean locaisDeDescargaContains(LocalDTO localDto) {		
		List<LocalDTO> locais = this.getLocaisDeDescarga();
		if (locais != null)
			for (LocalDTO local : locais) {
				if (local.getIdLocal() == localDto.getIdLocal()) return true;
			}
		return false;
	}

	public boolean locaisDeDescargaContains(Long localDeDescargaId) {
		List<LocalDTO> locais = this.getLocaisDeDescarga();
		if (locais != null)
			for (LocalDTO local : locais) {
				if (local.getIdLocal() == localDeDescargaId) return true;
			}
		return false;
	}

	public boolean locaisDeDescargaContainsIntegracao(String integracao) {
		List<LocalDTO> locais = this.getLocaisDeDescarga();
		if (locais != null)
			for (LocalDTO local : locais) {
				if (local.getIntegracao() != null  && local.getIntegracao().indexOf(integracao) > -1) return true;
			}
		return false;
	}
	
}

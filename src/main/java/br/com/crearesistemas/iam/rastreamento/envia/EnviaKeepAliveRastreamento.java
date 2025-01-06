package br.com.crearesistemas.iam.rastreamento.envia;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * @author eninomia
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EnviaKeepAliveRastreamento{
	
	@XmlElement(required=true)
	private String idDispositivo;
	
	@XmlElement(required=true)
	private Date data;
	
	@XmlElement(required=true)
	private Float latitude;

	@XmlElement(required=true)
	private Float longitude;

	private Integer ignicao;
	
	private Integer velocidade;
	
	private Integer rpm;
	
	private Long hodometro;
	
	private Long horimetro;
	
	private Integer canal;
	
	private Double anguloDirecao;

	// estado operacional de carregamento
	private Integer estadoOperacional1;

	public String getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(String idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public Integer getIgnicao() {
		return ignicao;
	}

	public void setIgnicao(Integer ignicao) {
		this.ignicao = ignicao;
	}

	public Integer getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(Integer velocidade) {
		this.velocidade = velocidade;
	}

	public Integer getRpm() {
		return rpm;
	}

	public void setRpm(Integer rpm) {
		this.rpm = rpm;
	}

	public Long getHodometro() {
		return hodometro;
	}

	public void setHodometro(Long hodometro) {
		this.hodometro = hodometro;
	}

	public Long getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(Long horimetro) {
		this.horimetro = horimetro;
	}

	public Integer getCanal() {
		return canal;
	}

	public void setCanal(Integer canal) {
		this.canal = canal;
	}

	public Double getAnguloDirecao() {
		return anguloDirecao;
	}

	public void setAnguloDirecao(Double anguloDirecao) {
		this.anguloDirecao = anguloDirecao;
	}

	// estado operacional de carregamento 15-cheio, 14-vazio
	public Integer getEstadoOperacional1() {
		return estadoOperacional1;
	}

	public void setEstadoOperacional1(Integer estadoOperacional1) {
		this.estadoOperacional1 = estadoOperacional1;
	}

}

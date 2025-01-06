package br.com.crearesistemas.model.agrolog;

import java.util.Date;

public class Telemetria  {

	private Long id;
	
	private Integer numVersao;
	
	private Date data;

	private Integer ignicao;
	
	private Integer velocidade;
	
	private Integer rpm;
	
	private Long hodometro;
	
	private Long horimetro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumVersao() {
		return numVersao;
	}

	public void setNumVersao(Integer numVersao) {
		this.numVersao = numVersao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	@Override
	public String toString() {
		return String
				.format("Telemetria [id=%s, data=%s, ignicao=%s, velocidade=%s, rpm=%s, hodometro=%s, horimetro=%s, rastreamento=%s]",
						id, data, ignicao, velocidade, rpm, hodometro,
						horimetro);
	}

}

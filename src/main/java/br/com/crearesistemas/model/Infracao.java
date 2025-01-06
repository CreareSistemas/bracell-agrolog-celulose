package br.com.crearesistemas.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Infracao {

	private Long integracao;
	private Long idDispositivo;
	private String tipoInfracao;
	private float limite;
	private float excesso;
	private float tempoExcesso;
	private Infracao.EventoInfracao evento;
	private Infracao.Rastreamento rastreamento;

	public Long getIntegracao() {
		return integracao;
	}

	public void setIntegracao(Long integracao) {
		this.integracao = integracao;
	}

	public Long getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public String getTipoInfracao() {
		return tipoInfracao;
	}

	public void setTipoInfracao(String tipoInfracao) {
		this.tipoInfracao = tipoInfracao;
	}

	public float getLimite() {
		return limite;
	}

	public void setLimite(float limite) {
		this.limite = limite;
	}

	public float getExcesso() {
		return excesso;
	}

	public void setExcesso(float excesso) {
		this.excesso = excesso;
	}

	public float getTempoExcesso() {
		return tempoExcesso;
	}

	public void setTempoExcesso(float tempoExcesso) {
		this.tempoExcesso = tempoExcesso;
	}

	public void setEvento(Infracao.EventoInfracao evento) {
		this.evento = evento;
	}

	public Infracao.EventoInfracao getEvento() {
		return evento;
	}

	public Infracao.Rastreamento getRastreamento() {
		return rastreamento;
	}

	public void setRastreamento(Infracao.Rastreamento rastreamento) {
		this.rastreamento = rastreamento;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Rastreamento {
		private Date data;
		private float latitude;
		private float longitude;
		private int ignicao;
		private int velocidade;
		private int rotacao;
		private long hodometro;
		private long horimetro;

		public Date getData() {
			return data;
		}

		public void setData(Date data) {
			this.data = data;
		}

		public float getLatitude() {
			return latitude;
		}

		public void setLatitude(float latitude) {
			this.latitude = latitude;
		}

		public float getLongitude() {
			return longitude;
		}

		public void setLongitude(float longitude) {
			this.longitude = longitude;
		}

		public int getIgnicao() {
			return ignicao;
		}

		public void setIgnicao(int ignicao) {
			this.ignicao = ignicao;
		}

		public int getVelocidade() {
			return velocidade;
		}

		public void setVelocidade(int velocidade) {
			this.velocidade = velocidade;
		}

		public int getRotacao() {
			return rotacao;
		}

		public void setRotacao(int rotacao) {
			this.rotacao = rotacao;
		}

		public long getHodometro() {
			return hodometro;
		}

		public void setHodometro(long hodometro) {
			this.hodometro = hodometro;
		}

		public Long getHorimetro() {
			return horimetro;
		}

		public void setHorimetro(long horimetro) {
			this.horimetro = horimetro;
		}

	}

	public static enum EventoInfracao {
		INICIO, FIM
	}

}

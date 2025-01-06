package br.com.crearesistemas.model.agrolog;



public class Dispositivo {

	private Long id;

	private Integer numVersao;
	
	private String identificador;
	
	private Integer tipoDispositivo;
	
	private Long equipamento_id;

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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Integer getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(Integer tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}

	public Long getEquipamento_id() {
		return equipamento_id;
	}

	public void setEquipamento_id(Long equipamento_id) {
		this.equipamento_id = equipamento_id;
	}
	
}

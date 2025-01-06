package br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class AgendamentoDTO {
	// /////////////////////////////////////////////////////////////////////////////////////////////

	public static final class Opcao {
		private boolean fila = false;
		private boolean mesa = false;
		private boolean quadra = false;
		private boolean barcaca = false;
		
		public boolean isFila() {
			return fila;
		}
		public void setFila(boolean fila) {
			this.fila = fila;
		}
		public boolean isMesa() {
			return mesa;
		}
		public void setMesa(boolean mesa) {
			this.mesa = mesa;
		}
		public boolean isQuadra() {
			return quadra;
		}
		public void setQuadra(boolean quadra) {
			this.quadra = quadra;
		}
		public boolean isBarcaca() {
			return barcaca;
		}
		public void setBarcaca(boolean barcaca) {
			this.barcaca = barcaca;
		}
		
	}
	// /////////////////////////////////////////////////////////////////////////////////////////////

	public static final class Origem {
		private boolean fila = false;
		private boolean mesa = false;
		private boolean quadra = false;
		private boolean barcaca = false;
		// informação do estagio de destino
		private Integer idEstadoOperacionalSI;
		
		// informação do local de destino
		private Long idLocal;
		
		// identificacao do local de destino
		private String identificacao;
		
		public boolean isFila() {
			return fila;
		}
		public void setFila(boolean fila) {
			this.fila = fila;
		}
		public boolean isMesa() {
			return mesa;
		}
		public void setMesa(boolean mesa) {
			this.mesa = mesa;
		}
		public boolean isQuadra() {
			return quadra;
		}
		public void setQuadra(boolean quadra) {
			this.quadra = quadra;
		}
		public Integer getIdEstadoOperacionalSI() {
			return idEstadoOperacionalSI;
		}
		public void setIdEstadoOperacionalSI(Integer idEstadoOperacionalSI) {
			this.idEstadoOperacionalSI = idEstadoOperacionalSI;
		}
		public Long getIdLocal() {
			return idLocal;
		}
		public void setIdLocal(Long idLocal) {
			this.idLocal = idLocal;
		}
		public String getIdentificacao() {
			return identificacao;
		}
		public void setIdentificacao(String identificacao) {
			this.identificacao = identificacao;
		}
		public boolean isBarcaca() {
			return barcaca;
		}
		public void setBarcaca(boolean barcaca) {
			this.barcaca = barcaca;
		}
		
	}
	// /////////////////////////////////////////////////////////////////////////////////////////////

	private boolean visivel = false;
	
	private boolean enabled = false;
	
	private Opcao opcao = new Opcao();
	
	private Origem origem = new Origem();
	
	// informação do estagio de destino
	private Integer idEstadoOperacionalSI;
	
	// informação do local de destino
	private Long idLocal;
	
	// identificacao do local de destino
	private String identificacao;
	

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public AgendamentoDTO mock() {
		
		setVisivel(false);
		
		setEnabled(false);
		
		setOpcao(new Opcao());
		
		return this;
		
	}

	public void setOpcao(Opcao opcao) {
		this.opcao = opcao;
		
	}
	

	public Opcao getOpcao() {
		return this.opcao;
		
	}
	
	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Integer getIdEstadoOperacionalSI() {
		return idEstadoOperacionalSI;
	}

	public void setIdEstadoOperacionalSI(Integer idEstadoOperacionalSI) {
		this.idEstadoOperacionalSI = idEstadoOperacionalSI;
	}

	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	
	
	
	
}

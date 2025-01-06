package br.com.crearesistemas.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum EstadoOperacional {	
	// Fluxo Operacional: PROPRIO
	DISPONIVEL("DisponÃ­vel", FluxoOperacional.PROPRIO),
	CARREGAMENTO("Carregamento", FluxoOperacional.PROPRIO),
	MANUTENCAO_ABASTECIMENTO("Abastecimento", FluxoOperacional.PROPRIO),
	MANUTENCAO_ELETRICA("ManutenÃ§Ã£o ElÃ©trica", FluxoOperacional.PROPRIO),
	MANUTENCAO_HIDRAULICA("ManutenÃ§Ã£o HidrÃ¡ulica", FluxoOperacional.PROPRIO),
	MANUTENCAO_MECANICA("ManutenÃ§Ã£o MecÃ¢nica", FluxoOperacional.PROPRIO),
	INTERVALO_REFEICAO("RefeiÃ§Ã£o", FluxoOperacional.PROPRIO),
	INTERVALO_TREINAMENTO("Treinamento", FluxoOperacional.PROPRIO),
	INTERVALO_OUTROS("Outros", FluxoOperacional.PROPRIO),
	DESLOCAMENTO_INTERNO("Deslocamento Interno", FluxoOperacional.PROPRIO),
	DESLOCAMENTO_PRANCHA("Deslocamento Prancha", FluxoOperacional.PROPRIO),
	RESERVA("Reserva", FluxoOperacional.PROPRIO),
	DESIGNADO("Designado", FluxoOperacional.PROPRIO),
	FILA("Fila", FluxoOperacional.PROPRIO), // 13
	
	// Fluxo Operacional: CARREGAMENTO
	CARREG_VAZIO("Vazio", FluxoOperacional.CARREGAMENTO),
	CARREG_CHEIO("Cheio", FluxoOperacional.CARREGAMENTO), // 15

	// Fluxo Operacional: TRANSPORTE
	TRANSP_FABRICA("F\u00E1brica", FluxoOperacional.TRANSPORTE),// 16
	TRANSP_VIAGEM("Viagem", FluxoOperacional.TRANSPORTE),// 17
	TRANSP_FILA_EM_CAMPO("Fila em Campo", FluxoOperacional.TRANSPORTE),// 18
	TRANSP_CARREGAMENTO("Em Carregamento", FluxoOperacional.TRANSPORTE), // 19

	// Fluxo Operacional: SITE_INDUSTRIAL
	SI_VIAGEM("Viagem", FluxoOperacional.SITE_INDUSTRIAL), //20
	SI_SENTIDO_FABRICA("Sentido Fábrica (Cerca/Cheio)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.ENTRADA, 1),//21
	SI_ACESSO_ENTRADA("Acesso Entrada", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.ENTRADA, 2), // 22
	SI_FILA_BALANCA_ENTRADA("Fila Balança (Apontamento)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.ENTRADA, 3), // 23
	SI_BALANCA_ENTRADAB("Balança Entrada (Int39)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.ENTRADA, 4), // 24
	SI_FILA_EXTERNA("Fila Campo", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 5), // 25 (remover)
	SI_FILA_INTERNA("Fila Interna", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 0), // 26 sequencia 0 -> desativado
	SI_QUADRAS("Quadras", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 2, 14, true), // 27 (quadras)
	SI_MESAS("Mesas", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 2, 8, true), // 28
	SI_STANDBY("Equipamentos StandBy", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 6, 13), //29 patio
	SI_BALANCA_SAIDA("Balança de Saída (Int40)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.SAIDA, 11), // 30 balança de saida
	SI_VARRICAO("Varrição (Apontamento)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.SAIDA, 10), //31
	SI_CHECKPOINT_SAIDA("Checkpoint Saída (cerca/vazio)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.SAIDA,9), // 32

	TRANSP_SEM_OT("Sem OT", FluxoOperacional.TRANSPORTE), // 33
	
	// Fluxo Operacional: SITE_INDUSTRIAL (continuacao)	
	SI_FILA("Fila Fabrica (Int35)", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 4, 6, true), // 34 -> 
	SI_ESPERA("Espera", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.NEUTRO, 1, 7, true),// 35 (remover)
	SI_FILA_BALANCA_SAIDA("Fila Balança de Saída", FluxoOperacional.SITE_INDUSTRIAL, SentidoLocal.SAIDA, 12),// 36
	SI_PORTO("Porto", FluxoOperacional.SITE_INDUSTRIAL),	 // ativo esta no outro porto maritimo pelotas
	
	// Fluxo Operacional: TRANSPORTE
	TRANSP_PORTO("Porto", FluxoOperacional.TRANSPORTE), //38
		
	// Fluxo Operacional: PORTO_MARITIMO
	PM_VIAGEM("Viagem", FluxoOperacional.PORTO_MARITIMO), // 39
	PM_FABRICA("Fabrica", FluxoOperacional.PORTO_MARITIMO),
	PM_ENTRADA("Entrada", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.ENTRADA, 1),
	PM_BALANCA_ENTRADA("Balan\u00E7a Entrada", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.ENTRADA, 2),
	PM_FILAS("Fila da Barca\u00E7a", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.ENTRADA, 4, 3),
	PM_BARCACA("Barca\u00E7a", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.NEUTRO, 2, 4, true),
	PM_SAIDA("Sa\u00EDda", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.SAIDA, 5),
	PM_BALANCA_SAIDA("Balan\u00E7a de Sa\u00EDda", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.SAIDA, 6),
	PM_VARRICAO("Varri\u00E7\u00E3o", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.SAIDA, 7),
	PM_ATIVOS_DISPONIVEIS("Ativos Dispon\u00EDveis", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.NEUTRO, 3, 8),
	PM_QUADRAS("Quadras", FluxoOperacional.PORTO_MARITIMO, SentidoLocal.NEUTRO, 2, 9, true); 
	

	
	
	private String value;
	
	private FluxoOperacional fluxoOperacional;
	
	private SentidoLocal sentidoFabrica;

	private int tamanhoColuna = 1;

	private int sequencia = 0;
	
	private boolean sortable = false;

	private EstadoOperacional(String value, FluxoOperacional fluxo) {
		this.value = value;
		this.fluxoOperacional = fluxo;
	}

	
	private EstadoOperacional(String value, FluxoOperacional fluxo, SentidoLocal sentido, int sequencia) {
		this(value, fluxo);
		this.sentidoFabrica = sentido;
		this.sequencia = sequencia;
	}
	
	private EstadoOperacional(String value, FluxoOperacional fluxo, SentidoLocal sentido, int tamanho, int sequencia) {
		this(value, fluxo, sentido, sequencia);
		this.tamanhoColuna  = tamanho;
	}

	private EstadoOperacional(String value, FluxoOperacional fluxo, SentidoLocal sentido, int tamanho, int sequencia, boolean sortable) {
		this(value, fluxo, sentido, tamanho, sequencia);
		this.sortable  = sortable;		
	}
	
	

	
	
	
	@Override
	public String toString() {
		return value;
	}
	
	
	
	public SentidoLocal getSentidoFabrica() {
		return sentidoFabrica;
	}


	public void setSentidoFabrica(SentidoLocal sentidoFabrica) {
		this.sentidoFabrica = sentidoFabrica;
	}


	public FluxoOperacional getFluxoOperacional() {
		return fluxoOperacional;
	}

	public void setFluxoOperacional(FluxoOperacional fluxoOperacional) {
		this.fluxoOperacional = fluxoOperacional;
	}
	
	
	public int getTamanhoColuna() {
		return tamanhoColuna;
	}


	public void setTamanhoColuna(int tamanhoColuna) {
		this.tamanhoColuna = tamanhoColuna;
	}


	public int getSequencia() {
		return sequencia;
	}


	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}


	public boolean isSortable() {
		return sortable;
	}


	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}


	public List<EstadoOperacional> getByFluxoOperacional(FluxoOperacional fluxo)
	{
		List<EstadoOperacional> _ret = new ArrayList<EstadoOperacional>();
		
		for (EstadoOperacional estado : EstadoOperacional.values() )
		{
			if( estado.getFluxoOperacional().equals(fluxo) )
			{
				_ret.add(estado);
			}
		}
		return _ret;
	}

	// verifica se o local do estagio Ã© do porto 
	public static boolean isEstagioPorto(EstadoOperacional estadoOperacional) {
		return estadoOperacional!= null && (estadoOperacional.fluxoOperacional == FluxoOperacional.PORTO_MARITIMO);
	}
	
	// verifica se o local do estagio Ã© da fÃ¡brica
	public static boolean isEstagioFabrica(EstadoOperacional estadoOperacional) {
		return estadoOperacional!= null && (estadoOperacional.fluxoOperacional == FluxoOperacional.SITE_INDUSTRIAL);	
	}

	public static boolean isFluxoInternoDoPorto(EstadoOperacional estadoOperacional) {
		boolean _ret = false;
		if (estadoOperacional != null && (estadoOperacional.getFluxoOperacional() == FluxoOperacional.PORTO_MARITIMO)) {
			switch (estadoOperacional) {
			case PM_VIAGEM: 
			case PM_FABRICA:
			break;
			
			default:
				_ret = true;
				break;
		    }
		}
		
		return _ret;
	}


	public static boolean isEstagioMesaFabrica(	EstadoOperacional estadoOperacional) {
		boolean result = false;
		switch (estadoOperacional) {
		case SI_MESAS:
			result  = true;
			break;
		default:
			break;
		}		
		return result;
	}


	public static boolean isEstagioPortoBarcaca(EstadoOperacional estadoOperacional) {
		boolean result = false;
		switch (estadoOperacional) {
		case PM_BARCACA:
			result  = true;
			break;
		default:
			break;
		}		
		return result;
	}


	public static EstadoOperacional getCarregEntity(Integer codigo) {
		EstadoOperacional _ret = EstadoOperacional.CARREG_VAZIO;
		
		if (codigo != null && codigo == EstadoOperacional.CARREG_CHEIO.ordinal()) {
			_ret = EstadoOperacional.CARREG_CHEIO;
		} else {
			_ret = EstadoOperacional.CARREG_VAZIO;
		}
		
		return _ret;
	}

	public static EstadoOperacional getByCode(Integer code)
	{
		EstadoOperacional _ret = EstadoOperacional.DISPONIVEL;
		
		for (EstadoOperacional estado : EstadoOperacional.values() )
		{
			if( estado.ordinal() == code.intValue() )
			{
				_ret = estado;
			}
		}
		return _ret;
	}


	public static Boolean stayInEstado(Integer estado, EstadoOperacional... candidates) {
		if (estado != null) {
			for (EstadoOperacional candidate: candidates) {
				if (candidate.ordinal() == estado) {
					return true;
				}
			}	
		}
		return false;
	}


	public static Boolean stayNotInEstado(Integer estado, EstadoOperacional... candidates) {
		return !stayInEstado(estado, candidates);		
	}



}

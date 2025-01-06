package br.com.crearesistemas.config;

public enum EstadoOperacionalProprio {
	DESCONHECIDO(-1), 			DISPONIVEL(0), 				CARREGAMENTO(1), 		MANUTENCAO_ABASTECIMENTO(2),	MANUTENCAO_ELETRICA(3),
	MANUTENCAO_HIDRAULICA(4),	MANUTENCAO_MECANICA(5),		INTERVALO_REFEICAO(6),	INTERVALO_TREINAMENTO(7),		INTERVALO_OUTROS(8),
	DESLOCAMENTO_INTERNO(9),	DESLOCAMENTO_PRANCHA(10),	RESERVA(11),  			DESIGNADO(12),					FILA(13);
	private int codigo = 0;		
	private EstadoOperacionalProprio(int codigo){this.codigo = codigo;}
	public int getCodigo() {
		return codigo;
	};		
}
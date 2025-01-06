package br.com.crearesistemas.config;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;



public class Logistica {

	// tempo limite da coleta
	public static final long LIMITE_COLETA_EM_SEGUNDOS = 7200;
	// distancia minima para comboio
	public static final Double DISTANCIA_MINIMA_LINEAR_METROS = 60.0;
	
	//public static final Double DISTANCIA_MAXIMA_LINEAR_METROS = 300.0;
	public static final Double DISTANCIA_MAXIMA_LINEAR_METROS = 60.0;
	
	// tempo limite do apontamento valido
	public static final long LIMITE_APONTAMENTOVALIDO_EM_SEGUNDOS = 14400;
	// tempo limite para mostrar e pcct e depois para indisponeis
	public static final long LIMITE_DESLOCAMENTOPRANCHA_SEGUNDOS = 1800;
	// tempo limite para implemento permanecer no estagio
	public static final int LIMITE_TEMPOESTAGIO_EM_SEGUNDOS = 7200;
	// limite sem segundos, considerando o rastreamento aconteceu recentemente
	public static final int LIMITE_RASTREAMENTO_ISRECENTE_EMSEGUNDOS = 1500;
	// tempo limite tracking
	public static final long LIMITE_TRACKING_EM_SEGUNDOS = 600;
	// hora de fechamento programacao no SGF
	public static int HORA_INICIAL = 0;
	// duracao em horas do fechamento da programacao
	public static int DURACAO_EM_HORAS = 24;
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// tempo limite da coleta em que podemos considerar quase real-time
	private static final int LIMITE_RASTREAMENTO_REALTIME_EMSEGUNDOS = 3 * 60;
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// ESTAGIO FILA FABRICA
	// identificacao da fila G1
	private static final String INTEGRACAO_FILA_G1 = "L034-G1";
	// identificacao da fila G2
	private static final String INTEGRACAO_FILA_G2 = "L034-G2";
	// chave de integracao da fila da fila interna f\u00E1brica
	private static final String INTEGRACAO_FILAINTERNA_FABRICA 	= "L034-FilaInterna";

	// //////////////////////////////////////////////////////////////////////////////////////
	// ESTAGIO MESAS
	// chave integracao mesa l100
	private static final String INTEGRACAO_MESA_L100 			= "L028-L100";
	// chave integracao mesa l200
	private static final String INTEGRACAO_MESA_L200 			= "L028-L200";
	// chave integracao mesa l300
	private static final String INTEGRACAO_MESA_L300 			= "L028-L300";

	// //////////////////////////////////////////////////////////////////////////////////////
	// ESTAGIO PATIO
	// chave integracao fila transito vazio
	private static final String INTEGRACAO_TRANSITO_VAZIO 		= "L029-TrVazio";
	// chave integracao fila transito vazio
	private static final String INTEGRACAO_TRANSITO_CARREGADO 	= "L029-TrCarregado";
	// chave de integracao da fila da barca\u00E7a
	private static final String INTEGRACAO_FILA_BARCACA 		= "L029-FilaBarcaca";
	// chave integracao ativos disponiveis na fabrica
	private static final String INTEGRACAO_PATIO_ATIVOS_DISPONIVEIS = "L029-Ativos";

	// //////////////////////////////////////////////////////////////////////////////////////
	// ESTAGIO FILA PORTO
	// identificacao da fila interna do porto
	private static final String INTEGRACAO_FILAINTERNA_PORTO = "L043-FInterna";
	// indetificacao da fila da barcaca no porto
	private static final String INTEGRACAO_FILABARCACA_PORTO = "L043-FBarcaca";
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// ESTAGIO Quadra sem identificacao		
	private static final String INTEGRACAO_QUADRAXX_FABRICA 	= "L027-Q??";
	private static final String INTEGRACAO_QUADRAXX_PORTO 		= "L049-Q??";

	
	// //////////////////////////////////////////////////////////////////////////////////////
	// INTEGRACAO SGF
	//	15	Fábrica
	//	16	Porto de Pelotas	
	// chave identificacao da fabrica SGF
	public static final String INTEGRACAO_SGF_FABRICA 			= "15";
	// chave identificacao do porto SGF
	public static final String INTEGRACAO_SGF_PORTO 			= "16";
	public static final int LIMITE_EXCESSO_ALTURA_EM_SEGUNDOS = 3*60; // 3min
	
	
	// data truncada da vigencia
	public static Date getDataReferencia(Date referencia) {
		DateTime data = new DateTime(referencia);
		DateTime dia = new DateTime(DateUtils.truncate(data.toDate(), Calendar.DATE));
		if (data.getHourOfDay() > Logistica.HORA_INICIAL) {
			dia = dia.plusDays(1);
		}
		return dia.toDate();
	}
	
	// data inicio de vigencia - ontem as 18 hrs
	public static Date getDataInicio(Date referencia) {
		DateTime data = new DateTime(referencia);
		DateTime dia = new DateTime(DateUtils.truncate(data.toDate(), Calendar.DATE));
		if (data.getHourOfDay() < Logistica.HORA_INICIAL) {
			dia = dia.minusDays(1);
		}
		return dia.plusHours(Logistica.HORA_INICIAL).toDate();
	}
	
	// data fim de vigencia - hj 18 hras
	public static Date getDataFim(Date referencia) {
		return (new DateTime(getDataInicio(referencia))).plusHours(Logistica.DURACAO_EM_HORAS).toDate();
	}

	

	public static boolean posicaoInconsistente(float latitude, float longitude) {
		
		return !((latitude > -89 && latitude < 89)
				&& (longitude > -179 && longitude < 179)
				&& (latitude != 0 && longitude != 0 ));
	}

	public static Integer consisteVelocidade(int velocidade, Integer velocidadeAtual) {
		if ((velocidade >= 0) && (velocidade < 200)) return velocidade;
		else return velocidadeAtual;
	}

	public static String getIntegracaoFilaG1() {		
		return INTEGRACAO_FILA_G1;
	}
	
	public static String getIntegracaoFilaG2() {		
		return INTEGRACAO_FILA_G2;
	}
	
	public static String getIntegracaoFilaBarcaca() {
		return INTEGRACAO_FILA_BARCACA;
	}

	public static String getIntegracaoFilaInternaNoPorto() {		
		return INTEGRACAO_FILAINTERNA_PORTO;
	}
	
	public static String getIntegracaoFilaBarcacaNoPorto() {
		return INTEGRACAO_FILABARCACA_PORTO;
	}
	
	public static String getIntegracaoFilaInternaNaFabrica() {
		return INTEGRACAO_FILAINTERNA_FABRICA;
	}
	
	public static String getIntegracaoFabrica() {
		return INTEGRACAO_SGF_FABRICA;
	}
	
	public static String getIntegracaoPorto() {
		return INTEGRACAO_SGF_PORTO;
	}
	
	public static String getIntegracaoTransitoVazio() {
		return INTEGRACAO_TRANSITO_VAZIO;
	}
	
	public static String getIntegracaoTransitoCarregado() {
		return INTEGRACAO_TRANSITO_CARREGADO;
	}
	
	public static String getIntegracaoMesaL100() {
		return INTEGRACAO_MESA_L100;
	}
	public static String getIntegracaoMesaL200() {
		return INTEGRACAO_MESA_L200;
	}
	public static String getIntegracaoMesaL300() {
		return INTEGRACAO_MESA_L300;
	}
	
	public static String getIntegracaoAtivosDisponiveis() {		
		return INTEGRACAO_PATIO_ATIVOS_DISPONIVEIS;
	}
	
	public static String getIntegracaoQuadraXX() {	
		return INTEGRACAO_QUADRAXX_FABRICA;
	}
	
	public static String getIntegracaoQuadraXXNoPorto() {
		return INTEGRACAO_QUADRAXX_PORTO;
	}

	

}

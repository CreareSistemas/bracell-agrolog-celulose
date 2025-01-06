package br.com.crearesistemas.config;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.EstagioOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.EntidadeProprietaria;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto.Acompanhamento;
import br.com.crearesistemas.pcct.wsrecebecaminhoestransporte.dto.CaminhaoTransporte;
import br.com.crearesistemas.pcct.wsrecebeprevisoes.dto.Previsao;
import br.com.crearesistemas.util.NumberUtils;

public class Config {

	private static final Config instance = new Config();

	public static final Config getInstance() {
		return instance;
	}

	// se o arquivo properties estiver no mesmo pacote desta classe:
	// private final ResourceBundle rb =
	// ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".creare");

	// se o arquivo properties estiver na ra\u00a1z do classpath:
	// private ResourceBundle rb = ResourceBundle.getBundle("creare");
	private ResourceBundle rb = null; // original bracell

	public static final String PREFIXO_CREARE_DISPOSITIVO = "CREARE_DISPOSITIVO_"; // seguido do id do ve\u00EDculo
	public static final String PREFIXO_CREARE_EQUIPAMENTO = "CREARE_EQUIPAMENTO_"; // seguido do id do ve\u00EDculo

	private static final int CODIGO_APONTAMENTO = 6152;

	private HashMap<Long, String> mapProjetos = new HashMap<>();
	private HashMap<Long, String> mapProjetosCaminhaoTransp = null;
	private HashMap<Long, String> mapProjetosGruaFlorestal = null;

	@SuppressWarnings("unused")
	private Config() {
		if (false) {
			try {
				File file = new File(
						"D:\\PROJETOS\\bracell\\01-MICROSERVICES\\bracell-agrolog-celulose\\ws-bracell-panel-timber\\src\\main\\webapp\\WEB-INF\\classes");
				URL[] urls = { file.toURI().toURL() };
				ClassLoader loader = new URLClassLoader(urls);
				rb = ResourceBundle.getBundle("creare", Locale.getDefault(), loader);
			} catch (Exception e) {
			}
		} else {
			rb = ResourceBundle.getBundle("creare");
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	public String getRepositoryDriverClassName() {
		return getString("datasource.repository.driver-class-name");
	}

	public String getRepositoryUserName() {
		return getString("datasource.repository.username");
	}

	public String getRepositoryPassword() {
		return getString("datasource.repository.password");
	}

	public String getRepositoryUrl() {
		return getString("datasource.repository.url");
	}

	public Boolean getRepositoryEnable() {
		return NumberUtils.parseInt(getString("datasource.repository.enable")) > 0;

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	private final String getString(final String name) {
		return rb.getString(name);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	public final String getDataSourceName() {
		return getString("DATASOURCE_NAME");
	}

	public final String getDataSourceNameBracell() {
		return getString("DATASOURCE_NAME_BRACELL");
	}


	public final float getJanelaHoras() {
		return NumberUtils.parseFloat(getString("JANELA_HORAS"));
	}

	public final int getJanelaMaxRows() {
		return NumberUtils.parseInt(getString("JANELA_MAX_ROWS"));
	}

	public final float getJanelaHorasApontamento() {
		return NumberUtils.parseFloat(getString("JANELA_HORAS_APONTAMENTO"));
	}

	public int getJanelaMaxRowsApontamento() {
		return NumberUtils.parseInt(getString("JANELA_MAX_ROWS_APONTAMENTO"));
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	public String getDispositivoId(Long vehicleId) {
		return PREFIXO_CREARE_DISPOSITIVO + vehicleId;
	}

	public String getEquipamentoId(Long vehicleId) {
		return PREFIXO_CREARE_EQUIPAMENTO + vehicleId;
	}

	public long getVeiculoId(String dispositivoId) {
		return NumberUtils.parseLong(removePrefixos(dispositivoId));
	}

	public String removePrefixos(String dispositivoId) {
		return dispositivoId.replaceAll(PREFIXO_CREARE_DISPOSITIVO, "").replaceAll(PREFIXO_CREARE_EQUIPAMENTO, "");
	}

	public int getCodigoApontamento() {
		return CODIGO_APONTAMENTO;
	}

	public String getSgfUrlBase() {
		return getString("sgf-urlbase");
	}

	public String getSgfAppId() {
		return getString("sgf-appId");
	}

	public String getSgfSecret() {
		return getString("sgf-secret");
	}

	List<Previsao> previsoes;

	public void setPrevisoes(List<Previsao> previsoes) {
		this.previsoes = previsoes;
	}

	public List<Previsao> getPrevisoes() {
		return this.previsoes;
	}

	public Long getCustomerId() {
		return NumberUtils.parseLong(getString("CUSTOMERID"));
	}

	public Boolean isProduction() {
		return !getSgfUrlBase().contains("sgfqas");
	}

	// /////////////////////////////////////// ESTADOS
	// ///////////////////////////////////////////////////////
	//
	public EstadoOperacional getEstadoOperacionalById(Integer code) {
		EstadoOperacional _ret = null;
		if (code != null) {
			for (EstadoOperacional estado : EstadoOperacional.values()) {
				if (estado.ordinal() == code.intValue()) {
					_ret = estado;
				}
			}
		}

		return _ret;
	}

	public String getEstadoOperacionalByCodEvento(int codEvento) {
		String _ret = "DISPONIVEL";
		for (EstadoOperacionalProprio estado : EstadoOperacionalProprio.values()) {
			if (estado.getCodigo() == codEvento) {
				_ret = estado.toString();
			}
		}

		return _ret;
	}

	// /////////////////////////////////////// LOGGER
	// ///////////////////////////////////////////////////////
	//
	public boolean printLogSgf() {
		return true;
	}

	public boolean printLogDowntimeSgf() {
		return false;
	}

	
	// /////////////////////////////////////// PCCT CONFIG
	// ///////////////////////////////////////////////////////
	//
	
	// Option indicates whether the worker should confirm 
	// the destination in transport order during on queuing state  
	public boolean hasOptOrderOnQueueEnabled = false;
	
	
	// /////////////////////////////////////// PCSI CONFIG
	// ///////////////////////////////////////////////////////
	//
	public static final Integer LINE1_CODE = 1;
	public static final Integer LINE2_CODE = 2;
	public static final Integer LIMIT_RETRIES_DOWNTIME = 1;
	public static final Integer LIMIT_ORDER_SECONDS = 96 * 3600; // 1hrs = 3600

	public static final Integer CARREG_CHEIO = 1;
	public static final Integer CARREG_VAZIO = 0;
	public static double MAX_DISTANCE_TO_CRANE = 1000.0;
	
	public Boolean pcsiOnlyOrders() {
		return true;
	}

	public Integer getDefaultLineId() {
		return LINE2_CODE;
	}

	public int getLimitOrderInSeconds() {
		return LIMIT_ORDER_SECONDS;
	}

	public Integer getLimitRetriesDowntimes() {
		return LIMIT_RETRIES_DOWNTIME;
	}
	
	public double getMaxDistanceToCrane() {
		return MAX_DISTANCE_TO_CRANE;
	}

	// /////////////////////////////////////// CHIPPER CONFIG
	// ///////////////////////////////////////////////////////

	// fix position or app device?
	public boolean configCraneFix() {
		return false;
	}

	public EstagioOperacional getEstagioFilaByChipperCode(Long chipperCode) {
		EstagioOperacional res = EstagioOperacional.FILA_MESA1;
		if (chipperCode != null) {
			if (isProduction()) {
				if (chipperCode.intValue() == 3) {
					// mesa 1
					res = EstagioOperacional.FILA_MESA1;
				}

				if (chipperCode.intValue() == 4) {
					// mesa 2
					res = EstagioOperacional.FILA_MESA2;
				}

				if (chipperCode.intValue() == 5) {
					// mesa 3
					res = EstagioOperacional.FILA_MESA3;
				}

				if (chipperCode.intValue() == 6) {
					// mesa 3
					res = EstagioOperacional.FILA_MESA4;
				}
			}

			if (isProduction() == false) {
				if (chipperCode.intValue() == 3) {
					// mesa 1
					res = EstagioOperacional.FILA_MESA1;
				}
				if (chipperCode.intValue() == 4) {
					// mesa 2
					res = EstagioOperacional.FILA_MESA2;
				}

				if (chipperCode.intValue() == 5) {
					// mesa 3
					res = EstagioOperacional.FILA_MESA3;
				}

				if (chipperCode.intValue() == 6) {
					// mesa 3
					res = EstagioOperacional.FILA_MESA4;
				}
			}
		}

		return res;
	}

	public Boolean isMesa(String plates) {
		Boolean result = false;
		List<String> lista = new ArrayList<String>(Arrays.asList("31004", "31005", "31006", "31007"));

		for (String item : lista) {
			if (plates != null && !plates.trim().isEmpty() && plates.trim().contains(item)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Long getLocalIdMesaByCrane(String name) {
		if (name.contains("31004")) {
			return 1000l;
		}

		if (name.contains("31005")) {
			return 1001l;
		}

		if (name.contains("31006")) {
			return 1002l;
		}

		if (name.contains("31007")) {
			return 1008l;
		}
		return null;
	}

	public Boolean isMesaL1(String plates) {
		Boolean result = false;
		List<String> lista = new ArrayList<String>(Arrays.asList("5002", "5005", "5007", "658"));

		for (String item : lista) {
			if (plates != null && !plates.trim().isEmpty() && plates.trim().contains(item)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isGruaLinha1(GruaEntity grua) {
		Boolean result = false;
		List<String> lista = new ArrayList<String>(Arrays.asList("5002", "5005", "5007", "658"));

		String plates = null;

		if (grua != null) {
			plates = grua.getName();

			for (String item : lista) {
				if (plates != null && !plates.trim().isEmpty() && plates.trim().contains(item)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	public Long getLocalIdMesaByCraneL1(String name) {
		if (name.contains("5002")) {
			return 1000l;
		}

		if (name.contains("5005")) {
			return 1000l;
		}

		if (name.contains("5007")) {
			return 1000l;
		}

		if (name.contains("658")) {
			return 1000l;
		}
		return null;
	}

	public EstagioOperacional getEstagioMesaByLocalId(Long localId) {
		if (localId != null) {
			if (localId.intValue() == 1000) {
				return EstagioOperacional.MESA_L1;
			}
			if (localId.intValue() == 1001) {
				return EstagioOperacional.MESA_L2;
			}

			if (localId.intValue() == 1002) {
				return EstagioOperacional.MESA_L3;
			}

			if (localId.intValue() == 1008) {
				return EstagioOperacional.MESA_L4;
			}
		}

		return null;
	}

	public EstagioOperacional getEstagioQuadraByLocalId(Long localId) {
		if (localId != null) {
			if (localId.intValue() == 3000) {
				return EstagioOperacional.QUADRA001;
			}
			if (localId.intValue() == 3001) {
				return EstagioOperacional.QUADRA002;
			}

			if (localId.intValue() == 3002) {
				return EstagioOperacional.QUADRA003;
			}

			if (localId.intValue() == 3003) {
				return EstagioOperacional.QUADRA004;
			}

			if (localId.intValue() == 3004) {
				return EstagioOperacional.QUADRA005;
			}
			if (localId.intValue() == 3005) {
				return EstagioOperacional.QUADRA006;
			}
			if (localId.intValue() == 3006) {
				return EstagioOperacional.QUADRA007;
			}
			if (localId.intValue() == 3007) {
				return EstagioOperacional.QUADRA008;
			}
			if (localId.intValue() == 3008) {
				return EstagioOperacional.QUADRA009;
			}
			if (localId.intValue() == 3009) {
				return EstagioOperacional.QUADRA010;
			}
			if (localId.intValue() == 3010) {
				return EstagioOperacional.QUADRA011;
			}
			if (localId.intValue() == 3011) {
				return EstagioOperacional.QUADRA012;
			}
			if (localId.intValue() == 3012) {
				return EstagioOperacional.QUADRA013;
			}
			if (localId.intValue() == 3013) {
				return EstagioOperacional.QUADRA014;
			}
			if (localId.intValue() == 3014) {
				return EstagioOperacional.QUADRA015;
			}
			if (localId.intValue() == 3015) {
				return EstagioOperacional.QUADRA016;
			}
			if (localId.intValue() == 3016) {
				return EstagioOperacional.QUADRA017;
			}
			if (localId.intValue() == 3017) {
				return EstagioOperacional.QUADRA018;
			}
			if (localId.intValue() == 3018) {
				return EstagioOperacional.QUADRA019;
			}
			if (localId.intValue() == 3019) {
				return EstagioOperacional.QUADRA020;
			}
		}

		return null;
	}

	// verifica se deve mudar a mesa da grua
	public boolean isGruaChangeMesa(GruaEntity grua) {
		Boolean res = false;
		Long localIdFixConfig = getLocalIdMesaByCrane(grua.getName());

		if (localIdFixConfig != null) {
			if (grua.getIdLocal() == null || localIdFixConfig.intValue() != grua.getIdLocal().intValue()) {
				grua.setIdLocal(localIdFixConfig);
				res = true;
			}
		}
		return res;
	}

	// //////////////////////////////////////CRANE EVENTS
	// ////////////////////////////////////////////////////////

	List<Integer> gruaFlorestalDisponivel = Arrays.asList(3111, 3471);
	List<Integer> gruaFlorestalCarreg = Arrays.asList(11, 1011, 1012, 1014);

	List<Integer> gruaFlorestalManutAbast = Arrays.asList(2291, 2281);
	List<Integer> gruaFlorestalManutHidra = Arrays.asList();
	List<Integer> gruaFlorestalManutMec = Arrays.asList(2251, 2241, 2261, 2301, 2231, 2231, 2251, 2271, 2261, 2051);

	List<Integer> gruaFlorestalIntervaloRef = Arrays.asList(3151, 3151);
	List<Integer> gruaFlorestalIntervaloTreina = Arrays.asList();
	List<Integer> gruaFlorestalIntervaloOutros = Arrays.asList(3211, 3191, 3201,3461,991);

	List<Integer> gruaFlorestalDeslocInt = Arrays.asList(3421, 1001, 1002, 1004);
	List<Integer> gruaFlorestalDeslocPrancha = Arrays.asList(3441);

	List<Integer> gruaFlorestalReserva = Arrays.asList(3171, 3451, 3101);
	List<Integer> gruaFlorestalDesig = Arrays.asList();
	List<Integer> gruaFlorestalFila = Arrays.asList();

	List<Integer> gruaPatioCarreg = Arrays.asList(11, 1011, 1012, 1014);
	List<Integer> gruaPatioOutros = Arrays.asList(991);
	List<Integer> gruaPatioManutMec = Arrays.asList(2301, 2291, 2281, 2271, 2261, 2261, 2251, 2251, 2241, 2231, 2231,
			2114, 2112, 2111, 2104, 2102, 2101, 2094, 2092, 2091, 2084, 2082, 2081, 2074, 2072, 2071, 2064, 2062, 2061,
			2054, 2052, 2051, 2044, 2042, 2041, 2034, 2032, 2031, 2024, 2022, 2021, 2014, 2012, 2011, 2004, 2002, 2001);

	List<Integer> apontamentoCaminhaoFilaBalanca = Arrays.asList(3081, 3082, 3084);
	List<Integer> apontamentoCaminhaoVarricao = Arrays.asList(3121, 3122, 3124);
	List<Integer> apontamentoGruasPatioStandBy = Arrays.asList(2301, 2291, 2281, 2271, 2261, 2261, 2251, 2251, 2241,
			2231, 2231, 2114, 2112, 2111, 2104, 2102, 2101, 2094, 2092, 2091, 2084, 2082, 2081, 2074, 2072, 2071, 2064,
			2062, 2061, 2054, 2052, 2051, 2044, 2042, 2041, 2034, 2032, 2031, 2024, 2022, 2021, 2014, 2012, 2011, 2004,
			2002, 2001, 991);

	public Boolean isGruaPatioCarregamento(Long eventCode) {
		if (eventCode != null) {
			if (hasInArray(eventCode, gruaPatioCarreg)) {
				return true;
			}
		}
		return false;
	}

	private List<CaminhaoTransporte> listCaminhaoTransp = null;

	private List<Acompanhamento> listAcompanhamentos = null;

	public EstadoOperacional eventoEstadoMap(TipoImplemento tipo, Long eventoCodigo) {
		switch (tipo) {
		case GRUA_FLORESTAL:
		case GRUA_FIXA:
		case GRUA_MOVEL:
			if (hasInArray(eventoCodigo, gruaFlorestalDisponivel))
				return EstadoOperacional.DISPONIVEL;
			if (hasInArray(eventoCodigo, gruaFlorestalCarreg))
				return EstadoOperacional.CARREGAMENTO;
			if (hasInArray(eventoCodigo, gruaFlorestalManutAbast))
				return EstadoOperacional.MANUTENCAO_ABASTECIMENTO;
			if (hasInArray(eventoCodigo, gruaFlorestalManutHidra))
				return EstadoOperacional.MANUTENCAO_HIDRAULICA;
			if (hasInArray(eventoCodigo, gruaFlorestalManutMec))
				return EstadoOperacional.MANUTENCAO_MECANICA;
			if (hasInArray(eventoCodigo, gruaFlorestalIntervaloRef))
				return EstadoOperacional.INTERVALO_REFEICAO;
			if (hasInArray(eventoCodigo, gruaFlorestalIntervaloTreina))
				return EstadoOperacional.INTERVALO_TREINAMENTO;
			if (hasInArray(eventoCodigo, gruaFlorestalIntervaloOutros))
				return EstadoOperacional.INTERVALO_OUTROS;
			if (hasInArray(eventoCodigo, gruaFlorestalDeslocInt))
				return EstadoOperacional.DESLOCAMENTO_INTERNO;
			if (hasInArray(eventoCodigo, gruaFlorestalDeslocPrancha))
				return EstadoOperacional.DESLOCAMENTO_PRANCHA;
			if (hasInArray(eventoCodigo, gruaFlorestalReserva))
				return EstadoOperacional.RESERVA;
			if (hasInArray(eventoCodigo, gruaFlorestalDesig))
				return EstadoOperacional.DESIGNADO;
			if (hasInArray(eventoCodigo, gruaFlorestalFila))
				return EstadoOperacional.FILA;
			break;

		default:
			break;
		}

		return EstadoOperacional.DISPONIVEL;
	}

	private Boolean hasInArray(Long item, List<Integer> lista) {
		if (item != null) {
			for (Integer elemento : lista) {
				if (elemento.intValue() == item.intValue()) {
					return true;
				}
			}
		}
		return false;
	}

	// ///////////////////////// MAP Control ////////////////////////////////////
	public void setProjetos(HashMap<Long, String> mapProjetos) {
		this.mapProjetos = mapProjetos;
	}

	public HashMap<Long, String> getMapProjetos() {
		return mapProjetos;
	}

	public void setMapProjetos(HashMap<Long, String> mapProjetos) {
		this.mapProjetos = mapProjetos;
	}

	public void setMapProjetosCaminhaoTransp(HashMap<Long, String> mapProjetosCaminhaoTransp) {
		this.mapProjetosCaminhaoTransp = mapProjetosCaminhaoTransp;
	}

	public HashMap<Long, String> getMapProjetosCaminhaoTransp() {
		return this.mapProjetosCaminhaoTransp;
	}

	public void setMapProjetosGruaFlorestal(HashMap<Long, String> mapProjetosGruaFlorestal) {
		this.mapProjetosGruaFlorestal = mapProjetosGruaFlorestal;
	}

	public HashMap<Long, String> getMapProjetosGruaFlorestal() {
		return this.mapProjetosGruaFlorestal;
	}

	public void setCaminhaoTransp(List<CaminhaoTransporte> listCaminhaoTransp) {
		this.listCaminhaoTransp = listCaminhaoTransp;
	}

	public List<CaminhaoTransporte> getCaminhaoTransp() {
		return this.listCaminhaoTransp;
	}

	public List<Acompanhamento> getListAcompanhamentos() {
		return listAcompanhamentos;
	}

	public void setListAcompanhamentos(List<Acompanhamento> listAcompanhamentos) {
		this.listAcompanhamentos = listAcompanhamentos;
	}
	
	

	// ///////////////////////// LOCATION ID ////////////////////////////////////
	public Double[] getCoordFabrica() {
		// latitude , longitude
		Double[] res = { -22.54181187265773, -48.81139755249024 };
		return res;
	}

	public boolean isLocalMesas(Long localId) {
		return hasInArray(localId, getLocalId06Mesas());
	}

	public boolean isLocalQuadras(Long localId) {
		return hasInArray(localId, getLocalId16Quadras());
	}

	public Integer getLocalId01SentidoFabrica() {
		return 985;
	}

	public Integer getLocalId02AcessoEntrada() {
		return 986;
	}

	public Integer getLocalId03FilaBalancaEnt() {
		return 987;
	}

	public Integer getLocalId04BalancaEnt() {
		return 989;
	}

	public List<Integer> getLocalId05FilaFabrica() {
		return Arrays.asList(1304, 1305, 2233, 2234, 2235);
	}

	public boolean isLocalFilaFabrica(Long localId) {
		return hasInArray(localId, getLocalId05FilaFabrica());
	}

	private boolean isLocalStandBy(Long idLocal) {
		Boolean res = false;
		if (idLocal != null) {
			if (idLocal.intValue() == getLocalId15StandBy().intValue()) {
				res = true;
			}
		}
		return res;
	}

	public boolean isGruaChangeQuadraOuStandBy(GruaEntity grua) {
		Boolean res = false;
		Integer localIdStandBy = getLocalId15StandBy();

		if (grua.getIdLocal() == null) {
			grua.setIdLocal(localIdStandBy.longValue());
			res = true;
		} else if (!isLocalStandBy(grua.getIdLocal()) && !isLocalQuadras(grua.getIdLocal())) {
			grua.setIdLocal(localIdStandBy.longValue());
			res = true;
		}
		return res;
	}

	public Integer getLocalFilaFabrica(EstagioOperacional estagio) {
		Integer res = 1304;
		switch (estagio) {
		case FILA_MESA1:
			res = 1304;
			break;

		case FILA_MESA2:
			res = 1305;
			break;

		case FILA_MESA3:
			res = 2233;
			break;

		case FILA_MESA4:
			res = 2234;
			break;

		case FILA_QUADRAS:
			res = 2235;
			break;
		default:
			break;
		}
		return res;
	}

	public List<Integer> getLocalId06Mesas() {
		return Arrays.asList(1000, 1001, 1002, 1008);
	}

	public Integer getLocalMesa(EstagioOperacional estagio) {
		Integer res = 1000;
		switch (estagio) {
		case MESA_L1:
			res = 1000;
			break;
		case MESA_L2:
			res = 1001;
			break;
		case MESA_L3:
			res = 1002;
			break;
		case MESA_L4:
			res = 1008;
			break;
		default:
			break;

		}
		return res;
	}

	public Integer getLocalQuadra(EstagioOperacional estagio) {
		Integer res = 3000;
		switch (estagio) {
		case QUADRA001:
			res = 3000;
			break;
		case QUADRA002:
			res = 3001;
			break;
		case QUADRA003:
			res = 3002;
			break;
		case QUADRA004:
			res = 3003;
			break;
		case QUADRA005:
			res = 3004;
			break;
		case QUADRA006:
			res = 3005;
			break;
		case QUADRA007:
			res = 3006;
			break;
		case QUADRA008:
			res = 3007;
			break;
		case QUADRA009:
			res = 3008;
			break;
		case QUADRA010:
			res = 3009;
			break;
		case QUADRA011:
			res = 3010;
			break;
		case QUADRA012:
			res = 3011;
			break;
		case QUADRA013:
			res = 3012;
			break;
		case QUADRA014:
			res = 3013;
			break;
		case QUADRA015:
			res = 3014;
			break;
		case QUADRA016:
			res = 3015;
			break;
		case QUADRA017:
			res = 3016;
			break;
		case QUADRA018:
			res = 3017;
			break;
		case QUADRA019:
			res = 3018;
			break;
		case QUADRA020:
			res = 3019;
			break;
		default:
			break;
		}
		return res;
	}

	public Integer getLocalId11CheckpointSaida() {
		return 1006;
	}

	public Integer getLocalId12Varricao() {
		return 1005;
	}

	public Integer getLocalId13BalancaSai() {
		return 1004;
	}

	public Integer getLocalId14FilaBalancaSai() {
		return 1007;
	}

	public Integer getLocalId15StandBy() {
		return 1003;
	}

	public List<Integer> getLocalId16Quadras() {
		return Arrays.asList(3000, 3001, 3002, 3003, 3004, 3005, 3006, 3007, 3008, 3009, 3010, 3011, 3012, 3013, 3014,
				3015, 3016, 3017, 3018, 3019, 3020);
	}

	// ///////////////////////// EVENTS ////////////////////////////////////
	public boolean isEventoCaminhaoFilaBalanca(Long eventCode) {
		return hasInArray(eventCode, apontamentoCaminhaoFilaBalanca);
	}

	public boolean isEventoCaminhaoVarricao(Long eventCode) {
		return hasInArray(eventCode, apontamentoCaminhaoVarricao);
	}

	public boolean isEventoGruaPatioStandyBy(Long eventCode) {
		return hasInArray(eventCode, apontamentoGruasPatioStandBy);
	}

	// ///////////////////////// OFFICE PROVIDERS
	// ////////////////////////////////////
	public Integer getPrestador(Long customerChildId) {
		if (customerChildId != null) {
			if (customerChildId.intValue() == 30115) {
				return 0; // bracell - proprios
			}

			if (customerChildId.intValue() == 29170) {
				return 0; // bracell
			}

			if (customerChildId.intValue() == 29829) {
				return 21; // garbuio
			}

			if (customerChildId.intValue() == 29827) {
				return 41; // expresso
			}

			if (customerChildId.intValue() == 29831) {
				return 105; // placidos
			}

			if (customerChildId.intValue() == 29828) {
				return 104; // model
			}

			if (customerChildId.intValue() == 29830) {
				return 8; // transpess
			}

			if (customerChildId.intValue() == 29832) {
				return 23; // lourenço
			}

			if (customerChildId.intValue() == 31751) {
				return 106; // LP - TRANSP. MADEIRA
			}
		}
		return 0;
	}

	public String getPrestadorName(Long customerChildId) {

		if (customerChildId != null) {
			if (customerChildId.intValue() == 30115) {
				return getCustomerName();
			}

			if (customerChildId.intValue() == 29170) {
				return getCustomerName();
			}

			if (customerChildId.intValue() == 29829) {
				return "GARBUIO";
			}

			if (customerChildId.intValue() == 29827) {
				return "EXPRESSO NEPOMUCENO";
			}

			if (customerChildId.intValue() == 29831) {
				return "PLÁCIDOS";
			}

			if (customerChildId.intValue() == 29828) {
				return "MODEL";
			}

			if (customerChildId.intValue() == 29830) {
				return "TRANSPESS";
			}

			if (customerChildId.intValue() == 29832) {
				return "LOURENÇO";
			}
		}
		return getCustomerName();
	}

	public String getCustomerName() {
		return "BRACELL";
	}

	public EntidadeProprietaria buscarPrestadorPorId(Long custId) {
		EntidadeProprietaria res = new EntidadeProprietaria();
		res.setCustomer_child_name(getPrestadorName(custId));
		res.setDescricao(getPrestadorName(custId));
		res.setId(getPrestador(custId).longValue());
		return res;
	}

	public String getLocalIdFabricaDescarga() {
		return "15";
	}

	public Integer getProjGenericoLocalId() {
		return 36200;
	}

	public String getProjGenerico() {
		return "EUCALIPTOS";
	}

	// ///////////////////////// STOCKYARD/CHIPPERS ////////////////////////////////////
	public Map<Long, GruaEntity> hashLocaisQuadras = new LinkedHashMap<>();
	public Map<Long, GruaEntity> hashLocaisMesasL1 = new LinkedHashMap<>();
	public Map<Long, CaminhaoEntity> mapCaminhoesMesasL1 = new LinkedHashMap<>(); // caminhoes linha 1

	
	
	public Long localMesaL1Caminhao(CaminhaoEntity caminhao) {
		Long localId = null;
		for ( Entry<Long, CaminhaoEntity> entry : mapCaminhoesMesasL1.entrySet()) {
			CaminhaoEntity caminhaoVal = entry.getValue();
			if (caminhaoVal != null && caminhao != null && caminhaoVal.getId() != null && caminhao.getId() != null) {
				if (caminhaoVal.getId().longValue() == caminhao.getId().longValue()) {
					localId = entry.getKey();
				}	
			}
		}
		
		return localId;
	}

	
	public void mapeiaLocaisMesaL1Caminhoes(List<CaminhaoEntity> caminhoes) {
		mapCaminhoesMesasL1 = new LinkedHashMap<>();
		CaminhaoEntity lastCaminhaoCarreg=null, lastCaminhaoEv=null;
		
		for (CaminhaoEntity caminhao : caminhoes) {
			if (caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_MESAS.ordinal()) {
				if (lastCarregL1(lastCaminhaoCarreg, caminhao)) {
					lastCaminhaoCarreg = caminhao;
				}
				if (lastEventL1(lastCaminhaoCarreg, caminhao)) {
					lastCaminhaoEv = caminhao;
				}
			}
		}
		
		
		if (lastCaminhaoCarreg != null || lastCaminhaoEv != null) {
			if (lastCaminhaoCarreg != null) {
				mapCaminhoesMesasL1.put(1000l, lastCaminhaoCarreg); // mesa 1	
			} else if (lastCaminhaoEv != null) {
				mapCaminhoesMesasL1.put(1000l, lastCaminhaoEv); // mesa 1	
			}
		}

	}


	
	private boolean lastCarregL1(CaminhaoEntity lastCaminhaoCarreg, CaminhaoEntity caminhao) {
		if (lastCaminhaoCarreg == null && caminhao != null && caminhao.getSiteDate() != null) {
			return true;
		} else if (lastCaminhaoCarreg != null && caminhao != null) {
			if (lastCaminhaoCarreg.getSiteDate() != null && caminhao.getSiteDate() != null)
			return lastCaminhaoCarreg.getSiteDate().before(caminhao.getSiteDate());
		}
		return false;
	}
	
	
	private boolean lastEventL1(CaminhaoEntity lastCaminhaoCarreg, CaminhaoEntity caminhao) {
		if (lastCaminhaoCarreg == null && caminhao != null && caminhao.getEventDateTime() != null) {
			return true;
		} else if (lastCaminhaoCarreg != null && caminhao != null) {
			if (lastCaminhaoCarreg.getEventDateTime() != null && caminhao.getEventDateTime() != null)
			return lastCaminhaoCarreg.getEventDateTime().before(caminhao.getEventDateTime());
		}
		return false;
	}
	

	public void setMapChipperL1Clear() {
		hashLocaisMesasL1 = new LinkedHashMap<>();
		hashLocaisMesasL1.put(1000l, null); // Mesa 1
	}
	
	
	
	
	public List<GruaEntity> getChipperOrStandByL1(List<GruaEntity> gruasLine1) {
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		
		
		GruaEntity gruaLoad = getNewerLoading(gruasLine1);
		
		if (gruaLoad == null) {
			gruaLoad = getNewerEvent(gruasLine1);
		}
		 
		
		for (GruaEntity gruaL1: gruasLine1) {
			if (gruaLoad != null && gruaL1.getName() == gruaLoad.getName()) {
				gruaL1.setIdLocal(getLocalIdMesaByCraneL1(gruaL1.getName()));
				gruaL1.setSiteEstadoOperacional(EstadoOperacional.SI_MESAS.ordinal());
			} else {
				gruaL1.setSiteEstadoOperacional(EstadoOperacional.SI_STANDBY.ordinal());
			}
			
			gruas.add(gruaL1);
		}
		
		
		return gruas;
	}
	
	private GruaEntity getNewerEvent(List<GruaEntity> gruasLine1) {
		GruaEntity grua = null;
		for (GruaEntity gruaL1: gruasLine1) {
			if (grua == null) {
				if (gruaL1.getEventDateTime() != null) {
					grua = gruaL1;	
				}
			} else {
				if (gruaL1.getEventDateTime().after(grua.getEventDateTime())) {
					grua = gruaL1;
				}
			}
		}
		return grua;
	}

	private GruaEntity getNewerLoading(List<GruaEntity> gruasLine1) {
		GruaEntity grua = null;
		for (GruaEntity gruaL1: gruasLine1) {
			if (grua == null) {
				if (gruaL1.getLoadDateTime() != null) {
					grua = gruaL1;	
				}
			} else {
				if (gruaL1.getLoadDateTime().after(grua.getLoadDateTime())) {
					grua = gruaL1;
				}
			}
		}
		return grua;
	}

	public List<GruaEntity> getNewerEventChipperL1(List<GruaEntity> gruasLine1) {
		
		
		for (GruaEntity gruaL1: gruasLine1) {
			Long localId = getLocalIdMesaByCraneL1(gruaL1.getName());
			
			if (hashLocaisMesasL1.containsKey(localId)) {			
				GruaEntity currGrua = hashLocaisMesasL1.get(localId);			
				if (isEventNewerThen(currGrua,gruaL1)) {
					gruaL1.setIdLocal(localId);
					hashLocaisMesasL1.put(gruaL1.getIdLocal(), gruaL1);
				}
			}	
		}
		
		
		
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		for ( Entry<Long, GruaEntity> entry : hashLocaisMesasL1.entrySet()) {
			GruaEntity candidate = entry.getValue();
			if (candidate != null) {
				Long localId = getLocalIdMesaByCraneL1(candidate.getName());
				candidate.setIdLocal(localId);
				gruas.add(candidate);	
			}			
		}		
		return gruas;
	}
	
	
	public List<GruaEntity> getLoadMapChipperL1(GruaEntity grua) {
		Long localId = getLocalIdMesaByCraneL1(grua.getName());
		
		if (hashLocaisMesasL1.containsKey(localId)) {			
			GruaEntity currGrua = hashLocaisMesasL1.get(localId);			
			if (isLoadEventNewerThen(currGrua,grua)) {
				grua.setIdLocal(localId);
				hashLocaisMesasL1.put(grua.getIdLocal(), grua);
			}
		}
		
		
		List<GruaEntity> gruas = new ArrayList<GruaEntity>();
		for ( Entry<Long, GruaEntity> entry : hashLocaisMesasL1.entrySet()) {
			GruaEntity candidate = entry.getValue();
			if (candidate != null) {
				localId = getLocalIdMesaByCraneL1(candidate.getName());
				candidate.setIdLocal(localId);
				gruas.add(candidate);	
			}			
		}		
		return gruas;
	}
	
	
	private boolean isLoadEventNewerThen(GruaEntity currGrua, GruaEntity candidateGrua) {
		boolean result = false;
		
		if (currGrua == null) {
			result = true;
		} else if (candidateGrua != null) {
			
			Date currEventDate = currGrua.getLoadDateTime();
			Date newEventDate = candidateGrua.getLoadDateTime();
			
			if (currEventDate == null && newEventDate != null) {
				result = true;
			} else
			if (currEventDate != null && newEventDate != null && currEventDate.before(newEventDate)) {
				result = true;
			}
		}
		
		return result;
	}
	
	private boolean isEventNewerThen(GruaEntity currGrua, GruaEntity candidateGrua) {
		boolean result = false;
		
		if (currGrua == null) {
			result = true;
		} else if (candidateGrua != null) {
			
			Date currEventDate = currGrua.getEventDateTime();
			Date newEventDate = candidateGrua.getEventDateTime();
			
			if (currEventDate == null && newEventDate != null) {
				result = true;
			} else
			if (currEventDate != null && newEventDate != null && currEventDate.before(newEventDate)) {
				result = true;
			}
		}
		
		return result;
	}

	public void mapeiaLocaisGruas(List<GruaEntity> gruas) {
		hashLocaisQuadras.put(3000l, null); // 00
		hashLocaisQuadras.put(3001l, null); // 01
		hashLocaisQuadras.put(3002l, null); // 02
		hashLocaisQuadras.put(3003l, null); // 03
		hashLocaisQuadras.put(3004l, null); // 03-1
		hashLocaisQuadras.put(3005l, null); // 02-1
		hashLocaisQuadras.put(3006l, null); // 02-2
		hashLocaisQuadras.put(3007l, null); //
		hashLocaisQuadras.put(3008l, null); //
		hashLocaisQuadras.put(3009l, null); //
		hashLocaisQuadras.put(3010l, null); //
		hashLocaisQuadras.put(3011l, null); //
		hashLocaisQuadras.put(3012l, null); //
		hashLocaisQuadras.put(3013l, null); //
		hashLocaisQuadras.put(3014l, null); //
		hashLocaisQuadras.put(3015l, null); //
		hashLocaisQuadras.put(3016l, null); //
		hashLocaisQuadras.put(3017l, null); //
		hashLocaisQuadras.put(3018l, null); //
		hashLocaisQuadras.put(3019l, null); //
		hashLocaisQuadras.put(3020l, null); //

		gruas.forEach(grua -> {
			if (hashLocaisQuadras.containsKey(grua.getIdLocal())) {
				hashLocaisQuadras.put(grua.getIdLocal(), grua);
			}
		});

	}

	public Boolean truckIsAloneInStockyard(CaminhaoEntity caminhao) {
		if (isLocalQuadras(caminhao.getIdLocal())) {
			if (hashLocaisQuadras.get(caminhao.getIdLocal()) == null) {
				return true;
			}
		}
		return false;
	}

	// ///////////////////////// CONVERTION ////////////////////////////////////
	public EstadoOperacional convertPgStateToEstadoOperacional(Integer pgStateCode) {
		EstadoOperacional res = null;
		if (pgStateCode == 200) {
			res = EstadoOperacional.TRANSP_FABRICA; // 16
		}

		if (pgStateCode == 202) {
			res = EstadoOperacional.TRANSP_VIAGEM; // 17
		}

		if (pgStateCode == 201) {
			res = EstadoOperacional.TRANSP_FILA_EM_CAMPO; // 18
		}
		if (pgStateCode == 203) {
			res = EstadoOperacional.TRANSP_CARREGAMENTO; // 19
		}

		if (pgStateCode == 207) {
			res = EstadoOperacional.TRANSP_VIAGEM; // 17
		}

		if (pgStateCode == 101) {
			res = EstadoOperacional.CARREG_CHEIO; // 15
		} else {
			res = EstadoOperacional.CARREG_VAZIO; // 14
		}
		return res;

	}

	public boolean isTransport(CaminhaoEntity caminhao) {
		if (caminhao != null && caminhao.getSiteEstadoOperacional() != null
				&& caminhao.getSiteEstadoOperacional() == EstadoOperacional.SI_VIAGEM.ordinal()) {
			if (caminhao.getTranspEstadoOperacional() != null
					&& caminhao.getTranspEstadoOperacional() != EstadoOperacional.TRANSP_FABRICA.ordinal()) {
				return true;
			}
		}
		return false;
	}

	// //////////////////////////////////////FARM CRANE INPUT
	// ////////////////////////////////////////////////////////

	/**
	 * 
	 * 
	 * 
	 * bracell crane input data format:
	 * plates,pileField,farm,field(stand),pile,pileLocale,chipper,eventType
	 *
	 * (bracell) 1.placa, 2.pilha fazenda, 3.projeto, 4.talhão, 5.pilha estoque,
	 * 6.local estoque, 7.linha de consumo, 8.tipo evento (1-carreg,
	 * 2-descarreg),9-odometro,10-horimetro //format:
	 * 1.driverId,2.plates,3.pileField,4.farm,5.field(stand),6.pile,7.pileLocale,8.chipper,9.eventType
	 * 
	 * 
	 * Generic: 10,2273762218,1723,14101 -- generic note:
	 * 10,driverId,odometer,hourmeter
	 * 
	 *
	 * Profile Crane Forest - farm - field - asset
	 * CNR9C32,0,1237,004,0,0,0,1,0,51912 -> 4g
	 * 102,driverId,plates,project,field,loadId(1=C,2=D),odometer,houmeter -> sat
	 * 
	 *
	 * Profile Stockyard: - Event Type (Loading/Unloading) - Stock Locale - Stock
	 * Pile - Asset RWS0C19,0,0,0,1513,2246,0,1,0,30667 ->4g
	 * 101,driverId,plates,pile,locale,loadtype,odometer,hourmeter -> sat
	 *
	 * Profile Line Feed: - Line Feed (chipper) - Asset *
	 * RWS0C19,0,0,0,0,0,1,2,0,30667 ->4g
	 * 100,driverId,plates,chipper,load,odometer,hourmeter -> sat
	 * 
	 */

	public String inputItem(String string, int fieldId) {
		String result = null;

		if (string != null) {
			String[] split = string.split(",");

			if (split != null && split.length > 0) {
				String code = split[0];
				if (isNoteFromSat(code)) {
					result = parseNoteSat(NoteSat.toEntity(NumberUtils.parseInt(code)), string, fieldId);
				} else {
					if (split.length >= fieldId) {
						result = split[fieldId - 1];
					}
				}
			}
		}

		return result;
	}

	// note satellite codes
	enum NoteSat {
		UNKNOWM(-1), NOTE_GENERIC(10), NOTE_CRANE_FOREST(102), NOTE_CRANE_STOCKYARD(101), NOTE_CRANE_CHIPPER(100);

		private Integer code;

		private NoteSat(Integer code) {
			this.code = code;
		}

		public static NoteSat toEntity(Integer code) {
			for (NoteSat item : values()) {
				if (item.code == code) {
					return item;
				}
			}
			return UNKNOWM;
		}

	}

	public String parseNoteSat(NoteSat code, String string, int fieldId) {
		String result = null;
		Integer pos = null;

		if (string != null) {
			String[] split = string.split(",");

			switch (code) {
			case NOTE_GENERIC:
				break;
			case NOTE_CRANE_FOREST:
				// 102,driverId,plates,project,field,loadId(1=C,2=D),odometer,houmeter -> sat
				if (fieldId == 1) { // plates
					pos = 2;
				}
				if (fieldId == 3) { // project
					pos = 3;
				}
				if (fieldId == 4) { // field
					pos = 4;
				}
				if (fieldId == 8) { // loadType
					pos = 5;
				}
				break;
			case NOTE_CRANE_STOCKYARD:
				// 101,driverId,plates,pile,locale,loadtype,odometer,hourmeter -> sat
				if (fieldId == 1) { // plates
					pos = 2;
				}
				if (fieldId == 5) { // pile
					pos = 3;
				}
				if (fieldId == 6) { // locale
					pos = 4;
				}
				if (fieldId == 8) { // loadType
					pos = 5;
				}
				break;
			case NOTE_CRANE_CHIPPER:
				// 100,driverId,plates,chipper,load,odometer,hourmeter -> sat
				if (fieldId == 1) { // plates
					pos = 2;
				}
				if (fieldId == 7) { // chipper
					pos = 3;
				}
				if (fieldId == 8) { // loadType
					pos = 4;
				}
				break;

			default:
				break;
			}

			if (pos != null && split != null) {
				if (split.length >= pos + 1) {
					return split[pos];
				}
			}

		}

		return result;
	}

	public Boolean isNoteFromSat(String strCode) {
		Boolean result = false;
		List<Integer> lista = new ArrayList<Integer>(Arrays.asList(10, 100, 101, 102));

		for (Integer item : lista) {
			Integer code = NumberUtils.parseInt(strCode);
			if (code != null && code == item) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Long getProjetoId(Long idProjeto) {
		Long res = 0l;

		if (idProjeto != null) {
			res = idProjeto.longValue();
		}
		return res;
	}

}

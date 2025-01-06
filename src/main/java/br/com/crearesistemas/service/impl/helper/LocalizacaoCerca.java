package br.com.crearesistemas.service.impl.helper;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.service.dto.RastreamentoDto;


public class LocalizacaoCerca {
	private static LocalizacaoCerca instance;
	
	private boolean modoRealTime = false;
	
	private boolean ativoEstaNoAcessoPrivadoEFabrica = false;
	private boolean ativoEstaNaFabrica = false;
	private boolean ativoEstaNoAcessoPrivado = false;
	private boolean ativoEstaControleDeAcesso = false;
	private boolean ativoEstaViaduto = false;
	private boolean ativoEstavaNoAcessoPrivadoEFabrica = false;
	private boolean ativoEstaAcessoSaida = false;
	private boolean ativoEstaFilaBarcacaNaFabrica = false;
	private boolean ativoEstaFilaTransitoNaFabrica = false;	
	private boolean ativoEstavaNaFabrica = false;
	
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// Porto de Rio Grande
	private boolean ativoEstaNoRioGrande = false;
	private boolean ativoEstavaNoRioGrande = false;
	

	// //////////////////////////////////////////////////////////////////////////////////////
	// Est\u00E1gios mesas da fabrica
	private boolean ativoEstaNaFabricaMesas = false;
	private boolean ativoEstaNaFabricaMesaL100 = false;
	private boolean ativoEstaNaFabricaMesaL300 = false;
	
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// Est\u00E1gios quadras da fabrica
	private boolean ativoEstaNaFabricaQuadras = false;
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// Est\u00E1gios fila fabrica
	private boolean ativoEstaFilaInternaNaFabrica 	= false;
	private boolean ativoEstaFila 					= false;
	private boolean ativoEstaFilaG1 				= false;
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// Est\u00E1gios patio da fabrica (quadras + mesas + filas)
	private boolean ativoEstaNaFabricaPatio = false;
		
	// //////////////////////////////////////////////////////////////////////////////////////
	// Area da barcaca em pelotas
	private boolean ativoEstaPelotasBarcaca         = false;
	private boolean ativoEstavaPelotasBarcaca       = false;
	
	// //////////////////////////////////////////////////////////////////////////////////////
	// Area da balanca + quadras
	private boolean ativoEstaPelotasBalancaQuadras = false;
	// Area das quadras
	private boolean ativoEstaPelotasQuadras = false;
	
	
	private boolean ativoEstaPelotasBarcacaFilasVarricao = false;
	private boolean ativoEstavaPelotasBalancaQuadras = false;
	private boolean ativoEstavaPelotasBarcacaFilasVarricao = false;

	// //////////////////////////////////////////////////////////////////////////////////////
	// Area da fila interna + fila barcaca em pelotas
	private boolean ativoEstaPelotasFilas = false;

	// //////////////////////////////////////////////////////////////////////////////////////
	// Area da varricao
	private boolean ativoEstaPelotasVarricao = false;
	private boolean ativoEstavaPelotasVarricao = false;


	
	public static synchronized LocalizacaoCerca getInstance() {
		if (instance == null) {
			instance = new LocalizacaoCerca();
		}
		return instance;		
	}
	
	public LocalizacaoCerca(RastreamentoDto rastreamento) {
		atualizaLocalizacao(rastreamento);
	}

	public LocalizacaoCerca() {
		super();
	}


	private void atualizaLocalizacao(RastreamentoDto rastreamento) {
		
		 ativoEstaNoAcessoPrivadoEFabrica 	= implementoEstaNoLocal(rastreamento, CERCAS.ACESSO_PRIVADO_FABRICA);
		 
		 if (ativoEstaNoAcessoPrivadoEFabrica) {
			 // ativo na fabrica
			 ativoEstaNaFabrica 				= implementoEstaNoLocal(rastreamento, CERCAS.FABRICA);
			 
			// acesso privado
			 if (!ativoEstaNaFabrica)	 			ativoEstaNoAcessoPrivado 			= implementoEstaNoLocal(rastreamento, CERCAS.ACESSO_PRIVADO);
			 
			 // //////////////////////////////////////////////////////////////////////////////////////
			 // ativo esta na fabrica
			 if (ativoEstaNaFabrica) {
				 // ativo na fila fabrica ?
				 ativoEstaFila 							= implementoEstaNoLocal(rastreamento, CERCAS.FILA);
				 ativoEstaFilaTransitoNaFabrica         = implementoEstaNoLocal(rastreamento, CERCAS.FILA_TRANSITO_FABRICA);
				 ativoEstaFilaInternaNaFabrica          = implementoEstaNoLocal(rastreamento, CERCAS.FILA_INTERNA_FABRICA);
				 ativoEstaNaFabricaMesas          		= implementoEstaNoLocal(rastreamento, CERCAS.FABRICA_MESAS);
				 ativoEstaNaFabricaQuadras				= implementoEstaNoLocal(rastreamento, CERCAS.FABRICA_QUADRAS);
				 ativoEstaNaFabricaPatio				= implementoEstaNoLocal(rastreamento, CERCAS.FABRICA_PATIO);
				 
				 if (ativoEstaNaFabricaMesas) {
					 ativoEstaNaFabricaMesaL100 = implementoEstaNoLocal(rastreamento, CERCAS.FABRICA_MESA_L100);
					 ativoEstaNaFabricaMesaL300 = implementoEstaNoLocal(rastreamento, CERCAS.FABRICA_MESA_L300);
				 }
				 
				 if (ativoEstaFila) {
					// filaG1
					 ativoEstaFilaG1   					= implementoEstaNoLocal(rastreamento, CERCAS.FILA_G1);
				 } else {
					 ativoEstaFilaBarcacaNaFabrica 	    = implementoEstaNoLocal(rastreamento, CERCAS.FILA_BARCACA_FABRICA);
				 }
			 }
			 
			 // //////////////////////////////////////////////////////////////////////////////////////
			 // ativo esta no acesso privado
			 if (ativoEstaNoAcessoPrivado) {
				 // controle de acesso
				 ativoEstaControleDeAcesso 			= implementoEstaNoLocal(rastreamento, CERCAS.CONTROLE_DE_ACESSO);
				 if (!ativoEstaControleDeAcesso){
					 // viaduto
					 ativoEstaViaduto 					= implementoEstaNoLocal(rastreamento, CERCAS.VIADUTO);					 
					 if (!ativoEstaViaduto) {
						 ativoEstaAcessoSaida 				= implementoEstaNoLocal(rastreamento, CERCAS.ENTRE_ACESSO_PRIVADO_SAIDA_E_VARRICAO);	 
					 }
				 } 
			 }
		 }

		 // //////////////////////////////////////////////////////////////////////////////////////
		 // verifica se ativo esta no porto rio grande
		 if (!ativoEstaNoRioGrande) {
			 ativoEstaNoRioGrande = implementoEstaNoLocal(rastreamento, CERCAS.PORTO_RIOGRANDE);
		 }
		 
		 // //////////////////////////////////////////////////////////////////////////////////////
		 // verifica se ativo esta no porto
		 if (!ativoEstaNoAcessoPrivadoEFabrica) {
			 ativoEstaPelotasQuadras = implementoEstaNoLocal(rastreamento, CERCAS.PELOTAS_QUADRAS);
			 ativoEstaPelotasBalancaQuadras   		= implementoEstaNoLocal(rastreamento, CERCAS.PELOTAS_BALANCA_QUADRAS);
			 if (!ativoEstaPelotasBalancaQuadras)
				 ativoEstaPelotasBarcacaFilasVarricao   = implementoEstaNoLocal(rastreamento, CERCAS.PELOTAS_BARCACA_FILAS_VARRICAO);
			 
			 ativoEstaPelotasBarcaca = implementoEstaNoLocal(rastreamento, CERCAS.PELOTAS_BARCACA);
			 ativoEstaPelotasFilas = implementoEstaNoLocal(rastreamento, CERCAS.PELOTAS_FILAS);
			 ativoEstaPelotasVarricao = implementoEstaNoLocal(rastreamento, CERCAS.PELOTAS_VARRICAO);
			 
		 }
		 		 
		// verifica se estava anteriormente na fabrica ou acesso privado
		 RastreamentoDto rastreamentoAnterior=new RastreamentoDto(0.0f,0.0f);
		if (rastreamentoAnterior != null){
			ativoEstavaNoAcessoPrivadoEFabrica 		= implementoEstaNoLocal(rastreamentoAnterior, CERCAS.ACESSO_PRIVADO_FABRICA);
			
			if (ativoEstavaNoAcessoPrivadoEFabrica){
				ativoEstavaNaFabrica 					= implementoEstaNoLocal(rastreamentoAnterior, CERCAS.FABRICA);	
			}
			
			if (!ativoEstavaNoAcessoPrivadoEFabrica) {
				ativoEstavaPelotasBalancaQuadras   			= implementoEstaNoLocal(rastreamentoAnterior, CERCAS.PELOTAS_BALANCA_QUADRAS);
				if (!ativoEstavaPelotasBalancaQuadras){
					ativoEstavaPelotasBarcacaFilasVarricao    	= implementoEstaNoLocal(rastreamentoAnterior, CERCAS.PELOTAS_BARCACA_FILAS_VARRICAO);	
				}
				
				ativoEstavaPelotasBarcaca = implementoEstaNoLocal(rastreamentoAnterior, CERCAS.PELOTAS_BARCACA);
				
				ativoEstavaPelotasVarricao = implementoEstaNoLocal(rastreamentoAnterior, CERCAS.PELOTAS_VARRICAO);
				
				// //////////////////////////////////////////////////////////////////////////////////////
				// verifica se ativo esta no porto rio grande				
				ativoEstavaNoRioGrande = implementoEstaNoLocal(rastreamentoAnterior, CERCAS.PORTO_RIOGRANDE);
				 
			}
			
		};			
	}
	
	
	public boolean isAtivoEstaNoAcessoPrivadoEFabrica() {
		return ativoEstaNoAcessoPrivadoEFabrica;
	}

	public void setAtivoEstaNoAcessoPrivadoEFabrica(
			boolean ativoEstaNoAcessoPrivadoEFabrica) {
		this.ativoEstaNoAcessoPrivadoEFabrica = ativoEstaNoAcessoPrivadoEFabrica;
	}

	public boolean isAtivoEstaNaFabrica() {
		return ativoEstaNaFabrica;
	}

	public void setAtivoEstaNaFabrica(boolean ativoEstaNaFabrica) {
		this.ativoEstaNaFabrica = ativoEstaNaFabrica;
	}

	public boolean isAtivoEstaNoAcessoPrivado() {
		return ativoEstaNoAcessoPrivado;
	}

	public void setAtivoEstaNoAcessoPrivado(boolean ativoEstaNoAcessoPrivado) {
		this.ativoEstaNoAcessoPrivado = ativoEstaNoAcessoPrivado;
	}
	
	public boolean isAtivoEstaControleDeAcesso() {
		return ativoEstaControleDeAcesso;
	}

	public void setAtivoEstaControleDeAcesso(boolean ativoEstaControleDeAcesso) {
		this.ativoEstaControleDeAcesso = ativoEstaControleDeAcesso;
	}

	public boolean isAtivoEstaViaduto() {
		return ativoEstaViaduto;
	}

	public void setAtivoEstaViaduto(boolean ativoEstaViaduto) {
		this.ativoEstaViaduto = ativoEstaViaduto;
	}
	
	public boolean isAtivoEstavaNoAcessoPrivadoEFabrica() {
		return ativoEstavaNoAcessoPrivadoEFabrica;
	}

	public void setAtivoEstavaNoAcessoPrivadoEFabrica(
			boolean ativoEstavaNoAcessoPrivadoEFabrica) {
		this.ativoEstavaNoAcessoPrivadoEFabrica = ativoEstavaNoAcessoPrivadoEFabrica;
	}
	
	public boolean isAtivoEstaNaFabricaPatio() {
		return ativoEstaNaFabricaPatio;
	}

	public void setAtivoEstaNaFabricaPatio(boolean ativoEstaNaFabricaPatio) {
		this.ativoEstaNaFabricaPatio = ativoEstaNaFabricaPatio;
	}

	public boolean isAtivoEstaFila() {
		return ativoEstaFila;
	}

	public void setAtivoEstaFila(boolean ativoEstaFila) {
		this.ativoEstaFila = ativoEstaFila;
	}
	
	public boolean isAtivoEstaFilaG1() {
		return ativoEstaFilaG1;
	}

	public void setAtivoEstaFilaG1(boolean ativoEstaFilaG1) {
		this.ativoEstaFilaG1 = ativoEstaFilaG1;
	}
	
	public boolean isAtivoEstaAcessoSaida() {
		return ativoEstaAcessoSaida;
	}

	public void setAtivoEstaAcessoSaida(boolean ativoEstaAcessoSaida) {
		this.ativoEstaAcessoSaida = ativoEstaAcessoSaida;
	}
	
	public boolean isAtivoEstavaNaFabrica() {
		return ativoEstavaNaFabrica;
	}

	public void setAtivoEstavaNaFabrica(boolean ativoEstavaNaFabrica) {
		this.ativoEstavaNaFabrica = ativoEstavaNaFabrica;
	}
	
	public boolean isAtivoEstaFilaBarcacaNaFabrica() {
		return ativoEstaFilaBarcacaNaFabrica;
	}

	public void setAtivoEstaFilaBarcacaNaFabrica(
			boolean ativoEstaFilaBarcacaNaFabrica) {
		this.ativoEstaFilaBarcacaNaFabrica = ativoEstaFilaBarcacaNaFabrica;
	}
	
	public boolean isAtivoEstaNoRioGrande() {
		return ativoEstaNoRioGrande;
	}

	public void setAtivoEstaNoRioGrande(boolean ativoEstaRioGrande) {
		this.ativoEstaNoRioGrande = ativoEstaRioGrande;
	}

	public boolean isAtivoEstavaNoRioGrande() {
		return ativoEstavaNoRioGrande;
	}

	public void setAtivoEstavaNoRioGrande(boolean ativoEstavaRioGrande) {
		this.ativoEstavaNoRioGrande = ativoEstavaRioGrande;
	}
	
	
	public Polygon getPolygon(CERCAS cercas) {
		Polygon poligono = null;
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		
		switch (cercas) {
		
		// cerca fabrica
		case FABRICA:
			/*Coordinate[] coordenadasFabrica = new Coordinate[] { 
					new Coordinate(-48.810539245605476, -22.554035263715026),new Coordinate(-48.830795288085945, -22.538339662672225),
					new Coordinate(-48.8144874572754, -22.520580871723936),new Coordinate(-48.788566589355476, -22.535010063302593),
					new Coordinate(-48.810539245605476, -22.554035263715026) };*/
			
			Coordinate[]  coordenadasFabrica = new Coordinate[] {
                    new Coordinate(-48.88419866678306, -22.518554232984492),  new Coordinate(-48.88632297632285, -22.52241939948942),
                    new Coordinate(-48.88666629907676, -22.52658176557893),   new Coordinate(-48.87735843658448, -22.533686127895415),
                    new Coordinate(-48.805298209190376, -22.58595504072408),  new Coordinate(-48.80313634872437, -22.587738105844466),
                    new Coordinate(-48.79680097103119, -22.58658902207813),   new Coordinate(-48.79454255104065, -22.584479041579222),
                    new Coordinate(-48.79444599151612, -22.5808781316731),    new Coordinate(-48.79770755767823, -22.570941707848476),
                    new Coordinate(-48.77921104431153, -22.53241767580791),   new Coordinate(-48.76311779022217, -22.501812739448),
                    new Coordinate(-48.76130461692811, -22.491523677838405),  new Coordinate(-48.76965165138245, -22.47763520524305),
                    new Coordinate(-48.79114151000977, -22.48630946820121),   new Coordinate(-48.79337310791016, -22.493367438791978),
                    new Coordinate(-48.840579986572266, -22.50320041558609),  new Coordinate(-48.88419866678306, -22.518554232984492)
             };
			
			poligono = geometryFactory.createPolygon(coordenadasFabrica);
			break;

		case BRACELL_BALANCA_LINHA1:
			Coordinate[] coordenadasBracellBALANCALinha1 = new Coordinate[] {
					new Coordinate(-48.810324668884284,-22.541787099772478),new Coordinate(-48.81024956703186,-22.541920873300057),new Coordinate(-48.81016373634339,-22.542049692130014),
					new Coordinate(-48.80993843078614,-22.542198329092145),new Coordinate(-48.80971312522888,-22.542084374102167),new Coordinate(-48.80972385406494,-22.54186141841491),
					new Coordinate(-48.809906244277954,-22.54163350778463),new Coordinate(-48.81011009216309,-22.541465051859532),new Coordinate(-48.810292482376106,-22.541341187077563),
					new Coordinate(-48.81048560142518,-22.54141550596009),new Coordinate(-48.81049633026124,-22.54165828069746),new Coordinate(-48.810324668884284,-22.541787099772478)
			};
			poligono = geometryFactory.createPolygon(coordenadasBracellBALANCALinha1);
			break;

			
					
		case BRACELL_BALANCA_LINHA2:
			Coordinate[] coordenadasBracellBALANCALinha2 = new Coordinate[] {
					new Coordinate(-48.80841493606568,-22.535727518509276),new Coordinate(-48.80871534347535,-22.53473655596098),new Coordinate(-48.809316158294685,-22.534241072019984),
					new Coordinate(-48.80991697311402,-22.533745586301144),new Coordinate(-48.81068944931031,-22.53293298587362),new Coordinate(-48.811333179473884,-22.53243749546138),
					new Coordinate(-48.81184816360474,-22.532675331081116),new Coordinate(-48.81223440170289,-22.53315100109181),new Coordinate(-48.81232023239136,-22.533646488944036),
					new Coordinate(-48.8120198249817,-22.534122155609605),new Coordinate(-48.81197690963745,-22.534260891411755),new Coordinate(-48.811590671539314,-22.5345978206367),
					new Coordinate(-48.81116151809693,-22.53503384547218),new Coordinate(-48.81047487258912,-22.535687880143882),new Coordinate(-48.80910158157349,-22.53576715686328),
					new Coordinate(-48.80974531173707,-22.53588607185704),new Coordinate(-48.80841493606568,-22.535727518509276)
			};
			poligono = geometryFactory.createPolygon(coordenadasBracellBALANCALinha2);
			break;

			
		case BRACELL_LINHA1:
			Coordinate[] coordenadasBracellLinha1 = new Coordinate[] {
					new Coordinate(-48.811054229736335,-22.54216860171252),new Coordinate(-48.80918741226196,-22.53913637536263),new Coordinate(-48.808028697967536,-22.53707521616193),
					new Coordinate(-48.807106018066406,-22.536936483187393),new Coordinate(-48.805303573608406,-22.53824453140822),new Coordinate(-48.800625801086426,-22.54171278109002),
					new Coordinate(-48.80090475082398,-22.54244605700666),new Coordinate(-48.802063465118415,-22.544586407672853),new Coordinate(-48.80397319793701,-22.547955410923674),
					new Coordinate(-48.80425214767457,-22.54819321981021),new Coordinate(-48.807041645050056,-22.54494312955706),new Coordinate(-48.80884408950806,-22.54343696421881),
					new Coordinate(-48.811054229736335,-22.54216860171252)
			};
			poligono = geometryFactory.createPolygon(coordenadasBracellLinha1);
			break;

			
		case BRACELL_LINHA2:
			Coordinate[] coordenadasBracellLinha2 = new Coordinate[] { 
				new Coordinate(-48.811354637146,-22.542010055579755),new Coordinate(-48.816847801208496,-22.539750753401982),new Coordinate(-48.815088272094734,-22.536044625022736),
				new Coordinate(-48.81326436996461,-22.534003239096812),new Coordinate(-48.811526298522956,-22.53243749546138),new Coordinate(-48.81043195724487,-22.533487933024745),
				new Coordinate(-48.80770683288575,-22.536421188061585),new Coordinate(-48.808565139770515,-22.537233767962114),new Coordinate(-48.809337615966804,-22.538819275949997),
				new Coordinate(-48.810453414917,-22.54046422124226),new Coordinate(-48.811354637146,-22.542010055579755)
			};
			poligono = geometryFactory.createPolygon(coordenadasBracellLinha2);
			break;

        case FACTORY_CHECKPOINT_ENTRANCE1:
        	Coordinate[] coordinatesEnt1 = new Coordinate[] {
                    new Coordinate(-48.79096984863282, -22.48654738318413),  new Coordinate(-48.78273010253907, -22.483216536211668),
                    new Coordinate(-48.76968383789063, -22.480995927039316), new Coordinate(-48.76144409179688, -22.491464201269597),
                    new Coordinate(-48.76316070556641, -22.501455906190692), new Coordinate(-48.77277374267579, -22.50240745949774),
                    new Coordinate(-48.7789535522461, -22.49986996946481),   new Coordinate(-48.785133361816406, -22.49749103028242),
                    new Coordinate(-48.79199981689454, -22.491940013104305), new Coordinate(-48.79096984863282, -22.48654738318413)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEnt1);
            break;

        case FACTORY_CHECKPOINT_ENTRANCE2:
        	Coordinate[] coordinatesEnt2 = new Coordinate[] {
                    new Coordinate(-48.88418197631837, -22.51858286390888),   new Coordinate(-48.8749122619629, -22.51921715492657),
                    new Coordinate(-48.86907577514649, -22.52476707713866),   new Coordinate(-48.87147903442383, -22.531902363819412),
                    new Coordinate(-48.87731552124024, -22.533487933024745),  new Coordinate(-48.88658523559571, -22.526511292363335),
                    new Coordinate(-48.886241912841804, -22.52238856634407),  new Coordinate(-48.88418197631837, -22.51858286390888)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEnt2);
            break;

        case FACTORY_CHECKPOINT_ENTRANCE3:
        	Coordinate[] coordinatesEnt3 = new Coordinate[] {
                    new Coordinate(-48.80401611328126, -22.577361333770288),  new Coordinate(-48.79775047302247, -22.577480212911677),
                    new Coordinate(-48.79448890686036, -22.58088803801929),   new Coordinate(-48.794574737548835, -22.584454276357988),
                    new Coordinate(-48.79680633544922, -22.586554351298265),  new Coordinate(-48.80315780639649, -22.58770343535385),
                    new Coordinate(-48.805303573608406, -22.585920369784613), new Coordinate(-48.804788589477546, -22.5807295363949),
                    new Coordinate(-48.80401611328126, -22.577361333770288)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEnt3);
            break;

        case FACTORY_CHECKPOINT_EMBED_ENTRANCE1:
        			
        	Coordinate[] coordinatesEmbEnt1 = new Coordinate[] {
        			new Coordinate(-48.778567312838284,-22.500557211121734),new Coordinate(-48.77757311041933,-22.500557211121734),
        			new Coordinate(-48.77757311041933,-22.50193829157061),new Coordinate(-48.778567312838284,-22.50193829157061),
        			new Coordinate(-48.778567312838284,-22.500557211121734)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEmbEnt1);
            break;
		
        case FACTORY_CHECKPOINT_EMBED_ENTRANCE2:
			
        	Coordinate[] coordinatesEmbEnt2 = new Coordinate[] {
        			new Coordinate(-48.870839595794685,-22.53130520067316),new Coordinate(-48.867802619060974,-22.53130520067316),
        			new Coordinate(-48.867802619060974,-22.53356721100715),new Coordinate(-48.870839595794685,-22.53356721100715),new Coordinate(-48.870839595794685,-22.53130520067316)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEmbEnt2);
            break;
		
        case FACTORY_CHECKPOINT_EMBED_ENTRANCE3:
			
        	Coordinate[] coordinatesEmbEnt3 = new Coordinate[] {
        			new Coordinate(-48.805861473083496,-22.57925678351722),new Coordinate(-48.80475282756379,-22.57925678351722),
					new Coordinate(-48.80475282756379,-22.580386115683943),new Coordinate(-48.805861473083496,-22.580386115683943),new Coordinate(-48.805861473083496,-22.57925678351722)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEmbEnt3);
            break;
            
        case FACTORY_CHECKPOINT_EMBED_EXIT1:
			
        	Coordinate[] coordinatesEmbExit1 = new Coordinate[] {
        			new Coordinate(-48.816851376614075,-22.55073977778581),new Coordinate(-48.81500244140626,-22.55073977778581),
					new Coordinate(-48.81500244140626,-22.553220139942045),new Coordinate(-48.816851376614075,-22.553220139942045),
					new Coordinate(-48.816851376614075,-22.55073977778581)
        			
        			
            };
        	poligono = geometryFactory.createPolygon(coordinatesEmbExit1);
            break;
	
        case FACTORY_CHECKPOINT_EMBED_EXIT2:
			
        	Coordinate[] coordinatesEmbExit2 = new Coordinate[] {
        			new Coordinate(-48.794176340976264,-22.52322104962861),new Coordinate(-48.791928291757365,-22.52322104962861),
					new Coordinate(-48.791928291757365,-22.525064388108873),new Coordinate(-48.794176340976264,-22.525064388108873),
					new Coordinate(-48.794176340976264,-22.52322104962861)
            };
        	poligono = geometryFactory.createPolygon(coordinatesEmbExit2);
            break;
            
            				
		default:
			break;
		}
	
		return poligono;
	}
	
	
	public boolean implementoEstaNoLocal(RastreamentoDto rastreamento, CERCAS cercas) {
		boolean implementoEstaLocal = false;
		
		if (rastreamento != null){
			// verifica se implemento esta atualmente na fábrica ou acesso privado
			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
			Coordinate coordenadaImplemento = new Coordinate(rastreamento.getLongitude(), rastreamento.getLatitude());
			
			
			Polygon poligono = getPolygon(cercas);
			if (poligono != null) {
				implementoEstaLocal = poligono.contains(geometryFactory.createPoint(coordenadaImplemento));	
			}
		}
		
		return implementoEstaLocal;
	}
	
	

	public boolean isAtivoEstaPelotasBalancaQuadras() {
		return ativoEstaPelotasBalancaQuadras;
	}

	public void setAtivoEstaPelotasBalancaQuadras(
			boolean ativoEstaPelotasBalancaQuadras) {
		this.ativoEstaPelotasBalancaQuadras = ativoEstaPelotasBalancaQuadras;
	}

	public boolean isAtivoEstaPelotasBarcacaFilasVarricao() {
		return ativoEstaPelotasBarcacaFilasVarricao;
	}
	
	public boolean isAtivoEstavaPelotasBarcaca() {
		return ativoEstavaPelotasBarcaca;
	}

	public void setAtivoEstavaPelotasBarcaca(boolean ativoEstavaPelotasBarcaca) {
		this.ativoEstavaPelotasBarcaca = ativoEstavaPelotasBarcaca;
	}
	
	public void setAtivoEstaPelotasBarcacaFilasVarricao(
			boolean ativoEstaPelotasBarcacaFilasVarricao) {
		this.ativoEstaPelotasBarcacaFilasVarricao = ativoEstaPelotasBarcacaFilasVarricao;
	}

	public boolean isAtivoEstaNoPortoPelotas() {		
		return this.isAtivoEstaPelotasBalancaQuadras() || this.isAtivoEstaPelotasBarcacaFilasVarricao() || this.isAtivoEstaPelotasBarcaca();
	}
	
	public boolean isAtivoEstaNoPortoRioGrande() {
		return this.isAtivoEstaNoRioGrande();
	}


	public boolean isAtivoEstavaNoPortoPelotas() {
		return this.isAtivoEstavaPelotasBalancaQuadras() || this.isAtivoEstavaPelotasBarcacaFilasVarricao() || this.isAtivoEstavaPelotasBarcaca();
	}
	
	public boolean isAtivoEstavaNoPortoRioGrande() {
		return this.isAtivoEstavaNoRioGrande();
	}


	public boolean isAtivoEstavaPelotasBalancaQuadras() {
		return ativoEstavaPelotasBalancaQuadras;
	}

	public void setAtivoEstavaPelotasBalancaQuadras(
			boolean ativoEstavaPelotasBalancaQuadras) {
		this.ativoEstavaPelotasBalancaQuadras = ativoEstavaPelotasBalancaQuadras;
	}

	public boolean isAtivoEstavaPelotasBarcacaFilasVarricao() {
		return ativoEstavaPelotasBarcacaFilasVarricao;
	}

	public void setAtivoEstavaPelotasBarcacaFilasVarricao(
			boolean ativoEstavaPelotasBarcacaFilasVarricao) {
		this.ativoEstavaPelotasBarcacaFilasVarricao = ativoEstavaPelotasBarcacaFilasVarricao;
	}

	public boolean isAtivoEstaFilaTransitoNaFabrica() {
		return ativoEstaFilaTransitoNaFabrica;
	}

	public boolean isModoRealTime() {		
		return modoRealTime;
	}

	public boolean isAtivoEstaFilaInternaNaFabrica() {
		return ativoEstaFilaInternaNaFabrica;
	}

	public boolean isAtivoEstaNaFabricaMesas() {
		return ativoEstaNaFabricaMesas;
	}

	public boolean isAtivoEstaNaFabricaQuadras() {
		return ativoEstaNaFabricaQuadras;
	}

	public boolean isAtivoEstaPelotasBarcaca() {
		return ativoEstaPelotasBarcaca         ;
	}

	public boolean isAtivoEstaPelotasFilas() {
		return ativoEstaPelotasFilas ;
	}

	public boolean isAtivoEstaPelotasQuadras() {
		return ativoEstaPelotasQuadras ;
	}

	public boolean isAtivoEstaNaFabricaMesaL100() {		
		return ativoEstaNaFabricaMesaL100;
	}
	
	public boolean isAtivoEstaNaFabricaMesaL300() {		
		return ativoEstaNaFabricaMesaL300;
	}

	public boolean isAtivoEstaPelotasVarricao() {
		return ativoEstaPelotasVarricao;
	}

	public boolean isAtivoEstavaPelotasVarricao() {
		return ativoEstavaPelotasVarricao;
	}

	public void setAtivoEstavaPelotasVarricao(boolean ativoEstavaPelotasVarricao) {
		this.ativoEstavaPelotasVarricao = ativoEstavaPelotasVarricao;
	}

	public void setAtivoEstaPelotasVarricao(boolean ativoEstaPelotasVarricao) {
		this.ativoEstaPelotasVarricao = ativoEstaPelotasVarricao;
	}

	public void eventsOnFences(CaminhaoEntity caminhao, CERCAS cerca, EventsFenceTrack<CaminhaoEntity> callback) {
		current = caminhao;
		if (isAssetOnOutsideFence(cerca)) {
			callback.onExit(caminhao);
        } else
        if (isAssetOnInsideFence(cerca)) {
        	callback.onEnter(caminhao);
        } else if (isAssetInside(cerca)) {
        	callback.onStayInside(caminhao);
        } else {
        	callback.onStayOutside(caminhao);
        }
		
	}
	
	private boolean isAssetOnInsideFence(CERCAS cerca) {
		return  current.getLatitude() != null && current.getLongitude() != null && current.getPrevLatitude() != null && current.getPrevLongitude() != null 
				&& isAssetInside(cerca) && isAssetWasInside(cerca);
	}

	private CaminhaoEntity current;

	private Boolean isAssetOnOutsideFence(CERCAS cerca) {
        return  current.getLatitude() != null && current.getLongitude() != null && current.getPrevLatitude() != null && current.getPrevLongitude() != null  
        		&& !isAssetInside(cerca) && isAssetWasInside(cerca);
    }

	private boolean isAssetWasInside(CERCAS cerca) {
		if (current.getLatitude() != null && current.getLongitude() != null && current.getPrevLatitude() != null && current.getPrevLongitude() != null) {
			RastreamentoDto rastre = new RastreamentoDto(current.getPrevLatitude(), current.getPrevLongitude());		
			return implementoEstaNoLocal(rastre, cerca);	
		}
				
		return false;
	}

	private boolean isAssetInside(CERCAS cerca) {
		if (current.getLatitude() != null && current.getLongitude() != null && current.getPrevLatitude() != null && current.getPrevLongitude() != null) {
			RastreamentoDto rastre = new RastreamentoDto(current.getLatitude(), current.getLongitude());
			return implementoEstaNoLocal(rastre, cerca);	
		}		
		return false;
	}

	public boolean isStayInCheckpoint(RastreamentoDto track) {
		return implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_ENTRANCE1) 
				|| implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_ENTRANCE2)
				|| implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_ENTRANCE3);
	}

	public boolean isStayInCheckpointEmbedding(RastreamentoDto track) {
		return implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_EMBED_ENTRANCE1) 
				|| implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_EMBED_ENTRANCE2)
				|| implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_EMBED_ENTRANCE3);
	}

	
	public boolean isStayInCheckpointExitEmbedding(RastreamentoDto track) {
		return implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_EMBED_EXIT1) 
				|| implementoEstaNoLocal(track, CERCAS.FACTORY_CHECKPOINT_EMBED_EXIT2);
	}
	
	public boolean isStayInEntrance(RastreamentoDto rastre) {
		
		return false;
	}
}

package br.com.crearesistemas.service.dto;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.EstagioOperacional;
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;
import br.com.crearesistemas.util.DateUtils;

public class TriggerEventosDto {

	// estados atuais
	EstadoOperacional estadoSite;
	EstadoOperacional estadoTransp;
	EstadoOperacional estadoCarreg;
	EstadoOperacional estadoProprio;
	Boolean isMesaOuQuadra;
	
	// ot 
	Long otId;
	String otNumero;
	SituacaoOrdemTransporte otSituacao;
	Date otDataLiberacao;

	
	Date 				tr01DataCercaSF;
	RastreamentoDto 	tr01RastreamentoSF;
	
	Date 				tr02DataCercaAE;
	RastreamentoDto 	tr02RastreamentoAE;
	
	Date	 			tr03dataAptFB;
	ApontamentoDto 		tr03ApontamentoFB;
	
	// interface 39 (recebimento)
	Date   				tr04DataEntradaBE;
	String 				tr04IdEntradaBE;
	
	
	Date   				tr04DataRecebimentoBE;
	String 				tr04IdBalancaEntradaBE;
	
	// interface 35 (reprogramação)
	Date 				tr05DataReprogramacaoFI;
	Long 				tr05PileCodeFI;
	Long 				tr05ChipperCodeFI;
	
	
	// data da mesa
	Date 				tr06DataMesa;
	ApontamentoDto 		tr06ApontamentoMesa;
	
	
	// cerca chekpoint saída
	Date 				tr11DataCercaCS;
	RastreamentoDto 	tr11RastreamentoCS;

	// data da varrição
	Date 				tr12DataVarricao;
	ApontamentoDto 		tr12ApontamentoVarricao;
	
	// interface 40 (saida)
	Date 				tr13DataFimViagem;
	String 				tr13IdBalancaSaida;

	// fim descarga (limpar se for aberto um novo carregamento)
	Date 				tr14DataFimDescarga;
	String 				tr14Grua;

	
	// apontamento
	Date 				tr15StandBy;
	ApontamentoDto 		tr15ApontamentoStandBy;
	
	// patio
	Date 				tr16DataQuadra;
	ApontamentoDto 		tr16ApontamentoQuadra;
	
	
	public Pair<EstadoOperacional, EstagioOperacional> getStateStoreResult(Boolean isStayInFactory) {
		Pair<EstadoOperacional, EstagioOperacional> res = null;
		
		Config config = Config.getInstance();
		
		Date trRecentDate = null;
		
		
		
		if (!isMesaOuQuadra && otSituacao == SituacaoOrdemTransporte.EXECUTADA) {
			if (tr13DataFimViagem != null) {
				if (DateUtils.compareWithPrevious(trRecentDate, tr13DataFimViagem)) {
					trRecentDate = tr13DataFimViagem;
					res = Pair.of(EstadoOperacional.SI_VARRICAO, null);
				}
			}
			
			if (tr11DataCercaCS != null) {
				if (DateUtils.compareWithPrevious(trRecentDate, tr11DataCercaCS)) {
					trRecentDate = tr11DataCercaCS;
					res = Pair.of(EstadoOperacional.SI_CHECKPOINT_SAIDA, null);	
				}
			}
			
			return res;
		}
		
		
		if (config.pcsiOnlyOrders()) {
			if (!isMesaOuQuadra && (otDataLiberacao == null || otDataLiberacao != null && otSituacao != SituacaoOrdemTransporte.LIBERADA)) {
				return null;
				//res = Pair.of(EstadoOperacional.TRANSP_SEM_OT, null);				
			}
		}
		
		if (tr01DataCercaSF != null) {
			if (DateUtils.compareWithPrevious(trRecentDate, tr01DataCercaSF)) {
				trRecentDate = tr01DataCercaSF;
				if (estadoCarreg != null && estadoCarreg == EstadoOperacional.CARREG_CHEIO) {
					res = Pair.of(EstadoOperacional.SI_SENTIDO_FABRICA, null);	
				} else {
					res = Pair.of(EstadoOperacional.SI_CHECKPOINT_SAIDA, null);
				}					
			}
		}
		
		if (tr02DataCercaAE != null) {
			if (DateUtils.compareWithPrevious(trRecentDate, tr02DataCercaAE)) {
				trRecentDate = tr02DataCercaAE;
				
				if (estadoCarreg != null && estadoCarreg == EstadoOperacional.CARREG_CHEIO) {
					res = Pair.of(EstadoOperacional.SI_ACESSO_ENTRADA, null);	
				}
				
			}
		}
		
		if (tr03dataAptFB != null && estadoCarreg != null &&  estadoCarreg == EstadoOperacional.CARREG_CHEIO) {
			if (DateUtils.compareWithPrevious(trRecentDate, tr03dataAptFB)) {
				trRecentDate = tr03dataAptFB;
				res = Pair.of(EstadoOperacional.SI_FILA_BALANCA_ENTRADA, null);
			}
		}
		
		if (tr04DataEntradaBE != null) {
			if (DateUtils.compareWithPrevious(trRecentDate, tr04DataEntradaBE)) {
				trRecentDate = tr04DataEntradaBE;
				res = Pair.of(EstadoOperacional.SI_BALANCA_ENTRADAB, null);
			}
		}
		
		if (tr04DataRecebimentoBE != null) {
			// recebimento possui prioridade?
			Boolean trRecebTemPrioridade =  true;			
			if (trRecebTemPrioridade &&  !isStayInFactory && estadoSite != null && estadoSite == EstadoOperacional.SI_FILA) {
				//System.out.println("----------------------tr04DataRecebimentoBE----------------------");
				//if (tr05DataReprogramacaoFI != null) {
					if (tr05ChipperCodeFI != null) {
							EstagioOperacional estagio = config.getEstagioFilaByChipperCode(tr05ChipperCodeFI);
							res = Pair.of(EstadoOperacional.SI_FILA, estagio);
					} else					
					if (tr05PileCodeFI != null) {
						res = Pair.of(EstadoOperacional.SI_FILA, EstagioOperacional.FILA_QUADRAS);
					} else {
						res = Pair.of(EstadoOperacional.SI_FILA, EstagioOperacional.FILA_MESA1);
					}
				//} else {
				//	res = Pair.of(EstadoOperacional.SI_FILA, EstagioOperacional.FILA_MESA1);
				//}
				return res;
			} else			
			if (DateUtils.compareWithPrevious(trRecentDate, tr04DataRecebimentoBE)) {
				trRecentDate = tr04DataRecebimentoBE;
				//if (tr05DataReprogramacaoFI != null) {
					if (tr05ChipperCodeFI != null) {
							EstagioOperacional estagio = config.getEstagioFilaByChipperCode(tr05ChipperCodeFI);
							res = Pair.of(EstadoOperacional.SI_FILA, estagio);
					} else					
					if (tr05PileCodeFI != null) {
						res = Pair.of(EstadoOperacional.SI_FILA, EstagioOperacional.FILA_QUADRAS);
					} else {
						res = Pair.of(EstadoOperacional.SI_FILA, EstagioOperacional.FILA_MESA1);
					}
				//} else {
				//	res = Pair.of(EstadoOperacional.SI_FILA, EstagioOperacional.FILA_MESA1);
				//}
			}
		}
		
		
		
		if (tr06DataMesa != null && estadoSite != null && estadoSite == EstadoOperacional.SI_MESAS) {
			if (tr06ApontamentoMesa != null && tr06ApontamentoMesa.getEventLocalId() != null) {
				EstagioOperacional estagio = config.getEstagioMesaByLocalId(tr06ApontamentoMesa.getEventLocalId());						
				if (estagio != null) {
					trRecentDate = tr06DataMesa;
					res = Pair.of(EstadoOperacional.SI_MESAS, estagio);	
					return res;
				}	
			}
			
			/*if (compareWithPrevious(trRecentDate, tr06DataMesa)) {
				if (tr06ApontamentoMesa != null && tr06ApontamentoMesa.getEventLocalId() != null) {
					EstagioOperacional estagio = config.getEstagioMesaByLocalId(tr06ApontamentoMesa.getEventLocalId());						
					if (estagio != null) {
						trRecentDate = tr06DataMesa;
						res = Pair.of(EstadoOperacional.SI_MESAS, estagio);	
					}	
				}
			}*/
		}
		
		/*
		if (tr11DataCercaCS != null) {
			if (compareWithPrevious(trRecentDate, tr11DataCercaCS)) {
				trRecentDate = tr11DataCercaCS;
				res = Pair.of(EstadoOperacional.SI_CHECKPOINT_SAIDA, null);	
			}
		}
	
		*/
		
		/*if (tr12DataVarricao != null) {
			if (compareWithPrevious(trRecentDate, tr12DataVarricao)) {
				trRecentDate = tr12DataVarricao;
				res = Pair.of(EstadoOperacional.SI_VARRICAO, null);
			}
		}*/
		
		/*
		if (tr13DataFimViagem != null) {
			if (compareWithPrevious(trRecentDate, tr13DataFimViagem)) {
				trRecentDate = tr13DataFimViagem;
				res = Pair.of(EstadoOperacional.SI_VARRICAO, null);
			}
		}
		*/
		
		
		if (tr14DataFimDescarga != null) {
			if (DateUtils.compareWithPrevious(trRecentDate, tr14DataFimDescarga)) {
				trRecentDate = tr14DataFimDescarga;
				res = Pair.of(EstadoOperacional.SI_FILA_BALANCA_SAIDA, null);	
			}
		}
		
		
		if (tr16DataQuadra != null && estadoSite != null && estadoSite == EstadoOperacional.SI_QUADRAS) {
			if (tr16ApontamentoQuadra != null && tr16ApontamentoQuadra.getEventLocalId() != null) {
				EstagioOperacional estagio = config.getEstagioQuadraByLocalId(tr16ApontamentoQuadra.getEventLocalId());						
				if (estagio != null) {
					trRecentDate = tr16DataQuadra;					
					res = Pair.of(EstadoOperacional.SI_QUADRAS, estagio);
					return res;
				}	
			}
		}
		
		
		
		return res;
	}
	
	
	
	


	public Long getOtId() {
		return otId;
	}
	public void setOtId(Long otId) {
		this.otId = otId;
	}
	public String getOtNumero() {
		return otNumero;
	}
	public void setOtNumero(String otNumero) {
		this.otNumero = otNumero;
	}
	public SituacaoOrdemTransporte getOtSituacao() {
		return otSituacao;
	}
	public void setOtSituacao(SituacaoOrdemTransporte otSituacao) {
		this.otSituacao = otSituacao;
	}
	public Date getTr01DataCercaSF() {
		return tr01DataCercaSF;
	}
	public void setTr01DataCercaSF(Date tr01DataCercaSF) {
		this.tr01DataCercaSF = tr01DataCercaSF;
	}
	public RastreamentoDto getTr01RastreamentoSF() {
		return tr01RastreamentoSF;
	}
	public void setTr01RastreamentoSF(RastreamentoDto tr01RastreamentoSF) {
		this.tr01RastreamentoSF = tr01RastreamentoSF;
	}
	public Date getTr02DataCercaAE() {
		return tr02DataCercaAE;
	}
	public void setTr02DataCercaAE(Date tr02DataCercaAE) {
		this.tr02DataCercaAE = tr02DataCercaAE;
	}
	public RastreamentoDto getTr02RastreamentoAE() {
		return tr02RastreamentoAE;
	}
	public void setTr02RastreamentoAE(RastreamentoDto tr02RastreamentoAE) {
		this.tr02RastreamentoAE = tr02RastreamentoAE;
	}
	public Date getTr03dataAptFB() {
		return tr03dataAptFB;
	}
	public void setTr03dataAptFB(Date tr03dataAptFB) {
		this.tr03dataAptFB = tr03dataAptFB;
	}
	public ApontamentoDto getTr03ApontamentoFB() {
		return tr03ApontamentoFB;
	}
	public void setTr03ApontamentoFB(ApontamentoDto tr03ApontamentoFB) {
		this.tr03ApontamentoFB = tr03ApontamentoFB;
	}
	public Date getTr04DataRecebimentoBE() {
		return tr04DataRecebimentoBE;
	}
	public void setTr04DataRecebimentoBE(Date tr04DataRecebimentoBE) {
		this.tr04DataRecebimentoBE = tr04DataRecebimentoBE;
	}
	public String getTr04IdBalancaEntradaBE() {
		return tr04IdBalancaEntradaBE;
	}
	public void setTr04IdBalancaEntradaBE(String tr04IdBalancaEntradaBE) {
		this.tr04IdBalancaEntradaBE = tr04IdBalancaEntradaBE;
	}
	public Date getTr05DataReprogramacaoFI() {
		return tr05DataReprogramacaoFI;
	}
	public void setTr05DataReprogramacaoFI(Date tr05DataReprogramacaoFI) {
		this.tr05DataReprogramacaoFI = tr05DataReprogramacaoFI;
	}
	public Long getTr05PileCodeFI() {
		return tr05PileCodeFI;
	}
	public void setTr05PileCodeFI(Long tr05PileCodeFI) {
		this.tr05PileCodeFI = tr05PileCodeFI;
	}
	public Long getTr05ChipperCodeFI() {
		return tr05ChipperCodeFI;
	}
	public void setTr05ChipperCodeFI(Long tr05ChipperCodeFI) {
		this.tr05ChipperCodeFI = tr05ChipperCodeFI;
	}
	public Date getTr06DataMesa() {
		return tr06DataMesa;
	}
	public void setTr06DataMesa(Date tr06DataMesa) {
		this.tr06DataMesa = tr06DataMesa;
	}
	public ApontamentoDto getTr06ApontamentoMesa() {
		return tr06ApontamentoMesa;
	}
	public void setTr06ApontamentoMesa(ApontamentoDto tr06ApontamentoMesa) {
		this.tr06ApontamentoMesa = tr06ApontamentoMesa;
	}
	public Date getTr11DataCercaCS() {
		return tr11DataCercaCS;
	}
	public void setTr11DataCercaCS(Date tr11DataCercaCS) {
		this.tr11DataCercaCS = tr11DataCercaCS;
	}
	public RastreamentoDto getTr11RastreamentoCS() {
		return tr11RastreamentoCS;
	}
	public void setTr11RastreamentoCS(RastreamentoDto tr11RastreamentoCS) {
		this.tr11RastreamentoCS = tr11RastreamentoCS;
	}
	
	public Date getTr12DataVarricao() {
		return tr12DataVarricao;
	}
	public void setTr12DataVarricao(Date tr12DataVarricao) {
		this.tr12DataVarricao = tr12DataVarricao;
	}
	public ApontamentoDto getTr12ApontamentoVarricao() {
		return tr12ApontamentoVarricao;
	}
	public void setTr12ApontamentoVarricao(ApontamentoDto tr12ApontamentoVarricao) {
		this.tr12ApontamentoVarricao = tr12ApontamentoVarricao;
	}
	public Date getTr13DataFimViagem() {
		return tr13DataFimViagem;
	}
	public void setTr13DataFimViagem(Date tr13DataFimViagem) {
		this.tr13DataFimViagem = tr13DataFimViagem;
	}
	public String getTr13IdBalancaSaida() {
		return tr13IdBalancaSaida;
	}
	public void setTr13IdBalancaSaida(String tr13IdBalancaSaida) {
		this.tr13IdBalancaSaida = tr13IdBalancaSaida;
	}
	public Date getTr15StandBy() {
		return tr15StandBy;
	}
	public void setTr15StandBy(Date tr15StandBy) {
		this.tr15StandBy = tr15StandBy;
	}
	public ApontamentoDto getTr15ApontamentoStandBy() {
		return tr15ApontamentoStandBy;
	}
	public void setTr15ApontamentoStandBy(ApontamentoDto tr15ApontamentoStandBy) {
		this.tr15ApontamentoStandBy = tr15ApontamentoStandBy;
	}
	public Date getTr16DataQuadra() {
		return tr16DataQuadra;
	}
	public void setTr16DataQuadra(Date tr16DataQuadra) {
		this.tr16DataQuadra = tr16DataQuadra;
	}
	public ApontamentoDto getTr16ApontamentoQuadra() {
		return tr16ApontamentoQuadra;
	}
	public void setTr16ApontamentoQuadra(ApontamentoDto tr16ApontamentoQuadra) {
		this.tr16ApontamentoQuadra = tr16ApontamentoQuadra;
	}

	public Date getOtDataLiberacao() {
		return otDataLiberacao;
	}

	public void setOtDataLiberacao(Date otDataLiberacao) {
		this.otDataLiberacao = otDataLiberacao;
	}



	public EstadoOperacional getEstadoSite() {
		return estadoSite;
	}



	public void setEstadoSite(EstadoOperacional estadoSite) {
		this.estadoSite = estadoSite;
	}



	public EstadoOperacional getEstadoTransp() {
		return estadoTransp;
	}



	public void setEstadoTransp(EstadoOperacional estadoTransp) {
		this.estadoTransp = estadoTransp;
	}



	public EstadoOperacional getEstadoCarreg() {
		return estadoCarreg;
	}



	public void setEstadoCarreg(EstadoOperacional estadoCarreg) {
		this.estadoCarreg = estadoCarreg;
	}



	public Date getTr04DataEntradaBE() {
		return tr04DataEntradaBE;
	}



	public void setTr04DataEntradaBE(Date tr04DataEntradaBE) {
		this.tr04DataEntradaBE = tr04DataEntradaBE;
	}



	public String getTr04IdEntradaBE() {
		return tr04IdEntradaBE;
	}



	public void setTr04IdEntradaBE(String tr04IdEntradaBE) {
		this.tr04IdEntradaBE = tr04IdEntradaBE;
	}



	


	public EstadoOperacional getEstadoProprio() {
		return estadoProprio;
	}



	public void setEstadoProprio(EstadoOperacional estadoProprio) {
		this.estadoProprio = estadoProprio;
	}



	public Date getTr14DataFimDescarga() {
		return tr14DataFimDescarga;
	}



	public void setTr14DataFimDescarga(Date tr14DataFimDescarga) {
		this.tr14DataFimDescarga = tr14DataFimDescarga;
	}



	public String getTr14Grua() {
		return tr14Grua;
	}



	public void setTr14Grua(String tr14Grua) {
		this.tr14Grua = tr14Grua;
	}



	public Boolean getIsMesaOuQuadra() {
		return isMesaOuQuadra;
	}



	public void setIsMesaOuQuadra(Boolean isMesaOuQuadra) {
		this.isMesaOuQuadra = isMesaOuQuadra;
	}






	
	

	
}

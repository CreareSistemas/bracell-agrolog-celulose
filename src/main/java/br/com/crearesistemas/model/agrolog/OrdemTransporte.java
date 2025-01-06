package br.com.crearesistemas.model.agrolog;

import java.util.Date;

import br.com.crearesistemas.enumeration.ModoProgramacao;
import br.com.crearesistemas.enumeration.SituacaoOrdemTransporte;
import br.com.crearesistemas.model.Apontamento;
import br.com.crearesistemas.model.Local;



public class OrdemTransporte {

	private Long id;
	
	private Long idProjeto;
	
	private String projeto;
	
	private Long idPrestador;
    
	private String prestador;
    
	

	
	private Date dataHistorico;
	
	private String integracao;
	
	private String documento;
	
	private SituacaoOrdemTransporte situacao;
	
	private OrdemTransporte original;
	
	private Implemento implementoTransporte;
	
	private Implemento implementoCarga;

	private Implemento implementoDescarga;

	private Apontamento apontamentoCarga;
	
	private Apontamento apontamentoDescarga;
	
	private Local localCarga;
	
	private Estoque estoqueCarga;
	
	private Local localDescarga;
	
	private Estoque estoqueDescarga;
	
	// data vigência truncada da ot
	private Date dataReferencia;
	
	// data do asicam de programacao de partida (cancela?)
	private Date dataProgramadaPartida;

	// data prevista para sair da cancela
	private Date dataPrevistaPartida;

	
	// data programada de chegada do asicam na cancela de entrada 
	private Date dataProgramadaChegada;
	
	// data prevista para entrar na cancela
	private Date dataPrevistaChegada;
	
	// data projetada de chegada (estimado pelo sistema) na cancela de entrada 
	private Date dataEstimadaChegada;

	// data prevista de chegada na fazenda (do sistema)
	private Date dataPrevistaDestino;

	// [efetiva 01] - data da ot com guia de cem liberada na balanca saida
	private Date dataLiberacao;
	
	// [efetiva 02] - data efetiva de saida da cancela
	private Date dataPartida;		
		
	// [efetiva 03] - data que entra na fila da grua
	private Date dataFila;

	// [efetiva 04] - data efetiva de chegada na fazenda (na grua de destino)
	private Date dataDestino;
	
	// [efetiva 05] - data efetiva de saida da grua de campo
	private Date dataRetorno;
	
	// [efetiva 06] - data efetiva de chegada na cancela
	private Date dataChegada;
		
	// [efetiva 07] - data efetiva de recebimento na balanca de entrada
	private Date dataRecebimento;

	// [efetiva 08] - data efetiva de finalização
	private Date dataConclusao;

	private Float tara;
	
	private Float pesoBruto;
	
	private Float pesoLiquido;
	
	private Float volumePrevisto;
	
	private Float volumeApurado;
	
	private Float horasPrevistasViagemVazio;

	private Float horasPrevistasViagemCheio;

	private Float quilometros;
	
	private Boolean calibracao = false;
	
	private String idRotograma;
	
	private String idBalancaEntrada;
	
	private String idBalancaSaida;

	private ModoProgramacao modoProgramacao;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public String getIntegracao() {
		return integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public SituacaoOrdemTransporte getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoOrdemTransporte situacao) {
		this.situacao = situacao;
	}

	public OrdemTransporte getOriginal() {
		return original;
	}

	public void setOriginal(OrdemTransporte original) {
		this.original = original;
	}

	public Implemento getImplementoTransporte() {
		return implementoTransporte;
	}

	public void setImplementoTransporte(Implemento implementoTransporte) {
		this.implementoTransporte = implementoTransporte;
	}

	public Implemento getImplementoCarga() {
		return implementoCarga;
	}

	public void setImplementoCarga(Implemento implementoCarga) {
		this.implementoCarga = implementoCarga;
	}

	public Implemento getImplementoDescarga() {
		return implementoDescarga;
	}

	public void setImplementoDescarga(Implemento implementoDescarga) {
		this.implementoDescarga = implementoDescarga;
	}

	public Apontamento getApontamentoCarga() {
		return apontamentoCarga;
	}

	public void setApontamentoCarga(Apontamento apontamentoCarga) {
		this.apontamentoCarga = apontamentoCarga;
	}

	public Apontamento getApontamentoDescarga() {
		return apontamentoDescarga;
	}

	public void setApontamentoDescarga(Apontamento apontamentoDescarga) {
		this.apontamentoDescarga = apontamentoDescarga;
	}

	public Local getLocalCarga() {
		return localCarga;
	}

	public void setLocalCarga(Local localCarga) {
		this.localCarga = localCarga;
	}

	public Estoque getEstoqueCarga() {
		return estoqueCarga;
	}

	public void setEstoqueCarga(Estoque estoqueCarga) {
		this.estoqueCarga = estoqueCarga;
	}

	public Local getLocalDescarga() {
		return localDescarga;
	}

	public void setLocalDescarga(Local localDescarga) {
		this.localDescarga = localDescarga;
	}

	public Estoque getEstoqueDescarga() {
		return estoqueDescarga;
	}

	public void setEstoqueDescarga(Estoque estoqueDescarga) {
		this.estoqueDescarga = estoqueDescarga;
	}

	public Date getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(Date dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public Date getDataProgramadaPartida() {
		return dataProgramadaPartida;
	}

	public void setDataProgramadaPartida(Date dataProgramadaPartida) {
		this.dataProgramadaPartida = dataProgramadaPartida;
	}

	public Date getDataPrevistaPartida() {
		return dataPrevistaPartida;
	}

	public void setDataPrevistaPartida(Date dataPrevistaPartida) {
		this.dataPrevistaPartida = dataPrevistaPartida;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Date getDataProgramadaChegada() {
		return dataProgramadaChegada;
	}

	public void setDataProgramadaChegada(Date dataProgramadaChegada) {
		this.dataProgramadaChegada = dataProgramadaChegada;
	}

	public Date getDataPrevistaChegada() {
		return dataPrevistaChegada;
	}

	public void setDataPrevistaChegada(Date dataPrevistaChegada) {
		this.dataPrevistaChegada = dataPrevistaChegada;
	}

	public Date getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Date dataChegada) {
		this.dataChegada = dataChegada;
	}

	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public Date getDataFila() {
		return dataFila;
	}

	public void setDataFila(Date dataFila) {
		this.dataFila = dataFila;
	}

	public Date getDataPrevistaDestino() {
		return dataPrevistaDestino;
	}

	public void setDataPrevistaDestino(Date dataPrevistaDestino) {
		this.dataPrevistaDestino = dataPrevistaDestino;
	}

	public Date getDataEstimadaChegada() {
		return dataEstimadaChegada;
	}

	public void setDataEstimadaChegada(Date dataEstimadaChegada) {
		this.dataEstimadaChegada = dataEstimadaChegada;
	}

	public Date getDataDestino() {
		return dataDestino;
	}

	public void setDataDestino(Date dataDestino) {
		this.dataDestino = dataDestino;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
		
	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public Float getTara() {
		return tara;
	}

	public void setTara(Float tara) {
		this.tara = tara;
	}

	public Float getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Float pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public Float getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(Float pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public Float getVolumePrevisto() {
		return volumePrevisto;
	}

	public void setVolumePrevisto(Float volumePrevisto) {
		this.volumePrevisto = volumePrevisto;
	}

	public Float getVolumeApurado() {
		return volumeApurado;
	}

	public void setVolumeApurado(Float volumeApurado) {
		this.volumeApurado = volumeApurado;
	}

	public Float getHorasPrevistasViagemVazio() {
		return horasPrevistasViagemVazio;
	}

	public void setHorasPrevistasViagemVazio(Float horasPrevistasViagemVazio) {
		this.horasPrevistasViagemVazio = horasPrevistasViagemVazio;
	}

	public Float getHorasPrevistasViagemCheio() {
		return horasPrevistasViagemCheio;
	}

	public void setHorasPrevistasViagemCheio(Float horasPrevistasViagemCheio) {
		this.horasPrevistasViagemCheio = horasPrevistasViagemCheio;
	}

	public Float getQuilometros() {
		return quilometros;
	}

	public void setQuilometros(Float quilometros) {
		this.quilometros = quilometros;
	}

	public String getIdRotograma() {
		return idRotograma;
	}

	public void setIdRotograma(String idRotograma) {
		this.idRotograma = idRotograma;
	}

	public Boolean getCalibracao() {
		return calibracao;
	}

	public void setCalibracao(Boolean calibracao) {
		this.calibracao = calibracao;
	}

	public String getIdBalancaEntrada() {
		return idBalancaEntrada;
	}

	public void setIdBalancaEntrada(String idBalancaEntrada) {
		this.idBalancaEntrada = idBalancaEntrada;
	}

	public String getIdBalancaSaida() {
		return idBalancaSaida;
	}

	public void setIdBalancaSaida(String idBalancaSaida) {
		this.idBalancaSaida = idBalancaSaida;
	}

	public ModoProgramacao getModoProgramacao() {
		return modoProgramacao;
	}

	public void setModoProgramacao(ModoProgramacao modoProgramacao) {
		this.modoProgramacao = modoProgramacao;
	}

	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

	public Long getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Long idPrestador) {
		this.idPrestador = idPrestador;
	}

	public String getPrestador() {
		return prestador;
	}

	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}

}
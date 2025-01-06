package br.com.crearesistemas.pcsil1.wsrecebetemposmediosciclointerno.dto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.stax2.ri.typed.NumberUtil;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.model.EntidadeProprietaria;
import br.com.crearesistemas.util.NumberUtils;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class TempoMedioCicloInterno {
	public static enum  TipoCalculo {
		DESCONHECIDO, PM_MEDIA_BARCACA_QUADRAS, SI_MEDIA_MESAS_QUADRAS
	}	
	@XmlElement(required = true)
	private Long idPrestador;
	
	@XmlElement(required = true)
	private String prestador;
	
	@XmlElement(required = true)
	private String grupo;
	
	@XmlElement(required = true)
	private Integer acessoPrivadoEntrada;
	
	@XmlElement(required = true)
	private Integer controleAcesso;
	
	@XmlElement(required = true)
	private Integer balancaEntrada;
	
	@XmlElement(required = true)
	private Integer bufferViaduto;
	
	@XmlElement(required = true)
	private Integer filaExterna;
	
	@XmlElement(required = true)
	private Integer filaInterna;
	
	@XmlElement(required = true)
	private Integer quadras;
	
	@XmlElement(required = true)
	private Integer fila;
	
	@XmlElement(required = true)
	private Integer espera;
	
	@XmlElement(required = true)
	private Integer mesas;
	
	@XmlElement(required = true)
	private Integer patio;
	
	@XmlElement(required = true)
	private Integer balancaSaida;
	
	@XmlElement(required = true)
	private Integer varricao;
	
	@XmlElement(required = true)
	private Integer acessoPrivadoSaida;
	
	@XmlElement(required = true)
	private Integer total;
	
	@XmlElement
	private Integer barcacas;

	@XmlElement
	private Integer mediaLocalDescarga;
	
	@XmlElement
	private Integer patioBalanca;
	
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
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public Integer getAcessoPrivadoEntrada() {
		return acessoPrivadoEntrada;
	}
	public void setAcessoPrivadoEntrada(Integer acessoPrivadoEntrada) {
		this.acessoPrivadoEntrada = acessoPrivadoEntrada;
	}
	public Integer getControleAcesso() {
		return controleAcesso;
	}
	public void setControleAcesso(Integer controleAcesso) {
		this.controleAcesso = controleAcesso;
	}
	public Integer getBalancaEntrada() {
		return balancaEntrada;
	}
	public void setBalancaEntrada(Integer balancaEntrada) {
		this.balancaEntrada = balancaEntrada;
	}
	public Integer getBufferViaduto() {
		return bufferViaduto;
	}
	public void setBufferViaduto(Integer bufferViaduto) {
		this.bufferViaduto = bufferViaduto;
	}
	public Integer getFilaExterna() {
		return filaExterna;
	}
	public void setFilaExterna(Integer filaExterna) {
		this.filaExterna = filaExterna;
	}
	public Integer getFilaInterna() {
		return filaInterna;
	}
	public void setFilaInterna(Integer filaInterna) {
		this.filaInterna = filaInterna;
	}
	public Integer getQuadras() {
		return quadras;
	}
	public void setQuadras(Integer quadras) {
		this.quadras = quadras;
	}
	public Integer getFila() {
		return fila;
	}
	public void setFila(Integer fila) {
		this.fila = fila;
	}
	public Integer getEspera() {
		return espera;
	}
	public void setEspera(Integer espera) {
		this.espera = espera;
	}
	public Integer getMesas() {
		return mesas;
	}
	public void setMesas(Integer mesas) {
		this.mesas = mesas;
	}
	public Integer getPatio() {
		return patio;
	}
	public void setPatio(Integer patio) {
		this.patio = patio;
	}
	public Integer getBalancaSaida() {
		return balancaSaida;
	}
	public void setBalancaSaida(Integer balancaSaida) {
		this.balancaSaida = balancaSaida;
	}
	public Integer getVarricao() {
		return varricao;
	}
	public void setVarricao(Integer varricao) {
		this.varricao = varricao;
	}
	public Integer getAcessoPrivadoSaida() {
		return acessoPrivadoSaida;
	}
	public void setAcessoPrivadoSaida(Integer acessoPrivadoSaida) {
		this.acessoPrivadoSaida = acessoPrivadoSaida;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
		
	public Integer getBarcacas() {
		return barcacas;
	}
	public void setBarcacas(Integer barcacas) {
		this.barcacas = barcacas;
	}
	
	public Integer getMediaLocalDescarga() {
		return mediaLocalDescarga;
	}
	public void setMediaLocalDescarga(Integer mediaLocalDescarga) {
		this.mediaLocalDescarga = mediaLocalDescarga;
	}
	
	public Integer getPatioBalanca() {
		return patioBalanca;
	}
	public void setPatioBalanca(Integer patioBalanca) {
		this.patioBalanca = patioBalanca;
	}
	public static TempoMedioCicloInterno convertFromMapEntry(
			Map.Entry<Long, HashMap<EstadoOperacional, Integer>> entry,
			EntidadeProprietaria entidade)
	{
		TempoMedioCicloInterno _ret = new TempoMedioCicloInterno();
		
		_ret.setGrupo("Geral");
		
		_ret.setIdPrestador(entidade.getId());
		_ret.setPrestador(entidade.getDescricao());
		
		Integer total = 0;
		Map<EstadoOperacional, Integer> tempoDict = entry.getValue();
		for(Map.Entry<EstadoOperacional, Integer> tempo : 
			tempoDict.entrySet())
		{
			EstadoOperacional estado = tempo.getKey();
			Integer duracao = tempo.getValue();
			
			
			switch (estado) 
			{
			    
				case SI_SENTIDO_FABRICA:
					total += duracao;
					_ret.acessoPrivadoEntrada = duracao;
				break;
				
				case SI_ACESSO_ENTRADA:
					total += duracao;
					_ret.controleAcesso = duracao;
				break;
				
				// fila balanca
				case SI_FILA_BALANCA_ENTRADA:
					total += duracao;
					_ret.filaExterna = duracao;
				break;
				
				// fila fabrica				
				case SI_FILA:
					total += duracao;
					_ret.fila = duracao;
				break;

				
				// quadras
				case SI_QUADRAS:
					total += duracao;
					_ret.quadras = duracao;
				break;
				
				case SI_MESAS:
					total += duracao;
					_ret.mesas = duracao;
				break;
				
				case SI_FILA_BALANCA_SAIDA:
					total += duracao;
					_ret.filaInterna = duracao;
				break;
				
				case SI_VARRICAO:
					total += duracao;
					_ret.varricao = duracao;
				break;
				
				
				case SI_CHECKPOINT_SAIDA:
					total += duracao;
					_ret.acessoPrivadoSaida = duracao;
				break;
	
				default:
					break;
			}		
		}
		_ret.setTotal(total);
		
		return _ret;
	}
	
	public static TempoMedioCicloInterno convertFromMapEntryPorto(
			Map.Entry<Long, HashMap<EstadoOperacional, Integer>> entry,
			EntidadeProprietaria entidade)
	{
		TempoMedioCicloInterno _ret = new TempoMedioCicloInterno();
		
		_ret.setGrupo("Geral");
		
		_ret.setIdPrestador(entidade.getId());
		_ret.setPrestador(entidade.getDescricao());
		
		Integer total = 0;
		Map<EstadoOperacional, Integer> tempoDict = entry.getValue();
		for(Map.Entry<EstadoOperacional, Integer> tempo : 
			tempoDict.entrySet())
		{
			EstadoOperacional estado = tempo.getKey();
			Integer duracao = tempo.getValue();
			
			
			switch (estado) 
			{
				case PM_ENTRADA:
					_ret.acessoPrivadoEntrada = duracao;
				break;
				
				case PM_BALANCA_ENTRADA:
					_ret.balancaEntrada = duracao;
				break;
								
				// fila origem fazendas
				case PM_FILAS:
					total += duracao;
					_ret.filaExterna = duracao;
				break;
				
				// fila origem patio ??
				case SI_FILA_INTERNA:
					total += duracao;
					_ret.filaInterna = duracao;
				break;
				
				// quadras
				case PM_QUADRAS:
					//total += duracao;
					_ret.quadras = duracao;
				break;
				
				// barcacas
				case PM_BARCACA:
					//total += duracao;
					_ret.barcacas = duracao;
				break;
				
				case PM_ATIVOS_DISPONIVEIS:
					total += duracao;
					_ret.patio = duracao;
				break;
				
				case PM_BALANCA_SAIDA:
					total += duracao;
					_ret.balancaSaida = duracao;
				break;
				
				case PM_VARRICAO:
					_ret.varricao = duracao;
				break;
				
				case PM_SAIDA:
					_ret.acessoPrivadoSaida = duracao;
				break;
				
	
				default:
					break;
			}		
		}
		_ret.setTotal(total);
		
		return _ret;
	}
	
	
	
	public static TempoMedioCicloInterno convertFromMapEntryCalcPorto(TempoMedioCicloInterno _t, Map<Long, HashMap<TipoCalculo, Integer>> calcDestinoDictByEntity) {

		for(Map.Entry<Long, HashMap<TipoCalculo, Integer>> entry : 
			calcDestinoDictByEntity.entrySet())
		{
			
			Long idPrestadorEntry 	= entry.getKey();
			Long idPrestador 		= _t.getIdPrestador();
			
			if (idPrestadorEntry == idPrestador) {
				Integer total = _t.getTotal();
				Map<TipoCalculo, Integer> tempoDict = entry.getValue();
				for(Map.Entry<TipoCalculo, Integer> tempo : 
					tempoDict.entrySet())
				{
					TipoCalculo calculo = tempo.getKey();
					Integer 	duracao = tempo.getValue();
					
					switch (calculo) 
					{
						case PM_MEDIA_BARCACA_QUADRAS:
							total += duracao;
							_t.mediaLocalDescarga = duracao;
						break;
						
						default:
						break;
					}		
				}
				_t.setTotal(total);
			}
		}
		
		return _t;
	}
	
	public static TempoMedioCicloInterno convertFromMapEntryCalc(TempoMedioCicloInterno _t, Map<Long, HashMap<TipoCalculo, Integer>> calcDestinoDictByEntity) {

		for(Map.Entry<Long, HashMap<TipoCalculo, Integer>> entry : 
			calcDestinoDictByEntity.entrySet())
		{
			
			Long idPrestadorEntry 	= entry.getKey();
			Long idPrestador 		= _t.getIdPrestador();
			
			if (idPrestadorEntry == idPrestador) {
				Integer total = _t.getTotal();
				Map<TipoCalculo, Integer> tempoDict = entry.getValue();
				for(Map.Entry<TipoCalculo, Integer> tempo : 
					tempoDict.entrySet())
				{
					TipoCalculo calculo = tempo.getKey();
					Integer 	duracao = tempo.getValue();
					
					switch (calculo) 
					{
						case SI_MEDIA_MESAS_QUADRAS:
							total += duracao;
							_t.mediaLocalDescarga = duracao;
						break;

						
						default:
						break;
					}		
				}
				_t.setTotal(total);
			}
		}
		
		return _t;
	}
	
}

package br.com.crearesistemas.pcsi.wsrecebeindisponiveis.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.enumeration.EstadoOperacional;
import br.com.crearesistemas.enumeration.TipoImplemento;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeImplementosIndisponiveisResponse {
	
	@XmlElementWrapper
	@XmlElement(name = "implementoIndisponivel")
	private List<ImplementoIndisponivel> listaDeImplementosIndisponiveis = new ArrayList<ImplementoIndisponivel>();

	private Date dataHistorico;

	public List<ImplementoIndisponivel> getListaDeImplementosIndisponiveis() {
		return listaDeImplementosIndisponiveis;
	}

	public void setListaDeImplementosIndisponiveis(
			List<ImplementoIndisponivel> listaDeImplementosIndisponiveis) {
		this.listaDeImplementosIndisponiveis = listaDeImplementosIndisponiveis;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public void mockar() 
	{
		ImplementoIndisponivel imp01 = new ImplementoIndisponivel();
		imp01.setId(184L);
		imp01.setIdentificacao("GRRIO-01");		
		imp01.setIdEstadoOperacional(EstadoOperacional.CARREGAMENTO.ordinal());
		imp01.setIdTipoImplemento(TipoImplemento.GRUA_FLORESTAL.ordinal());
		
		
		ImplementoIndisponivel imp02 = new ImplementoIndisponivel();
		imp02.setId(170L);
		imp02.setIdentificacao("EJW1506");		
		imp02.setIdEstadoOperacional(EstadoOperacional.CARREG_VAZIO.ordinal());
		imp02.setIdTipoImplemento(TipoImplemento.CAMINHAO_TRANSPORTE.ordinal());

		
		ImplementoIndisponivel imp03 = new ImplementoIndisponivel();
		imp02.setId(124L);
		imp02.setIdentificacao("MKZ5333");		
		imp02.setIdEstadoOperacional(EstadoOperacional.SI_QUADRAS.ordinal());
		imp02.setIdTipoImplemento(TipoImplemento.CAMINHAO_TRANSPORTE.ordinal());

		
		listaDeImplementosIndisponiveis.add(imp01);
		listaDeImplementosIndisponiveis.add(imp02);
		listaDeImplementosIndisponiveis.add(imp03);
		
		
	}
}

package br.com.crearesistemas.pcct.wsrecebechegadas.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeChegadasResponse 
{	
	@XmlElementWrapper
	@XmlElement(name = "chegada")
	private List<Chegada> listaDeChegadas = new ArrayList<Chegada>();

	private Date dataHistorico;

	public List<Chegada> getListaDeChegadas() 
	{
		return listaDeChegadas;
	}

	public void setListaDeChegadas(
			List<Chegada> listaDeChegadas) 
	{
		this.listaDeChegadas = listaDeChegadas;
	}

	public Date getDataHistorico() 
	{
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) 
	{
		this.dataHistorico = dataHistorico;
	}
	
	public void mockar()
	{
		Calendar c = Calendar.getInstance();
		Date daqui45m = new Date();
		Date daqui2h = new Date();
		Date daqui3h = new Date();
		Date daqui1d = new Date();
		
		c.setTime(daqui45m);
		c.add(Calendar.MINUTE, 45);
		daqui45m = c.getTime();
		
		c.setTime(daqui2h);
		c.add(Calendar.HOUR, 2);
		daqui2h = c.getTime();
		
		c.setTime(daqui3h);
		c.add(Calendar.HOUR, 3);
		daqui3h = c.getTime();
		
		c.setTime(daqui1d); 
		c.add(Calendar.DATE, 1);		
		daqui1d = c.getTime();

		Chegada c1 = new Chegada();
		c1.setIdOrdemTransporte(1L);
		c1.setIdentificacao("AAA000");
		c1.setDataPrevistaChegada(daqui45m);
		
		Chegada c2 = new Chegada();
		c2.setIdOrdemTransporte(2L);
		c2.setIdentificacao("BBB000");
		c2.setDataPrevistaChegada(daqui3h);

		listaDeChegadas.add(c1);
		listaDeChegadas.add(c2);
	}
}

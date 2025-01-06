package br.com.crearesistemas.pcct.wsrecebepartidas.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebePartidasResponse 
{	
	@XmlElementWrapper
	@XmlElement(name = "partida")
	private List<Partida> listaDePartidas = new ArrayList<Partida>();

	public List<Partida> getListaDePartidas() 
	{
		return listaDePartidas;
	}

	public void setListaDePartidas(
			List<Partida> listaDePartidas) 
	{
		this.listaDePartidas = listaDePartidas;
	}
	
	public void mockar()
	{
		Partida p1 = new Partida();
		p1.setIdOrdemTransporte(1L);
		p1.setIdentificacao("AAA000");
		p1.setDataChegada(new DateTime().minusHours(3).toDate());
		p1.setDataJornada(new DateTime().plusHours(3).toDate());

		Partida p2 = new Partida();
		p2.setIdOrdemTransporte(1L);
		p2.setIdentificacao("BBB000");
		p2.setDataChegada(new DateTime().minusHours(6).toDate());
		p2.setDataJornada(new DateTime().plusHours(1).toDate());
		
		Partida p3 = new Partida();
		p3.setIdOrdemTransporte(1L);
		p3.setIdentificacao("CCC000");
		p3.setDataChegada(new DateTime().minusMinutes(47).toDate());
		p3.setDataJornada(new DateTime().plusMinutes(12).toDate());
		
		listaDePartidas.add(p1);
		listaDePartidas.add(p2);
		listaDePartidas.add(p3);
	}
}

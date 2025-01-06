package br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeAcompanhamentoTransporte 
{
	@XmlElement(required = true)
	private Date dataBase;

	public Date getDataBase() 
	{
		return dataBase;
	}

	public void setDataBase(Date data) 
	{
		this.dataBase = data;
	}
}

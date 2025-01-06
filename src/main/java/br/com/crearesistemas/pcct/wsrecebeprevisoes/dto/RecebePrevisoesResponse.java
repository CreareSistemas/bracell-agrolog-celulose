package br.com.crearesistemas.pcct.wsrecebeprevisoes.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebePrevisoesResponse  {

	@XmlElementWrapper
	@XmlElement(name = "previsao")
	private List<Previsao> listaDePrevisoes = new ArrayList<Previsao>();

	public List<Previsao> getListaDePrevisoes() {
		return listaDePrevisoes;
	}

	public void setListaDePrevisoes(List<Previsao> listaDePrevisoes) {
		this.listaDePrevisoes = listaDePrevisoes;
	}
	
	@XmlElement(required = true)
    protected int erro;
   
    @XmlElement(required = true)
    protected String mensagem;

    @XmlElement(required = false)
    protected String identificacao;

	public int getErro() {
		return erro;
	}

	public void setErro(int erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}


}

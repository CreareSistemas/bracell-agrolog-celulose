package br.com.crearesistemas.pcsi.wsrecebetemposmediosciclointerno.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeTemposMediosCicloInternoResponse
{
	
	@XmlElement(required = true)
    protected int erro;
   
    @XmlElement(required = true)
    protected String mensagem;

    
	@XmlElementWrapper
	@XmlElement(name = "tempoMedioCicloInterno")
	private List<TempoMedioCicloInterno> listaDeTemposMediosCicloInterno = new ArrayList<TempoMedioCicloInterno>();

	public List<TempoMedioCicloInterno> getListaDeTemposMediosCicloInterno() {
		return listaDeTemposMediosCicloInterno;
	}

	public void setListaDeTemposMediosCicloInterno(
			List<TempoMedioCicloInterno> listaDeTemposMediosCicloInterno) {
		this.listaDeTemposMediosCicloInterno = listaDeTemposMediosCicloInterno;
	}

	public List<TempoMedioCicloInterno> mockar() {
		TempoMedioCicloInterno a1 = new TempoMedioCicloInterno();
		a1.setIdPrestador(7L);
		a1.setPrestador("Scapini");
		a1.setGrupo("Geral");
		a1.setAcessoPrivadoEntrada(87990);
		a1.setControleAcesso(46000);
		a1.setBalancaEntrada(32962);
		a1.setBufferViaduto(937810);
		a1.setFilaExterna(850173);
		a1.setFilaInterna(83001);
		a1.setQuadras(937103);
		a1.setMesas(65826);
		a1.setPatio(73910);
		a1.setBalancaSaida(63921);
		a1.setVarricao(183601);
		a1.setAcessoPrivadoSaida(30810);
		a1.setTotal(99999);
		
		TempoMedioCicloInterno b1 = new TempoMedioCicloInterno();
		b1.setIdPrestador(21L);
		b1.setPrestador("BBM");
		b1.setGrupo("Geral");
		b1.setAcessoPrivadoEntrada(749810);
		b1.setControleAcesso(37017);
		b1.setBalancaEntrada(103947);
		b1.setBufferViaduto(1137);
		b1.setFilaExterna(98702);
		b1.setFilaInterna(874937);
		b1.setQuadras(981091);
		b1.setMesas(89171);
		b1.setPatio(93871);
		b1.setBalancaSaida(81919);
		b1.setVarricao(18399);
		b1.setAcessoPrivadoSaida(98784);
		b1.setTotal(999111);
		
		listaDeTemposMediosCicloInterno.add(a1);
		listaDeTemposMediosCicloInterno.add(b1);
		
		return listaDeTemposMediosCicloInterno;
	}

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
	
	
}

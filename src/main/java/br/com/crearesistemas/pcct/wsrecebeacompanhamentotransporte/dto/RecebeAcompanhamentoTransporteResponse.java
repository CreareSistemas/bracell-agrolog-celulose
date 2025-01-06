package br.com.crearesistemas.pcct.wsrecebeacompanhamentotransporte.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.pcct.wsrecebegruasflorestais.dto.GruaFlorestal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeAcompanhamentoTransporteResponse  {

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
		
		
	@XmlElementWrapper
	@XmlElement(name = "acompanhamento")
	private List<Acompanhamento> listaDeAcompanhamentos = new ArrayList<Acompanhamento>();

	public List<Acompanhamento> getListaDeAcompanhamentos() {
		return listaDeAcompanhamentos;
	}

	public void setListaDeAcompanhamentos(List<Acompanhamento> listaDeAcompanhamentos) {
		this.listaDeAcompanhamentos = listaDeAcompanhamentos;
	}
	
	public void mockar() {
		Acompanhamento acompanhamento = null;
		
		acompanhamento = new Acompanhamento();
		acompanhamento.setIdProjeto(833L);
		acompanhamento.setProjeto("AMENGUAL");
		acompanhamento.setIdPrestador(21L);
		acompanhamento.setPrestador("BBM");
		acompanhamento.setCicloPrevisto(8520);
		acompanhamento.setCicloRealizado(950);
		acompanhamento.setVolumePrevisto(1);
		acompanhamento.setVolumeRealizado(2);
		acompanhamento.setViagemPrevisto(4);
		acompanhamento.setViagemRealizado(3);
		acompanhamento.setVolumeCaminhaoPrevisto(10);
		acompanhamento.setVolumeCaminhaoRealizado(12);
		acompanhamento.setPesoMadeira(250);
		acompanhamento.setProjecaoFinalVolume(300);
		acompanhamento.setProjecaoFinalViagem(10);
		acompanhamento.setProjecaoFinalGrauAtendimento(80);
		listaDeAcompanhamentos.add(acompanhamento);
	
		acompanhamento = new Acompanhamento();
		acompanhamento.setIdProjeto(833L);
		acompanhamento.setProjeto("AMENGUAL");
		acompanhamento.setIdPrestador(7L);
		acompanhamento.setPrestador("Scapini");
		acompanhamento.setCicloPrevisto(8520);
		acompanhamento.setCicloRealizado(950);
		acompanhamento.setVolumePrevisto(3);
		acompanhamento.setVolumeRealizado(1);
		acompanhamento.setViagemPrevisto(7);
		acompanhamento.setViagemRealizado(10);
		acompanhamento.setVolumeCaminhaoPrevisto(20);
		acompanhamento.setVolumeCaminhaoRealizado(18);
		acompanhamento.setPesoMadeira(400);
		acompanhamento.setProjecaoFinalVolume(1000);
		acompanhamento.setProjecaoFinalViagem(13);
		acompanhamento.setProjecaoFinalGrauAtendimento(90);
		listaDeAcompanhamentos.add(acompanhamento);

		acompanhamento = new Acompanhamento();
		acompanhamento.setIdProjeto(553L);
		acompanhamento.setProjeto("LIMOEIRO");
		acompanhamento.setIdPrestador(7L);
		acompanhamento.setPrestador("Scapini");
		acompanhamento.setCicloPrevisto(55);
		acompanhamento.setCicloRealizado(55);
		acompanhamento.setVolumePrevisto(3);
		acompanhamento.setVolumeRealizado(1);
		acompanhamento.setViagemPrevisto(3);
		acompanhamento.setViagemRealizado(2);
		acompanhamento.setVolumeCaminhaoPrevisto(182);
		acompanhamento.setVolumeCaminhaoRealizado(190);
		acompanhamento.setPesoMadeira(740);
		acompanhamento.setProjecaoFinalVolume(600);
		acompanhamento.setProjecaoFinalViagem(20);
		acompanhamento.setProjecaoFinalGrauAtendimento(89);
		listaDeAcompanhamentos.add(acompanhamento);
}

	
}

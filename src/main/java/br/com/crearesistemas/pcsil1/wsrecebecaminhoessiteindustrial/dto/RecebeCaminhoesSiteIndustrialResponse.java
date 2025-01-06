package br.com.crearesistemas.pcsil1.wsrecebecaminhoessiteindustrial.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import br.com.crearesistemas.pcsi.wsrecebecaminhoessiteindustrial.dto.CaminhaoSiteIndustrial;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeCaminhoesSiteIndustrialResponse {
	private static final Logger logger = Logger.getLogger(RecebeCaminhoesSiteIndustrialResponse.class);
	@XmlElementWrapper
	@XmlElement(name = "caminhaoSiteIndustrial")
	private List<CaminhaoSiteIndustrial> listaDeCaminhoesSiteIndustrial = new ArrayList<CaminhaoSiteIndustrial>();

	private Date dataHistorico;

	public List<CaminhaoSiteIndustrial> getListaDeCaminhoesSiteIndustrial() {
		return listaDeCaminhoesSiteIndustrial;
	}

	public void setListaDeCaminhoesSiteIndustrial(
			List<CaminhaoSiteIndustrial> listaDeCaminhoesSiteIndustrial) {
		this.listaDeCaminhoesSiteIndustrial = listaDeCaminhoesSiteIndustrial;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	

	
}

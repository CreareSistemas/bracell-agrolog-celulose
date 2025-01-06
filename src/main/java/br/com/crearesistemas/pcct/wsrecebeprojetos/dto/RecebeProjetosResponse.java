package br.com.crearesistemas.pcct.wsrecebeprojetos.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RecebeProjetosResponse {
	
	@XmlElementWrapper
	@XmlElement(name = "projeto")
	private List<Projeto> listaDeProjetos = new ArrayList<Projeto>();

	private Date dataHistorico;

	public List<Projeto> getListaDeProjetos() {
		return listaDeProjetos;
	}

	public void setListaDeProjetos(List<Projeto> listaDeProjetos) {
		this.listaDeProjetos = listaDeProjetos;
	}

	public Date getDataHistorico() {
		return dataHistorico;
	}

	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}

	public void mockar() {
		listaDeProjetos.add(new Projeto(2L, "SAO BENTO"));
		listaDeProjetos.add(new Projeto(3L, "COREADA"));
		listaDeProjetos.add(new Projeto(4L, "SUSPIRO"));
	}
	
	public Projeto getProjetoById(Long id) {
		List<Projeto> projetos = this.getListaDeProjetos();
		for (Projeto prj : projetos) {
			if (prj.getId() == id) return prj;
		}
		return null;
	}
	
	public boolean listaDeProjetosContains(Projeto projeto) {
		List<Projeto> projetos = this.getListaDeProjetos();
		for (Projeto prj : projetos) {
			if (prj.getId() == projeto.getId()) return true;
		}
		return false;
	}

	public void atualizaListaDeDescargaProjeto(Long idProjeto, LocalDTO localDto) {
		Projeto projeto = this.getProjetoById(idProjeto);
		if (projeto != null) {
			// atualiza a lista de descarga
			if (!projeto.locaisDeDescargaContains(localDto)) {
				projeto.getLocaisDeDescarga().add(localDto);
			}
		}
	}

	public void filtroPorIdLocalDeDescarga(String localDeDescargaId, Boolean incluirProjetosSemOT) {
		List<Projeto> projetos = this.getListaDeProjetos();
		
		List<Projeto> filtro = new ArrayList<Projeto>();
		
		for (Projeto projeto : projetos){
			if (projeto.locaisDeDescargaContainsIntegracao(localDeDescargaId)) {
				filtro.add(projeto);
			}
			
			if (incluirProjetosSemOT != null && incluirProjetosSemOT) {
				if (projeto.getLocaisDeDescarga().size() < 1) {
					filtro.add(projeto);
				}	
			}
			
		}
		
		this.setListaDeProjetos(filtro);
	}
	
}

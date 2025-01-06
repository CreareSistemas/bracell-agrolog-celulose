package br.com.crearesistemas.model;

public class ProjetoEntity {

	Long id;
	String name;
	Double distancia;
	Double distMtsProject;
	Double distMtsTotal;


	Long projectCode;
	String projectId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Double getDistMtsProject() {
		return distMtsProject;
	}

	public void setDistMtsProject(Double distMtsProject) {
		this.distMtsProject = distMtsProject;
	}

	public Double getDistMtsTotal() {
		return distMtsTotal;
	}

	public void setDistMtsTotal(Double distMtsTotal) {
		this.distMtsTotal = distMtsTotal;
	}

	public Long getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(Long projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}

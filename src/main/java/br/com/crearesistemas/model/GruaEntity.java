package br.com.crearesistemas.model;

import java.util.Date;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.service.impl.helper.GeoLocalizacao;

public class GruaEntity {

	Long id;
	String name;
	String prefix;
	String description;
	Long operatorId;
	Long customerChildId;
	
	
	Date eventdateTime;  // data hora do ultimo evento
	Date avldateTime; // data hora do ultimo avl
	Float latitude;
	Float longitue;
	Long projectId; // pk projeto
	
	// dados projeto da grua
	Long sgfProjectCode;
	String sgfProjectId;
	Date sgfProjectDate;
	
	// downtime atual
	Integer groupId;			// grupo atual eventos
	Integer downtimeCode;       // cod parada (sgf) atual
	Date lastDateDowntime;		// data ultimo evento

	
	
	// dados eventos	  
	Long eventReportId;  // pk da eventos
	Long eventCode;   	// cod evento
	String projectName;  // nome do projeto
	String project;     // projectCode
	String field;		// standId
	String plates;      // placa
	String locale;
	String pile;
	String loadType;  // 1 - carrega, 2 -descarreg
	String chipper;
	String operatorName;
	Float hourmeter;	   // horimetro atual
	
	
	Integer loading;
	// custom_columns
	private Long idLocal;	
	
	// fluxo do transporte
	Integer transpEstadoOperacional;
	Integer transpFluxo;
	Date    transpDate;
	
	// fluxo do carregamento
	Integer carregEstadoOperacional;
	Integer carregFluxo;
	Date    carregDate;

	// fluxo do site
	Integer siteEstadoOperacional;
	Integer siteFluxo;
	Date    siteDate;

	// fluxo proprio
	Integer proprioEstadoOperacional;
	Integer proprioFluxo;
	Date    proprioDate;

	
	// dados do evento anterior para o controle de paradas
	DowntimeDto downtime;
		
	
	// ultimo carregamento
	Date loadDateTime;  		// data hora do ultimo evento carregamento
	Long loadEventCode;   		// ultimo cod evento de carregamento
	Long loadEventReportId;  	// pk da evento de carregamento

	
	public Integer getLoading() {
		return loading;
	}

	public void setLoading(Integer loading) {
		this.loading = loading;
	}

	
	public Integer getCarregEstadoOperacional() {
		return carregEstadoOperacional;
	}

	public void setCarregEstadoOperacional(Integer carregEstadoOperacional) {
		this.carregEstadoOperacional = carregEstadoOperacional;
	}

	public Integer getCarregFluxo() {
		return carregFluxo;
	}

	public void setCarregFluxo(Integer carregFluxo) {
		this.carregFluxo = carregFluxo;
	}

	public Integer getSiteEstadoOperacional() {
		return siteEstadoOperacional;
	}

	public void setSiteEstadoOperacional(Integer siteEstadoOperacional) {
		this.siteEstadoOperacional = siteEstadoOperacional;
	}

	public Integer getSiteFluxo() {
		return siteFluxo;
	}

	public void setSiteFluxo(Integer siteFluxo) {
		this.siteFluxo = siteFluxo;
	}

	public Integer getProprioEstadoOperacional() {
		return proprioEstadoOperacional;
	}

	public void setProprioEstadoOperacional(Integer proprioEstadoOperacional) {
		this.proprioEstadoOperacional = proprioEstadoOperacional;
	}

	public Integer getProprioFluxo() {
		return proprioFluxo;
	}

	public void setProprioFluxo(Integer proprioFluxo) {
		this.proprioFluxo = proprioFluxo;
	}

	public Long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}

	public String getChipper() {
		return chipper;
	}

	public void setChipper(String chipper) {
		this.chipper = chipper;
	}

	public Date getAvldateTime() {
		return avldateTime;
	}

	public void setAvldateTime(Date avldateTime) {
		this.avldateTime = avldateTime;
	}

	public Date getEventDateTime() {
		return eventdateTime;
	}

	public void setEventDateTime(Date dateTime) {
		this.eventdateTime = dateTime;
	}

	
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitue() {
		return longitue;
	}

	public void setLongitue(Float longitue) {
		this.longitue = longitue;
	}

	
	
	public Date getEventdateTime() {
		return eventdateTime;
	}

	public void setEventdateTime(Date eventdateTime) {
		this.eventdateTime = eventdateTime;
	}

	public Long getEventCode() {
		return eventCode;
	}

	public void setEventCode(Long eventCode) {
		this.eventCode = eventCode;
	}


	public Integer getxTranspEstadoOperacional() {
		return transpEstadoOperacional;
	}

	public void setTranspEstadoOperacional(Integer transpEstadoOperacional) {
		this.transpEstadoOperacional = transpEstadoOperacional;
	}

	public Integer getTranspFluxo() {
		return transpFluxo;
	}

	public void setTranspFluxo(Integer transpFluxo) {
		this.transpFluxo = transpFluxo;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPile() {
		return pile;
	}

	public void setPile(String pile) {
		this.pile = pile;
	}

	public String getPlates() {
		return plates;
	}

	public void setPlates(String plates) {
		this.plates = plates;
	}

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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
		
	public Long getCustomerChildId() {
		return customerChildId;
	}

	public void setCustomerChildId(Long customerChildId) {
		this.customerChildId = customerChildId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	
	public Long getEventReportId() {
		return eventReportId;
	}

	public void setEventReportId(Long eventReportId) {
		this.eventReportId = eventReportId;
	}

	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Boolean isMesa() {
		return Config.getInstance().isMesa(name);
	}
	
	public Boolean isMesaL1() {
		return Config.getInstance().isMesaL1(name);
	}
	
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getDowntimeCode() {
		return downtimeCode;
	}

	public void setDowntimeCode(Integer downtimeCode) {
		this.downtimeCode = downtimeCode;
	}

	public DowntimeDto getDowntime() {
		return downtime;
	}

	public void setDowntime(DowntimeDto downtime) {
		this.downtime = downtime;
	}
	
	public Float getHourmeter() {
		return hourmeter;
	}

	public void setHourmeter(Float hourmeter) {
		this.hourmeter = hourmeter;
	}
	
	

	public Date getTranspDate() {
		return transpDate;
	}

	public void setTranspDate(Date transpDate) {
		this.transpDate = transpDate;
	}

	public Date getCarregDate() {
		return carregDate;
	}

	public void setCarregDate(Date carregDate) {
		this.carregDate = carregDate;
	}

	public Date getSiteDate() {
		return siteDate;
	}

	public void setSiteDate(Date siteDate) {
		this.siteDate = siteDate;
	}

	public Date getProprioDate() {
		return proprioDate;
	}

	public void setProprioDate(Date proprioDate) {
		this.proprioDate = proprioDate;
	}

	// está em downtime e não mudou de código
	public boolean isDowntime() {
		return downtimeCode != null && downtimeCode > 0;
	}

	public boolean isDowntimeSameCode() {		
		if (downtime != null && downtime.getEndDowntime() == null) {
			return isDowntime() && downtime.downtimeCode == null ||
				   isDowntime() && downtime.downtimeCode == downtimeCode;
		} else {
			return isDowntime();	
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getTranspEstadoOperacional() {
		return transpEstadoOperacional;
	}

	public Date getLastDateDowntime() {
		return lastDateDowntime;
	}

	public void setLastDateDowntime(Date lastDateDowntime) {
		this.lastDateDowntime = lastDateDowntime;
	}
	
	
	
	public Long getSgfProjectCode() {
		return sgfProjectCode;
	}

	public void setSgfProjectCode(Long sgfProjectCode) {
		this.sgfProjectCode = sgfProjectCode;
	}

	public String getSgfProjectId() {
		return sgfProjectId;
	}

	public void setSgfProjectId(String sgfProjectId) {
		this.sgfProjectId = sgfProjectId;
	}
	
	

	public Date getSgfProjectDate() {
		return sgfProjectDate;
	}

	public void setSgfProjectDate(Date sgfProjectDate) {
		this.sgfProjectDate = sgfProjectDate;
	}

	public Date getLoadDateTime() {
		return loadDateTime;
	}

	public void setLoadDateTime(Date loadDateTime) {
		this.loadDateTime = loadDateTime;
	}

	public Long getLoadEventCode() {
		return loadEventCode;
	}

	public void setLoadEventCode(Long loadEventCode) {
		this.loadEventCode = loadEventCode;
	}

	public Long getLoadEventReportId() {
		return loadEventReportId;
	}

	public void setLoadEventReportId(Long loadEventReportId) {
		this.loadEventReportId = loadEventReportId;
	}

	public boolean consisteLastDate(Date endDowntime) {
		if (lastDateDowntime != null && endDowntime != null) {
			return endDowntime.after(lastDateDowntime);
		} else if (endDowntime == null) {
			return false;
		}
		return true;
	}

	public boolean validateCoords() {
		return GeoLocalizacao.validateCoords(latitude, longitue);
	}
	
}

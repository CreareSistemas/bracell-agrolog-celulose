package br.com.crearesistemas.model;

import java.util.Date;

import br.com.crearesistemas.service.dto.TriggerEventosDto;
import br.com.crearesistemas.service.impl.helper.GeoLocalizacao;

public class CaminhaoEntity {
	Long id;
	String name;
	String prefix;
	String description;
	Long operatorId;
	Long customerChildId;
	Integer type;
	
	Date eventDateTime;
	Date avldateTime;
	

	Boolean isStayInFactory;
	Integer lineId; // linha 1 ou 2
	Long projectId; // está fixo no projeto 36200l (pcct)
	String projectIdName; // nome do projeto da raia (pcct)
	Integer percentualConclusao; // pcct
	Double distMtsProject;
	Double distMtsTotal;
	String spot;	
	
	// previsao chegada Fabrica
	Date dataPrevisaoChegadaFabrica;
	Double distMtsFabrica;
	Date lastUpdPrevisaoChegadaFabrica;
	
	
	Long eventReportId;  	// pk da eventos atual (events_temp.evento_id)
	Long eventCode;			// cod evento
	Float latitude;
	Float longitude;
	
	Float prevLatitude;
	Float prevLongitude;

	Float hourmeter;	   // horimetro atual
	Integer speed;
	Integer loading;       // operational_state
	String operatorName;
	
	
	// downtime atual
	Integer groupId;			// grupo atual eventos
	Integer downtimeCode;       // cod parada (sgf) atual
	Date lastDateDowntime;			// data ultimo evento
	
	// ----------------------------------------------------------------------
	// dados do campo custom_columns
	// data pcct
	private Date dataPartidaPcct;

	// data pcsi
	private Date dataChegadaPcsi;

	// data liberacao
	private Date dataLiberacao;
	
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
	Date    siteFilaDate;
	
	// fluxo proprio
	Integer proprioEstadoOperacional;
	Integer proprioFluxo;
	Date    proprioDate;

	// para utilizar nos Estagios 
	private Long idLocal;	// local da grua, fila
	Long idGrua;            // grua mais proxima no pcct, no pcsi é a grua de mesa/patio
	
	// dados do carregamento no patio
	Long eventInternalId;   // pk de carregamento da grua patio
	Date craneDateTime;     // data de inicio do carregamento na grua
	Boolean isPatioInterno; // signfica que nasceu de um carregamento interno
	String locale;
	String pile;
	String chipper;
	String craneOperatorName;  // operador da grua
	String loadType;  // 1 - carrega, 2 -descarreg 
	
	
	Long   eventLoadId;         // pk de carregamento da grua campo
	String projectName;   		// nome do projeto no apontamento
	String project;       		// projectCode (campo)
	String field;		  		// standId (campo)
	String plates;		  		// placa (campo)
	Boolean apontCampoInvalido; // apontamento de campo invalido
	String apontSgfResult;		// mensagem sgf integração com apontamento
	
	// dados do evento anterior para o controle de paradas
	DowntimeDto downtime;
	
	// dados eventos troca estagio SI
	TriggerEventosDto triggerSI = new TriggerEventosDto();
	
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

	public Long getIdGrua() {
		return idGrua;
	}
	
	

	public Float getPrevLatitude() {
		return prevLatitude;
	}

	public void setPrevLatitude(Float prevLatitude) {
		this.prevLatitude = prevLatitude;
	}

	
	
	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getPrevLongitude() {
		return prevLongitude;
	}

	public void setPrevLongitude(Float prevLongitude) {
		this.prevLongitude = prevLongitude;
	}

	
	public void setIdGrua(Long idGrua) {
		this.idGrua = idGrua;
	}

	
	public Date getAvldateTime() {
		return avldateTime;
	}

	public void setAvldateTime(Date avldateTime) {
		this.avldateTime = avldateTime;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	
	
	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	
	
	public Long getEventCode() {
		return eventCode;
	}

	public void setEventCode(Long eventCode) {
		this.eventCode = eventCode;
	}

	
	public Integer getTranspEstadoOperacional() {
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
	
	

	public Date getDataPartidaPcct() {
		return dataPartidaPcct;
	}

	public void setDataPartidaPcct(Date dataPartidaPcct) {
		this.dataPartidaPcct = dataPartidaPcct;
	}


	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getDataChegadaPcsi() {
		return dataChegadaPcsi;
	}

	public void setDataChegadaPcsi(Date dataChegadaPcsi) {
		this.dataChegadaPcsi = dataChegadaPcsi;
	}

	
	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public String getProjectIdName() {
		return projectIdName;
	}

	public void setProjectIdName(String projectIdName) {
		this.projectIdName = projectIdName;
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

	public String getSpot() {
		return spot;
	}

	public void setSpot(String spot) {
		this.spot = spot;
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

	public Integer getLoading() {
		return loading;
	}

	public void setLoading(Integer loading) {
		this.loading = loading;
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

	public String getChipper() {
		return chipper;
	}

	public void setChipper(String chipper) {
		this.chipper = chipper;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getCraneOperatorName() {
		return craneOperatorName;
	}

	public void setCraneOperatorName(String craneOperatorName) {
		this.craneOperatorName = craneOperatorName;
	}

	
	public Boolean getIsPatioInterno() {
		return isPatioInterno;
	}

	public void setIsPatioInterno(Boolean isPatioInterno) {
		this.isPatioInterno = isPatioInterno;
	}

	public Long getEventReportId() {
		return eventReportId;
	}

	public void setEventReportId(Long eventReportId) {
		this.eventReportId = eventReportId;
	}

	public Long getEventInternalId() {
		return eventInternalId;
	}

	public void setEventInternalId(Long eventInternalId) {
		this.eventInternalId = eventInternalId;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public Date getCraneDateTime() {
		return craneDateTime;
	}

	public void setCraneDateTime(Date craneDateTime) {
		this.craneDateTime = craneDateTime;
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

	// verifica se mudou de eventos
	public boolean isCloseDowntime() {		
		return downtime != null 
				&& downtime.sgfDowntime != null && !downtime.sgfDowntime  
				;
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


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public TriggerEventosDto getTriggerSI() {
		return triggerSI;
	}

	public void setTriggerSI(TriggerEventosDto triggerSI) {
		this.triggerSI = triggerSI;
	}

	public Long getEventLoadId() {
		return eventLoadId;
	}

	public void setEventLoadId(Long eventLoadId) {
		this.eventLoadId = eventLoadId;
	}

	public Boolean getApontCampoInvalido() {
		return apontCampoInvalido;
	}

	public void setApontCampoInvalido(Boolean apontCampoInvalido) {
		this.apontCampoInvalido = apontCampoInvalido;
	}

	public String getApontSgfResult() {
		return apontSgfResult;
	}

	public void setApontSgfResult(String apontSgfResult) {
		this.apontSgfResult = apontSgfResult;
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

	
	
	public Date getLastUpdPrevisaoChegadaFabrica() {
		return lastUpdPrevisaoChegadaFabrica;
	}

	public void setLastUpdPrevisaoChegadaFabrica(Date lastUpdPrevisaoChegadaFabrica) {
		this.lastUpdPrevisaoChegadaFabrica = lastUpdPrevisaoChegadaFabrica;
	}

	public Date getDataPrevisaoChegadaFabrica() {
		return dataPrevisaoChegadaFabrica;
	}

	public void setDataPrevisaoChegadaFabrica(Date dataPrevisaoChegadaFabrica) {
		this.dataPrevisaoChegadaFabrica = dataPrevisaoChegadaFabrica;
	}

	public Double getDistMtsFabrica() {
		return distMtsFabrica;
	}

	public void setDistMtsFabrica(Double distMtsFabrica) {
		this.distMtsFabrica = distMtsFabrica;
	}

	public Integer getPercentualConclusao() {
		return percentualConclusao;
	}

	public void setPercentualConclusao(Integer percentualConclusao) {
		this.percentualConclusao = percentualConclusao;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public Boolean getIsStayInFactory() {
		return isStayInFactory;
	}

	public void setIsStayInFactory(Boolean isStayInFactory) {
		this.isStayInFactory = isStayInFactory;
	}

	
	public Date getSiteFilaDate() {
		return siteFilaDate;
	}

	public void setSiteFilaDate(Date siteFilaDate) {
		this.siteFilaDate = siteFilaDate;
	}

	
	public Date getLastDateDowntime() {
		return lastDateDowntime;
	}

	public void setLastDateDowntime(Date lastDateDowntime) {
		this.lastDateDowntime = lastDateDowntime;
	}

	public Boolean hasChangeLine(Integer currlineId) {
		Boolean res = false;
		
		if (lineId == null && currlineId == null) {
			return false;
		} else
		if (lineId != null && currlineId != null) {
			if (lineId.intValue() != currlineId.intValue()) {
				res = true;
			}
		} else {			
			res = (currlineId != null) || (lineId != null);
		}
			
			
		return res;
	}

	public boolean consisteLastDate(Date endDowntime) {
		if (lastDateDowntime != null && endDowntime != null) {
			return endDowntime.after(lastDateDowntime);
		} else if (endDowntime == null) {
			return false;
		}
		return true;
	}

	public Boolean isEqualsPlates(String placa) {
		if (placa != null) {
			return !placa.trim().isEmpty() && placa.length()> 3 && getName().contains(placa.trim());
		} else {
			return false;
		}
	}

	public boolean validateCoords() {
		return GeoLocalizacao.validateCoords(latitude, longitude);
	}

			
}

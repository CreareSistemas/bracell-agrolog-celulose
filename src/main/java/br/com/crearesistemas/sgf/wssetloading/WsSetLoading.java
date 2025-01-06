package br.com.crearesistemas.sgf.wssetloading;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.model.CaminhaoEntity;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.sgf.BaseAuth;
import br.com.crearesistemas.sgf.SgfEventsLoading;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class WsSetLoading extends BaseAuth{
	private static final Logger logger = Logger.getLogger(WsSetLoading.class);
	static Config config = Config.getInstance();
	
	public static SetLoadingResponse enviaApontamentoSgf(
			GruaEntity grua, CaminhaoEntity caminhao, SgfCrane sgfGrua) {
		
		logger.info("enviaApontamentoSgf: " + caminhao.getName());
		logger.info("reportID: " + caminhao.getEventLoadId());
		
		caminhao.setApontCampoInvalido(null);
		caminhao.setApontSgfResult(null);
		
		SetLoading loading = new SetLoading();
		
		if (caminhao.getEventLoadId() != null) {
			loading.setReportID(Long.toString(caminhao.getEventLoadId()));	
		} else {
			if (caminhao.getEventReportId() != null) {
				loading.setReportID(Long.toString(caminhao.getEventReportId()));
			} else {
				loading.setReportID(Long.toString(new Date().getTime()));
			}
		}
		
		if (caminhao.getPlates() != null) {
			loading.setTruckPlate(caminhao.getPlates());	
		} else {
			loading.setTruckPlate(caminhao.getName());
		}
		
		logger.info("truckPlate: " 		+ loading.getTruckPlate()  );
		
		loading.setProjectCode(NumberUtils.parseLong(caminhao.getProject()));
		loading.setStandId(StringUtils.leftPad(caminhao.getField(), 3, '0'));
		loading.setProductCode(1); // madeira
		loading.setCraneCode(sgfGrua.getCraneCode());
		if (caminhao.getCraneDateTime() != null) {
			loading.setStartingDate(DateUtils.formatDate(caminhao.getCraneDateTime())); //"2021-07-21T14:10:00"
		} else {
			loading.setStartingDate(DateUtils.formatDate(grua.getEventDateTime())); //"2021-07-21T14:10:00"	
		}
		
		logger.info("startingDate: " 	+ loading.getStartingDate()  );
		
		Date dataInicio = caminhao.getCraneDateTime();
		Date dataFim = new Date();
		
		if (dataInicio != null && dataFim != null) {
			if (dataFim.after(dataInicio)) {
				loading.setEndingDate(DateUtils.formatDate(dataFim));
			} else {
				loading.setEndingDate(DateUtils.formatDate(DateUtils.somarSegundos(dataInicio, 65)));
			}
		} else {
			loading.setEndingDate(DateUtils.formatDate(dataFim));	
		}
		
		loading.setCraneOperatorName(caminhao.getCraneOperatorName());
		
		
		logger.info("================= Dados Carregamento (SetLoading) Campo Sgf: ============== " );
		logger.info("url: " 			+ config.getSgfUrlBase() );
		logger.info("url follow: " 		+ BaseAuth.followRedirectURL(config.getSgfUrlBase()));
		logger.info("gruaName: " 		+ grua.getName() );
		logger.info("reportID: " 		+ loading.getReportID() );
		logger.info("truckPlate: " 		+ loading.getTruckPlate()  );
		logger.info("projectCode: " 	+ loading.getProjectCode()  );
		logger.info("standId: " 		+ loading.getStandId()  );
		logger.info("productCode: " 	+ loading.getProductCode()  );
		logger.info("craneCode: " 		+ loading.getCraneCode()  );
		logger.info("pileCode: " 		+ loading.getPileCode()  );
		logger.info("craneOperatorName: " + loading.getCraneOperatorName()  );
		logger.info("startingDate: " 	+ loading.getStartingDate()  );
		logger.info("endingDate: " 		+ loading.getEndingDate()  );
		
		
		SetLoadingResponse result = WsSetLoading.setLoading(
				config.getSgfUrlBase(), 
				config.getSgfAppId(),
				config.getSgfSecret(), 
				loading);
		
		if (result != null) {
			logger.info(" **************************** Result Sgf (SetLoading): *********************************** ");		
			logger.info("returnId: " + result.getReturnId()  );
			logger.info("returnMessage: " + result.getReturnMessage()  );
			
			if (result.getReturnMessage() != null) {
				caminhao.setApontSgfResult(result.getReturnMessage());
			}	
			
			if (result.getReturnId() != null) {
				Integer res = NumberUtils.parseInt(result.getReturnId());
				if (res != null) {
					caminhao.setApontCampoInvalido(res < 0);	
				}
			}
			
		}
		//Pair.of(result, )
		
		return result;
		
	}
	
	
		private static void consistenciaQas(SetLoading loading) {
			
			if (loading.getLocaleCode() == null) {
				loading.setLocaleCode(0l); // em desenv, este campo não pode ser nulo	
			}
			
			if (loading.getPileCode() == null) {
				loading.setPileCode(0l); // em desenv, este campo não pode ser nulo	
			}
			
			if (loading.getScaleCraneWeight() == null) {
				loading.setScaleCraneWeight(0); // em desenv, este campo não pode ser nulo	
			}
			

		}
	
	
		public static SetLoadingResponse setLoading(String urlBase, String user, String secret, SetLoading loading)
	    {
			if (urlBase.contains("sgfqas")) {
				consistenciaQas(loading);
			}
			
		  SetLoadingResponse result = null;
	      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
	      engine.setFollowRedirects(true);
	      Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
	      try
	      {
	    	   String posturl = urlBase + "/SetLoading?appId=" + user + "&secret=" + secret;
	    	   
	    	   System.out.println("URL follow: " + followRedirectURL(posturl) );
	    	   
	    	   Builder target = client
	    			   .target(followRedirectURL(posturl)).request();
		  		
	  		Entity<SetLoading> entity = Entity.entity(loading, "application/json");
	  		
	  		Response response = target.post(entity);

	  		result = response.readEntity(SetLoadingResponse.class);
	  		
	         response.close();
	      }
	      finally
	      {
	         client.close();
	      }
	      
	      return result;
	   }
	
		
		public static String setLoadingString(String urlBase, String user, String secret, SetLoading loading)
		   {
			  String result = null;
		      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
		      engine.setFollowRedirects(true);
		      Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
		      try
		      {
		    	   String posturl = urlBase + "/SetLoading?appId=" + user + "&secret=" + secret;
		    	   
		    	   Builder target = client
		    			   .target(posturl).request();
			  		
		  		Entity<SetLoading> entity = Entity.entity(loading, "application/json");
		  		
		  		Response response = target.post(entity);

		  		result = response.readEntity(String.class);
		  		
		         response.close();
		      }
		      finally
		      {
		         client.close();
		      }
		      
		      return result;
		   }


		public static SetLoadingResponse enviaApontamentoSgf(SgfEventsLoading nonTreatedEvent) {
			SetLoading loading = new SetLoading();
			
			loading.setReportID(nonTreatedEvent.getReportID());
			loading.setTruckPlate(nonTreatedEvent.getTruckPlate());
			loading.setProjectCode(nonTreatedEvent.getProjectCode());
			loading.setStandId(StringUtils.leftPad(nonTreatedEvent.getStandId(), 3, '0'));
			loading.setProductCode(nonTreatedEvent.getProductCode());
			loading.setCraneCode(nonTreatedEvent.getCraneCode());
			loading.setPileCode(nonTreatedEvent.getPileCode());
			loading.setCraneOperatorName(nonTreatedEvent.getCraneOperatorName());
			loading.setStartingDate(DateUtils.formatDate(nonTreatedEvent.getStartDate()));
			loading.setEndingDate(DateUtils.formatDate(nonTreatedEvent.getEndDate()));
			
			logger.info("================= Dados Carregamento (SetLoading) Campo Sgf: ============== " );
			logger.info("url: " 			+ config.getSgfUrlBase() );
			logger.info("url follow: " 		+ BaseAuth.followRedirectURL(config.getSgfUrlBase()));
			logger.info("reportID: " 		+ loading.getReportID() );
			logger.info("truckPlate: " 		+ loading.getTruckPlate()  );
			logger.info("projectCode: " 	+ loading.getProjectCode()  );
			logger.info("standId: " 		+ loading.getStandId()  );
			logger.info("productCode: " 	+ loading.getProductCode()  );
			logger.info("craneCode: " 		+ loading.getCraneCode()  );
			logger.info("pileCode: " 		+ loading.getPileCode()  );
			logger.info("craneOperatorName: " + loading.getCraneOperatorName()  );
			logger.info("startingDate: " 	+ loading.getStartingDate()  );
			logger.info("endingDate: " 		+ loading.getEndingDate()  );
			
			SetLoadingResponse result = WsSetLoading.setLoading(
					config.getSgfUrlBase(), 
					config.getSgfAppId(),
					config.getSgfSecret(), 
					loading);

			if (result != null) {
				logger.info(" **************************** Result Sgf (SetLoading): *********************************** ");		
				logger.info("returnId: " + result.getReturnId()  );
				logger.info("returnMessage: " + result.getReturnMessage()  );
			}
			return result;
		}
}

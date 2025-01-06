package br.com.crearesistemas.sgf.wssetunloading;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import br.com.crearesistemas.sgf.BaseAuth;

public class WsSetUnloading extends BaseAuth{

	//static String urlBase = "http://sgfqas-sp.bracell.com/Ws_AutomationSGF/WsAutomationSGF";
	static String urlBase = "https://sgfqas-sp.bracell.com/Ws_AutomationSGF/WsAutomationSGF";
	static String appId = "CED41F47-7442-C045-ADAF-2A8EE6416AAF";
	static String secret = "C8A33FBADFB048F1A89B3E9820525C47";

	
	public static SetUnloadingResponse setUnloading(String urlBase, String user, String secret, SetUnloading loading)
	   {
		  SetUnloadingResponse result;
	      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
	      engine.setFollowRedirects(true);
	      Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
	      try
	      {
	    	   String posturl = urlBase + "/SetUnLoading?appId=" + user + "&secret=" + secret;
	    	   Builder target = client
	    			   .target(followRedirectURL(posturl)).request();
		  		
	  		Entity<SetUnloading> entity = Entity.entity(loading, "application/json");
	  		
	  		Response response = target.post(entity);

	  		result = response.readEntity(SetUnloadingResponse.class);
	  		
	         response.close();
	      }
	      finally
	      {
	         client.close();
	      }
	      
	      return result;
	   }
	
	
	public static void main(String[] args) {
		descargaGruaMesa01();
	}
	
	
	 		 
	
	 private static void descargaGruaMesa01() {
				 
			SetUnloading loading = new SetUnloading();
			
			loading.setTruckPlate("GBT2055");
			loading.setReportID("2323925");			
			loading.setCraneCode(479l); // 5005
			
			loading.setChipperCode(4l);   // chipper não pode ser nulo, senão volta com erro
			loading.setPileCode(0l);
			
			loading.setCraneOperatorName("João da Silva");
			loading.setStartingHorimeterNumber("1");
			loading.setEndingHorimeterNumber("200");
			loading.setStartingDate("2021-11-04T12:18:00");
			loading.setEndingDate("2021-11-04T12:21:20");
			
			SetUnloadingResponse result = WsSetUnloading.setUnloading(urlBase, appId, secret, loading);
			
			System.out.println("==== Dados Descarregamento Campo Sgf: ==== " );
			System.out.println("reportID: " + loading.getReportID() );
			System.out.println("truckPlate: " + loading.getTruckPlate()  );
			System.out.println("craneCode: " + loading.getCraneCode()  );
			System.out.println("operatorName: " + loading.getCraneOperatorName()  );
			System.out.println("chipperCode: " + loading.getChipperCode() );
			System.out.println("pileCode: " + loading.getPileCode()  );
			System.out.println("startingDate: " + loading.getStartingDate()  );
			System.out.println("endingDate: " + loading.getEndingDate()  );
			
			System.out.println("--- result sgfUnloading:  " );
			System.out.println("--- result = " + result.toString()  + "---");
			System.out.println("result = " + result.toString() );
	}
		
	
	
	
}

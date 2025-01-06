package br.com.crearesistemas.sgf.wssetequipmentdowntime;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.model.GruaEntity;
import br.com.crearesistemas.sgf.BaseAuth;
import br.com.crearesistemas.sgf.wsgetcranes.SgfCrane;
import br.com.crearesistemas.util.DateUtils;
import br.com.crearesistemas.util.NumberUtils;

/*
 
 Método para realizar o apontamento das paradas de 
 		- caminhões, 
 		- gruas de campo e de 
 		- pátio , registrados pelo sistema Creare.
 
 */

public class WsSetEquipmentDowntime extends BaseAuth {
		private static final Logger logger = Logger.getLogger(WsSetEquipmentDowntime.class);
	
		
		
		public static SetEquipmentDowntimeResponse setEquipmentDowntime(String urlBase, 
				String user, String secret, SetEquipmentDowntime downtime)
	    {
			SetEquipmentDowntimeResponse result = null;
			ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
			engine.setFollowRedirects(true);
			Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
			try {
				try
		      	{
		    	    String posturl = urlBase + "/SetEquipmentDowntime?appId=" + user + "&secret=" + secret;
		    	   
		    	    Builder target = client
		    			   .target(followRedirectURL(posturl)).request();
			  		
			  		Entity<SetEquipmentDowntime> entity = Entity.entity(downtime, "application/json");
			  		
			  		Response response = target.post(entity);
		
			  		result = response.readEntity(SetEquipmentDowntimeResponse.class);
			  		
			        response.close();
		      	}
		      finally
		        {
		    	  if (client != null) client.close();
		        }
			} catch (Exception e) {
				e.printStackTrace();
			};
	            
	      	return result;
	   }
		
		
		public static void main(String[] args) {
			SetEquipmentDowntime downtime = new SetEquipmentDowntime();
			
			/*
			 
			 Indica se é uma parada de 
			 	- Caminhão (“E”), 
			 	- Grua de Pátio (“P”) ou 
			 	- Grua de Campo (”C”).
			 
			 */
			downtime.setTipReport("E");
			
			downtime.setEquipmentPlate("GAB4594");
			downtime.setDowntimeCode(8);
			downtime.setOperatorName("Operador cominhao WS");
			downtime.setStartDownTime("2021-09-09T14:10:00");
			downtime.setEndDownTime("2021-09-09T15:10:00");
			
			// parametros opcionais
			downtime.setLocaleCode(0l);
			downtime.setStartingHorimeterNumber(0d);
			downtime.setEndingHorimeterNumber(0d);
			
			SetEquipmentDowntimeResponse response = setEquipmentDowntime(
					"http://sgfqas-sp.bracell.com/Ws_AutomationSGF/WsAutomationSGF",
					"CED41F47-7442-C045-ADAF-2A8EE6416AAF","C8A33FBADFB048F1A89B3E9820525C47",downtime);
			
			
			System.out.println("ReturnId: " 
					+ response.getReturnId() 
					+ " \n Message:" 
					+ ((response.getReturnMessage() != null)?response.getReturnMessage()[0]:""));
		}
		
}

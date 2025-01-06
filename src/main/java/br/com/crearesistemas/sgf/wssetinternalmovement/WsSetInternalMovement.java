package br.com.crearesistemas.sgf.wssetinternalmovement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import br.com.crearesistemas.config.Config;
import br.com.crearesistemas.sgf.BaseAuth;


public class WsSetInternalMovement extends BaseAuth {
	private static final Logger logger = Logger.getLogger(WsSetInternalMovement.class);
	
		private static void consistenciaQas(SetInternalMovement loading) {
			
			if (loading.getLocaleCode() == null) {
				loading.setLocaleCode(0l); // em desenv, este campo não pode ser nulo	
			}
			
			if (loading.getPileCode() == null) {
				loading.setPileCode(0l); // em desenv, este campo não pode ser nulo	
			}
		}
	
	
		public static String setLoading(String urlBase, String user, String secret, SetInternalMovement loading)
	    {
			if (urlBase.contains("sgfqas")) {
				consistenciaQas(loading);
			}
			
			
			String resultStr = null;
			
			SetInternalMovementResponse result = null;
			ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
			engine.setFollowRedirects(true);
			Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
	      try
	      	{
		    	   String posturl = urlBase + "/SetInternalMovement?appId=" + user + "&secret=" + secret;
		    	   
		    	   Builder target = client
		    			   .target(followRedirectURL(posturl)).request();
			  		
		  		Entity<SetInternalMovement> entity = Entity.entity(loading, "application/json");
		  		
		  		Response response = target.post(entity);
	
		  		String result2 = response.readEntity(String.class);
		  		resultStr = result2;
		  		
		  		if (Config.getInstance().printLogSgf()) {
		  			logger.info("===> Result SetInternalMovement: " + result2);
		  		}
		  		//result = response.readEntity(SetInternalMovementResponse.class);
		         response.close();
	      	}
	      finally
	        {
	    	  client.close();
	        }
	      
	      return resultStr;
	   }
	
		
		public static String setLoadingString(String urlBase, String user, String secret, SetInternalMovement loading)
		   {
			  String result = null;
		      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
		      engine.setFollowRedirects(true);
		      Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
		      try
		      {
		    	   String posturl = urlBase + "/SetInternalMovement?appId=" + user + "&secret=" + secret;
		    	   
		    	   Builder target = client
		    			   .target(posturl).request();
			  		
		  		Entity<SetInternalMovement> entity = Entity.entity(loading, "application/json");
		  		
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
}

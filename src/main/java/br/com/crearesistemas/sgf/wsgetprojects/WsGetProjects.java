package br.com.crearesistemas.sgf.wsgetprojects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import br.com.crearesistemas.sgf.BaseAuth;

public class WsGetProjects extends BaseAuth{

	public static ProjectsResponse getProjects(String urlBase, String user, String secret) {
		
		 ProjectsResponse result;
	      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
	      engine.setFollowRedirects(true);
	      Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
	      try
	      {
	    	  
	    	   String posturl = urlBase + "/GetProjects?appId=" + user + "&secret=" + secret;
	    	   Builder target = client
	    			   .target(followRedirectURL(posturl)).request();
		  	
	    	   // consome
	    	   Response response = target.get();

	  			         
	    	   result = response.readEntity(ProjectsResponse.class);
	  		
	         response.close();
	      }
	      finally
	      {
	         client.close();
	      }
	      
	      return result;

	}
	
	public static void main(String[] args) {
		
		ProjectsResponse lista = getProjects("https://sgfqas-sp.bracell.com/Ws_AutomationSGF/WsAutomationSGF",
				"CED41F47-7442-C045-ADAF-2A8EE6416AAF","C8A33FBADFB048F1A89B3E9820525C47");
		System.out.println("Qtd Projetos: " + lista.size());
	}
	

}

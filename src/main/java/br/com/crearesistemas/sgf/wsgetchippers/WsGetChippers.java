package br.com.crearesistemas.sgf.wsgetchippers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;

import br.com.crearesistemas.sgf.BaseAuth;

public class WsGetChippers extends BaseAuth{

	public static ChippersResponse getChippers(String urlBase, String user, String secret) {
		
		 ChippersResponse result;
	      ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine();
	      engine.setFollowRedirects(true);
	      Client client = ((ResteasyClientBuilder)ClientBuilder.newBuilder()).httpEngine(engine).build();
	      try
	      {
	    	  
	    	   String posturl = urlBase +"/GetChippers"+ "?appId=" + user + "&secret=" + secret;
	    	   Builder target = client
	    			   .target(followRedirectURL(posturl)).request();
		  	
	    	   // consome
	    	   Response response = target.get();

	  			         
	    	   result = response.readEntity(ChippersResponse.class);
	  		
	         response.close();
	      }
	      finally
	      {
	         client.close();
	      }
	      
	      return result;

	}
	//br.com.crearesistemas.sgf.wsgetprojects.ProjectsResponse[0]->br.com.crearesistemas.sgf.wsgetprojects.SgfProject["projectCode"])
	
	public static void main(String[] args) {
		
		ChippersResponse lista = getChippers("http://sgfqas-sp.bracell.com/Ws_AutomationSGF/WsAutomationSGF/GetProjects","CED41F47-7442-C045-ADAF-2A8EE6416AAF","C8A33FBADFB048F1A89B3E9820525C47");
	}
	

}

package br.com.crearesistemas.sgf;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseAuth {
	 private static final int TEMPORARY_REDIRECT = 307;

	    public static String followRedirectURL(String url)
	    {
	        HttpURLConnection con = null;
	        try {
	            con = (HttpURLConnection) new URL(url ).openConnection();
	            con.setInstanceFollowRedirects(false);
	            con.connect();
	            int status = con.getResponseCode();
	            if (status != HttpURLConnection.HTTP_OK) {
	                if (status == HttpURLConnection.HTTP_MOVED_TEMP
	                        || status == HttpURLConnection.HTTP_MOVED_PERM
	                        || status == HttpURLConnection.HTTP_SEE_OTHER
	                        || status == TEMPORARY_REDIRECT
	                )
	                    return con.getHeaderField("Location");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return url;
	    }

}

package br.com.crearesistemas.sgf.soap.wsgetparadas;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(targetNamespace="http://tempuri.org/", name="ws_inflorhub")
public class GetParadasClient extends Service {
	
	public final static QName SERVICE = new QName("http://tempuri.org/", "ws_inflorhub");
	public final static QName PORT = new QName("http://tempuri.org/", "WS_InflorHubSoap");

	public GetParadasClient(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

    public GetParadasClient(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    @WebEndpoint(name = "WS_InflorHubSoap")
    public GetParadasInterface getGetParadasInflor() {
        return super.getPort(PORT, GetParadasInterface.class);
    }
 
}

package es.um.swit.ws;

import java.net.URL;

import javax.xml.namespace.QName;

import es.um.swit.backup.service.GestorRegistros;
import es.um.swit.backup.service.GestorRegistrosService;


public class GestorRegistrosWS {
	
	private static final GestorRegistrosWS instance = new GestorRegistrosWS();
	
	private static GestorRegistros service;
	
	private static final QName SERVICE_NAME = new QName("http://service.backup.swit.um.es/", "GestorRegistrosService");
	
	private GestorRegistrosWS() {
		
	}
	
	public static GestorRegistrosWS getInstance() {
        return instance;
	}
	
	public static GestorRegistros getWsInstance() {
		URL wsdlURL = GestorRegistrosService.WSDL_LOCATION;
		
        GestorRegistrosService ss = new GestorRegistrosService(wsdlURL, SERVICE_NAME);
        
        GestorRegistros service = ss.getGestorRegistrosPort();
        
		return service;
	}
}

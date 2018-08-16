package es.um.swit.utils;

import javax.servlet.http.HttpServletRequest;

import es.um.swit.beans.FicherosEsquemasBean;
import es.um.swit.constantes.ConstantesCadenas;

public class ControllerUtils {
	/**
	 * @param request
	 * @return
	 */
	public static FicherosEsquemasBean getFicherosEsquemasBeanFromSession(HttpServletRequest request) {
		FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
    	if(feb == null) {
    		feb = new FicherosEsquemasBean();
    	}
		return feb;
	}
    
	/**
	 * @param request
	 * @return
	 */
	public static void removeFicherosEsquemasBeanFromSession(HttpServletRequest request) {
		FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
    	if(feb != null) {
    		request.getSession().removeAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
    	}
	}

}

package es.um.swit.backup.utils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import es.um.swit.backup.resources.DescripcionRespuesta;

public class UtilsWs {
	
	/**
	 * Recupera la solicitud del servlet del servicio web.
	 * @param wsContext
	 * @return
	 */
	public static HttpServletRequest getRequest(WebServiceContext wsContext) {
		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest request = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
	
		return request;
	}

	/**
	 * Devuelve la descripción textual del código de respuesta indicado.
	 * @param idCodigoRespuesta
	 * @return
	 */
	public static String recuperarDescripcionCodigo(int idCodigoRespuesta) {
		return DescripcionRespuesta.getDescripcionRespuesta(idCodigoRespuesta);
	}

}

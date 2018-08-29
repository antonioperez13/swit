package es.um.swit.backup.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import es.um.swit.backup.beans.Registro;
import es.um.swit.backup.beans.RespuestaGestorRegistros;
import es.um.swit.backup.beans.RespuestaRecuperarRegistro;
import es.um.swit.backup.service.GestorRegistrosService;


@WebService(targetNamespace = "http://impl.service.backup.swit.um.es/", endpointInterface = "es.um.swit.backup.service.GestorRegistrosService", portName = "GestorRegistrosServiceImplPort", serviceName = "GestorRegistrosServiceImplService")
public class GestorRegistrosServiceImpl implements GestorRegistrosService{
	
	/**
	 * Constructor vacío de la clase.
	 */
	public GestorRegistrosServiceImpl() {}
	
	/** Contexto del servicio web. */
	@Resource
	private static WebServiceContext wsContext;
	
	/**
	 * Devuelve el contexto del servicio web.
	 * @return the wsContext
	 */
	public static WebServiceContext getWsContext() {
		return wsContext;
	}
	
	
	@Override
	public RespuestaGestorRegistros registrarMappings(byte[] mappingsFile) {
		Registro nuevoRegistro = new Registro();
		boolean guardarMappingsCorrecto = nuevoRegistro.saveMappingsFile(mappingsFile);
		
		RespuestaGestorRegistros respuesta;
		if(!guardarMappingsCorrecto) {
			return new RespuestaGestorRegistros(100, nuevoRegistro.getId());
		}
		
		boolean resultadoRegistro = GestorRegistrosSingleton.getInstance().addRegistro(nuevoRegistro);
		
		
		if(resultadoRegistro) {
			respuesta = new RespuestaGestorRegistros(1, nuevoRegistro.getId());
		} else {
			respuesta = new RespuestaGestorRegistros(2, nuevoRegistro.getId());
		}
		
		return respuesta;
	}
	
	@Override
	public RespuestaGestorRegistros reemplazarMappings(byte[] mappingsFile, String idRegistro) {
		Registro registro = GestorRegistrosSingleton.getInstance().getRegistro(idRegistro);
		
		// Caso de error donde no existe un registro con el id dado, se está reemplazando luego debería existir
		if(registro == null) {
			return new RespuestaGestorRegistros(101, idRegistro);
		}
		
		boolean guardarMappingsCorrecto = registro.replaceMappingsFile(mappingsFile);
		
		// Error al guardar el nuevo fichero de mappings
		if(!guardarMappingsCorrecto) {
			return new RespuestaGestorRegistros(102, idRegistro);
		}
		
		// Si llegamos a este punto es porque si había un registro con el id proporcionado
		GestorRegistrosSingleton.getInstance().addRegistro(registro);
		
		return new RespuestaGestorRegistros(2, registro.getId());
	}


	@Override
	public RespuestaRecuperarRegistro recuperarMappings(String idRegistro) {
		Registro registro = GestorRegistrosSingleton.getInstance().getRegistro(idRegistro);
		
		RespuestaRecuperarRegistro respuesta;
		
		if(registro == null) {
			respuesta = new RespuestaRecuperarRegistro(101, idRegistro, null);
		} else {
			byte[] mappingsFile = null;
			
			mappingsFile = registro.retrieveMappingsFile();
			
			if(mappingsFile != null) {
				respuesta = new RespuestaRecuperarRegistro(3, idRegistro, mappingsFile);
			} else {
				respuesta = new RespuestaRecuperarRegistro(103, idRegistro, null);
			}
		}
		
		return respuesta;
	}
	
	@Override
	public boolean borrarRegistro(String idRegistro) {
		return GestorRegistrosSingleton.getInstance().deleteRegistro(idRegistro);
	}

	@Override
	public boolean borrarTodosRegistros() {
		return GestorRegistrosSingleton.getInstance().deleteAllRegistros();
	}

	@Override
	public List<Registro> recuperarTodosRegistros() {
		List<Registro> listaRegistros = new LinkedList<>();
		
		for (Entry<String, Registro> entry : GestorRegistrosSingleton.getInstance().getAllRegistros().entrySet()){
			listaRegistros.add(entry.getValue());
		}
		
		return listaRegistros;
	}


	
	
}

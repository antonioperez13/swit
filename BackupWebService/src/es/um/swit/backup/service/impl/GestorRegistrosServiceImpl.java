package es.um.swit.backup.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.jws.WebService;

import es.um.swit.backup.beans.Registro;
import es.um.swit.backup.beans.RespuestaGestorRegistros;
import es.um.swit.backup.beans.RespuestaRecuperarRegistro;
import es.um.swit.backup.service.GestorRegistrosService;


@WebService(targetNamespace = "http://impl.service.backup.swit.um.es/", endpointInterface = "es.um.swit.backup.service.GestorRegistrosService", portName = "GestorRegistrosServiceImplPort", serviceName = "GestorRegistrosServiceImplService")
public class GestorRegistrosServiceImpl implements GestorRegistrosService{
	
	public GestorRegistrosServiceImpl() {}
	
	@Override
	public RespuestaGestorRegistros registrarMappings(byte[] mappingsFile) {
		Registro nuevoRegistro = new Registro();
		boolean guardarMappingsCorrecto = nuevoRegistro.saveMappingsFile(mappingsFile);
		
		RespuestaGestorRegistros respuesta;
		if(!guardarMappingsCorrecto) {
			return new RespuestaGestorRegistros(1, "No se ha podido guardar el fichero de mappings.", nuevoRegistro.getId());
		}
		
		boolean resultadoRegistro = GestorRegistrosSingleton.getInstance().addRegistro(nuevoRegistro);
		
		
		if(resultadoRegistro) {
			respuesta = new RespuestaGestorRegistros(0, "Ok", nuevoRegistro.getId());
		} else {
			respuesta = new RespuestaGestorRegistros(0, "Se ha sobreescrito el registro con el mismo ID", nuevoRegistro.getId());
		}
		
		return respuesta;
	}
	
	@Override
	public RespuestaGestorRegistros reemplazarMappings(byte[] mappingsFile, String idRegistro) {
		Registro registro = GestorRegistrosSingleton.getInstance().getRegistro(idRegistro);
		
		// Caso de error donde no existe un registro con el id dado, se está reemplazando luego debería existir
		if(registro == null) {
			return new RespuestaGestorRegistros(1, "No había registro con id " + idRegistro, null);
		}
		
		boolean guardarMappingsCorrecto = registro.replaceMappingsFile(mappingsFile);
		
		// Error al guardar el nuevo fichero de mappings
		if(!guardarMappingsCorrecto) {
			return new RespuestaGestorRegistros(2, "No se ha podido guardar el fichero de mappings con id " + idRegistro, null);
		}
		
		// Si llegamos a este punto es porque si había un registro con el id proporcionado
		GestorRegistrosSingleton.getInstance().addRegistro(registro);
		
		return new RespuestaGestorRegistros(0, "Se ha sobreescrito el registro con el mismo ID", registro.getId());
	}


	@Override
	public RespuestaRecuperarRegistro recuperarMappings(String idRegistro) {
		Registro registro = GestorRegistrosSingleton.getInstance().getRegistro(idRegistro);
		
		RespuestaRecuperarRegistro respuesta;
		
		if(registro == null) {
			respuesta = new RespuestaRecuperarRegistro(10, "No existe el registro con id " + idRegistro, "");
		} else {
			byte[] mappingsFile = null;
			
			mappingsFile = registro.retrieveMappingsFile();
			
			if(mappingsFile != null) {
				respuesta = new RespuestaRecuperarRegistro(0, "Ok", idRegistro, mappingsFile);
			} else {
				respuesta = new RespuestaRecuperarRegistro(11, "No se ha encontrado el fichero asociado al registro", idRegistro, null);
			}
		}
		
		return respuesta;
	}

	@Override
	public boolean borrarTodosRegistros() {
		GestorRegistrosSingleton.getInstance().deleteAllRegistros();
		return true;
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

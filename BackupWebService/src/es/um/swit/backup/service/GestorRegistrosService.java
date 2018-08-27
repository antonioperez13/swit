package es.um.swit.backup.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import es.um.swit.backup.beans.Registro;
import es.um.swit.backup.beans.RespuestaGestorRegistros;
import es.um.swit.backup.beans.RespuestaRecuperarRegistro;

@WebService(name = "GestorRegistros", targetNamespace = "http://service.backup.swit.um.es/")
public interface GestorRegistrosService {
	
	@WebResult(name = "respuestaGestorRegistros")
	public RespuestaGestorRegistros registrarMappings(@WebParam(name = "mappingsFile") byte[] mappingsFile);

	@WebResult(name = "respuestaGestorRegistros")
	public RespuestaGestorRegistros reemplazarMappings(@WebParam(name = "mappingsFile") byte[] mappingsFile, @WebParam(name = "idRegistro") String idRegistro);

	@WebResult(name = "respuestaRecuperarRegistros")
	public RespuestaRecuperarRegistro recuperarMappings(@WebParam(name = "idRegistro") String idRegistro);

	@WebResult(name = "todosRegistrosBorrados")
	public boolean borrarTodosRegistros();
	
	@WebResult(name = "listaRegistros")
	public List<Registro> recuperarTodosRegistros();

}

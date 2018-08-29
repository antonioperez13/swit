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
	
	/**
	 * Crea un nuevo registro y guarda el fichero de mappings asociado.
	 * @param mappingsFile
	 * @return
	 */
	@WebResult(name = "respuestaGestorRegistros")
	public RespuestaGestorRegistros registrarMappings(@WebParam(name = "mappingsFile") byte[] mappingsFile);

	/**
	 * Reemplaza un registro existente, reemplazando el fichero de mappings asociado.
	 * @param mappingsFile
	 * @param idRegistro
	 * @return
	 */
	@WebResult(name = "respuestaGestorRegistros")
	public RespuestaGestorRegistros reemplazarMappings(@WebParam(name = "mappingsFile") byte[] mappingsFile, @WebParam(name = "idRegistro") String idRegistro);

	/**
	 * Recupera el fichero de mappings asociado al registro indicado.
	 * @param idRegistro
	 * @return
	 */
	@WebResult(name = "respuestaRecuperarRegistros")
	public RespuestaRecuperarRegistro recuperarMappings(@WebParam(name = "idRegistro") String idRegistro);
	
	/**
	 * Borra el registro indicado y su fichero asociado.
	 * @return
	 */
	@WebResult(name = "borrarRegistro")
	public boolean borrarRegistro(@WebParam(name = "idRegistro") String idRegistro);
	
	/**
	 * Borra todos los registros y sus ficheros asociados.
	 * @return
	 */
	@WebResult(name = "todosRegistrosBorrados")
	public boolean borrarTodosRegistros();
	
	/**
	 * Devuelve los identificadores de todos los registros existentes.
	 * @return
	 */
	@WebResult(name = "listaRegistros")
	public List<Registro> recuperarTodosRegistros();

}

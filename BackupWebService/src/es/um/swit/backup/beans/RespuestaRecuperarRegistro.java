package es.um.swit.backup.beans;

import java.io.Serializable;

import es.um.swit.backup.utils.UtilsWs;

public class RespuestaRecuperarRegistro extends RespuestaGestorRegistros implements Serializable{
	
	/** */
	private static final long serialVersionUID = 6700553222838982760L;
	
	/** Contenido del fichero de mapeos en forma de bytes */
	private byte[] mappingsFile;
	
	/**
	 * Constructor vacío de la clase
	 */
	public RespuestaRecuperarRegistro() {}
	
	/**
	 * Constructor de la clase
	 * @param idCodigo
	 * @param idRegistro
	 * @param mappingsFile
	 * @param descripcionCodigo
	 */
	public RespuestaRecuperarRegistro(int idCodigo, String idRegistro, byte[] mappingsFile, String descripcionCodigo) {
		super(idCodigo, idRegistro, descripcionCodigo);
		
		
		this.mappingsFile = mappingsFile;
	}
	
	/**
	 * Constructor de la clase
	 * La descripcion textual del codigo de respuesta se asigna según el propio código.
	 * @param idCodigo
	 * @param idRegistro
	 * @param mappingsFile
	 */
	public RespuestaRecuperarRegistro(int idCodigo, String idRegistro, byte[] mappingsFile) {
		super(idCodigo, idRegistro, UtilsWs.recuperarDescripcionCodigo(idCodigo));
		
		this.mappingsFile = mappingsFile;
	}
	
	/**
	 * @return the mappingsFile
	 */
	public byte[] getMappingsFile() {
		return mappingsFile;
	}
	/**
	 * @param mappingsFile the mappingsFile to set
	 */
	public void setMappingsFile(byte[] mappingsFile) {
		this.mappingsFile = mappingsFile;
	}
	
	
}

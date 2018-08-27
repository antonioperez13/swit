package es.um.swit.backup.beans;

import java.io.Serializable;

public class RespuestaRecuperarRegistro extends RespuestaGestorRegistros implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6700553222838982760L;
	
	
	private byte[] mappingsFile;
	
	public RespuestaRecuperarRegistro() {}
	
	public RespuestaRecuperarRegistro(int idCodigo, String descripcionCodigo, String idRegistro, byte[] mappingsFile) {
		super(idCodigo, descripcionCodigo, idRegistro);
		
		
		this.mappingsFile = mappingsFile;
	}
	
	public RespuestaRecuperarRegistro(int idCodigo, String descripcionCodigo, String idRegistro) {
		this(idCodigo, descripcionCodigo, idRegistro, null);
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

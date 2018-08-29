package es.um.swit.backup.beans;

import java.io.File;
import java.io.Serializable;

import es.um.swit.backup.utils.FileUtils;

public class Registro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2428485036237383954L;
	
	/** Identificador del registro (nombre del fichero de mappings asociado */
	private String id;
	
	/**
	 * Constructor vacío de la clase
	 */
	public Registro() {}
	
	/**
	 * Constructor de la clase
	 * @param id
	 */
	public Registro(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Guarda el fichero de mappings asignándole como nombre el identificador
	 * del registro.
	 * @param mappingsFile
	 * @return
	 */
	public boolean saveMappingsFile(byte[] mappingsFile) {
		this.id = FileUtils.asignarIdRegistro(mappingsFile);
		boolean resultado = FileUtils.saveMappingsFile(mappingsFile, this.id);
		
		return resultado;
	}
	
	/**
	 * Reemplaza el fichero de mappings asociado al registro.
	 * @param mappingsFile
	 * @return
	 */
	public boolean replaceMappingsFile(byte[] mappingsFile) {
		boolean resultado = FileUtils.saveMappingsFile(mappingsFile, this.id);
		
		return resultado;
	}
	
	/**
	 * Elimina el fichero de mappings asociado al registro.
	 * @return
	 */
	public boolean deleteMappingsFile() {
		boolean resultado = true;
		File archivo = new File(FileUtils.getMappingsFolder(), id);
		
		if(archivo.exists()) {
			// Lista de ficheros presentes en el directorio
			resultado = archivo.delete();
		}
		
		return resultado;
	}

	/**
	 * Devuelve el contenido del fichero de mappings asociado al registro.
	 * @return
	 */
	public byte[] retrieveMappingsFile() {
		byte[] mappingsFile = null;
		
		mappingsFile = FileUtils.loadMappingsFile(this.id);
		
		return mappingsFile;
	}
}

package es.um.swit.backup.beans;

import java.io.Serializable;

import es.um.swit.backup.utils.FileUtils;

public class Registro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2428485036237383954L;
	
	private String id;
	
	public Registro() {}
	
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
	
	public boolean saveMappingsFile(byte[] mappingsFile) {
		this.id = FileUtils.asignarIdRegistro(mappingsFile);
		boolean resultado = FileUtils.saveMappingsFile(mappingsFile, this.id);
		
		return resultado;
	}
	
	public boolean replaceMappingsFile(byte[] mappingsFile) {
		boolean resultado = FileUtils.saveMappingsFile(mappingsFile, this.id);
		
		return resultado;
	}

	public byte[] retrieveMappingsFile() {
		byte[] mappingsFile = null;
		
		mappingsFile = FileUtils.loadMappingsFile(this.id);
		
		return mappingsFile;
	}
}

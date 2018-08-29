package es.um.swit.backup.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import es.um.swit.backup.beans.Registro;
import es.um.swit.backup.utils.FileUtils;

public class GestorRegistrosSingleton {
	
	/** Instancia de la clase */
	private static final GestorRegistrosSingleton instance = new GestorRegistrosSingleton();
	
	/** Mapa de los registros existentes en el servidor */
	private Map<String, Registro> catalogoRegistros = new HashMap<String, Registro>();
	
	/**
	 * Constructor de la clase.
	 */
	private GestorRegistrosSingleton(){
		// Se recuperan los registros que se hubieran guardado anteriormente
		recuperarCatalogoRegistros();
	}
	
	/**
	 * Devuelve la instancia de la clase.
	 * @return
	 */
	public static GestorRegistrosSingleton getInstance() {
        return instance;
	}
	
	/**
	 * Añade un nuevo registro al catalogo.
	 * @param nuevoRegistro
	 * @return true si es un nuevo registro, false si había un registro con ese identificador
	 */
	public boolean addRegistro(Registro nuevoRegistro) {
		Registro anteriorRegistro = catalogoRegistros.put(nuevoRegistro.getId(), nuevoRegistro);
		
		return anteriorRegistro == null;
	}
	
	/**
	 * Devuelve el registro con el id indicado, o null si no existe.
	 * @param idRegistro
	 * @return
	 */
	public Registro getRegistro(String idRegistro) {
		return catalogoRegistros.get(idRegistro);
	}
	
	/**
	 * Devuelve todos los registros guardados.
	 * @return
	 */
	public Map<String, Registro> getAllRegistros(){
		return catalogoRegistros;
	}
	
	/**
	 * Guarda en un fichero todos los registros
	 * @return
	 */
	public boolean recuperarCatalogoRegistros() {
		// Directorio donde se guardan los ficheros de mappings
		File directorio = new File(FileUtils.getMappingsFolder());
		
		if(directorio.exists()) {
			try {
				// Lista de ficheros presentes en el directorio
				File[] files = directorio.listFiles();
	
				// Se crea un registro por cada fichero
				for (File file : files) {
					catalogoRegistros.put(file.getName(), new Registro(file.getName()));
				}
				
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		return false;
	}
	
	/**
	 * Elimina todos los registros del catalogo.
	 * @return true si todos los registros y sus ficheros han sido borrados,
	 * false si al menos un fichero no se ha podido eliminar
	 */
	public boolean deleteAllRegistros() {
		boolean todosBorrados = true;
		
		try {
			File directorio = new File(FileUtils.getMappingsFolder());
			
			if(directorio.exists()) {
				// Lista de ficheros presentes en el directorio
				File[] files = directorio.listFiles();
	
				// Se borra cada fichero del directorio
				for (File file : files) {
					// Se elimina el fichero
					todosBorrados = file.delete() && todosBorrados;
				}
			}
			catalogoRegistros.clear();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return todosBorrados;
	}

	/**
	 * Elimina el registro del catálogo y su fichero asociado.
	 * @param idRegistro
	 * @return true si se borran el registro y su fichero, false en caso contrario.
	 */
	public boolean deleteRegistro(String idRegistro) {
		boolean resultado = false;
		
		Registro registro = catalogoRegistros.remove(idRegistro);
		
		if(registro != null) {
			resultado = registro.deleteMappingsFile();
		}
		
		return resultado;
	}
}

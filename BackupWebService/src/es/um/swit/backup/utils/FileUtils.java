package es.um.swit.backup.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class FileUtils {
	
	/** Nombre del directorio donde se guardan los ficheros con los mappings */
	public static final String MAPPINGS_FOLDER = "/SwitMappingsBackup/switMappings/";
	
	/**
	 * Devuelve la ruta donde se almacenarán los ficheros de mappings.
	 * @return
	 */
	public static String getMappingsFolder() {
		String ruta = MAPPINGS_FOLDER;
		while(ruta.contains("/")) {
			ruta = ruta.replace("/", File.separator);
		}
		return ruta;
	}
	
	/**
	 * Devuelve un identificador único para un fichero usando el SHA1 del fichero 
	 * y el instante en milisegundos
	 * @param file
	 * @return
	 */
	public static String asignarIdRegistro(byte[] file) {
		return calcSHA1(file) + System.currentTimeMillis();
	}
	
	/**
	 * Devuelve una cadena que representa el SHA1 del fichero.
	 * En caso de error al crear el SHA1 devuelve la cadena vacía.
	 * @param file
	 * @return
	 */
	public static String calcSHA1(byte[] file) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			return new HexBinaryAdapter().marshal(sha1.digest(file));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	/**
	 * Guarda en un fichero con el nombre indicado los bytes que corresponden al archivo con los mappings.
	 * @param mappingsFileBytes
	 * @param fileName
	 * @return
	 */
	public static boolean saveMappingsFile(byte[] mappingsFileBytes, String fileName) {
		boolean resultado = false;
		
		String mappingsFolderRoute = FileUtils.getMappingsFolder();
		
		 // Crea la carpeta de subidas en el servidor
		File mappingsFolder = new File(mappingsFolderRoute);
        if(! mappingsFolder.exists()){
        	mappingsFolder.mkdirs();
        }
		
        File mappingsFile = new File(mappingsFolderRoute, fileName);
        
        /* Si el fichero ya existía se borra dado que:
         * 1- Si se está reemplazando es necesario guardar el nuevo contenido
         * 2- Si es nuevo no debería haber un fichero con el mismo nombre
         */
        if(mappingsFile.exists()) {
        	mappingsFile.delete();
        }
		
		try {
			InputStream input = new ByteArrayInputStream(mappingsFileBytes);
			Files.copy(input, mappingsFile.toPath());
			
			resultado = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

	/**
	 * Devuelve los bytes que corresponden a un archivo de mappings con el id indicado.
	 * @param id
	 * @return Bytes del fichero de mappings o null si no existe el fichero con dicho id.
	 */
	public static byte[] loadMappingsFile(String id) {
		// TODO Implementar loadMappingsFile
		byte[] mappingsFileBytes = null;
		
		String mappingsFolder = FileUtils.getMappingsFolder();
		
		if(!new File(mappingsFolder).exists()){
			return null;
		}
		
		File mappingsFile = new File(mappingsFolder, id);
		
		try {
			mappingsFileBytes = Files.readAllBytes(mappingsFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return mappingsFileBytes;
	}
}

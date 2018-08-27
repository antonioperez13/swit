package es.um.swit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.springframework.web.multipart.MultipartFile;

import commons.entrada.ProcessOwl;
import commons.entrada.ProcessXsd;
import commons.tree.NodeOwl;
import commons.tree.NodeXsd;
import es.um.swit.beans.FicherosEsquemasBean;
import es.um.swit.constantes.ConstantesErrores;
import es.um.swit.constantes.FileUploadConstants;
import es.um.swit.enums.TipoFichero;

public class FilesUtils {
	
	/**
	 * Devuelve la ruta donde se almacenarán los ficheros temporales.
	 * @return
	 */
	public static String getTempRoute() {
		return File.separator + FileUploadConstants.TEMP_FOLDER_NAME + File.separator;
	}
	
	/**
	 * Dado un fichero XSD trata de parsearlo a un árbol de nodos para su uso.
	 * En el caso de que el resultado del parseo sea nulo o no tenga nodos
	 * hijos se considera que no se ha podido parsear. 
	 * @param fichero
	 * @return
	 */
	public static NodeXsd parsearXsd(File fichero) {
		
		if(fichero == null || fichero.length() <= 0) {
			return null;
		}
		
		NodeXsd arbol;
		try {
			arbol = ProcessXsd.getSchemasStructure(fichero);
		} catch (Exception e) {
			return null;
		}
		
		if(arbol != null &&
			arbol.hasChildren()) {
			return arbol;
		}
		
		return null;
	}
	
	/**
	 * Dado un fichero OWL trata de parsearlo a un árbol de nodos para su uso.
	 * En el caso de que el resultado del parseo sea nulo o no tenga nodos
	 * hijos se considera que no se ha podido parsear. 
	 * @param fichero
	 * @return
	 */
	public static NodeOwl parsearOwl(File fichero) {
		
		if(fichero == null || fichero.length() <= 0) {
			return null;
		}
		
		NodeOwl arbol;
		try {
			arbol = ProcessOwl.getOwlStructure(fichero);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(arbol != null &&
			arbol.hasChildren()) {
			return arbol;
		}
		
		return null;
	}

	/**
	 * Trata los ficheros e intenta parsearlos. Los posibles errores resultantes se añaden
	 * al objeto devuelto por el método.
	 * @param feb
	 */
	public static Error parsearFicheros(FicherosEsquemasBean feb) {
		Error error = new Error();
		
		tratarFicheroSource(feb, error);
		
		tratarFicheroTarget(feb, error);
		
		return error;
	}

	private static void tratarFicheroSource(FicherosEsquemasBean feb, Error error) {
		if(feb.getSourceFileType() == TipoFichero.XSD) {
			NodeXsd.resetIdCount();
			tratarFicheroXsd(feb, error);
		}
		
		// Reservado para otros tipos de fichero
		// else if(feb.getSourceFileType() == TipoFichero.) {
	}
	
	private static void tratarFicheroTarget(FicherosEsquemasBean feb, Error error) {
		NodeOwl.resetIdCount();
		tratarFicheroOwl(feb, error);
		
	}

	/**
	 * @param feb
	 * @param error
	 */
	private static void tratarFicheroXsd(FicherosEsquemasBean feb, Error error) {
		File xsdFile = feb.getSourceFile();
		
		if(xsdFile != null && xsdFile.length() > 0) {
			NodeXsd tree = FilesUtils.parsearXsd(xsdFile);
	        
			if(tree != null) {
				//System.out.println(tree.treeToString(0) + "\n");
				feb.setSourceTree(tree);
			} else {
				error.addError(ConstantesErrores.ERROR_PARSEO_FICHERO_XSD);
			}
		} else {
			if(feb.getSourceTree() == null) {
				error.addError(ConstantesErrores.ERROR_FICHERO_XSD);
			}
		}
	}
	
	/**
	 * @param feb
	 * @param error
	 */
	private static void tratarFicheroOwl(FicherosEsquemasBean feb, Error error) {
		File owlFile = feb.getTargetFile();
			
		if(owlFile != null && owlFile.length() > 0) {
			NodeOwl tree = FilesUtils.parsearOwl(owlFile);
	        
			if(tree != null) {
				//System.out.println(tree.treeToString(0) + "\n");
				feb.setTargetTree(tree);
			} else {
				error.addError(ConstantesErrores.ERROR_PARSEO_FICHERO_OWL);
			}
		} else {
			if(feb.getTargetTree() == null) {
				error.addError(ConstantesErrores.ERROR_FICHERO_OWL);
			}
		}
	}
	
	
	public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
	    File convFile = new File(multipart.getOriginalFilename());
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	public static File convert(MultipartFile file)
	{    
	    File convFile = new File(file.getOriginalFilename());
	    try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(file.getBytes());
		    fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    
	    return convFile;
	}
	
	
	/**
	 * Devuelve una cadena que representa el SHA1 del fichero.
	 * En caso de error al crear el SHA1 devuelve la cadena vacía.
	 * @param file
	 * @return
	 */
	public static String calcSHA1(File file) {
		try {
			
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			InputStream input = new FileInputStream(file);
		
			byte[] buffer = new byte[8192];
			int len = input.read(buffer);

			while (len != -1) {
				sha1.update(buffer, 0, len);
				len = input.read(buffer);
			}

			return new HexBinaryAdapter().marshal(sha1.digest());
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}

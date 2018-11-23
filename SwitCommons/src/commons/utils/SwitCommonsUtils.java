package commons.utils;

public class SwitCommonsUtils {
	
	/**
	 * Divide la cadena dada por el caracter "#" y se queda con la última subcadena, que
	 * salvo rara excepción coincide con el nombre corto que identifica al elemento 
	 * @param uri
	 * @return El último elemento de la cadena si ésta se ha podido dividir por "#", 
	 * la cadena sin procesar en caso contrario
	 */
    public static String extractElementStringFromUri(String uri) {
    	String extract;
    	
    	String[] nodo = uri.split("#");
        if(nodo.length > 1) {
        	extract = nodo[1].substring(0, nodo[1].length());
        } else {
        	extract = uri;
        }
        
        return extract;
    }
	
	/**
	 * Divide la cadena dada por el caracter "#" y se queda con la última subcadena, que
	 * salvo rara excepción coincide con el nombre corto que identifica al elemento 
	 * @param element
	 * @return El último elemento de la cadena si ésta se ha podido dividir por "#", 
	 * la cadena sin procesar en caso contrario
	 */
    public static String extractElementString(String element) {
    	String extract;
    	
    	String[] nodo = element.split("#");
        if(nodo.length > 1) {
        	extract = nodo[1].substring(0, nodo[1].length()-1);
        } else {
        	extract = element;
        }
        
        return extract;
    }
    
    /**
     * Elimina los signos "<" y ">" del principio y el final de una URI extraída
     * de una ontología.
     * @param uri
     * @return
     */
    public static String deleteUriComparatorsBrackets(String uri) {
		return uri.replace("<", "").replace(">", "");
	}
    
    /**
     * Dada una ruta cuyos elementos están separados por el separador indicado devuelve
     * el último elemento de la ruta.
     * @param route Ruta con elementos
     * @param separator Caracter separador de elementos en la ruta
     * @return Último elemento de la ruta
     */
    public static String lastRouteElement(String route, String separator) {
    	String[] elements = route.split(separator);
    	int lastElement = elements.length;
    	
    	// Si el último elemento está vacío es porque la ruta terminaba en "/"
    	// luego nos quedamos con el anterior del array
    	if("".equals(elements[lastElement])) {
    		return elements[lastElement-1];
    	} else { // Si no está vacío lo devolvemos
    		return elements[lastElement];
    	}
    }
    
    /**
     * Dada una ruta cuyos elementos están separados por "/" devuelve
     * el último elemento de la ruta.
     * @param route Ruta con elementos
     * @return Último elemento de la ruta
     */
    public static String lastRouteElement(String route) {
    	String[] elements = route.split("/");
    	int lastElement = elements.length-1;
    	
    	// Si el último elemento está vacío es porque la ruta terminaba en "/"
    	// luego nos quedamos con el anterior del array
    	if("".equals(elements[lastElement])) {
    		return elements[lastElement-1];
    	} else { // Si no está vacío lo devolvemos
    		return elements[lastElement];
    	}
    }
}

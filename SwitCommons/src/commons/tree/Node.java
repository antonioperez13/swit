package commons.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Node{
   
	/** Nombre del nodo */
	private String name;
	
	/** Identificador del nodo */
	private final int id;

	/**
	 * Constructor de la clase añadiendo el nombre del nodo
	 * @param name
	 */
	public Node(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Constructor vacío. El nombre del nodo queda vacío.
	 */
	public Node(int id) {
		this("", id);
	}

	/**
	 * Recupera el nombre del nodo
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del nodo
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Recupera el identificador del nodo
	 * @return 
	 */
	public int getId() {
		return id;
	}
	
	public abstract Node getChild(String childName);
	
	/**
	 * Dada una ruta del árbol en forma de lista de cadenas busca el nodo que le corresponde.
	 * En caso de llegar a un punto muerto o que no exista el nodo devuelve null.
	 * @param routeList
	 * @return El nodo que corresponde a la ruta o null si no existe.
	 */
	private Node getNodeFromRoute(List<String> routeList) {
		Node node = null;
		
		// Si quedan elementos por recorrer en la ruta
		if(routeList.size() > 0) {
			// Buscamos si hay un hijo con el nombre del primer elemento
			// de la lista
			Node child = this.getChild(routeList.get(0));
			// Si hay un hijo con el nombre buscado
			if(child != null) {
				// Quitamos el primer elemento de la lista
				routeList.remove(0);
				
				// Llamamos al método recursivo
				node = child.getNodeFromRoute(routeList);
			}
			// Si no hubiera un hijo significaría que no existe la ruta en el árbol
			// y por tanto debe devolver null
		} else {
			// Si no quedaban elementos en la lista es que éste
			// era el nodo que buscábamos
			return this;
		}
		
		return node;
	}
	
	/**
	 * Dada una ruta del árbol busca el nodo que le corresponde.
	 * En caso de llegar a un punto muerto o que no exista el nodo devuelve null.
	 * @param route
	 * @return El nodo que corresponde a la ruta o null si no existe.
	 */
	public Node getNodeFromRoute(String route) {
		// Separamos los elementos de la ruta
		String[] routeArray = route.split("/");
		
		// Los pasamos a una lista y llamamos al método recursivo para encontrar el nodo
		List<String> routeList = new ArrayList<>(Arrays.asList(routeArray));
		
		Node node = null;
		
		// Si la ruta tiene al menos un elemento y el primer elemento coincide con éste nodo
		if(routeList.size() > 0 && this.getName().equals(routeList.get(0))) {
			// Quitamos el primer elemento que ya sabemos que está
			routeList.remove(0);
			
			// Llamamos al método recursivo
			node = this.getNodeFromRoute(routeList);
		}
		
		return node;
	}
	
	/**
	 * Genera la ruta en el árbol desde la raíz hasta el nodo invocador.
	 * La ruta consta de los nombres de cada nodo.
	 * @return
	 */
	public abstract String getRuta();
	
	/**
	 * Devuelve el nodo que se corresponda al identificador dado.
	 * @param id
	 * @return
	 */
	public abstract Node getNodeById(int id);
	
	
	/**
	 * Muestra los nombres de los hijos con el texto formateado con tabuladores,
	 * tantos niveles como sea la profundidad en el árbol dada.
	 * @param depth
	 * @return
	 */
	public abstract String treeToString(int depth);
	
	/**
	 * Muestra la estructura del árbol en forma de texto tabulado, usando
	 * los nombres de los nodos.
	 * @return
	 */
	public String treeToString() {
		return treeToString(0);
	}
	
	/**
	 * Devuelve una cadena de tantos bloques de espacios en blanco como 
	 * indique la variable dado como parámetro
	 * @param depth
	 * @return
	 */
	protected static String printIndent(int depth) {
        String indent = "";
		for (int i = 0; i < depth; i++) {
            indent += "    ";
        }
		return indent;
    }
	
	/**
	 * Devuelve un string para incluir en un HTML de forma que el árbol aparezca como una lista.
	 * @param depth
	 * @param routeUpHere Ruta en el árbol de nodos hasta el nodo invocador
	 * @return
	 */
	public abstract String treeToHtml(int depth, String routeUpHere);
	
	/**
	 * Devuelve un string para incluir en un HTML de forma que el árbol aparezca como una lista.
	 * @return
	 */
	public String treeToHtmlList(int depth) {
		return "<ul>\n" + treeToHtml(depth, "") + "\n</ul>\n";
	}
	
	/**
	 * Devuelve el número de elementos que contiene, iterando de forma recursiva en los nodos
	 * hijos si los tiene.
	 * @return
	 */
	public abstract int getTotalNumElements();
	
}

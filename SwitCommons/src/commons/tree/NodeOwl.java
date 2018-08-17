package commons.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;

public class NodeOwl extends Node{
	
	/** Nodos hijos */
    private List<NodeOwl> children;
    /** Nodo padre */
    private NodeOwl parent;
    /** Nombre representativo del objeto en la ontología */
	private String representativeName;
	/** Tipo de objeto en la ontología */
	private OwlType type;
	/** Variable usada para asignar el identificador a un nuevo nodo */
	private static int nextId = 0;
	
	/**
	 * Constructor de la clase.
	 * Añade el nombre del nodo, el nombre completo en la ontología, el tipo 
	 * de elemento en la ontología y el nodo padre.
	 * @param name
	 * @param representativeName
	 * @param type
	 * @param parent
	 */
	public NodeOwl(String name, String representativeName, OwlType type, NodeOwl parent) {
		super(name, NodeOwl.nextId++);
    	this.representativeName = representativeName; 
    	this.type = type;
    	this.parent = parent;
    	this.children = new ArrayList<>();
    }
	
	/**
	 * Constructor de la clase.
	 * Añade el nombre del nodo, el nombre completo en la ontología, el tipo 
	 * de elemento en la ontología y el nodo padre queda a nulo.
	 * @param name
	 * @param representativeName
	 * @param type
	 */
    public NodeOwl(String name, String representativeName, OwlType type) {
        this(name, representativeName, type, null);
    }
    
    /**
     * Constructor vacío.
     * El nombre y el nombre en la ontología quedan vacíos.
     */
    public NodeOwl() {
    	this("", "", null, null);
    }
    
    /**
     * Añade un nuevo nodo como hijo dados su nombre, su nombre en la ontología
     * y el tipo de dato en la ontología.
     * @param name
     * @param representativeName
     * @param type
     */
    public void addChild(String name, String representativeName, OwlType type) {
    	NodeOwl child = new NodeOwl(name, representativeName, type, this);
	    this.children.add(child);
    }
    
    /**
     * Añade el nodo dado como hijo.
     * @param child
     */
    public void addChild(NodeOwl child) {
	    child.setParent(this);
	    this.children.add(child);
	}
	
    /**
     * Añade todos los nodos dados como hijos.
     * @param children
     */
	public void addChildren(List<NodeOwl> children) {
	    for(NodeOwl t : children) {
	        t.setParent(this);
	    }
	    this.children.addAll(children);
	}
	
	/**
	 * Indica si el nodo tiene hijos.
	 * @return
	 */
	public boolean hasChildren() {
		return !this.children.isEmpty();
	}
	
	/**
	 * Recupera una lista con los nodos hijos.
	 * @return
	 */
	public List<NodeOwl> getChildren() {
	    return this.children;
	}
	
	/**
	 * Busca entre los hijos del nodo si alguno coincide
	 * con el nombre dado y lo devuelve.
	 * @param nodeName
	 * @return El nodo encontrado o null.
	 */
	@Override
	public NodeOwl getChild(String nodeName){
		for(NodeOwl child : children) {
			if(child.getName().equals(nodeName)) {
				return child;
			}
		}
		return null;
	}
	
	/**
	 * Establece el nodo padre.
	 * @param parent
	 */
	public void setParent(NodeOwl parent) {
	    this.parent = parent;
	}
	
	/**
	 * Recupera el nodo padre.
	 * @return
	 */
	public NodeOwl getParent() {
	    return parent;
	}
	
	/**
	 * Elimina el nodo padre.
	 */
	public void removeParent() {
		this.parent = null;
	}
	
	/**
	 * Indica si tiene un hijo con el nombre dado.
	 * @param name
	 * @return
	 */
	public NodeOwl hasChild(String name) {
		for(NodeOwl child : this.children) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		return null;
	}
	
	@Override
	public NodeOwl getNodeById(int id) {
		if(this.getId() == id) {
			return this;
		}
		
		NodeOwl nodo = null;
		for(NodeOwl child : this.children) {
			nodo = child.getNodeById(id);
			if(nodo != null) {
				return nodo;
			}
		}
		
		return null;
	}

	/**
	 * Recupera el nombre del elemento en la ontología.
	 * @return
	 */
	public String getRepresentativeName() {
		return representativeName;
	}

	/**
	 * Establece el nombre del elemento en la ontología.
	 * @param representativeName
	 */
	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}

	/**
	 * Recupera el tipo del elemento en la ontología.
	 * @return
	 */
	public OwlType getType() {
		return type;
	}

	/**
	 * Establece el tipo del elemento en la ontología.
	 * @param type
	 */
	public void setType(OwlType type) {
		this.type = type;
	}
	
	/**
	 * Busca el primer nodo con el nombre dado desde el nodo invocador
	 * @param name
	 * @return
	 */
	public NodeOwl inDepthSearch(String name) {
		if(this.getName().equals(name)) {
			return this;
		}
		
		if(!this.children.isEmpty()) {
	    	for(NodeOwl child : this.children) {
	    		return child.inDepthSearch(name);
	    	}
		}
		
		return null;
	}
	
	/**
	 * Busca en todos los nodos del árbol aquellos cuyo nombre
	 * es igual que el dado como parámetro y los devuelve en
	 * forma de lista
	 * @param name
	 * @return
	 */
	public List<NodeOwl> getNodesWithName(String name){
		ArrayList<NodeOwl> nodo = new ArrayList<>();
		
		if(name.equals(this.getName().toString())) {
			nodo.add(this);
		}
		
		if(this.hasChildren()) {
			Collections.sort(this.children, NodeComparator.compareByName());
	    	for(NodeOwl child : this.children) {
	    		nodo.addAll(child.getNodesWithName(name));
	    	}
		}
		
		return nodo;
	}
	
	/**
	 * Dado el nombre de un nodo devolverá la ruta en forma de string de
	 * la primera rama que vaya desde el nodo que invoca el método hasta
	 * el primer nodo que coincida con el nombre buscado.
	 * La ruta será null en caso de no existir el nodo con dicho nombre.
	 * @param nodeName
	 * @return
	 */
//	@Override
//	public String getRoute(String nodeName) {
//		StringBuilder builder = new StringBuilder();
//		
//		builder.append("/");
//		builder.append(this.getName());
//		// Si este era el nodo buscado se devuelve
//		if(nodeName.equals(this.getName().toString())) {
//			return builder.toString();
//		}
//		
//		String childRoute = null;
//		// Si no era este no era el nodo se busca entre sus hijos
//		if(this.hasChildren()) {
//	    	for(NodeOwl child : this.children) {
//	    		childRoute = child.getRoute(nodeName);
//	    		// Si el hijo devuelve una cadena distinta de null significa que
//	    		// se ha llegado al nodo correcto, por lo que se devuelve la ruta
//	    		if(childRoute != null) {
//	    			builder.append(childRoute);
//	    			return builder.toString();
//	    		}
//	    	}
//		}
//		
//		// En caso de no ser el nodo buscado y no tener hijos devuelve null
//		return null;
//	}
	
	/**
	 * Genera la ruta en el árbol desde la raíz hasta el nodo invocador.
	 * La ruta consta de los nombres de cada nodo.
	 * @return
	 */
	@Override
	public String getRuta() {
		StringBuilder builder = new StringBuilder();
		
		if(this.parent != null) {
			builder.append(this.parent.getRuta());
		}
		
		builder.append("/");
		builder.append(this.getName());
		
		return builder.toString();
	}
	
	/**
	 * Muestra los nombres de los hijos con el texto formateado con tabuladores,
	 * tantos como sea la profundidad en el árbol.
	 * @param depth
	 * @return
	 */
	@Override
	public String treeToString(int depth) {
		StringBuilder builder = new StringBuilder();
		builder.append("(" + type.getCode() + "): ").append(this.getName()).append(" (id=" + this.getId() + ")");
		builder.append("\n");
		if(!this.children.isEmpty()) {
			Collections.sort(this.children, NodeComparator.compareByNameOwl());
			for(NodeOwl child : this.children) {
				builder.append(printIndent(depth+1));
				builder.append(child.treeToString(depth+1));
			}
		}
		
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Collections.sort(this.children, NodeComparator.compareByName());
		builder.append("Nodo [name=");
		builder.append(this.getName());
		builder.append(", children=");
		builder.append(children);
		if(parent != null) {
			builder.append(", parent=");
			builder.append(parent.getName());
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String treeToHtml(int depth, String routeUpHere) {
		StringBuilder builder = new StringBuilder();
		// INI - Declaracion de caracteristicas de jstree
		
		// Determina si el nodo aparece o no desplegado
		if(depth == 4) builder.append("<li data-jstree='{\"opened\":true}'");
		else builder.append("<li data-jstree='{\"opened\":false, ");
		
		// Icono del elemento
		builder.append("\"icon\":\"").append(getOwlIconName(type)).append("\"}' ");
		
		// Tipo de elemento OWL
		builder.append("tipo-elemento=\"").append(type.getCode()).append("\" ");
		
		// Identificador del elemento
		builder.append("id-elemento=\"").append(this.getId()).append("\" ");
		
		// Ruta al elemento en el árbol
		String route = routeUpHere + this.getName() + "/";
		builder.append("ruta-elemento=\"").append(route).append("\" ");
		
		builder.append("\">");
		// FIN - Declaracion de caracteristicas de jstree
		
		// TODO No haría falta ya que los iconos dan esta información
		//builder.append("(" + type.getCode() + "): ");
		
		String entityName = StringEscapeUtils.escapeHtml4(this.getName());
		
		builder.append(entityName);
		
		if(!this.children.isEmpty()) {
			builder.append("<ul>");
			Collections.sort(this.children, NodeComparator.compareByNameOwl());
			for(NodeOwl child : this.children) {
				//builder.append(printIndent(depth+1));
				builder.append(child.treeToHtml(depth+1, route));
			}
			builder.append("</ul>");
		}
		builder.append("</li>");
		
		return builder.toString();
	}
	
	/**
	 * Dado un tipo de elemento OWL devuelve un string con la ruta y nombre del
	 * icono que se ajuste a dicho tipo.
	 * @param type
	 * @return
	 */
	private String getOwlIconName(OwlType type) {
		String icono = "/swit-web/resources/icons/owlType_";
		String tipo;
		if(type.equals(OwlType.CLASS)) {
			tipo =  "class";
		} else if(type.equals(OwlType.INDIVIDUAL)){
			tipo =  "individual";
		} else if(type.equals(OwlType.DATATYPE)){
			tipo =  "datatype";
		} else if(type.equals(OwlType.PROPERTY)){
			tipo =  "property";
		} else {
			tipo =  "void";
		}
		
		return icono + tipo + ".png";
	}

	public String treeToHtmlDebug(int depth) {
		StringBuilder builder = new StringBuilder();
		builder.append(printIndent(depth)).append("<li data-jstree='{\"opened\":true}'>");
		builder.append("(" + type.getCode() + "): ");
		
		String entityName = StringEscapeUtils.escapeHtml4(this.getName());
		
		String route = "/" + this.getName();
		
		builder.append(entityName);
		
		if(!this.children.isEmpty()) {
			builder.append("\n").append(printIndent(depth+1)).append("<ul>").append("\n");
			Collections.sort(this.children, NodeComparator.compareByNameOwl());
			for(NodeOwl child : this.children) {
				//builder.append(printIndent(depth+1));
				builder.append(child.treeToHtml(depth+1, route)).append("\n");
			}
			builder.append(printIndent(depth)).append("</ul>");
		}
		builder.append("</li>");
		
		return builder.toString();
	}

	@Override
	public int getTotalNumElements() {
		// Añade el número de hijos
		int total = children.size();
		
		// Itera por los hijos, recuperando el número de elementos en cada uno
		for(NodeOwl hijo : children) {
			total += hijo.getTotalNumElements();
		}
		
		return total;
	}
	
	/**
	 * Reinicia el contador que asigna identificadores a los nodos que se crean.
	 */
	public static void resetIdCount() {
		NodeOwl.nextId = 0;
	}
	
}

package commons.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;

public class NodeXsd extends Node{
	
	/** Nodos hijos */
    private List<NodeXsd> children;
    /** Nodo padre */
    private NodeXsd parent;
    /** Variable usada para asignar el identificador a un nuevo nodo */
	private static int nextId = 0;
	
	private String value;
    
    /**
     * Constructor de la clase.
     * Añade el nombre del nodo y el padre de éste.
     * @param name
     * @param parent
     */
    public NodeXsd(String name, String value, NodeXsd parent) {
    	super(name, NodeXsd.nextId++);
    	this.parent = parent;
    	this.value = value;
    	this.children = new ArrayList<>();
    }
    
    /**
     * Constructor de la clase.
     * Añade el nombre del nodo y el padre queda a null.
     * @param name
     */
    public NodeXsd(String name, String value) {
        this(name, value, null);
    }
    
    /**
     * Constructor de la clase.
     * Añade el nombre del nodo y el padre queda a null.
     * @param name
     */
    public NodeXsd(String name) {
        this(name, null, null);
    }
    
    /**
     * Constructor vacío.
     * El nombre del nodo queda vacío.
     */
    public NodeXsd() {
    	this("", null);
    }

    /**
     * Añade el nodo dado como hijo
     * @param child
     */
    public void addChild(NodeXsd child) {
        child.setParent(this);
        this.children.add(child);
    }

    /**
     * Añade un nuevo nodo dado su nombre
     * @param nombre
     */
    public void addChild(String nombre) {
        NodeXsd newChild = new NodeXsd(nombre);
        newChild.setParent(this);
        this.children.add(newChild);
    }

    /**
     * Añade los nodos de la lista dada como hijos
     * @param children
     */
    public void addChildren(List<NodeXsd> children) {
        for(NodeXsd t : children) {
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
    public List<NodeXsd> getChildren() {
        return this.children;
    }
    
    /**
     * Devuelve al primer hijo directo cuyo nombre coincida con el dado por parámetro.
     * @param nodeName
     * @return El nodo hijo si hay coincidencia o null en caso contrario.
     */
    @Override
    public NodeXsd getChild(String nodeName){
    	for(NodeXsd child : children) {
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
    public void setParent(NodeXsd parent) {
        this.parent = parent;
    }

    /**
     * Recupera el nodo padre.
     * @return
     */
    public NodeXsd getParent() {
        return parent;
    }
    
    /**
     * Elimina el nodo padre.
     */
    public void removeParent() {
    	this.parent = null;
    }
    
    /**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
    
    /**
     * Comprueba si existe un nodo hijo con el nombre dado.
     * @param name
     * @return
     */
    public NodeXsd hasChild(String name) {
    	for(NodeXsd child : this.children) {
    		if(child.getName().equals(name)) {
    			return child;
    		}
    	}
    	return null;
    }
    
    @Override
	public NodeXsd getNodeById(int id) {
		if(this.getId() == id) {
			return this;
		}
		
		NodeXsd nodo = null;
		for(NodeXsd child : this.children) {
			nodo = child.getNodeById(id);
			if(nodo != null) {
				return nodo;
			}
		}
		
		return null;
	}
    
    /**
     * Busca el primer nodo con el nombre dado desde el nodo invocador
     * @param name
     * @return
     */
    public NodeXsd inDepthSearch(String name) {
    	if(this.getName().equals(name)) {
    		return this;
    	}
    	
    	if(!this.children.isEmpty()) {
	    	for(NodeXsd child : this.children) {
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
    public List<NodeXsd> getNodesWithName(String name){
    	ArrayList<NodeXsd> nodos = new ArrayList<>();
    	
    	if(name.equals(this.getName().toString())) {
    		nodos.add(this);
    	}
    	
    	if(this.hasChildren()) {
    		Collections.sort(this.children, NodeComparator.compareByName());
	    	for(NodeXsd child : this.children) {
	    		nodos.addAll(child.getNodesWithName(name));
	    	}
    	}
    	
    	return nodos;
    }
    
    /**
     * Dado el nombre de un nodo devolverá la ruta en forma de string de
     * la primera rama que vaya desde el nodo que invoca el método hasta
     * el primer nodo que coincida con el nombre buscado.
     * La ruta será null en caso de no existir el nodo con dicho nombre.
     * @param nodeName
     * @return
     */
//    @Override
//    public String getRoute(String nodeName) {
//    	StringBuilder builder = new StringBuilder();
//    	
//    	builder.append("/");
//    	builder.append(this.getName());
//    	// Si este era el nodo buscado se devuelve
//    	if(nodeName.equals(this.getName().toString())) {
//    		return builder.toString();
//    	}
//    	
//    	String childRoute = null;
//    	// Si no era este no era el nodo se busca entre sus hijos
//    	if(this.hasChildren()) {
//	    	for(NodeXsd child : this.children) {
//	    		childRoute = child.getRoute(nodeName);
//	    		// Si el hijo devuelve una cadena distinta de null significa que
//	    		// se ha llegado al nodo correcto, por lo que se devuelve la ruta
//	    		if(childRoute != null) {
//	    			builder.append(childRoute);
//	    			return builder.toString();
//	    		}
//	    	}
//    	}
//    	
//    	// En caso de no ser el nodo buscado y no tener hijos devuelve null
//    	return null;
//    }
    
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
		builder.append("{").append(this.getName()).append(" (id=" + this.getId() + ")");
		
		if(!this.children.isEmpty()) {
			Collections.sort(this.children, NodeComparator.compareByName());
			builder.append("\n");
			for(NodeXsd child : this.children) {
				builder.append(printIndent(depth+1));
				builder.append(child.treeToString(depth+1)).append("\n");
			}
			builder.append(printIndent(depth));
		}
		
		
		builder.append("}");
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
		builder.append("<li data-jstree='{\"opened\":true}' ");
		
		// Identificador del elemento
		builder.append("id-elemento=\"").append(this.getId()).append("\" ");
		
		// Ruta al elemento en el árbol
		String route = routeUpHere + this.getName() + "/";
		builder.append("ruta-elemento=\"").append(route).append("\" ");
		
		// Nombre del elemento
		builder.append("nombre-elemento=\"").append(this.getName()).append("\" ");
		
		if(this.getValue() != null && !this.getValue().equals("")) {
			// Valor del elemento
			builder.append("valor-elemento=\"").append(this.getValue()).append("\" ");
		}
		
		builder.append(">");
		// FIN - Declaracion de caracteristicas de jstree
		
		String entityName = this.getName();
		if(this.getValue() != null && !this.getValue().equals("")) {
			entityName += " : " + this.getValue();
		}
		entityName = StringEscapeUtils.escapeHtml4(entityName);
		
		builder.append(entityName);
		
		if(!this.children.isEmpty()) {
			builder.append("<ul>");
			Collections.sort(this.children, NodeComparator.compareByName());
			for(NodeXsd child : this.children) {
				builder.append(child.treeToHtml(depth+1, route));
			}
			builder.append("</ul>");
		}
		
		builder.append("</li>");
		
		return builder.toString();
	}
	
	public String treeToHtmlDebug(int depth) {
		StringBuilder builder = new StringBuilder();
		builder.append(printIndent(depth)).append("<li data-jstree='{\"opened\":true}'>");
		
		String entityName = StringEscapeUtils.escapeHtml4(this.getName());
		
		String route = "/" + this.getName();
		
		builder.append(entityName);
		
		if(!this.children.isEmpty()) {
			builder.append("\n").append(printIndent(depth+1)).append("<ul>").append("\n");
			Collections.sort(this.children, NodeComparator.compareByName());
			for(NodeXsd child : this.children) {
//				builder.append(printIndent(depth+1));
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
		for(NodeXsd hijo : children) {
			total += hijo.getTotalNumElements();
		}
		
		return total;
	}

	/**
	 * Reinicia el contador que asigna identificadores a los nodos que se crean.
	 */
	public static void resetIdCount() {
		NodeXsd.nextId = 0;
	}
	
}

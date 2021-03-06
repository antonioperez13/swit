package commons.reglas;

import java.io.Serializable;

public class Elemento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1904228229393710496L;
	
	/** Nombre del elemento */
	private String name;
	/** Identificador del elemento */
	private int id;
	/** Ruta del elemento en el árbol de nodos. Cada nodo está separado por "/" */
	private String route;
	/** Tipo del elemento (si aplica al esquema de datos) */
	private String type;
	/** URI del elemento (si aplica al esquema de datos) */
	private String uri;
	
	public Elemento(String name, int id, String route, String type, String uri) {
		this.name = name;
		this.id = id;
		this.route = route;
		this.type = type;
		this.uri = uri;
	}
	
	public Elemento(String name, String type, String uri) {
		this(name, -1, null, type, uri);
	}
	
	public Elemento(String name, int id, String route) {
		this(name, id, route, null, null);
	}
	
	public Elemento(String name) {
		this(name, -1, null, null, null);
	}
	
	public Elemento() {};

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elemento other = (Elemento) obj;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		return true;
	}
	
}

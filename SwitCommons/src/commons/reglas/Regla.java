package commons.reglas;

public class Regla {
	
	/** Variable usada para asignar el identificador a una nueva regla */
	private static int nextId = 1;
	
	private static final String DOMAIN_NODE_SOURCE = "Domain node (Source):\t\t";
	private static final String DOMAIN_CLASS_TARGET = "Domain class (Target):\t\t";
	
	private static final String RANGE_NODE_SOURCE = "Range node (Source):\t\t";
	private static final String RANGE_CLASS_TARGET = "Range class (Target):\t\t";
	
	private static final String PROPERTY_VALUE_SOURCE = "Property value (Source):\t";
	private static final String PROPERTY_TARGET = "Property (Target):\t\t";
	
	private Integer id;

	private TipoRegla tipo;
	
	
	private Elemento domainNodeSource;
	
	private Elemento rangeNodeSource;
	
	private Elemento propertyValueSource;
	
	
	private Elemento domainClassTarget;
	
	private Elemento rangeClassTarget;
	
	private Elemento propertyTarget;
	
	/**
	 * Constructor vacío de la clase.
	 * Todos los atributos quedan vacíos.
	 */
	public Regla() {}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Asigna un identificador a la regla si no lo tiene
	 * @return El identificador de la regla
	 */
	public Integer setId() {
		if(this.id == null) {
			this.id = Regla.nextId;
			Regla.nextId++;
		}
		
		return this.id;
	}
	
	/**
	 * @return the tipo
	 */
	public TipoRegla getTipo() {
		return tipo;
	}
	
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoRegla tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the domainNodeSource
	 */
	public Elemento getDomainNodeSource() {
		return domainNodeSource;
	}
	
	/**
	 * @param domainNodeSource the domainNodeSource to set
	 */
	public void setDomainNodeSource(Elemento domainNodeSource) {
		this.domainNodeSource = domainNodeSource;
	}
	
	/**
	 * @return the rangeNodeSource
	 */
	public Elemento getRangeNodeSource() {
		return rangeNodeSource;
	}
	
	/**
	 * @param rangeNodeSource the rangeNodeSource to set
	 */
	public void setRangeNodeSource(Elemento rangeNodeSource) {
		this.rangeNodeSource = rangeNodeSource;
	}
	
	/**
	 * @return the propertyValueSource
	 */
	public Elemento getPropertyValueSource() {
		return propertyValueSource;
	}
	
	/**
	 * @param propertyValueSource the propertyValueSource to set
	 */
	public void setPropertyValueSource(Elemento propertyValueSource) {
		this.propertyValueSource = propertyValueSource;
	}
	
	/**
	 * @return the domainClassTarget
	 */
	public Elemento getDomainClassTarget() {
		return domainClassTarget;
	}
	
	/**
	 * @param domainClassTarget the domainClassTarget to set
	 */
	public void setDomainClassTarget(Elemento domainClassTarget) {
		this.domainClassTarget = domainClassTarget;
	}
	
	/**
	 * @return the rangeClassTarget
	 */
	public Elemento getRangeClassTarget() {
		return rangeClassTarget;
	}
	
	/**
	 * @param rangeClassTarget the rangeClassTarget to set
	 */
	public void setRangeClassTarget(Elemento rangeClassTarget) {
		this.rangeClassTarget = rangeClassTarget;
	}
	
	/**
	 * @return the propertyTarget
	 */
	public Elemento getPropertyTarget() {
		return propertyTarget;
	}

	/**
	 * @param propertyTarget the propertyTarget to set
	 */
	public void setPropertyTarget(Elemento propertyTarget) {
		this.propertyTarget = propertyTarget;
	}
	
	
	/************************************************************
	 *  Funciones para añadir los elementos del esquema origen
	 ************************************************************/
	
	/**
	 * Añade el domainNodeSource a la regla
	 * @param name
	 * @param id
	 * @param route
	 * @param type
	 */
	public void addDomainNodeSource(String name, int id, String route, String type) {
		this.domainNodeSource = new Elemento(name, id, route, type);
	}
	
	/**
	 * Añade el domainNodeSource a la regla
	 * @param name
	 * @param id
	 * @param route
	 * @param type
	 */
	public void addRangeNodeSource(String name, int id, String route, String type) {
		this.rangeNodeSource = new Elemento(name, id, route, type);
	}
	
	/**
	 * Añade el domainNodeSource a la regla
	 * @param name
	 * @param id
	 * @param route
	 * @param type
	 */
	public void addPropertyValueSource(String name, int id, String route, String type) {
		this.propertyValueSource = new Elemento(name, id, route, type);
	}
	
	/************************************************************
	 *  Funciones para añadir los elementos del esquema destino
	 ************************************************************/
	
	/**
	 * Añade el domainNodeSource a la regla
	 * @param name
	 * @param id
	 * @param route
	 * @param type
	 */
	public void addDomainClassTarget(String name, int id, String route, String type) {
		this.domainClassTarget = new Elemento(name, id, route, type);
	}
	
	/**
	 * Añade el domainNodeSource a la regla
	 * @param name
	 * @param id
	 * @param route
	 * @param type
	 */
	public void addRangeClassTarget(String name, int id, String route, String type) {
		this.rangeClassTarget = new Elemento(name, id, route, type);
	}
	
	/**
	 * Añade el domainNodeSource a la regla
	 * @param name
	 * @param id
	 * @param route
	 * @param type
	 */
	public void addPropertyTarget(String name, int id, String route, String type) {
		this.propertyTarget = new Elemento(name, id, route, type);
	}
	
	
	/************************************************************
	 *  Utilidades
	 ************************************************************/
	
	@Override
	public String toString() {
		StringBuilder regla = new StringBuilder();
		
		regla.append("Id = " + this.id + "\n");
		
		switch(this.tipo) {
		case CLASE:
			regla.append(reglaClaseToString());
			break;
		case PROPIEDAD:
			regla.append(reglaPropiedadToString());
			break;
		case RELACION:
			regla.append(reglaRelacionToString());
			break;
		default:
			regla.append("ERROR: La regla no se ajusta a ningún tipo.");
		}
		
		return regla.toString();
	}

	private String reglaClaseToString() {
		StringBuilder regla = new StringBuilder();
		
		regla.append(reglaLineToString(DOMAIN_NODE_SOURCE, domainNodeSource.getName()));
		regla.append(reglaLineToString(DOMAIN_CLASS_TARGET, domainClassTarget.getName()));
		
		return regla.toString();
	}
	
	private String reglaPropiedadToString() {
		StringBuilder regla = new StringBuilder();
		
		regla.append(reglaClaseToString());
		
		regla.append(reglaLineToString(PROPERTY_VALUE_SOURCE, propertyValueSource.getName()));
		regla.append(reglaLineToString(PROPERTY_TARGET, propertyTarget.getName()));
		
		return regla.toString();
	}
	
	private String reglaRelacionToString() {
		StringBuilder regla = new StringBuilder();
		
		regla.append(reglaClaseToString());
		
		regla.append(reglaLineToString(RANGE_NODE_SOURCE, rangeNodeSource.getName()));
		regla.append(reglaLineToString(RANGE_CLASS_TARGET, rangeClassTarget.getName()));
		
		regla.append(reglaLineToString(PROPERTY_TARGET, propertyTarget.getName()));
		
		return regla.toString();
	}
	
	/**
	 * Devuelve una cadena en una línea describiendo un atributo.
	 * @param tipoAtributo
	 * @param valor
	 * @return
	 */
	private String reglaLineToString(String tipoAtributo, String valor) {
		return tipoAtributo + valor + "\n";
	}
	
	public String toHtmlString() {
		String texto = this.toString();
		texto = texto.replaceAll("\n", "<br>");
		return texto;
	}
	
	public String comprobarTipoRegla(){
		// Sea cual sea la regla, debe tener informados estos dos campos
		if(this.domainNodeSource != null && this.domainClassTarget != null){
			
			// Si tiene informado este campo la regla puede ser de propiedad o de relación
			if(this.propertyTarget !=null){
				// Si tiene rangeNodeSource y rangeClassTarget pero no propertyValueSource es una regla de relacion
				if(this.rangeNodeSource != null && this.rangeClassTarget != null &&
						this.propertyValueSource == null){
					if(this.tipo == TipoRegla.RELACION){
						return "Regla de relación: correcta";
					}
				} 
				// Si no tiene rangeNodeSource y rangeClassTarget pero si propertyValueSource es una regla de propiedad
				else if(this.rangeNodeSource == null && this.rangeClassTarget == null &&
						this.propertyValueSource != null){
					if(this.tipo == TipoRegla.PROPIEDAD){
						return "Regla de propiedad: correcta";
					}
				}
			}
			// Si no cumplía alguno de los otros casos solo queda comprobar que el tipo de regla de clase sea correcto
			else if(this.tipo == TipoRegla.CLASE){
				return "Regla de clase: correcta";
			}
		}
		return "Regla incorrecta";
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
		Regla other = (Regla) obj;
		if (domainClassTarget == null) {
			if (other.domainClassTarget != null)
				return false;
		} else if (!domainClassTarget.equals(other.domainClassTarget))
			return false;
		if (domainNodeSource == null) {
			if (other.domainNodeSource != null)
				return false;
		} else if (!domainNodeSource.equals(other.domainNodeSource))
			return false;
		if (propertyTarget == null) {
			if (other.propertyTarget != null)
				return false;
		} else if (!propertyTarget.equals(other.propertyTarget))
			return false;
		if (propertyValueSource == null) {
			if (other.propertyValueSource != null)
				return false;
		} else if (!propertyValueSource.equals(other.propertyValueSource))
			return false;
		if (rangeClassTarget == null) {
			if (other.rangeClassTarget != null)
				return false;
		} else if (!rangeClassTarget.equals(other.rangeClassTarget))
			return false;
		if (rangeNodeSource == null) {
			if (other.rangeNodeSource != null)
				return false;
		} else if (!rangeNodeSource.equals(other.rangeNodeSource))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
}

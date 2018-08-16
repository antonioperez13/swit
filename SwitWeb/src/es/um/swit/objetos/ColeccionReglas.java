package es.um.swit.objetos;

import java.util.ArrayList;
import java.util.List;

import commons.reglas.Regla;

public class ColeccionReglas {
	
	/** Lista de reglas */
	private List<Regla> reglas;
	
	
	/**
	 * Constructor de la clase.
	 * Inicializa la lista de reglas.
	 */
	public ColeccionReglas() {
		reglas = new ArrayList<>();
	}
	
	/**
	 * Añade una regla.
	 * @param nueva
	 * @return
	 */
	public boolean addRegla(Regla nueva) {
		return reglas.add(nueva);
	}
	
	/**
	 * Crea y añade una regla de clase.
	 * @param domainNodeId
	 * @param domainClassId
	 * @return
	 */
//	public boolean addReglaClase(String domainNodeId, String domainClassId) {
//		return addRegla(new Regla(domainNodeId, domainClassId));
//	}
	
	/**
	 * Crea y añade una regla de propiedad.
	 * @param domainNodeId
	 * @param domainClassId
	 * @param propertyValueSourceId
	 * @param propertyTargetId
	 * @return
	 */
//	public boolean addReglaPropiedad(String domainNodeId, String domainClassId,
//			String propertyValueSourceId, String propertyTargetId) {
//		return addRegla(new Regla(domainNodeId, domainClassId, propertyValueSourceId, propertyTargetId));
//	}
	
	/**
	 * Crea y añade una regla de relación.
	 * @param domainNodeId
	 * @param domainClassId
	 * @param rangeNodeId
	 * @param rangeClassId
	 * @param property
	 * @return
	 */
//	public boolean addReglaRelacion(String domainNodeId, String domainClassId,
//			String rangeNodeId, String rangeClassId,
//			String property) {
//		return addRegla(new Regla(domainNodeId, domainClassId, rangeNodeId, rangeClassId, property));
//	}
	
	/**
	 * Recupera una lista con todas las reglas.
	 * @return
	 */
	public List<Regla> getAllReglas(){
		return new ArrayList<>(reglas);
	}
	
	/**
	 * Elimina todas las reglas.
	 */
	public void removeAllReglas() {
		reglas.clear();
	}
}

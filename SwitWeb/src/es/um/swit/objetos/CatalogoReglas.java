package es.um.swit.objetos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import commons.reglas.Regla;

public class CatalogoReglas {
	
//	private static CatalogoReglas unicaInstancia = null;
	
	/** Lista de reglas */
	private List<Regla> reglas;
	
	/**
	 * Devuelve la instancia del catalogo.
	 * @return
	 */
//	public static CatalogoReglas getInstantcia() {
//		if(unicaInstancia == null) {
//			unicaInstancia = new CatalogoReglas();
//		}
//		return unicaInstancia;
//	}
	
	/**
	 * Constructor de la clase.
	 * Inicializa la lista de reglas.
	 */
	public CatalogoReglas() {
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
	 * Comprueba si una regla ya existe en el catálogo de reglas y la devuelve.
	 * La comprobación de si la regla existe se hace según los elementos que componen
	 * la regla exceptuando el Id.
	 * @param regla
	 * @return La regla o null si no existe
	 */
	public Regla recuperarRegla(Regla regla) {
		for(Regla r : reglas) {
			if(r.equals(regla)) {
				return r;
			}
		}
		
		return null;
	}
	
	/**
	 * Elimina del catálogo la regla cuyo identificador coincida
	 * con el pasado por parámetro.
	 * @param id
	 * @return La regla eliminada, null si no existía
	 */
	public Regla removeReglaPorId(int id) {
		Iterator<Regla> reglasIterator = reglas.iterator();
		while (reglasIterator.hasNext()) {
			Regla regla = reglasIterator.next();
			if(regla != null && id == regla.getId()) {
				reglasIterator.remove();
				return regla;
			}
		}
		
		return null;
	}
	
	/**
	 * Elimina todas las reglas.
	 */
	public void removeAllReglas() {
		reglas.clear();
	}
}

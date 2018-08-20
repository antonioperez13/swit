package es.um.swit.objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import commons.reglas.Regla;

public class CatalogoReglas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8186230406314824055L;
	
	/** Lista de reglas */
	private List<Regla> reglas;
	
	
	/**
	 * Constructor de la clase.
	 * Inicializa la lista de reglas.
	 */
	public CatalogoReglas() {
		reglas = new ArrayList<>();
	}
	
	public CatalogoReglas(CatalogoReglas otroCatalogo) {
		reglas = new ArrayList<>(otroCatalogo.getAllReglas());
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

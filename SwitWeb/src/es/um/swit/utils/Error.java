package es.um.swit.utils;

import java.util.ArrayList;

public class Error {
	private ArrayList<String> descError;
	
	/**
	 * Constructor de la clase.
	 * Inicializa el error a false.
	 */
	public Error() {
		descError = new ArrayList<>();
	}

	/**
	 * @return the errors
	 */
	public boolean hasErrors() {
		return !this.descError.isEmpty();
	}

	/**
	 * @return the descError
	 */
	public ArrayList<String> getDescError() {
		return descError;
	}

	/**
	 * @param descError the descError to set
	 */
	public void setDescError(ArrayList<String> descError) {
		this.descError = descError;
	}
	
	/**
	 * Añade un error al conjunto de errores.
	 * @param newError
	 */
	public void addError(String newError) {
		this.descError.add(newError);
	}
	
}

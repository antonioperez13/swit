package es.um.swit.backup.beans;

import java.io.Serializable;

import es.um.swit.backup.utils.UtilsWs;

public class RespuestaGestorRegistros implements Serializable{
	
	/** */
	private static final long serialVersionUID = 5558803155935505030L;
	
	/** Código que identifica el resultado. */
	private int idCodigo;
	/** Descripción textual del resultado. */
	private String descripcionCodigo;
	/** Identificador del registro implicado. */
	private String idRegistro;
	
	/**
	 * Constructor vacío de la clase
	 */
	public RespuestaGestorRegistros() {}
	
	/**
	 * Constructor de la clase.
	 * @param idCodigo
	 * @param idRegistro
	 * @param descripcionCodigo
	 */
	public RespuestaGestorRegistros(int idCodigo, String idRegistro, String descripcionCodigo) {
		this.idCodigo = idCodigo;
		this.idRegistro = idRegistro;
		this.descripcionCodigo = descripcionCodigo;
	}
	
	/**
	 * Constructor de la clase.
	 * La descripcion textual del codigo de respuesta se asigna según el propio código.
	 * @param idCodigo
	 * @param idRegistro
	 */
	public RespuestaGestorRegistros(int idCodigo, String idRegistro) {
		this(idCodigo, idRegistro, UtilsWs.recuperarDescripcionCodigo(idCodigo));
	}
	
	/**
	 * @return the idCodigo
	 */
	public int getIdCodigo() {
		return idCodigo;
	}
	/**
	 * @param idCodigo the idCodigo to set
	 */
	public void setIdCodigo(int idCodigo) {
		this.idCodigo = idCodigo;
	}
	/**
	 * @return the descripcionCodigo
	 */
	public String getDescripcionCodigo() {
		return descripcionCodigo;
	}
	/**
	 * @param descripcionCodigo the descripcionCodigo to set
	 */
	public void setDescripcionCodigo(String descripcionCodigo) {
		this.descripcionCodigo = descripcionCodigo;
	}
	
	/**
	 * @return the idRegistro
	 */
	public String getIdRegistro() {
		return idRegistro;
	}
	/**
	 * @param idRegistro the idRegistro to set
	 */
	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}
	
}

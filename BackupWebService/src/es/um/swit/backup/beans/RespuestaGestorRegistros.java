package es.um.swit.backup.beans;

import java.io.Serializable;

public class RespuestaGestorRegistros implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5558803155935505030L;
	
	private int idCodigo;
	private String descripcionCodigo;
	private String idRegistro;
	
	public RespuestaGestorRegistros() {}
	
	public RespuestaGestorRegistros(int idCodigo, String descripcionCodigo, String idRegistro) {
		this.idCodigo = idCodigo;
		this.descripcionCodigo = descripcionCodigo;
		this.idRegistro = idRegistro;
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

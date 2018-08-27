
package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respuestaGestorRegistros complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="respuestaGestorRegistros"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descripcionCodigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idCodigo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="idRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaGestorRegistros", propOrder = {
    "descripcionCodigo",
    "idCodigo",
    "idRegistro"
})
@XmlSeeAlso({
    RespuestaRecuperarRegistro.class
})
public class RespuestaGestorRegistros {

    protected String descripcionCodigo;
    protected int idCodigo;
    protected String idRegistro;

    /**
     * Obtiene el valor de la propiedad descripcionCodigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionCodigo() {
        return descripcionCodigo;
    }

    /**
     * Define el valor de la propiedad descripcionCodigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionCodigo(String value) {
        this.descripcionCodigo = value;
    }

    /**
     * Obtiene el valor de la propiedad idCodigo.
     * 
     */
    public int getIdCodigo() {
        return idCodigo;
    }

    /**
     * Define el valor de la propiedad idCodigo.
     * 
     */
    public void setIdCodigo(int value) {
        this.idCodigo = value;
    }

    /**
     * Obtiene el valor de la propiedad idRegistro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRegistro() {
        return idRegistro;
    }

    /**
     * Define el valor de la propiedad idRegistro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRegistro(String value) {
        this.idRegistro = value;
    }

}

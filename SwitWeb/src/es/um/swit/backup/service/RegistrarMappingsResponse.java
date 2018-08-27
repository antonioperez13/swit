
package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registrarMappingsResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registrarMappingsResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="respuestaGestorRegistros" type="{http://service.backup.swit.um.es/}respuestaGestorRegistros" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarMappingsResponse", propOrder = {
    "respuestaGestorRegistros"
})
public class RegistrarMappingsResponse {

    protected RespuestaGestorRegistros respuestaGestorRegistros;

    /**
     * Obtiene el valor de la propiedad respuestaGestorRegistros.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaGestorRegistros }
     *     
     */
    public RespuestaGestorRegistros getRespuestaGestorRegistros() {
        return respuestaGestorRegistros;
    }

    /**
     * Define el valor de la propiedad respuestaGestorRegistros.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaGestorRegistros }
     *     
     */
    public void setRespuestaGestorRegistros(RespuestaGestorRegistros value) {
        this.respuestaGestorRegistros = value;
    }

}

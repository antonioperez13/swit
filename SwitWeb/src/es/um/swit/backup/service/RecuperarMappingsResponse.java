
package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para recuperarMappingsResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="recuperarMappingsResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="respuestaRecuperarRegistros" type="{http://service.backup.swit.um.es/}respuestaRecuperarRegistro" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperarMappingsResponse", propOrder = {
    "respuestaRecuperarRegistros"
})
public class RecuperarMappingsResponse {

    protected RespuestaRecuperarRegistro respuestaRecuperarRegistros;

    /**
     * Obtiene el valor de la propiedad respuestaRecuperarRegistros.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaRecuperarRegistro }
     *     
     */
    public RespuestaRecuperarRegistro getRespuestaRecuperarRegistros() {
        return respuestaRecuperarRegistros;
    }

    /**
     * Define el valor de la propiedad respuestaRecuperarRegistros.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaRecuperarRegistro }
     *     
     */
    public void setRespuestaRecuperarRegistros(RespuestaRecuperarRegistro value) {
        this.respuestaRecuperarRegistros = value;
    }

}

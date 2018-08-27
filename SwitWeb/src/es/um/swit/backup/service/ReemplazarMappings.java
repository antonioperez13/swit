
package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para reemplazarMappings complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="reemplazarMappings"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mappingsFile" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
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
@XmlType(name = "reemplazarMappings", propOrder = {
    "mappingsFile",
    "idRegistro"
})
public class ReemplazarMappings {

    protected byte[] mappingsFile;
    protected String idRegistro;

    /**
     * Obtiene el valor de la propiedad mappingsFile.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getMappingsFile() {
        return mappingsFile;
    }

    /**
     * Define el valor de la propiedad mappingsFile.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setMappingsFile(byte[] value) {
        this.mappingsFile = value;
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

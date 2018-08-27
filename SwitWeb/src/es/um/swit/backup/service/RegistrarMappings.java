
package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registrarMappings complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registrarMappings"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="mappingsFile" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarMappings", propOrder = {
    "mappingsFile"
})
public class RegistrarMappings {

    protected byte[] mappingsFile;

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

}


package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para borrarRegistroResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="borrarRegistroResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="borrarRegistro" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "borrarRegistroResponse", propOrder = {
    "borrarRegistro"
})
public class BorrarRegistroResponse {

    protected boolean borrarRegistro;

    /**
     * Obtiene el valor de la propiedad borrarRegistro.
     * 
     */
    public boolean isBorrarRegistro() {
        return borrarRegistro;
    }

    /**
     * Define el valor de la propiedad borrarRegistro.
     * 
     */
    public void setBorrarRegistro(boolean value) {
        this.borrarRegistro = value;
    }

}

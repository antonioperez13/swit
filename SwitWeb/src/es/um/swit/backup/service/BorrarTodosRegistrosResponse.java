
package es.um.swit.backup.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para borrarTodosRegistrosResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="borrarTodosRegistrosResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="todosRegistrosBorrados" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "borrarTodosRegistrosResponse", propOrder = {
    "todosRegistrosBorrados"
})
public class BorrarTodosRegistrosResponse {

    protected boolean todosRegistrosBorrados;

    /**
     * Obtiene el valor de la propiedad todosRegistrosBorrados.
     * 
     */
    public boolean isTodosRegistrosBorrados() {
        return todosRegistrosBorrados;
    }

    /**
     * Define el valor de la propiedad todosRegistrosBorrados.
     * 
     */
    public void setTodosRegistrosBorrados(boolean value) {
        this.todosRegistrosBorrados = value;
    }

}

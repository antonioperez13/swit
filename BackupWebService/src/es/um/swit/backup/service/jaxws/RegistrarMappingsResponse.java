
package es.um.swit.backup.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.2.6
 * Sun Aug 26 20:48:02 CEST 2018
 * Generated source version: 3.2.6
 */

@XmlRootElement(name = "registrarMappingsResponse", namespace = "http://service.backup.swit.um.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarMappingsResponse", namespace = "http://service.backup.swit.um.es/")

public class RegistrarMappingsResponse {

    @XmlElement(name = "respuestaGestorRegistros")
    private es.um.swit.backup.beans.RespuestaGestorRegistros respuestaGestorRegistros;

    public es.um.swit.backup.beans.RespuestaGestorRegistros getRespuestaGestorRegistros() {
        return this.respuestaGestorRegistros;
    }

    public void setRespuestaGestorRegistros(es.um.swit.backup.beans.RespuestaGestorRegistros newRespuestaGestorRegistros)  {
        this.respuestaGestorRegistros = newRespuestaGestorRegistros;
    }

}


package es.um.swit.backup.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.2.6
 * Tue Aug 28 20:17:49 CEST 2018
 * Generated source version: 3.2.6
 */

@XmlRootElement(name = "registrarMappings", namespace = "http://service.backup.swit.um.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarMappings", namespace = "http://service.backup.swit.um.es/")

public class RegistrarMappings {

    @XmlElement(name = "mappingsFile")
    private byte[] mappingsFile;

    public byte[] getMappingsFile() {
        return this.mappingsFile;
    }

    public void setMappingsFile(byte[] newMappingsFile)  {
        this.mappingsFile = newMappingsFile;
    }

}


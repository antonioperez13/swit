
package es.um.swit.backup.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.um.swit.backup.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BorrarRegistro_QNAME = new QName("http://service.backup.swit.um.es/", "borrarRegistro");
    private final static QName _BorrarRegistroResponse_QNAME = new QName("http://service.backup.swit.um.es/", "borrarRegistroResponse");
    private final static QName _BorrarTodosRegistros_QNAME = new QName("http://service.backup.swit.um.es/", "borrarTodosRegistros");
    private final static QName _BorrarTodosRegistrosResponse_QNAME = new QName("http://service.backup.swit.um.es/", "borrarTodosRegistrosResponse");
    private final static QName _RecuperarMappings_QNAME = new QName("http://service.backup.swit.um.es/", "recuperarMappings");
    private final static QName _RecuperarMappingsResponse_QNAME = new QName("http://service.backup.swit.um.es/", "recuperarMappingsResponse");
    private final static QName _RecuperarTodosRegistros_QNAME = new QName("http://service.backup.swit.um.es/", "recuperarTodosRegistros");
    private final static QName _RecuperarTodosRegistrosResponse_QNAME = new QName("http://service.backup.swit.um.es/", "recuperarTodosRegistrosResponse");
    private final static QName _ReemplazarMappings_QNAME = new QName("http://service.backup.swit.um.es/", "reemplazarMappings");
    private final static QName _ReemplazarMappingsResponse_QNAME = new QName("http://service.backup.swit.um.es/", "reemplazarMappingsResponse");
    private final static QName _RegistrarMappings_QNAME = new QName("http://service.backup.swit.um.es/", "registrarMappings");
    private final static QName _RegistrarMappingsResponse_QNAME = new QName("http://service.backup.swit.um.es/", "registrarMappingsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.um.swit.backup.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BorrarRegistro }
     * 
     */
    public BorrarRegistro createBorrarRegistro() {
        return new BorrarRegistro();
    }

    /**
     * Create an instance of {@link BorrarRegistroResponse }
     * 
     */
    public BorrarRegistroResponse createBorrarRegistroResponse() {
        return new BorrarRegistroResponse();
    }

    /**
     * Create an instance of {@link BorrarTodosRegistros }
     * 
     */
    public BorrarTodosRegistros createBorrarTodosRegistros() {
        return new BorrarTodosRegistros();
    }

    /**
     * Create an instance of {@link BorrarTodosRegistrosResponse }
     * 
     */
    public BorrarTodosRegistrosResponse createBorrarTodosRegistrosResponse() {
        return new BorrarTodosRegistrosResponse();
    }

    /**
     * Create an instance of {@link RecuperarMappings }
     * 
     */
    public RecuperarMappings createRecuperarMappings() {
        return new RecuperarMappings();
    }

    /**
     * Create an instance of {@link RecuperarMappingsResponse }
     * 
     */
    public RecuperarMappingsResponse createRecuperarMappingsResponse() {
        return new RecuperarMappingsResponse();
    }

    /**
     * Create an instance of {@link RecuperarTodosRegistros }
     * 
     */
    public RecuperarTodosRegistros createRecuperarTodosRegistros() {
        return new RecuperarTodosRegistros();
    }

    /**
     * Create an instance of {@link RecuperarTodosRegistrosResponse }
     * 
     */
    public RecuperarTodosRegistrosResponse createRecuperarTodosRegistrosResponse() {
        return new RecuperarTodosRegistrosResponse();
    }

    /**
     * Create an instance of {@link ReemplazarMappings }
     * 
     */
    public ReemplazarMappings createReemplazarMappings() {
        return new ReemplazarMappings();
    }

    /**
     * Create an instance of {@link ReemplazarMappingsResponse }
     * 
     */
    public ReemplazarMappingsResponse createReemplazarMappingsResponse() {
        return new ReemplazarMappingsResponse();
    }

    /**
     * Create an instance of {@link RegistrarMappings }
     * 
     */
    public RegistrarMappings createRegistrarMappings() {
        return new RegistrarMappings();
    }

    /**
     * Create an instance of {@link RegistrarMappingsResponse }
     * 
     */
    public RegistrarMappingsResponse createRegistrarMappingsResponse() {
        return new RegistrarMappingsResponse();
    }

    /**
     * Create an instance of {@link RespuestaRecuperarRegistro }
     * 
     */
    public RespuestaRecuperarRegistro createRespuestaRecuperarRegistro() {
        return new RespuestaRecuperarRegistro();
    }

    /**
     * Create an instance of {@link RespuestaGestorRegistros }
     * 
     */
    public RespuestaGestorRegistros createRespuestaGestorRegistros() {
        return new RespuestaGestorRegistros();
    }

    /**
     * Create an instance of {@link Registro }
     * 
     */
    public Registro createRegistro() {
        return new Registro();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarRegistro }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "borrarRegistro")
    public JAXBElement<BorrarRegistro> createBorrarRegistro(BorrarRegistro value) {
        return new JAXBElement<BorrarRegistro>(_BorrarRegistro_QNAME, BorrarRegistro.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarRegistroResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "borrarRegistroResponse")
    public JAXBElement<BorrarRegistroResponse> createBorrarRegistroResponse(BorrarRegistroResponse value) {
        return new JAXBElement<BorrarRegistroResponse>(_BorrarRegistroResponse_QNAME, BorrarRegistroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarTodosRegistros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "borrarTodosRegistros")
    public JAXBElement<BorrarTodosRegistros> createBorrarTodosRegistros(BorrarTodosRegistros value) {
        return new JAXBElement<BorrarTodosRegistros>(_BorrarTodosRegistros_QNAME, BorrarTodosRegistros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarTodosRegistrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "borrarTodosRegistrosResponse")
    public JAXBElement<BorrarTodosRegistrosResponse> createBorrarTodosRegistrosResponse(BorrarTodosRegistrosResponse value) {
        return new JAXBElement<BorrarTodosRegistrosResponse>(_BorrarTodosRegistrosResponse_QNAME, BorrarTodosRegistrosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarMappings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "recuperarMappings")
    public JAXBElement<RecuperarMappings> createRecuperarMappings(RecuperarMappings value) {
        return new JAXBElement<RecuperarMappings>(_RecuperarMappings_QNAME, RecuperarMappings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarMappingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "recuperarMappingsResponse")
    public JAXBElement<RecuperarMappingsResponse> createRecuperarMappingsResponse(RecuperarMappingsResponse value) {
        return new JAXBElement<RecuperarMappingsResponse>(_RecuperarMappingsResponse_QNAME, RecuperarMappingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarTodosRegistros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "recuperarTodosRegistros")
    public JAXBElement<RecuperarTodosRegistros> createRecuperarTodosRegistros(RecuperarTodosRegistros value) {
        return new JAXBElement<RecuperarTodosRegistros>(_RecuperarTodosRegistros_QNAME, RecuperarTodosRegistros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperarTodosRegistrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "recuperarTodosRegistrosResponse")
    public JAXBElement<RecuperarTodosRegistrosResponse> createRecuperarTodosRegistrosResponse(RecuperarTodosRegistrosResponse value) {
        return new JAXBElement<RecuperarTodosRegistrosResponse>(_RecuperarTodosRegistrosResponse_QNAME, RecuperarTodosRegistrosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReemplazarMappings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "reemplazarMappings")
    public JAXBElement<ReemplazarMappings> createReemplazarMappings(ReemplazarMappings value) {
        return new JAXBElement<ReemplazarMappings>(_ReemplazarMappings_QNAME, ReemplazarMappings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReemplazarMappingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "reemplazarMappingsResponse")
    public JAXBElement<ReemplazarMappingsResponse> createReemplazarMappingsResponse(ReemplazarMappingsResponse value) {
        return new JAXBElement<ReemplazarMappingsResponse>(_ReemplazarMappingsResponse_QNAME, ReemplazarMappingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarMappings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "registrarMappings")
    public JAXBElement<RegistrarMappings> createRegistrarMappings(RegistrarMappings value) {
        return new JAXBElement<RegistrarMappings>(_RegistrarMappings_QNAME, RegistrarMappings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarMappingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.backup.swit.um.es/", name = "registrarMappingsResponse")
    public JAXBElement<RegistrarMappingsResponse> createRegistrarMappingsResponse(RegistrarMappingsResponse value) {
        return new JAXBElement<RegistrarMappingsResponse>(_RegistrarMappingsResponse_QNAME, RegistrarMappingsResponse.class, null, value);
    }

}

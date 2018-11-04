package commons.reglas;

import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CatalogoReglas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8186230406314824055L;
	
	/** Lista de reglas */
	private List<Regla> reglas;
	
	
	/**
	 * Constructor de la clase.
	 * Inicializa la lista de reglas.
	 */
	public CatalogoReglas() {
		reglas = new ArrayList<>();
	}
	
	public CatalogoReglas(CatalogoReglas otroCatalogo) {
		reglas = new ArrayList<>(otroCatalogo.getAllReglas());
	}

	/**
	 * Añade una regla.
	 * @param nueva
	 * @return
	 */
	public boolean addRegla(Regla nueva) {
		return this.reglas.add(nueva);
	}
	
	/**
	 * Recupera una lista con todas las reglas.
	 * @return
	 */
	public List<Regla> getAllReglas(){
		return new ArrayList<>(this.reglas);
	}
	
	/**
	 * Añade al catalogo todas las reglas del catalogo indicado.
	 * @param otroCatalogo
	 */
	public void addAllReglas(CatalogoReglas otroCatalogo) {
		this.reglas.addAll(otroCatalogo.getAllReglas());
	}
	
	/**
	 * Añade al catalogo todas las reglas de la lista de reglas.
	 * @param reglas
	 */
	public void addAllReglas(List<Regla> reglas) {
		this.reglas.addAll(reglas);
	}
	
	/**
	 * Añade al catalogo todas las reglas de la lista de reglas y 
	 * reasigna identificadores para cada una desde cero.
	 * @param reglas
	 */
	public void addAllReglasRestartIds(List<Regla> reglas) {
		this.reglas.addAll(reglas);
		
		// Se reinicia el valor de asignación de id de reglas y se les
		// asigna un nuevo identificador a cada una
		Regla.reiniciarAsignacionId();
		for(Regla regla : this.reglas) {
			regla.setId();
		}
	}
	
	
	
	
	/**
	 * Comprueba si una regla ya existe en el catálogo de reglas y la devuelve.
	 * La comprobación de si la regla existe se hace según los elementos que componen
	 * la regla exceptuando el Id.
	 * @param regla
	 * @return La regla o null si no existe
	 */
	public Regla recuperarRegla(Regla regla) {
		for(Regla r : reglas) {
			if(r.equals(regla)) {
				return r;
			}
		}
		
		return null;
	}
	
	/**
	 * Elimina del catálogo la regla cuyo identificador coincida
	 * con el pasado por parámetro.
	 * @param id
	 * @return La regla eliminada, null si no existía
	 */
	public Regla removeReglaPorId(int id) {
		Iterator<Regla> reglasIterator = reglas.iterator();
		while (reglasIterator.hasNext()) {
			Regla regla = reglasIterator.next();
			if(regla != null && id == regla.getId()) {
				reglasIterator.remove();
				return regla;
			}
		}
		
		return null;
	}
	
	/**
	 * Elimina todas las reglas.
	 */
	public void removeAllReglas() {
		reglas.clear();
	}
	
	public String toXmlFileSave() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        
        Element catalogo = document.createElement("catalogo");
        document.appendChild(catalogo);
        
        for(Regla regla : reglas) {
	        // Se inserta cada regla al catalogo dentro del xml
	        Element reglaXml = document.createElement("regla");
	        catalogo.appendChild(reglaXml);
	        
	        // Se añade como atributo el tipo de regla al xml
	        Attr tipoRegla = document.createAttribute("tipo");
			tipoRegla.setValue(regla.getTipo().getCode());
			reglaXml.setAttributeNode(tipoRegla);
			
			// Se añade la etiqueta que le puso el usuario
			Element etiqueta = document.createElement("userTag");
			etiqueta.appendChild(document.createTextNode(regla.getEtiqueta()));
			reglaXml.appendChild(etiqueta);
	        
			// Se añaden los elementos comunes a todos los tipos de regla
	        reglaXml.appendChild(elementoToDocument("domainNodeSource", regla.getDomainNodeSource(), document));
	        
	        reglaXml.appendChild(elementoToDocument("domainClassTarget", regla.getDomainClassTarget(), document));

	        // Según el tipo de regla se añaden los elementos restantes
	        if(regla.getTipo() == TipoRegla.PROPIEDAD) {
	        	reglaXml.appendChild(elementoToDocument("propertyValueSource", regla.getPropertyValueSource(), document));
	        	reglaXml.appendChild(elementoToDocument("propertyTarget", regla.getPropertyTarget(), document));
	        	
	        } else if(regla.getTipo() == TipoRegla.RELACION) {
		    	reglaXml.appendChild(elementoToDocument("rangeNodeSource", regla.getRangeNodeSource(), document));
		    	reglaXml.appendChild(elementoToDocument("rangeClassTarget", regla.getRangeClassTarget(), document));
	        	reglaXml.appendChild(elementoToDocument("propertyTarget", regla.getPropertyTarget(), document));
	        }
	        
        }

		// create the xml file
		//transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource domSource = new DOMSource(document);
		
		// Devuelve el XML por consola
		//StreamResult streamResult = new StreamResult(System.out);

		// Devuelve el XML en un String
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);

		transformer.transform(domSource, streamResult);
		
		System.out.println(writer.toString());

		System.out.println("\nDone creating XML File");
		
		return writer.toString();
	}
	
	private Element elementoToDocument(String nombreElemento, Elemento elemento, Document document) {
		Element documentElement = document.createElement(nombreElemento);
        
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(elemento.getName()));
        Element route = document.createElement("route");
        route.appendChild(document.createTextNode(elemento.getRoute()));
        
        documentElement.appendChild(name);
        documentElement.appendChild(route);
        
        // Si tiene atributo type se añade
        if(elemento.getType() != null && !elemento.getType().isEmpty()) {
        	Element type = document.createElement("type");
            type.appendChild(document.createTextNode(elemento.getType()));
            
            Element uri = document.createElement("uri");
            uri.appendChild(document.createTextNode(elemento.getUri()));
            
            documentElement.appendChild(type);
            documentElement.appendChild(uri);
        }
        
        return documentElement;
	}
	
	
	
	
	
	
	
	
	/**
	 * Construye el fichero XML que se utiliza en el último paso de SWIT para ejecutar los mapeos definidos.
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public String mappingXmlFile() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        
        Element alignment = document.createElement("Alignment");
        document.appendChild(alignment);
        
        for(Regla regla : reglas) {
	        // Se inserta cada regla al catalogo dentro del xml
	        Element reglaXml = document.createElement("map");
	        alignment.appendChild(reglaXml);
	        
	        // Se añade como atributo el tipo de regla al xml
	        Element tipoRegla = document.createElement("type");
			tipoRegla.appendChild(document.createTextNode(regla.getTipo().getCode()));
			reglaXml.appendChild(tipoRegla);
			
			// Se añade la etiqueta que le puso el usuario
			Element etiqueta = document.createElement("userTag");
			etiqueta.appendChild(document.createTextNode(regla.getEtiqueta()));
			reglaXml.appendChild(etiqueta);

	        // Según el tipo de regla se añaden los elementos restantes
	        if(regla.getTipo() == TipoRegla.CLASE) {
	        	reglaClaseToDocument(reglaXml, regla, document);
	        } else if(regla.getTipo() == TipoRegla.PROPIEDAD) {
	        	reglaPropiedadToDocument(reglaXml, regla, document);
	        } else if(regla.getTipo() == TipoRegla.RELACION) {
	        	reglaRelacionToDocument(reglaXml, regla, document);
	        }
	        
        }

		// create the xml file
		//transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource domSource = new DOMSource(document);
		
		// Devuelve el XML por consola
		//StreamResult streamResult = new StreamResult(System.out);

		// Devuelve el XML en un String
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);

		transformer.transform(domSource, streamResult);

		System.out.println(writer.toString());
		
		System.out.println("\nDone creating XML File");
		
		return writer.toString();
	}
	
	/**
	 * Construye la estructura que conforma una regla de clase en XML.
	 * @param elementoPadre Elemento XML al que se añaden como hijos las estructuras creadas.
	 * @param regla
	 * @param document
	 */
	private void reglaClaseToDocument(Element elementoPadre, Regla regla, Document document) {
        // Se añade DomainClassTarget
        Element clase = document.createElement("class");
        Element classId = document.createElement("id");
        classId.appendChild(document.createTextNode(regla.getDomainClassTarget().getUri()));
        clase.appendChild(classId);
        elementoPadre.appendChild(clase);
        
        
        // Se añade DomainNodeSource
        Element arch = document.createElement("arch");
        Element nodepath = document.createElement("nodepath");
        nodepath.appendChild(document.createTextNode(regla.getDomainNodeSource().getRoute()));
        arch.appendChild(nodepath);
        elementoPadre.appendChild(arch);
	}
	
	/**
	 * Construye la estructura que conforma una regla de propiedad en XML.
	 * @param elementoPadre Elemento XML al que se añaden como hijos las estructuras creadas.
	 * @param regla
	 * @param document
	 */
	private void reglaPropiedadToDocument(Element elementoPadre, Regla regla, Document document) {
		// Se añaden DomainClassTarget, DomainNodeSource y PropertyTarget
        addElementosComunesReglasPropiedadRelacion(elementoPadre, regla, document);
        
        // Se añade PropertyValueSource
        Element target = document.createElement("target");
        Element archTarget = document.createElement("arch");
        Element archTargetValuepath = document.createElement("valuepath");
        archTargetValuepath.appendChild(document.createTextNode(regla.getPropertyValueSource().getName()));
        archTarget.appendChild(archTargetValuepath);
        target.appendChild(archTarget);
        elementoPadre.appendChild(target);
	}
	
	/**
	 * Construye la estructura que conforma una regla de relación en XML.
	 * @param elementoPadre Elemento XML al que se añaden como hijos las estructuras creadas.
	 * @param regla
	 * @param document
	 */
	private void reglaRelacionToDocument(Element elementoPadre, Regla regla, Document document) {
		// Se añaden DomainClassTarget, DomainNodeSource y PropertyTarget
        addElementosComunesReglasPropiedadRelacion(elementoPadre, regla, document);
        
        // Se añaden RangeClassTarget y RangeNodeSource 
        Element target = document.createElement("target");
        Element archTarget = document.createElement("arch");
        
        // RangeClassTarget
        Element clase = document.createElement("class");
        Element claseId = document.createElement("id");
        claseId.appendChild(document.createTextNode(regla.getRangeClassTarget().getUri()));
        clase.appendChild(claseId);
        target.appendChild(clase);
        
        // RangeNodeSource
        Element archTargetNodepath = document.createElement("nodepath");
        archTargetNodepath.appendChild(document.createTextNode(regla.getRangeNodeSource().getName()));
        archTarget.appendChild(archTargetNodepath);
        target.appendChild(archTarget);
        
        elementoPadre.appendChild(target);
	}

	/**
	 * Construye la estructura en XML común para las reglas de tipo propiedad
	 * y relación.
	 * @param elementoPadre Elemento XML al que se añaden como hijos las estructuras creadas.
	 * @param regla
	 * @param document
	 */
	private void addElementosComunesReglasPropiedadRelacion(Element elementoPadre, Regla regla, Document document) {
		// Se añaden DomainClassTarget y DomainNodeSource
        Element source = document.createElement("source");
        reglaClaseToDocument(source, regla, document);
        elementoPadre.appendChild(source);
        
        // Se añade PropertyTarget
        Element predicate = document.createElement("predicate");
        Element predicateId = document.createElement("id");
        predicateId.appendChild(document.createTextNode(regla.getPropertyTarget().getUri()));
        predicate.appendChild(predicateId);
        elementoPadre.appendChild(predicate);
	}
	
	

}

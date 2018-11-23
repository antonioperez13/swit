package commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import commons.reglas.CatalogoReglas;
import commons.reglas.Elemento;
import commons.reglas.Regla;
import commons.reglas.TipoRegla;

public class XmlFileUtils {
	
	/*************************************************************
	 ************** CONSTANTES DE TEXTO
	 *************************************************************/
	
	public static final String XML_TAG_ALIGNMENT = "Alignment";
	
	public static final String XML_TAG_MAP = "map";
	
	public static final String XML_TAG_TYPE = "type";
	
	public static final String XML_TAG_COMMENTARY = "commentary";
	
	public static final String XML_TAG_SOURCE = "source";
	
	public static final String XML_TAG_TARGET = "target";
	
	public static final String XML_TAG_CLASS = "class";
	
	public static final String XML_TAG_ID = "id";
	
	public static final String XML_TAG_ARCH = "arch";
	
	public static final String XML_TAG_NODEPATH = "nodepath";
	
	public static final String XML_TAG_VALUEPATH = "valuepath";
	
	public static final String XML_TAG_PREDICATE = "predicate";
	
	
	
	
	
	/*************************************************************
	 ************** CONSTRUCCIÓN DEL FICHERO XML
	 *************************************************************/
	
	/**
	 * Construye el fichero XML que se utiliza en el último paso de SWIT para ejecutar los mapeos definidos.
	 * @param reglas 
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static String mappingXmlFile(CatalogoReglas reglas) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        
        Element alignment = document.createElement(XML_TAG_ALIGNMENT);
        document.appendChild(alignment);
        
        for(Regla regla : reglas.getAllReglas()) {
	        // Se inserta cada regla al catalogo dentro del xml
	        Element reglaXml = document.createElement(XML_TAG_MAP);
	        alignment.appendChild(reglaXml);
	        
	        // Se añade como atributo el tipo de regla al xml
	        Element tipoRegla = document.createElement(XML_TAG_TYPE);
			tipoRegla.appendChild(document.createTextNode(regla.getTipo().getCode()));
			reglaXml.appendChild(tipoRegla);
			
			// Se añade la etiqueta que le puso el usuario
			Element etiqueta = document.createElement(XML_TAG_COMMENTARY);
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

		// Crea el fichero XML
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// Propiedad para indentar el contenido y que sea legible
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource domSource = new DOMSource(document);

		// Devuelve el XML en un String
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);
		transformer.transform(domSource, streamResult);
		
		return writer.toString();
	}

	/**
	 * Construye la estructura que conforma una regla de clase en XML.
	 * @param elementoPadre Elemento XML al que se añaden como hijos las estructuras creadas.
	 * @param regla
	 * @param document
	 */
	private static void reglaClaseToDocument(Element elementoPadre, Regla regla, Document document) {
        // Se añade DomainClassTarget
        Element clase = document.createElement(XML_TAG_CLASS);
        Element classId = document.createElement(XML_TAG_ID);
        classId.appendChild(document.createTextNode(regla.getDomainClassTarget().getUri()));
        clase.appendChild(classId);
        elementoPadre.appendChild(clase);
        
        
        // Se añade DomainNodeSource
        Element arch = document.createElement(XML_TAG_ARCH);
        Element nodepath = document.createElement(XML_TAG_NODEPATH);
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
	private static void reglaPropiedadToDocument(Element elementoPadre, Regla regla, Document document) {
		// Se añaden DomainClassTarget, DomainNodeSource y PropertyTarget
        addElementosComunesReglasPropiedadRelacion(elementoPadre, regla, document);
        
        // Se añade PropertyValueSource
        Element target = document.createElement(XML_TAG_TARGET);
        Element archTarget = document.createElement(XML_TAG_ARCH);
        Element archTargetValuepath = document.createElement(XML_TAG_VALUEPATH);
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
	private static void reglaRelacionToDocument(Element elementoPadre, Regla regla, Document document) {
		// Se añaden DomainClassTarget, DomainNodeSource y PropertyTarget
        addElementosComunesReglasPropiedadRelacion(elementoPadre, regla, document);
        
        // Se añaden RangeClassTarget y RangeNodeSource 
        Element target = document.createElement(XML_TAG_TARGET);
        Element archTarget = document.createElement(XML_TAG_ARCH);
        
        // RangeClassTarget
        Element clase = document.createElement(XML_TAG_CLASS);
        Element claseId = document.createElement(XML_TAG_ID);
        claseId.appendChild(document.createTextNode(regla.getRangeClassTarget().getUri()));
        clase.appendChild(claseId);
        target.appendChild(clase);
        
        // RangeNodeSource
        Element archTargetNodepath = document.createElement(XML_TAG_NODEPATH);
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
	private static void addElementosComunesReglasPropiedadRelacion(Element elementoPadre, Regla regla, Document document) {
		// Se añaden DomainClassTarget y DomainNodeSource
        Element source = document.createElement(XML_TAG_SOURCE);
        reglaClaseToDocument(source, regla, document);
        elementoPadre.appendChild(source);
        
        // Se añade PropertyTarget
        Element predicate = document.createElement(XML_TAG_PREDICATE);
        Element predicateId = document.createElement(XML_TAG_ID);
        predicateId.appendChild(document.createTextNode(regla.getPropertyTarget().getUri()));
        predicate.appendChild(predicateId);
        elementoPadre.appendChild(predicate);
	}
	
	
	/*************************************************************
	 ************** LECTURA DEL FICHERO XML
	 *************************************************************/
	
	/**
	 * Interpreta el contenido de un fichero XML para extraer las reglas y guardarlas en una lista.
	 * @param contenidoFichero
	 * @param reglas
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static CatalogoReglas loadXmlFile(InputStream contenidoFichero) throws ParserConfigurationException, SAXException, IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		
		CatalogoReglas reglas = new CatalogoReglas();
		
		// Se carga el documento que el usuario ha subido
		org.jdom2.Document document = (org.jdom2.Document) builder.build(contenidoFichero);
		
		org.jdom2.Element rootNode = document.getRootElement();
		// Nos quedamos con las reglas que están guardadas con la etiqueta "map"
		List<org.jdom2.Element> list = rootNode.getChildren(XML_TAG_MAP);

		for (int i = 0; i < list.size(); i++) {
			// Cogemos la regla
			org.jdom2.Element node = (org.jdom2.Element) list.get(i);
			
			// Objeto donde se guardará la regla leída
			Regla regla = new Regla();
			
			// Etiqueta que el usuario puso a la regla
			if(node.getChild(XML_TAG_COMMENTARY) != null) {
				regla.setEtiqueta(node.getChild(XML_TAG_COMMENTARY).getText());
			}

			// Según el tipo de regla que sea
			String tipoRegla = node.getChild(XML_TAG_TYPE).getText();
			if(tipoRegla.equals(TipoRegla.CLASE.getCode())) {
				loadXmlClassRule(regla, node);
				regla.setTipo(TipoRegla.CLASE);
				
			} else if(tipoRegla.equals(TipoRegla.PROPIEDAD.getCode())) {
				loadXmlPropertyRule(regla, node);
				regla.setTipo(TipoRegla.PROPIEDAD);
				
			} else if(tipoRegla.equals(TipoRegla.RELACION.getCode())) {
				loadXmlRelationRule(regla, node);
				regla.setTipo(TipoRegla.RELACION);
			}
			
			// La añadimos a la lista
			reglas.addRegla(regla);
		}
		
		return reglas;
	}

	/**
	 * Dado el nodo del XML donde se encuentran los elementos domainNode y domainClass
	 * los interpreta y los guarda en una regla.
	 * @param regla
	 * @param node
	 */
	private static void loadXmlClassRule(Regla regla, org.jdom2.Element node) {
		String nodepath = node.getChild(XML_TAG_ARCH).getChild(XML_TAG_NODEPATH).getText();
		regla.setDomainNodeSource(new Elemento(SwitCommonsUtils.lastRouteElement(nodepath), -1, nodepath));
		
		String uri = node.getChild(XML_TAG_CLASS).getChild(XML_TAG_ID).getText();
		regla.setDomainClassTarget(new Elemento(SwitCommonsUtils.extractElementStringFromUri(uri), "", uri));
	}

	/**
	 * Dado el nodo del XML donde se encuentran los elementos que conforman una regla de propiedad
	 * los interpreta y los guarda en una regla.
	 * @param regla
	 * @param node
	 */
	private static void loadXmlPropertyRule(Regla regla, org.jdom2.Element node) {
		// Domain node source y Domain class target
		loadXmlClassRule(regla, node.getChild(XML_TAG_SOURCE));
		
		// Property target
		String propertyTargetUri = node.getChild(XML_TAG_PREDICATE).getChild(XML_TAG_ID).getText();
		regla.setPropertyTarget(new Elemento(SwitCommonsUtils.extractElementStringFromUri(propertyTargetUri), "", propertyTargetUri));
		
		org.jdom2.Element targetNode = node.getChild(XML_TAG_TARGET);
		
		// Property value source
		String targetNodepath = targetNode.getChild(XML_TAG_ARCH).getChild(XML_TAG_VALUEPATH).getText();
		regla.setPropertyValueSource(new Elemento(targetNodepath, -1, ""));
		
	}
	
	/**
	 * Dado el nodo del XML donde se encuentran los elementos que conforman una regla de relación
	 * los interpreta y los guarda en una regla.
	 * @param regla
	 * @param node
	 */
	private static void loadXmlRelationRule(Regla regla, org.jdom2.Element node) {
		// Domain node source y Domain class target
		loadXmlClassRule(regla, node.getChild(XML_TAG_SOURCE));
		
		// Property target
		String propertyTargetUri = node.getChild(XML_TAG_PREDICATE).getChild(XML_TAG_ID).getText();
		regla.setPropertyTarget(new Elemento(SwitCommonsUtils.extractElementStringFromUri(propertyTargetUri), "", propertyTargetUri));
		
		org.jdom2.Element targetNode = node.getChild(XML_TAG_TARGET);
		
		// Range node source
		String targetNodepath = targetNode.getChild(XML_TAG_ARCH).getChild(XML_TAG_NODEPATH).getText();
		regla.setRangeNodeSource(new Elemento(targetNodepath, -1, ""));
		
		// Range class target
		String targetUri = targetNode.getChild(XML_TAG_CLASS).getChild(XML_TAG_ID).getText();
		regla.setRangeClassTarget(new Elemento(SwitCommonsUtils.extractElementStringFromUri(targetUri), "", targetUri));
		
	}
}

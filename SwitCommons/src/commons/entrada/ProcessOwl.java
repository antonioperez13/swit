package commons.entrada;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import commons.tree.NodeComparator;
import commons.tree.NodeOwl;
import commons.tree.OwlType;

/**
 * Clase preparada para parsear desde un fichero OWL una ontología a un árbol de nodos
 * del tipo {@link #NodeOwl}.
 * @author Antonio
 *
 */
public class ProcessOwl {
	
	/**
	 * Método que recupera un fichero OWL desde la ruta dada y parsea  
	 * de una ontología a un árbol de nodos del tipo {@link #NodeOwl}.
	 * @param pathFile
	 * @return
	 * @throws Exception 
	 */
	public static NodeOwl getOwlStructureFromFilePath(String pathFile) throws Exception {
		// Fichero donde se encuentra la ontología
		File fileInput = new File(pathFile);
		
		NodeOwl structure = getOwlStructure(fileInput);
        
		return structure;
	}


	/**
	 * Método que parsea desde un fichero OWL una ontología a un árbol de nodos
	 * del tipo {@link #NodeOwl}.
	 * @param fileInput
	 * @return
	 */
	public static NodeOwl getOwlStructure(File fileInput) throws Exception{
		// Procesado del fichero y creación de los objetos necesarios para el resto
		// del proceso
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = null;
		try {
			ontology = manager.loadOntologyFromOntologyDocument(fileInput);
		} catch (OWLOntologyCreationException e1) {
			e1.printStackTrace();
			throw new Exception();
		}
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
        reasoner.precomputeInferences();
		
        //****************************/
        //** CLASES E INDIVIDUALES  **/
        //****************************/
        // Recuperamos el nodo raíz y a partir de él obtenemos el resto de clases e individuales de la ontología
        Node<OWLClass> topNode = reasoner.getTopClassNode();
        NodeOwl structure = new NodeOwl(extractElementString(topNode.getRepresentativeElement().toString()),
        		topNode.getRepresentativeElement().toString(), OwlType.CLASS);
        // En este paso comenzamos procesando clases e individuales desde la raíz
        extractClassesAndIndividuals(topNode, structure, 0, reasoner);
        
        // Quitamos el nodo raíz que no sirve de nada y nos quedamos con el resto del árbol
        structure = structure.getChildren().get(0);
		structure.removeParent();
        
		//****************/
        //** DATATYPES  **/
        //****************/
		// Recuperamos los datatypes que hayan en la ontología
		List<NodeOwl> datatypes = new ArrayList<>();
        for(OWLDataProperty i : ontology.getDataPropertiesInSignature()) {
        	// Cada nuevo elemento se añade como un nodo hijo de la raíz
        	NodeOwl dataTypeNode = new NodeOwl(extractElementString(i.toString()), i.toString(), OwlType.DATATYPE, structure);
        	datatypes.add(dataTypeNode);
        }
        // Ordenamos por nombre los elementos que se acaban de encontrar y se añaden como hijos de la raíz
        Collections.sort(datatypes, NodeComparator.compareByName());
        structure.addChildren(datatypes);
        
        
        //************************/
        //** OBJECT PROPERTIES  **/
        //************************/
        // Recuperamos los object properties de la ontología
        List<NodeOwl> properties = new ArrayList<>();
        for(OWLObjectProperty i : ontology.getObjectPropertiesInSignature()) {
        	// Cada nuevo elemento se añade como un nodo hijo de la raíz
        	NodeOwl propertyNode = new NodeOwl(extractElementString(i.toString()), i.toString(), OwlType.PROPERTY, structure);
        	properties.add(propertyNode);
        }
        // Ordenamos por nombre los elementos que se acaban de encontrar y se añaden como hijos de la raíz
        Collections.sort(properties, NodeComparator.compareByName());
        structure.addChildren(properties);
        
        // Por defecto nombramos a la raíz "Root"
        structure.setName("Root");
		return structure;
	}
	
	
	/**
	 * Extrae las clases e individuales del nodo padre dado como parámetro. 
	 * @param parent Nodo padre en el objeto que contiene la ontología y que se está extrayendo
	 * @param nodoPadre Nodo en el que se quieren incluir las clases e individuales encontrados
	 * @param depth Profundidad en el árbol en el que se están añadiendo los nuevos elementos
	 * @param reasoner
	 */
	private static void extractClassesAndIndividuals(Node<OWLClass> parent, NodeOwl nodoPadre, int depth, OWLReasoner reasoner) {
        // Para no añadir el último nodo de la rama que no aporta nada (el nodo owl:Nothing
		// o una clase insatisfactible) salimos de la función
        if (parent.isBottomNode()) {
            return;
        }
        
        // Se añade la clase hija al padre
        NodeOwl subNodoClass = new NodeOwl(extractElementString(parent.getRepresentativeElement().toString()),
        		parent.getRepresentativeElement().toString(), OwlType.CLASS);
        nodoPadre.addChild(subNodoClass);
        
        //System.out.println();
        for (Node<OWLClass> child : reasoner.getSubClasses(parent.getRepresentativeElement(), true)) {
            // Se añaden las subclases a la clase actual
            extractClassesAndIndividuals(child, subNodoClass, depth + 1, reasoner);
        }

        // Trata los individuales
        List<NodeOwl> individuals = new ArrayList<>();
        for(Node<OWLNamedIndividual> child : reasoner.getInstances(parent.getRepresentativeElement(), true)){
        	// Se añaden los individuales al nodo padre
        	NodeOwl subNodoIndividual = new NodeOwl(extractElementString(child.getRepresentativeElement().toString()),
        			child.getRepresentativeElement().toString(), OwlType.INDIVIDUAL);
        	individuals.add(subNodoIndividual);

        	//System.out.println();
        }
        // Se ordenan los individuales y se añaden al padre
        Collections.sort(individuals, NodeComparator.compareByName());
        subNodoClass.addChildren(individuals);
    }
	
	/**
	 * Divide la cadena dada por el caracter "#" y se queda con la última subcadena, que
	 * salvo rara excepción coincide con el nombre corto que identifica al elemento 
	 * @param element
	 * @return El último elemento de la cadena si ésta se ha podido dividir por "#", 
	 * la cadena sin procesar en caso contrario
	 */
    private static String extractElementString(String element) {
    	String extract;
    	
    	String[] nodo = element.split("#");
        if(nodo.length > 1) {
        	extract = nodo[1].substring(0, nodo[1].length()-1);
        } else {
        	extract = element;
        }
        
        return extract;
    }
    
}

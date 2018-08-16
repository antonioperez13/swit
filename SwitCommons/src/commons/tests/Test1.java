package commons.tests;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.xerces.impl.xs.XSImplementationImpl;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import commons.entrada.ProcessOwl;
import commons.entrada.ProcessXsd;
import commons.tree.NodeOwl;
import commons.tree.NodeXsd;
import uk.ac.manchester.cs.jfact.JFactFactory;


public class Test1 {
	
	public static OWLReasoner reasoner;
	
	public static final String DIVISOR_TESTS_INI = "\n\n#########################################################################################";
	public static final String DIVISOR_TESTS_FIN = "#########################################################################################\n\n";
	public static int initialDepth = 5;

	public static void main(String[] args) {
		
		boolean testProcesSchemaFilesImpl = false;
		boolean testNodo = false;
		boolean testProcessXsd = false;
		boolean testOwlLoad = false;
		boolean testProcessOwl = false;
		boolean testCreacionReglas = true;
		
		Logger logger = LoggerFactory.getLogger(Test1.class);
		logger.info("Pruebas preliminares");

		
		String XsdPath = "D:\\Universidad\\Universidad_2016-2017\\TFG\\SWIT\\molecule.xsd";
		String owlPath = "D:\\Universidad\\Universidad_2016-2017\\TFG\\SWIT\\molecule.owl";
		
		if(testProcesSchemaFilesImpl) {
			printHeader("Prueba de la carga de ficheros XSD");
			
			testProcessSchemaFiles(XsdPath);
		}
	 
		if(testNodo) {
			printHeader("Prueba de los nodos");
			
			testNode();
		}
		
		if(testProcessXsd) {
			printHeader("Prueba del processXsd");
			
			testProcessXsd(XsdPath);
		}
		
		if(testOwlLoad) {
			printHeader("Prueba de la carga de ficheros OWL");
			
			testOwlLoad(owlPath);
		}
		
		if(testProcessOwl) {
			printHeader("Prueba del processOwl");
			
			NodeOwl esquemaOwl = null;
			try {
				esquemaOwl = ProcessOwl.getOwlStructureFromFilePath(owlPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(esquemaOwl.treeToString());
		}
		
		if(testCreacionReglas) {
//			Regla clase1 = new Regla("datadoc/data/molecule", "Molecule");
//			Regla propiedad1 = new Regla("datadoc/data/atom", "Atom", "@x", "(D): x");
//			Regla relacion1 = new Regla("datadoc/data/atom", "Atom", "@atom", "(C): atom", "(O): hasAtom()");
//			
//			System.out.println("Regla de clase:\n" + clase1.toString());
//			System.out.println();
//			System.out.println("Regla de propiedad:\n" + propiedad1.toString());
//			System.out.println();
//			System.out.println("Regla de relación:\n" + relacion1.toString());
		}
	}


	private static void testProcessSchemaFiles(String XsdPath) {
		System.setProperty(DOMImplementationRegistry.PROPERTY, "com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl");
		DOMImplementationRegistry registry = null;
		try {
			registry = DOMImplementationRegistry.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		XSImplementationImpl impl = (XSImplementationImpl) registry.getDOMImplementation("XS-Loader");
		XSLoader schemaLoader = impl.createXSLoader(null);
		XSModel model = schemaLoader.loadURI(XsdPath);
		
		//HashMap<String, ArrayList<String>> schema = ProcessSchemaFilesImpl.getSchemasStructure(new DataSourceParameters(), XsdPath);
	}


	private static void testNode() {
		NodeXsd root = new NodeXsd("Root");

		NodeXsd child1 = new NodeXsd("Child1");
		child1.addChild("Grandchild1");
		child1.addChild("Grandchild2");

		NodeXsd child2 = new NodeXsd("Child2");
		child2.addChild("Grandchild3");

		root.addChild(child1);
		root.addChild(child2);
		root.addChild("Child3");
		
		root.addChildren(Arrays.asList(
		        new NodeXsd("Child4"),
		        new NodeXsd("Child5"),
		        new NodeXsd("Child6")
		));
		
		NodeXsd child5 = root.getChild("Child5");
		NodeXsd child5_1 = new NodeXsd("Child5_1");
		NodeXsd child5_1_1 = new NodeXsd("Child5_1_1");
		NodeXsd child5_1_1_1 = new NodeXsd("Child5_1_1_1");
		NodeXsd child5_1_1_1_1 = new NodeXsd("Child5_1_1_1_1");
		NodeXsd child5_1_1_1_2 = new NodeXsd("Child5_1_1_1_2");
		NodeXsd child5_1_1_1_3 = new NodeXsd("Child5_1_1_1_3");
		
		
		child5.addChild(child5_1);
		child5_1.addChild(child5_1_1);
		child5_1_1.addChild(child5_1_1_1);
		
		child5_1_1_1.addChildren(Arrays.asList(
				child5_1_1_1_1,
				child5_1_1_1_2,
				child5_1_1_1_3
		));
		
		System.out.println(root.treeToString(0));
		
//		System.out.println(root.getRoute("Grandchild3"));
//		System.out.println(root.getRoute("Child5_1_1_1_2"));
//		System.out.println(root.getRoute("Child5_1_1"));
//		System.out.println(root.getRoute("Child5_1_1_1_6"));
		System.out.println(child5_1_1_1_3.getRuta());
	}


	private static void testProcessXsd(String XsdPath) {
		NodeXsd tree = null;
		try {
			tree = ProcessXsd.getSchemasStructureFromFilePath(XsdPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(tree.treeToString(0) + "\n");
		
		String[] elementsName = {
				"properties : properties",
				"@name",
				"@x",
				"@element",
				"@coorddimension",
				"@noExiste"
		};
		
		int elementSelected = 4;
		List<NodeXsd> nodos = tree.getNodesWithName(elementsName[elementSelected]);
		System.out.println("Rutas a los nodos con nombre \"" + elementsName[elementSelected] + "\":");
		for(NodeXsd n : nodos) {
			System.out.println("> " + n.getRuta());
		}
	}


	private static void testOwlLoad(String owlPath) {
		// load file
		File file = new File(owlPath);

		// loading the ontology
		try {
		    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		    OWLOntology localOntology = manager.loadOntologyFromOntologyDocument(file);

		    //getting all axioms    
		    Set<OWLAxiom> axSet= localOntology.getAxioms();
		    
		    OWLReasonerFactory reasonerFactory = new JFactFactory();
		    reasoner = reasonerFactory.createReasoner(localOntology);
		    
		    Node<OWLClass> topClass = reasoner.getTopClassNode();
		    Node<OWLDataProperty> topDataProperty = reasoner.getTopDataPropertyNode();
		    Node<OWLObjectPropertyExpression> topObjectProperty = reasoner.getTopObjectPropertyNode();
		    
		    NodeSet<OWLClass> classNodeSet = reasoner.getSubClasses(topClass.getRepresentativeElement(), true);
		    NodeSet<OWLDataProperty> dataPropertyNodeSet = reasoner.getSubDataProperties(topDataProperty.getRepresentativeElement(), true);
			NodeSet<OWLObjectPropertyExpression> objectPropertyNodeSet = reasoner.getSubObjectProperties(topObjectProperty.getRepresentativeElement(), true);
		    
//		        for(Node<OWLClass> oc: nodeset) {
//		        	System.out.println(oc);
//		        }
		    
		    System.out.println(owlTreeToString(topClass, initialDepth));
		    
		    /*for(OWLAxiom axiom: axSet) {
		    	System.out.println("> " + axiom.getNNF());
		    }*/
		    
		} catch (OWLOntologyCreationException e) {
		    e.printStackTrace();
		}
	}
	
	public static String owlTreeToString(Node<OWLClass> nodo, int depth) {
		StringBuilder builder = new StringBuilder();
		if(depth != 0 && nodo.getSize() > 0 && nodo.getRepresentativeElement() != null) {
			builder.append("{").append(nodo.getRepresentativeElement());
			
			NodeSet<OWLClass> classNodeSet = reasoner.getSubClasses(nodo.getRepresentativeElement(), true);
			
			
			if(!classNodeSet.isEmpty()) {
				
				String child = "";
				for(Node<OWLClass> oc: classNodeSet) {
					child = owlTreeToString(oc, depth-1);
					
					if(!child.isEmpty()) {
						builder.append("\n");
						for(int i = 0; i < initialDepth-depth+1; i++) {
							builder.append("    ");
						}
						builder.append(child).append("\n");
					}
		        }
				if(!child.isEmpty()) {
					for(int i = 0; i < initialDepth-depth; i++) {
						builder.append("    ");
					}
				}
			}
//			
//			if(this.children != null) {
//				builder.append("\n");
//				for(NodoXsd child : this.children) {
//					for(int i = 0; i < depth+1; i++) {
//						builder.append("    ");
//					}
//					builder.append(child.toString(depth+1)).append("\n");
//				}
//				for(int i = 0; i < depth; i++) {
//					builder.append("    ");
//				}
//			}
			
			
			builder.append("}");
		}
		
		return builder.toString();
	}
	
	
	public static String printHeader(String msg) {
		StringBuilder header = new StringBuilder();
		header.append(DIVISOR_TESTS_INI);
		header.append(msg);
		header.append(DIVISOR_TESTS_FIN);
		return header.toString();
	}
}

package commons.tests;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import uk.ac.manchester.cs.jfact.JFactFactory;

public class Test2 {
	private static Logger logger = LoggerFactory
	        .getLogger(Test2.class);
	// Why This Failure marker
	private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");

	static OWLReasoner reasoner;

	static void printChildren(NodeSet<OWLClass> owlClasses) {
	    for (Node<OWLClass> node : owlClasses) {
	        logger.trace(node.getRepresentativeElement().toString());
	        if (!node.getRepresentativeElement().isBottomEntity())
	            printChildren(reasoner.getSubClasses(node.getRepresentativeElement(), true));
	    }       
	}
	
	public static String owlPath = "D:\\Universidad\\Universidad_2016-2017\\TFG\\SWIT\\molecule.owl";

	public static void main(String[] args) {
		boolean test1 = false;
		boolean test2 = true;
		boolean test3 = false;
		
        // Initialize		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File fileInput = new File(owlPath);
		 OWLOntology ontology = null;
		try {
			ontology = manager.loadOntologyFromOntologyDocument(fileInput);
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if(test1) {
			
			File fileOutput = new File(owlPath + ".txt");
			
		    try {
		        // Setup physical IRI for storing ontology
		        IRI saveDocumentIRI = IRI.create(owlPath + ".txt");
	
		        // Write to JsonLD format
		        //OWLDocumentFormat ontologyFormat = new RDFJsonLDDocumentFormat();
		       // manager.saveOntology(ontology, ontologyFormat, saveDocumentIRI);        
	
		        // Print tree structure
		        OWLReasonerFactory reasonerFactory = new JFactFactory();
		        reasoner = reasonerFactory.createReasoner(ontology);
		        Node<OWLClass> top = reasoner.getTopClassNode();
		        logger.trace(top.getRepresentativeElement().toString());
		        printChildren(reasoner.getSubClasses(top.getRepresentativeElement(), true));          
		    } catch (Throwable t) {
		        logger.error(WTF_MARKER, t.getMessage(), t);
		    }
		}
	    
		if(test2) {
		    try {
				shouldUseReasoner();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(test3) {
			
		}
	}
	
	
	/**
     * @param manager
     *        manager
     * @return loaded ontology
     * @throws OWLOntologyCreationException
     *         if a problem pops up
     */
    @Nonnull
	static
    OWLOntology load(@Nonnull OWLOntologyManager manager) throws OWLOntologyCreationException {
        // in this test, the ontology is loaded from a string
    	//String path = "file:///d://Universidad//Universidad_2016-2017//TFG//SWIT//molecule.owl";
        //return manager.loadOntologyFromOntologyDocument(new StringDocumentSource(path));
    	
    	File file = new File(owlPath);
    	
        return manager.loadOntologyFromOntologyDocument(file);
    }
	
	
	/**
     * An example which shows how to interact with a reasoner. In this example
     * Pellet is used as the reasoner. You must get hold of the pellet libraries
     * from pellet.owldl.com.
     * 
     * @throws Exception
     *         exception
     */
    public static void shouldUseReasoner() throws Exception {
        // Create our ontology manager in the usual way.
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ont = load(manager);
        // We need to create an instance of OWLReasoner. An OWLReasoner provides
        // the basic query functionality that we need, for example the ability
        // obtain the subclasses of a class etc. To do this we use a reasoner
        // factory. Create a reasoner factory. In this case, we will use HermiT,
        // but we could also use FaCT++ (http://code.google.com/p/factplusplus/)
        // or Pellet(http://clarkparsia.com/pellet) Note that (as of 03 Feb
        // 2010) FaCT++ and Pellet OWL API 3.0.0 compatible libraries are
        // expected to be available in the near future). For now, we'll use
        // HermiT HermiT can be downloaded from http://hermit-reasoner.com Make
        // sure you get the HermiT library and add it to your class path. You
        // can then instantiate the HermiT reasoner factory: Comment out the
        // first line below and uncomment the second line below to instantiate
        // the HermiT reasoner factory. You'll also need to import the
        // org.semanticweb.HermiT.Reasoner package.
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        // OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        // We'll now create an instance of an OWLReasoner (the implementation
        // being provided by HermiT as we're using the HermiT reasoner factory).
        // The are two categories of reasoner, Buffering and NonBuffering. In
        // our case, we'll create the buffering reasoner, which is the default
        // kind of reasoner. We'll also attach a progress monitor to the
        // reasoner. To do this we set up a configuration that knows about a
        // progress monitor. Create a console progress monitor. This will print
        // the reasoner progress out to the console.
        // ConsoleProgressMonitor progressMonitor = new
        // ConsoleProgressMonitor();
        // Specify the progress monitor via a configuration. We could also
        // specify other setup parameters in the configuration, and different
        // reasoners may accept their own defined parameters this way.
        // OWLReasonerConfiguration config = new SimpleConfiguration(
        // progressMonitor);
        // Create a reasoner that will reason over our ontology and its imports
        // closure. Pass in the configuration.
        // OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
        OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
        // Ask the reasoner to do all the necessary work now
        reasoner.precomputeInferences();
        // We can determine if the ontology is actually consistent (in this
        // case, it should be).
        boolean consistent = reasoner.isConsistent();
        // System.out.println("Consistent: " + consistent);
        // We can easily get a list of unsatisfiable classes. (A class is
        // unsatisfiable if it can't possibly have any instances). Note that the
        // getUnsatisfiableClasses method is really just a convenience method
        // for obtaining the classes that are equivalent to owl:Nothing.
        Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
        // This node contains owl:Nothing and all the classes that are
        // equivalent to owl:Nothing - i.e. the unsatisfiable classes. We just
        // want to print out the unsatisfiable classes excluding owl:Nothing,
        // and we can used a convenience method on the node to get these
        Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
        if (!unsatisfiable.isEmpty()) {
            // System.out.println("The following classes are unsatisfiable: ");
            for (OWLClass cls : unsatisfiable) {
                // System.out.println(" " + cls);
            }
        } else {
            // System.out.println("There are no unsatisfiable classes");
        }
        // Now we want to query the reasoner for all descendants of Marsupial.
        // Vegetarians are defined in the ontology to be animals that don't eat
        // animals or parts of animals.
        OWLDataFactory fac = manager.getOWLDataFactory();
        // Get a reference to the vegetarian class so that we can as the
        // reasoner about it. The full IRI of this class happens to be:
        // <http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#Marsupials>
        OWLClass marsupials = fac.getOWLClass(IRI.create(
            "http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#Marsupials"));
        // Now use the reasoner to obtain the subclasses of Marsupials. We can
        // ask for the direct subclasses or all of the (proper)
        // subclasses. In this case we just want the direct ones
        // (which we specify by the "true" flag).
        NodeSet<OWLClass> subClses = reasoner.getSubClasses(marsupials, true);
        // The reasoner returns a NodeSet, which represents a set of Nodes. Each
        // node in the set represents a subclass of Marsupial. A node of
        // classes contains classes, where each class in the node is equivalent.
        // For example, if we asked for the subclasses of some class A and got
        // back a NodeSet containing two nodes {B, C} and {D}, then A would have
        // two proper subclasses. One of these subclasses would be equivalent to
        // the class D, and the other would be the class that is equivalent to
        // class B and class C. In this case, we don't particularly care about
        // the equivalences, so we will flatten this set of sets and print the
        // result
        Set<OWLClass> clses = subClses.getFlattened();
        // for (OWLClass cls : clses) {
        // System.out.println(" " + cls);
        // }
        // We can easily
        // retrieve the instances of a class. In this example we'll obtain the
        // instances of the class Marsupials.
        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(marsupials, false);
        // The reasoner returns a NodeSet again. This time the NodeSet contains
        // individuals. Again, we just want the individuals, so get a flattened
        // set.
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
        // for (OWLNamedIndividual ind : individuals) {
        // System.out.println(" " + ind);
        // }
        
        
        
        
        
        System.out.println("\n\nÁrbol de clases (las \"C\" en SWIT):");
        // Finally, let's print out the class hierarchy.
        Node<OWLClass> topNode = reasoner.getTopClassNode();
        print(topNode, reasoner, 0);
        
        /*
        System.out.println("\n\nIndividuals (las \"I\" en SWIT):");
        for (OWLNamedIndividual i : ont.getIndividualsInSignature()) {
            System.out.println(i);
            for(Node<OWLClass> j: reasoner.getTypes(i, true)) {
	            System.out.print("\t");
	            System.out.println(j.getRepresentativeElement());
            }
            System.out.println();
        }*/
        
        //System.out.println("\n\nDatatypes (las \"D\" en SWIT):");
        for(OWLDataProperty i : ont.getDataPropertiesInSignature()) {
        	printIndent(1);
        	System.out.println("(D): " + extractElementString(i.toString()));
        	System.out.println();
        }
        
        //System.out.println("\n\nObject properties (las \"O\" en SWIT):");
        for(OWLObjectProperty i : ont.getObjectPropertiesInSignature()) {
        	printIndent(1);
        	System.out.println("(O): " + extractElementString(i.toString()));
        	System.out.println();
        }
        
        
        
        /*
        System.out.println("\n\n");
        // Again, it's worth noting that not all of the individuals that are
        // returned were explicitly stated to be marsupials. Finally, we can ask
        // for the property values (property assertions in OWL speak) for a
        // given
        // individual and property.
        // Let's get all properties for all individuals
        /*
        for (OWLNamedIndividual i : ont.getIndividualsInSignature()) {
            for (OWLObjectProperty p : ont.getObjectPropertiesInSignature()) {
                NodeSet<OWLNamedIndividual> individualValues = reasoner.getObjectPropertyValues(i, p);
                Set<OWLNamedIndividual> values = individualValues.getFlattened();
                if(!values.isEmpty()) {
					System.out.println("The property values for "+p+" for individual "+i+" are: ");
					for (OWLNamedIndividual ind : values) {
						System.out.println("    " + ind);
					}
					System.out.println();
                }
            }
        }*/
    }

    private static void print(@Nonnull Node<OWLClass> parent, @Nonnull OWLReasoner reasoner, int depth) {
        // We don't want to print out the bottom node (containing owl:Nothing
        // and unsatisfiable classes) because this would appear as a leaf node
        // everywhere
        if (parent.isBottomNode()) {
            return;
        }
        // Print an indent to denote parent-child relationships
        printIndent(depth);
        // Now print the node (containing the child classes)
        printNode(parent);
        System.out.println();
        for (Node<OWLClass> child : reasoner.getSubClasses(parent.getRepresentativeElement(), true)) {
            assert child != null;
            // Recurse to do the children. Note that we don't have to worry
            // about cycles as there are non in the inferred class hierarchy
            // graph - a cycle gets collapsed into a single node since each
            // class in the cycle is equivalent.
            print(child, reasoner, depth + 1);
        }
        // Imprime las I
        for(Node<OWLNamedIndividual> child : reasoner.getInstances(parent.getRepresentativeElement(), true)){
        	printIndent(depth+1);
        	
        	System.out.println("(I): " + extractElementString(child.getRepresentativeElement().toString()));

        	System.out.println();
        }
    }
    
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

    private static void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }

    private static void printNode(@Nonnull Node<OWLClass> node) {
        // The default prefix used here is only an example.
        // For real ontologies, choose a meaningful prefix - the best
        // choice depends on the actual ontology.
        DefaultPrefixManager pm = new DefaultPrefixManager(null, null, "http://owl.man.ac.uk/2005/07/sssw/people#");
        // Print out a node as a list of class names in curly brackets
        for (Iterator<OWLClass> it = node.getEntities().iterator(); it.hasNext();) {
            OWLClass cls = it.next();
            // User a prefix manager to provide a slightly nicer shorter name
            String shortForm = pm.getShortForm(cls);
            //System.out.println("(C): " + shortForm);
            
            System.out.println("(C): " + extractElementString(cls.toString()));
            
        }
    }
    
    
    
    
}

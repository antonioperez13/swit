package commons.tree;

/**
 * Tipos de elementos de una ontología
 * @author Antonio
 *
 */
public enum OwlType {
	CLASS("Class"), 
	INDIVIDUAL("Individual"), 
	DATATYPE("Datatype"), 
	PROPERTY("Property");
	
	private String code;

    OwlType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
}

package commons.tree;

/**
 * Tipos de elementos de una ontología
 * @author Antonio
 *
 */
public enum OwlType {
	CLASS("C"), 
	INDIVIDUAL("I"), 
	DATATYPE("D"), 
	PROPERTY("O");
	
	private String code;

    OwlType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
}

package es.um.swit.enums;

public enum TipoFichero {
	// Tipos de origen
	XSD("XML schema"),
	DATABASE("Relational database"),
	ARCHETYPED("Archtyped data"),
	
	// Tipos de destino
	OWL("OWL ontology file"),
	KNOWLEDGEBASE("Knowledge base"),
	VIRTUOSO("Virtuoso repository");
	
	private String desc;

	TipoFichero(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return desc;
    }
}

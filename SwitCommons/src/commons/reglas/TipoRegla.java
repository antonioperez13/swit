package commons.reglas;

public enum TipoRegla {
	CLASE("Arch2Class"),
	PROPIEDAD("Arch2Prop"),
	RELACION("Arch2Rel");
	
	private String code;

	TipoRegla(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

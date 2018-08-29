package es.um.swit.backup.resources;

import java.util.HashMap;
import java.util.Map;

public enum DescripcionRespuesta {
	dErrorIndeterminado(-1, "Error indeterminado"),
	
	// Operaciones terminadas correctamente
	d0(0, "OK"),
	d1(1, "Se ha creado un nuevo registro"),
	d2(2, "Había un registro con el ID indicado y se ha sobreescrito"),
	d3(3, "Se ha recuperado el fichero de mappings del registro"),
	
	// Operaciones terminadas incorrectamente
	d100(100, "No se ha podido guardar el fichero de mappings"),
	d101(101, "No había registro con el id indicado"),
	d102(102, "No se ha podido guardar el fichero de mappings con el id indicado"),
	d103(103, "No se ha encontrado el fichero asociado al registro indicado");
	
    private int value;
    private String desc;
    private static Map<Integer, DescripcionRespuesta> map = new HashMap<>();

    private DescripcionRespuesta(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    static {
        for (DescripcionRespuesta pageType : DescripcionRespuesta.values()) {
            map.put(pageType.value, pageType);
        }
    }
    
    /**
     * Devuelve la descripción de la respuesta asociada al código indicado.
     * @param codigo
     * @return
     */
    public static String getDescripcionRespuesta(int codigo) {
    	String desc = map.get(codigo).getDesc();
    	if(desc == null) {
    		return dErrorIndeterminado.getDesc();
    	}
    	return desc;
    }

    public int getValue() {
        return value;
    }
    
    public String getDesc() {
    	return desc;
    }
}


// El primer valor es el que se declara, se transforma en el segundo
var TipoRegla = Object.freeze({"CLASE":"CLASE", "PROPIEDAD":"PROPIEDAD", "RELACION":"RELACION"});
//var TipoElementoOwl = Object.freeze({"CLASS":"C", "INDIVIDUAL":"I", "DATATYPE":"D" , "PROPERTY":"O"});


function Regla(id, tipo, domainNodeSource, domainClassTarget, rangeNodeSource, rangeClassTarget, propertyValueSource, propertyTarget, etiqueta){
	this.tipo = tipo;
	
	this.id = id;
	
	this.domainNodeSource = domainNodeSource;
	this.rangeNodeSource = rangeNodeSource;
	this.propertyValueSource = propertyValueSource;
	
	this.domainClassTarget = domainClassTarget;
	this.rangeClassTarget = rangeClassTarget;
	this.propertyTarget = propertyTarget;
	
	this.etiqueta = etiqueta;
}

function comprobarTipoRegla(regla){
	// Sea cual sea la regla, debe tener informados estos dos campos
	if(regla.domainNodeSource != null && regla.domainClassTarget != null){
		
		// Si tiene informado este campo la regla puede ser de propiedad o de relación
		if(regla.propertyTarget !=null){
			// Si tiene rangeNodeSource y rangeClassTarget pero no propertyValueSource es una regla de relacion
			if(regla.rangeNodeSource != null && regla.rangeClassTarget != null &&
					regla.propertyValueSource == null){
				if(regla.tipo == TipoRegla.RELACION){
					return "Regla de relación: correcta";
				}
			} 
			// Si no tiene rangeNodeSource y rangeClassTarget pero si propertyValueSource es una regla de propiedad
			else if(regla.rangeNodeSource == null && regla.rangeClassTarget == null &&
					regla.propertyValueSource != null){
				if(regla.tipo == TipoRegla.PROPIEDAD){
					return "Regla de propiedad: correcta";
				}
			}
		}
		// Si no cumplía alguno de los otros casos solo queda comprobar que el tipo de regla de clase sea correcto
		else if(regla.tipo == TipoRegla.CLASE){
			return "Regla de clase: correcta";
		}
	}
	return "Regla incorrecta";
}

/**
 * Prepara la regla dada para que sea una regla de clase, por lo que establece a null
 * todos los atributos que no sean necesarios para ese tipo de regla.
 * @param regla
 * @returns
 */
function prepararReglaClase(regla){
	
	// Comprobamos que tenga los valores mínimos para el tipo de regla
	if(!isReglaClase(regla)){
		return null;
	}
	
	var newRegla = Object.assign({}, regla); 
	
	// Establecemos el tipo de regla
	newRegla.tipo = TipoRegla.CLASE;
	
	// Eliminamos los atributos innecesarios para la regla
	newRegla.rangeNodeSource = null;
	newRegla.rangeClassTarget = null;
	newRegla.propertyValueSource = null;
	newRegla.propertyTarget = null;
	
	return newRegla;
}

/**
 * Prepara la regla dada para que sea una regla de propiedad, por lo que establece a null
 * todos los atributos que no sean necesarios para ese tipo de regla.
 * @param regla
 * @returns
 */
function prepararReglaPropiedad(regla){
	
	// Comprobamos que tenga los valores mínimos para el tipo de regla
	if(!isReglaPropiedad(regla)){
		return null;
	}
	
	var newRegla = Object.assign({}, regla); 
	
	// Establecemos el tipo de regla
	newRegla.tipo = TipoRegla.PROPIEDAD;
	
	// Eliminamos los atributos innecesarios para la regla
	newRegla.rangeNodeSource = null;
	newRegla.rangeClassTarget = null;
	
	return newRegla;
}

/**
 * Prepara la regla dada para que sea una regla de relacion, por lo que establece a null
 * todos los atributos que no sean necesarios para ese tipo de regla.
 * @param regla
 * @returns
 */
function prepararReglaRelacion(regla){
	
	// Comprobamos que tenga los valores mínimos para el tipo de regla
	if(!isReglaRelacion(regla)){
		return null;
	}
	
	var newRegla = Object.assign({}, regla); 
	
	// Establecemos el tipo de regla
	newRegla.tipo = TipoRegla.RELACION;
	
	// Eliminamos los atributos innecesarios para la regla
	newRegla.propertyValueSource = null;
	
	return newRegla;
}




/**
 * Comprueba si tiene los campos necesarios para ser una regla de clase.
 * @param regla
 * @returns true en caso de tener la información necesaria, false en caso contrario.
 */
function isReglaClase(regla){
	if(regla == null){
		return false;
	}
	
	if(regla.domainNodeSource == null || regla.domainClassTarget == null){
		return false;
	}
	return true;
}

function isReglaPropiedad(regla){
	if(!isReglaClase(regla)){
		return false;
	}
	
	if(regla.propertyTarget == null || regla.propertyValueSource == null){
		return false;
	}
	
	return true;
}

function isReglaRelacion(regla){
	if(!isReglaClase(regla)){
		return false;
	}
	
	if(regla.propertyTarget == null  || regla.rangeNodeSource == null || 
			regla.rangeClassTarget == null){
		return false;
	}
	
	return true;
}


/**
 * Comprueba si la variable de tipo String es null o vacía
 * @param string
 * @returns
 */
function isNullOrVoid(string){
	return string == null || string === "";
}



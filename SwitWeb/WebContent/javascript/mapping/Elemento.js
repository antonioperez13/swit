
// El primer valor es el que se declara, se transforma en el segundo
var TipoElementoOwl = Object.freeze({"CLASS":"C", "INDIVIDUAL":"I", "DATATYPE":"D" , "PROPERTY":"O"});


function Elemento(name, id, route, type){
	this.name = name;
	
	this.id = id;
	this.route = route;
	this.type = type;
}


/**
 * Comprueba si la variable de tipo String es null o vacía
 * @param string
 * @returns
 */
function isNullOrVoid(string){
	return string == null || string === "";
}



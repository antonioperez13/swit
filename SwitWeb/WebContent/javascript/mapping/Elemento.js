
// El primer valor es el que se declara, se transforma en el segundo
var TipoElementoOwl = Object.freeze({"CLASS":"Class", "INDIVIDUAL":"Individual", "DATATYPE":"Datatype" , "PROPERTY":"Property"});


function Elemento(name, id, route, type, uri){
	this.name = name;
	
	this.id = id;
	this.route = route;
	this.type = type;
	this.uri = uri;
}


/**
 * Comprueba si la variable de tipo String es null o vacía
 * @param string
 * @returns
 */
function isNullOrVoid(string){
	return string == null || string === "";
}



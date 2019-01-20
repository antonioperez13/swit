// El identificador de cada elemento del formulario será "idElementoFormulario + numElementoFormulario"
var idElementoFormulario = "formElement";
var numElementoFormulario = 0;

/**
 * Inicia la preparación del formulario, recorriendo las columnas definidas en el JSON.
 * @returns
 */
function prepararFormulario(){
	var idColumna = "subcolumna";
	
	var numColumna;
	for (numColumna = 0; numColumna < jsonOptions.columns.length; numColumna++) {
		var divColumn = document.getElementById(idColumna + (numColumna+1));
		crearCampos(divColumn, jsonOptions.columns[numColumna].fields);
	}
	
}

/**
 * Crea los campos que se encuentren en el elemento JSON seleccionado dentro del elemento
 * contenedor.
 * @param elementoContenedor
 * @param jsonCampos
 * @returns
 */
function crearCampos(elementoContenedor, jsonCampos){
	var numCampo;
	for(numCampo = 0; numCampo < jsonCampos.length; numCampo++) {
		crearCampo(jsonCampos[numCampo], elementoContenedor);
	}
}

/**
 * Crea el campo de texto que se le pasa en el elemento HTML indicado.
 * @param jsonCampo
 * @param elementoContenedor
 * @returns
 */
function crearCampo(jsonCampo, elementoContenedor){
	//console.log(jsonCampo.name + ": " + jsonCampo.command);
	
	// Título de la opción
	var header = document.createElement('h2');
	// Si es un campo obligatorio se maracará con un asterisco en rojo
	if(jsonCampo.required){
		header.innerHTML = jsonCampo.name + "&nbsp;<label style='color: red;font-weight: bold;'>*</label>" 
	} else {
		header.innerText = jsonCampo.name;
	}
	elementoContenedor.appendChild(header);
	
	// Divisor horizontal
	var divisor = document.createElement('hr');
	divisor.className += " hr-min-margin";
	elementoContenedor.appendChild(divisor);
	
	// Si el campo tiene opciones será un seleccionable
	if(jsonCampo.options != null && jsonCampo.options.length > 0){
		crearCampoOpciones(jsonCampo, elementoContenedor);
	} 
	// Si el campo tiene subcampos éstos serán nuevos elementos dependientes del padre
	else {
		// Se crea el input de texto
		var textInput = document.createElement("input");
		textInput.id = idElementoFormulario + numElementoFormulario++;
		textInput.type = "text";
		textInput.setAttribute("command", jsonCampo.command);
		textInput.className += " form-control width100";
		elementoContenedor.appendChild(textInput);
		
		// Si el campo tuviera subcampos se crean también
		if(jsonCampo.fields != null && jsonCampo.fields.length > 0){
			crearCampos(crearContenedor(elementoContenedor), jsonCampo.fields);
		}
	}
}

/**
 * Crea un menú desplegable en el elemento HTML indicado.
 * @param jsonCampo
 * @param elementoContenedor
 * @returns
 */
function crearCampoOpciones(jsonCampo, elementoContenedor) {
	// Se crea un select
	var select = document.createElement("select");
	select.id = idElementoFormulario + numElementoFormulario++;
	select.setAttribute("command", jsonCampo.command);
	select.className += " form-control width100";
	// Atributo especial para el elemento que deberá ser el primero en el comando
	if(jsonCampo.firstCommand != null){
		select.setAttribute("firstCommand", true);
	}
	elementoContenedor.appendChild(select);
	
	var opciones = jsonCampo.options;
	
	// Se añaden las opciones al select
	var numOpcion;
	var tieneSubcampos = false;
	for(numOpcion = 0; numOpcion < opciones.length; numOpcion++) {
		//console.log(opciones[numOpcion].name + ": " + opciones[numOpcion].command);
		
		var option = document.createElement("option");
		option.text = opciones[numOpcion].name;
		option.value = opciones[numOpcion].command;
		if(opciones[numOpcion].parallel != null){
			option.setAttribute("parallel", true);
		}
		select.add(option);
		
		// Si el campo tiene subcampos éstos serán nuevos elementos dependientes del padre
		if(opciones[numOpcion].fields != null && opciones[numOpcion].fields.length > 0){
			tieneSubcampos = true;
			var divContenedor = crearContenedor(elementoContenedor);
			var condicionesContenedor = select.getAttribute("condicionesContenedor");
			if(condicionesContenedor == null){
				condicionesContenedor = "";
			}
			
			condicionesContenedor += option.value + ":" + divContenedor.id + ";"; 
			
			select.setAttribute("condicionesContenedor", condicionesContenedor);
			
			crearCampos(divContenedor, opciones[numOpcion].fields);
		}
	}
	
	if(tieneSubcampos){
		select.onchange = function() {cambiarDivVisible(select);};
	}
}

/**
 * Crea un contendor en el elemento indicado.
 * @param elementoPadre
 * @returns
 */
function crearContenedor(elementoPadre){
	var contenedorHijo = document.createElement('div');
	contenedorHijo.id = idElementoFormulario + numElementoFormulario++;
	contenedorHijo.className += " padl-20";
	contenedorHijo.style.display = "none";
	elementoPadre.appendChild(contenedorHijo);
	return contenedorHijo;
}

/**
 * Controla la visibilidad del elemento indicado según un atributo "condicionesContendor".
 * @param selectElement
 * @returns
 */
function cambiarDivVisible(selectElement){
	var condicionesVisibilidadContenedor = selectElement.getAttribute("condicionesContenedor").split(";").filter(function(el) {return el.length != 0});
	condicionesVisibilidadContenedor.forEach(function(element) {
		var tupla = element.split(":");
		if(selectElement.options[selectElement.selectedIndex].value === tupla[0]){
			document.getElementById(tupla[1]).style.display = "block";
		} else {
			document.getElementById(tupla[1]).style.display = "none";
		}
	});
}

/**
 * Construye el comando tomando todos los campos de texto y menus desplegables del formulario. 
 * @param errorMsg
 * @returns
 */
function construirComando(errorMsg){
	var comando = "";
	
	var firstCommandElement = document.querySelectorAll('[firstCommand]')[0];
	var firstCommandOption = firstCommandElement.options[firstCommandElement.selectedIndex];
	comando += tratarCadenaComando(firstCommandOption.value);
	
	var separator = " ";
	if(firstCommandOption.getAttribute("parallel") != null){
		separator = "=";
	}
	
	var selects = document.querySelectorAll("select:not([firstCommand])");
	var inputs = document.querySelectorAll("input[type='text']");
	
	
	// Selects
	var i;
	for(i = 0; i < selects.length; i++) {
		if(selects[i].parentNode.style.display != "none"){
			comando += tratarCadenaComandoValor(selects[i].getAttribute("command"), selects[i].options[selects[i].selectedIndex].value, separator);
		}
	}
	
	// Inputs
	var j;
	for(j = 0; j < inputs.length; j++) {
		if(inputs[j].value != "" && inputs[j].parentNode.style.display != "none"){
			// Se añade el input si procede
			comando += tratarCadenaComandoValor(inputs[j].getAttribute("command"), inputs[j].value, separator);
		}
	}
	
	// Se pone el resultado en el elemento para mostrarlo
	document.getElementById("buttonComandoGenerado").value = comando.trim();
	document.getElementById("buttonComandoGenerado").title = '<spring:message code="configurador.title.copiar.comando.generado"/>';
	document.getElementById("buttonComandoGenerado").className = 
		document.getElementById("buttonComandoGenerado").className.replace("btn-default", "btn-success").replace("btn-danger", "btn-success");
	
	return comando;
}


/**
 * Comprueba si el valor pasado está vacío, en cuyo caso devuelve la cadena vacía.
 * Si el valor no está vacío le concatena un espacio detrás.
 * @param valor
 * @returns
 */
function tratarCadenaComando(valor){
	if(valor == ""){
		return "";
	} else {
		return valor + " ";
	}
}

/**
 * Comprueba si el comando y el valor pasados están vacíos, en cuyo caso devuelve la cadena vacía.
 * En otro caso devuelve la concatenación de comando y valor separados por espacio o un separador específico si el comando empieza o no por "--" .
 * @param comando
 * @param valor
 * @param separador
 * @returns
 */
function tratarCadenaComandoValor(comando, valor, separador){
	if(comando == "" && valor == ""){
		return "";
	}
	
	if(comando.startsWith("--")){
		return comando + separador + valor + " ";
	} else {
		return comando + " " + valor + " ";
	}
}



/**
 * Copia el contenido del string pasado como parámetro al
 * portapapeles.
 * @param str
 * @returns
 */
function copyStringToClipboard (str) {
	   // Se crea un elemento auxiliar
	   var auxElement = document.createElement('textarea');
	   // Se le establece la cadena a copiar
	   auxElement.value = str;
	   // Se configura como no editable para evitar perder el foco
	   auxElement.setAttribute('readonly', '');
	   auxElement.style = {position: 'absolute', left: '-9999px'};
	   document.body.appendChild(auxElement);
	   // Se selecciona el texto
	   auxElement.select();
	   // Se copia el texto
	   document.execCommand('copy');
	   // Se elimina el elemento auxiliar
	   document.body.removeChild(auxElement);
}
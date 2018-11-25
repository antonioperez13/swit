<%@include file="../commons/taglibs.jsp" %>

<!-- INI - Pasos creaciones guiadas -->
<jsp:include page="/WEB-INF/jsp/mapping/creacionGuiadaReglaClase.jsp"/>
<jsp:include page="/WEB-INF/jsp/mapping/creacionGuiadaReglaPropiedad.jsp"/>
<jsp:include page="/WEB-INF/jsp/mapping/creacionGuiadaReglaRelacion.jsp"/>
<!-- FIN - Pasos creaciones guiadas -->


<script type="text/javascript">

/**#########################################
###			Botones esquema origen
#########################################**/

/**
 * Añade la funcionalidad de elegir el elemento domain node al botón de la creación guiada.
 * También habilita (si aplica) el botón para ir al siguiente paso de la creación de la regla.
 * Los identificadores de los botones "Añadir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectDomainNode(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirDomainNode(idBotonSiguiente, tour);});
}

/**
 * Añade la funcionalidad de elegir el elemento range node source al botón de la creación guiada.
 * También habilita (si aplica) el botón para ir al siguiente paso de la creación de la regla.
 * Los identificadores de los botones "Añadir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectRangeNodeSource(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirRangeNodeSource(idBotonSiguiente, tour);});
}

/**
 * Añade la funcionalidad de elegir el elemento property value source al botón de la creación guiada.
 * También habilita (si aplica) el botón para ir al siguiente paso de la creación de la regla.
 * Los identificadores de los botones "Añadir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectPropertyValueSource(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirPropertyValueSource(idBotonSiguiente, tour);});
}



/**#########################################
###			Botones esquema destino
#########################################**/

/**
 * Añade la funcionalidad de elegir el elemento domain class al botón de la creación guiada.
 * También habilita (si aplica) el botón para ir al siguiente paso de la creación de la regla.
 * Los identificadores de los botones "Añadir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectDomainClass(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirDomainClass(idBotonSiguiente, tour);});
}

/**
 * Añade la funcionalidad de elegir el elemento range class target al botón de la creación guiada.
 * También habilita (si aplica) el botón para ir al siguiente paso de la creación de la regla.
 * Los identificadores de los botones "Añadir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectRangeClassTarget(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirRangeClassTarget(idBotonSiguiente, tour);});
}

/**
 * Añade la funcionalidad de elegir el elemento property target al botón de la creación guiada.
 * También habilita (si aplica) el botón para ir al siguiente paso de la creación de la regla.
 * Los identificadores de los botones "Añadir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectPropertyTarget(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirPropertyTarget(idBotonSiguiente, tour);});
}


/**##############################################################
###	Seleccion de elementos en esquema origen
##############################################################**/

/**
 * Elige el elemento seleccionado como domain node y comprueba si se debe habilitar el botón "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirDomainNode(idBotonSiguiente, tour) {
	selectDomainNodeSource();
	comprobarElementoSeleccionadoDomainNode(idBotonSiguiente);
	// Si el elemento se ha añadido correctamente saltamos al siguiente paso
	if(nuevaRegla.domainNodeSource != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como range node source y comprueba si se debe habilitar el botón "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirRangeNodeSource(idBotonSiguiente, tour) {
	selectRangeNodeSource();
	comprobarElementoSeleccionadoRangeNodeSource(idBotonSiguiente);
	// Si el elemento se ha añadido correctamente saltamos al siguiente paso
	if(nuevaRegla.rangeNodeSource != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como property value source y comprueba si se debe habilitar el botón "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirPropertyValueSource(idBotonSiguiente, tour) {
	selectPropertySource();
	comprobarElementoSeleccionadoPropertyValueSource(idBotonSiguiente);
	// Si el elemento se ha añadido correctamente saltamos al siguiente paso
	if(nuevaRegla.propertyValueSource != null){
		tour.next();
	}
}


/**##############################################################
###	Seleccion de elementos en esquema destino
##############################################################**/

/**
 * Elige el elemento seleccionado como domain class y comprueba si se debe habilitar el botón "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirDomainClass(idBotonSiguiente, tour) {
	selectDomainClassTarget();
	comprobarElementoSeleccionadoDomainClass(idBotonSiguiente);
	// Si el elemento se ha añadido correctamente saltamos al siguiente paso
	if(nuevaRegla.domainClassTarget != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como range class target y comprueba si se debe habilitar el botón "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirRangeClassTarget(idBotonSiguiente, tour) {
	selectRangeClassTarget();
	comprobarElementoSeleccionadoRangeClassTarget(idBotonSiguiente);
	// Si el elemento se ha añadido correctamente saltamos al siguiente paso
	if(nuevaRegla.rangeClassTarget != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como property target y comprueba si se debe habilitar el botón "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirPropertyTarget(idBotonSiguiente, tour) {
	selectPropertyTarget();
	comprobarElementoSeleccionadoPropertyTarget(idBotonSiguiente);
	// Si el elemento se ha añadido correctamente saltamos al siguiente paso
	if(nuevaRegla.propertyTarget != null){
		tour.next();
	}
}


/**##############################################################
###	Comprobacion de seleccion de elementos en esquema origen
##############################################################**/


/**
 * Si hay elegido un elemento para el domain node el botón para ir al siguiente pasó se habilitará,
 * si no hay elemento se deshabilitará.
 * @param idBotonSiguiente Identificador del botón que se habilitará/deshabilitará
 * @returns
 */
function comprobarElementoSeleccionadoDomainNode(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.domainNodeSource != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el range node source el botón para ir al siguiente pasó se habilitará,
 * si no hay elemento se deshabilitará.
 * @param idBotonSiguiente Identificador del botón que se habilitará/deshabilitará
 * @returns
 */
function comprobarElementoSeleccionadoRangeNodeSource(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.rangeNodeSource != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el property value source el botón para ir al siguiente pasó se habilitará,
 * si no hay elemento se deshabilitará.
 * @param idBotonSiguiente Identificador del botón que se habilitará/deshabilitará
 * @returns
 */
function comprobarElementoSeleccionadoPropertyValueSource(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.propertyValueSource != null, idBotonSiguiente);
}


/**##############################################################
###	Comprobacion de seleccion de elementos en esquema destino
##############################################################**/

/**
 * Si hay elegido un elemento para el domain class el botón para ir al siguiente pasó se habilitará,
 * si no hay elemento se deshabilitará.
 * @param idBotonSiguiente Identificador del botón que se habilitará/deshabilitará
 * @returns
 */
function comprobarElementoSeleccionadoDomainClass(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.domainClassTarget != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el range class target el botón para ir al siguiente pasó se habilitará,
 * si no hay elemento se deshabilitará.
 * @param idBotonSiguiente Identificador del botón que se habilitará/deshabilitará
 * @returns
 */
function comprobarElementoSeleccionadoRangeClassTarget(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.rangeClassTarget != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el property target el botón para ir al siguiente pasó se habilitará,
 * si no hay elemento se deshabilitará.
 * @param idBotonSiguiente Identificador del botón que se habilitará/deshabilitará
 * @returns
 */
function comprobarElementoSeleccionadoPropertyTarget(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.propertyTarget != null, idBotonSiguiente);
}


/**##############################################################
###	Funcionalidades sobre botones
##############################################################**/

/**
 * Según el valor booleano del parámetro "habilitar" se habilitará o deshabilitará el botón
 * cuyo identificador se pasa por el parámetro "idBotonSiguiente".
 * @param habilitar
 * @param idBotonSiguiente
 * @returns
 */
function habilitarBotonSiguiente(habilitar, idBotonSiguiente){
	if(habilitar){
		habilitarBoton(idBotonSiguiente);
	} else {
		deshabilitarBoton(idBotonSiguiente);
	}
}

/**
 * Habilita el botón cuyo identificador se pasa por parámetro.
 * @param idBoton
 * @returns
 */
function habilitarBoton(idBoton){
	$('#'+idBoton).prop("disabled", false);
}

/**
 * Deshabilita el botón cuyo identificador se pasa por parámetro.
 * @param idBoton
 * @returns
 */
function deshabilitarBoton(idBoton){
	$('#'+idBoton).prop("disabled", true);
}

/**
 * Oculta el botón cuyo identificador se pasa por parámetro.
 * @param idBoton
 * @returns
 */
function ocultarBoton(idBoton) {
	$('#'+idBoton).hide();
}

/**
 * Muestra los elementos seleccionados, tanto de origen como de destino
 * @returns
 */
function mostrarElementosSeleccionados(){
	mostrarElementosIzq();
	mostrarElementosDer();
}

/**
 * Muestra el bloque de creación de condiciones
 * @returns
 */
function mostrarBloqueCondiciones() {
	$("#divCreacionGuiadaReglaPropiedadCondiciones").show();
	$("#divBloqueCondicionesBotones").show();
	// Se cargan las condiciones ya que el elemento que las contiene se elimina al cambiar de paso en la creación guiada
	document.getElementById("selectCondiciones").innerHTML = nuevaRegla.condiciones;
}

/**
 * Oculta el bloque de creación de condiciones
 * @returns
 */
function ocultarBloqueCondiciones() {
	$("#divCreacionGuiadaReglaPropiedadCondiciones").hide();
	// Se guardan las condiciones ya que el elemento que las contiene se elimina al cambiar de paso en la creación guiada
	nuevaRegla.condiciones = document.getElementById("selectCondiciones").innerHTML;
}


/**##############################################################
###	Funcionalidades para condiciones de Regla de propiedad
##############################################################**/

/**
 * Añade una nueva condición tomando el elemento seleccionado del esquema origen y el valor
 * que el usuario haya escrito en el cuadro de texto habilitado para ello.
 * @returns
 */
function addCondicion() {
	// Elemento select del HTML
	var select = document.getElementById("selectCondiciones");
	
	// Nueva condición que se está definiendo
	var condicion = document.createElement("option");
	
	// Se recupera el elemento seleccionado del esquema origen
	var selectedElement = getSelectedSourceElement();
	var name = $('#sourceSchema').jstree(true).get_selected(true)[0].text;
	var route = $(selectedElement).attr("ruta-elemento");
	
	// Valor que el usuario le asigna a la condición
	var valor = document.getElementById("idInputCondicionCreacionGuiadaReglaPropiedad").value;
	
	// Se asignan los valores a atributos de la opción
	condicion.setAttribute("nombre", name);
	condicion.setAttribute("ruta", route);
	condicion.setAttribute("valor", valor);
	
	// Se representa la condición en forma de texto para que el usuario la pueda identificar
	condicion.text = name + "=" + valor;
	
	// Se añade a la página
	select.add(condicion);
	
	// Se respaldan las condiciones ya que el plugin borra el contenido HTML del paso si la página
	// se mueve
	nuevaRegla.condiciones = document.getElementById("selectCondiciones").innerHTML;
}

/**
 * Elimina la condición seleccionada.
 * @returns
 */
function removeCondicion() {
	var select = document.getElementById("selectCondiciones");
	select.remove(select.selectedIndex)
}



</script>
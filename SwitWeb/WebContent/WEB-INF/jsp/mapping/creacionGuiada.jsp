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
 * A�ade la funcionalidad de elegir el elemento domain node al bot�n de la creaci�n guiada.
 * Tambi�n habilita (si aplica) el bot�n para ir al siguiente paso de la creaci�n de la regla.
 * Los identificadores de los botones "A�adir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectDomainNode(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirDomainNode(idBotonSiguiente, tour);});
}

/**
 * A�ade la funcionalidad de elegir el elemento range node source al bot�n de la creaci�n guiada.
 * Tambi�n habilita (si aplica) el bot�n para ir al siguiente paso de la creaci�n de la regla.
 * Los identificadores de los botones "A�adir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectRangeNodeSource(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirRangeNodeSource(idBotonSiguiente, tour);});
}

/**
 * A�ade la funcionalidad de elegir el elemento property value source al bot�n de la creaci�n guiada.
 * Tambi�n habilita (si aplica) el bot�n para ir al siguiente paso de la creaci�n de la regla.
 * Los identificadores de los botones "A�adir" y "Siguiente"
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
 * A�ade la funcionalidad de elegir el elemento domain class al bot�n de la creaci�n guiada.
 * Tambi�n habilita (si aplica) el bot�n para ir al siguiente paso de la creaci�n de la regla.
 * Los identificadores de los botones "A�adir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectDomainClass(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirDomainClass(idBotonSiguiente, tour);});
}

/**
 * A�ade la funcionalidad de elegir el elemento range class target al bot�n de la creaci�n guiada.
 * Tambi�n habilita (si aplica) el bot�n para ir al siguiente paso de la creaci�n de la regla.
 * Los identificadores de los botones "A�adir" y "Siguiente"
 * @param idBotonAdd
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function buttonAddClickSelectRangeClassTarget(idBotonAdd, idBotonSiguiente, tour){
	$('#'+idBotonAdd).off("click").on("click", function(){creacionGuiadaElegirRangeClassTarget(idBotonSiguiente, tour);});
}

/**
 * A�ade la funcionalidad de elegir el elemento property target al bot�n de la creaci�n guiada.
 * Tambi�n habilita (si aplica) el bot�n para ir al siguiente paso de la creaci�n de la regla.
 * Los identificadores de los botones "A�adir" y "Siguiente"
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
 * Elige el elemento seleccionado como domain node y comprueba si se debe habilitar el bot�n "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirDomainNode(idBotonSiguiente, tour) {
	selectDomainNodeSource();
	comprobarElementoSeleccionadoDomainNode(idBotonSiguiente);
	// Si el elemento se ha a�adido correctamente saltamos al siguiente paso
	if(nuevaRegla.domainNodeSource != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como range node source y comprueba si se debe habilitar el bot�n "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirRangeNodeSource(idBotonSiguiente, tour) {
	selectRangeNodeSource();
	comprobarElementoSeleccionadoRangeNodeSource(idBotonSiguiente);
	// Si el elemento se ha a�adido correctamente saltamos al siguiente paso
	if(nuevaRegla.rangeNodeSource != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como property value source y comprueba si se debe habilitar el bot�n "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirPropertyValueSource(idBotonSiguiente, tour) {
	selectPropertySource();
	comprobarElementoSeleccionadoPropertyValueSource(idBotonSiguiente);
	// Si el elemento se ha a�adido correctamente saltamos al siguiente paso
	if(nuevaRegla.propertyValueSource != null){
		tour.next();
	}
}


/**##############################################################
###	Seleccion de elementos en esquema destino
##############################################################**/

/**
 * Elige el elemento seleccionado como domain class y comprueba si se debe habilitar el bot�n "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirDomainClass(idBotonSiguiente, tour) {
	selectDomainClassTarget();
	comprobarElementoSeleccionadoDomainClass(idBotonSiguiente);
	// Si el elemento se ha a�adido correctamente saltamos al siguiente paso
	if(nuevaRegla.domainClassTarget != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como range class target y comprueba si se debe habilitar el bot�n "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirRangeClassTarget(idBotonSiguiente, tour) {
	selectRangeClassTarget();
	comprobarElementoSeleccionadoRangeClassTarget(idBotonSiguiente);
	// Si el elemento se ha a�adido correctamente saltamos al siguiente paso
	if(nuevaRegla.rangeClassTarget != null){
		tour.next();
	}
}

/**
 * Elige el elemento seleccionado como property target y comprueba si se debe habilitar el bot�n "Siguiente"
 * @param idBotonSiguiente
 * @param tour
 * @returns
 */
function creacionGuiadaElegirPropertyTarget(idBotonSiguiente, tour) {
	selectPropertyTarget();
	comprobarElementoSeleccionadoPropertyTarget(idBotonSiguiente);
	// Si el elemento se ha a�adido correctamente saltamos al siguiente paso
	if(nuevaRegla.propertyTarget != null){
		tour.next();
	}
}


/**##############################################################
###	Comprobacion de seleccion de elementos en esquema origen
##############################################################**/


/**
 * Si hay elegido un elemento para el domain node el bot�n para ir al siguiente pas� se habilitar�,
 * si no hay elemento se deshabilitar�.
 * @param idBotonSiguiente Identificador del bot�n que se habilitar�/deshabilitar�
 * @returns
 */
function comprobarElementoSeleccionadoDomainNode(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.domainNodeSource != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el range node source el bot�n para ir al siguiente pas� se habilitar�,
 * si no hay elemento se deshabilitar�.
 * @param idBotonSiguiente Identificador del bot�n que se habilitar�/deshabilitar�
 * @returns
 */
function comprobarElementoSeleccionadoRangeNodeSource(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.rangeNodeSource != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el property value source el bot�n para ir al siguiente pas� se habilitar�,
 * si no hay elemento se deshabilitar�.
 * @param idBotonSiguiente Identificador del bot�n que se habilitar�/deshabilitar�
 * @returns
 */
function comprobarElementoSeleccionadoPropertyValueSource(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.propertyValueSource != null, idBotonSiguiente);
}


/**##############################################################
###	Comprobacion de seleccion de elementos en esquema destino
##############################################################**/

/**
 * Si hay elegido un elemento para el domain class el bot�n para ir al siguiente pas� se habilitar�,
 * si no hay elemento se deshabilitar�.
 * @param idBotonSiguiente Identificador del bot�n que se habilitar�/deshabilitar�
 * @returns
 */
function comprobarElementoSeleccionadoDomainClass(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.domainClassTarget != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el range class target el bot�n para ir al siguiente pas� se habilitar�,
 * si no hay elemento se deshabilitar�.
 * @param idBotonSiguiente Identificador del bot�n que se habilitar�/deshabilitar�
 * @returns
 */
function comprobarElementoSeleccionadoRangeClassTarget(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.rangeClassTarget != null, idBotonSiguiente);
}

/**
 * Si hay elegido un elemento para el property target el bot�n para ir al siguiente pas� se habilitar�,
 * si no hay elemento se deshabilitar�.
 * @param idBotonSiguiente Identificador del bot�n que se habilitar�/deshabilitar�
 * @returns
 */
function comprobarElementoSeleccionadoPropertyTarget(idBotonSiguiente){
	habilitarBotonSiguiente(nuevaRegla.propertyTarget != null, idBotonSiguiente);
}


/**##############################################################
###	Funcionalidades sobre botones
##############################################################**/

/**
 * Seg�n el valor booleano del par�metro "habilitar" se habilitar� o deshabilitar� el bot�n
 * cuyo identificador se pasa por el par�metro "idBotonSiguiente".
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
 * Habilita el bot�n cuyo identificador se pasa por par�metro.
 * @param idBoton
 * @returns
 */
function habilitarBoton(idBoton){
	$('#'+idBoton).prop("disabled", false);
}

/**
 * Deshabilita el bot�n cuyo identificador se pasa por par�metro.
 * @param idBoton
 * @returns
 */
function deshabilitarBoton(idBoton){
	$('#'+idBoton).prop("disabled", true);
}

/**
 * Oculta el bot�n cuyo identificador se pasa por par�metro.
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

</script>
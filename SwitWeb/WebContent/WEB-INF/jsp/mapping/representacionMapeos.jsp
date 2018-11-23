<%@include file="../commons/taglibs.jsp" %>

<script type="text/javascript">
	/**
	 * Elimina la regla asociada al botón de eliminación pulsado.
	 * @param botonEliminar
	 * @returns
	 */
	function eliminarRegla(botonEliminar){
		var divPlegableRegla = $(botonEliminar).closest("div:has([id^=botones-regla-])")[0];
		var reglaId = divPlegableRegla.getAttribute("id").split("plegable-regla-")[1];
		
		// Elimina la regla de la página
		$(divPlegableRegla).animate({height:'0'});
		$(divPlegableRegla).animate({margin:'0'});
		$(divPlegableRegla).css("border", "0");
		setTimeout(function(){
			$(divPlegableRegla).remove();
		},1500);
		
		// Elimina la regla del servidor
		removeRuleById(reglaId);
	}
	
	/**
	 * Elimina todas las reglas creadas, tanto en el cliente como en el servidor.
	 * @returns
	 */
	function eliminarTodasReglas(){
		var respuesta = confirm("<spring:message code='mapeo.texto.borrar.todas.reglas.confirmacion'/>");
	    if (respuesta == false) {
	        return;
	    }
		
		ColeccionReglas = [];
		
		var divPlegables = $("[id^=plegable-regla-]");
		
		// Elimina todas las reglas de la página
		$(divPlegables).animate({height:'0'});
		$(divPlegables).animate({margin:'0'});
		$(divPlegables).css("border", "0");
		setTimeout(function(){
			$(divPlegables).remove();
		},1500);
		
		// Borra todas las reglas del servidor
		borrarReglasServidor();
	}
	
	/**
	 * Elimina todas las reglas del cliente.
	 * @returns
	 */
	function eliminarTodasReglasCliente(){
		ColeccionReglas = [];
		
		var divPlegables = $("[id^=plegable-regla-]");
		
		// Elimina todas las reglas de la página
		$(divPlegables).remove();
	}
	
	/**
	 * Pliega todas las reglas que se muestran en el bloque Reglas creadas
	 * @returns
	 */
	function contraerTodasReglas(){
		$('[id^=datos-regla-]').collapse('hide');
	}
	
	/**
	 * Despliega todas las reglas que se muestran en el bloque Reglas creadas
	 * @returns
	 */
	function expandirTodasReglas(){
		$('[id^=datos-regla-]').collapse('show');
	}
	
	/**
	 * Devuelve una cadena de texto que resume la regla.
	 * Usa los nombres de los elementos que conforman la regla
	 * @param regla
	 * @returns
	 */
	function getTituloResumenReglaCreada(regla){
		if(regla.tipo == TipoRegla.CLASE) {
			return regla.domainNodeSource.name + " -> " + regla.domainClassTarget.name;
		} else if(regla.tipo == TipoRegla.PROPIEDAD) {
			return regla.domainNodeSource.name + " (" + regla.propertyValueSource.name + ")" + " -> " +
				   regla.domainClassTarget.name + " (" + regla.propertyTarget.name + ")";
		} if(regla.tipo == TipoRegla.RELACION) { 
			return regla.domainNodeSource.name + " (" + regla.rangeNodeSource.name + ")" + " -> " +
				   regla.domainClassTarget.name + " (" + regla.rangeClassTarget.name + " | " + regla.propertyTarget.name + ")";
		}
	}
	
	/**
	 * Genera un contendor para representar los datos de una regla.
	 * El identificador del contenedor será "plegable-regla-" terminado 
	 * con el id de la regla.
	 * @param reglaId
	 * @returns El nuevo contenedor
	 */
	function getNuevoContenedorRegla(reglaId){
		// Creamos el contenedor de la nueva regla
		var contenedorNuevaRegla = document.createElement("div");
		contenedorNuevaRegla.id = "plegable-regla-" + reglaId;
		contenedorNuevaRegla.className= "panel panel-default transicion";
		
		return contenedorNuevaRegla;
	}
	
	/**
	 * Añade los botones que controlan la visibilidad de los datos de la regla
	 * y la eliminación de la misma.
	 * @param regla
	 * @param contenedorNuevaRegla
	 * @returns
	 */
	function addBotonesContenedorNuevaRegla(regla, contenedorNuevaRegla){
		//var tituloResumenRegla = getTituloResumenReglaCreada(regla);
		var tituloResumenRegla = regla.etiqueta;
		var dataBotones = {
	        botonesRegla: 'botones-regla-' + regla.id,
	        botonPlegarRegla: 'boton-plegar-regla-' + regla.id,
	        targetDatosRegla: '#datos-regla-' + regla.id,
	        botonPlegarContenido: tituloResumenRegla
	    };
		
		$(contenedorNuevaRegla).loadTemplate($("#plantilla-botones"), dataBotones, { append: true });
	}
	
	/**
	 * Añade el esqueleto donde se introducirán los datos de la regla.
	 * Se diferencian dos secciones: datos del esquema de origen y datos del
	 * esquema de destino.
	 * @param regla
	 * @param contenedorNuevaRegla
	 * @returns
	 */
	function addEstructuraContenidoNuevaRegla(regla, contenedorNuevaRegla){
		var dataContenido = {
			datosRegla: "datos-regla-" + regla.id,
			datosElementosOrigenRegla: "datos-elementos-origen-regla-" + regla.id,
			datosElementosDestinoRegla: "datos-elementos-destino-regla-" + regla.id
	    };
		
		$(contenedorNuevaRegla).loadTemplate($("#plantilla-contenido"), dataContenido, { append: true });
	}
	
	/**
	 * Dada una regla crea la representación en HTML para mostrarla en la página.
	 * La cantidad de información que mostrará dependerá del tipo de regla.
	 * @param regla
	 * @returns
	 */
	function representarRegla(regla){
		// Contenedor donde se guardan todas las reglas creadas
		var contenedorReglasCreadas = $("#representacionReglasContenedor");
		
		// Contenedor para la nueva regla
		var contenedorNuevaRegla = getNuevoContenedorRegla(regla.id);
		
		// Se añaden los botones de control sobre el contenedor de la regla
		addBotonesContenedorNuevaRegla(regla, contenedorNuevaRegla);
		
		// Se añade la estructura donde se guardarán los elementos
		addEstructuraContenidoNuevaRegla(regla, contenedorNuevaRegla)
		
		// Se añade el contenedor creado a la página
		$(contenedorReglasCreadas).append(contenedorNuevaRegla);
		
		// Recuperamos los contendores de los datos de la regla
		var contenedorDatosOrigen = $("#datos-elementos-origen-regla-" + regla.id);
		var contenedorDatosDestino = $("#datos-elementos-destino-regla-" + regla.id);
		
		// Según el tipo de regla se añaden los elementos adecuados
		if(regla.tipo == TipoRegla.CLASE){
			$('#boton-plegar-regla-' + regla.id).addClass("btn-success");
			representarReglaClase(regla, contenedorDatosOrigen, contenedorDatosDestino);
		} else if(regla.tipo == TipoRegla.PROPIEDAD){
			$('#boton-plegar-regla-' + regla.id).addClass("btn-violet");
			representarReglaPropiedad(regla, contenedorDatosOrigen, contenedorDatosDestino);
		} else if(regla.tipo == TipoRegla.RELACION){
			$('#boton-plegar-regla-' + regla.id).addClass("btn-warning");
			representarReglaRelacion(regla, contenedorDatosOrigen, contenedorDatosDestino);
		}
	}
	
	/**
	 * Añade los datos de los elementos de una regla de clase.
	 * @param regla
	 * @param contenedorDatosOrigen
	 * @param contenedorDatosDestino
	 * @returns
	 */
	function representarReglaClase(regla, contenedorDatosOrigen, contenedorDatosDestino){
		// Datos esquema origen
		addElementoEsquemaOrigen(regla.domainNodeSource, "<spring:message code='mapeo.creacionGuiada.texto.domainNode'/>", contenedorDatosOrigen);
		
		// Datos esquema destino
		addElementoEsquemaDestino(regla.domainClassTarget, "<spring:message code='mapeo.creacionGuiada.texto.domainClass'/>", contenedorDatosDestino);
	}
	
	/**
	 * Añade los datos de los elementos de una regla de propiedad.
	 * @param regla
	 * @param contenedorDatosOrigen
	 * @param contenedorDatosDestino
	 * @returns
	 */
	function representarReglaPropiedad(regla, contenedorDatosOrigen, contenedorDatosDestino){
		// Datos esquema origen
		addElementoEsquemaOrigen(regla.domainNodeSource, "<spring:message code='mapeo.creacionGuiada.texto.domainNode'/>", contenedorDatosOrigen);
		addElementoEsquemaOrigen(regla.propertyValueSource, "<spring:message code='mapeo.creacionGuiada.texto.propertyValueSource'/>", contenedorDatosOrigen);
		
		// Datos esquema destino
		addElementoEsquemaDestino(regla.domainClassTarget, "<spring:message code='mapeo.creacionGuiada.texto.domainClass'/>", contenedorDatosDestino);
		addElementoEsquemaDestino(regla.propertyTarget, "<spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/>", contenedorDatosDestino);
	}
	
	/**
	 * Añade los datos de los elementos de una regla de relación.
	 * @param regla
	 * @param contenedorDatosOrigen
	 * @param contenedorDatosDestino
	 * @returns
	 */
	function representarReglaRelacion(regla, contenedorDatosOrigen, contenedorDatosDestino){
		// Datos esquema origen
		addElementoEsquemaOrigen(regla.domainNodeSource, "<spring:message code='mapeo.creacionGuiada.texto.domainNode'/>", contenedorDatosOrigen);
		addElementoEsquemaOrigen(regla.rangeNodeSource, "<spring:message code='mapeo.creacionGuiada.texto.rangeNodeSource'/>", contenedorDatosOrigen);
		
		// Datos esquema destino
		addElementoEsquemaDestino(regla.domainClassTarget, "<spring:message code='mapeo.creacionGuiada.texto.domainClass'/>", contenedorDatosDestino);
		addElementoEsquemaDestino(regla.rangeClassTarget, "<spring:message code='mapeo.creacionGuiada.texto.rangeClassTarget'/>", contenedorDatosDestino);
		addElementoEsquemaDestino(regla.propertyTarget, "<spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/>", contenedorDatosDestino);
	}
	
	/**
	 * Plantilla para añadir los datos de un elemento del esquema de origen.
	 * @param elemento
	 * @param nombreTipoElementoRegla
	 * @param contenedorDatosOrigen
	 * @returns
	 */
	function addElementoEsquemaOrigen(elemento, nombreTipoElementoRegla, contenedorDatosOrigen){
		var dataElementoOrigen = {
			nombreTipoElemento: nombreTipoElementoRegla,
			nombreElemento: elemento.name
	    };
		
		$(contenedorDatosOrigen).loadTemplate($("#plantilla-elemento-generico"), dataElementoOrigen, { append: true });
	}
	
	/**
	 * Plantilla para añadir los datos de un elemento del esquema de destino.
	 * @param elemento
	 * @param nombreTipoElementoRegla
	 * @param contenedorDatosDestino
	 * @returns
	 */
	function addElementoEsquemaDestino(elemento, nombreTipoElementoRegla, contenedorDatosDestino){
		var dataElementoDestino = {
			nombreTipoElemento: nombreTipoElementoRegla,
			nombreElemento: elemento.name,
			uriElemento: "<spring:message code='mapeo.representacionRegla.uri'/>" + escapeHtml(elemento.uri)
	    };
		
		$(contenedorDatosDestino).loadTemplate($("#plantilla-elemento-owl"), dataElementoDestino, { append: true });
	}
</script>

<!-- Plantilla botones -->
<script type="text/html" id="plantilla-botones">
	<div data-id="botonesRegla" class="btn-group btn-group-justified"
		role="group" aria-label="">
		<div class=" btn-group" role="group" style="width: 95%;">
			<button data-template-bind='[
     			{"attribute": "id", "value": "botonPlegarRegla"},
     			{"attribute": "data-target", "value": "targetDatosRegla"},
				{"attribute": "content", "value": "botonPlegarContenido"}
			]'
				class="list-group-button-title btn collapsed"
				data-toggle="collapse"
				title="<spring:message code='comunes.boton.mostrarRegla.title'/>"></button>
		</div>
		<div class="btn-group" role="group" style="width: 5%;">
			<button class="list-group-button-delete btn btn-danger"
				title="<spring:message code='comunes.boton.borrarRegla.title'/>" onclick="eliminarRegla(this)">
				<i class="material-icons list-group-button-delete-icon">delete_sweep</i>
			</button>
		</div>
	</div>
</script>

<!-- Plantilla elemento genérico -->
<script type="text/html" id="plantilla-elemento-generico">
	<div class='well-green'>
    	<p class="representacionRegla-titulo" data-content="nombreTipoElemento"></p>
    	<p class="representacionRegla-elem" data-content="nombreElemento"></p>
    </div>
</script>

<!-- Plantilla elemento OWL -->
<script type="text/html" id="plantilla-elemento-owl">
	<div class='well-lavender'>
    	<p class="representacionRegla-titulo" data-content="nombreTipoElemento"></p>
    	<p class="representacionRegla-elem" data-content="nombreElemento"></p>
		<p class="representacionRegla-dato" data-content="uriElemento"></p>
    </div>
</script>

<!-- Plantilla contenido -->
<script type="text/html" id="plantilla-contenido">
	<div data-id="datosRegla" class='container-fluid collapse'>
		<div class='row' style="margin-top: 10px;">
			<div data-id="datosElementosOrigenRegla" class='col-lg-6 col-elem-creacionGuiada'>
			</div>
	    	<div data-id="datosElementosDestinoRegla" class='col-lg-6 col-elem-creacionGuiada'>
			</div>
		</div>
	</div>
</script>


<%-- Bloque de reglas creadas --%>
<div class="container-fluid marco-representacion-reglas">
	<%-- Contenedor de desplegables de reglas --%>
	<div id="representacionReglasContenedor" class="panel-group">
		
	</div>
</div>

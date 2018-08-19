<%@include file="../commons/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>

<!-- JQuery, Bootstrap -->
<%@include file="../commons/imports.jsp" %>

<!-- CSS -->
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" >
<%-- <link href="${pageContext.request.contextPath}/css/nms_font.css" rel="stylesheet" > --%>

<!-- Bootstrap filestyle (input ficheros) -->
<script language="javascript" src="${pageContext.request.contextPath}/javascript/bootstrap-filestyle/bootstrap-filestyle.min.js"></script>

<!-- Funciones para la carga de esquemas -->
<script language="javascript" src="${pageContext.request.contextPath}/javascript/schemaUpload/schemaUpload.js"></script>

<script type="text/javascript">
	window.onload = function() {
		prepararTiposFicheroOrigen();
		prepararTiposFicherosDestino();
		prepararTiposFicherosSalida();
		
	    document.getElementById('uploadButtonSourceFile').addEventListener('click',
	    		function(event){
	    			var tipoFichero = $("#sourceSchemaType :selected").val();
	    			sourceFileUploading = true;
	    			uploadFile(event, tipoFichero, TipoFichero.SOURCE);
	    		}, false);
	    
	    document.getElementById('uploadButtonTargetFile').addEventListener('click', 
	    		function(event){
			    	var tipoFichero = $("#targetSchemaType :selected").val();
			    	targetFileUploading = true;
					uploadFile(event, tipoFichero, TipoFichero.TARGET);
				}, false);
	    
	    // Inicialmente no hay ficheros subidos ni subiéndose
	    sourceFileUploaded = false;
	    targetFileUploaded = false;
	    
	    sourceFileUploading = false;
	    targetFileUploading = false;
	    
		// Inicializa la ayuda interactiva
	    ayudaInteractiva.init();
	}
	
	/**
	 * Carga los tipos de ficheros admitidos como esquema de origen
	 * @returns
	 */
	function prepararTiposFicheroOrigen(){
		cargarDatosSelect("sourceSchemaType", '<spring:message code="data.cargar.ficheros.tipos.origen"/>');
	}
	
	/**
	 * Carga los tipos de ficheros admitidos como esquema de destino
	 * @param selectId
	 * @returns
	 */
	function prepararTiposFicherosDestino(){
		cargarDatosSelect("targetSchemaType", '<spring:message code="data.cargar.ficheros.tipos.destino"/>');
	}
	
	/**
	 * Carga los tipos de ficheros admitidos como salida
	 * @param selectId
	 * @returns
	 */
	function prepararTiposFicherosSalida(){
		cargarDatosSelect("outputFileType", '<spring:message code="data.cargar.ficheros.tipos.salida"/>');
	}

	/**
	 * Hace visibles la barra de progreso y el mensaje para la subida del fichero de esquema de origen
	 */
	function uploadSourceFileFadeIn(){
		$('#sourceFileProgressBar').fadeIn(500);
		$('#sourceFileOutput').fadeIn(750);
	}
	
	/**
	 * Hace visibles la barra de progreso y el mensaje para la subida del fichero de esquema de destino
	 */
	function uploadTargetFileFadeIn(){
		$('#targetFileProgressBar').fadeIn(500);
		$('#targetFileOutput').fadeIn(750);
	}
	
	/**
	 * Realiza de forma automatica la subida del fichero al seleccionarlo 
	 */
	function autouploadSourceFile(){
		$('#uploadButtonSourceFile').click();
	}
	
	/**
	 * Realiza de forma automatica la subida del fichero al seleccionarlo
	 */
	function autouploadTargetFile(){
		$('#uploadButtonTargetFile').click();
	}
	
	// Instancia de la ayuda interactiva
	var ayudaInteractiva = new Tour({
		smartPlacement: true,
		backdrop: true,
		backdropContainer: 'body',
		backdropPadding: 10,
		template: "<div class='popover tour' style='min-width:20%'>\
		    <div class='arrow'></div>\
		    <h3 class='popover-title'></h3>\
		    <div class='popover-content'></div>\
		    <div class='popover-navigation'>\
		    	<div class='btn-group'>\
		        	<button class='btn btn-default' data-role='prev'><spring:message code="comunes.boton.anterior" /></button>\
		        	<button class='btn btn-default' data-role='next'><spring:message code="comunes.boton.siguiente" /></button>\
		        </div>\
		        <button class='btn btn-default' data-role='end'><spring:message code="comunes.boton.finalizar" /></button>\
		    </div>\
		  </div>",
		steps: [
		{
		  backdrop: false,
		  placement: "bottom",
		  element: "#titulo",
		  title: "<spring:message code="cargar.ficheros.texto.ayuda.titulo1" />",
		  content: "<spring:message code="cargar.ficheros.texto.ayuda.desc1" />"
		},
		{
		  element: "#formularioFicheroEsquemaOrigen",
		  title: "<spring:message code="cargar.ficheros.texto.ayuda.titulo2" />",
		  content: "<spring:message code="cargar.ficheros.texto.ayuda.desc2" />"
		},
		{
		  placement: "left",
		  element: "#formularioFicheroEsquemaDestino",
		  title: "<spring:message code="cargar.ficheros.texto.ayuda.titulo3" />",
		  content: "<spring:message code="cargar.ficheros.texto.ayuda.desc3" />"
		},
		<%-- Los dos siguientes pasos son visibles solo bajo ciertas condiciones, así que solo deben ser visibles mientras se 
			 explica al usuario lo que son, después volverán a estar ocultos si todavía no deben verse --%>
		{
		  onShow: function (ayudaInteractiva) {mostrarIrAMapeo()},
		  onHidden: function (ayudaInteractiva) {ocultarIrAMapeo()},
		  element: "#formularioTipoFicheroSalida",
		  title: "<spring:message code="cargar.ficheros.texto.ayuda.titulo4" />",
		  content: "<spring:message code="cargar.ficheros.texto.ayuda.desc4" />"
		},
		{
		  onShow: function (ayudaInteractiva) {mostrarIrAMapeo()},
		  onHidden: function (ayudaInteractiva) {ocultarIrAMapeo()},
		  backdrop: false,
		  element: "#idIniciarMapeo",
		  title: "<spring:message code="cargar.ficheros.texto.ayuda.titulo5" />",
		  content: "<spring:message code="cargar.ficheros.texto.ayuda.desc5" />"
		}
	]});
	
</script>


<title><spring:message code="cargar.ficheros.titulo.pagina"/></title>

</head>
<body>

<i id="botonIniciarAyuda" title="Muestra la ayuda interactiva" onclick="iniciarAyuda();" class="material-icons boton-ayuda-solo">help</i>

<div id="main">
	<br>
	
	<div id="titulo" style='text-align:center;'>
		<spring:message code="cargar.ficheros.mensaje.principal" />
	</div>
	
	<br>
	
	<%-- Adorno --%>
	<div>
		<hr class="hr-10">
	</div>

	<!-- INI - Mensajes de error -->
	<jsp:include page="/WEB-INF/jsp/schemaUpload/errorMessages.jsp"/>
	<!-- FIN - Mensajes de error -->
	
	<!-- Bloque de errores -->
	<div class="nonVisible">
		<div id="divErrores" class="error-msg-block">
			<p id="pErrores" class="error-msg">
				<spring:message code="comunes.error.declaracion" />
			</p>
			<c:if test="${errors != null}">
				<c:forEach items="${errors}" var="error">
			        <p class="error-msg">
			            ${error}
			        </p>
			    </c:forEach>
		    </c:if>
		</div>
		
		<%-- Adorno --%>
		<div>
			<hr class="hr-10">
		</div>
	</div>
	
	<div class="container-fluid" style="font-size:1.2em;">
		<div class="row">
			<div class="col-lg-2"></div>
			
			<!-- INI - Formulario de carga de fichero de entrada -->
			<div class="col-lg-3">
				 <div id="formularioFicheroEsquemaOrigen">
				 	<p><spring:message code="cargar.ficheros.mensaje.seleccion.tipo.esquema.origen"/></p>
				 	
				 	<!-- Select con los tipos de fichero de esquema de origen -->
				 	<select class="form-control" id="sourceSchemaType"></select>
				 	
				 	<br>
				 	
				 	<!-- Input fichero -->
					<div class="text-center">
				        <form style="margin-bottom: 1px" onsubmit="event.preventDefault();">
				            <input type="file" id="sourceFile" class="filestyle" data-btnClass="btn-info" 
				            		data-text="<spring:message code="cargar.ficheros.boton.seleccionar.archivo"/>" 
				            		style="margin-bottom: 5px" onchange="autouploadSourceFile(event)"/>
				            <br/>
				            <input id="uploadButtonSourceFile" type="button" class="btn btn-primary" value="<spring:message code="cargar.ficheros.boton.cargar.archivo"/>"
				            	onclick="uploadSourceFileFadeIn()"/>
				        </form>
						<br>
					</div>
					<div class="text-center">
						<!-- Barra progreso carga fichero -->
				        <div id='sourceFileProgressBar' class="marco-respuesta"  style='height: 30px; border: 2px solid green; display: none'>
				            <div id='sourceFileBar' style='height: 100%; background: #33dd33; width: 0%'>
				            </div>
				        </div>
				        <output class="marco-simple" style="display:none; margin-top:10px;" id="sourceFileOutput"></output>
					</div>
				</div>
			</div>
			<!-- FIN - Formulario de carga de fichero de entrada -->
			
			<div class="col-lg-2 text-center">
				<br><br>
				<!-- <span class="glyphicon" style="font-size:80px; font-family: Arial, Helvetica, sans-serif;">&#xe093;</span> -->
				<i class="arrow right" style="padding:40px;" ></i>
			</div>
				
			<!-- INI - Formulario de carga de fichero de salida -->
			<div class="col-lg-3">
				<div id="formularioFicheroEsquemaDestino">
					<p><spring:message code="cargar.ficheros.mensaje.seleccion.tipo.esquema.destino"/></p>
					
					<!-- Select con los tipos de fichero de esquema de destino -->
					<select class="form-control" id="targetSchemaType"></select>
					
				 	<br>
					
					<!-- Input fichero -->
					<div class="text-center">
				        <form style="margin-bottom: 1px" onsubmit="event.preventDefault();">
				            <input type="file" id="targetFile" class="filestyle" data-btnClass="btn-info" 
				            		data-text="<spring:message code="cargar.ficheros.boton.seleccionar.archivo"/>"
				            		style="margin-bottom: 5px" onchange="autouploadTargetFile(event)"/>
				            <br/>
				            <input id="uploadButtonTargetFile" type="button" class="btn btn-primary" value="<spring:message code="cargar.ficheros.boton.cargar.archivo"/>"
				            	onclick="uploadTargetFileFadeIn()" style="margin-top: 5px"/>
				        </form>
				        <br>
					</div>
					<div class="text-center">
						<!-- Barra progreso carga fichero -->
				        <div id='targetFileProgressBar' class="marco-respuesta"  style='height: 30px; border: 2px solid green; display: none'>
				            <div id='targetFileBar' style='height: 100%; background: #33dd33; width: 0%'>
				            </div>
				        </div>
				        <output class="marco-simple" style="display:none; margin-top:10px;" id="targetFileOutput"></output>
					</div>
				</div>
			</div>
			<!-- FIN - Formulario de carga de fichero de salida -->
			
			<div class="col-lg-2 "></div>
		</div>
		
		<br>
		<%-- Adorno --%>
		<div>
			<hr class="hr-10">
		</div>
		
		<br>
		
		<%-- INI - Zona Ir a mapeo --%>
		<form:form method="post" action="mappingPrincipal.html" modelAttribute="ficherosEsquemas">
			<div id="divIniciarMapeo" style='text-align: center; display: none;' >
			
				<div class="row">
					<div class="col-lg-5 "></div>
					
					<%-- Selector de fichero de salida --%>
					<div id="formularioTipoFicheroSalida" class="col-lg-2">
						<p><spring:message code="cargar.ficheros.mensaje.seleccion.tipo.fichero.salida"/></p>
						<!-- Select con los tipos de fichero de esquema de origen -->
						<form:select class="form-control" id="outputFileType" path="outputFileType"/>
					</div>
					
					<div class="col-lg-5 "></div>
				</div>
				
				<div class="row">
					<div class="col-lg-4 "></div>
					
					<div class="col-lg-4 ">
						<hr class=" mx-5">
					</div>
						
					<div class="col-lg-4 "></div>
				</div>
				
				<div class="row">
					<div class="col-lg-5 "></div>
					
					<%-- Botón Ir a mapeo --%>
					<div id="idIniciarMapeo" class="col-lg-2 ">					
						<input id="botonIniciarMapeo" type="submit" class="btn btn-primary" value="<spring:message code="cargar.ficheros.boton.ir.mapear"/>"/>
					</div>
					
					<div class="col-lg-5 "></div>
			</div>
		</div> 
		</form:form>
		<%-- FIN - Zona Ir a mapeo --%>
		
	</div> <%-- container --%>
</div>

</body>
</html>
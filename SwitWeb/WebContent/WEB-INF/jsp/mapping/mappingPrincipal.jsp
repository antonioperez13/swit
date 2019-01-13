<%@include file="../commons/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="mapeo.titulo.pagina"/></title>
	
	<!-- JQuery, Bootstrap -->
	<%@include file="../commons/imports.jsp" %>
	
	<!-- CSS -->
	<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" >
	<link href="${pageContext.request.contextPath}/css/sidebar.css" rel="stylesheet" >
	<link href="${pageContext.request.contextPath}/css/modal-error.css" rel="stylesheet" >
	
	<!-- Bootstrap filestyle (input ficheros) -->
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/bootstrap-filestyle/bootstrap-filestyle.min.js"></script>
	
	<!-- JSTree -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.6/jstree.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.6/themes/default/style.min.css" />
	<link href="${pageContext.request.contextPath}/javascript/jstree/themes/proton/style.min.css" rel="stylesheet" >
	
	<!-- Jquery-template (plantillas html) -->
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/jquery-template/jquery.loadTemplate.min.js"></script>
	
	<!-- Funciones para mapping -->
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/Elemento.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/Regla.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/mappingPrincipal.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/Filtro_reglas_creadas.js"></script>

	
	<!-- INI - Pasos creaciones guiadas -->
	<jsp:include page="/WEB-INF/jsp/mapping/creacionGuiada.jsp"/>
	<!-- FIN - Pasos creaciones guiadas -->

	<script type="text/javascript">
		$(function() {
			<%-- Mostrar colores distintos para las l�neas pares e impares --%>
			$.jstree.defaults.core.themes.stripes = true;
			<%-- Mostrar icono delante de cada elemento de la lista --%>
			$.jstree.defaults.core.themes.icons = true;
			
			/* *************** */
			/* Lista izquierda */
			/* *************** */
			$('#sourceSchema').on('changed.jstree', function (e, data) {
			    var i, j, r = [];
			    for(i = 0, j = data.selected.length; i < j; i++) {
			      r.push(data.instance.get_node(data.selected[i]).text);
			    }
			    $('#elem_izq_selec').html(r.join(', '));
			  })
			  .jstree({
				"core" : {
				    "themes" : {
						"variant" : "default",
						'name': 'proton',
						'responsive': true,
						'stripes': false,
						'icons': false
				    },
				    "multiple" : false
				},
				"plugins" : [ "wholerow", "search"],
				"search": {
		            "case_insensitive": true,
		            "show_only_matches" : true
		        }
			});
			
			/* B�squeda en la lista izquierda */
			var to_search_source = false;
			$('#searchSourceSchema').keyup(function () {
				if(to_search_source) { clearTimeout(to_search_source); }
				to_search_source = setTimeout(function () {
				  var v = $('#searchSourceSchema').val();
				  $('#sourceSchema').jstree(true).search(v);
				}, 250);
			});
			
			/* ************* */
			/* Lista derecha */
			/* ************* */
			$('#targetSchema').on('changed.jstree', function (e, data) {
			    var i, j, r = [];
			    for(i = 0, j = data.selected.length; i < j; i++) {
			      r.push(data.instance.get_node(data.selected[i]).text);
			    }
			    $('#elem_der_selec').html(r.join(', '));
			  })
			  .jstree({
					"core" : {
					    "themes" : {
							"variant" : "default",
							'name': 'proton',
							'responsive': true,
							'stripes': false
					    },
					    "multiple" : false
					},
					"plugins" : [ "wholerow", "search"],
					"search": {
					    "case_insensitive": true,
					    "show_only_matches" : true
					}
			  });
			
			/* B�squeda en la lista derecha */
			var to_search_target = false;
			$('#searchTargetSchema').keyup(function () {
				if(to_search_target) { clearTimeout(to_search_target); }
				to_search_target = setTimeout(function () {
				  var v = $('#searchTargetSchema').val();
				  $('#targetSchema').jstree(true).search(v);
				}, 250);
			});
			
			/* Limpieza de la b�squeda en el esquema origen */
			$("#searchSourceClearButton").click(function () {
				$("#sourceSchema").jstree("clear_search");
				$("#searchSourceSchema").val("");
			});
			
			/* Limpieza de la b�squeda en el esquema destino */
			$("#searchTargetClearButton").click(function () {
				$("#targetSchema").jstree("clear_search");
				$("#searchTargetSchema").val("");
			});
		});
		
		/* Abre el panel lateral izquierdo */
		function openNav() {
		    document.getElementById("mySidenav").style.width = "102%";
		    $("#openSidebarButton").hide(100);
		    setTimeout(function () {
		    	$("#sidebarMenu").fadeIn(500);
		    }, 250);
		    
		}
	
		/* Cierra el panel lateral izquierdo */
		function closeNav() {
		    $("#sidebarMenu").hide();
			document.getElementById("mySidenav").style.width = "0";
			setTimeout(function () {
				$("#openSidebarButton").show(200);
		    }, 250);
		}
		
		// Funciones para las creaciones guiadas de reglas
		function comenzarCreacionGuiadaReglaClase(){
			ayudaInteractiva.end();
			creacionGuiadaReglaClase.restart();
		}
		
		function comenzarCreacionGuiadaReglaPropiedad(){
			ayudaInteractiva.end();
			creacionGuiadaReglaPropiedad.restart();
		}
		
		function comenzarCreacionGuiadaReglaRelacion(){
			ayudaInteractiva.end();
			creacionGuiadaReglaRelacion.restart();
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
			        	<button class='btn btn-default' data-role='prev'><spring:message code='comunes.boton.anterior' /></button>\
			        	<button class='btn btn-default' data-role='next'><spring:message code='comunes.boton.siguiente' /></button>\
			        </div>\
			        <button class='btn btn-default' data-role='end'><spring:message code='comunes.boton.finalizar' /></button>\
			    </div>\
			  </div>",
			steps: [
			{
			  backdrop: false,
			  placement: "bottom",
			  element: "#titulo",
			  title: '<spring:message code="mapeo.texto.ayuda.titulo1" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc1" />'
			},
			{
			  placement: "bottom",
			  element: "#botonesCreacionGuiadaReglas",
			  title: '<spring:message code="mapeo.texto.ayuda.titulo2" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc2" />'
			},
			{
			  placement: "top",
			  element: "#esquemasDatosDiv",
			  title: '<spring:message code="mapeo.texto.ayuda.titulo3" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc3" />'
			},
			{
			  element: "#searchSourceSchemaDiv",
			  title: '<spring:message code="mapeo.texto.ayuda.titulo4" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc4" />'
			},
			{
			  element: "#reglasCreadasDiv",
			  title: '<spring:message code="mapeo.texto.ayuda.titulo5" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc5" />'
			},
			{
			  placement: "left",
			  element: "#representacionReglasDiv",
			  title: '<spring:message code="mapeo.texto.ayuda.titulo6" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc6" />'
			},
			{
			  orphan: true,
			  onShow: function (ayudaInteractiva) {scrollToTop(); openNav();},
			  onResume: function (ayudaInteractiva) {closeNav();},
			  backdrop: false,
			  title: '<spring:message code="mapeo.texto.ayuda.titulo7" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc7" />'
			},
			{
			  element: "#idRegistroBackup",
			  onShow: function (ayudaInteractiva) {scrollToTop(); openNav();},
			  onHidden: function (ayudaInteractiva) {closeNav();},
			  onResume: function (ayudaInteractiva) {closeNav();},
			  title: '<spring:message code="mapeo.texto.ayuda.titulo8" />',
			  content: '<spring:message code="mapeo.texto.ayuda.desc8" />'
			},
		]});
		
		/* Carga guiada de un fichero de mappings */
		var cargaFicherosMappings = new Tour({
			smartPlacement: true,
			backdrop: true,
			backdropContainer: 'body',
			backdropPadding: 10,
			template: "<div class='popover tour' style='min-width:20%'>\
			    <div class='arrow'></div>\
			    <h3 class='popover-title'></h3>\
			    <div class='popover-content'></div>\
			    <form id='mappingsFileForm' style='margin-bottom: 1px; margin-left: 14px;' onsubmit='event.preventDefault();'>\
			        <input type='file' id='mappingsFileInput' class='filestyle' data-btnClass='btn-info'\
			        		data-text='<spring:message code='cargar.ficheros.boton.seleccionar.archivo'/>'\
			        		style='margin-bottom: 5px'/>\
			        <br/>\
			        <input id='mappingsFileUploadButton' type='button' class='btn btn-success' value='<spring:message code='comunes.boton.cargar'/>'\
			        		onclick='loadMappingsFile()'/>\
			    </form>\
			    <div class='popover-navigation'>\
			    	<button id='cargaFicherosMappings-botonAceptar' disabled=true class='btn btn-success' data-role='end'><spring:message code='comunes.boton.aceptar' /></button>\
			        <button id='cargaFicherosMappings-botonCancelar' class='btn btn-danger' data-role='end'><spring:message code='comunes.boton.cancelar' /></button>\
			    </div>\
			  </div>",
			steps: [
			{
			  orphan: true,
			  title: '<spring:message code="mapeo.cargar.fichero.local.titulo" />',
			  content: '<spring:message code="mapeo.cargar.fichero.local.desc" />'
			},
			{
			  orphan: true,
			  onShown: function (cargaFicherosMappings) {loadMappingsFileSuccess();},
			  title: '<spring:message code="mapeo.cargar.fichero.local.titulo" />',
			  content: '<spring:message code="mapeo.cargar.fichero.local.carga.correcta" />'
			},
			{
			  orphan: true,
			  onShown: function (cargaFicherosMappings) {loadMappingsFileError();},
			  title: '<spring:message code="mapeo.cargar.fichero.local.titulo" />',
			  content: '<spring:message code="mapeo.cargar.fichero.local.carga.incorrecta" />'
			},
		]});
		
		// Utilidades
		function scrollGoCreacionReglas() {
			$('html, body').animate({
			    scrollTop: $("#adorno1").offset().top
			}, 700);
		}
		
		function scrollGoRepresentacionReglas() {
			$('html, body').animate({
			    scrollTop: $("#representacionReglasContenedor").offset().top
			}, 700);
		}
		
		function scrollToTop() {
			window.scroll({top: 0, left: 0, behavior: 'smooth' });
		}
		
		
		window.onload = function(){
			// Oculta el selector de idioma
			document.getElementById("selectorIdiomas").style.display = "none";
			
			// Inicializacion de la creaci�n guiada de una regla de clase
			creacionGuiadaReglaClase.init();
			
			creacionGuiadaReglaPropiedad.init();
			
			creacionGuiadaReglaRelacion.init();
			
			// Contenedor de mensajes de error y advertencia
		    $('.hover_bkgr_fricc').click(function(){
		        $('.hover_bkgr_fricc').hide();
		    });
		    $('.hover_bkgr_fricc_advert').click(function(){
		        $('.hover_bkgr_fricc_advert').hide();
		    });
		    
		    targetSchemaTreeData = $('#targetSchema').jstree(true).get_json('#', {flat:true});
		}
	</script>
</head>
<body>
	<!-- INI - Cabecera -->
	<jsp:include page="/WEB-INF/jsp/commons/header-help.jsp"/>
	<!-- FIN - Cabecera -->

	<!-- INI - Mensajes de error -->
	<jsp:include page="/WEB-INF/jsp/mapping/errorMessages.jsp"/>
	<!-- FIN - Mensajes de error -->
	
	<!-- Modal para mostrar los errores que ocurran -->
	<div class="hover_bkgr_fricc">
	    <span class="helper"></span>
	    <div class="panel panel-danger">
		    <div class="panel-heading"><spring:message code="comunes.titulo.error"/></div>
		    <div class="panel-body" id="modalErrorDescripcion"></div>
	    </div>
	</div>
	
	<!-- Modal para mostrar advertencias -->
	<div class="hover_bkgr_fricc_advert">
	    <span class="helper"></span>
		<div class="panel panel-warning">
		    <div class="panel-heading"><spring:message code="comunes.titulo.advertencia"/></div>
		    <div class="panel-body" id="modalAdvertenciaDescripcion"></div>
	    </div>
	</div>
	
	<!-- Men� lateral izquierdo -->
	<div id="menuLateral" class=" container">
		<div id="mySidenav" class="row sidenav">
			<div id="menuLateral" class="col-lg-2 navbar-menu">
				<div id="sidebarMenu" class="sidebar-menu">
					<br>
					<br>
					<a href="execConfiguration.html" class="a-button"><spring:message code="mapeo.menuIzq.ir.configurador.ejecucion.texto"/></a>
					<hr>
					<a href="javascript:void(0)" id="botonCerrarMenuLateral" class="closebtn" onclick="closeNav()">&times;</a>
					<!-- Cargar reglas desde equipo -->
					<a class="a-button" onclick="cargaFicherosMappings.restart();"><spring:message code="mapeo.menuIzq.cargar.fichero.mappings.local.texto"/></a>
					<!-- Guardar reglas en equipo -->
					<a onclick="return downloadMappingsFile();" class="a-button"><spring:message code="mapeo.menuIzq.guardar.fichero.mappings.local.texto"/></a>
					<div style="display: none;" class="alert alert-danger" id="alertSinReglasPorGuardarLocalError">
					    <spring:message code="mapeo.guardar.fichero.mappings.no.reglas"/>
					</div>
					<hr>
					<!-- Cargar reglas desde servidor -->
					<a class="a-button" onclick="retrieveBackupMappingsFile();"><spring:message code="mapeo.menuIzq.cargar.fichero.mappings.backup.texto"/></a>
					<div style="display: none;" class="alert alert-danger" id="cargarFicheroReglasBackupAlert">
					    <spring:message code="mapeo.cargar.fichero.mappings.no.idRegistroSesion"/>
					</div>
					<!-- Guardar reglas en servidor -->
					<a class="a-button" onclick="saveBackupMappingsFile();"><spring:message code="mapeo.menuIzq.guardar.fichero.mappings.backup.texto"/></a>
					<div style="display: none;" class="alert alert-danger" id="alertSinReglasPorGuardarBackupError">
					    <spring:message code="mapeo.guardar.fichero.mappings.no.reglas"/>
					</div>
					<hr>
					<hr>
					<!-- Borrar todas las reglas -->
					<a class="a-button" onclick="eliminarTodasReglas();"><spring:message code="mapeo.menuIzq.borrar.todas.reglas"/></a>
					<hr>
					<!-- Identificador de sesi�n -->
					<a class="a-button" onclick="copyIdSesionToClipboard();" style="vertical-align: bottom;" title="<spring:message code="mapeo.menuIzq.identificador.sesion.title"/>"><spring:message code="mapeo.menuIzq.identificador.sesion.texto"/></a>
					<input id="idRegistroBackup" type="text" pattern="[A-Za-z0-9]" placeholder="<spring:message code="mapeo.menuIzq.identificador.sesion.input.placeholder"/>"/>
				</div>
			</div>
			<div class="col-lg-10 dimmer-obscure" onclick="closeNav()"></div>
		</div>
	</div>
	
	<!-- Bot�n men� lateral izquierdo -->
	<div class="botones-laterales">
		<div>
			<i id="openSidebarButton" onclick="openNav()" class="material-icons navbar-button">menu</i>
		</div>
	</div>
	
<div id="main" class="wrapper-obscure">
	<%-- ------------------------- HEADER ------------------------- --%>
	<div id="titulo" class="item-header" style="margin:auto;text-align:center;">
		<h1>
			<spring:message code="mapeo.mensaje.subtitulo"/>
		</h1>
	</div>
	
	<%-- Adorno --%>
	<div id="adorno1">
		<hr class="hr-1">
	</div>
	
	<%-- Botones de creaci�n de reglas --%>
	<div id="botonesCreacionGuiadaReglas" class="row" style="text-align: center; margin-bottom: 10px;">
		<div class="btn-group" role="group" >
			<input class="btn no-click" type="button" value="<spring:message code="mapeo.botones.creacionGuiada.titulo"/>"/>
			<input class="btn btn-success" type="button" value="<spring:message code="mapeo.creacionGuiada.boton.crear.regla.clase"/>"
				<%-- onclick="mappingClassRule()"/> --%>
				onclick="comenzarCreacionGuiadaReglaClase()"/>
			<input class="btn btn-violet" type="button" value="<spring:message code="mapeo.creacionGuiada.boton.crear.regla.propiedad"/>"
				<%-- onclick="mappingPropertyRule()"/> --%>
				onclick="comenzarCreacionGuiadaReglaPropiedad()"/>
			<input class="btn btn-warning" type="button" value="<spring:message code="mapeo.creacionGuiada.boton.crear.regla.relacion"/>" 
				<%-- onclick="mappingRelationRule()"/> --%>
				onclick="comenzarCreacionGuiadaReglaRelacion()"/>
		</div>
		
		<%-- Ir al bloque de Reglas creadas --%>
		<i onclick="scrollGoRepresentacionReglas()" id="goRepresentacionReglasButton" 
			title="<spring:message code="comunes.boton.ir.abajo.title"/>" 
			class="material-icons">arrow_downward</i>
	</div>
	
	
		
	<%-- ------------------------- INI GRID ------------------------- --%>
	<div class="container-fluid " style="font-size:1.2em;">
		<div id="esquemasDatosDiv" class="row">
			<%-- ------------------------- ITEM LEFT ------------------------- --%>
			<div id="sourceSchemaDiv" class="col-lg-6 ">
				
				<%-- Barra de b�squeda del esquema origen --%>	
				<div id="searchSourceSchemaDiv" class="input-bar">
					<div class="input-bar-item width100">
						<form>
							<div class="input-group">
								<%-- T�tulo de la barra --%>	
								<span class="input-group-btn">
									<button class="btn btn-info no-click">
										<spring:message code="mapeo.mensaje.busqueda.titulo"/>
									</button>
								</span>
								<%-- Entrada de texto --%>
								<input type="text" id="searchSourceSchema" value="" class="form-control width100" style="z-index: 0;">
								<%-- Bot�n para limpiar la b�squeda --%>
								<span class="input-group-btn">
									<button type="button" id="searchSourceClearButton" value="Limpiar b�squeda" class="btn btn-warning" style="z-index: 0;">
										<spring:message code="mapeo.mensaje.busqueda.boton.limpiar"/>
									</button>
								</span>
							</div>
						</form>
					</div>
				</div>
				
				<%-- �rbol de elementos del esquema origen --%>
				<div id="sourceSchema" class="w-100 marco-arbol">
					${sourceTree}
				</div>
			</div>
			
			<%-- ------------------------- ITEM RIGHT ------------------------- --%>
			<div id="targetSchemaDiv" class="col-lg-6">
				<%-- Barra de b�squeda del esquema destino --%>	
				<div id="searchTargetSchemaDiv" class="input-bar">
					<div class="input-bar-item width100">
						<form>
							<div class="input-group">
								<%-- T�tulo de la barra --%>	
								<span class="input-group-btn">
									<button class="btn btn-info no-click">
										<spring:message code="mapeo.mensaje.busqueda.titulo"/>
									</button>
								</span>
								<%-- Entrada de texto --%>
								<input type="text" id="searchTargetSchema" value="" class="form-control width100" style="z-index: 0;">
								<%-- Bot�n para limpiar la b�squeda --%>
								<span class="input-group-btn">
									<button type="button" id="searchTargetClearButton" value="Limpiar b�squeda" class="btn btn-warning" style="z-index: 0;">
										<spring:message code="mapeo.mensaje.busqueda.boton.limpiar"/>
									</button>
								</span>
							</div>
						</form>
					</div>
				</div>
				
				<%-- �rbol de elementos del esquema destino --%>
				<div id="targetSchema" class="w-100 marco-arbol">
					${targetTree}
				</div>
			</div>
		</div> <%-- /row --%>
		
		<%-- Adorno --%>
		<div>
			<hr class="hr-1">
		</div>
		
		<%-- ------------------------- REGLAS CREADAS ------------------------- --%>
		<div id="reglasCreadasDiv">
			<div class="row" style="margin-top: 20px;">
				<div class="col-lg-12">
					<h3 style="float: left; margin-top: 5px;"><spring:message code="mapeo.representacionRegla.titulo.bloque"/></h3>
					<hr class="width100">
				</div>
			</div>
			<div class="row">
				<div class="col-lg-1">
				</div>
				
				
				<%-- Filtros --%>
				<div class="col-lg-11">
					<h3><spring:message code="mapeo.filtros.bloque.titulo"/></h3>
					<hr>
					<div class="row" style="border-style: groove; border-width: 0px 0px 1px 1px; border-radius: 12px; width:98%;">
						<div class="col-lg-1" style="width: 0%;">
						</div>
						
						<%-- Filtrado por elementos --%>
						<div class="col-lg-6">
							<h3><spring:message code="mapeo.filtros.filtro.elementos.titulo"/></h3>
							<hr>
							<div class="row well-green" style="width: 100%;">
								<div class="col-lg-5">
									<label><spring:message code="mapeo.filtros.filtro.elementos.origen.titulo"/></label>
								</div>
								<div class="col-lg-7">
									<input id="idRepresentacionReglasEsquemaOrigen" 
										type="text" style="width: 95%;"
										placeholder="<spring:message code="mapeo.filtros.filtro.elementos.placeholder"/>"/>	
								</div>
							</div>
							
							<div class="row well-lavender" style="width: 100%;">
								<div class="col-lg-5">
									<label><spring:message code="mapeo.filtros.filtro.elementos.destino.titulo"/></label>
								</div>
								<div class="col-lg-7">
									<input id="idRepresentacionReglasEsquemaDestino" 
										type="text" style="width: 95%;"
										placeholder="<spring:message code="mapeo.filtros.filtro.elementos.placeholder"/>"/>	
								</div>
							</div>
						</div>
						
						<%-- Filtrado por tipo de regla --%>
						<div class="col-lg-3">
							<h3><spring:message code="mapeo.filtros.filtro.tipo.regla.titulo"/></h3>
							<hr>
							<div style="padding-left: 20px;">
								<div class="checkbox">
							      <label class="btn btn-success"><input id="checkboxFiltroReglaClase" type="checkbox" value="" checked><spring:message code="mapeo.filtros.filtro.tipo.regla.clase"/></label>
							    </div>
							    <div class="checkbox">
							      <label class="btn btn-violet"><input id="checkboxFiltroReglaPropiedad" type="checkbox" value="" checked><spring:message code="mapeo.filtros.filtro.tipo.regla.propiedad"/></label>
							    </div>
							    <div class="checkbox">
							      <label class="btn btn-warning"><input id="checkboxFiltroReglaRelacion" type="checkbox" value="" checked><spring:message code="mapeo.filtros.filtro.tipo.regla.relacion"/></label>
							    </div>
							</div>
						</div>
						
						<%-- Botones para aplicar/limpiar filtros --%>
						<div class="col-lg-2" style="text-align: center; border-style: solid; border-width: 0px 0px 0px 1px;">
							<div class="row">
								<br>
								<br>
							</div>
							<div class="row">
								<input id="botonAplicarFiltrosReglasCreadas" 
									class="btn btn-info" type="button" 
									value="<spring:message code="mapeo.filtros.boton.aplicar"/>"
									onclick="filtrarReglasCreadas($('#idRepresentacionReglasEsquemaOrigen').val().trim(), $('#idRepresentacionReglasEsquemaDestino').val().trim());"/>
								<input id="botonLimpiarFiltrosReglasCreadas" 
									class="btn btn-warning" type="button" 
									value="<spring:message code="mapeo.filtros.boton.limpiar"/>"
									onclick="limpiarFiltroReglasCreadas();"/>
								<br>
								<br>
								<br>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-lg-1" style="text-align: left;">
					<%-- Botones contraer/expandir reglas --%>
					<h3 style="text-align: center;"><spring:message code="mapeo.representacionRegla.utilidades.titulo"/></h3>
					<hr>
					<div class="btn-group-vertical">
						<button type="button" class="btn btn-primary"
							onclick="contraerTodasReglas()">
							<spring:message code="comunes.boton.contraer.todas" />
						</button>
						<button type="button" class="btn btn-primary"
							onclick="expandirTodasReglas()">
							<spring:message code="comunes.boton.expandir.todas" />
						</button>
					</div>
				</div>
				
				<%-- Bloque de reglas creadas --%>
				<h3><spring:message code="mapeo.representacionRegla.subtitulo.bloque"/></h3>
				<hr>
				<div id="representacionReglasDiv" class="col-lg-11">
					<%@include file="representacionMapeos.jsp" %>
				</div>
				</div>
		</div> <%-- ------------------------- FIN Reglas creadas ------------------------- --%>
	</div>
	<%-- ------------------------- FIN GRID ------------------------- --%>
		
</div>

<!-- INI - Pie de p�gina -->
<jsp:include page="/WEB-INF/jsp/commons/footer.jsp"/>
<!-- FIN - Pie de p�gina -->

</body>
</html>
<%@include file="../commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="mapeo.titulo.pagina"/></title>
	
	<!-- JQuery, Bootstrap -->
	<%@include file="../commons/imports.jsp" %>
	
	<!-- CSS -->
	<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" >
	<%-- <link href="${pageContext.request.contextPath}/css/nms_font.css" rel="stylesheet" > --%>
	<link href="${pageContext.request.contextPath}/css/sidebar.css" rel="stylesheet" >
	
	<!-- Bootstrap filestyle (input ficheros) -->
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/bootstrap-filestyle/bootstrap-filestyle.min.js"></script>
	
	<!-- JSTree -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.5/jstree.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.5/themes/default/style.min.css" />
	<!-- <link rel="stylesheet" href="http://www.orangehilldev.com/jstree-bootstrap-theme/demo/assets/dist/themes/proton/style.css" /> -->
	<link href="${pageContext.request.contextPath}/javascript/jstree/themes/proton/style.min.css" rel="stylesheet" >
	
	<!-- Funciones para mapping -->
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/Elemento.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/Regla.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/javascript/mapping/mappingPrincipal.js"></script>

	
	<!-- INI - Pasos creaciones guiadas -->
	<jsp:include page="/WEB-INF/jsp/mapping/creacionGuiada.jsp"/>
	<!-- FIN - Pasos creaciones guiadas -->

	<script type="text/javascript">
	
		
		$(function() {
			$('#jstree').jstree({
				"plugins" : [ "wholerow" ]
			});
		});
		
		$(function() {
			<%-- Mostrar colores distintos para las líneas pares e impares --%>
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
			
			/* Búsqueda en la lista izquierda */
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
			
			/* Búsqueda en la lista derecha */
			var to_search_target = false;
			$('#searchTargetSchema').keyup(function () {
				if(to_search_target) { clearTimeout(to_search_target); }
				to_search_target = setTimeout(function () {
				  var v = $('#searchTargetSchema').val();
				  $('#targetSchema').jstree(true).search(v);
				}, 250);
			});
			
			/* Limpieza de la búsqueda en el esquema origen */
			$("#searchSourceClearButton").click(function () {
				$("#sourceSchema").jstree("clear_search");
				$("#searchSourceSchema").val("");
			});
			
			/* Limpieza de la búsqueda en el esquema destino */
			$("#searchTargetClearButton").click(function () {
				$("#targetSchema").jstree("clear_search");
				$("#searchTargetSchema").val("");
			});
		});
		
		/* Set the width of the side navigation to 250px and the left margin of the page content to 250px and add a black background color to body */
		function openNav() {
		    document.getElementById("mySidenav").style.width = "102%";
		    $("#openSidebarButton").hide(100);
		    setTimeout(function () {
		    	$("#sidebarMenu").fadeIn(500);
		    }, 250);
		    
		}
	
		/* Set the width of the side navigation to 0 and the left margin of the page content to 0, and the background color of body to white */
		function closeNav() {
		    $("#sidebarMenu").hide();
			document.getElementById("mySidenav").style.width = "0";
			setTimeout(function () {
				$("#openSidebarButton").show(200);
		    }, 250);
		}
		
		
		window.onload = function(){
			// Inicializacion de la creación guiada de una regla de clase
			creacionGuiadaReglaClase.init();
			
			creacionGuiadaReglaPropiedad.init();
			
			creacionGuiadaReglaRelacion.init();
		}
		
		
		function comenzarCreacionGuiadaReglaClase(){
			creacionGuiadaReglaClase.restart();
		}
		
		function comenzarCreacionGuiadaReglaPropiedad(){
			creacionGuiadaReglaPropiedad.restart();
		}
		
		function comenzarCreacionGuiadaReglaRelacion(){
			creacionGuiadaReglaRelacion.restart();
		}
		
	</script>

</head>
<body>

	<!-- INI - Mensajes de error -->
	<jsp:include page="/WEB-INF/jsp/mapping/errorMessages.jsp"/>
	<!-- FIN - Mensajes de error -->
	
	<!-- Menú lateral izquierdo -->
	<div class=" container">
		<div id="mySidenav" class="row sidenav">
			<div class="col-lg-2 navbar-menu">
				<div id="sidebarMenu" class="sidebar-menu">
					<br>
					<br>				  
					<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
					<a href="#">Cargar fichero de mapeo</a>
					<a href="#">Guardar fichero de mapeo</a>
					<a href="#">Borrar todas las reglas</a>
					<a href="#">Información</a>
				</div>
			</div>
			<div class="col-lg-10 dimmer-obscure" onclick="closeNav()"></div>
		</div>
	</div>
	
	<!-- Botón menú lateral izquierdo -->
	<i id="openSidebarButton" onclick="openNav()" class="material-icons navbar-button" style="font-size:24px;color:white">menu</i>
	
<div id="main" class="wrapper-obscure">
	<%-- ------------------------- HEADER ------------------------- --%>
	<div class="item-header" style="margin:auto;text-align:center;">
		<h1>
			<spring:message code="mapeo.mensaje.subtitulo"/>
		</h1>
	</div>
	
	<div id="dummy"></div>
	
	<%-- Adorno --%>
	<div id="adorno1">
		<hr class="hr-1">
	</div>
	
	<div id="botonesCreacionGuiadaReglas" class="row" style="text-align: center; margin-bottom: 10px;">
		<div class="btn-group" role="group" >
			<input class="btn no-click" type="button" value="<spring:message code="mapeo.botones.creacionGuiada.titulo"/>"/>
			<input class="btn btn-success" type="button" value="Regla de clase"
				<%-- onclick="mappingClassRule()"/> --%>
				onclick="comenzarCreacionGuiadaReglaClase()"/>
			<input class="btn btn-violet" type="button" value="Regla de propiedad"
				<%-- onclick="mappingPropertyRule()"/> --%>
				onclick="comenzarCreacionGuiadaReglaPropiedad()"/>
			<input class="btn btn-warning" type="button" value="Regla de relación" 
				<%-- onclick="mappingRelationRule()"/> --%>
				onclick="comenzarCreacionGuiadaReglaRelacion()"/>
		</div>
	</div>
		
	<%-- ------------------------- INI GRID ------------------------- --%>
	<div class="container-fluid " style="font-size:1.2em;">
		<div class="row">
			<%-- ------------------------- ITEM LEFT ------------------------- --%>
			<div id="sourceSchemaDiv" class="col-lg-6 ">
				
				<%-- Barra de búsqueda del esquema origen --%>	
				<div id="searchSourceSchemaDiv" class="input-bar">
					<div class="input-bar-item width100">
						<form>
							<div class="input-group">
								<%-- Título de la barra --%>	
								<span class="input-group-btn">
									<button class="btn btn-info no-click">Búsqueda: </button>
								</span>
								<%-- Entrada de texto --%>
								<input type="text" id="searchSourceSchema" value="" class="form-control width100" style="z-index: 0;">
								<%-- Botón para limpiar la búsqueda --%>
								<span class="input-group-btn">
									<button type="button" id="searchSourceClearButton" value="Limpiar búsqueda" class="btn btn-warning" style="z-index: 0;">Limpiar búsqueda</button>
								</span>
							</div>
						</form>
					</div>
				</div>
				
				<%-- Árbol de elementos del esquema origen --%>
				<div id="sourceSchema" class="w-100 marco-arbol">
					${sourceTree}
				</div>
			</div>
			
			<%-- ------------------------- ITEM CENTER ------------------------- --%>
			<!-- <div id="botones_centrales" class="col-lg-2 text-center" style="display: none;">
				<br>
				
				 <ul style="list-style-type: none;">
					<li>
						<input class="boton-central btn btn-info centrar-elem" type="button" value="Regla de clase"
							onclick="mappingClassRule()"/>
							onclick="comenzarCreacionGuiadaReglaClase()"/>
					</li>
					<li>
						<input class="boton-central btn btn-info centrar-elem" type="button" value="Regla de propiedad"
							onclick="mappingPropertyRule()"/>
							onclick="comenzarCreacionGuiadaReglaPropiedad()"/>
					</li>
					<li>
						<input class="boton-central btn btn-info centrar-elem" type="button" value="Regla de relación" 
							onclick="mappingRelationRule()"/>
							onclick="comenzarCreacionGuiadaReglaRelacion()"/>
					</li>
				</ul>
				
				<hr>
				
				<ul style="list-style-type: none;">
					<li>
						<input class="boton-central btn btn-primary" type="button" value="domainNodeSource"
							onclick="selectDomainNodeSource()"/>
					</li>
					<li>
						<input class="boton-central btn btn-primary" type="button" value="rangeNodeSource"
							onclick="selectRangeNodeSource()"/>
					</li>
					<li>
						<input class="boton-central btn btn-primary" type="button" value="propertyValueSource"
							onclick="selectPropertySource()"/>
					</li>
				</ul>
				
				<hr>
				
				<ul style="list-style-type: none;">	
					<li>
						<input class="boton-central btn btn-secondary" type="button" value="domainClassTarget"
							onclick="selectDomainClassTarget()"/>
					</li>
					
					<li>
						<input class="boton-central btn btn-secondary" type="button" value="rangeClassTargetId"
							onclick="selectRangeClassTarget()"/>
					</li>
					<li>
						<input class="boton-central btn btn-secondary" type="button" value="propertyTargetId"
							onclick="selectPropertyTarget()"/>
					</li>
				</ul>
					
				
				<hr>
				
				<ul style="list-style-type: none;">
					<li>
						<input class="boton-central btn btn-danger" type="button" value="Borrar regla"
							onclick="borrarRegla()"/>
					</li>
					<li>
						<input class="boton-central btn btn-danger" type="button" value="Borrar reglas servidor"
							onclick="borrarReglasServidor()"/>
					</li>
					<li>
						<input class="boton-central btn btn-success" type="button" value="Test tipo OWL"
							onclick="testTipoOwl()"/>
					</li>
				</ul>
			</div> -->
			
			<%-- ------------------------- ITEM RIGHT ------------------------- --%>
			<div id="targetSchemaDiv" class="col-lg-6">
				<%-- Barra de búsqueda del esquema destino --%>	
				<div id="searchTargetSchemaDiv" class="input-bar">
					<div class="input-bar-item width100">
						<form>
							<div class="input-group">
								<%-- Título de la barra --%>	
								<span class="input-group-btn">
									<button class="btn btn-info no-click">Búsqueda: </button>
								</span>
								<%-- Entrada de texto --%>
								<input type="text" id="searchTargetSchema" value="" class="form-control width100" style="z-index: 0;">
								<%-- Botón para limpiar la búsqueda --%>
								<span class="input-group-btn">
									<button type="button" id="searchTargetClearButton" value="Limpiar búsqueda" class="btn btn-warning" style="z-index: 0;">Limpiar búsqueda</button>
								</span>
							</div>
						</form>
					</div>
				</div>
				
				<%-- Árbol de elementos del esquema destino --%>
				<div id="targetSchema" class="w-100 marco-arbol">
					${targetTree}
				</div>
			</div>
		</div> <%-- /row --%>
		
		<%-- Adorno --%>
		<div>
			<hr class="hr-1">
		</div>
		
		<div class="row">
			<%-- ------------------------- FOOTER LEFT ------------------------- --%>
			<div class="col-lg-4 text-center marco-simple">
				<p>Elementos seleccionados (izq)</p>
				<pre id="elementos-izq"></pre>
			</div>
			
			<%-- ------------------------- FOOTER ------------------------- --%>
			<div class="col-lg-4 text-center marco-simple">
				<p>Respuesta "Prueba JSON"</p>
				<p id="mensaje"></p>
			</div>
			
			<%-- ------------------------- FOOTER RIGHT ------------------------- --%>
			<div class="col-lg-4 text-center marco-simple">
				<p>Elementos seleccionados (der)</p>
				<pre id="elementos-der"></pre>
			</div>
		</div> <%-- /row --%>
		
		<%-- ------------------------- REGLAS CREADAS ------------------------- --%>
		<div class="row">
			<div class="col-lg-12">
				<h3>Mapeos creados</h3>
				<hr>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-2">
				<div class="btn-group-vertical">
				  <button type="button" class="btn btn-primary" onclick="contraerTodasReglas()">Contraer todas</button>
				  <button type="button" class="btn btn-primary" onclick="expandirTodasReglas()">Expandir todas</button>
				  <hr>
				  <button type="button" class="btn btn-danger" onclick="eliminarTodasReglas()">Borrar todas las reglas</button>
				</div>
			</div>
			
			<div class="col-lg-10">
				<%@include file="representacionMapeos.jsp" %>
			</div>
		</div>
	</div>
	<%-- ------------------------- FIN GRID ------------------------- --%>

</div>

</body>
</html>
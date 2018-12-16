<%@include file="../commons/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>

<!-- JQuery, Bootstrap -->
<%@include file="../commons/imports.jsp" %>

<!-- CSS -->
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" >

<!-- Bootstrap filestyle (input ficheros) -->
<script src="${pageContext.request.contextPath}/javascript/bootstrap-filestyle/bootstrap-filestyle.min.js"></script>

<!-- Funciones para el funcionamiento de este formulario -->
<script src="${pageContext.request.contextPath}/javascript/execConfig/execConfig.js"></script>

<style>
	h2 {font-size: 1.1em; font-weight: bold;}
	h3 {font-size: 1.0em; font-weight: bold;}
	h4 {font-size: 0.8em;}
	.inputNumber {width: 25%;}
</style>

<script type="text/javascript">
	var jsonOptions;
	window.onload = function() {
		// Se preparan las opciones del formulario
	    jsonOptions = ${jsonString};
	    
	    prepararFormulario();
	    
	}
</script>


<title><spring:message code="configurador.titulo.pagina"/></title>

</head>
<body>

<!-- INI - Cabecera -->
<jsp:include page="/WEB-INF/jsp/commons/header.jsp"/>
<!-- FIN - Cabecera -->

<div id="main">
	<br>
	
	<div id="titulo" style='text-align:center;'>
		<h1><spring:message code="configurador.mensaje.titulo" /></h1>
	</div>
	
	<br>
	
	<%-- Adorno --%>
	<div>
		<hr>
	</div>
	
	<div class="container-fluid" style="font-size:1.2em;">
		
		<%-- INI - Formulario --%>
		<div id="divIniciarMapeo" style='text-align: left;' >
			<div class="row">
				<%-- MARGEN IZQUIERDO --%>
				<div class="col-lg-1 "></div>
				
				<%-- CONTENIDO PRINCIPAL --%>
				<div class="col-lg-10 ">
					<%-- SUBCOLUMNAS --%>
					<div class="container-fluid" style="font-size:1.0em;">
					
						<%-- FILA 1 - SUBCOLUMNAS --%>
						<div class="row">
							<%-- SUBCOLUMNA 1 --%>
							<div class="col-lg-12 " style="">
								<%-- COMANDO GENERADO --%>
								<h2><spring:message code="configurador.titulo.comando" /></h2>
								<hr class="hr-min-margin">
								
								<div class="btn-group" role="group" style="display: flex; justify-content: center;">
									<input class="btn btn-info" type="button" value="<spring:message code="configurador.titulo.boton.generar.comando" />" onclick="construirComando('${errorCamposObligatorios}');"/>
									<input id="buttonComandoGenerado" class="btn btn-default button-text-selectable" type="button" value=""
										style="white-space: normal;" onclick="copyStringToClipboard(this.value);"/>
								</div>
							</div> <%-- SUBCOLUMNA 1 --%>
							
						</div> <%-- FILA 1 - SUBCOLUMNAS --%>
						
						<br>
						
						<hr class="hr-10 hr-min-margin">
						<hr class="hr-5 hr-min-margin">
						<hr class="hr-1 hr-min-margin">
						
						<%-- FILA 2 - SUBCOLUMNAS --%>
						<div class="row">
							<%-- SUBCOLUMNA 1 --%>
							<div class="col-lg-4 " style="">
								<%-- CONTENIDO --%>
								<div class="container-fluid" style="font-size:1.0em;">
									<div class="row">
										<div id="subcolumna1" class="col-lg-11 " style="">
											
										</div>
										
										<div class="col-lg-1 "></div>
									</div>
								</div>
							</div> <%-- SUBCOLUMNA 1 --%>
							
							<%-- SUBCOLUMNA 2 --%>
							<div class="col-lg-4 vl" style="">
								<%-- CONTENIDO --%>
								<div class="container-fluid" style="font-size:1.0em;">
									<div class="row">
										<div id="subcolumna2" class="col-lg-11 " style="">
											
										</div>
										
										<div class="col-lg-1 "></div>
									</div>
								</div>
							</div> <%-- SUBCOLUMNA 2 --%>
							
							<%-- SUBCOLUMNA 3 --%>
							<div class="col-lg-4 vl" style="">
								<%-- CONTENIDO --%>
								<div class="container-fluid" style="font-size:1.0em;">
									<div class="row ">
										<div id="subcolumna3" class="col-lg-11" style="">
											
										</div>
										
										
										
										<div class="col-lg-1 "></div>
									</div>
								</div>
							</div> <%-- SUBCOLUMNA 3 --%>
						</div> <%-- FILA 2 - SUBCOLUMNAS --%>
						
						<br>
						
						<%-- MENSAJE AVISO CAMPOS OBLIGATORIOS --%>
						<div style="font-weight: bold;">
							<hr class="hr-min-margin">
							<spring:message code="configurador.mensaje.campos.obligatorios" />
						</div>
						
					</div> <%-- SUBCOLUMNAS --%>
				</div> <%-- CONTENIDO PRINCIPAL --%>
				
				
				
				<%-- MARGEN DERECHO --%>
				<div class="col-lg-1 "></div>
			</div>
		
			<br>
			<br>
			<br>
			
			<div class="row" style="height: 100px;">
			</div>
			
		</div>
		<%-- FIN - Formulario --%>
		
	</div> <%-- container --%>
	
	<!-- INI - Pie de página -->
	<jsp:include page="/WEB-INF/jsp/commons/footer.jsp"/>
	<!-- FIN - Pie de página -->
</div>

</body>
</html>

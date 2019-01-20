<%@include file="commons/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="inicio.titulo.pagina"/></title>

<!-- JQuery, Bootstrap -->
<%@include file="commons/imports.jsp" %>

<!-- CSS -->
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" >

	<script type="text/javascript">
		window.onload = function(){
			// Muestra el selector de idioma
			document.getElementById("selectorIdiomas").style.display = "inline";
		}
	</script>

</head>
<body>
<!-- INI - Cabecera -->
<jsp:include page="/WEB-INF/jsp/commons/header.jsp"/>
<!-- FIN - Cabecera -->

	<div class="container mw-75">
		
		<br><br>
		<!-- Título y descripción -->
		<div class="text-center">
			<h1 style="font-size:4em;">
				<spring:message code="comunes.nombre.herramienta"/>
			</h1>
			<div class="well rounded-border" style="background-color: snow;">
			<h2>
				<spring:message code="inicio.descripcion.swit"/>
			</h2>
			</div>
		</div>
		
		<br><br>
		
		<!-- Botones -->
		<div class="container-fluid ">
			<div class="text-center">
				<h4><spring:message code="inicio.boton.iniciar.mapeo"/></h4>
				<form:form method="get" action="schemaUploadStart.html">
					<input class="btn btn-primary" type="submit" value="<spring:message code="comunes.boton.ir.carga.ficheros"/>" />
		        </form:form>
	        </div>
		</div>
		
		<div class="container-fluid ">
			<div class="text-center">
				<h4><spring:message code="inicio.boton.iniciar.configurador"/></h4>
				<form:form method="post" action="execConfiguration.html">
					<input class="btn btn-primary" type="submit" value="<spring:message code="comunes.boton.ir.carga.ficheros"/>" />
		        </form:form>
	        </div>
		</div>
		
		
		
	</div>
	
	<%-- Adorno --%>
	<div class="mx-5">
		<br>
		<hr class="hr-5">
		<hr class="hr-10">
	</div>

<!-- INI - Pie de página -->
	<jsp:include page="/WEB-INF/jsp/commons/footer.jsp"/>
	<!-- FIN - Pie de página -->
</body>
</html>
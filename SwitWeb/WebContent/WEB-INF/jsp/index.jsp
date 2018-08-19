<%@include file="commons/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="inicio.titulo.pagina"/></title>

<!-- JQuery, Bootstrap -->
<%@include file="commons/imports.jsp" %>

<!-- CSS -->
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" >
<%-- <link href="${pageContext.request.contextPath}/css/nms_font.css" rel="stylesheet" > --%>

</head>
<body>
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
			<div class="row">
				<div class="col-lg-2 text-center"></div>
				<div class="col-lg-4 text-center">
					<h4><spring:message code="inicio.boton.iniciar.mapeo"/></h4>
					<form:form method="get" action="schemaUploadStart.html">
						<input class="btn btn-primary" type="submit" value="<spring:message code="comunes.boton.ir.carga.ficheros"/>" />
			        </form:form>
		        </div>
		        
		        <div class="col-lg-4 text-center">
			        <h4><spring:message code="inicio.boton.ir.informacion"/></h4>
					<form:form method="get" action="initInformation.html">
						<input class="btn btn-info" type="submit" value="<spring:message code="comunes.boton.ir.informacion"/>" />
			        </form:form>
		        </div>
		        <div class="col-lg-2 text-center"></div>
		       </div>
		</div>
		
	</div>
	
	<%-- Adorno --%>
	<div class="mx-5">
		<br>
		<hr class="hr-5">
		<hr class="hr-10">
	</div>


</body>
</html>
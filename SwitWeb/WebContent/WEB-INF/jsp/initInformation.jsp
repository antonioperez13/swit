<%@include file="commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="informacion.titulo.pagina"/></title>

<!-- CSS -->
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" > 

</head>
<body>
	<div id="mensajePrincipal">
		<spring:message code="informacion.mensaje.principal"/>
	</div>
	
	<hr>
	
	<p><spring:message code="informacion.mensaje.iniciar.mapeo"/></p>
		<form:form method="get" action="schemaUploadStart.html">
            <input type="submit" value="<spring:message code="comunes.boton.ir.carga.ficheros"/>" />
        </form:form>
</body>
</html>
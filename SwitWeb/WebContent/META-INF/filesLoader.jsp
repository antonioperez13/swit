<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- CSS for Bootstrap -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">

<!-- CSS -->
<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" > 

<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
<c:import url="/WEB-INF/javascript/ajax1.js" />
</script>

<script type="text/javascript">
	$(window).on("load", function() {
		if('${ficherosEsquemas.xsdFile}' != null &&
				'${ficherosEsquemas.owlFile}' != null){
			$('#botonIniciarMapeo').prop('disabled', false);
		} else {
			$('#botonIniciarMapeo').prop('disabled', true);
		}
	});


	function activarIniciarMapeo(){
		if('${ficherosEsquemas.xsdFile}' != null &&
				'${ficherosEsquemas.owlFile}' != null){
			$('#botonIniciarMapeo').prop('disabled', false);
		}
	}
</script>

<title>Carga de ficheros - SWIT</title>

</head>
<body>
	${message}

	<div style='text-align: center;'>
		<p>Seleccione el fichero XSD</p>
		<form:form method="post" action="uploadXsd.html"
			modelAttribute="ficherosEsquemas" enctype="multipart/form-data">
			<form:input type="file" path="xsdFile" />
			<br/>
			<input type="submit" value="Cargar XSD" />
		</form:form>
	</div>

	<div style='text-align: center;'>
		<p>Seleccione el fichero OWL</p>
		<form:form method="post" action="uploadOwl.html"
			modelAttribute="ficherosEsquemas" enctype="multipart/form-data"
			onsubmit="activarIniciarMapeo()">
			<form:input type="file" path="owlFile" />
			<br/>
			<input type="submit" value="Cargar OWL" />
		</form:form>
	</div>
	
	
	<div>
		<form style="margin-bottom: 20px">
            <input type="file" id="files" multiple style="margin-bottom: 20px"/><br/>
            <output id="selectedFiles"></output>
            <input id="uploadButton" type="button" value="Upload" style="margin-top: 20px"/>
        </form>
	
		<div id="progress-wrp">
		    <div class="progress-bar"></div>
		    <div class="status">0%</div>
		</div>
	</div>

<hr>

	<!-- Si el fichero XSD se ha subido correctamente -->
	<c:choose>
		<c:when test="${not empty ficherosEsquemas.sourceFileName}">
			<div class="marco-respuesta">
				<p>Fichero XSD "${ficherosEsquemas.sourceFileName}" subido correctamente.</p>
			</div>
		</c:when>
		<c:when test="${'' == ficherosEsquemas.sourceFileName}">
			<div class="marco-respuesta">
				<p>El fichero XSD subido no tiene la extensión correcta.</p>
			</div>
		</c:when>
	</c:choose>
	
	<!-- Si el fichero OWL se ha subido correctamente -->
	<c:choose>
		<c:when test="${not empty ficherosEsquemas.owlFileName}">
			<div class="marco-respuesta">
				<p>Fichero OWL "${ficherosEsquemas.owlFileName}" subido correctamente.</p>
			</div>
		</c:when>
		<c:when test="${'' == ficherosEsquemas.owlFileName}">
			<div class="marco-respuesta">
				<p>El fichero OWL subido no tiene la extensión correcta.</p>
			</div>
		</c:when>
	</c:choose>


<hr>

	<c:choose>
		<c:when test="${not empty ficherosEsquemas.xsdFile && not empty ficherosEsquemas.owlFile }">
			<div style='text-align: center;' >
				<form:form method="post" action="mappingPrincipal.html" >
					<input id="botonIniciarMapeo" type="submit" value="Ir a mapear"/>
				</form:form>
			</div>
		</c:when>
	</c:choose>


</body>
</html>
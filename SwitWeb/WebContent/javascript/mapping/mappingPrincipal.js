var ColeccionReglas = [];
var nuevaRegla = new Regla();

/**
 * Inicia la ayuda interactiva
 * @returns
 */
function iniciarAyuda(){
	ayudaInteractiva.restart();
}

function downloadMappingsFile(){
	if(ColeccionReglas.length == 0){
		showErrorById("alertSinReglasPorGuardarLocalError");
		return false;
	}
	
	window.location.href = "downloadMappingsFile.html";
	return true;
}

function showErrorById(idErrorAlert){
	$("#" + idErrorAlert).fadeTo(2000, 500).slideUp(500, function(){
		$("#" + idErrorAlert).slideUp(2000);
	});
}

//var data = {}
//data["domainNodeSource"] = $('#sourceSchema').jstree(true).get_text($('#sourceSchema').jstree(true).get_selected(true)[0]);

function saveBackupMappingsFile(){
	if(!isNullOrVoid($("#idRegistroBackup").val())){
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "saveBackupMappingsFile.html",
			data : ""+$("#idRegistroBackup").val(),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("saveBackupMappingsFile - SUCCESS");
				//var responseData = JSON.parse(data);
				$('#mensaje').html(data.result);
				//nuevaRegla = new Regla();
			},
			error : function(e) {
				console.log("saveBackupMappingsFile - ERROR: ", e);
			},
			done : function(e) {
				console.log("saveBackupMappingsFile - DONE");
			}
		});
	} else {
		showErrorById("alertSinReglasPorGuardarBackupError");
	}
}

function retrieveBackupMappingsFile(){
	if(!isNullOrVoid($("#idRegistroBackup").val())){
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "retrieveBackupMappingsFile.html",
			data : ""+$("#idRegistroBackup").val(),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("retrieveBackupMappingsFile - SUCCESS");
				//var responseData = JSON.parse(data);
				$('#mensaje').html(data.result);
				
				parseFileContentToReglas(data.result);
			},
			error : function(e) {
				console.log("retrieveBackupMappingsFile - ERROR: ", e);
			},
			done : function(e) {
				console.log("retrieveBackupMappingsFile - DONE");
			}
		});
	} else {
		showErrorById("cargarFicheroReglasBackupAlert");
	}
}

function parseFileContentToReglas(fileContent){
	// Elimina todas las reglas de la página
	eliminarTodasReglasPagina();
	
	var reglas = JSON.parse(fileContent);
	
	reglas.forEach(function(regla) {
		var nuevaRegla = Object.assign({}, regla);
		
		// Representa la regla en la página
		representarRegla(nuevaRegla);
		
		// La añade a la colección
	    ColeccionReglas.push(nuevaRegla);
	});
	
}

function loadMappingsFile() {
    var formData = new FormData();
    formData.append('multipartFile', $('#mappingsFileInput')[0].files[0]);
    console.log("form data " + formData);
    $.ajax({
        url : 'loadMappingsFile.html',
        data : formData,
        processData : false,
        contentType : false,
        type : 'POST',
        success : function(data) {
        	parseFileContentToReglas(data.result);
            cargaFicherosMappings.goTo(1);
        },
        error : function(err) {
            cargaFicherosMappings.goTo(2);
        }
    });
}

function loadMappingsFileSuccess(){
	$("#mappingsFileForm").hide();
	$("#cargaFicherosMappings-botonAceptar").prop('disabled', false);
	$("#cargaFicherosMappings-botonCancelar").hide;
}

function loadMappingsFileError(){
	$("#mappingsFileForm").hide();
	$("#cargaFicherosMappings-botonAceptar").prop('disabled', false);
	$("#cargaFicherosMappings-botonCancelar").hide;
}

function saveMappingsToFile(){
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "saveMappingsToFile.html",
		data : "",
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("saveMappingsToFile - SUCCESS");
			//var responseData = JSON.parse(data);
			$('#mensaje').html(data.result);
			//nuevaRegla = new Regla();			
		},
		error : function(e) {
			console.log("saveMappingsToFile - ERROR: ", e);
		},
		done : function(e) {
			console.log("saveMappingsToFile - DONE");
		}
	});
}

function removeRuleById(idRegla){
	// Se elimina la regla de la estructura de datos auxiliar
	borrarReglaPorId(idRegla);
	
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "removeRuleById.html",
		data : ""+idRegla,
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("removeRuleById - SUCCESS");
			//var responseData = JSON.parse(data);
			$('#mensaje').html(data.result);
			//nuevaRegla = new Regla();
			
			if(data.code == "210"){
				console.log(data.result);
			} else if(data.code == "410") {
				console.log(data.result);
			}				
		},
		error : function(e) {
			console.log("removeRuleById - ERROR: ", e);
		},
		done : function(e) {
			console.log("removeRuleById - DONE");
		}
	});
}

function mappingClassRule(){
	
	var regla = prepararReglaClase(nuevaRegla);
	
	if(regla == null){
		console.log("mappingClassRule - ERROR: Regla de clase incorrecta");
		$('#mensaje').html("Error: Regla de clase incorrecta");
	} else {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "mappingRule.html",
			data : JSON.stringify(regla),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("mappingClassRule - SUCCESS");
				//var responseData = JSON.parse(data);
				$('#mensaje').html(data.result);
				//nuevaRegla = new Regla();
				
				if(data.code == "201"){
					regla.id = data.msg;
					console.log(comprobarTipoRegla(regla));
					ColeccionReglas.push(regla);
					representarRegla(regla);
				} else if(data.code == "202") {
					console.log("Regla repetida");
				}				
			},
			error : function(e) {
				console.log("mappingClassRule - ERROR: ", e);
			},
			done : function(e) {
				console.log("mappingClassRule - DONE");
			}
		});
	}
}

function mappingPropertyRule(){
	
	var regla = prepararReglaPropiedad(nuevaRegla);
	
	if(regla == null){
		console.log("mappingPropertyRule - ERROR: Regla de propiedad incorrecta");
		$('#mensaje').html("Error: Regla de clase incorrecta");
	} else {
		console.log(comprobarTipoRegla(regla));
		ColeccionReglas.push(regla);
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "mappingRule.html",
			data : JSON.stringify(regla),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("mappingPropertyRule - SUCCESS");
				//var responseData = JSON.parse(data);
				$('#mensaje').html(data.result);
				//nuevaRegla = new Regla();
				
				if(data.code == "201"){
					regla.id = data.msg;
					console.log(comprobarTipoRegla(regla));
					ColeccionReglas.push(regla);
					representarRegla(regla);
				} else if(data.code == "202") {
					console.log("Regla repetida");
				}
			},
			error : function(e) {
				console.log("mappingPropertyRule - ERROR: ", e);
			},
			done : function(e) {
				console.log("mappingPropertyRule - DONE");
			}
		});
	}
}

function mappingRelationRule(){
	
	var regla = prepararReglaRelacion(nuevaRegla);
	
	if(regla == null){
		console.log("mappingRelationRule - ERROR: Regla de relación incorrecta");
		$('#mensaje').html("Error: Regla de clase incorrecta");
	} else {
		console.log(comprobarTipoRegla(regla));
		ColeccionReglas.push(regla);
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "mappingRule.html",
			data : JSON.stringify(regla),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("mappingRelationRule - SUCCESS");
				//var responseData = JSON.parse(data);
				$('#mensaje').html(data.result);
				//nuevaRegla = new Regla();
				
				if(data.code == "201"){
					regla.id = data.msg;
					console.log(comprobarTipoRegla(regla));
					ColeccionReglas.push(regla);
					representarRegla(regla);
				} else if(data.code == "202") {
					console.log("Regla repetida");
				}
			},
			error : function(e) {
				console.log("mappingRelationRule - ERROR: ", e);
			},
			done : function(e) {
				console.log("mappingRelationRule - DONE");
			}
		});
	}
}

function copyIdSesionToClipboard(){
	document.getElementById("idRegistroBackup").select();
	document.execCommand("copy");
}

//	REGLA
//		id;
//		tipo;
//		domainNodeSource;
//		domainClassTarget;
//		rangeNodeSource;
//		rangeClassTarget;
//		propertyValueSource;
//		propertyTarget;



/** FUNCIONES DEL SOURCE */
function createSelectedSourceElement(){
	var selectedElement = getSelectedSourceElement();
	var name = $('#sourceSchema').jstree(true).get_selected(true)[0].text;
	var id = $(selectedElement).attr("id-elemento");
	var route = $(selectedElement).attr("ruta-elemento");
	var tipo = $(selectedElement).attr("tipo-elemento");
	
	return new Elemento(name, id, route, tipo);
}

function selectDomainNodeSource() {
	nuevaRegla.domainNodeSource = createSelectedSourceElement();
	mostrarElementosIzq();
}

function selectRangeNodeSource() {
	nuevaRegla.rangeNodeSource = createSelectedSourceElement();
	mostrarElementosIzq();
}

function selectPropertySource() {
	nuevaRegla.propertyValueSource = createSelectedSourceElement();
	mostrarElementosIzq();
}


/** FUNCIONES DEL TARGET */
function createSelectedTargetElement(){
	var selectedElement = getSelectedTargetElement();
	var name = $('#targetSchema').jstree(true).get_selected(true)[0].text;
	var id = $(selectedElement).attr("id-elemento");
	var route = $(selectedElement).attr("ruta-elemento");
	var tipo = $(selectedElement).attr("tipo-elemento");
	
	return new Elemento(name, id, route, tipo);
}

function selectDomainClassTarget() {
	nuevaRegla.domainClassTarget = createSelectedTargetElement();
	//nuevaRegla.domainClassTarget = $('#targetSchema').jstree(true).get_text($('#targetSchema').jstree(true).get_selected(true)[0]);
	
	if(!comprobarSeleccionDomainClassTarget(nuevaRegla.domainClassTarget)){
		nuevaRegla.domainClassTarget = null;
		
	}
	
	mostrarElementosDer();
}

function selectRangeClassTarget() {
	nuevaRegla.rangeClassTarget = createSelectedTargetElement();
	mostrarElementosDer();
}

function selectPropertyTarget() {
	nuevaRegla.propertyTarget = createSelectedTargetElement();
	mostrarElementosDer();
}



/** COMPROBACIONES PARA LOS ELEMENTOS DEL TARGET SELECCIONADOS */

function comprobarSeleccionDomainClassTarget(nuevoDomainClassTarget){

	if(nuevoDomainClassTarget.type == TipoElementoOwl.DATATYPE ||
			nuevoDomainClassTarget.type == TipoElementoOwl.PROPERTY){
		mostrarErrorSeleccionarClaseOwl();
		return false;
	}
	
	return true;
}

function comprobarSeleccionRangeClassTarget(nuevoDomainClassTarget){
	
	return true;
}


function mostrarErrorSeleccionarClaseOwl(){
	tempAlert($('#owlErrorSeleccionarClase').text(), 2000);
	
	mostrarErrorEnCreacionGuiada($('#owlErrorSeleccionarClase').text(), 3000);
}

function mostrarErrorEnCreacionGuiada(textoError, duracion) {
	$("[id='errorEleccionCreacionGuiada']").text(textoError);
	$("[id='errorEleccionCreacionGuiada']").fadeIn(500);
	setTimeout(function(){
		$("[id='errorEleccionCreacionGuiada']").fadeOut(1000);
	},duracion);
}


function mostrarElementosIzq(){
	var texto = "";
	if(nuevaRegla.domainNodeSource != null){
		texto += "domainNodeSource = " + nuevaRegla.domainNodeSource.name + "\n";
		$("[id='creacionGuiadaDomainNode']").text(nuevaRegla.domainNodeSource.name);
	}
	if(nuevaRegla.rangeNodeSource != null){
		texto += "rangeNodeSource = " + nuevaRegla.rangeNodeSource.name + "\n";
		$("[id='creacionGuiadaRangeNodeSource']").text(nuevaRegla.rangeNodeSource.name);
	}
	if(nuevaRegla.propertyValueSource != null){
		texto += "propertyValueSource = " +  nuevaRegla.propertyValueSource.name;
		$("[id='creacionGuiadaPropertyValueSource']").text(nuevaRegla.propertyValueSource.name);
	}
	
	$('#elementos-izq').text(texto);
}

function mostrarElementosDer(){
	var texto = "";
	if(nuevaRegla.domainClassTarget != null){
		texto += "domainClassTarget = " + nuevaRegla.domainClassTarget.name + "\n";
		$("[id='creacionGuiadaDomainClass']").text(nuevaRegla.domainClassTarget.name);
	}
	if(nuevaRegla.rangeClassTarget != null){
		texto += "rangeClassTarget = " + nuevaRegla.rangeClassTarget.name + "\n";
		$("[id='creacionGuiadaRangeClassTarget']").text(nuevaRegla.rangeClassTarget.name);
	}
	if(nuevaRegla.propertyTarget != null){
		texto += "propertyTarget = " +  nuevaRegla.propertyTarget.name;
		$("[id='creacionGuiadaPropertyTarget']").text(nuevaRegla.propertyTarget.name);
	}
	
	$('#elementos-der').text(texto);
}

function borrarRegla() {
	nuevaRegla = new Regla();
}

function borrarReglasServidor(){
	$.ajax({
		type : "POST",
		url : "removeAllRules.html",
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS");
			$('#mensaje').html(data.result);
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

/**
 * Elimina la regla que coincida con el id indicado.
 * @param botonEliminar
 * @returns La regla eliminada. Devuelve null si no existe la regla.
 */
function borrarReglaPorId(reglaId){
	var numReglasTotal = ColeccionReglas.length;
	// Recorre la el array de reglas
	for (var i = 0; i < numReglasTotal; i++) {
		if(ColeccionReglas[i].id == reglaId) {
			// Si encuentra la regla la elimina del array
			var reglaBorrada = ColeccionReglas[i];
			ColeccionReglas.splice(i, 1);
			return reglaBorrada;
		}
	}
	return null;
}

function testTipoOwl(){
	var tipo = obtenerTipoElementoOwl();
	var idElemento = obtenerIdElementoOwl();
	tempAlert("Tipo de elemento OWL: " + tipo + ", id = " + idElemento 
			, 2000);
}

function getSelectedSourceElement(){
	return $($('#sourceSchema').jstree(true).get_selected(true)[0]).attr("li_attr");
}

function getSelectedTargetElement(){
	return $($('#targetSchema').jstree(true).get_selected(true)[0]).attr("li_attr");
}


function obtenerTipoElementoOwl(){
	return $($($('#targetSchema').jstree(true).get_selected(true)[0]).attr("li_attr")).attr("tipo-elemento");
}

function obtenerIdElementoOwl(){
	return $($($('#targetSchema').jstree(true).get_selected(true)[0]).attr("li_attr")).attr("id-elemento");
}

function obtenerRutaElementoOwl(){
	return $($($('#targetSchema').jstree(true).get_selected(true)[0]).attr("li_attr")).attr("ruta-elemento");
}

function obtenerIdElementoSource(){
	return $($($('#sourceSchema').jstree(true).get_selected(true)[0]).attr("li_attr")).attr("id-elemento");
}

function obtenerRutaElementoSource(){
	return $($($('#sourceSchema').jstree(true).get_selected(true)[0]).attr("li_attr")).attr("ruta-elemento");
}

function tempAlert(msg,duration){
	var el = document.createElement("div");
	el.setAttribute("style","position:absolute;top:50%;left:50%;background-color:white;font-size: x-large;");
	el.innerHTML = msg;
	setTimeout(function(){
		el.parentNode.removeChild(el);
	},duration);
	document.body.appendChild(el);
}

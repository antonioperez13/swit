var ColeccionReglas = [];
var nuevaRegla = new Regla();
var targetSchemaTreeData;

// polyfill para usar asign en IE
if (typeof Object.assign != 'function') {
  Object.assign = function(target, varArgs) { // .length of function is 2
    'use strict';
    if (target == null) { // TypeError if undefined or null
      throw new TypeError('Cannot convert undefined or null to object');
    }

    var to = Object(target);

    for (var index = 1; index < arguments.length; index++) {
      var nextSource = arguments[index];

      if (nextSource != null) { // Skip over if undefined or null
        for (var nextKey in nextSource) {
          // Avoid bugs when hasOwnProperty is shadowed
          if (Object.prototype.hasOwnProperty.call(nextSource, nextKey)) {
            to[nextKey] = nextSource[nextKey];
          }
        }
      }
    }
    return to;
  };
}

/**
 * Inicia la ayuda interactiva
 * @returns
 */
function iniciarAyuda(){
	ayudaInteractiva.restart();
}

/**
 * Si hay reglas en la colección llama al controlador para descargarlas,
 * si no muestra un mensaje de error.
 * @returns
 */
function downloadMappingsFile(){
	if(ColeccionReglas.length == 0){
		showErrorById("alertSinReglasPorGuardarLocalError");
		return false;
	}
	
	window.location.href = "downloadMappingsFile.html";
	return true;
}

/**
 * Utilidad para mostrar durante unos segundos y ocultar
 * el elemento indicado por su id.
 * @param idErrorAlert
 * @returns
 */
function showErrorById(idErrorAlert){
	$("#" + idErrorAlert).fadeTo(2000, 500).slideUp(500, function(){
		$("#" + idErrorAlert).slideUp(2000);
	});
}

/**
 * Muestra el modal usado para indicar un error. 
 * @param desc
 * @returns
 */
function showModalError(desc){
	$("#modalErrorDescripcion").text(desc);
	$('.hover_bkgr_fricc').show();
}

/**
 * Muestra el modal usado para indicar una advertencia.
 * @param desc
 * @returns
 */
function showModalAdvert(desc){
	$("#modalAdvertenciaDescripcion").text(desc);
	$('.hover_bkgr_fricc_advert').show();
}

/**
 * Llama al controlador para guardar las reglas en el servidor del WS.
 * Si hay identificador de sesión llama a la función de reemplazo, si no
 * a la de registro.
 * Si no hay reglas muestra un error.
 * @returns
 */
function saveBackupMappingsFile(){
	if(ColeccionReglas.length > 0){
		if(isNullOrVoid($("#idRegistroBackup").val())){
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "saveBackupMappingsFile.html",
				dataType : 'json',
				timeout : 100000,
				success : function(data) {
					if(data.code == "205"){
						console.log("saveBackupMappingsFile - SUCCESS");
						$('#mensaje').html(data.result);
						$("#idRegistroBackup").val(data.result);
					} else if(data.code == "404" || data.code == "405"){
						console.log("saveBackupMappingsFile - ERROR");
						$('#mensaje').html(data.msg);
						showModalError(data.msg);
					} else {
						console.log(data.msg);
						showModalError(data.msg);
					}
				},
				error : function(e) {
					console.log("saveBackupMappingsFile - ERROR: ", e);
				},
				done : function(e) {
					console.log("saveBackupMappingsFile - DONE");
				}
			});
		} else {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "saveBackupMappingsFileReplace.html",
				data : ""+$("#idRegistroBackup").val(),
				dataType : 'json',
				timeout : 100000,
				success : function(data) {
					if(data.code == "205"){
						console.log("saveBackupMappingsFile - SUCCESS");
						$('#mensaje').html(data.result);
						$("#idRegistroBackup").val(data.result);
					} else if(data.code == "404" || data.code == "405"){
						console.log("saveBackupMappingsFile - ERROR");
						$('#mensaje').html(data.msg);
						showModalError(data.msg);
					} else {
						console.log(data.msg);
						showModalError(data.msg);
					}
				},
				error : function(e) {
					console.log("saveBackupMappingsFile - ERROR: ", e);
				},
				done : function(e) {
					console.log("saveBackupMappingsFile - DONE");
				}
			});
		}
	} else {
		showErrorById("alertSinReglasPorGuardarBackupError");
	}
}

/**
 * Llama al controlador para recuperar las reglas desde el WS.
 * Si no hay identficador de sesión muestra un error.
 * @returns
 */
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
				if(data.code == "207"){
					console.log("retrieveBackupMappingsFile - SUCCESS");
					$('#mensaje').html(data.msg);
					
					// Se parsea el fichero para crear las reglas
					parseFileContentToReglas(data.result);
				} else { //if(data.code == "404" || data.code == "407")
					console.log("retrieveBackupMappingsFile - SUCCESS");
					$('#mensaje').html(data.msg);
					showModalError(data.msg);
				}
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

/**
 * Parsea el contenido de un fichero (en JSON) a una 
 * colección de reglas.
 * @param fileContent
 * @returns
 */
function parseFileContentToReglas(fileContent){
	
	var reglas = JSON.parse(fileContent);
	
	reglas.forEach(function(regla) {
		var nuevaRegla = Object.assign({}, regla);
		
		// Representa la regla en la página
		representarRegla(nuevaRegla);
		
		// La añade a la colección
	    ColeccionReglas.push(nuevaRegla);
	});
	
}

/**
 * Carga un fichero local de reglas creado con SWIT.
 * @returns
 */
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
        	eliminarTodasReglasCliente();
        	parseFileContentToReglas(data.result);
            cargaFicherosMappings.goTo(1);
        },
        error : function(err) {
            cargaFicherosMappings.goTo(2);
        }
    });
}

/**
 * Funcionalidad en caso de que se cargue correctamente un fichero.
 * @returns
 */
function loadMappingsFileSuccess(){
	$("#mappingsFileForm").hide();
	$("#cargaFicherosMappings-botonAceptar").prop('disabled', false);
	$("#cargaFicherosMappings-botonCancelar").hide;
}

/**
 * Funcionalidad en caso de que no se cargue correctamente un fichero.
 * @returns
 */
function loadMappingsFileError(){
	$("#mappingsFileForm").hide();
	$("#cargaFicherosMappings-botonAceptar").prop('disabled', false);
	$("#cargaFicherosMappings-botonCancelar").hide;
}

/**
 * Elimina la regla de la colección, de la página y llama al controlador para
 * eliminarla del catálogo donde se encuentra.
 * @param idRegla
 * @returns
 */
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
			
			if(data.code == "203"){
				console.log(data.result);
			} else if(data.code == "402") {
				console.log(data.msg);
				showModalError(data.msg);
			} else {
				console.log(data.msg);
				showModalError(data.msg);
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

/**
 * Crea una regla de clase nueva con los elementos seleccionados.
 * Llama al controlador para guardar la nueva regla.
 * @returns
 */
function mappingClassRule(){
	var regla = prepararReglaClase(nuevaRegla);
	
	if(regla == null){
		console.log("mappingClassRule - ERROR: Regla de clase incorrecta");
		$('#mensaje').html("Error: Regla de clase incorrecta");
	} else {
		regla.etiqueta = $("#idInputEtiquetaCreacionGuiadaReglaClase").val();
		
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
					regla.id = data.result;
					console.log(comprobarTipoRegla(regla));
					ColeccionReglas.push(regla);
					representarRegla(regla);
				} else if(data.code == "202") {
					console.log("Regla repetida");
					showModalAdvert(data.msg);
				} else {
					console.log(data.msg);
					showModalError(data.msg);
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

/**
 * Crea una regla de propiedad nueva con los elementos seleccionados.
 * Llama al controlador para guardar la nueva regla.
 * @returns
 */
function mappingPropertyRule(){
	var regla = prepararReglaPropiedad(nuevaRegla);
	
	if(regla == null){
		console.log("mappingPropertyRule - ERROR: Regla de propiedad incorrecta");
		$('#mensaje').html("Error: Regla de clase incorrecta");
	} else {
		regla.etiqueta = $("#idInputEtiquetaCreacionGuiadaReglaPropiedad").val();
		
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
					regla.id = data.result;
					console.log(comprobarTipoRegla(regla));
					ColeccionReglas.push(regla);
					representarRegla(regla);
				} else if(data.code == "202") {
					console.log("Regla repetida");
					showModalAdvert(data.msg);
				} else {
					console.log(data.msg);
					showModalError(data.msg);
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

/**
 * Crea una regla de relacion nueva con los elementos seleccionados.
 * Llama al controlador para guardar la nueva regla.
 * @returns
 */
function mappingRelationRule(){
	var regla = prepararReglaRelacion(nuevaRegla);
	
	if(regla == null){
		console.log("mappingRelationRule - ERROR: Regla de relación incorrecta");
		$('#mensaje').html("Error: Regla de clase incorrecta");
	} else {
		regla.etiqueta = $("#idInputEtiquetaCreacionGuiadaReglaRelacion").val();
		
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
					regla.id = data.result;
					console.log(comprobarTipoRegla(regla));
					ColeccionReglas.push(regla);
					representarRegla(regla);
				} else if(data.code == "202") {
					console.log("Regla repetida");
					showModalAdvert(data.msg);
				} else {
					console.log(data.msg);
					showModalError(data.msg);
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

/**
 * Copia el contenido del input del identificador de sesión al portapapeles.
 * @returns
 */
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
	var uri = $(selectedElement).attr("uri-elemento");
	
	return new Elemento(name, id, route, tipo, uri);
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
	if(nuevaRegla.domainNodeSource != null){
		$("[id='creacionGuiadaDomainNode']").text(nuevaRegla.domainNodeSource.name);
	}
	if(nuevaRegla.rangeNodeSource != null){
		$("[id='creacionGuiadaRangeNodeSource']").text(nuevaRegla.rangeNodeSource.name);
	}
	if(nuevaRegla.propertyValueSource != null){
		$("[id='creacionGuiadaPropertyValueSource']").text(nuevaRegla.propertyValueSource.name);
	}
}

function mostrarElementosDer(){
	if(nuevaRegla.domainClassTarget != null){
		$("[id='creacionGuiadaDomainClass']").text(nuevaRegla.domainClassTarget.name);
	}
	if(nuevaRegla.rangeClassTarget != null){
		$("[id='creacionGuiadaRangeClassTarget']").text(nuevaRegla.rangeClassTarget.name);
	}
	if(nuevaRegla.propertyTarget != null){
		$("[id='creacionGuiadaPropertyTarget']").text(nuevaRegla.propertyTarget.name);
	}
}

/**
 * Borra la regla actual.
 * @returns
 */
function borrarRegla() {
	nuevaRegla = new Regla();
}

/**
 * Llama al servidor para borrar todas las reglas.
 * @returns
 */
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


function escapeHtml(unsafe) {
    return unsafe
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
 }


/**
 * Deshabilita los elementos del árbol del esquema destino que tengan la clase indicada.
 * @param tipoElemento Tipo 
 */
function deshabilitarElementosEsquemaDestinoPorTipoElemento(tipoElemento){
	$("#targetSchema").jstree(true).deselect_node($("#targetSchema").jstree(true).get_selected());
	$.each(targetSchemaTreeData, function (i, val) {
	    if($($(val)[0].li_attr).attr('tipo-elemento') === tipoElemento){
			$("#targetSchema").jstree(true).disable_node(val);
	    }
	});
}

/**
 * Habilita los elementos del árbol del esquema destino que tengan la clase indicada.
 * @param tipoElemento Tipo 
 */
function habilitarElementosEsquemaDestinoPorTipoElemento(tipoElemento){
	$.each(targetSchemaTreeData, function (i, val) {
	    if($($(val)[0].li_attr).attr('tipo-elemento') === tipoElemento){
			$("#targetSchema").jstree(true).enable_node(val);
	    }
	});
}

/**
 * Habilita todos los elementos del árbol del esquema destino.
 */
function habilitarTodosElementosEsquemaDestino(){
	$.each(targetSchemaTreeData, function (i, val) {
		$("#targetSchema").jstree(true).enable_node(val);
	});
}


function deshabilitarElementosEsquemaDestinoExceptoClass(){
	$("#targetSchema").jstree(true).deselect_node($("#targetSchema").jstree(true).get_selected());
	$.each(targetSchemaTreeData, function (i, val) {
	    if($($(val)[0].li_attr).attr('tipo-elemento') !== TipoElementoOwl.CLASS){
			$("#targetSchema").jstree(true).disable_node(val);
	    }
	});
}

function deshabilitarElementosEsquemaDestinoExceptoProperty(){
	$("#targetSchema").jstree(true).deselect_node($("#targetSchema").jstree(true).get_selected());
	$.each(targetSchemaTreeData, function (i, val) {
	    if($($(val)[0].li_attr).attr('tipo-elemento') !== TipoElementoOwl.PROPERTY){
			$("#targetSchema").jstree(true).disable_node(val);
	    }
	});
}
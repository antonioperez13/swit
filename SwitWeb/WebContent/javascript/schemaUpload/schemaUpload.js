/** El tipo de fichero puede ser del esquema de origen (source) o de destino (target) */
var TipoFichero = Object.freeze({"SOURCE":"source", "TARGET":"target"});

/** Variables para controlar la subida del fichero */
var totalFileLength, totalUploaded, fileCount, filesUploaded;

/** Indica si el fichero que se está subiendo es para el esquema de origen o destino */
//var fileType;

/** Indican si ya hay subido un fichero origen/destino */
var sourceFileUploaded, targetFileUploaded;
/** Indican qué fichero se está subiendo en un momento determinado */
var sourceFileUploading, targetFileUploading;


/**
 * Carga en el select indicado las posibles opciones, que se obtendrán de un string con un formato concreto.
 * @param selectId Id del select
 * @param stringData String con las opciones. El formato es "value-1:text-1;value-2:text-2;...value-n:text-n"
 * @returns
 */
function cargarDatosSelect(selectId, stringData){
	var select = document.getElementById(selectId);
	
	stringData.split(";").forEach( function(element){
		var par = element.split(":");
		
		var option = document.createElement("option");
		option.value = par[0];
		option.text = par[1];
		
		select.add(option);
	});
}
 
/**
 * Muestra información de debug.
 * @param s
 * @returns
 */
function debug(s) {
    var debug = document.getElementById('debug');
    if (debug) {
        debug.innerHTML = debug.innerHTML + '<br/>' + s;
    }
}
 
/**
 * Funcion que se ejecuta al terminar de subir un fichero. Actualiza los elementos de la
 * vista necesarios y comprueba si se tiene que mostrar el botón para continuar al mapeo.
 * @param tipoFichero
 * @param fileType
 * @returns
 */
function onUploadComplete(tipoFichero, fileType) {
    totalUploaded += document.getElementById(fileType + 'File').files[0].size;

    var bar = document.getElementById(fileType + 'FileBar');
    bar.style.width = '100%';
    bar.innerHTML = '100%';
    //alert('Finished uploading file(s)');
    
    var output = [];
    
    output.push("Fichero " + tipoFichero.toUpperCase() + " subido correctamente");
    
    document.getElementById(fileType + 'FileOutput').innerHTML = output.join('');
    
    
    // Comprueba el tipo de fichero subido para dejarlo indicado
    if(sourceFileUploading){
    	sourceFileUploaded = true;
    	sourceFileUploading = false;
    } else if(targetFileUploading){
    	targetFileUploaded = true;
    	targetFileUploading = false;
    }
    
    // Se comprueba si ya se han subido todos los ficheros necesarios
    checkAllFilesUploaded();
}

 
/**
 * Actualiza la barra de progreso de la subida del fichero
 * @param e
 * @param tipoFichero
 * @param fileType
 * @returns
 */
function onUploadProgress(e, tipoFichero, fileType) {
    if (e.lengthComputable) {
        var percentComplete = parseInt((e.loaded + totalUploaded) * 100 / totalFileLength);
        var bar = document.getElementById(fileType + 'FileBar');
        bar.style.width = percentComplete + '%';
        bar.innerHTML = percentComplete + ' %';
    } else {
        debug('unable to compute');
    }
}

/**
 * Reinicia la barra de progreso del tipo de fichero que se intenta subir
 * @param fileType
 * @returns
 */
function restartBar(fileType){
	var bar = document.getElementById(fileType + 'FileBar');
    bar.style.width = 0 + '%';
    bar.innerHTML = '';
    document.getElementById(fileType + 'FileOutput').innerHTML = "";
}
 

/**
 * Alerta a mostrar en caso de error al subir un fichero
 * @param e
 * @returns
 */
function onUploadFailed(e) {
	checkAllFilesUploaded();
    alert("Error uploading file");
}

/**
 * Realiza la subida del fichero seleccionado
 * @param e
 * @param tipoFichero
 * @param fileType
 * @returns
 */
function uploadFile(e, tipoFichero, fileType) {
	// Reinicia la barra de progreso del fichero que se va a subir
	restartBar(fileType);
	
	// Reinicia el marcador de fichero subido según el caso
    if(sourceFileUploading){
    	sourceFileUploaded = false;
    } else if(targetFileUploading){
    	targetFileUploaded = false;;
    }
	
    var xhr = new XMLHttpRequest();
    var fd = new FormData();
    var file = document.getElementById(fileType + 'File').files[0];
    fd.append("multipartFile", file);
    xhr.upload.addEventListener("progress", function(){onUploadProgress(e, tipoFichero, fileType);}, false);
    xhr.addEventListener("load", function(){onUploadComplete(tipoFichero, fileType)}, false);
    xhr.addEventListener("error", onUploadFailed, false);
    xhr.open("POST", "upload" + tipoFichero + ".html");
    //debug('uploading ' + file.name);
    // Se comprueba si el fichero subido es válido
    var errorId = validateFile(file, tipoFichero);
    if(errorId == 0){
    	xhr.send(fd);
    } else {
    	// Se cambia el marcador de subida de fichero
    	if(sourceFileUploading){
    		sourceFileUploading = false;
        } else if(targetFileUploading){
        	targetFileUploading = false;
        }
    	
    	showValidationError(errorId, tipoFichero, fileType);
    	checkAllFilesUploaded();
    }
}

/**
 * Hace visible con una transición la sección de elección del formato de fichero de salida y el botón para 
 * ir al siguiente paso
 * @returns
 */
function irAMapeoFadeIn(){
	$('#divIniciarMapeo').fadeIn(1000);
}

/**
 * Oculta con una transición la sección de elección del formato de fichero de salida y el botón para 
 * ir al siguiente paso
 * @returns
 */
function irAMapeoFadeOut(){
	$('#divIniciarMapeo').fadeOut(500);
}


/////////////////////////////////////////////////////////////////////////////////////////
// Validaciones y mensajes de error
////////////////////////////////////////////////////////////////////////////////////////

/**
 * Validacion de los ficheros subidos.
 * @param file
 * @param tipoFichero
 * @returns
 */
function validateFile(file, tipoFichero){
	// Se comprueba la extensión del fichero
	var fileExtension = getFileExtension(file.name);
	if(!stringEqualsIgnoreCase(fileExtension, tipoFichero)){
		return 1;
	}
	
	// Se comprueba el tamaño del fichero
	if(file.size == 0){
		return 2;
	}
	
	// Si todas las validaciones han ido bien se devuelve 0
	return 0;
}

/**
 * Selecciona el mensaje de error que se ajuste al caso dado y lo
 * muestra en la vista.
 * @param errorId
 * @param tipoFichero
 * @param fileType
 * @returns
 */
function showValidationError(errorId, tipoFichero, fileType){
	var messageCode;
	switch(errorId){
	case 1: // Formato incorrecto
		messageCode = $("#errorFormatoIncorrecto").text();
		break;
	case 2: // Fichero vacío
		messageCode = $("#errorFicheroVacio").text();
		break;
	default: // Error genérico
		messageCode = $("#errorGenerico").text();
	}
	
	showErrorMessage(tipoFichero, messageCode, fileType);
}

/**
 * Establece en el elemento de la vista correspondiente el mensaje de error que se 
 * ajuste a los parámetros dados.
 * @param tipoFichero
 * @param messageCode
 * @param fileType
 * @returns
 */
function showErrorMessage(tipoFichero, messageCode, fileType){
	var output = [];
    
    output.push("Error: " + messageCode);
    
    document.getElementById(fileType + 'FileOutput').innerHTML = output.join('');
}

/**
 * Comprueba si se han subido los esquemas fuente y objetivo
 * necesarios para el mapeo y muestra el botón para continuar.
 * @returns
 */
function checkAllFilesUploaded(){
	if(sourceFileUploaded && targetFileUploaded){
		irAMapeoFadeIn();
	} else {
		irAMapeoFadeOut();
	}
}

/////////////////////////////////////////////////////////////////////////////////////////
// Mensajes de ayuda
/////////////////////////////////////////////////////////////////////////////////////////

/**
 * Hace visible la sección de elección del formato de fichero de salida y el botón para 
 * ir al siguiente paso
 * @returns
 */
function mostrarIrAMapeo(){
	$('#divIniciarMapeo').show();
}

/**
 * Oculta la sección de elección del formato de fichero de salida y el botón para 
 * ir al siguiente paso solo si no se cumplen las condiciones para que sean visibles
 * @returns
 */
function ocultarIrAMapeo(){
	if(!sourceFileUploaded || !targetFileUploaded){
		$('#divIniciarMapeo').hide();
	}
}

/**
 * Inicia la ayuda interactiva
 * @returns
 */
function iniciarAyuda(){
	ayudaInteractiva.restart();
}


/////////////////////////////////////////////////////////////////////////////////////////
// Utiles
/////////////////////////////////////////////////////////////////////////////////////////


/**
 * Devuelve la extensión del fichero.
 * @param filename
 * @returns
 */ 
function getFileExtension(filename){
	return filename.split('.').pop();
}

/**
 * Compara las cadenas ignorando mayusculas/minusculas
 * @param string1
 * @param string2
 * @returns
 */
function stringEqualsIgnoreCase(string1, string2){
	return string1.toUpperCase() === string2.toUpperCase();
}


/**
 * Añade un mensaje de error al elemento que se indique
 * @param msg
 * @param parentElementId
 * @returns
 */
function addErrorMsg(msg, parentElementId){
	var newError = document.createElement("p");
	newError.className = "error-msg";
	newError.textContent = msg;
	document.getElementById(parentElementId).appendChild(newError);
}


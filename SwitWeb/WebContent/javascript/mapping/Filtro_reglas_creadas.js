
function limpiarFiltroReglasCreadas(){
	// Se vacían los imput de texto
	$('#idRepresentacionReglasEsquemaOrigen').val("");
	$('#idRepresentacionReglasEsquemaDestino').val("");
	
	// Se marcan de nuevo los checkbox de los tipos de reglas
	$("#checkboxFiltroReglaClase").prop('checked', true);
	$("#checkboxFiltroReglaPropiedad").prop('checked', true);
	$("#checkboxFiltroReglaRelacion").prop('checked', true);
	
	// Se hacen visibles todas las reglas
	$("#representacionReglasContenedor").children().show();
}


function filtrarReglasCreadas(sourceFilter, targetFilter){
	// Se ocultan todas las reglas
	$("#representacionReglasContenedor").children().hide();

	// Aquellas reglas que cumplan con todos los filtros serán visibles
	ColeccionReglas.forEach(function(element) {
		var mostrarReglaPorOrigen = false;
		var mostrarReglaPorDestino = false;
		var mostrarReglaPorClase = false;
		
		
		// Filtrado por elemento del esquema origen
		if(sourceFilter != ""){
			if(element.domainNodeSource.name.toLowerCase().includes(sourceFilter.toLowerCase())){
				mostrarReglaPorOrigen = true;
			} else if(element.rangeNodeSource != null && element.rangeNodeSource.name.toLowerCase().includes(sourceFilter.toLowerCase())){
				mostrarReglaPorOrigen = true;
			} else if(element.propertyValueSource != null && element.propertyValueSource.name.toLowerCase().includes(sourceFilter.toLowerCase())){
				mostrarReglaPorOrigen = true;
			}
		} else {
			mostrarReglaPorOrigen = true;
		}
		
		// Filtrado por elemento del esquema destino
		if(targetFilter != ""){
			if(element.domainClassTarget.name.toLowerCase().includes(targetFilter.toLowerCase())){
				mostrarReglaPorDestino = true;
			} else if(element.rangeClassTarget != null && element.rangeClassTarget.name.toLowerCase().includes(targetFilter.toLowerCase())){
				mostrarReglaPorDestino = true;
			} else if(element.propertyTarget != null && element.propertyTarget.name.toLowerCase().includes(targetFilter.toLowerCase())){
				mostrarReglaPorDestino = true;
			}
		} else {
			mostrarReglaPorDestino = true;
		}
		
		// Filtrado por clase de regla
		if($("#checkboxFiltroReglaClase").prop('checked') && element.tipo == TipoRegla.CLASE){
			mostrarReglaPorClase = true;
		}
		if($("#checkboxFiltroReglaPropiedad").prop('checked') && element.tipo == TipoRegla.PROPIEDAD){
			mostrarReglaPorClase = true;
		}
		if($("#checkboxFiltroReglaRelacion").prop('checked') && element.tipo == TipoRegla.RELACION){
			mostrarReglaPorClase = true;
		}
		
		// Según los filtros anteriores se muestra o no la regla
		if(mostrarReglaPorOrigen && mostrarReglaPorDestino && mostrarReglaPorClase){
			$("#plegable-regla-" + element.id).show();
		}
	});
};

<%@include file="../commons/taglibs.jsp" %>

<script type="text/javascript">

/**
 * Si el input de la etiqueta está vacío se deshabilita
 * el botón de creación de la regla, en caso contrario se habilita.
 */
function habilitarBotonCrearReglaPropiedad(){
	if($("#idInputEtiquetaCreacionGuiadaReglaPropiedad").val() == ""){
		deshabilitarBoton("botonCrearReglaPropiedad");
	} else {
		habilitarBoton("botonCrearReglaPropiedad");
	}
}

//Instancia de la creación guiada de una regla de propiedad
var creacionGuiadaReglaPropiedad = new Tour({
	smartPlacement: true,
	backdrop: true,
	backdropContainer: 'body',
	template: "<div class='popover tour' style='min-width:30%'>\
	    <div class='arrow'></div>\
	    <h3 class='popover-title creacionGuiada-titulo'></h3>\
	    <div class='popover-content'></div>\
	    <div style='padding-left: 14px; padding-right: 14px;'>\
		    <div class='container-fluid'>\
				<div class='row'>\
					<div class='col-lg-6 col-elem-creacionGuiada'>\
		    			<dl>\
			    			<div class='well-green'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.domainNode'/></dt><dd id='creacionGuiadaDomainNode' class='dd-20'> </dd>\
			    			</div>\
			    			<div class='well-green'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.propertyValueSource'/></dt><dd id='creacionGuiadaPropertyValueSource' class='dd-20'> </dd>\
			    			</div>\
		    			</dl>\
		    		</div>\ <%-- column --%>
		    		<div class='col-lg-6 col-elem-creacionGuiada'>\
		    			<dl>\
			    			<div class='well-lavender'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.domainClass'/></dt><dd id='creacionGuiadaDomainClass' class='dd-20'> </dd>\
			    			</div>\
			    			<div class='well-lavender'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/></dt><dd id='creacionGuiadaPropertyTarget' class='dd-20'> </dd>\
			    			</div>\
		    			</dl>\
		    		</div>\ <%-- column --%>
		    	</div>\ <%-- row --%>
		    </div>\ <%-- container --%>
		    <label id='labelInputEtiquetaCreacionGuiadaReglaPropiedad' style='width: 100%; display: none;'><spring:message code='mapeo.creacionGuiada.texto.etiqueta.titulo'/>\
		    	<input id='idInputEtiquetaCreacionGuiadaReglaPropiedad' onkeyup='habilitarBotonCrearReglaPropiedad();' type='text' placeholder='Etiqueta' style='width: 100%;'>\
		    	<label>- La etiqueta no debe quedar vacía</label>\
		    </label>\
		</div>\
	    <p id='errorEleccionCreacionGuiada' class='well-warning'></p>\
	    <div class='popover-navigation'>\
	    	<button id='botonAddElementoReglaPropiedad' style='margin-bottom: 5px; width:100%;' class='btn btn-success'><spring:message code='comunes.boton.add' /></button>\
	    	<br>\
	    	<div class='btn-group'>\
		        <button class='btn btn-default' data-role='prev'><spring:message code='comunes.boton.anterior' /></button>\
		        <button id='botonSiguienteReglaPropiedad' class='btn btn-primary' data-role='next'><spring:message code='comunes.boton.siguiente' /></button>\
		    </div>\
		    <button id='botonCrearReglaPropiedad' disabled='true' class='btn btn-success' style='margin-left: 5px;' data-role='end' onclick='mappingPropertyRule(); borrarRegla();'><spring:message code='comunes.boton.crear' /></button>\
	        <button id='botonFinalizarReglaPropiedad' class='btn btn-danger' data-role='end' onclick='borrarRegla();'><spring:message code='comunes.boton.cancelar' /></button>\
	    </div>\
	  </div>",
	backdropPadding: 4,
	steps: [
	{
	  element: "#sourceSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainNode.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainNode.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectDomainNode("botonAddElementoReglaPropiedad", "botonSiguienteReglaPropiedad", creacionGuiadaReglaPropiedad);
		  comprobarElementoSeleccionadoDomainNode("botonSiguienteReglaPropiedad");
		  mostrarElementosSeleccionados();
	  }
	},
	{
	  element: "#sourceSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.propertyValueSource.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.propertyValueSource.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectPropertyValueSource("botonAddElementoReglaPropiedad", "botonSiguienteReglaPropiedad", creacionGuiadaReglaPropiedad);
		  comprobarElementoSeleccionadoPropertyValueSource("botonSiguienteReglaPropiedad");
		  mostrarElementosSeleccionados();
	  }
	},
	{
	  placement: "left",
	  element: "#targetSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainClass.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainClass.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectDomainClass('botonAddElementoReglaPropiedad', "botonSiguienteReglaPropiedad", creacionGuiadaReglaPropiedad);
	  	  comprobarElementoSeleccionadoDomainClass("botonSiguienteReglaPropiedad");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  placement: "left",
	  element: "#targetSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.propertyTarget.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.propertyTarget.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectPropertyTarget('botonAddElementoReglaPropiedad', "botonSiguienteReglaPropiedad", creacionGuiadaReglaPropiedad);
	  	  comprobarElementoSeleccionadoPropertyTarget("botonSiguienteReglaPropiedad");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  orphan: true,
	  placement: "bottom",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.crear.regla.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.crear.regla.info" /><br><spring:message code="mapeo.creacionGuiada.texto.crear.regla.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  $("#labelInputEtiquetaCreacionGuiadaReglaPropiedad").show();
		  mostrarElementosSeleccionados();
		  ocultarBoton('botonAddElementoReglaPropiedad');
	  }
	}
	]
});

</script>
<%@include file="../commons/taglibs.jsp" %>

<script type="text/javascript">

//Instancia de la creación guiada de una regla de propiedad
var creacionGuiadaReglaRelacion = new Tour({
	smartPlacement: true,
	backdrop: true,
	backdropContainer: 'body',
	template: "<div class='popover tour' style='min-width:30%'>\
	    <div class='arrow'></div>\
	    <h3 class='popover-title creacionGuiada-titulo'></h3>\
	    <div class='popover-content'></div>\
	    <div style='padding-left: 14px;'>\
		    <div class='container-fluid'>\
				<div class='row'>\
					<div class='col-lg-6 col-elem-creacionGuiada'>\
		    			<dl>\
		    				<div class='dt-well'>\
		    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainNode'/></dt><dd id='creacionGuiadaDomainNode' class='dd-20'> </dd>\
		    				</div>\
		    				<div class='dt-well'>\
		    					<dt><spring:message code='mapeo.creacionGuiada.texto.rangeNodeSource'/></dt><dd id='creacionGuiadaRangeNodeSource' class='dd-20'> </dd>\
		    				</div>\
		    			</dl>\
		    		</div>\ <%-- column --%>
		    		<div class='col-lg-6 col-elem-creacionGuiada'>\
		    			<dl>\
		    				<div class='dt-well'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.domainClass'/></dt><dd id='creacionGuiadaDomainClass' class='dd-20'> </dd>\
			    			</div>\
			    			<div class='dt-well'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.rangeClassTarget'/></dt><dd id='creacionGuiadaRangeClassTarget' class='dd-20'> </dd>\
		    				</div>\
		    				<div class='dt-well'>\
			    				<dt><spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/></dt><dd id='creacionGuiadaPropertyTarget' class='dd-20'> </dd>\
			    			</div>\
			    		</dl>\
		    		</div>\ <%-- column --%>
		    	</div>\ <%-- row --%>
		    </div>\ <%-- container --%>
		</div>\
	    <p id='errorEleccionCreacionGuiada' class='well-warning'></p>\
	    <div class='popover-navigation'>\
	    	<button id='botonAddElementoReglaRelacion' style='margin-bottom: 5px; width:100%;' class='btn btn-success'><spring:message code='comunes.boton.add' /></button>\
	    	<br>\
	    	<div class='btn-group'>\
		        <button class='btn btn-default' data-role='prev'><spring:message code='comunes.boton.anterior' /></button>\
		        <button id='botonSiguienteReglaRelacion' class='btn btn-primary' data-role='next'><spring:message code='comunes.boton.siguiente' /></button>\
		    </div>\
		    <button id='botonCrearReglaRelacion' disabled='true' class='btn btn-success' style='margin-left: 5px;' data-role='end' onclick='mappingRelationRule(); borrarRegla();'><spring:message code='comunes.boton.crear' /></button>\
	        <button id='botonFinalizarReglaRelacion' class='btn btn-danger' data-role='end' onclick='borrarRegla();'><spring:message code='comunes.boton.cancelar' /></button>\
	    </div>\
	  </div>",
	backdropPadding: 4,
	steps: [
	{
	  element: "#sourceSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainNode.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainNode.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectDomainNode("botonAddElementoReglaRelacion", "botonSiguienteReglaRelacion", creacionGuiadaReglaRelacion);
		  comprobarElementoSeleccionadoDomainNode("botonSiguienteReglaRelacion");
		  mostrarElementosSeleccionados();
	  }
	},
	{
	  element: "#sourceSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.rangeNodeSource.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.rangeNodeSource.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectRangeNodeSource('botonAddElementoReglaRelacion', "botonSiguienteReglaRelacion", creacionGuiadaReglaRelacion);
	  	  comprobarElementoSeleccionadoRangeNodeSource("botonSiguienteReglaRelacion");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  placement: "left",
	  element: "#targetSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainClass.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainClass.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaClase) {
		  buttonAddClickSelectDomainClass('botonAddElementoReglaRelacion', "botonSiguienteReglaRelacion", creacionGuiadaReglaRelacion);
	  	  comprobarElementoSeleccionadoDomainClass("botonSiguienteReglaRelacion");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  placement: "left",
	  element: "#targetSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.rangeClassTarget.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.rangeClassTarget.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectRangeClassTarget('botonAddElementoReglaRelacion', "botonSiguienteReglaRelacion", creacionGuiadaReglaRelacion);
	  	  comprobarElementoSeleccionadoRangeClassTarget("botonSiguienteReglaRelacion");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  placement: "left",
	  element: "#targetSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.propertyTarget.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.propertyTarget.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  buttonAddClickSelectPropertyTarget('botonAddElementoReglaRelacion', "botonSiguienteReglaRelacion", creacionGuiadaReglaRelacion);
	  	  comprobarElementoSeleccionadoPropertyTarget("botonSiguienteReglaRelacion");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  orphan: true,
	  placement: "bottom",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.crear.regla.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.crear.regla.info" /><br><spring:message code="mapeo.creacionGuiada.texto.crear.regla.desc" /> ",
	  onShown: function (creacionGuiadaReglaPropiedad) {
		  mostrarElementosSeleccionados();
		  habilitarBotonCrearFinalizar('botonCrearReglaRelacion', 'botonFinalizarReglaRelacion');
		  ocultarBoton('botonAddElementoReglaRelacion');
	  }
	}
	]
});

</script>
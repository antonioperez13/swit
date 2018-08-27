<%@include file="../commons/taglibs.jsp" %>

<script type="text/javascript">

//Instancia de la creación guiada de una regla de clase
var creacionGuiadaReglaClase = new Tour({
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
	    				<div class='dt-well'>\
	    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainNode'/></dt><dd id='creacionGuiadaDomainNode' class='dd-20'> </dd>\
	    				</div>\
		    		</div>\ <%-- column --%>
		    		<div class='col-lg-6 col-elem-creacionGuiada'>\
		    			<dl>\
		    				<div class='dt-well'>\
		    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainClass'/></dt><dd id='creacionGuiadaDomainClass' class='dd-20'> </dd>\
		    				</div>\
		    			</dl>\
		    		</div>\ <%-- column --%>
		    	</div>\ <%-- row --%>
		    </div>\ <%-- container --%>
		</div>\
	    <p id='errorEleccionCreacionGuiada' class='well-warning'></p>\
	    <div class='popover-navigation'>\
	    	<button id='botonAddElementoReglaClase' style='margin-bottom: 5px; width:100%;' class='btn btn-success'><spring:message code='comunes.boton.add' /></button>\
	    	<br>\
	    	<div class='btn-group'>\
		        <button class='btn btn-default' data-role='prev'><spring:message code='comunes.boton.anterior' /></button>\
		        <button id='botonSiguienteReglaClase' class='btn btn-primary' data-role='next'><spring:message code='comunes.boton.siguiente' /></button>\
		    </div>\
		    <button id='botonCrearReglaClase' disabled='true' class='btn btn-success' style='margin-left: 5px;' data-role='end' onclick='mappingClassRule(); borrarRegla();'><spring:message code='comunes.boton.crear' /></button>\
	        <button id='botonFinalizarReglaClase' class='btn btn-danger' data-role='end' onclick='borrarRegla();'><spring:message code='comunes.boton.cancelar' /></button>\
	    </div>\
	  </div>",
	backdropPadding: 4,
	steps: [
	{
	  element: "#sourceSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainNode.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainNode.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaClase) {
		  buttonAddClickSelectDomainNode("botonAddElementoReglaClase", "botonSiguienteReglaClase", creacionGuiadaReglaClase);
		  comprobarElementoSeleccionadoDomainNode("botonSiguienteReglaClase");
		  mostrarElementosSeleccionados();
	  }
	},
	{
	  placement: "left",
	  element: "#targetSchemaDiv",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainClass.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.elegir.domainClass.desc" /><br><spring:message code="mapeo.creacionGuiada.texto.elegir.add.elemento.desc" /> ",
	  onShown: function (creacionGuiadaReglaClase) {
		  buttonAddClickSelectDomainClass('botonAddElementoReglaClase', "botonSiguienteReglaClase", creacionGuiadaReglaClase);
	  	  comprobarElementoSeleccionadoDomainClass("botonSiguienteReglaClase");
	  	  mostrarElementosSeleccionados();
	  }
	},
	{
	  orphan: true,
	  placement: "bottom",
	  title: "<spring:message code="mapeo.creacionGuiada.texto.crear.regla.titulo" />",
	  content: "<spring:message code="mapeo.creacionGuiada.texto.crear.regla.info" /><br><spring:message code="mapeo.creacionGuiada.texto.crear.regla.desc" /> ",
	  onShown: function (creacionGuiadaReglaClase) {
		  mostrarElementosSeleccionados();
		  habilitarBotonCrearFinalizar('botonCrearReglaClase', 'botonFinalizarReglaClase');
		  ocultarBoton('botonAddElementoReglaClase');
	  }
	}
	]
});

</script>
<%@include file="../commons/taglibs.jsp" %>

<script type="text/javascript">
	function eliminarRegla(botonEliminar){
		var divPlegableRegla = $(botonEliminar).closest("div:has([regla-id])")[0];
		var reglaId = divPlegableRegla.getAttribute("id").split("plegable-reglaId-")[1];
		
		// Elimina la regla de la página
		$(divPlegableRegla).animate({height:'0'});
		$(divPlegableRegla).animate({margin:'0'});
		$(divPlegableRegla).css("border", "0");
		setTimeout(function(){
			$(divPlegableRegla).remove();
		},1500);
		
		// Elimina la regla del servidor
		removeRuleById(reglaId);
	}
	
	function eliminarTodasReglas(){
		var divPlegables = $("[id^=plegable-reglaId-]");
		
		// Elimina todas las reglas de la página
		$(divPlegables).animate({height:'0'});
		$(divPlegables).animate({margin:'0'});
		$(divPlegables).css("border", "0");
		setTimeout(function(){
			$(divPlegables).remove();
		},1500);
		
		// Borra todas las reglas del servidor
		borrarReglasServidor();
	}
	
	function contraerTodasReglas(){
		$('[id^=plegable-regla-]').collapse('hide');
	}
	
	function expandirTodasReglas(){
		$('[id^=plegable-regla-]').collapse('show');
	}
</script>


<div class="container-fluid marco-representacion-reglas">
  <div class="panel-group">
  	<div id="plegable-reglaId-1" class="panel panel-default transicion">
		<%-- Botón desplegar regla + borrar regla --%>
		<div class="btn-group btn-group-justified" role="group" aria-label="Justified button group" regla-id="1">
			<div class=" btn-group" role="group" style="width:95%;">
				<button class="list-group-button-title btn btn-info collapsed" data-toggle="collapse" data-target="#plegable-regla-1" title="Ver/Ocultar regla">
					atom -> Atom
				</button>
			</div>
			<div class="btn-group" role="group" style="width:5%;">
				<button class="list-group-button-delete btn btn-danger" title="Borrar regla" onclick="eliminarRegla(this)">
					<i class="material-icons list-group-button-delete-icon">delete_sweep</i>
				</button>
			</div>
		</div>	
		
		<%-- Datos de la regla --%>
		<div id="plegable-regla-1" class='container-fluid collapse'>
			<div class='row' style="margin-top: 10px;">
				<div class='col-lg-6 col-elem-creacionGuiada'>
    				<div class='dt-well'>
    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainNode'/></dt><dd class='dd-20'>atom</dd>
    				</div>
	    		</div> <%-- column --%>
	    		<div class='col-lg-6 col-elem-creacionGuiada'>
	    			<dl>
	    				<div class='dt-well'>
	    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainClass'/></dt><dd class='dd-20'>Atom</dd>
	    				</div>
	    				<div class='dt-well'>
		    				<dt><spring:message code='mapeo.creacionGuiada.texto.rangeClassTarget'/></dt><dd class='dd-20'>Molecule</dd>
	    				</div>
	    				<div class='dt-well'>
		    				<dt><spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/></dt><dd class='dd-20'>has_atom</dd>
		    			</div>
	    			</dl>
	    		</div> <%-- column --%>
	    	</div> <%-- row --%>
	    </div> <%-- container --%>
    </div>
    
    
    
    <div id="plegable-reglaId-2" class="panel panel-default transicion">
		<%-- Botón desplegar regla + borrar regla --%>
		<div class="btn-group btn-group-justified" role="group" aria-label="Justified button group" regla-id="2">
			<div class=" btn-group" role="group" style="width:95%;">
				<button class="list-group-button-title btn btn-info collapsed" data-toggle="collapse" data-target="#plegable-regla-2" title="Ver/Ocultar regla">
					atom (x) -> Atom (X)
				</button>
			</div>
			<div class="btn-group" role="group" style="width:5%;">
				<button class="list-group-button-delete btn btn-danger" title="Borrar regla" onclick="eliminarRegla(this)">
					<i class="material-icons list-group-button-delete-icon">delete_sweep</i>
				</button>
			</div>
		</div>
      <div id="plegable-regla-2" class='container-fluid collapse'>
			<div class='row' style="margin-top: 10px;">
				<div class='col-lg-6 col-elem-creacionGuiada'>
    				<div class='dt-well'>
    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainNode'/></dt><dd id='creacionGuiadaDomainNode' class='dd-20'>atom</dd>
    				</div>
	    		</div> <%-- column --%>
	    		<div class='col-lg-6 col-elem-creacionGuiada'>
	    			<dl>
	    				<div class='dt-well'>
	    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainClass'/></dt><dd id='creacionGuiadaDomainClass' class='dd-20'>Atom</dd>
	    				</div>
	    				<div class='dt-well'>
		    				<dt><spring:message code='mapeo.creacionGuiada.texto.rangeClassTarget'/></dt><dd id='creacionGuiadaRangeClassTarget' class='dd-20'>Molecule</dd>
	    				</div>
	    				<div class='dt-well'>
		    				<dt><spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/></dt><dd id='creacionGuiadaPropertyTarget' class='dd-20'>has_atom</dd>
		    			</div>
	    			</dl>
	    		</div> <%-- column --%>
	    	</div> <%-- row --%>
	    </div> <%-- container --%>
    </div>
    
    
    <div id="plegable-reglaId-3" class="panel panel-default transicion">
		<%-- Botón desplegar regla + borrar regla --%>
		<div class="btn-group btn-group-justified" role="group" aria-label="Justified button group" regla-id="3">
			<div class=" btn-group" role="group" style="width:95%;">
				<button class="list-group-button-title btn btn-info collapsed" data-toggle="collapse" data-target="#plegable-regla-3" title="Ver/Ocultar regla">
					atom (molecule, x) -> Atom (X, has_atom)
				</button>
			</div>
			<div class="btn-group" role="group" style="width:5%;">
				<button class="list-group-button-delete btn btn-danger" title="Borrar regla" onclick="eliminarRegla(this)">
					<i class="material-icons list-group-button-delete-icon">delete_sweep</i>
				</button>
			</div>
		</div>
      <div id="plegable-regla-3" class='container-fluid collapse'>
			<div class='row' style="margin-top: 10px;">
				<div class='col-lg-6 col-elem-creacionGuiada'>
    				<div class='dt-well'>
    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainNode'/></dt><dd id='creacionGuiadaDomainNode' class='dd-20'>atom</dd>
    				</div>
	    		</div> <%-- column --%>
	    		<div class='col-lg-6 col-elem-creacionGuiada'>
	    			<dl>
	    				<div class='dt-well'>
	    					<dt><spring:message code='mapeo.creacionGuiada.texto.domainClass'/></dt><dd id='creacionGuiadaDomainClass' class='dd-20'>Atom</dd>
	    				</div>
	    				<div class='dt-well'>
		    				<dt><spring:message code='mapeo.creacionGuiada.texto.rangeClassTarget'/></dt><dd id='creacionGuiadaRangeClassTarget' class='dd-20'>Molecule</dd>
	    				</div>
	    				<div class='dt-well'>
		    				<dt><spring:message code='mapeo.creacionGuiada.texto.propertyTarget'/></dt><dd id='creacionGuiadaPropertyTarget' class='dd-20'>has_atom</dd>
		    			</div>
	    			</dl>
	    		</div> <%-- column --%>
	    	</div> <%-- row --%>
	    </div> <%-- container --%>
    </div>
  </div>
</div>

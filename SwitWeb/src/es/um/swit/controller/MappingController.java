package es.um.swit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import commons.reglas.Regla;
import commons.tree.Node;
import commons.tree.NodeOwl;
import es.um.swit.beans.FicherosEsquemasBean;
import es.um.swit.constantes.ConstantesCadenas;
import es.um.swit.constantes.ConstantesTexto;
import es.um.swit.objetos.CatalogoReglas;
import es.um.swit.utils.AjaxResponseBody;
import es.um.swit.utils.Error;
import es.um.swit.utils.FilesUtils;

@Controller
@SessionAttributes("CatalogoReglas")
public class MappingController {
	
	private static Logger logger = LogManager
	        .getLogger(MappingController.class);
	
	
	@RequestMapping(value = "/mappingPrincipal", method = RequestMethod.GET)
    public ModelAndView displayUploadForm(HttpServletRequest request, ModelMap model, String outputFileType) {
		logger.debug("displayUploadForm" + ConstantesTexto.START);
        ModelAndView view = new ModelAndView("mapping/mappingPrincipal");
    	
        FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
        
        // Se establece el tipo de fichero de salida
        feb.setOutputFileType(outputFileType);
        
        Error errores = FilesUtils.parsearFicheros(feb);
        
        if(!errores.hasErrors()) {
        	request.getSession().setAttribute("sourceTree", feb.getSourceTree().treeToHtmlList(4));
        	request.getSession().setAttribute("targetTree", feb.getTargetTree().treeToHtmlList(4));
        	
//        	SchemaFilesUploadController.replaceFicherosEsquemasBeanFromSession(request, feb);
        } else {
        	for(String error: errores.getDescError()) {
            	logger.error("- " + error);
            }
        	// TODO Redirigir a pantalla de carga de ficheros si falla el parseo y mostrar el error
        	model.addAttribute(ConstantesTexto.ERRORS, errores.getDescError());
        	
        	view = SchemaFilesUploadController.schemaFilesUploadView(model);
        	
        	logger.debug("displayUploadForm" + ConstantesTexto.ERROR_END);
        }
		
        view.addAllObjects(model);
        
        logger.debug("displayUploadForm" + ConstantesTexto.END);
    	return view;
    }
	
	/**
	 * Recibe la petición para mostrar la página de mapeos. Redirige al método que procesa la petición. 
	 * *Necesaria para el patrón POST/REDIRECT/GET.
	 * @param request
	 * @param model
	 * @param outputFileType
	 * @return
	 */
	@RequestMapping(value = "/mappingPrincipal", method = RequestMethod.POST)
    public String displayUploadFormPost(HttpServletRequest request, ModelMap model, String outputFileType) {
    	return "redirect:/mappingPrincipal.html?outputFileType="+outputFileType;
    }
	
	
	/**
	 * Inicializa el catálogo de reglas.
	 * @return
	 */
	@ModelAttribute("CatalogoReglas")
	private CatalogoReglas getCatalogoReglas() {
		return new CatalogoReglas();
	}
	
	/**
	 * 
	 * @param regla
	 * @param request
	 * @param reglas
	 * @return
	 */
	@RequestMapping(value = "/mappingRule")
    public ResponseEntity<AjaxResponseBody> mappingRule(@RequestBody Regla regla, HttpServletRequest request, 
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("mappingRule" + ConstantesTexto.START);
		
		// TODO Quitar tras las pruebas
		FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
		List<NodeOwl> nodeOwl = ((NodeOwl) feb.getTargetTree()).getChildren();
		feb.getTargetTree().getNodeFromRoute("Root/Modifier/ChemicalProperties/Chirality/chirality/");
		Node nodo =  feb.getTargetTree().getNodeById(100);
		// ////////////////////////////
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		// Comprobar si la regla ya existía
		Regla reglaRecuperada = reglas.recuperarRegla(regla);
		if(reglaRecuperada == null) {
			// Añadimos la nueva regla
			regla.setId();
			reglas.addRegla(regla);
			
			result.setCode("201");
			result.setMsg(regla.getId().toString());
			result.setResult(regla.toHtmlString());
		} else { // Si ya existe la regla no se añade
			result.setCode("202");
			result.setMsg("Already exists");
			result.setResult(reglaRecuperada.toHtmlString());
		}
		
		
		/* INI -DEBUG */
		StringBuilder reglasTotal = new StringBuilder("\n\n");
		int numRegla = 1;
		for(Regla r: reglas.getAllReglas()) {
			reglasTotal.append("\tRegla " + numRegla + " -----------------\n")
					   .append(r.toString())
					   .append("------------------------------------------------------\n");
			numRegla++;
		}
		reglasTotal.append("\n");
		logger.debug(reglasTotal);
		/* FIN - DEBUG */
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("mappingRule" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	/**
	 * 
	 * @param idRegla Identificador de la regla que se quiere eliminar
	 * @param request
	 * @param reglas
	 * @return
	 */
	@RequestMapping(value = "/removeRuleById")
    public ResponseEntity<AjaxResponseBody> removeRuleById(@RequestBody String idRegla, HttpServletRequest request, 
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("removeRuleById" + ConstantesTexto.START);
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		Regla reglaEliminada = reglas.removeReglaPorId(Integer.parseInt(idRegla));
		
		if(reglaEliminada != null) {
			result.setCode("210");
			result.setMsg("Regla eliminada");
			result.setResult(reglaEliminada.toHtmlString());
		} else {
			result.setCode("410");
			result.setMsg("Regla no encontrada");
			result.setResult("No se ha encontrado la regla, no se ha eliminado");
		}
		
		/* INI -DEBUG */
		logger.debug("removeRuleById - Regla borrada");
		/* FIN - DEBUG */
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("removeRuleById" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	/**
	 * 
	 * @param request
	 * @param reglas
	 * @return
	 */
	@RequestMapping(value = "/removeAllRules")
    public ResponseEntity<AjaxResponseBody> removeAllRules(HttpServletRequest request, 
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("removeAllRules" + ConstantesTexto.START);
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		int numReglas = reglas.getAllReglas().size();
		reglas.removeAllReglas();
		
		result.setCode("211");
		result.setMsg("Reglas eliminadas");
		result.setResult(numReglas + " reglas eliminadas.");
		
		/* INI -DEBUG */
		logger.debug("removeAllRules - Reglas borradas");
		/* FIN - DEBUG */
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("removeAllRules" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
}

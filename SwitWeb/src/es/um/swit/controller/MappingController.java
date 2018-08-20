package es.um.swit.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        	
        } else {
        	for(String error: errores.getDescError()) {
            	logger.error("- " + error);
            }
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
			result.setMsg("La regla ya existe");
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
			result.setResult("Eliminada: <br>" + reglaEliminada.toHtmlString());
		} else {
			result.setCode("410");
			result.setMsg("Regla no encontrada");
			result.setResult("No se ha encontrado la regla, no se ha eliminado");
		}
		
		/* INI -DEBUG */
		logger.debug("removeRuleById - Regla borrada - \n{}", reglaEliminada.toString());
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
		result.setResult("Reglas eliminadas: " + numReglas);
		
		/* INI -DEBUG */
		logger.debug("removeAllRules - Reglas borradas");
		/* FIN - DEBUG */
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("removeAllRules" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/saveMappingsToFile")
    public ResponseEntity<AjaxResponseBody> saveMappingsToFile(HttpServletRequest request, 
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("saveMappingsToFile" + ConstantesTexto.START);
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		try {
			FileOutputStream f = new FileOutputStream(new File("E:\\Antonio\\Escritorio\\myObjects.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
	
			// Write objects to file
		
			o.writeObject(reglas);
			
			o.close();
			f.close();
			
			
			FileInputStream fi = new FileInputStream(new File("E:\\Antonio\\Escritorio\\myObjects.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			CatalogoReglas pr1 = (CatalogoReglas) oi.readObject();
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		result.setCode("200");
		result.setMsg("Fichero guardado");
		result.setResult("Fichero guardado");

		
		/* INI -DEBUG */
		logger.debug("saveMappingsToFile - Regla borrada - \n{}");
		/* FIN - DEBUG */
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("saveMappingsToFile" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/downloadMappingsFile")
    public void downloadMappingsFile(HttpServletRequest request, HttpServletResponse response,
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("downloadMappingsFile" + ConstantesTexto.START);
		
		
		response.setContentType("application/zip");
	 	response.setHeader("Content-Disposition", "attachment;filename=" + "mappings.zip");
		
		try {
			ObjectOutputStream o = new ObjectOutputStream(response.getOutputStream());
			
			// Write objects to file
			o.writeObject(reglas);
			
			o.close();
			
			FileInputStream fi = new FileInputStream(new File("E:\\mappings.zip"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			CatalogoReglas pr1 = (CatalogoReglas) oi.readObject();
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		result.setCode("200");
		result.setMsg("Regla eliminada");
		result.setResult("Eliminada: <br>");
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("downloadMappingsFile" + ConstantesTexto.END);
	    
	    //return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/loadMappingsFile")
    public void loadMappingsFile(HttpServletRequest request,
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("loadMappingsFile" + ConstantesTexto.START);
		
		try {
			FileInputStream fi = new FileInputStream(new File("E:\\mappings.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			CatalogoReglas reglasCargadas = (CatalogoReglas) oi.readObject();
			
			// Se comprueban las reglas cargadas desde el fichero subido y se añaden
			cargarReglasFichero(reglas, reglasCargadas, request);
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		result.setCode("200");
		result.setMsg("Regla eliminada");
		result.setResult("Eliminada: <br>");
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("loadMappingsFile" + ConstantesTexto.END);
	    
	    //return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	/**
	 * Dado un catalogo de reglas cargadas se comprueban si hay duplicadas con las existentes y
	 * si hay reglas que no sean posibles según los esquemas de datos que se estén usando.
	 * @param reglasExistentes
	 * @param reglasFichero
	 * @param request 
	 */
	private void cargarReglasFichero(CatalogoReglas reglasExistentes, CatalogoReglas reglasFichero, HttpServletRequest request) {
		// Este catálogo será el modificado, eliminando aquellas reglas duplicadas o inválidas
		CatalogoReglas reglasCargadas = new CatalogoReglas(reglasFichero);
		
		// Se eliminan las reglas duplicadas
		comprobarReglasDuplicadas(reglasExistentes, reglasCargadas);
		
		// Se eliminan las reglas que no puedan existir por los esquemas de datos usados
		comprobarElementosReglas(reglasCargadas, request);
	}

	/**
	 * Comprueba que los elementos de las reglas del catálogo existan en los esquemas de datos usados para el mapeo.
	 * Aquellas reglas con elementos que no existan en los esquemas se eliminarán.
	 * @param reglasCargadas
	 * @param request
	 */
	private void comprobarElementosReglas(CatalogoReglas reglasCargadas, HttpServletRequest request) {
		FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
		
		// TODO Implementar comprobarElementosReglas()
	}

	/**
	 * Recorre las reglas de los dos catálogos y elimina del catálogo de reglas cargadas aquellas que ya 
	 * existan en el otro catálogo.
	 * @param reglasExistentes
	 * @param reglasCargadas
	 */
	private void comprobarReglasDuplicadas(CatalogoReglas reglasExistentes, CatalogoReglas reglasCargadas) {
		for(Regla reglaExistente : reglasExistentes.getAllReglas()) {
			Iterator<Regla> reglasIterator = reglasCargadas.getAllReglas().iterator();
			while (reglasIterator.hasNext()) {
				// Si la regla cargada es igual a una regla existente se borra la cargada del catálogo
				if(reglasIterator.next().equals(reglaExistente)) {
					reglasIterator.remove();
				}
			}
		}
		
	}
}

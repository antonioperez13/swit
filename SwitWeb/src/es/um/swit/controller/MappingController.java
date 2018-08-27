package es.um.swit.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import commons.reglas.CatalogoReglas;
import commons.reglas.Regla;
import commons.tree.Node;
import commons.tree.NodeOwl;
import es.um.swit.backup.service.GestorRegistros;
import es.um.swit.backup.service.RespuestaGestorRegistros;
import es.um.swit.backup.service.RespuestaRecuperarRegistro;
import es.um.swit.beans.FicherosEsquemasBean;
import es.um.swit.beans.UploadedFile;
import es.um.swit.constantes.ConstantesCadenas;
import es.um.swit.constantes.ConstantesTexto;
import es.um.swit.utils.AjaxResponseBody;
import es.um.swit.utils.Error;
import es.um.swit.utils.FilesUtils;
import es.um.swit.ws.GestorRegistrosWS;

@Controller
@SessionAttributes("CatalogoReglas")
public class MappingController {
	
	private static Logger logger = LogManager
	        .getLogger(MappingController.class);
	
	/**
	 * Inicializa el catálogo de reglas.
	 * @return
	 */
	@ModelAttribute("CatalogoReglas")
	private CatalogoReglas getCatalogoReglas() {
		return new CatalogoReglas();
	}
	
	/**
	 * Inicializa la vista de creación de mapeos.
	 * Transforma los ficheros añadidos en la página anterior y los representa en forma de lista
	 * desplegable para su uso.
	 * @param request
	 * @param model
	 * @param outputFileType
	 * @return
	 */
	@RequestMapping(value = "/mappingPrincipal", method = RequestMethod.GET)
    public ModelAndView displayUploadForm(HttpServletRequest request, ModelMap model, String outputFileType, 
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("displayUploadForm" + ConstantesTexto.START);
        ModelAndView view = new ModelAndView("mapping/mappingPrincipal");
    	
        FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
        
        // Se reinician el catalogo de reglas y la asignacion de identificadores
        reglas.removeAllReglas();
        Regla.reiniciarAsignacionId();
        
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
	
////////////////////////////////////////////////////////////////////////////////////////////////////
///// CREACION Y MANIPULACION DE REGLAS
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Crea una regla y la añade al catálogo.
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
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
///// GUARDAR MAPEOS CREADOS
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Crea un fichero con los mapeos creados y lo descarga en el equipo del usuario.
	 * @param request
	 * @param response
	 * @param reglas
	 */
	@RequestMapping(value = "/downloadMappingsFile")
    public void downloadMappingsFile(HttpServletRequest request, HttpServletResponse response,
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("downloadMappingsFile" + ConstantesTexto.START);
		
		
		response.setContentType("text/plain");
	 	response.setHeader("Content-Disposition", "attachment;filename=" + "mappings.txt");
		
		try {
			ObjectOutputStream o = new ObjectOutputStream(response.getOutputStream());
			
			// Write objects to file
			o.writeObject(reglas);
			
			o.close();
			
			// TODO Eliminar tras las pruebas
			File file = new File("E:\\Antonio\\Escritorio\\mappings.txt");
			FileInputStream fi = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fi);

			byte[] bFile = Files.readAllBytes(file.toPath());
			
			GestorRegistros wsInstance = GestorRegistrosWS.getWsInstance();
			
			RespuestaGestorRegistros respuesta = wsInstance.registrarMappings(bFile);
			
			// Read objects
			CatalogoReglas pr1 = (CatalogoReglas) oi.readObject();
			
			String sha1 = FilesUtils.calcSHA1(file);
			oi.close();
			fi.close();
			// Fin TODO Eliminar
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		result.setCode("200");
		result.setMsg("Ok");
		result.setResult("Fichero descargado");
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("downloadMappingsFile" + ConstantesTexto.END);
	    
	    //return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	/**
	 * Usa el WS para guardar los mapeos creados.
	 * @param request
	 * @param reglas
	 * @return
	 */
	@RequestMapping(value = "/saveBackupMappingsFile")
    public ResponseEntity<AjaxResponseBody> saveBackupMappingsFile(@RequestBody String idRegistro, 
    		HttpServletRequest request, @ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("saveBackupMappingsFile" + ConstantesTexto.START);
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(bos);
			
			// Write objects to file
			o.writeObject(reglas);
			o.flush();
			
			// Se pasa a un array de bytes para guardarlo
			byte[] mappings = bos.toByteArray();
			
			o.close();
			bos.close();
			
			RespuestaGestorRegistros respuesta;
			
			// Llamada al WS de respaldo
			GestorRegistros wsInstance = GestorRegistrosWS.getWsInstance();
			if(idRegistro.isEmpty()) {
				respuesta = wsInstance.registrarMappings(mappings);
			} else {
				respuesta = wsInstance.reemplazarMappings(mappings, idRegistro);
			}
			
			if(respuesta.getIdCodigo() == 0) {
				result.setCode("200");
				result.setMsg("Ok");
				result.setResult(respuesta.getIdRegistro());
			} else {
				result.setCode("402");
				result.setMsg("Error al guardar el fichero en la nube");
				result.setResult("Error");
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
			result.setCode("401");
			result.setMsg("Error al guardar el fichero en la nube");
			result.setResult("Error");
		}	
		
		
		// Prepara y envía la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("saveBackupMappingsFile" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
///// CARGAR MAPEOS CREADOS
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Carga las reglas desde un fichero que proporciona el usuario.
	 * @param request
	 * @param uploadedFile
	 * @param reglas
	 * @return
	 */
	@RequestMapping(value = "/loadMappingsFile")
    public ResponseEntity<AjaxResponseBody> loadMappingsFile(HttpServletRequest request, @ModelAttribute UploadedFile uploadedFile,
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("loadMappingsFile" + ConstantesTexto.START);
		
		File mappingsFile = saveUploadedFile(request, uploadedFile);
		
		// Prepara la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		AjaxResponseBody result = new AjaxResponseBody();
		
		
		if(mappingsFile != null) {
			try {
				FileInputStream fi = new FileInputStream(mappingsFile);
				
				// Se recuperan las reglas del fichero
				recuperarReglasFichero(request, reglas, result, fi);
				
				fi.close();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Se elimina el fichero temporal
			mappingsFile.delete();
			
			result.setCode("200");
			result.setMsg("Fichero de reglas cargado correctaemnte");
			//	El atributo result se establece arriba
		} else {
			result.setCode("400");
			result.setMsg("Error en la carga del fichero");
			result.setResult("Error en la carga del fichero");
			return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
		}
		
		
	    
	    logger.debug("loadMappingsFile" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
	/**
	 * Carga las reglas que haya en el fichero recuperado desde el WS según el identificador de registro.
	 * @param idRegistro
	 * @param request
	 * @param reglas
	 * @return
	 */
	@RequestMapping(value = "/retrieveBackupMappingsFile")
    public ResponseEntity<AjaxResponseBody> retrieveBackupMappingsFile(@RequestBody String idRegistro,
    		HttpServletRequest request, @ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("retrieveBackupMappingsFile" + ConstantesTexto.START);
		
		// Prepara la respuesta
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		AjaxResponseBody result = new AjaxResponseBody();
		
		// Llamada al WS de respaldo
		GestorRegistros wsInstance = GestorRegistrosWS.getWsInstance();
		RespuestaRecuperarRegistro respuesta = wsInstance.recuperarMappings(idRegistro.toUpperCase());
		
		if(respuesta.getIdCodigo() == 0) {
			try {
				ByteArrayInputStream bia = new ByteArrayInputStream(respuesta.getMappingsFile());
				
				// Se recuperan las reglas del fichero
				recuperarReglasFichero(request, reglas, result, bia);
				
				bia.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				
				result.setCode("400");
				result.setMsg("Error en la carga del fichero");
				result.setResult("Error en la carga del fichero");
				
				logger.debug("retrieveBackupMappingsFile" + ConstantesTexto.ERROR_END);
			}
			
			result.setCode("200");
			result.setMsg("Fichero de reglas de respaldo cargado correctaemnte");
			//	El atributo result se establece arriba
		} else {
			result.setCode("400");
			result.setMsg("Error en la carga del fichero");
			result.setResult("Error en la carga del fichero");
			return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.NOT_ACCEPTABLE);
		}
		
		
	    
	    logger.debug("retrieveBackupMappingsFile" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }

	/**
	 * Recupera las reglas de un fichero.
	 * @param request
	 * @param reglas
	 * @param result
	 * @param contenidoFichero
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws JsonProcessingException
	 */
	private void recuperarReglasFichero(HttpServletRequest request, CatalogoReglas reglas, AjaxResponseBody result,
			InputStream contenidoFichero) throws IOException, ClassNotFoundException, JsonProcessingException {
		ObjectInputStream oi = new ObjectInputStream(contenidoFichero);

		// Read objects
		CatalogoReglas reglasCargadas = (CatalogoReglas) oi.readObject();
		
		// Se comprueban las reglas cargadas desde el fichero subido y se añaden
		cargarReglasFichero(reglas, reglasCargadas, request);
		
		ObjectMapper mapper = new ObjectMapper();
		String reglasJson = mapper.writeValueAsString(reglas.getAllReglas());
		
		// Se pasan las reglas cargadas a la vista
		result.setResult(reglasJson);
		
		oi.close();
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
///// UTILES
////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Guarda el fichero en una ruta temporal.
	 * @param request
	 * @param uploadedFile
	 * @return
	 */
	private File saveUploadedFile(HttpServletRequest request, UploadedFile uploadedFile) {
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
        String fileName = multipartFile.getOriginalFilename();
        
        // Crea la carpeta de subidas en el servidor
        String realPathtoUploads =  request.getServletContext().getRealPath(FilesUtils.getTempRoute());
        if(! new File(realPathtoUploads).exists()){
            new File(realPathtoUploads).mkdir();
        }
        
        // Guarda el fichero subido en la carpeta
        File file = null;
        try {
            file = new File(realPathtoUploads, fileName);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return file;
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
		List<Regla> reglasCargadas = new ArrayList<>(reglasFichero.getAllReglas());
		
		// Se eliminan las reglas duplicadas
		comprobarReglasDuplicadas(reglasExistentes, reglasCargadas);
		
		// Se eliminan las reglas que no puedan existir por los esquemas de datos usados
		comprobarElementosReglas(reglasCargadas, request);
		
		// Se añaden todas las reglas válidas
		reglasExistentes.addAllReglasRestartIds(reglasCargadas);
	}
	
	/**
	 * Recorre las reglas de las dos colecciones y elimina de la lista de reglas cargadas aquellas que ya 
	 * existan en el otro catálogo.
	 * @param reglasExistentes
	 * @param reglasCargadas
	 */
	private void comprobarReglasDuplicadas(CatalogoReglas reglasExistentes, List<Regla> reglasCargadas) {
		for(Regla reglaExistente : reglasExistentes.getAllReglas()) {
			Iterator<Regla> reglasIterator = reglasCargadas.iterator();
			while (reglasIterator.hasNext()) {
				// Si la regla cargada es igual a una regla existente se borra la cargada del catálogo
				if(reglasIterator.next().equals(reglaExistente)) {
					reglasIterator.remove();
				}
			}
		}
	}

	/**
	 * Comprueba que los elementos de las reglas de la lista existan en los esquemas de datos usados para el mapeo.
	 * Aquellas reglas con elementos que no existan en los esquemas se eliminarán.
	 * @param reglasCargadas
	 * @param request
	 */
	private void comprobarElementosReglas(List<Regla> reglasCargadas, HttpServletRequest request) {
		FicherosEsquemasBean feb = (FicherosEsquemasBean) request.getSession().getAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION);
		
		// TODO Implementar comprobarElementosReglas()
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// TODO TEST METHOD ///////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/saveMappingsToFile")
    public ResponseEntity<AjaxResponseBody> saveMappingsToFile(HttpServletRequest request, 
    		@ModelAttribute("CatalogoReglas") CatalogoReglas reglas) {
		logger.debug("saveMappingsToFile" + ConstantesTexto.START);
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		try {
			File file = new File("E:\\Antonio\\Escritorio\\myObjects.txt");
			FileOutputStream f = new FileOutputStream(file);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			byte[] bFile = Files.readAllBytes(file.toPath());
	
			// Write objects to file
		
			o.writeObject(reglas);
			
			o.close();
			f.close();
			
			FileInputStream fi = new FileInputStream(new File("E:\\Antonio\\Escritorio\\myObjects.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			CatalogoReglas pr1 = (CatalogoReglas) oi.readObject();
			//////////////////////////////////////////////////////////////
			
		} catch (IOException | ClassNotFoundException e) {
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
	// TEST METHOD END ////////////////////////////////////////////////////////////////////////////////////////////////
}

package es.um.swit.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import commons.reglas.Regla;
import es.um.swit.constantes.ConstantesTexto;
import es.um.swit.utils.AjaxResponseBody;

@Controller
public class AjaxController {
	
	
	
	private static Logger logger = LogManager
	        .getLogger(AjaxController.class);
	
	
	@RequestMapping(value = "/mapearPar")
    public ResponseEntity<AjaxResponseBody> mapearPar(@RequestBody Regla regla) {
		logger.debug("mapearPar" + ConstantesTexto.START);
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		result.setCode("200");
		result.setMsg("OK");
		result.setResult(regla.getDomainNodeSource().getName() + " <-> " + regla.getDomainClassTarget().getName());
		
		
		
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    logger.debug("mapearPar" + ConstantesTexto.END);
	    
	    return new ResponseEntity<AjaxResponseBody>(result, httpHeaders, HttpStatus.OK);
    }
	
}

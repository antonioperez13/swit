package es.um.swit.controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExecConfigurationController {
	
	private static final String CONFIG_FILE_LOCATION = "/config/execConfigOptions.json";
	
	private static final String SPRING_MESSAGES_REGEX_PATTERN = "\\\"<spring:message code='(.+?)'.*\\\"";
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/execConfiguration")
	public ModelAndView execConfiguration(ModelMap model, HttpServletRequest request, Locale locale) {
		
		ModelAndView view = new ModelAndView("execConfig/execConfig");
		
		File jsonFile = new File(request.getServletContext().getRealPath(File.separator) + CONFIG_FILE_LOCATION);
		
		String jsonString = "{}";
		
		try {
			jsonString = FileUtils.readFileToString(jsonFile);
			jsonString = jsonString.replaceAll("\"", "\\\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Objetos necesarios para traducir los códigos de spring message a los mensajes finales
		Pattern pattern = Pattern.compile(SPRING_MESSAGES_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(jsonString);
        StringBuffer stringBuffer = new StringBuffer();
        
		try {
			// Mientras hayan ocurrencias de códigos spring message
			while (matcher.find()) {
				// Nos quedamos con el código de mensaje
				String messageCode = matcher.group(1);

				// Recuperamos el mensaje del fichero de properties
				String msg = messageSource.getMessage(messageCode, null, locale);

				// Reemplazamos y añadimos al buffer
				matcher.appendReplacement(stringBuffer, "\"" + msg + "\"");
			}
			
			// Añadimos el resto del texto
			matcher.appendTail(stringBuffer);
		} catch (Exception e) {
			matcher.appendReplacement(stringBuffer, "<Error>");
		}
		
		// Mensaje de error en caso de que falten por completar campos obligatorios
		String errorCamposObligatorios = messageSource.getMessage("configurador.mensaje.error.campos.obligatorios", null, locale);
		
		model.addAttribute("errorCamposObligatorios", errorCamposObligatorios);
		model.addAttribute("jsonString", stringBuffer.toString());
		view.addAllObjects(model);
		
		return view;
	}
	
}

package es.um.swit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InitInformationController {
	
	@RequestMapping("/index")
	public static ModelAndView mostrarIndex() {
		
		ModelAndView view = new ModelAndView("index");
		
		return view;
	}
	
	@RequestMapping("/schemaUploadStart")
	public static ModelAndView schemaUploadStart(ModelMap model) {
		
		ModelAndView view = SchemaFilesUploadController.schemaFilesUploadView(model);
		
		return view;
	}
	
}

package es.um.swit.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import es.um.swit.beans.FicherosEsquemasBean;
import es.um.swit.beans.UploadedFile;
import es.um.swit.constantes.ConstantesCadenas;
import es.um.swit.constantes.ConstantesTexto;
import es.um.swit.constantes.FileUploadConstants;
import es.um.swit.enums.TipoEsquema;
import es.um.swit.enums.TipoFichero;
import es.um.swit.utils.ControllerUtils;
import es.um.swit.utils.FilesUtils;

@Controller
public class SchemaFilesUploadController {
	
	private static Logger logger = LogManager
	        .getLogger(SchemaFilesUploadController.class);
	
	@RequestMapping("schemaFilesUpload")
	public static ModelAndView schemaFilesUploadView(ModelMap model) {
		
		ModelAndView view = new ModelAndView("schemaUpload/schemaFilesUpload");
		
		model.addAttribute("ficherosEsquemas", new FicherosEsquemasBean());
		
		view.addAllObjects(model);
		
		return view;
	}
	
	@RequestMapping("/uploadXsd")
    public void saveXsdFile(HttpServletRequest request,
            @ModelAttribute UploadedFile uploadedFile,
            BindingResult bindingResult, Model model) {
		
		logger.debug("uploadXsd" + ConstantesTexto.START);
 
		// Guarda el fichero subido
        File file = saveUploadedFile(request, uploadedFile);
        
        // Obtenido el fichero se le da tratamiento a los datos
        prepararFichero(request, model, file,
        		FileUploadConstants.XSD_EXTENSION, TipoFichero.XSD, TipoEsquema.ORIGEN);
        
        logger.debug("uploadXsd" + ConstantesTexto.END);
    }
	
	@RequestMapping("/uploadOwl")
    public void saveOwlFile(HttpServletRequest request,
            @ModelAttribute UploadedFile uploadedFile,
            BindingResult bindingResult, Model model) {
		
		logger.debug("uploadOwl" + ConstantesTexto.START);
 
		// Guarda el fichero subido
        File file = saveUploadedFile(request, uploadedFile);
        
        // Obtenido el fichero se le da tratamiento a los datos
        prepararFichero(request, model, file,
        		FileUploadConstants.OWL_EXTENSION, TipoFichero.OWL, TipoEsquema.DESTINO);
        
        logger.debug("uploadOwl" + ConstantesTexto.END);
    }
	
	/**
	 * Guarda el fichero en una ruta temporal.
	 * @param request
	 * @param uploadedFile
	 * @return
	 */
	private File saveUploadedFile(HttpServletRequest request, UploadedFile uploadedFile) {
		MultipartFile multipartFile = uploadedFile.getMultipartFile();
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        
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
	 * Si el archivo no es null se comprueba su extensión: si es la misma que la indicada como parámetro se establecen los
	 * atributos correspondientes en el bean que se usa en la vista; si es distinta se añade el nombre del fichero vacío al bean
	 * para distinguir que se ha producido un error. El bean se termina añadiendo a sesión y al modelo.
	 * Si el archivo era null no se hace nada.
	 * 
	 * @param request
	 * @param model
	 * @param archivo
	 * @param extension
	 * @param tipoFichero
	 */
	private void prepararFichero(HttpServletRequest request, Model model, File archivo, String extension,
			TipoFichero tipoFichero, TipoEsquema tipoEsquema) {
		if(archivo != null) {
	        // Se recupera, si existe, el bean de sesión
	    	FicherosEsquemasBean feb = ControllerUtils.getFicherosEsquemasBeanFromSession(request);
	    	
	    	/* Se comprueba la extensión del fichero y si es la correcta se guarda dicho fichero.
	    	 * Si no, se establece su nombre como vacío para indicar el error */
	    	if(tipoEsquema == TipoEsquema.ORIGEN) {
	    		establecerBeanDatosFicheroOrigen(archivo, extension, tipoFichero, feb);
	    	} else {
	    		establecerBeanDatosFicheroDestino(archivo, extension, tipoFichero, feb);
	    	}
			
			// Se añade el bean al modelo para la vista y se guarda en sesión
			request.getSession().setAttribute(ConstantesCadenas.FICHEROS_ESQUEMAS_BEAN_SESION, feb);
			
			model.addAttribute("ficherosEsquemas", feb);
        }
	}

	/**
	 * Establece los datos en el bean de un fichero de esquema de origen.
	 * @param archivo
	 * @param extension
	 * @param tipoFichero
	 * @param feb
	 */
	private void establecerBeanDatosFicheroOrigen(File archivo, String extension, TipoFichero tipoFichero,
			FicherosEsquemasBean feb) {
		String ext1 = FilenameUtils.getExtension(archivo.getName());
		if(ext1.equalsIgnoreCase(extension)) {
			feb.setSourceFileName(archivo.getName());
			feb.setSourceFile(archivo);
			feb.setSourceFileType(tipoFichero);
		} else {
			feb.setSourceFileName("");
		}
	}
	
	/**
	 * Establece los datos en el bean de un fichero de esquema de destino.
	 * @param archivo
	 * @param extension
	 * @param tipoFichero
	 * @param feb
	 */
	private void establecerBeanDatosFicheroDestino(File archivo, String extension, TipoFichero tipoFichero,
			FicherosEsquemasBean feb) {
		String ext1 = FilenameUtils.getExtension(archivo.getName());
		if(ext1.equalsIgnoreCase(extension)) {
			feb.setTargetFileName(archivo.getName());
			feb.setTargetFile(archivo);
			feb.setTargetFileType(tipoFichero);
		} else {
			feb.setTargetFileName("");
		}
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView displayUploadForm(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("uploadFile");
        
        ControllerUtils.removeFicherosEsquemasBeanFromSession(request);
    	
    	return view;
    }
	
	
	
}

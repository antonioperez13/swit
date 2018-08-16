package es.um.swit.form;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class MultipleFileUploadBean {
	private List<MultipartFile> file;
	 
    public List<MultipartFile> getFiles() {
        return file;
    }
 
    public void setFiles(List<MultipartFile> files) {
        this.file = files;
    }
}

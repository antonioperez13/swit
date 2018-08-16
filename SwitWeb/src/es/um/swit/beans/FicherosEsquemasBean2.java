package es.um.swit.beans;

import org.springframework.web.multipart.MultipartFile;

import commons.tree.NodeOwl;
import commons.tree.NodeXsd;

public class FicherosEsquemasBean2 {
	/** Nombre del fichero XSD */
	private String sourceFileName;
	
	private MultipartFile xsdFile;
	
	private NodeXsd arbolXsd;
	
	private String owlFileName;
	
	private MultipartFile owlFile;
	
	private NodeOwl arbolOwl;
	
	
	/** *******************
	 * MÉTODOS
	 ***********************/

	/**
	 * @return the sourceFileName
	 */
	public String getsourceFileName() {
		return sourceFileName;
	}

	/**
	 * @param sourceFileName the sourceFileName to set
	 */
	public void setsourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	/**
	 * @return the xsdFile
	 */
	public MultipartFile getXsdFile() {
		return xsdFile;
	}

	/**
	 * @param xsdFile the xsdFile to set
	 */
	public void setXsdFile(MultipartFile xsdFile) {
		this.xsdFile = xsdFile;
	}
	
	/**
	 * @return the arbolXsd
	 */
	public NodeXsd getArbolXsd() {
		return arbolXsd;
	}

	/**
	 * @param arbolXsd the arbolXsd to set
	 */
	public void setArbolXsd(NodeXsd arbolXsd) {
		this.arbolXsd = arbolXsd;
	}

	/**
	 * @return the owlFileName
	 */
	public String getOwlFileName() {
		return owlFileName;
	}

	/**
	 * @param owlFileName the owlFileName to set
	 */
	public void setOwlFileName(String owlFileName) {
		this.owlFileName = owlFileName;
	}

	/**
	 * @return the owlFile
	 */
	public MultipartFile getOwlFile() {
		return owlFile;
	}

	/**
	 * @param owlFile the owlFile to set
	 */
	public void setOwlFile(MultipartFile owlFile) {
		this.owlFile = owlFile;
	}
	
	/**
	 * @return the arbolOwl
	 */
	public NodeOwl getArbolOwl() {
		return arbolOwl;
	}

	/**
	 * @param arbolOwl the arbolOwl to set
	 */
	public void setArbolOwl(NodeOwl arbolOwl) {
		this.arbolOwl = arbolOwl;
	}
	
}

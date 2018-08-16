package es.um.swit.beans;


import java.io.File;

import commons.tree.Node;
import commons.tree.NodeOwl;
import commons.tree.NodeXsd;
import es.um.swit.enums.TipoFichero;

public class FicherosEsquemasBean {
	/** Nombre del fichero del esquema de origen */
	private String sourceFileName;
	
	/** Fichero del esquema de origen */
	private File sourceFile;
	
	/** Árbol del esquema de origen */
	private Node sourceTree;
	
	/** Tipo de fichero del esquema de origen */
	private TipoFichero sourceFileType;
	
	/** Nombre del fichero del esquema de destino */
	private String targetFileName;
	
	/** Fichero del esquema de destino */
	private File targetFile;
	
	/** Árbol del esquema de destino */
	private NodeOwl targetTree;
	
	/** Tipo de fichero del esquema de destino */
	private TipoFichero targetFileType;
	
	/** Tipo de fichero de salida del mapeo */
	private String outputFileType;
	
	/** *******************
	 * MÉTODOS
	 ***********************/

	/**
	 * @return the sourceFileName
	 */
	public String getSourceFileName() {
		return sourceFileName;
	}

	/**
	 * @param sourceFileName the sourceFileName to set
	 */
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	/**
	 * @return the sourceFile
	 */
	public File getSourceFile() {
		return sourceFile;
	}

	/**
	 * @param sourceFile the sourceFile to set
	 */
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	/**
	 * @return the sourceTree
	 */
	public Node getSourceTree() {
		return sourceTree;
	}

	/**
	 * @param sourceTree the sourceTree to set
	 */
	public void setSourceTree(NodeXsd sourceTree) {
		this.sourceTree = sourceTree;
	}
	
	/**
	 * @return the sourceFileType
	 */
	public TipoFichero getSourceFileType() {
		return sourceFileType;
	}

	/**
	 * @param sourceFileType the sourceFileType to set
	 */
	public void setSourceFileType(TipoFichero sourceFileType) {
		this.sourceFileType = sourceFileType;
	}

	/**
	 * @return the targetFileName
	 */
	public String getTargetFileName() {
		return targetFileName;
	}

	/**
	 * @param targetFileName the targetFileName to set
	 */
	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	/**
	 * @return the targetFile
	 */
	public File getTargetFile() {
		return targetFile;
	}

	/**
	 * @param targetFile the targetFile to set
	 */
	public void setTargetFile(File targetFile) {
		this.targetFile = targetFile;
	}
	
	/**
	 * @return the targetTree
	 */
	public NodeOwl getTargetTree() {
		return targetTree;
	}

	/**
	 * @param targetTree the targetTree to set
	 */
	public void setTargetTree(NodeOwl targetTree) {
		this.targetTree = targetTree;
	}
	
	/**
	 * @return the targetFileType
	 */
	public TipoFichero getTargetFileType() {
		return targetFileType;
	}

	/**
	 * @param targetFileType the targetFileType to set
	 */
	public void setTargetFileType(TipoFichero targetFileType) {
		this.targetFileType = targetFileType;
	}
	
	/**
	 * @return the outputFileType
	 */
	public String getOutputFileType() {
		return outputFileType;
	}

	/**
	 * @param outputFileType the outputTypeFile to set
	 */
	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}
}

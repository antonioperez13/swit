package commons.entrada;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import commons.tree.NodeXsd;

/**
 * Clase preparada para parsear desde un fichero XSD un esquema de datos
 * a un árbol de nodos del tipo {@link #NodeXsd}.
 * @author Antonio
 *
 */
public class ProcessXsd {

	/** Árbol con la estructura del fichero XSD procesado */
	private static NodeXsd structure;

	public static NodeXsd getSchemasStructureFromFilePath(String filePath) throws Exception {

		File xmlFile = new File(filePath);
		
		getSchemasStructure(xmlFile);
		
		return structure;
	}

	/**
	 * Dado un fichero XSD parsea su estructura a un árbol de nodos.
	 * @param xmlFile
	 */
	public static NodeXsd getSchemasStructure(File xmlFile) throws Exception{
		structure = new NodeXsd("rootNode");
		
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootElement = document.getRootElement();
			List<Element> list = rootElement.getChildren();

			for (int i = 0; i < list.size(); i++) {

				Element element = (Element) list.get(i);
				
				if (element.getName().equals("element")) {
					Attribute att = element.getAttribute("name");
					Attribute att2 = element.getAttribute("type");
					String name = att.getValue();
					if (att2 != null)
						name = name + " : " + att2.getValue();
					
					NodeXsd subNode = new NodeXsd(name);
					structure.addChild(subNode);
					ProcessXsd.getSubConcepts(subNode, element, rootElement, name, new ArrayList<String>());

				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new Exception();
		}

		
		structure = structure.getChildren().get(0);
		structure.removeParent();
		
		return structure;
	}

	private static boolean isCommonType(String type) {
		if (type.equals("xs:string") || type.equals("xs:decimal") || type.equals("xs:integer") || type.equals("xs:boolean") || type.equals("xs:date") || type.equals("xs:time"))
			return true;
		return false;

	}

	private static void getSubConcepts(NodeXsd node, Element element, Element root, String key, ArrayList<String> processedTypes) {

		List<Element> list = element.getChildren();
		boolean finish = false;

		if (list.size() == 0) {
			if (element.getName().equals("element")) {
				Attribute att2 = element.getAttribute("type");
				Attribute att3 = element.getAttribute("ref");
				if (att2 != null || att3 != null) {
					Attribute typeAtt = (att2 == null) ? att3 : att2;
					boolean found = false;

					String type = typeAtt.getValue();
					if (isCommonType(type)) {
						finish = true;
					}

					else {

						List<Element> c = root.getChildren();
						if (processedTypes.contains(type)) {
							found = true;
						} else {
							for (int j = 0; j < c.size(); j++) {
								Element cType = (Element) c.get(j);
								if (cType.getName().equals("complexType") || cType.getName().equals("element")) {
									Attribute att = cType.getAttribute("name");
									if (att != null && att.getValue().equals(type)) {
										found = true;
										processedTypes.add(att.getValue());
										ProcessXsd.getSubConcepts(node, cType, root, key, processedTypes);
										processedTypes.remove(att.getValue());
										break;
									}
								}
							}
						}
						if (!found) {
							if (type.contains(":")) {
								String[] parts = type.split(":");
								String type2 = parts[1];
								if (processedTypes.contains(type2)) {
									found = true;
								} else {
									for (int j = 0; j < c.size(); j++) {
										Element cType = (Element) c.get(j);
										if (cType.getName().equals("complexType")) {
											Attribute att = cType.getAttribute("name");

											if (att != null && att.getValue().equals(type2)) {
												found = true;
												processedTypes.add(att.getValue());
												ProcessXsd.getSubConcepts(node, cType, root, key, processedTypes);
												processedTypes.remove(att.getValue());
												break;
											}
										}
									}
								}
							}
						}
						if (!found) {
							if (key.contains(" : ")) {
								String[] parts = key.split(" : ");
								String k = parts[0];
								ProcessXsd.changeElementName(key, k);
							}
						}
					}

				}
			}
		}
		if (!finish) {
			for (int i = 0; i < list.size(); i++) {
				Element subElement = (Element) list.get(i);
				if (subElement.getName().equals("element")) {
					Attribute att = subElement.getAttribute("name");
					Attribute att2 = subElement.getAttribute("type");
					Attribute att3 = subElement.getAttribute("ref");
					if (att == null) {
						att = att3;
					}

					String name = att.getValue();
					if (att2 != null)
						name = name + " : " + att2.getValue();
					
					NodeXsd subNode = new NodeXsd(name);
					node.addChild(subNode);
					ProcessXsd.getSubConcepts(subNode, subElement, root, name, processedTypes);
				} else if (subElement.getName().equals("attribute")) {
					Attribute att = subElement.getAttribute("name");
					String name = "@" + att.getValue();
					NodeXsd subNode = new NodeXsd(name);
					node.addChild(subNode);
				} else {
					ProcessXsd.getSubConcepts(node, subElement, root, key, processedTypes);

				}
			}
		}
	}

	/**
	 * Cambia el nombre de los nodos con el nombre indicado con el nuevo
	 * nombre que se proporcione.
	 * @param elementName
	 * @param newElementName
	 */
	private static void changeElementName(String elementName, String newElementName) {
		List<NodeXsd> nodos = structure.getNodesWithName(elementName);
		
		for(NodeXsd nodo : nodos) {
			nodo.setName(newElementName);
		}
	}

}
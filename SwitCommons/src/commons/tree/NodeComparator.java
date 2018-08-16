package commons.tree;

import java.util.Comparator;

public class NodeComparator {
	
	public static Comparator<Node> compareByName(){
		return new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.getName().compareTo(n2.getName());
			}
			
		};
	}
	
	public static Comparator<NodeOwl> compareByNameOwl(){
		return new Comparator<NodeOwl>() {
			@Override
			public int compare(NodeOwl n1, NodeOwl n2) {
				if(n1.getType() == OwlType.CLASS && n2.getType() == OwlType.CLASS) {
					return n1.getName().compareTo(n2.getName());
				}
				return 0;
			}
			
		};
	}
	
}

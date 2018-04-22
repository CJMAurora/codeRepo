package roadNetwork;

import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) {
		NodeSet nodeSet=new NodeSet();
		nodeSet.readData();
		for(int i=0;i<nodeSet.nodeList.size();i++) {
			System.out.println(nodeSet.nodeList.get(i));
		}
		 
		EdgeSet edgeSet=new EdgeSet();
		edgeSet.readEdge();
//		List<Vertex> vertexs = new ArrayList<Vertex>();
//		for(int i=0;i<nodeSet.nodeList.size();i++) {
//			Vertex v=new Vertex(nodeSet.nodeList.get(i).getNodeId());
//			vertexs.add(v);
//		}
//	        Vertex a = new Vertex("A", 0);
//	        Vertex b = new Vertex("B");
//	        Vertex c = new Vertex("C");
//	        Vertex d = new Vertex("D");
//	        Vertex e = new Vertex("E");
//	        Vertex f = new Vertex("F");
//	        vertexs.add(a);
//	        vertexs.add(b);
//	        vertexs.add(c);
//	        vertexs.add(d);
//	        vertexs.add(e);
//	        vertexs.add(f);
//	        int[][] edges = {
//	                {Integer.MAX_VALUE,6,3,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},
//	                {6,Integer.MAX_VALUE,2,5,Integer.MAX_VALUE,Integer.MAX_VALUE},
//	                {3,2,Integer.MAX_VALUE,3,4,Integer.MAX_VALUE},
//	                {Integer.MAX_VALUE,5,3,Integer.MAX_VALUE,5,3},
//	                {Integer.MAX_VALUE,Integer.MAX_VALUE,4,5,Integer.MAX_VALUE,5},
//	                {Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,3,5,Integer.MAX_VALUE}
//	        
//	        };
//		    int l=nodeSet.nodeList.size();
//	        double[][] edges=new double[l][l];
//	        for(int j=0;j<nodeSet.nodeList.size();j++) {
//	        	for(int i=0;i<EdgeSet.edgeList.size();i++) {
//	        		int m=EdgeSet.edgeList.get(i).getStartNode();
//	        		int n=EdgeSet.edgeList.get(i).getEndNode();
//	        		edges[m][n]=EdgeSet.edgeList.get(i).getDistance();
//	        	}
//	        }
//	        Graph graph = new Graph(vertexs, edges);
//	        graph.printGraph();
//	        graph.search();
		
	}

}

package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import roadNetwork.*;

public class WeightedGraph {
	public static HashMap<Integer, HashMap<Integer, Double>> hm = new HashMap<>();

	private class Vertex implements Comparable<Vertex>// 顶点
	{
		private int vertexLabel;// 顶点标识
		private List<Edge> adjEdges;// 顶点的所有邻接边(点)
		private double dist;// 顶点到源点的最短距离
		private Vertex preNode;// 前驱顶点

		public Vertex(int vertexLabel) {
			this.vertexLabel = vertexLabel;
			adjEdges = new LinkedList<Edge>();
			dist = Double.MAX_VALUE;
			preNode = null;
		}

		@Override
		public int compareTo(Vertex v) {
			if (this.dist > v.dist)
				return 1;
			else if (this.dist < v.dist)
				return -1;
			return 0;
		}
	}

	private class Edge {
		private double weight;// 边的权值(带权图)
		private Vertex endVertex;

		public Edge(double weight2, Vertex endVertex) {
			this.weight = weight2;
			this.endVertex = endVertex;
		}
	}

	private Map<Integer, Vertex> weightedGraph;// 存储图(各个顶点)
	private Vertex startVertex;// 单源最短路径的起始顶点

	// 图的信息保存在文件中,从文件中读取成字符串graphContent

	// public WeightedGraph(int startV) {
	// weightedGraph = new LinkedHashMap<Integer, WeightedGraph.Vertex>();
	// buildGraph(startV);//解析字符串构造图
	// }
	private void buildGraph(int startV) {
		// String[] lines = graphContent.split("\n");
		EdgeSet edgeSet = new EdgeSet();
		ArrayList<roadNetwork.Edge> edgeList = edgeSet.edgeList;
		weightedGraph = new LinkedHashMap<Integer, WeightedGraph.Vertex>();
		int startNodeLabel, endNodeLabel;// 开始节点标签，结束节点标签
		Vertex startNode, endNode;
		double weight;
		for (int i = 0; i < edgeList.size(); i++) {
			startNodeLabel = edgeList.get(i).getStartNode();
			endNodeLabel = edgeList.get(i).getEndNode();
			weight = edgeList.get(i).getDistance();
			startNode = weightedGraph.get(startNodeLabel);
			if (startNode == null) {
				startNode = new Vertex(startNodeLabel);
				weightedGraph.put(startNodeLabel, startNode);
			}
			endNode = weightedGraph.get(endNodeLabel);
			if (endNode == null) {
				endNode = new Vertex(endNodeLabel);
				weightedGraph.put(endNodeLabel, endNode);
			}
			Edge e1 = new Edge(weight, endNode);
			startNode.adjEdges.add(e1);
			Edge e2 = new Edge(weight, startNode);
			endNode.adjEdges.add(e2);
		}
		startVertex = weightedGraph.get(startV);
	}

	public void dijkstra() {
		BinaryHeap<Vertex> heap = new BinaryHeap<WeightedGraph.Vertex>();
		init(heap);// inital heap

		while (!heap.isEmpty()) {
			Vertex v = heap.deleteMin();
			List<Edge> adjEdges = v.adjEdges;// 获取v的所有邻接点
			for (Edge e : adjEdges) {
				Vertex adjNode = e.endVertex;
				// update
				if (adjNode.dist > e.weight + v.dist) {
					adjNode.dist = e.weight + v.dist;
					adjNode.preNode = v;
				}
			} // end for

			// 更新之后破坏了堆序性质,需要进行堆调整,这里直接重新构造堆(相当于decreaseKey)
			heap.buildHeap();
		}

	}

	private void init(BinaryHeap<Vertex> heap) {
		startVertex.dist = 0;// 源点到其自身的距离为0
		for (Vertex v : weightedGraph.values()) {
			heap.insert(v);
		}
	}

	public double showDistance(int startV, int endV) {
		// WeightedGraph(startV);
		double dis = 0;
		if(!hm.containsKey(startV)) {
			buildGraph(startV);
			dijkstra();
			
			HashMap<Integer,Double> disMap=new HashMap<>();
			for (Vertex v : weightedGraph.values()) {
					// printPath(v);
					// System.out.println();
				disMap.put(v.vertexLabel, v.dist);
					//System.out.println(startVertex.vertexLabel + "到" + v.vertexLabel + "的距离: " + v.dist);
				if(endV == v.vertexLabel) {
						dis = v.dist;
				}
				
			}
			hm.put(startV, disMap);
		}
		else {
			HashMap<Integer,Double> disMap=hm.get(startV);
			dis=disMap.get(endV);
		}
		
//		for (Vertex v : weightedGraph.values()) {
//			if ((startVertex.vertexLabel != v.vertexLabel) && (endV == v.vertexLabel)) {
//				// printPath(v);
//				// System.out.println();
//				System.out.println(startVertex.vertexLabel + "到" + v.vertexLabel + "的距离: " + v.dist);
//				dis = v.dist;
//				break;
//			}
//		}
		return dis;
	}

	// 打印源点到 end 顶点的 最短路径
	private void printPath(Vertex end) {
		if (end.preNode != null)
			printPath(end.preNode);
		System.out.print(end.vertexLabel + "--> ");
	}
}
package roadNetwork;

public class Edge {
	private int edgeId;
	private int startNode;
	private int endNode;
	private double distance;
	public int getEdgeId() {
		return edgeId;
	}
	public void setEdgeId(int edgeId) {
		this.edgeId = edgeId;
	}
	public int getStartNode() {
		return startNode;
	}
	public void setStartNode(int startNode) {
		this.startNode = startNode;
	}
	public int getEndNode() {
		return endNode;
	}
	public void setEndNode(int endNode) {
		this.endNode = endNode;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	

}

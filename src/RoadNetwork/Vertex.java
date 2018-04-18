package RoadNetwork;

public class Vertex implements Comparable<Vertex>{
	 /**
     * 节点名称(A,B,C,D)
     */
    private int name;
    
    /**
     * 最短路径长度
     */
    private double path;
    
    /**
     * 节点是否已经出列(是否已经处理完毕)
     */
    private boolean isMarked;
    
    public Vertex(int name){
        this.name = name;
        this.path = Integer.MAX_VALUE; //初始设置为无穷大
        this.setMarked(false);
    }
    
    public Vertex(int name, double path){
        this.name = name;
        this.path = path;
        this.setMarked(false);
    }
    
    @Override
    public int compareTo(Vertex o) {
        return o.path > path?-1:1;
    }

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public double getPath() {
		return path;
	}

	public void setPath(double path) {
		this.path = path;
	}

	public boolean isMarked() {
		return isMarked;
	}

	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}
    

}

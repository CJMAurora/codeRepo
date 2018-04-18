package RoadNetwork;

public class Vertex implements Comparable<Vertex>{
	 /**
     * �ڵ�����(A,B,C,D)
     */
    private int name;
    
    /**
     * ���·������
     */
    private double path;
    
    /**
     * �ڵ��Ƿ��Ѿ�����(�Ƿ��Ѿ��������)
     */
    private boolean isMarked;
    
    public Vertex(int name){
        this.name = name;
        this.path = Integer.MAX_VALUE; //��ʼ����Ϊ�����
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

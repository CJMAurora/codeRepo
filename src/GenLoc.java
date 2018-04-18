import java.util.ArrayList;


public class GenLoc {
	int singleId;//如果是一个地址，就存在singleId中
	ArrayList<Integer> locList;//如果还有多个地址，就放在locList中
	public int getSingleId() {
		return singleId;
	}
	public void setSingleId(int singleId) {
		this.singleId = singleId;
	}
	public ArrayList<Integer> getLocList() {
		return locList;
	}
	public void setLocList(ArrayList<Integer> locList) {
		this.locList = locList;
	}
	

}

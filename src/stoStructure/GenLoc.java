package stoStructure;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Aurora
 *该类是地点泛化类，存储时，地点还没有泛化，所以地点都放在singleId中
 *泛化的地点放在locList中
 */

public class GenLoc implements Serializable{
	private int singleId;//如果是一个地址，就存在singleId中
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

package stoStructure;
import java.util.ArrayList;
/**
 * 
 * @author Aurora
 *存储所有人的信息
 *包括用户ID和其轨迹集合
 */

public class UserInfo {
	private int id;
	private ArrayList<Traj> trajList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Traj> getTrajList() {
		return trajList;
	}
	public void setTrajList(ArrayList<Traj> trajList) {
		this.trajList = trajList;
	}
    
}

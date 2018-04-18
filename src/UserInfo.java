import java.util.ArrayList;


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

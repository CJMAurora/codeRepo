package unsafeRule;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Aurora
 *z支持轨迹类，用来存储满足不安全规则的轨迹
 */
public class suppTraj implements Serializable{
	ArrayList<Integer> traj;
	double count;
	public ArrayList<Integer> getTraj() {
		return traj;
	}
	public void setTraj(ArrayList<Integer> traj) {
		this.traj = traj;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}

}

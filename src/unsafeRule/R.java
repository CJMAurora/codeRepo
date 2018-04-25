package unsafeRule;

import java.util.ArrayList;

public class R {
	int rId;
	suppTraj trajNume;
	suppTraj trajDemo;
	float conf;
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public suppTraj getTrajNume() {
		return trajNume;
	}
	public void setTrajNume(suppTraj trajNume) {
		this.trajNume = trajNume;
	}
	public suppTraj getTrajDemo() {
		return trajDemo;
	}
	public void setTrajDemo(suppTraj trajDemo) {
		this.trajDemo = trajDemo;
	}
	public float getConf() {
		return conf;
	}
	public void setConf(float conf) {
		this.conf = conf;
	}
	

}
class suppTraj{
	ArrayList<Integer> traj;
	int count;
}

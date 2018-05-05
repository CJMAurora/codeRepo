package unsafeRule;

import java.io.Serializable;

/**
 * 
 * @author Aurora
 *不安全规则类
 */
//可以被序列化
public class R implements Serializable {
	int rId;
	suppTraj trajNume;
	suppTraj trajDemo;
	double conf;
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
	public double getConf() {
		return conf;
	}
	public void setConf(double conf) {
		this.conf = conf;
	}
	

}

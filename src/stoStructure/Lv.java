package stoStructure;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Aurora
 *用来存储用户的访问点，包括时间和位置
 */

public class Lv implements Serializable{ 
	private int hour;
	private int min;
	private GenLoc genLoc=new GenLoc();
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public GenLoc getGenLoc() {
		return genLoc;
	}
	public void setGenLoc(GenLoc genLoc) {
		this.genLoc = genLoc;
	}
	public Integer getLoc(){
		return genLoc.getSingleId();
	}
    
}

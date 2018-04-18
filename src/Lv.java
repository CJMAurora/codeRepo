import java.util.ArrayList;


public class Lv { 
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
		return genLoc.singleId;
	}
    
}

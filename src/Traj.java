import java.util.ArrayList;


public class Traj {
    private String date;
	private  ArrayList<Lv> lvList;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<Lv> getLvList() {
		return lvList;
	}
	public void setLvList(ArrayList<Lv> lvList) {
		this.lvList = lvList;
	}
//	public ArrayList<Integer> getInitLoc(){//得到一一天中只包含初始地点的轨迹
//		ArrayList<Integer> initLocList=new ArrayList<>();
//		for(int i=0;i<lvList.size();i++){
//			initLocList.add(lvList.get(i).getLoc());
//		}
//		return initLocList;
//	}

}

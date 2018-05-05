package generalization;

import java.io.IOException;
import java.util.ArrayList;

import stoStructure.*;

public class testCandZone {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Storage s=new Storage();
		s.putDataFromFile();
		s.storageDate();
		//ArrayList<Lv> lvList=s.userInfoList.get(0).getTrajList().get(30).getLvList();
		GenCand gc=new GenCand();
		ArrayList<Traj> trajList=s.userInfoList.get(0).getTrajList();
//		for(int i=0;i<trajList.size();i++) {
//			gc.getGencand(trajList.get(i).getLvList(), 0.032424025, 2);
//		}
	    gc.suppress(trajList);
		//cz.getCandZone(lvList, 0.032424025, 2);
	}

}

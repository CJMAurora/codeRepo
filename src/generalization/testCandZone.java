package generalization;

import java.util.ArrayList;

import stoStructure.Lv;
import stoStructure.Storage;

public class testCandZone {
	public static void main(String[] args) {
		Storage s=new Storage();
		s.putDataFromFile();
		s.storageDate();
		ArrayList<Lv> lvList=s.userInfoList.get(2).getTrajList().get(30).getLvList();
		CandZone cz=new CandZone();
		cz.getCandZone(lvList, 0.032424025, 2);
	}

}

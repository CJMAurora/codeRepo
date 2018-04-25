package generalization;

import java.util.ArrayList;
import java.util.HashMap;
import stoStructure.*;
import unsafeRule.R;
import unsafeRule.Rule;

public class CandZone {
	
	public void getCandZone(ArrayList<Lv> lvList,double v,int a) {
		DisInfo dis=new DisInfo();
		dis.setDisInfo(0);
		HashMap<Integer, ArrayList<Dist>> disInfo=dis.disInfo;
		Storage s=new Storage();
		//s.putDataFromFile();
		//s.storageDate();
		Rule r=new Rule();
		ArrayList<R> rlist=r.genRList(0, 0.5f);
		for(int i=0;i<lvList.size();i++) {
			ArrayList<Integer> candList=new ArrayList<>();
			if(i==0) {
				int loc=lvList.get(i+1).getLoc();//得到访问点中的地址
				double time=lvList.get(i+1).getHour()-lvList.get(i).getHour()+(lvList.get(i+1).getMin()-lvList.get(i).getMin())/60;
				double thed=time*v;
				ArrayList<Dist> distList=disInfo.get(loc);
				for(int j=0;j<distList.size();j++) {
					if(distList.get(j).getDist()<=thed) {
						if(r.PointPre(distList.get(j).getLoc())>=a) {
							candList.add(distList.get(j).getLoc());//得到候选地点
						}
					}
				}
				
			}
			else if(i==lvList.size()-1) {
				int loc=lvList.get(i-1).getLoc();//得到访问点中的地址 
				double time=lvList.get(i).getHour()-lvList.get(i-1).getHour()+(lvList.get(i).getMin()-lvList.get(i-1).getMin())/60;
				double thed=time*v;
				ArrayList<Dist> distList=disInfo.get(loc);
				for(int j=0;j<distList.size();j++) {
					if(distList.get(j).getDist()<=thed) {
						if(r.PointPre(distList.get(j).getLoc())>=a) {
							candList.add(distList.get(j).getLoc());//得到候选地点
						}
					}
				}
			}
			else{
				ArrayList<Integer> candList1=new ArrayList<>();
				ArrayList<Integer> candList2=new ArrayList<>();
				int loc1=lvList.get(i-1).getLoc();//得到访问点中的地址 
				double time1=lvList.get(i).getHour()-lvList.get(i-1).getHour()+(lvList.get(i).getMin()-lvList.get(i-1).getMin())/60;
				double thed1=time1*v;
				ArrayList<Dist> distList1=disInfo.get(loc1);
				int loc2=lvList.get(i+1).getLoc();//得到访问点中的地址
				double time2=lvList.get(i+1).getHour()-lvList.get(i).getHour()+(lvList.get(i+1).getMin()-lvList.get(i).getMin())/60;
				double thed2=time2*v;
				ArrayList<Dist> distList2=disInfo.get(loc2);
				for(int j=0;j<distList1.size();j++) {
					if(distList1.get(j).getDist()<thed1) {
						candList1.add(distList1.get(j).getLoc());
					}
				}
				for(int j=0;j<distList2.size();j++) {
					if(distList2.get(j).getDist()<thed2) {
						candList2.add(distList2.get(j).getLoc());
					}
				}
				//交集
		        candList1.retainAll(candList2);
		        for(int j=0;j<candList1.size();j++) {
		        	if(r.PointPre(candList1.get(j))>=a) {
		        		candList.add(candList1.get(j));
		        	}
		        }
		        
			}
			for(int j=0;j<candList.size();j++) {
				System.out.println(candList.get(j));
			}
		}
		
	}

}

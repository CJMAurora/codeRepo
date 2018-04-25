import java.math.BigDecimal;
import java.util.ArrayList;

import generalization.*;
import roadNetwork.*;
import stoStructure.*;
import unsafeRule.*;

public class test1 {
	public static void main(String[] args) {
		Storage s=new Storage();
		Function f=new Function();
		Distance d=new Distance();
        s.putDataFromFile();
		s.storageDate();
		EdgeSet e=new EdgeSet();
		NodeSet n=new NodeSet();
		e.readEdge();
		n.readData();
		
		ArrayList<UserInfo> userInfoList=s.userInfoList;
		ArrayList<Traj> trajList=userInfoList.get(0).getTrajList();
		double dist;
		PutFile pf=new PutFile(0,"F:\\trajPrivacy\\distance\\");
		long startTime = System.currentTimeMillis();
		for(int i=0;i<trajList.size();i++) {
			ArrayList<Lv> lvList=trajList.get(i).getLvList();
			for(int j=0;j<lvList.size();j++) {
				for(int k=j+1;k<lvList.size();k++) {
					//dist=d.disbP(lvList.get(j).getLoc(), lvList.get(k).getLoc(), 1.5f);
					//System.out.println("点"+lvList.get(j).getLoc()+"到点"+lvList.get(k).getLoc()+"的距离为："+dist);
					//String con=lvList.get(j).getLoc()+" "+lvList.get(k).getLoc()+" "+dist+"\r\n";
					
				}
				
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间为："+(endTime-startTime)+"ms");
		
	}

}

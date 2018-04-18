import java.math.BigDecimal;
import java.util.ArrayList;
import RoadNetwork.*;
import RoadNetwork.EdgeSet;
import RoadNetwork.NodeSet;

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
		long startTime = System.currentTimeMillis();
		//for(int i=0;i<trajList.size();i++) {
			ArrayList<Lv> lvList=trajList.get(0).getLvList();
			for(int j=0;j<lvList.size();j++) {
				for(int i=j+1;i<lvList.size();i++) {
					dist=d.disbP(lvList.get(j).getLoc(), lvList.get(i).getLoc(), 1.5f);
					System.out.println("点"+lvList.get(j).getLoc()+"到点"+lvList.get(i).getLoc()+"的距离为："+dist);
				}
				
			}
			//d.disbP(19542, 13338, 1.5f);
		//}
		//long startTime = System.currentTimeMillis();
		//d.edgeCand(22765, 2);
		//d.PtE(13338,1.5f);
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间为："+(endTime-startTime)+"ms");
		
		//System.out.println(dis);
		//ArrayList<CandE> cList=d.edgeCand(22765, 2);
		//System.out.println(cList.size());
//		for(int i=0;i<cList.size();i++) {
//			System.out.println(cList.get(i).geteId()+" "+cList.get(i).getX1()+" "+cList.get(i).getY1()+" "+cList.get(i).getX2()+" "+cList.get(i).getY2());
//		}
		
	}

}

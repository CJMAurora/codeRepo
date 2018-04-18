import java.util.ArrayList;
import RoadNetwork.*;

public class Test {
	    static PreTree p=new PreTree(); 
	public static void main(String[] args) {
		Storage s=new Storage();
		Function f=new Function();
        s.putDataFromFile();
		s.storageDate();
//		ArrayList<UserInfo> userInfoList=s.userInfoList;
//		ArrayList<Traj> trajList=userInfoList.get(0).getTrajList();
//		System.out.println(userInfoList.get(0). getId());
// 	    p.genPreTree(trajList);
//		for(int b=0;b<trajList.size();b++){
//			ArrayList<Lv> lvList=trajList.get(b).getLvList();
//			ArrayList<ArrayList<Integer>> alltraj=f.getAllSubTraj(lvList);
//			for(int i=0;i<alltraj.size();i++){
//				String str=" ";
//				for(int j=0;j<alltraj.get(i).size();j++){
//					 str +=alltraj.get(i).get(j)+"->";
//					 
//				}
//				System.out.println(str);
//				int n=p.trajFre(alltraj.get(i));
//				if(n>=2&&alltraj.get(i).size()>2){
//					//System.out.println(alltraj.get(i).size());
//					System.out.println(str);
//					System.out.println(n);
//					System.out.println();
//				}
//				System.out.println(n);
//			}
//		 }
//		Rule r=new Rule();
//		ArrayList<R> rlist=r.genRList(0, 0.5f);
//        for(int i=0;i<rlist.size();i++){
//        	System.out.println(rlist.get(i).trajNume.traj+" "+rlist.get(i).trajNume.count+" "+rlist.get(i).trajDemo.traj+" "+rlist.get(i).trajDemo.count+" "+rlist.get(i).conf);
//        }
		Distance d=new Distance();
		EdgeSet e=new EdgeSet();
		NodeSet n=new NodeSet();
		e.readEdge();
		n.readData();
		//ArrayList<CandE> cList=d.edgeCand(19542, 0);
//		System.out.println(cList.size());
//		for(int i=0;i<cList.size();i++) {
//			System.out.println(cList.get(i).geteId()+" "+cList.get(i).getX1()+" "+cList.get(i).getY1()+" "+cList.get(i).getX2()+" "+cList.get(i).getY2());
//		}
		
	}
	

}

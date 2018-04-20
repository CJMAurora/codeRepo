package unsafeRule;

import java.util.ArrayList;

import stoStructure.Storage;

public class test {
	public static void main(String[] args) {
		Storage s=new Storage();
		s.putDataFromFile();
		s.storageDate();
		Rule r=new Rule();
		ArrayList<R> rlist=r.genRList(2, 0.5f);//第几个用户
        for(int i=0;i<rlist.size();i++){
        	System.out.println(rlist.get(i).trajNume.traj+" "+rlist.get(i).trajNume.count+" "+rlist.get(i).trajDemo.traj+" "+rlist.get(i).trajDemo.count+" "+rlist.get(i).conf);
        }
	}

}

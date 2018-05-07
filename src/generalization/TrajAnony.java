package generalization;

import java.io.IOException;
import java.util.ArrayList;

import stoStructure.*;
import unsafeRule.*;

public class TrajAnony {
	
	public void update() throws ClassNotFoundException, IOException {
		Rule r=new Rule();
		ArrayList<R> rlist=r.genRList(0, 0.5f);//得到用户的不安全规则
		GenCand gc=new GenCand();
		ArrayList<Traj> trajList=Storage.userInfoList.get(0).getTrajList();
		ArrayList<Q> qList=gc.getQList(trajList);
		while(rlist!=null) {
			//取出score值最高的
			Q q=qList.get(0);
			Op op=q.getOp();
			//根据操作op的轨迹id得到这条轨迹的访问点序列
			ArrayList<Lv> lvList=trajList.get(op.getTrajId()).getLvList();
			//如果泛化点为0，说明为抑制操作，需要把LvList中相应的点删除
			if(op.getGenLoc()==0) {
				for(int i=0;i<lvList.size();i++) {
					if(lvList.get(i).getLoc()==op.getLocId())
						lvList.remove(i);
				}
			}
			//如果不为0，说明为泛化操作
			else {
				for(int i=0;i<lvList.size();i++) {
					if(lvList.get(i).getLoc()==op.getLocId()) {
						//把操作的泛化地点加入到locLis中
						lvList.get(i).getGenLoc().getLocList().add(op.getGenLoc());
					}
				}
			}
			
		}
	}

}

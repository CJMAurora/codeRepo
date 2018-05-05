package unsafeRule;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

import stoStructure.*;
/**
 * 
 * @author Aurora
 *生成所有的不安全规则
 *先把该用户的随机的敏感地点从文件中读出，放到senPoint中
 *得到包含敏感点的子轨迹
 *求出该子轨迹的个数和其条件轨迹的个数
 *若c>conf(给定的阈值) 就把这条轨迹的信息添加到R中
 */

public class Rule{
	
	private ArrayList<Integer> getSenP(int id){
		String path="F:\\trajPrivacy\\ranSeq\\"+id+"测试机.txt";
		String tab="\t";
		ArrayList<Integer> senPoint=new ArrayList<>();
		try{
			File file=new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));//br是字符流
		    String line = null;
		    while((line=br.readLine())!=null){
		    	String[] data=line.split(tab);
		    	senPoint.add(Integer.parseInt(data[0]));
		    }
			br.close();
		}catch(Exception e){
			e.printStackTrace();
            System.out.println("读取文件出错");
		}
		return senPoint;
	}
	public ArrayList<R> genRList(int id,float conf){//得到不安全的规则
		ArrayList<R> rList=new ArrayList<>();
		
		Function f=new Function();
		PreTree p=new PreTree();
		ArrayList<UserInfo> userInfoList=Storage.userInfoList;
		ArrayList<Traj> trajList=userInfoList.get(id).getTrajList();
		int userId=userInfoList.get(id).getId();
		ArrayList<Integer> senPList=getSenP(userId);
		PreTree.genPreTree(trajList);
		int sum=0;//用来记录id号
		for(int i=0;i<trajList.size();i++){//遍历用户每天的轨迹
			ArrayList<Lv> lvList=trajList.get(i).getLvList();//得到其一天的访问点序列
			int senP=senPList.get(i);//得到该天的敏感点
			//System.out.println("senP的值为："+senP);
			ArrayList<ArrayList<Integer>> senTraj=f.getSenTraj(lvList, senP);//包含敏感点的所有轨迹
			for(int j=0;j<senTraj.size();j++){
				ArrayList<Integer> trajNume=senTraj.get(j);//支持轨迹
				ArrayList<Integer> trajDemo=new ArrayList<Integer>();//条件轨迹
				for(int k=0;k<trajNume.size();k++){
					if(trajNume.get(k)==senP)
						continue;
					else
						trajDemo.add(trajNume.get(k));
				}
				double a=p.trajFre(trajNume);//支持轨迹的个数
				double b=p.trajFre(trajDemo);//条件轨迹的个数
				if(b>1){//分母不为0时进行操作，分母大于1过滤分子，分母全是1的不安全关联
				double c=a/b;
					//System.out.println(a+" "+b+" "+c);
					if(c>conf){//若比值大于给定阈值，将其加入到rList中
						suppTraj supptraj1=new suppTraj();
						supptraj1.traj=trajNume;
						supptraj1.count=a;
						suppTraj supptraj2=new suppTraj();
						supptraj2.traj=trajDemo;
						supptraj2.count=b;
						R r=new R();
						r.rId=sum;
						r.trajNume=supptraj1;
						r.trajDemo=supptraj2;
						r.conf=c;
						rList.add(r);
						sum++;
						if(r.trajDemo.count>0){
							System.out.println(r.rId+" "+r.trajNume.traj+" "+r.trajNume.count+" "+r.trajDemo.traj+" "+r.trajDemo.count+" "+r.conf);
						}
						
					}
				}
				
			}
		}
		return rList;
	}
	public int  PointPre(int loc) {
		PreTree p=new PreTree();
		return p.PointPre(loc);
	}

}


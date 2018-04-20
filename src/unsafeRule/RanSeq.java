package unsafeRule;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import stoStructure.*;

/**
 * 
 * @author Aurora
 *该类的功能是针对某用户的轨迹，随机生成敏感位置点
 *然后把生成的敏感位置点存入文件
 */
public class RanSeq{
	public static void main(String[] args){
		Storage s=new Storage();
	    s.putDataFromFile();
		s.storageDate();
		String path="F:\\trajPrivacy\\ranSeq\\";
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		int userId=s.userInfoList.get(2).getId();//得到用户的id
		ArrayList<Traj> trajList=s.userInfoList.get(2).getTrajList();//得到某个用户的轨迹
		String fileName= userId+".txt";//使用用户id做文件的名称，创建文件
		File file=new File(f,fileName); 
		FileWriter fw=null;
		PreTree p=new PreTree();
		p.genPreTree(trajList);
		int supp=2;//自己设定阈值
		for(int i=0;i<trajList.size();i++){
			ArrayList<Integer> locList=new ArrayList<>();
			ArrayList<Lv> lvList =trajList.get(i).getLvList();
			for(int j=0;j<lvList.size();j++){
				System.out.println("轨迹中点的个数"+p.PointPre(lvList.get(j).getLoc()));
				if(p.PointPre(lvList.get(j).getLoc())>=supp){//如果轨迹中的地点的个数大于给定阈值，将其作为敏感点的候选集
					locList.add(lvList.get(j).getLoc());
				}
			}
			int l=locList.size();
			
			if(l>0){
				System.out.println("l的长度:"+l);//打印处均是为了测试
				int x=(int)(Math.random()*l);//生成0-l（这是字母，不是1）之间的随机数
				int point=locList.get(x);
				System.out.println("随机变量的值"+point);
				if(!file.exists()){
					try{
						file.createNewFile();
					}catch(IOException e){
						e.printStackTrace();
					}
					
				}
				try{
					fw = new FileWriter(file,true);
					String xstr=point+"\r\n";
					fw.write(xstr);
				}
				catch(IOException e){
					e.printStackTrace();
				}
				finally{
					try{
						if(fw!=null){
							fw.close();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}	
			}
			else{
				System.out.println("lvList的长度"+lvList.size());
				int x=(int)(Math.random()*lvList.size());//如果该轨迹中的点的个数没有超过阈值的，就随便选一个点
				int point=lvList.get(x).getLoc();
				System.out.println("随机变量point的值"+point);
				if(!file.exists()){
					try{
						file.createNewFile();
					}catch(IOException e){
						e.printStackTrace();
					}
					
				}
				try{
					fw = new FileWriter(file,true);
					String xstr=point+"\r\n";
					fw.write(xstr);
				}
				catch(IOException e){
					e.printStackTrace();
				}
				finally{
					try{
						if(fw!=null){
							fw.close();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}	
			}
		}
			
			
		 
		
	}
	

}

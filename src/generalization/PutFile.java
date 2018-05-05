package generalization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import roadNetwork.Node;
import roadNetwork.NodeSet;
import stoStructure.*;

/**
 * 
 * @author Aurora
 *将用户两点之间的距离放到文件中存储起来
 */
public class PutFile {
	private int id;
	private String path;
	public PutFile(int id,String path) {
		this.id=id;
		this.path=path;
	}
	public void writeFile() {
		Storage s=new Storage();
		Distance d=new Distance();
		File f=new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		String fileName=s.userInfoList.get(id).getId()+"测试集"+".txt";//以用户的id命名
		File file=new File(f,fileName);//创建文件
		FileWriter fw=null;
		if(!file.exists()) {
			try {
				file.createNewFile();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Integer> locList=getLocList();
		double dist;
		String context;
		for(int i=0;i<locList.size()-1;i++) {
			NearestE ne1=d.PtE(locList.get(i),1.5f);
			for(int j=i+1;j<locList.size();j++) {
				NearestE ne2=d.PtE(locList.get(j),1.5f);
				dist=d.disbP(ne1, ne2, 1.5f);
				context=locList.get(i)+" "+locList.get(j)+" "+dist+"\r\n";
				System.out.println("点"+locList.get(i)+"到点"+locList.get(j)+"的距离为："+dist);
				try {
					fw=new FileWriter(file,true);
					fw.write(context);
				}catch(IOException e) {
					e.printStackTrace();
				}
				finally {
					try {
						if(fw!=null) {
							fw.close();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	public ArrayList<Integer> getLocList(){
		Storage s=new Storage();
		ArrayList<Traj> trajList=s.userInfoList.get(id).getTrajList();
		ArrayList<Integer> locList=new ArrayList<>();
		
		for(int i=0;i<trajList.size();i++) {
			ArrayList<Lv> lvList=trajList.get(i).getLvList();
			for(int j=0;j<lvList.size();j++) {
				int sign=-1;
				//进行去重操作
				for(int k=0;k<locList.size();k++) {
					if(lvList.get(j).getLoc().equals(locList.get(k))) {
						sign=1;
						break;
					}
				}
				if(sign==-1) {
					locList.add(lvList.get(j).getLoc());
				}
			}
		}
		return locList;
	}


}

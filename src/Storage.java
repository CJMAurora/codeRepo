import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Storage {
	   public static ArrayList<UserInfo> userInfoList=new ArrayList<UserInfo>();
	   public static ArrayList<Loc> locList=new ArrayList<>();
	    ArrayList<Integer> allId=new ArrayList<>();
		ArrayList<String> allDate=new ArrayList<>();
		ArrayList<Integer> allHour=new ArrayList<>();
		ArrayList<Integer> allMin=new ArrayList<>();
		ArrayList<Integer> allLoc=new ArrayList<>();
		public  void putDataFromFile(){//将数据从文件中读取出来，并分别放到各个数组中
			try{
				String tab="\t";
			   File file = new File("E:\\Gowalla_totalCheckins.txt");//文件
			    //File file = new File("E:\\test.txt");//文件
				//File file =new File(E:\\test.txt");
			    BufferedReader br = new BufferedReader(new FileReader(file));//br是字符流
			    String line = null;
			    while((line=br.readLine())!=null){
			    	if(line.equals(""))//如果是空行，就跳过
			    		continue;
			    	else{              //否则将其分离，然后放到对应的数组中
			    		String[] data=line.split(tab);
			    		allId.add(Integer.parseInt(data[0]));
			    		String[] data1=data[1].split("T");
			    		allDate.add(data1[0]);
			    		String[] data2=data1[1].split(":");
			    		allHour.add(Integer.parseInt(data2[0]));
			    		allMin.add(Integer.parseInt(data2[1]));
			    		allLoc.add(Integer.parseInt(data[4]));
			    		Loc loc=new Loc();//因为locList只是在地图中使用，所以分离的时候直接把它存储起来
			    		loc.setLocId(Integer.parseInt(data[4]));
			    		loc.setxCoor(Double.parseDouble(data[2]));
			    		loc.setyCoor(Double.parseDouble(data[3]));
			    		locList.add(loc);
			    	}
			    
			    }
			    br.close();
			}catch(Exception e){
				e.printStackTrace();
	            System.out.println("读取文件出错");
			}
		}
		//得到同一天下的访问地点Lv
		private ArrayList<Lv> getLvSomedate(int dayStart,int dayend){
			ArrayList<Lv> lvList=new ArrayList<Lv>();
			for(int i=dayend;i>=dayStart;i--){//因为原始签到数据是从大到小的顺序，存储的时候就倒着存储了
				int sign=-1;//标记，用来标记该地点是否访问过
				Lv lv=new Lv();
				GenLoc genLoc=new GenLoc();
				for(int j=0;j<lvList.size();j++){//进行去重操作
					if(allLoc.get(i)==lvList.get(j).getGenLoc().singleId){
						sign=j;
						break;
					}
				}
				if(sign==-1){
				lv.setHour(allHour.get(i));
				lv.setMin(allMin.get(i));
				genLoc.setSingleId(allLoc.get(i));
				lv.setGenLoc(genLoc);
				lvList.add(lv);
				}
			}
			return lvList;
		}
		//得到同一个ID下的trajectory
		private ArrayList<Traj> getTrajSameId(int idStart,int idEnd){
			ArrayList<Traj> trajList=new ArrayList<>();
			int dayStart=idStart;
			String headDay=allDate.get(dayStart);//headDay开始时指向最开始的天

			for(int i=idStart;i<=idEnd;i++){
				//用于判断是否走到了最后的id，防止数组越界
				if(i == idEnd){
					Traj traj=new Traj();
					traj.setDate(headDay);
					traj.setLvList(getLvSomedate(dayStart,idEnd-1));
					trajList.add(traj);
					break;
				}
				if(!(allDate.get(i).equals(headDay))){
					Traj traj=new Traj();
					traj.setDate(headDay);
					traj.setLvList(getLvSomedate(dayStart,i-1));
					trajList.add(traj);					
					dayStart=i;
					headDay=allDate.get(i);
				}

			}
			return trajList;
		}
		public void storageDate(){//存储数据
		    
		    int idStart=0;
		    int headId=allId.get(0);
		    for(int i=0;i<allId.size();i++)
		    {
			    
			    if(headId!=allId.get(i))//找到同一个id的截至位置
			    {
			    	UserInfo userInfo=new UserInfo();
			    	userInfo.setId(headId);
			    	userInfo.setTrajList(getTrajSameId(idStart,i));
			    	userInfoList.add(userInfo);
				    idStart=i;
				    headId=allId.get(i);
				}
			    if(i==(allId.size()-1)){
			    	UserInfo userInfo=new UserInfo();
			    	userInfo.setId(headId);
			    	userInfo.setTrajList(getTrajSameId(idStart,i+1));
			    	userInfoList.add(userInfo);
			    }
			    
			}
		}
		public void getLocList(int id){//测试功能，用来测试locList里面存储的数据是否正确
			for(int i=0;i<locList.size();i++){
				if(locList.get(i).locId==id){
					System.out.println(locList.get(i).locId+" "+locList.get(i).xCoor+" "+locList.get(i).yCoor);
					break;
				}
			}
		}
//		public void getTrajById(int id){//测试功能，看其轨迹点是否正确
//	    	for(int i=0;i<userInfoList.size();i++){
//	    		if(userInfoList.get(i).id==id){
//	    			System.out.println(id);
//	    			ArrayList<Traj> t=userInfoList.get(i).trajList;
//	    			for(int j=0;j<t.size();j++){
//	    				System.out.println(t.get(j).date);
//	    				ArrayList<Lv> l=t.get(j).lvList;
//	    				for(int n=0;n<l.size();n++){
//	    					System.out.println(l.get(n).hour+" "+l.get(n).min+" "+l.get(n).genLoc.singleId);
//	    					//System.out.println(l.get(n).min);
//	    					//System.out.println(l.get(n).genLoc.singleId);
//	    				}
//	    			}
//	    		}
//	    	}
//	    }
}

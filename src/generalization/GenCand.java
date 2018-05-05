package generalization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import stoStructure.Lv;
import stoStructure.Traj;
import unsafeRule.R;
import unsafeRule.Rule;

public class GenCand {
	Rule r=new Rule();
	ArrayList<R> rlist=r.genRList(0, 0.5f);//得到用户的不安全规则
	public static ArrayList<Q> qList=new ArrayList<Q>();
	public void getGencand(ArrayList<Lv> lvList,double v,int a) {
		DisInfo.setDisInfo(0);//得到用户的距离信息（此处id放入用户id）
		HashMap<Integer, ArrayList<Dist>> disInfo=DisInfo.disInfo;
		//得到用户的不安全规则（此处的id放入序号id）
		for(int k=0;k<rlist.size();k++) {
			System.out.println(rlist.get(k).getrId()+" "+rlist.get(k).getTrajNume()+"  "+rlist.get(k).getTrajDemo()+" "+rlist.get(k).getConf());
		}
		if(lvList.size()==1) {
			System.out.println("该轨迹只有一个点不用泛化");
		}
		else {
			for(int i=0;i<lvList.size();i++) {
				ArrayList<Integer> candList=new ArrayList<>();//得到每个地点的候选集
				//如果是这条轨迹的第一个地点，进行以下处理
				if(i==0) {
					int loc=lvList.get(i+1).getLoc();//得到访问点中的地址
					double time=lvList.get(i+1).getHour()-lvList.get(i).getHour()+(lvList.get(i+1).getMin()-lvList.get(i).getMin())/60;
					double thed=time*v;
					ArrayList<Dist> distList=disInfo.get(loc);
					for(int j=0;j<distList.size();j++) {
						if(distList.get(j).getDist()<=thed) {
							if(r.PointPre(distList.get(j).getLoc())>=a) {
								ArrayList<R> rlistCopy=new ArrayList<>(rlist);
								int label=rChange(rlistCopy,lvList.get(i).getLoc(),distList.get(j).getLoc(),lvList);
								if(label==0) {
									candList.add(distList.get(j).getLoc());//得到候选地点
								}
							}
						}
					}
					
				}
				//如果是轨迹的最后一个地点
				else if(i==lvList.size()-1) {
					int loc=lvList.get(i-1).getLoc();//得到访问点中的地址 
					double time=lvList.get(i).getHour()-lvList.get(i-1).getHour()+(lvList.get(i).getMin()-lvList.get(i-1).getMin())/60;
					double thed=time*v;
					ArrayList<Dist> distList=disInfo.get(loc);
					for(int j=0;j<distList.size();j++) {
						if(distList.get(j).getDist()<=thed) {
							if(r.PointPre(distList.get(j).getLoc())>=a) {
								ArrayList<R> rlistCopy=new ArrayList<>(rlist);
								int label=rChange(rlistCopy,lvList.get(i).getLoc(),distList.get(j).getLoc(),lvList);
								if(label==0) {
									candList.add(distList.get(j).getLoc());//得到候选地点
								}
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
			        		ArrayList<R> rlistCopy=new ArrayList<>(rlist);
			        		int label=rChange(rlistCopy,lvList.get(i).getLoc(),candList1.get(j),lvList);
							if(label==0) {
								candList.add(candList1.get(j));
							}
			        	}
			        }
			        
				}
				System.out.println("点"+lvList.get(i).getLoc()+"的泛化点为");
				for(int j=0;j<candList.size();j++) {
					ArrayList<R> rlistCopy=new ArrayList<>(rlist);
					System.out.println(candList.get(j));
					Op op=new Op();
					op.setLocId(lvList.get(i).getLoc());
					op.setGenLoc(candList.get(j));
					Q q=new Q();
					q.setOp(op);
					System.out.println("候选匿名点为："+candList.get(j));
					double anonyGain=getAnonyGain(rlistCopy,lvList.get(i).getLoc(),candList.get(j),0.5,lvList);
					System.out.println("匿名收益为："+anonyGain);
					q.setAnonyGain(anonyGain);
					q.setScore(anonyGain);//因为预泛化的时候只是用一个点去泛化，信息损失为1，所以score=anonyGain
					qList.add(q);
				}
			}
		}
		
		
	}
	public int rChange(ArrayList<R> rlistCopy,int l1,int l2,ArrayList<Lv> lvList) {
		ArrayList<Integer> beforeList=new ArrayList<>();
		ArrayList<Integer> afterList=new ArrayList<>();
		//泛化之前的轨迹
	    for(int i=0;i<lvList.size();i++) {
	    	beforeList.add(lvList.get(i).getLoc());
	    }
	    //泛化后产生的轨迹
	    for(int i=0;i<lvList.size();i++) {
	    	if(lvList.get(i).getLoc()==l1) {
	    		afterList.add(l2);
	    	}
	    	else{
	    		afterList.add(lvList.get(i).getLoc());
	    	}
	    }
		for(int k=0;k<rlistCopy.size();k++) {
			boolean flag1=beforeList.containsAll(rlistCopy.get(k).getTrajNume().getTraj());
			//如果r的分子包含在list中
			if(flag1==true) {
				//r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag2=rlistCopy.get(k).getTrajNume().getTraj().contains(l1);
				if(flag2==true) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()-0.5);
				}
				
			}
			boolean flag7=afterList.containsAll(rlistCopy.get(k).getTrajNume().getTraj());
			if(flag7==true) {
				//r的规则中的分子中含用来泛化的地点，这条规则的条数就加0.5
				boolean flag3=rlistCopy.get(k).getTrajNume().getTraj().contains(l2);
				if(flag3==true) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()+0.5);
				}
			}
			//如果r的分母包含在list中
			boolean flag4=beforeList.containsAll(rlistCopy.get(k).getTrajDemo().getTraj());
			if(flag4==true) {
				//r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag5=rlistCopy.get(k).getTrajDemo().getTraj().contains(l1);
				if(flag5==true) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()-0.5);
				}
				
			}
			boolean flag8=afterList.containsAll(rlistCopy.get(k).getTrajDemo().getTraj());
			if(flag8==true) {
				//r的规则中的分子中含用来泛化的地点，这条规则的条数就加0.5
				boolean flag6=rlistCopy.get(k).getTrajDemo().getTraj().contains(l2);
				if(flag6==true) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()+0.5);
				}
			}
		}
		int label=0;
		int sum=0;
		for(int k=0;k<rlistCopy.size();k++) {
			//重新计算r中不安全规则的置信度conf，如果其大于原始的conf，则不符合
			double conf=rlistCopy.get(k).getTrajNume().getCount()/rlistCopy.get(k).getTrajDemo().getCount();
//			System.out.println("xin:"+conf);
//			System.out.println("jiu:"+rlistCopy.get(k).getConf());
			if(rlistCopy.get(k).getConf()<conf) {
				label=1;
				break;
			}
			if(rlistCopy.get(k).getConf()==conf) {
				sum++;
			}
			
		}
		if(label==0&&sum!=rlistCopy.size())
			return 0;
		else 
			return 1;
	}
	public double getAnonyGain(ArrayList<R> rlistCopy,int l1,int l2,double thed,ArrayList<Lv> lvList) {
		ArrayList<Integer> list=new ArrayList<>();
	    for(int i=0;i<lvList.size();i++) {
	    	list.add(lvList.get(i).getLoc());
	    }
		for(int k=0;k<rlistCopy.size();k++) {
			boolean flag1=list.containsAll(rlistCopy.get(k).getTrajNume().getTraj());
			//如果r的分子包含在list中
			if(flag1==true) {
				//r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag2=rlistCopy.get(k).getTrajNume().getTraj().contains(l1);
				if(flag2==true) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()-0.5);
				}
				//r的规则中的分子中含用来泛化的地点，这条规则的条数就加0.5
				boolean flag3=rlistCopy.get(k).getTrajNume().getTraj().contains(l2);
				if(flag3==true) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()+0.5);
				}
			}
			//如果r的分母包含在list中
			boolean flag4=list.containsAll(rlistCopy.get(k).getTrajDemo().getTraj());
			if(flag4==true) {
				//r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag5=rlistCopy.get(k).getTrajDemo().getTraj().contains(l1);
				if(flag5==true) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()-0.5);
				}
				//r的规则中的分子中含用来泛化的地点，这条规则的条数就加0.5
				boolean flag6=rlistCopy.get(k).getTrajDemo().getTraj().contains(l2);
				if(flag6==true) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()+0.5);
				}
			}
		}
		double annoyGain=0;
		for(int k=0;k<rlistCopy.size();k++) {
			double conf=rlistCopy.get(k).getTrajNume().getCount()/rlistCopy.get(k).getTrajDemo().getCount();
			if(conf<=thed) {
				annoyGain=annoyGain+1;
			}
			else {
				System.out.println("旧conf为："+rlistCopy.get(k).getConf());
				System.out.println("新conf为："+conf);
				double fr=(rlistCopy.get(k).getConf()-conf)/(conf-thed);
				annoyGain=annoyGain+fr;
				System.out.println("fr为："+fr);
			}
				
		}
		return annoyGain;
	}
	public void suppress(ArrayList<Traj> trajList) throws ClassNotFoundException, IOException {
		//ArrayList<R> rlistCopy=new ArrayList<>(rlist);
		int count=locSize(trajList);//得到用户的地点访问个数
		for(int i=0;i<trajList.size();i++) {
			//得到轨迹的地点
			ArrayList<Integer> locList=new ArrayList<>();
			for(int j=0;j<trajList.get(i).getLvList().size();j++) {
				locList.add(trajList.get(i).getLvList().get(j).getLoc());
			}
			
			for(int j=0;j<rlist.size();j++) {
				ArrayList<R> rlistCopy=deepCopy(rlist);
				ArrayList<Integer> list=new ArrayList<>(rlist.get(j).getTrajNume().getTraj());
				//list=rlist.get(j).getTrajNume().getTraj();//得到分子的轨迹
				//如果用户的这条轨迹包含r中的轨迹
				if(locList.containsAll(list)) {
					list.removeAll(rlist.get(j).getTrajDemo().getTraj());
					//得到敏感地点
					int senLoc=list.get(0);
					Op op=new Op();
					op.setLocId(senLoc);
					double anonyGain=annoyGainS(rlistCopy,senLoc,0.5,locList);
					double infoLoss=Math.log(count)/Math.log(2);
					double score=anonyGain/infoLoss;
					Q q=new Q();
					q.setOp(op);
					q.setAnonyGain(anonyGain);  
					q.setInfoLoss(infoLoss);
					q.setScore(score);
					qList.add(q);
				}
				
				
			}
		}
	}
	//
	public double annoyGainS(ArrayList<R> rlistCopy,int loc,double thed,ArrayList<Integer> locList) {
		for(int i=0;i<rlistCopy.size();i++) {
			boolean flag1=rlistCopy.get(i).getTrajNume().getTraj().contains(loc);
			if(flag1==true) {
				boolean flag2=locList.containsAll(rlistCopy.get(i).getTrajNume().getTraj());
				if(flag2==true) {
					rlistCopy.get(i).getTrajNume().setCount(rlistCopy.get(i).getTrajNume().getCount()-1);
				}
			}
			//如果r的分母包含在list中
			boolean flag3=rlistCopy.get(i).getTrajDemo().getTraj().contains(loc);
			if(flag3==true) {
				//r的规则中的分子含有被泛化的地点的话，这条规则的条数就减1
				boolean flag4=locList.containsAll(rlistCopy.get(i).getTrajDemo().getTraj());
				if(flag4==true) {
					rlistCopy.get(i).getTrajDemo().setCount(rlistCopy.get(i).getTrajDemo().getCount()-1);
				}
			}
		}
		double annoyGain=0;
		for(int k=0;k<rlistCopy.size();k++) {
			double conf=rlistCopy.get(k).getTrajNume().getCount()/rlistCopy.get(k).getTrajDemo().getCount();
			if(conf<=thed) {
				System.out.println("conf<thed其值为："+conf);
				annoyGain=annoyGain+1;
			}
			else {
				System.out.println("旧conf为："+rlistCopy.get(k).getConf());
				System.out.println("新conf为："+conf);
				double fr=(rlistCopy.get(k).getConf()-conf)/(conf-thed);
				annoyGain=annoyGain+fr;
				System.out.println("fr为："+fr);
			}
				
		}
		return annoyGain;
	}
	private int locSize(ArrayList<Traj> trajList) {
		int flag=0;
		ArrayList<Integer> locList=new ArrayList<>();
		for(int i=0;i<trajList.size();i++) {
			for(int j=0;j<trajList.get(i).getLvList().size();j++) {
				for(int k=0;k<locList.size();k++) {
					if(trajList.get(i).getLvList().get(j).getLoc()==locList.get(k)) {
						flag=1;
						break;
					}	
				}
				if(flag==0) {
					locList.add(trajList.get(i).getLvList().get(j).getLoc());
				}
			}
			
		}
		return locList.size();
	}
	public void getQList(ArrayList<Traj> trajList) throws ClassNotFoundException, IOException {
		for(int i=0;i<trajList.size();i++) {
			getGencand(trajList.get(i).getLvList(), 0.032424025, 2);
		}
		suppress(trajList);
	}
	//进行list的深拷贝
	public static <T> ArrayList<T> deepCopy(ArrayList<T> src) throws IOException, ClassNotFoundException {  
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
	    out.writeObject(src);  

	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
	    ObjectInputStream in = new ObjectInputStream(byteIn);  
	    @SuppressWarnings("unchecked")  
	    List<T> dest = (List<T>) in.readObject();  
	    return (ArrayList<T>) dest;  
	}  


}

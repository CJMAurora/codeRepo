package generalization;

import java.util.ArrayList;
import java.util.HashMap;
import stoStructure.*;
import unsafeRule.R;
import unsafeRule.Rule;

public class CandZone {
	ArrayList<Q> qList=new ArrayList<Q>();
	public void getCandZone(ArrayList<Lv> lvList,double v,int a) {
		DisInfo.setDisInfo(4);//得到用户的距离信息（此处id放入用户id）
		HashMap<Integer, ArrayList<Dist>> disInfo=DisInfo.disInfo;
		Rule r=new Rule();
		ArrayList<R> rlist=r.genRList(2, 0.5f);//得到用户的不安全规则（此处的id放入序号id）
		for(int k=0;k<rlist.size();k++) {
			System.out.println(rlist.get(k).getrId()+" "+rlist.get(k).getTrajNume()+"  "+rlist.get(k).getTrajDemo()+" "+rlist.get(k).getConf());
		}
		if(lvList.size()==1) {
			System.out.println("该轨迹只有一个点不用泛化");
		}
		else {
			for(int i=0;i<lvList.size();i++) {
				ArrayList<Integer> candList=new ArrayList<>();//得到地点的候选集
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
								int label=rChange(rlistCopy,lvList.get(i).getLoc(),distList.get(j).getLoc());
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
								int label=rChange(rlistCopy,lvList.get(i).getLoc(),distList.get(j).getLoc());
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
			        		int label=rChange(rlistCopy,lvList.get(i).getLoc(),candList1.get(j));
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
					double anonyGain=getAnonyGain(rlistCopy,lvList.get(i).getLoc(),candList.get(j),0.5);
					System.out.println("匿名收益为："+anonyGain);
					
				}
			}
		}
		
		
	}
	public int rChange(ArrayList<R> rlistCopy,int l1,int l2) {
		for(int k=0;k<rlistCopy.size();k++) {
			for(int t=0;t<rlistCopy.get(k).getTrajNume().getTraj().size();t++) {
				//如果r的规则中的分子中含用来泛化的地点的话，这条规则的条数就加0.5
				if(rlistCopy.get(k).getTrajNume().getTraj().get(t)==l2) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()+0.5);
				}
				
				//如果r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				if(rlistCopy.get(k).getTrajNume().getTraj().get(t)==l1) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()-0.5);
				}
				
			}
			for(int t=0;t<rlistCopy.get(k).getTrajDemo().getTraj().size();t++) {
				//如果r的规则中的分母中含用来泛化的地点的话，这条规则的条数就加0.5
				if(rlistCopy.get(k).getTrajDemo().getTraj().get(t)==l2) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()+0.5);
				}
				//如果r的规则中的分母含有被泛化的地点的话，这条规则的条数就减0.5
				if(rlistCopy.get(k).getTrajDemo().getTraj().get(t)==l1) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()-0.5);
				}
			}
		}
		int label=0;
		System.out.println(l1+" "+l2);
		for(int k=0;k<rlistCopy.size();k++) {
			//重新计算r中不安全规则的置信度conf，如果其大于原始的conf，则不符合
			double conf=rlistCopy.get(k).getTrajNume().getCount()/rlistCopy.get(k).getTrajDemo().getCount();
			System.out.println("xin:"+conf);
			System.out.println("jiu:"+rlistCopy.get(k).getConf());
			if(rlistCopy.get(k).getConf()<conf) {
				label=1;
				break;
			}
		}
		if(label==0)
			return 0;
		else 
			return 1;
	}
	public double getAnonyGain(ArrayList<R> rlistCopy,int l1,int l2,double thed) {
		for(int k=0;k<rlistCopy.size();k++) {
			for(int t=0;t<rlistCopy.get(k).getTrajNume().getTraj().size();t++) {
				//如果r的规则中的分子中含用来泛化的地点的话，这条规则的条数就加0.5
				if(rlistCopy.get(k).getTrajNume().getTraj().get(t)==l2) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()+0.5);
				}
				
				//如果r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				if(rlistCopy.get(k).getTrajNume().getTraj().get(t)==l1) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()-0.5);
				}
				
			}
			for(int t=0;t<rlistCopy.get(k).getTrajDemo().getTraj().size();t++) {
				//如果r的规则中的分母中含用来泛化的地点的话，这条规则的条数就加0.5
				if(rlistCopy.get(k).getTrajDemo().getTraj().get(t)==l2) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()+0.5);
				}
				//如果r的规则中的分母含有被泛化的地点的话，这条规则的条数就减0.5
				if(rlistCopy.get(k).getTrajDemo().getTraj().get(t)==l1) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()-0.5);
				}
			}
		}
		double annoyGain=0;
		for(int k=0;k<rlistCopy.size();k++) {
			double conf=rlistCopy.get(k).getTrajNume().getCount()/rlistCopy.get(k).getTrajDemo().getCount();
			if(conf<thed) {
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

}

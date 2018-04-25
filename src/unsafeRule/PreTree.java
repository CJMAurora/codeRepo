package unsafeRule;
  import java.util.ArrayList;
import java.util.HashMap;

import stoStructure.*;

public  class PreTree {
	private static Node root=new Node();//前缀树的根
	static Node p=root;//p是指针，用来创建和遍历前缀树
    private static void preTree(ArrayList<ArrayList<Integer>> allSubTraj){
		
		for(int i=0;i<allSubTraj.size();i++){
			ArrayList<Integer> subTraj=allSubTraj.get(i);
			for(int j=0;j<subTraj.size();j++){//得到轨迹中的每个地点
				if(!p.map.containsKey(subTraj.get(j))){//如果不包含这个子节点，就新建一个节点
					Node node = new Node();
					node.locId = subTraj.get(j);//把地点id放入到locId中
					p.sonNodes.add(node);//然后将这个节点放入子节点中
					//取出他所在的索引值
					int index = p.sonNodes.indexOf(node);
		      		p.map.put(subTraj.get(j),index);//把这个节点的值和位置放在hashmap中
		      		if(j==subTraj.size()-1){//如果这条轨迹遍历完了，就将其值加1
		      			p.sonNodes.get(index).value++;
		      			p=root;//p指针重新指向root
		      			break;
		      		}
		      		else{
		      			p=p.sonNodes.get(index);//如果不是的话就继续往下遍历
		      		}
				}
				else{
					int index = p.map.get(subTraj.get(j));//取出这个点的位置
					if(j==subTraj.size()-1){//如果是最后一个点，就让其value加1
		      			p.sonNodes.get(index).value++;
		      			p=root;
		      			break;
		      		}
					p=p.sonNodes.get(index);//如果不是的话就继续往下遍历
				}
			}
		}
	}
    public static void genPreTree(ArrayList<Traj> trajList){//给定一个人的轨迹，得到其生成的前缀树
    	Function f=new Function();
		for(int b=0;b<trajList.size();b++){
			ArrayList<Lv> lvList=trajList.get(b).getLvList();
			ArrayList<ArrayList<Integer>> alltraj=f.getAllSubTraj(lvList);
//			for(int i=0;i<alltraj.size();i++){//此处的打印，是为了测试
//				String str=i+" ";
//				for(int j=0;j<alltraj.get(i).size();j++){
//					 str +=alltraj.get(i).get(j)+"->";
//				}
//				System.out.println(str);
//			}
		    preTree(alltraj);
		}
    }
    public int trajFre(ArrayList<Integer> subTraj){//输入子轨迹，得到子轨迹的频度
    	int count=0;
    	for(int i=0;i<subTraj.size();i++){
    		if(!p.map.containsKey(subTraj.get(i))){
    			System.out.println("无该轨迹！");
    			break;
    		}
    		else{
    			int index = p.map.get(subTraj.get(i));//如果包含该地点，就接着往下查找，直到该轨迹中的点遍历完，得到频度值
    			if(i==subTraj.size()-1){//如果是最后一个点，就让其value加1
    			    count=p.sonNodes.get(index).value;
    			    p=root;
	      			break;
	      		}
    			p=p.sonNodes.get(index);
    		}
    	}
    	return count;
    }
    public int PointPre(int loc){//给定一个地点，得到该地点的个数
    	int count=0;
    	if(!p.map.containsKey(loc)){
			System.out.println("无该轨迹！");
			
		}
		else{
			int index = p.map.get(loc);
			count=p.sonNodes.get(index).value;
			p=root;
      		}
	    return count;
    }
}
class Node{
	int locId;
	int value=0;
	ArrayList<Node> sonNodes=new ArrayList<Node>();
	HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
}
package generalization;

import java.io.IOException;
import java.util.ArrayList;

import roadNetwork.EdgeSet;
import roadNetwork.NodeSet;
import stoStructure.Storage;
/**
 * 
 * @author Aurora
 *工程的第一步，先得到用户的随机敏感点，运行类RanSeq
 */
public class testTrajAnony {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Storage s=new Storage();
		s.putDataFromFile();
		s.storageDate();
		EdgeSet e=new EdgeSet();
		NodeSet n=new NodeSet();
		e.readEdge();
		n.readData();
		//第二步，运行PutFIle函数，把用户任意两点之间的距离存起来
//		String path="F:\\trajPrivacy\\distance\\";
//		PutFile pf=new PutFile(0,path);//此处id不是用户id
//		ArrayList<Integer> locList= pf.getLocList();
//		for(int i=0;i<locList.size();i++) {
//			System.out.println(locList.get(i));
//		}
//		pf.writeFile();
		TrajAnony ta=new TrajAnony();
		ta.update();
	}

}

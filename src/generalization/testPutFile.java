package generalization;

import java.util.ArrayList;

import roadNetwork.EdgeSet;
import roadNetwork.NodeSet;
import stoStructure.Storage;
/**
 * 
 * @author Aurora
 *程序运行的第一步，先得到用户经过的地点任意两点之间的距离
 *注意id指的是不是用户id
 */

public class testPutFile {
	public static void main(String[] args) {
		Storage s=new Storage();
		s.putDataFromFile();
		s.storageDate();
		EdgeSet e=new EdgeSet();
		NodeSet n=new NodeSet();
		e.readEdge();
		n.readData();
		String path="F:\\trajPrivacy\\distance\\";
		PutFile pf=new PutFile(0,path);//此处id不是用户id
		ArrayList<Integer> locList= pf.getLocList();
		for(int i=0;i<locList.size();i++) {
			System.out.println(locList.get(i));
		}
		pf.writeFile();
	}

}

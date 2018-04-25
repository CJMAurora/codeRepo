package generalization;

import java.util.ArrayList;

import roadNetwork.EdgeSet;
import roadNetwork.NodeSet;
import stoStructure.Storage;

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
		PutFile pf=new PutFile(2,path);
		ArrayList<Integer> locList= pf.getLocList();
		for(int i=0;i<locList.size();i++) {
			System.out.println(locList.get(i));
		}
		pf.writeFile();
	}

}

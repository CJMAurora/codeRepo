package roadNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EdgeSet {
	public static ArrayList<Edge> edgeList=new ArrayList<>();
	public void readEdge() {
		try {
			String tab=" ";
			File file=new File("E:\\edge.txt");
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=null;
			while((line=br.readLine())!=null) {
				if(line.equals(""))
		    		continue;
				else {
					String data[]=line.split(tab);
					Edge edge=new Edge();
					edge.setEdgeId(Integer.parseInt(data[0]));
					edge.setStartNode(Integer.parseInt(data[1]));
					edge.setEndNode(Integer.parseInt(data[2]));
					edge.setDistance(Double.parseDouble(data[3]));
					edgeList.add(edge);
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
            System.out.println("��ȡ�ļ�����");
		}
	}

}

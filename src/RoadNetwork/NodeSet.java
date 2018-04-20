package roadNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class NodeSet {
	public static ArrayList<Node> nodeList=new ArrayList<>();
	public void readData() {
		try {
			String tab=" ";
			File file=new File("E:\\node.txt");//�ļ�
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=null;
			while((line=br.readLine())!=null){
				if(line.equals(""))//����ǿ��У�������
		    		continue;
				else {
					String data[]=line.split(tab);
					Node node=new Node();
					node.setNodeId(Integer.parseInt(data[0]));
					node.setyCoor(Double.parseDouble(data[1]));
					node.setxCoor(Double.parseDouble(data[2]));
					nodeList.add(node);
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
            System.out.println("��ȡ�ļ�����");
		}
	}

}

package generalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import stoStructure.Storage;
import stoStructure.Traj;

/**
 * 
 * @author Aurora
 *该类完成的功能对一个点与其所有可达点的距离进行排序
 *首先从文件中读出两点之间的距离信息
 *然后按照设定的类格式对其进行存储
 *最后用sort方法进行排序
 */

public class DisInfo {
	public static HashMap<Integer, ArrayList<Dist>> disInfo = new HashMap<>();

	public static void setDisInfo(int userId) {
		String path = "F:\\trajPrivacy\\distance\\" + userId + ".txt";
		String tab = " ";
		String line=null;
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));// br是字符流
			//String line = null;
			while ((line = br.readLine()) !=null) {
				String[] data = line.split(tab);
				int loc1 = Integer.parseInt(data[0]);
				int loc2 = Integer.parseInt(data[1]);
				double dist = Double.parseDouble(data[2]);
				if (disInfo.containsKey(loc1)) {
					ArrayList<Dist> distList = disInfo.get(loc1);
					int sign = 0;
					for (int i = 0; i < distList.size(); i++) {
						if (distList.get(i).getLoc() == loc2) {
							sign = 1;
							break;
						}
					}
					if (sign == 0) {
						Dist d = new Dist();
						d.setLoc(loc2);
						d.setDist(dist);
						distList.add(d);
					}
				}
				else {
					Dist d1 = new Dist();
					d1.setLoc(loc2);
					d1.setDist(dist);
					ArrayList<Dist> distList1 = new ArrayList<>();
					distList1.add(d1);
					disInfo.put(loc1, distList1);
				}
				if (disInfo.containsKey(loc2)) {
					ArrayList<Dist> distList = disInfo.get(loc2);
					int sign = 0;
					for (int i = 0; i < distList.size(); i++) {
						if (distList.get(i).getLoc() == loc1) {
							sign = 1;
							break;
						}
					}
					if (sign == 0) {
						Dist d = new Dist();
						d.setLoc(loc1);
						d.setDist(dist);
						distList.add(d);
					}
				} else {
					Dist d2 = new Dist();
					d2.setLoc(loc1);
					d2.setDist(dist);
					ArrayList<Dist> distList2 = new ArrayList<>();
					distList2.add(d2);
					disInfo.put(loc2, distList2);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(line);
			System.out.println("读取文件出错");
		}
		/**
		 * 将ArrayList<Dist>进行排序（从小到大）
		 */
		Iterator<Map.Entry<Integer, ArrayList<Dist>>> iterator2 = disInfo.entrySet().iterator();
		int num=0;
		while (iterator2.hasNext()) {
			num++;
			Map.Entry<Integer, ArrayList<Dist>> entry = iterator2.next();
			System.out.println(entry.getKey());
			Collections.sort(entry.getValue());
			System.out.println(num);
			System.out.println(entry.getValue().size());
			for (int i = 0; i < entry.getValue().size(); i++) {
				System.out.println(entry.getValue().get(i).getLoc() + " " + entry.getValue().get(i).getDist());
			}
		}
	}
}

class Dist implements Comparable<Dist> {
	private int loc;
	private double dist;

	public int getLoc() {
		return loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	@Override
	public int compareTo(Dist arg0) {
		// TODO Auto-generated method stub
		if (this.dist > arg0.dist)
			return 1;
		else if (this.dist < arg0.dist)
			return -1;
		return 0;
	}

}
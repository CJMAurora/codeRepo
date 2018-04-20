package unsafeRule;
import java.util.ArrayList;

import stoStructure.*;

/**
 * 
 * @author Aurora
 *函数getAllSubTraj()得到一条轨迹的所有子轨迹
 *函数getSenTraj()是得到包含敏感地点的所有子轨迹
 */
public class Function {
	public ArrayList<ArrayList<Integer>> getAllSubTraj(ArrayList<Lv> list) { // 得到总集s的所有子集allsubsets
		ArrayList<ArrayList<Integer>> allSubTraj = new ArrayList<ArrayList<Integer>>();
		int max = 1 << list.size();// 左移list.size()位
		for (int j = 0; j < max; j++) {
			int index = 0;
			int k = j; // 开始的节点
			ArrayList<Integer> subtra = new ArrayList<Integer>();// 用ArrayList存储子轨迹
			while (k > 0) {
				if ((k & 1) > 0) { // k & 1 与运算，结果是两个 1，0
					subtra.add(list.get(index).getGenLoc().getSingleId());
				}
				k >>= 1; // 右移位赋值运算符 等同与 k = k >> 1 即，k除以2
				index++;
			}
			if (subtra.size() != 0) {// 排除空子集
				allSubTraj.add(subtra);
			}
		}
		return allSubTraj;
	}

	public ArrayList<ArrayList<Integer>> getSenTraj(ArrayList<Lv> list, int senP) {
		ArrayList<ArrayList<Integer>> senTraj = new ArrayList<ArrayList<Integer>>();
		int max = 1 << list.size();// 左移list.size()位
		for (int j = 0; j < max; j++) {
			int index = 0;
			int k = j; // 开始的节点
			int visit = 0;
			ArrayList<Integer> subtra = new ArrayList<Integer>();// 用ArrayList存储子轨迹
			while (k > 0) {
				if ((k & 1) > 0) { // k & 1 与运算，结果是两个 1，0
					int loc = list.get(index).getGenLoc().getSingleId();// 得到这条轨迹中的点
					if (loc == senP) {
						visit = 1;
					}
					subtra.add(loc);
				}
				k >>= 1; // 右移位赋值运算符 等同与 k = k >> 1 即，k除以2
				index++;
			}
			if (subtra.size() != 0 && visit == 1) {// 排除空子集
				senTraj.add(subtra);
			}
		}
		return senTraj;
	}


}

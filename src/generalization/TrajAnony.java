package generalization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import stoStructure.*;
import unsafeRule.*;

public class TrajAnony {

	public void update() throws ClassNotFoundException, IOException {
		Rule r = new Rule();
		ArrayList<R> rlist = r.genRList(2, 0.5f);// 得到用户的不安全规则此处id不是用户id
		GenCand gc = new GenCand(rlist);
		ArrayList<Traj> trajList = Storage.userInfoList.get(2).getTrajList();
		// 得到qlist
		ArrayList<Q> qList = gc.getQList(trajList);
		for (int k = 0; k < qList.size(); k++) {
			System.out.println("操作：{" + qList.get(k).getOp().getTrajId() + ":" + qList.get(k).getOp().getLocId() + ","
					+ qList.get(k).getOp().getGenLoc() + "}" + qList.get(k).getAnonyGain() + " "
					+ qList.get(k).getInfoLoss() + qList.get(k).getScore());
		}
		while (rlist.size()!=0) {
			// 取出score值最高的
			Q q = qList.get(0);
			Op op = q.getOp();
			// 根据操作op的轨迹id得到这条轨迹的访问点序列
			ArrayList<Lv> lvList = trajList.get(op.getTrajId()).getLvList();
			ArrayList<Integer> locList = new ArrayList<>();
			for (int i = 0; i < lvList.size(); i++) {
				locList.add(lvList.get(i).getLoc());
			}
			// 如果泛化点为0，说明为抑制操作，需要把LvList中相应的点删除
			if (op.getGenLoc() == 0) {
				for (int i = 0; i < lvList.size(); i++) {
					if (lvList.get(i).getLoc() == op.getLocId()) {
						lvList.remove(i);
						// 删除地点之后更新rlist
						rlist=updateRs(rlist, op.getLocId(), 0.5, locList);
						qList=updateQs(op,qList, rlist,  trajList);
						Collections.sort(qList);
					}

				}
			}
			// 如果不为0，说明为泛化操作
			else {
				int tab = 0;
				for (int i = 0; i < lvList.size(); i++) {
					if (lvList.get(i).getLoc() == op.getLocId()) {
						// 把操作的泛化地点加入到locLis中
						tab = i;
						ArrayList<Integer> list=new ArrayList<>();
						list.add(op.getGenLoc());
						lvList.get(i).getGenLoc().setLocList(list);
						// 泛化之后更新rlist
						rlist=updateRg(rlist, op, lvList.get(i).getGenLoc().getLocList(), 0.5, locList, tab);
						qList = updateQg(lvList, op, 0.032424025, qList, rlist, trajList);
						Collections.sort(qList);
					}
				}
			}
			for (int k = 0; k < qList.size(); k++) {
				System.out.println("操作：{" + qList.get(k).getOp().getTrajId() + ":" + qList.get(k).getOp().getLocId()
						+ "," + qList.get(k).getOp().getGenLoc() + "}" + qList.get(k).getAnonyGain() + " "
						+ qList.get(k).getInfoLoss() + qList.get(k).getScore());
			}
		}
	}

	public ArrayList<R> updateRs(ArrayList<R> rlist, int loc, double thed, ArrayList<Integer> locList) {
		// 得到用户访问点
		for (int i = 0; i < rlist.size(); i++) {
			boolean flag1 = rlist.get(i).getTrajNume().getTraj().contains(loc);
			if (flag1 == true) {
				boolean flag2 = locList.containsAll(rlist.get(i).getTrajNume().getTraj());
				if (flag2 == true) {
					rlist.get(i).getTrajNume().setCount(rlist.get(i).getTrajNume().getCount() - 1);
				}
			}
			// 如果r的分母包含在list中
			boolean flag3 = rlist.get(i).getTrajDemo().getTraj().contains(loc);
			if (flag3 == true) {
				// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减1
				boolean flag4 = locList.containsAll(rlist.get(i).getTrajDemo().getTraj());
				if (flag4 == true) {
					rlist.get(i).getTrajDemo().setCount(rlist.get(i).getTrajDemo().getCount() - 1);
				}
			}
		}
		for (int k = 0; k < rlist.size(); k++) {
			double conf = rlist.get(k).getTrajNume().getCount() / rlist.get(k).getTrajDemo().getCount();
			if (conf <= thed) {
				System.out.println("抑制更新R：conf<thed其值为：" + conf);
				rlist.remove(k);
				k--;
			}
			else
				System.out.println("抑制更新R：conf的值为：" + conf);
		}
		return rlist;
	}

	public ArrayList<R> updateRg(ArrayList<R> rlist, Op op, ArrayList<Integer> genList, double thed, ArrayList<Integer> locList,
			int tab) {
		// 先对含有被泛化地点的规则处理，改变conf
		for (int k = 0; k < rlist.size(); k++) {
			boolean flag1 = locList.containsAll(rlist.get(k).getTrajNume().getTraj());
			// 如果r的分子包含在list中
			if (flag1 == true) {
				// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag2 = rlist.get(k).getTrajNume().getTraj().contains(op.getLocId());
				double num1=(genList.size() - 1) / genList.size();
				int x=genList.size() + 1;
				double num2=(double)genList.size() / x;
				if (flag2 == true) {
					rlist.get(k).getTrajNume().setCount(rlist.get(k).getTrajNume().getCount()
							+ ((double)(genList.size() - 1) / (double)genList.size()) - ((double)genList.size() /(double) (genList.size() + 1)));
				}
		
			}
			// 如果r的分母包含在list中
			boolean flag3 = locList.containsAll(rlist.get(k).getTrajDemo().getTraj());
			if (flag3 == true) {
				// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag4 = rlist.get(k).getTrajDemo().getTraj().contains(op.getLocId());
				if (flag4 == true) {
					rlist.get(k).getTrajDemo().setCount(rlist.get(k).getTrajDemo().getCount()
							+ ((double)(genList.size() - 1) / (double)genList.size()) - ((double)genList.size() / (double)(genList.size() + 1)));
				}
				
			}

		}
		// 再对泛化点进行处理
		for (int i = 0; i < genList.size(); i++) {
			// 将locList中被泛化点的位置修改为泛化点的位置
			locList.set(tab, genList.get(i));
			if (i == genList.size() - 1) {
				for (int k = 0; k < rlist.size(); k++) {
					boolean flag1 = locList.containsAll(rlist.get(k).getTrajNume().getTraj());
					// 如果r的分子包含在list中
					if (flag1 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag2 = rlist.get(k).getTrajNume().getTraj().contains(genList.get(i));
						if (flag2 == true) {
							rlist.get(k).getTrajNume()
									.setCount(rlist.get(k).getTrajNume().getCount() + (1 / (double)(genList.size() + 1)));
						}
					}
					// 如果r的分母包含在list中
					boolean flag3 = locList.containsAll(rlist.get(k).getTrajDemo().getTraj());
					if (flag3 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag4 = rlist.get(k).getTrajDemo().getTraj().contains(genList.get(i));
						if (flag4 == true) {
							rlist.get(k).getTrajDemo()
									.setCount(rlist.get(k).getTrajDemo().getCount() + (1 / (double)(genList.size() + 1)));
						}
					}

				}
			} else {
				for (int k = 0; k < rlist.size(); k++) {
					boolean flag1 = locList.containsAll(rlist.get(k).getTrajNume().getTraj());
					// 如果r的分子包含在list中
					if (flag1 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag2 = rlist.get(k).getTrajNume().getTraj().contains(genList.get(i));
						if (flag2 == true) {
							rlist.get(k).getTrajNume().setCount(rlist.get(k).getTrajNume().getCount()
									- ((double)(genList.size() - 1) / (double)genList.size()) + (1 / (double)(genList.size() + 1)));
						}
					}
					// 如果r的分母包含在list中
					boolean flag3 = locList.containsAll(rlist.get(k).getTrajDemo().getTraj());
					if (flag3 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag4 = rlist.get(k).getTrajDemo().getTraj().contains(genList.get(i));
						if (flag4 == true) {
							rlist.get(k).getTrajDemo().setCount(rlist.get(k).getTrajDemo().getCount()
									- ((double)(genList.size() - 1) /(double) genList.size()) + (1 / (double)(genList.size() + 1)));
						}
					}

				}
			}
		}
		for (int k = 0; k < rlist.size(); k++) {
			double conf = rlist.get(k).getTrajNume().getCount() / rlist.get(k).getTrajDemo().getCount();
			if (conf <= thed) {
				//System.out.println("泛化更新R：conf<thed其值为：" + conf);
				rlist.remove(k);
				k--;
			}
			else
				System.out.println("泛化更新R：conf的值为：" + conf);
		}
		return rlist;
	}

	public ArrayList<Q> updateQg(ArrayList<Lv> lvList, Op op, double v, ArrayList<Q> qList, ArrayList<R> rlist,
			ArrayList<Traj> trajList) throws ClassNotFoundException, IOException {
		// 先看op操作执行后，Q中的其他操作是否满足时空约束，如果不满足，就将其删除
		DisInfo.setDisInfo(0);// 得到用户的距离信息（此处id放入用户id）
		HashMap<Integer, ArrayList<Dist>> disInfo = DisInfo.disInfo;
		int index = 0;
		// 取出被泛化点的位置
		for (int i = 0; i < lvList.size(); i++) {
			if (lvList.get(i).getLoc() == op.getLocId()) {
				index = i;
				break;
			}
		}
		if(index==0) {
			// 得到被泛化点后一个位置地点
			int afterL = lvList.get(index + 1).getLoc();
			for (int i = 1; i < qList.size(); i++) {
				// 找到Q中有对同一条轨迹进行操作的操作
				if (qList.get(i).getOp().getTrajId() == op.getTrajId()) {
					if (qList.get(i).getOp().getLocId() == afterL) {
						if(qList.get(i).getOp().getGenLoc()!=0) {
							// 得到v*(t[i]-t[i-1])阈值
							double time = lvList.get(index + 1).getHour() - lvList.get(index).getHour()
									+ (lvList.get(index + 1).getMin() - lvList.get(index).getMin()) / 60;
							double thed = time * v;
							// 得到后一个地点的泛化地点的距离集
							ArrayList<Dist> distList = disInfo.get(qList.get(i).getOp().getGenLoc());
							double dist = 0;
							for (int k = 0; k < distList.size(); k++) {
								if (distList.get(k).getLoc() == op.getGenLoc()) {
									// 得到Dist(l',l)
									dist = distList.get(k).getDist();
									break;
								}
							}
							// 如果前一地点的泛化点到该操作的地点的泛化点的距离大于thed，则删除qList中对前一地点的泛化操作
							if (dist > thed) {
								qList.remove(i);
							}
						}
						
					}
				}

			}
		}
		else if(index==lvList.size()-1) {
			// 得到被泛化点前一个位置地点
			int beforeL = lvList.get(index - 1).getLoc();
			for (int i = 1; i < qList.size(); i++) {
				// 找到Q中有对同一条轨迹进行操作的操作
				if (qList.get(i).getOp().getTrajId() == op.getTrajId()) {
					if (qList.get(i).getOp().getLocId() == beforeL) {
						if(qList.get(i).getOp().getGenLoc()!=0) {
							// 得到v*(t[i]-t[i-1])阈值
							double time = lvList.get(index).getHour() - lvList.get(index - 1).getHour()
									+ (lvList.get(index).getMin() - lvList.get(index - 1).getMin()) / 60;
							double thed = time * v;
							// 得到前一个地点的泛化地点的距离集
							ArrayList<Dist> distList = disInfo.get(qList.get(i).getOp().getGenLoc());
							double dist = 0;
							for (int k = 0; k < distList.size(); k++) {
								if (distList.get(k).getLoc() == op.getGenLoc()) {
									// 得到Dist(l',l)
									dist = distList.get(k).getDist();
									break;
								}
							}
							// 如果前一地点的泛化点到该操作的地点的泛化点的距离大于thed，则删除qList中对前一地点的泛化操作
							if (dist > thed) {
								qList.remove(i);
							}
						}
						
					}
				}

			}
		}
		else {
			// 得到被泛化点前一个位置地点
			int beforeL = lvList.get(index - 1).getLoc();
			//ArrayList<Integer> genLoc=lvList.get(index - 1).getGenLoc().getLocList();
			// 得到被泛化点后一个位置地点
			int afterL = lvList.get(index + 1).getLoc();
			for (int i = 1; i < qList.size(); i++) {
				// 找到Q中有对同一条轨迹进行操作的操作
				if (qList.get(i).getOp().getTrajId() == op.getTrajId()) {
					if (qList.get(i).getOp().getLocId() == beforeL) {
						if(qList.get(i).getOp().getGenLoc()!=0) {
							// 得到v*(t[i]-t[i-1])阈值
							double time = lvList.get(index).getHour() - lvList.get(index - 1).getHour()
									+ (lvList.get(index).getMin() - lvList.get(index - 1).getMin()) / 60;
							double thed = time * v;
							// 得到前一个地点的泛化地点的距离集
							ArrayList<Dist> distList = disInfo.get(qList.get(i).getOp().getGenLoc());
							double dist = 0;
							for (int k = 0; k < distList.size(); k++) {
								if (distList.get(k).getLoc() == op.getGenLoc()) {
									// 得到Dist(l',l)
									dist = distList.get(k).getDist();
									break;
								}
							}
							// 如果前一地点的泛化点到该操作的地点的泛化点的距离大于thed，则删除qList中对前一地点的泛化操作
							if (dist > thed) {
								qList.remove(i);
							}
						}
						
					}
					if (qList.get(i).getOp().getLocId() == afterL) {
						if(qList.get(i).getOp().getGenLoc()!=0) {
							// 得到v*(t[i]-t[i-1])阈值
							double time = lvList.get(index + 1).getHour() - lvList.get(index).getHour()
									+ (lvList.get(index + 1).getMin() - lvList.get(index).getMin()) / 60;
							double thed = time * v;
							// 得到后一个地点的泛化地点的距离集
							ArrayList<Dist> distList = disInfo.get(qList.get(i).getOp().getGenLoc());
							double dist = 0;
							for (int k = 0; k < distList.size(); k++) {
								if (distList.get(k).getLoc() == op.getGenLoc()) {
									// 得到Dist(l',l)
									dist = distList.get(k).getDist();
									break;
								}
							}
							// 如果前一地点的泛化点到该操作的地点的泛化点的距离大于thed，则删除qList中对前一地点的泛化操作
							if (dist > thed) {
								qList.remove(i);
							}
						}
						
					}
				}

			}
		}
		
		// 然后将Q中的第一条操作删除，因为其已被执行
		qList.remove(0);
		for (int i = 0; i < qList.size(); i++) {
			int tab = 0;
			Op opnew = qList.get(i).getOp();
			// 对rlist进行深拷贝
			ArrayList<R> rlistCopy = GenCand.deepCopy(rlist);
			int label = opnew.getGenLoc();

			// 得到新操作的轨迹访问点
			ArrayList<Lv> lvListN = trajList.get(opnew.getTrajId()).getLvList();
			//lvListN进行深拷贝
			ArrayList<Lv> lvListNCopy=GenCand.deepCopy(lvListN);
			// 得到新操作轨迹的地点序列，并找到被泛化地点的位置
			ArrayList<Integer> locList = new ArrayList<>();

			for (int j = 0; j < lvListNCopy.size(); j++) {
				locList.add(lvListNCopy.get(j).getLoc());
				if (lvListNCopy.get(j).getLoc() == opnew.getLocId()) {
					tab = j;
				}
			}
			// 说明新操作为泛化操作
			if (label != 0) {
				// 得到被泛化地点的泛化序列
				ArrayList<Integer> genList = new ArrayList<>();
				genList = lvListNCopy.get(tab).getGenLoc().getLocList();
				// 前面只是取出来，还没有用这个操作对其进行泛化
				if(genList==null) {
					genList=new ArrayList<>();
				}
				genList.add(opnew.getGenLoc());
				double anonyGain = annoyGaing(rlistCopy, opnew, genList, 0.5, locList, tab);
				double infoLoss = Math.log(genList.size()+1)/ Math.log(2);
				for (int j = 0; j < lvListNCopy.size(); j++) {
					if(lvListNCopy.get(j).getGenLoc().getLocList()==null) {
						continue;
					}
					else {
						infoLoss = infoLoss + Math.log(lvListNCopy.get(j).getGenLoc().getLocList().size()) / Math.log(2);
					}
					
				}
				qList.get(i).setAnonyGain(anonyGain);
				qList.get(i).setInfoLoss(infoLoss);
				double score = anonyGain / infoLoss;
				qList.get(i).setScore(score);
			} else {
				// 抑制的话将这个点删除
				lvListNCopy.remove(tab);
				double anonyGain = anonyGains(rlistCopy, opnew.getLocId(), 0.5, locList);
				double infoLoss = Math.log(locList.size()) / Math.log(2);
				for (int j = 0; j < lvListNCopy.size(); j++) {
					if(lvListNCopy.get(j).getGenLoc().getLocList()==null) {
						continue;
					}
					else {
						infoLoss = infoLoss + Math.log(lvListNCopy.get(j).getGenLoc().getLocList().size()) / Math.log(2);
					}
				}
				qList.get(i).setAnonyGain(anonyGain);
				qList.get(i).setInfoLoss(infoLoss);
				double score = anonyGain / infoLoss;
				qList.get(i).setScore(score);
			}
		}
		return qList;

	}

	// 新操作是泛化操作
	public double annoyGaing(ArrayList<R> rlistCopy, Op op, ArrayList<Integer> genList, double thed,
			ArrayList<Integer> locList, int tab) {
		// 先对含有被泛化地点的规则处理，改变conf
		for (int k = 0; k < rlistCopy.size(); k++) {
			boolean flag1 = locList.containsAll(rlistCopy.get(k).getTrajNume().getTraj());
			// 如果r的分子包含在list中
			if (flag1 == true) {
				// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag2 = rlistCopy.get(k).getTrajNume().getTraj().contains(op.getLocId());
				if (flag2 == true) {
					rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()
							+ ((double)(genList.size() - 1) /(double) genList.size()) - ((double)genList.size() / (double)(genList.size() + 1)));
				}
				// r的规则中的分子中含用来泛化的地点，这条规则的条数就加0.5
				// boolean flag3=rlist.get(k).getTrajNume().getTraj().contains(l2);
				// if(flag3==true) {
				// rlist.get(k).getTrajNume().setCount(rlist.get(k).getTrajNume().getCount()+0.5);
				// }
			}
			// 如果r的分母包含在list中
			boolean flag3 = locList.containsAll(rlistCopy.get(k).getTrajDemo().getTraj());
			if (flag3 == true) {
				// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
				boolean flag4 = rlistCopy.get(k).getTrajDemo().getTraj().contains(op.getLocId());
				if (flag4 == true) {
					rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()
							+ ((double)(genList.size() - 1) /(double) genList.size()) - ((double)genList.size() /(double) (genList.size() + 1)));
				}
				// r的规则中的分子中含用来泛化的地点，这条规则的条数就加0.5
				// boolean flag6=rlist.get(k).getTrajDemo().getTraj().contains(l2);
				// if(flag6==true) {
				// rlist.get(k).getTrajDemo().setCount(rlist.get(k).getTrajDemo().getCount()+0.5);
				// }
			}

		}
		// 再对泛化点进行处理
		for (int i = 0; i < genList.size(); i++) {
			// 将locList中被泛化点的位置修改为泛化点的位置
			locList.set(tab, genList.get(i));
			if (i == genList.size() - 1) {
				for (int k = 0; k < rlistCopy.size(); k++) {
					boolean flag1 = locList.containsAll(rlistCopy.get(k).getTrajNume().getTraj());
					// 如果r的分子包含在list中
					if (flag1 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag2 = rlistCopy.get(k).getTrajNume().getTraj().contains(genList.get(i));
						if (flag2 == true) {
							rlistCopy.get(k).getTrajNume()
									.setCount(rlistCopy.get(k).getTrajNume().getCount() + (1 / (double)(genList.size() + 1)));
						}
					}
					// 如果r的分母包含在list中
					boolean flag3 = locList.containsAll(rlistCopy.get(k).getTrajDemo().getTraj());
					if (flag3 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag4 = rlistCopy.get(k).getTrajDemo().getTraj().contains(genList.get(i));
						if (flag4 == true) {
							rlistCopy.get(k).getTrajDemo()
									.setCount(rlistCopy.get(k).getTrajDemo().getCount() + (1 / (double)(genList.size() + 1)));
						}
					}

				}
			} else {
				for (int k = 0; k < rlistCopy.size(); k++) {
					boolean flag1 = locList.containsAll(rlistCopy.get(k).getTrajNume().getTraj());
					// 如果r的分子包含在list中
					if (flag1 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag2 = rlistCopy.get(k).getTrajNume().getTraj().contains(genList.get(i));
						if (flag2 == true) {
							rlistCopy.get(k).getTrajNume().setCount(rlistCopy.get(k).getTrajNume().getCount()
									- ((double)(genList.size() - 1) / (double)genList.size()) + (1 / (double)(genList.size() + 1)));
						}
					}
					// 如果r的分母包含在list中
					boolean flag3 = locList.containsAll(rlistCopy.get(k).getTrajDemo().getTraj());
					if (flag3 == true) {
						// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减0.5
						boolean flag4 = rlistCopy.get(k).getTrajDemo().getTraj().contains(genList.get(i));
						if (flag4 == true) {
							rlistCopy.get(k).getTrajDemo().setCount(rlistCopy.get(k).getTrajDemo().getCount()
									- ((double)(genList.size() - 1) / (double)genList.size()) + (1 /(double) (genList.size() + 1)));
						}
					}

				}
			}
		}
		double annoyGain = 0;
		for (int k = 0; k < rlistCopy.size(); k++) {
			double conf = rlistCopy.get(k).getTrajNume().getCount() / rlistCopy.get(k).getTrajDemo().getCount();
			if (conf <= thed) {
				annoyGain = annoyGain + 1;
				//System.out.println("旧conf为："+rlistCopy.get(k).getConf());
			// System.out.println("新操作为泛化操作新conf为："+conf);
			} else {
				// System.out.println("新conf为："+conf);
				double fr = (rlistCopy.get(k).getConf() - conf) / (conf - thed);
				annoyGain = annoyGain + fr;
				// System.out.println("新操作为泛化操作fr为："+fr);
			}

		}
		return annoyGain;
	}

	// 如果当前操作是抑制的话，不用考虑时空约束，直接更新Q
	public ArrayList<Q> updateQs(Op op,ArrayList<Q> qList, ArrayList<R> rlist, ArrayList<Traj> trajList)
			throws ClassNotFoundException, IOException {
		qList.remove(0);
		for (int i = 0; i < qList.size(); i++) {
			int tab = 0;
			Op opnew = qList.get(i).getOp();
			//如果新操作同对同一个点进行泛化或抑制，则将其删除，因为这个点已经被删除
			if(opnew.getTrajId()==op.getTrajId()&&opnew.getLocId()==op.getLocId()) {
				qList.remove(i);
			}
			else {
				// 对rlist进行深拷贝
				ArrayList<R> rlistCopy = GenCand.deepCopy(rlist);
				int label = opnew.getGenLoc();

				// 得到新操作的轨迹访问点
				ArrayList<Lv> lvListN = trajList.get(opnew.getTrajId()).getLvList();
				ArrayList<Lv> lvListNCopy=GenCand.deepCopy(lvListN);
				// 得到新操作轨迹的地点序列，并找到被泛化地点的位置
				ArrayList<Integer> locList = new ArrayList<>();

				for (int j = 0; j < lvListNCopy.size(); j++) {
					locList.add(lvListNCopy.get(j).getLoc());
					if (lvListNCopy.get(j).getLoc() == opnew.getLocId()) {
						tab = j;
					}
				}
				// 说明新操作为泛化操作
				if (label != 0) {
					// 得到被泛化地点的泛化序列
					 
					ArrayList<Integer> genList = lvListNCopy.get(tab).getGenLoc().getLocList();
					if(genList==null) {
						genList = new ArrayList<>();
					}
					// 前面只是取出来，还没有用这个操作对其进行泛化
					genList.add(opnew.getGenLoc());
					double anonyGain = annoyGaing(rlistCopy, opnew, genList, 0.5, locList, tab);
					double infoLoss = Math.log(genList.size()+1)/ Math.log(2);
					for (int j = 0; j < lvListNCopy.size(); j++) {
						if(lvListNCopy.get(j).getGenLoc().getLocList()==null) {
							continue;
						}
						else {
							infoLoss = infoLoss + Math.log(lvListNCopy.get(j).getGenLoc().getLocList().size()) / Math.log(2);
						}
					}
					qList.get(i).setAnonyGain(anonyGain);
					qList.get(i).setInfoLoss(infoLoss);
					double score = anonyGain / infoLoss;
					qList.get(i).setScore(score);
				} else {
					// 抑制的话将这个点删除
					lvListNCopy.remove(tab);
					double anonyGain = anonyGains(rlistCopy, opnew.getLocId(), 0.5, locList);
					double infoLoss =  Math.log(locList.size()) / Math.log(2);
					for (int j = 0; j < lvListNCopy.size(); j++) {
						if(lvListNCopy.get(j).getGenLoc().getLocList()==null) {
							continue;
						}
						else {
							infoLoss = infoLoss + Math.log(lvListNCopy.get(j).getGenLoc().getLocList().size()) / Math.log(2);
						}
					}
					qList.get(i).setAnonyGain(anonyGain);
					qList.get(i).setInfoLoss(infoLoss);
					double score = anonyGain / infoLoss;
					qList.get(i).setScore(score);
				}
			}
			
		}

		return qList;
	}

	public double anonyGains(ArrayList<R> rlistCopy, int loc, double thed, ArrayList<Integer> locList) {
		// 得到用户访问点
		for (int i = 0; i < rlistCopy.size(); i++) {
			boolean flag1 = rlistCopy.get(i).getTrajNume().getTraj().contains(loc);
			if (flag1 == true) {
				boolean flag2 = locList.containsAll(rlistCopy.get(i).getTrajNume().getTraj());
				if (flag2 == true) {
					rlistCopy.get(i).getTrajNume().setCount(rlistCopy.get(i).getTrajNume().getCount() - 1);
				}
			}
			// 如果r的分母包含在list中
			boolean flag3 = rlistCopy.get(i).getTrajDemo().getTraj().contains(loc);
			if (flag3 == true) {
				// r的规则中的分子含有被泛化的地点的话，这条规则的条数就减1
				boolean flag4 = locList.containsAll(rlistCopy.get(i).getTrajDemo().getTraj());
				if (flag4 == true) {
					rlistCopy.get(i).getTrajDemo().setCount(rlistCopy.get(i).getTrajDemo().getCount() - 1);
				}
			}
		}
		double annoyGain = 0;
		for (int k = 0; k < rlistCopy.size(); k++) {
			double conf = rlistCopy.get(k).getTrajNume().getCount() / rlistCopy.get(k).getTrajDemo().getCount();
			if (conf <= thed) {
				System.out.println("conf<thed其值为：" + conf);
				annoyGain = annoyGain + 1;
			} else {
				// System.out.println("旧conf为："+rlistCopy.get(k).getConf());
				// System.out.println("新conf为："+conf);
				double fr = (rlistCopy.get(k).getConf() - conf) / (conf - thed);
				annoyGain = annoyGain + fr;
				// System.out.println("fr为："+fr);
			}

		}
		return annoyGain;
	}

}

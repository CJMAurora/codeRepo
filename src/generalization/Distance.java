package generalization;
import java.math.BigDecimal;
import java.util.ArrayList;

import dijkstra.WeightedGraph;
import roadNetwork.Edge;
import roadNetwork.EdgeSet;
import roadNetwork.Node;
import roadNetwork.NodeSet;
import stoStructure.*;

public class Distance {
	//根据节点id，得到节点的坐标
	public Loc getLocCoor(int locId) {
		Storage s = new Storage();
		double xLoc = 0;
		double yLoc = 0;
		Loc l = new Loc();
		ArrayList<Loc> locList = s.locList;
		for (int i = 0; i < locList.size(); i++) {
			if (locList.get(i).getLocId() == locId) {
				xLoc = locList.get(i).getxCoor();
				yLoc = locList.get(i).getyCoor();
				break;
			}
			
		}
		l.setLocId(locId);
		l.setxCoor(xLoc);
		l.setyCoor(yLoc);
		return l;
	}

	// 求签到地点最近边的候选集
	public ArrayList<CandE> edgeCand(int locId, float thed) {
		ArrayList<CandE> candEList = new ArrayList<>();
		double xLoc = getLocCoor(locId).getxCoor();
		double yLoc = getLocCoor(locId).getyCoor();
		EdgeSet edgeSet = new EdgeSet();
		ArrayList<Edge> edgeList = edgeSet.edgeList;
		NodeSet nodeSet = new NodeSet();
		ArrayList<Node> nodeList = nodeSet.nodeList;
		for (int i = 0; i < edgeList.size(); i++) {
			double x1 = 0, x2 = 0;
			double y1 = 0, y2 = 0;
			int index1 = 0, index2 = 2;
			int eId;
			for (int j = 0; j < nodeList.size(); j++) {
				if (edgeList.get(i).getStartNode() == nodeList.get(j).getNodeId()) {
					x1 = nodeList.get(j).getxCoor();
					y1 = nodeList.get(j).getyCoor();
					index1 = 1;
				}
				if (edgeList.get(i).getEndNode() == nodeList.get(j).getNodeId()) {
					x2 = nodeList.get(j).getxCoor();
					y2 = nodeList.get(j).getyCoor();
					index2 = 1;
				}
				if ((index1 == 1) && (index2 == 1))
					break;
			}
			if(((xLoc<x1&&xLoc<x2)||(xLoc>x1&&xLoc>x2))&&((yLoc<y1&&yLoc<y2)||(yLoc>y1&&yLoc>y2))) {
				double d1=Math.sqrt((xLoc-x1)*(xLoc-x1)+(yLoc-y1)*(yLoc-y1));
				double d2=Math.sqrt((xLoc-x2)*(xLoc-x2)+(yLoc-y2)*(yLoc-y2));
				double mind=Math.min(d1,d2);
				if(mind<thed*edgeList.get(i).getDistance()) {
					eId = edgeList.get(i).getEdgeId();
					CandE candE = new CandE();
					candE.seteId(eId);
					candE.setX1(x1);
					candE.setX2(x2);
					candE.setY1(y1);
					candE.setY2(y2);
					candE.setStartE(edgeList.get(i).getStartNode());
					candE.setEndE(edgeList.get(i).getEndNode());
					candE.setD(edgeList.get(i).getDistance());
					candEList.add(candE);
				}
			}
			else{
				eId = edgeList.get(i).getEdgeId();
				CandE candE = new CandE();
				candE.seteId(eId);
				candE.setX1(x1);
				candE.setX2(x2);
				candE.setY1(y1);
				candE.setY2(y2);
				candE.setStartE(edgeList.get(i).getStartNode());
				candE.setEndE(edgeList.get(i).getEndNode());
				candE.setD(edgeList.get(i).getDistance());
				candEList.add(candE);
			}
		}
		return candEList;
	}

	// 点到直线的距离
	public NearestE PtE(int locId,float thed) {
		NearestE ne=new NearestE();
		ArrayList<CandE> candEList = edgeCand(locId, thed);
		double dis = Double.POSITIVE_INFINITY;
		double index = 0;
		double xLoc = getLocCoor(locId).getxCoor();
		double yLoc = getLocCoor(locId).getyCoor();
		double x1=0,x2=0,y1=0,y2=0;
		int e=0;
		double d=0;//边的距离
		for (int i = 0; i < candEList.size(); i++) {
			x1 = candEList.get(i).getX1();
			x2 = candEList.get(i).getX2();
			y1 = candEList.get(i).getY1();
			y2 = candEList.get(i).getY2();
			double m = Math.abs((y2 - y1) * xLoc - (x2 - x1) * yLoc + x2  * y1 - y2 * x1);
			double n = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
			index = m / n;
			if (index < dis) {
				e=candEList.get(i).geteId();
				d=candEList.get(i).getD();
				//System.out.println(e);
				dis = index;
				//System.out.println(dis);
				ne.eId=e;
				ne.startE=candEList.get(i).getStartE();
				ne.endE=candEList.get(i).getEndE();
				ne.dis=dis;
				double l1=Math.sqrt((x1-xLoc)*(x1-xLoc)+(y1-yLoc)*(y1-yLoc));//����ߵĿ�ʼ�ڵ�ľ���
				double l2=Math.sqrt((x2-xLoc)*(x2-xLoc)+(y2-yLoc)*(y2-yLoc));
				double d1=Math.sqrt(l1*l1-dis*dis);
				double d2=Math.abs(d-d1);
				ne.d1=d1;
				ne.d2=d2;
			}
		}
	    //System.out.println(d);
		//System.out.println(ne.eId+" "+ne.startE+" "+ne.endE+" "+ne.dis+" "+ne.d1+" "+ne.d2);
		return ne;
	}
	public double disbP(int p1,int p2,float thed) {
		double dist=0;
		NearestE ne1=PtE(p1,thed);
		NearestE ne2=PtE(p2,thed);
		int[] ep=new int[4];
		ep[0]=ne1.startE;
		ep[1]=ne1.endE;
		ep[2]=ne2.startE;
		ep[3]=ne2.endE;
		WeightedGraph graph=new WeightedGraph();
		double[][] d=new double[2][4];
		d[0][2]=graph.showDistance(ep[0],ep[2]);
		d[0][3]=graph.showDistance(ep[0],ep[3]);
		d[1][2]=graph.showDistance(ep[1],ep[2]);
		d[1][3]=graph.showDistance(ep[1],ep[3]);
		double dmin=Double.MAX_VALUE;
		int index1=0,index2=0;
		for(int i=0;i<2;i++) {
			for(int j=2;j<4;j++) {
				if(d[i][j]<dmin) {
					dmin=d[i][j];
					index1=i;
					index2=j;
				}
			}
		}
		if(index1==0) {
			if(index2==2) {
				dist=ne1.dis+ne1.d1+dmin+ne2.dis+ne2.d1;
			}
	        if(index2==3) {
	        	dist=ne1.dis+ne1.d1+dmin+ne2.dis+ne2.d2;
	        }
	     }
		if(index1==1) {
			if(index2==2) {
				dist=ne1.dis+ne1.d2+dmin+ne2.dis+ne2.d1;
			}
	        if(index2==3) {
	        	dist=ne1.dis+ne1.d2+dmin+ne2.dis+ne2.d2;
	        }
		}
		System.out.println(dist);
        return dist;
		
	}

}
//�����
class NearestE{
	int eId;
	int startE;
	int endE;
	double dis;
	double d1;//签到地点距最近边开始节点的距离
	double d2;//签到地点距最近边结束的距离	
}
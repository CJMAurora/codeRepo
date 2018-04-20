package dijkstra;
import roadNetwork.*;
public class Test {
    public static void main(String[] args) {
    	EdgeSet edgeSet=new EdgeSet();
    	edgeSet.readEdge();
    	WeightedGraph graph=new WeightedGraph();
    	graph.showDistance(8510,8511);
           //String graphFilePath;
//            if(args.length == 0)
//                graphFilePath = "E:\\edge.txt";
//            else
//                graphFilePath = args[0];
//            
//            String graphContent = FileUtil.read(graphFilePath, null);
//            WeightedGraph graph = new WeightedGraph(graphContent);
//            graph.dijkstra();
//            
    	
    }
}
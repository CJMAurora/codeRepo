package generalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DisInfo {
	//public static ArrayList<Dist> distList=new ArrayList<>();
	//HashMap<Integer,Double> distMap=new HashMap<>();
	public static HashMap<Integer,HashMap<Integer,Double>> disInfo=new HashMap<>();
	public static void setDistList(int userId) {
		String path="F:\\trajPrivacy\\distance\\"+userId+".txt";
		String tab=" ";
		try{
			File file=new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));//br是字符流
		    String line = null;
		    while((line=br.readLine())!=null){
		    	String[] data=line.split(tab);
		    	int loc1=Integer.parseInt(data[0]);
		    	int loc2=Integer.parseInt(data[1]);
		    	double dist=Double.parseDouble(data[2]);
		    	if(disInfo.containsKey(loc1)) {
		    		HashMap<Integer,Double> distMap=disInfo.get(loc1);
		    		if(!distMap.containsKey(loc2)) {
		    			distMap.put(loc2, dist);
		    			disInfo.put(loc1, distMap);
		    		}
		    	}
		       if(disInfo.containsKey(loc2)) {
		    		HashMap<Integer,Double> distMap=disInfo.get(loc2);
		    		if(!distMap.containsKey(loc1)) {
		    			distMap.put(loc1, dist);
		    			disInfo.put(loc2, distMap);
		    		}
		    	}
		    	else {
		    	    HashMap<Integer,Double> distMap1=new HashMap<>();
		    	    distMap1.put(loc2, dist);
		    	    disInfo.put(loc1, distMap1);
		    	    HashMap<Integer,Double> distMap2=new HashMap<>();
		    	    distMap2.put(loc1, dist);
		    	    disInfo.put(loc2, distMap2);
		    	}
		    }
			br.close();
		}catch(Exception e){
			e.printStackTrace();
            System.out.println("读取文件出错");
		}
		Iterator<Map.Entry<Integer, HashMap<Integer,Double>>> iterator2 = disInfo.entrySet().iterator();
	    while(iterator2.hasNext()){
	   	     Map.Entry<Integer, HashMap<Integer,Double>> entry = iterator2.next();
	       	List<Map.Entry<Integer,Double>> list=new ArrayList<>();  
            list.addAll(entry.getValue().entrySet());  
            DisInfo.ValueComparator vc=new ValueComparator();  
            Collections.sort(list,vc); 
	        System.out.println(entry.getKey());
	        for(Iterator<Map.Entry<Integer,Double>> it=list.iterator();it.hasNext();)  
	        {  
	            System.out.println(it.next());  
	        } 
	    }
	}
	 
	private static class ValueComparator implements Comparator<Map.Entry<Integer,Double>>  
    {  
        public int compare(Map.Entry<Integer,Double> m,Map.Entry<Integer,Double> n)  
        {  
        	if(m.getValue() >  n.getValue())
                return 1;
            else if(m.getValue() < n.getValue())
                return -1;
            return 0;
        }  
    } 
	

}

//class Dist{
//	private HashMap<Integer,Double> distMap=new HashMap<>();
//	public HashMap<Integer, Double> getDistMap() {
//		return distMap;
//	}
//	public void setDistMap(HashMap<Integer, Double> distMap) {
//		this.distMap = distMap;
//	} 
//	
//}
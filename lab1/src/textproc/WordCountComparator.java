package textproc;

import java.util.*;
import java.util.Map.Entry;

public class WordCountComparator implements Comparator<Map.Entry<String,Integer>> {

	@Override
	public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
		int o1 = arg0.getValue();
		int o2 = arg1.getValue();
		//int diff = arg0.getKey().charAt(0)-arg1.getKey().charAt(0); 
		//om diff>0 så är arg0 senare i alfabetet än arg1 och ska därmed placeras under.
		
		// lite osäker på hur det funkar med o1 < o2. -> o1 är mindre än o2 och ska placeras under på listan
		// måste då vara att det "naturliga" är att sortera minst till störst, dvs stigande ordning, medan vi vill ha fallande.
		
//		if(o1 < o2 || (o1==o2 && diff>0) ) { 
//			return 1;
//		} else if (o1 > o2 || (o1==o2 && diff<0)) {
//			return -1;
//		} else {
//			return 0;
//		}
		
		int res = o2 - o1;
		if(res != 0) {
			return res;
		} else {
			return arg0.getKey().compareTo(arg1.getKey());
		}
		
	}

	
}

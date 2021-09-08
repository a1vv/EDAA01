package textproc;

import java.util.*;

public class MultiWordCounter implements TextProcessor {
	
	private Map<String, Integer> m;
	
	public MultiWordCounter(String[] landskap) {
		m = new TreeMap<String, Integer>();
		for(String s : landskap) {
			m.put(s,0);
		}
	}
	
	public void process(String w) {
		if (m.containsKey(w)) { 
			m.put(w,m.get(w)+1);
		}
	}
	
	public void report() {
		for(String key : m.keySet()) {
			System.out.println(key + ": "+m.get(key));
		}
		
	}
}

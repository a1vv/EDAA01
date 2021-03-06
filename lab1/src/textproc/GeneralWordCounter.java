package textproc;

import java.util.*;

public class GeneralWordCounter implements TextProcessor {

	Map<String, Integer> m;
	Set<String> stopwords;
	
	public GeneralWordCounter(Set<String> stopwords) {
		this.stopwords = stopwords;
		m = new TreeMap<String, Integer>();
	}
	
	public void process(String w) {
		int value = 1;
		if(!stopwords.contains(w)) {
			if (m.containsKey(w)) { 
				value = m.get(w)+1;
			}
			m.put(w,value);
		}
	}
	
	public List<Map.Entry<String,Integer>> getWordList() {
		return new ArrayList<Map.Entry<String,Integer>>(m.entrySet());
	}
	
	public void report() {
//		for(String key : m.keySet()) {
//			if(m.get(key)>=200) {
//				System.out.println(key + ": "+m.get(key));
//			}
//		}
		Set<Map.Entry<String, Integer>> wordSet = m.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
		wordList.sort(new WordCountComparator());
		
		for(int i = 0 ; i < 20 ; i++) {
			System.out.println(wordList.get(i));
		}
	}
}

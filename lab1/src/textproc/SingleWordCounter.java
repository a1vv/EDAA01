package textproc;

public class SingleWordCounter implements TextProcessor {
	private String word;
	private int n;

	public SingleWordCounter(String word) {
		this.word = word;
		n = 0;
	}

	public void process(String w) {
		if (word.equals(w)) { // stod w == word, jämför inte strängens innehåll.
			n++;
		}
	}

	public void report() {
		System.out.println(word + ": " + n);
	}

}

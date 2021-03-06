package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };
	private static ArrayList<TextProcessor> processors;
	
	public static void main(String[] args) throws FileNotFoundException {
		long t0 = System.nanoTime();
		
		processors = new ArrayList<TextProcessor>();
		
		processors.add(new SingleWordCounter("nils"));
		processors.add(new SingleWordCounter("norge"));
		processors.add(new MultiWordCounter(REGIONS));

		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		
		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> stopwords = new HashSet<String>();
		while(scan.hasNext()) {
			String word = scan.next().toLowerCase();
			stopwords.add(word);
		}
		
		processors.add(new GeneralWordCounter(stopwords));

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			for(TextProcessor p : processors) {
				p.process(word);
			}
		}

		s.close();

		for(TextProcessor p : processors) {
			p.report();
			System.out.println("_______________________________________");
		}
		
		long t1 = System.nanoTime();
		
		System.out.println("tid: " + (t1-t0)/100000.0 + " ms.");
		
		
	}
}
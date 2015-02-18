package assigment3.a;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistence.BerkeleyDB;
import assigment1.a.Pair;
import assigment1.a.Utilities;
import assigment2.WebURLExtension;

import com.sleepycat.persist.EntityCursor;

public class IndexBuilder {
	
	public static void main(String[] args) {
	    final int N = 51138;
	    int doc_num = 0;
	    int term_num = 0;
		long startTime = System.currentTimeMillis();
		BerkeleyDB db=BerkeleyDB.getInstance();
		EntityCursor<WebURLExtension> webURLs = db.getCursorWebURLs();		
		try {			
			HashMap<String, List<Pair<String, List<Integer>>>> result_part1 = new HashMap<String, List<Pair<String, List<Integer>>>>();
			String pathOut = "c:\\Users\\Administrator\\Desktop\\outcome_a_g.txt";
			
			for (WebURLExtension entity = webURLs.first(); entity != null; entity = webURLs.next()) {
 
				//get information from database of crawler
				List<String> tokens = Utilities.tokenizeString(entity.getTextContent());
				HashMap<String, List<Integer>> wordfreq = WordFrequenciesExtension.computeWordFrequencies(tokens);			
				String Url = entity.getURL();
				
				for(String s: wordfreq.keySet()) {
					if(s.charAt(0)<='g') {
						if(result_part1.get(s)==null) {
							List<Pair<String, List<Integer>>> newlist= new ArrayList<Pair<String, List<Integer>>>();
							newlist.add(Pair.createPair(Url, wordfreq.get(s)));
							result_part1.put(s, newlist);
				    	}
						else {
							List<Pair<String, List<Integer>>> oldlist = result_part1.get(s);
							oldlist.add(Pair.createPair(Url, wordfreq.get(s)));
							result_part1.put(s, oldlist);
						}
					}
				}
				System.out.println(Url);
			}
			
			try {
				PrintWriter writer = new PrintWriter(pathOut);
				for (String s: result_part1.keySet()) {
					term_num++;
					writer.print(s);
					int df = result_part1.get(s).size();
					double idf = Math.log10(N/df);
					writer.print("? idf=" + idf);
					for (Pair<String, List<Integer>> p : result_part1.get(s)) {
						writer.print("? url=" + p.first);
						int tf = p.second.get(0);
						writer.print("? tf-idf=" + idf*Math.log(1+tf));
						writer.print("? tf=" + tf);
						writer.print("? position=" + p.second.subList(1,1+tf));
					}
					writer.println("");
					writer.println("");
				}
				writer.close();    
			} catch (Exception e) {
			System.out.println("There is a problem writing the file.");
			}	
     
		} finally {}
				
		try {			
			HashMap<String, List<Pair<String, List<Integer>>>> result_part2 = new HashMap<String, List<Pair<String, List<Integer>>>>();
			String pathOut = "c:\\Users\\Administrator\\Desktop\\outcome_h_s.txt";
			
			for (WebURLExtension entity = webURLs.first(); entity != null; entity = webURLs.next()) {
 
				//get information from database of crawler
				List<String> tokens = Utilities.tokenizeString(entity.getTextContent());
				HashMap<String, List<Integer>> wordfreq = WordFrequenciesExtension.computeWordFrequencies(tokens);			
				String Url = entity.getURL();
				
				for(String s: wordfreq.keySet()) {
					if(s.charAt(0)>'g' && s.charAt(0)<='s') {
						if(result_part2.get(s)==null) {
							List<Pair<String, List<Integer>>> newlist= new ArrayList<Pair<String, List<Integer>>>();
							newlist.add(Pair.createPair(Url, wordfreq.get(s)));
							result_part2.put(s, newlist);
				    	}
						else {
							List<Pair<String, List<Integer>>> oldlist = result_part2.get(s);
							oldlist.add(Pair.createPair(Url, wordfreq.get(s)));
							result_part2.put(s, oldlist);
						}
					}
				}
				System.out.println(Url);
			}
			
			try {
				PrintWriter writer = new PrintWriter(pathOut);
				for (String s: result_part2.keySet()) {
					term_num++;
					writer.print(s);
					int df = result_part2.get(s).size();
					double idf = Math.log10(N/df);
					writer.print("? idf=" + idf);
					for (Pair<String, List<Integer>> p : result_part2.get(s)) {
						writer.print("? url=" + p.first);
						int tf = p.second.get(0);
						writer.print("? tf-idf=" + idf*Math.log(1+tf));
						writer.print("? tf=" + tf);
						writer.print("? position=" + p.second.subList(1,1+tf));
					}
					writer.println("");
					writer.println("");
				}
				writer.close();    
			} catch (Exception e) {
			System.out.println("There is a problem writing the file.");
			}	
     
		} finally {}
	
		try {			
			HashMap<String, List<Pair<String, List<Integer>>>> result_part3 = new HashMap<String, List<Pair<String, List<Integer>>>>();
			String pathOut = "c:\\Users\\Administrator\\Desktop\\outcome_t_z.txt";
			
			for (WebURLExtension entity = webURLs.first(); entity != null; entity = webURLs.next()) {
 
				//get information from database of crawler
				List<String> tokens = Utilities.tokenizeString(entity.getTextContent());
				HashMap<String, List<Integer>> wordfreq = WordFrequenciesExtension.computeWordFrequencies(tokens);			
				String Url = entity.getURL();
				doc_num++;
				
				for(String s: wordfreq.keySet()) {
					if(s.charAt(0)>'s') {
						if(result_part3.get(s)==null) {
							List<Pair<String, List<Integer>>> newlist= new ArrayList<Pair<String, List<Integer>>>();
							newlist.add(Pair.createPair(Url, wordfreq.get(s)));
							result_part3.put(s, newlist);
				    	}
						else {
							List<Pair<String, List<Integer>>> oldlist = result_part3.get(s);
							oldlist.add(Pair.createPair(Url, wordfreq.get(s)));
							result_part3.put(s, oldlist);
						}
					}
				}
				System.out.println(Url);
			}
			
			try {
				PrintWriter writer = new PrintWriter(pathOut);
				for (String s: result_part3.keySet()) {
					term_num++;
					writer.print(s);
					int df = result_part3.get(s).size();
					double idf = Math.log10(N/df);
					writer.print("? idf=" + idf);
					for (Pair<String, List<Integer>> p : result_part3.get(s)) {
						writer.print("? url=" + p.first);
						int tf = p.second.get(0);
						writer.print("? tf-idf=" + idf*Math.log(1+tf));
						writer.print("? tf=" + tf);
						writer.print("? position=" + p.second.subList(1,1+tf));
					}
					writer.println("");
					writer.println("");
				}
				writer.close();    
			} catch (Exception e) {
			System.out.println("There is a problem writing the file.");
			}	
     
		} finally {
			webURLs.close();
			long endTime = System.currentTimeMillis();
			System.out.println((endTime-startTime)/1000 + " s");
			System.out.println("Doc num = " + doc_num + " AND Term num =" + term_num);
		}
		
	}

}
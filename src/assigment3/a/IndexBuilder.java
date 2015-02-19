package assigment3.a;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import persistence.BerkeleyDB;
import persistence.InvertedIndexDB;
import assigment1.a.Utilities;
import assigment2.WebURLExtension;

import com.sleepycat.persist.EntityCursor;

public class IndexBuilder {
	
	public static void buildIndex() {
	  
		BerkeleyDB db=BerkeleyDB.getInstance();
		InvertedIndexDB indexDB = InvertedIndexDB.getInstance();
		EntityCursor<WebURLExtension> webURLs = db.getCursorWebURLs();	
		
		//HashMap<String, TermInvertedIndex> hashIndex= new HashMap<String, TermInvertedIndex>();
		TermInvertedIndex term;
				
			
			for (WebURLExtension entity = webURLs.first(); entity != null; entity = webURLs.next()) {
 
				//get information from database of crawler
				List<String> tokens = Utilities.tokenizeString(entity.getTextContent());
				HashMap<String, List<Integer>> wordfreq = WordFrequenciesExtension.computeWordFrequencies(tokens);			
				String Url = entity.getURL();
				
				Iterator<String> iterator= wordfreq.keySet().iterator();
				
				while(iterator.hasNext()){
					String s = iterator.next(); //next term
					if((term =indexDB.getTerm(s))!=null){ //the hashIndex contains the term, so add the document
						DocInvertedIndex doc=new DocInvertedIndex(Url);
						for(Integer location: wordfreq.get(s)){
							doc.addLocation(location);
						}
						term.addDocument(doc);
					}else{ //otherwise create the term
						TermInvertedIndex newTerm = new TermInvertedIndex(s);
						DocInvertedIndex doc=new DocInvertedIndex(Url);
						for(Integer location: wordfreq.get(s)){
							doc.addLocation(location);
						}
						newTerm.addDocument(doc);
						indexDB.putTerm(newTerm);
					}
				}
				
				//indexDB.syncStore();
				
				System.out.println(Url);
			}
			
			/*
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
			*/
			
		
			webURLs.close();
			
			
			//System.out.println((endTime-startTime)/1000 + " s");
			//System.out.println("Doc num = " + doc_num + " AND Term num =" + term_num);
	
	}

}
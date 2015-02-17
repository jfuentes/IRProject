package assigment3.a;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistence.BerkeleyDB;
import persistence.BerkeleyDB_index;
import assigment1.a.Pair;
import assigment1.a.Utilities;
import assigment2.WebURLExtension;

import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityCursor;

public class IndexBuilder {
	
	public static void main(String[] args) {
		BerkeleyDB db=BerkeleyDB.getInstance();
		EntityCursor<WebURLExtension> webURLs = db.getCursorWebURLs();
		
		BerkeleyDB_index dbindex=BerkeleyDB_index.getInstance();
		EntityCursor<InvertedIndexExtension> indexCursor = dbindex.getCursorIndex();
		
		long startTime = System.currentTimeMillis();
		
		try {
		     for (WebURLExtension entity = webURLs.first();
		                   entity != null;
		                   entity = webURLs.next()) {
		    	 
		    	//get information from database of crawler
		    	List<String> tokens = Utilities.tokenizeString(entity.getTextContent());
		    	HashMap<String, List<Integer>> wordfreq = WordFrequenciesExtension.computeWordFrequencies(tokens);
		    	String Url = entity.getURL();
		    	
		    	Transaction txn = BerkeleyDB_index.getEnv().beginTransaction(null, null);
		    	//put information into new database
		    	for(String s: wordfreq.keySet()){
		    		if(dbindex.getIndex(s)==null) {
		    			String newlist = Url + "=" + wordfreq.get(s);
		    			dbindex.getMasterIndex().put(txn, (new InvertedIndexExtension(s, newlist)));
		    		}
		    		else {
		    			String oldlist = dbindex.getIndex(s).getIndex();
		    			oldlist += " ? " + Url + "=" + wordfreq.get(s);
		    			dbindex.deleteIndex(s);
		    			dbindex.getMasterIndex().put(txn,(new InvertedIndexExtension(s, oldlist)));
		    		}
		    		
		    	}
		    	System.out.println(Url);
		    	txn.commit();
		     }
		     
		     String pathOut = "c:\\Users\\Administrator\\Desktop\\outcome.txt";
		     try {
		         PrintWriter writer = new PrintWriter(pathOut);
		         for (InvertedIndexExtension entity_index = indexCursor.first();
			    		 entity_index != null;
			    		 entity_index = indexCursor.next()) {
		        	 writer.println(entity_index.getTerm() + " ? " + entity_index.getIndex());
			     }
		         writer.close();    
		       } catch (Exception e) {
		         System.out.println("There is a problem writing the file.");
		       } 
		     
		 } finally {
			 webURLs.close();
			 indexCursor.close();
			 long endTime = System.currentTimeMillis();
			 System.out.println((endTime-startTime)/1000/60 + " min");
		 }
	}
	

}

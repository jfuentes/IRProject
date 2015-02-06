package assigment2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import assigment1.a.Pair;

import com.sleepycat.persist.EntityCursor;

import persistence.BerkeleyDB;

public class CrawlerStatistics {
	
	public static long getTotalUniquePages(){
		BerkeleyDB db=BerkeleyDB.getInstance();
		return db.getTotalWebpages();
	}
	
	public static void getSubdomains(){
		BerkeleyDB db=BerkeleyDB.getInstance();
		EntityCursor<WebURLExtension> webURLs = db.getCursorWebURLs();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		ArrayList<Pair<String, Integer>> arrayList= new ArrayList<Pair<String, Integer>>();
		
		try {
		     for (WebURLExtension entity = webURLs.first();
		                   entity != null;
		                   entity = webURLs.next()) {
		    	 if(hashMap.containsKey(entity.getSubDomain())){
						
						hashMap.put(entity.getSubDomain(), hashMap.get(entity.getSubDomain())+1);
				}else{
						hashMap.put(entity.getSubDomain(), 1);
				}
		     }
		 } finally {
			 webURLs.close();
		 }
		
		Iterator<String> iterator= hashMap.keySet().iterator();
		
		while(iterator.hasNext()){
			String s = iterator.next();
			Pair<String, Integer> pair= Pair.createPair(s, hashMap.get(s));
			arrayList.add(pair);
		}
	
		Collections.sort(arrayList, new Comparator<Pair<String, Integer>>(){
			public int compare(Pair<String, Integer> pair1, Pair<String, Integer> pair2){
				return pair2.second.compareTo(pair1.second);
			}
		});
		
		//write the results to the File Subdomains.txt
		PrintWriter writer;
		try {
			writer = new PrintWriter("Subdomains.txt", "UTF-8");
			for(Pair<String, Integer> subdomain: arrayList)
				writer.println("http://"+subdomain.first+".uci.edu, "+subdomain.second);
			writer.close();
			System.out.println("File Subdomains.txt created successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

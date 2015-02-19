package assigment3.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import assigment1.a.Pair;

public class WordFrequenciesExtension {

	public static HashMap<String, List<Integer>> computeWordFrequencies(List<String> list){
		HashMap<String, List<Integer>> hashMap = new HashMap<String, List<Integer>>();
		int p = 0;
		// For the List<Integer>, the first int is count, while the following int are the positions of the word.
		for(String s: list){
			if(hashMap.containsKey(s)){
				List<Integer> oldlist = hashMap.get(s);
				//oldlist.set(0, oldlist.get(0)+1);
				oldlist.add(p);
				//hashMap.put(s, oldlist);
			}else{
				List<Integer> newlist = new ArrayList<Integer>();
				//newlist.add(1);
				newlist.add(p);
				hashMap.put(s, newlist);
			}
			p++;
		}
		
		return hashMap;
	}
	
}



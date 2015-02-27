package assigment3.a;

import java.util.*;

import persistence.InvertedIndexDB;
import assigment1.a.Pair;
import assigment1.a.Utilities;

public class QueryProcessor {
	
	String word;
	InvertedIndexDB index;
	
	//use List to store information about documents
	List<String> documents = new ArrayList<String>();
	//count how many words in query can be found in this document
	List<Integer> word_exist = new ArrayList<Integer>();
	//count the total of tf-idf of this document
	List<Double> tf_idfs = new ArrayList<Double>();
	
	public QueryProcessor(String w, InvertedIndexDB index) {
		this.word = w;
		this.index = index;
	}
	
	public List<String> ParseQuery() {
		return Utilities.tokenizeString(word);
	}
	
	public void DoSearch() {
		long corpus = index.getTotalTerms();
		//draw information of word out of database
		for (String w : ParseQuery()) {
			TermInvertedIndex term = index.getTerm(w);
			long df = term.getList().size();
			//turn information of word into information of document
			for (DocInvertedIndex doc : term.getList()) {
				String docName = doc.getDocument();
				List<Integer> docLocations = doc.getLocations();
				double tf_idf = (1+Math.log10(docLocations.size()))*(Math.log10(corpus/df));
				//put information of document into List
				putInfo(docName, tf_idf);
			}
		}
		
		sortAndPrint();
	}
	
	public void putInfo(String docName, double tf_idf) {
		if(documents.indexOf(docName)==-1) {
			documents.add(docName);
			word_exist.add(1);
			tf_idfs.add(tf_idf);
		}
		else {
			int position = documents.indexOf(docName);
			word_exist.set(position, word_exist.get(position) + 1);
			tf_idfs.set(position, tf_idfs.get(position) + tf_idf);
		}
	}
	
	public void sortAndPrint() {
		//calculate score based on information in List
		Double[] scores = new Double[documents.size()];
		for (int i=0;i<documents.size();i++) {
			scores[i] = word_exist.get(i)*100 + tf_idfs.get(i);
		}
		//create a List containing id and score
		List<Pair<Integer,Double>> ranking = new ArrayList<Pair<Integer,Double>>();
		for (int i=0;i<documents.size();i++) {
			ranking.add(Pair.createPair(i,scores[i]));
		}
		//rank the id by score
		//NOTE: now the rank is reversed with most relevant at end!
		Collections.sort(ranking, new Comparator<Pair<Integer, Double>>(){
			public int compare(Pair<Integer, Double> pair1, Pair<Integer, Double> pair2){
				return pair1.second.compareTo(pair2.second);
			}
		});
		//print out the result
		
		for (int i=0;i<documents.size();i++) {
			int id = ranking.get(i).first;
			System.out.print(" " + documents.get(id) + " ");
			System.out.print("score: " + ranking.get(i).second + " ");
			System.out.print(word_exist.get(id) + " of " + ParseQuery().size() + " words matched ");
			System.out.print("total tf-idf: " + tf_idfs.get(id));
			System.out.println("");
		}
		System.out.println(word + ": " + documents.size() + " results");
	}

}

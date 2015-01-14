package assigment1;

import java.io.File;
import java.util.List;

import assigment1.a.Utilities;
import assigment1.a.Pair;
import assigment1.b.WordFrequencies;
import assigment1.c.TwoGrams;

public class TestAssigment {
	
	public static void main(String[] args){
		//test part A
		System.out.println("Part A");
		File file= new File("file.txt");
		List<String> list =Utilities.tokenizeFile(file);
		Utilities.print(list);
		
		System.out.println("Part B");
		//test part B
		List<Pair<String, Integer>> listFrecuencies=WordFrequencies.computeWordFrequencies(list);
		WordFrequencies.print(listFrecuencies);
		
		System.out.println("Part C");
		//test part C
		List<Pair<String, Integer>> listFrequenciesGrams=TwoGrams.computeTwoGramFrequencies(list);
		TwoGrams.print(listFrequenciesGrams);
	}

}

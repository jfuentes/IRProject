package assigment1;

import java.io.File;
import java.util.List;

import assigment1.a.Utilities;
import assigment1.a.Pair;
import assigment1.b.WordFrequencies;
import assigment1.c.TwoGrams;
import assigment1.d.Palindromes;

public class TestAssigment {
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		
		//test part A
		System.out.println("Part A");
		File file= new File("1ststop.txt");
		List<String> list =Utilities.tokenizeFile(file);
		Utilities.print(list);
		
		//test part B
		System.out.println();
		System.out.println("Part B");
		List<Pair<String, Integer>> listFrequencies=WordFrequencies.computeWordFrequencies(list);
		WordFrequencies.print(listFrequencies);
		
		//test part C
		System.out.println();
		System.out.println("Part C");
		
		List<Pair<String, Integer>> listFrequenciesGrams=TwoGrams.computeTwoGramFrequencies(list);
		TwoGrams.print(listFrequenciesGrams);
		
		
		//Test part D
		System.out.println();
		System.out.println("Part D");
		

		
		List<Pair<String, Integer>> listFrequenciesPalindromes=new Palindromes().computePalindromeFrequencies(list);
		Palindromes.print(listFrequenciesPalindromes);
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\nTotal time: " +totalTime+"ms");
	}

}

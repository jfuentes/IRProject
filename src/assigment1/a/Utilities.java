package assigment1.a;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilities {
	
	public static List<String> tokenizeFile(File textFile){
		
		/*
		 * Count, separate words. We can use more advanced character patterns in split. Here we separate a String based on non-word characters. We use "\W+" to mean this.
		 * Pattern:
		 * The pattern means "one or more non-word characters." A plus means "one or more" and a W means non-word.
		 */
		
		ArrayList<String> list= new ArrayList<String>();
		
		try {
			Scanner scanner = new Scanner(textFile);
			
			while(scanner.hasNextLine()){
				String s = scanner.nextLine();
				for(String token :s.split("\\W+")){ // W means non-word as a delimiter
					if(token.length()>0)
						list.add(token.toLowerCase());
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	
	public static void print(List<String> list){
		
		for(String s: list)
			System.out.println(s);
		
	}
	

}

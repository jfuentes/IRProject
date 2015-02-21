package assigment3.a;

import java.util.Scanner;

import persistence.BerkeleyDB;
import persistence.InvertedIndexDB;
import assigment1.a.Pair;
import assigment2.CrawlerStatistics;
import assigment2.WebURLExtension;

public class IndexController {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int option;
		System.out.println("*** Welcome to IR Crawling Project - Milestone 1***");
		do {

			System.out.println();
			System.out.println("Menu:");
			System.out.println("1.- Build Index for ics.uci.edu (It may take a long time)");
			System.out.println("2.- Compute Deliverables");
			System.out.println("3.- Execute query (beta)");
			System.out.println("4.- Exit");
			System.out.print("Option: ");
			option = sc.nextInt();
			switch (option) {
			case 1:
				IndexBuilder.buildIndex();
				break;
			case 2:
				computeDeliverables();
				break;
			case 3:
				excecuteQuery(sc);
				break;
			case 4:
				System.exit(0);
			default:
				System.out.println("Invalid option");
			}
		} while (option != 4);
		BerkeleyDB.close();
		InvertedIndexDB.close();
		sc.close();
	}

	private static void computeDeliverables() {
		// TODO Auto-generated method stub
		System.out.println("***********************************");
		System.out.println("****      Deliverables     ****");
		System.out.println("***********************************");
		System.out.println();
		

	}
	
	private static void excecuteQuery(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("***********************************");
		System.out.println("****      Query     ****");
		System.out.println("***********************************");
		System.out.println();
		System.out.print("Enter the word to search: ");
		String word = sc.next();
		
		InvertedIndexDB index=InvertedIndexDB.getInstance();
		TermInvertedIndex term = index.getTerm(word);
		if(term!=null)
			System.out.println(term.toString());
		else
			System.out.println("There are no documents with that query");

	}
}

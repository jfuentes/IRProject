package assigment3.a;
import java.util.ArrayList;


import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;


@Entity
public class TermInvertedIndex {
	
	@PrimaryKey
	private String term;
	
	private ArrayList<DocInvertedIndex> list=new ArrayList<DocInvertedIndex>();
	
	public TermInvertedIndex(){

	}
	
	public TermInvertedIndex(String term){
		this.term=term;
	}
	
	public void addDocument(DocInvertedIndex doc){
		list.add(doc);
	}
	
	public String toString(){
		String s="";
		s+=term+": ";
		for(DocInvertedIndex doc: list)
			s+=doc.toString()+" ";
		return s;
	}

}

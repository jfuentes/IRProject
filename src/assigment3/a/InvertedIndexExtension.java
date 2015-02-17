package assigment3.a;

import java.util.List;

import assigment1.a.Pair;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;


@Entity
public class InvertedIndexExtension {
	
	//attributes
	
	@PrimaryKey
	private String term;
	
	private String indexlist;
	
	
	public InvertedIndexExtension(){
		
	}
	
	public InvertedIndexExtension(String term) {
		this.term=term;
	}
	
	public InvertedIndexExtension(String term, String indexlist) {
		this.term=term;
		this.indexlist=indexlist;
	}
	
	public String getTerm(){
		return term;
	}

	public String getIndex() {
		return indexlist;
	}
	
	public void setIndex(String indexlist) {
		this.indexlist=indexlist;
	}
	

}

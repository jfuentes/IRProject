package assigment3.a;

import java.util.ArrayList;

import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;

@Persistent public class DocInvertedIndex {
	
	@PrimaryKey
	private String document;
	private ArrayList<Integer> locations= new ArrayList<Integer>();
	
	public DocInvertedIndex(){
		
	}
	
	public DocInvertedIndex(String document){
		this.document=document;
	}
	
	public void addLocation(int location){
		locations.add(location);
	}
	
	public String toString(){
		String s="";
		s+=document;
		s+="["+locations.size()+"|";
		for(Integer location: locations)
			s+=location+",";
		s+="]";
		return s;
	}

}

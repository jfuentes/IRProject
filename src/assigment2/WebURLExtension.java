package assigment2;

import edu.uci.ics.crawler4j.url.WebURL;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;


@Entity
public class WebURLExtension {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//attributes
	
	@PrimaryKey
	private String url;
	  
	private WebURL webURL;
	private String textContent;
	
	public WebURLExtension(){
		
	}
	
	public WebURLExtension(WebURL webURL, String textContent){
		this.webURL=webURL;
		this.textContent=textContent;
	}
	
	public String getTextContent(){
		return textContent;
	}
	
	public WebURL getWebURL(){
		return webURL;
	}

}

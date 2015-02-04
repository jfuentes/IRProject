package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

import assigment2.WebURLExtension;

public class BerkeleyDB {
	//instence
	private static BerkeleyDB instance;
	
	// to set up the database
	private static Environment environment;
	private static EntityStore store;

	private static PrimaryIndex<String, WebURLExtension> websiteIndex;

	private static String dbRoot = "/home/joel/workspace/IRProject/data/berkeleydata";
	private static File dbDir;
	
	private BerkeleyDB(){
		setup();
	}
	
	public static BerkeleyDB getInstance(){
		if(instance==null)
			instance=new BerkeleyDB();
		return instance;
	}
	
	


	public void setup(){

		//add one validation for correct parameters

		// create dir
		File dir = new File(dbRoot);
		if(dir.exists()){
			dbDir = dir;
			
		}else{
			dir.mkdir();
			dbDir = dir;
			System.out.println("Created directory for database!\t");
		}
		
		System.out.println("[status] Database started at " + dbRoot);

		// setting up enviroment
		EnvironmentConfig envConf = new EnvironmentConfig();
		StoreConfig storeConf = new StoreConfig();

		envConf.setAllowCreate(true);
		envConf.setTransactional(true);
		storeConf.setAllowCreate(true);
		//		storeConf.setTransactional(true);

		storeConf.setDeferredWrite(true);
		//		envConf.setLocking(false);

		environment = new Environment(dbDir, envConf);
		store = new EntityStore(environment, "EntityStore", storeConf);

		websiteIndex = store.getPrimaryIndex(String.class, WebURLExtension.class);

	}

	public static void close(){
		try{
			if(store != null)
				store.close();
			if(environment != null)
				environment.close();
		}catch(DatabaseException e){
			System.out.println("Cannot close database");
		}
	}
	/*------------------------------- Webpage --------------------------------*/
	/*
	 *  The following methods are used to store and retrieve the websites from the database (BerkeleyDB)
	 */
	
	//put a website in DB
	public void putWebpage(WebURLExtension webpage){
		websiteIndex.put(webpage);
		store.sync();
	}
	
	//get a website from DB
	public WebURLExtension getWebpage(String url){
		return websiteIndex.get(url);
	}

	//delete a website
	public void deleteWebpage(String url){
		websiteIndex.delete(url);
	}
	
	
}
package persistence;

import java.io.File;

import assigment3.a.InvertedIndexExtension;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Durability;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class BerkeleyDB_index {
	
	//instence
	private static BerkeleyDB_index instance;
	
	// to set up the database
	private static Environment environment;
	private static EntityStore store;

	private static PrimaryIndex<String, InvertedIndexExtension> iiIndex;

	private static String dbRoot = "F:\\GitHub\\IRProject\\IRProject\\data\\indexdata";
	private static File dbDir;
	
	private BerkeleyDB_index(){
		setup();
	}
	
	public static BerkeleyDB_index getInstance(){
		if(instance==null)
			instance=new BerkeleyDB_index();
		return instance;
	}
	
	public static Environment getEnv(){

		return environment;
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
		storeConf.setTransactional(true);

		//storeConf.setDeferredWrite(true);
		//		envConf.setLocking(false);

		environment = new Environment(dbDir, envConf);
		store = new EntityStore(environment, "EntityStore", storeConf);

		iiIndex = store.getPrimaryIndex(String.class, InvertedIndexExtension.class);

	}

	public static void close(){
		try{
			if(store != null)
				store.close();
			if(environment != null)
				environment.close();
			instance=null;
		}catch(DatabaseException e){
			System.out.println("Cannot close database");
		}
	}
	
	/*------------------------------- Webpage --------------------------------*/
	/*
	 *  The following methods are used to store and retrieve the websites from the database (BerkeleyDB_index)
	 */
	
	//put a index in DB
	public void putIndex(InvertedIndexExtension index){
		iiIndex.put(index);
		store.sync();
	}
	
	//get a index from DB
	public InvertedIndexExtension getIndex(String term){
		return iiIndex.get(term);		
	}
	
	public PrimaryIndex<String, InvertedIndexExtension> getMasterIndex(){
		return iiIndex;
	}
	
//	public long getTotalWebpages(){
//		return iiIndex.count();
		
//	}
	
	public EntityCursor<InvertedIndexExtension> getCursorIndex(){
		return iiIndex.entities();
	}

	//delete a entity
	public void deleteIndex(String term){
		iiIndex.delete(term);
	}
	

}

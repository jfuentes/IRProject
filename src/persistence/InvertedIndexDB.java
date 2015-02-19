package persistence;

import java.io.File;

import assigment3.a.TermInvertedIndex;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class InvertedIndexDB {
		//instence
		private static InvertedIndexDB instance;
		
		// to set up the database
		private static Environment environment;
		private static EntityStore store;

		private static PrimaryIndex<String, TermInvertedIndex> invertedIndex;

		private static String dbRoot = "/home/joel/workspace/IRProject/data/index";
		private static File dbDir;
		
		private InvertedIndexDB(){
			setup();
		}
		
		public static InvertedIndexDB getInstance(){
			if(instance==null)
				instance=new InvertedIndexDB();
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

			invertedIndex = store.getPrimaryIndex(String.class, TermInvertedIndex.class);

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
		/*------------------------------- TermInvertedIndex --------------------------------*/
		/*
		 *  The following methods are used to store and retrieve the terms from the database (BerkeleyDB)
		 */
		
		//put a website in DB
		public void putTerm(TermInvertedIndex term){
			invertedIndex.put(term);
			store.sync();
		}
		
		//get a website from DB
		public TermInvertedIndex getTerm(String term){
			return invertedIndex.get(term);		
		}
		
		public long getTotalTerms(){
			return invertedIndex.count();
			
		}
		
		public EntityCursor<TermInvertedIndex> getCursorTerms(){
			return invertedIndex.entities();
		}

		//delete a website
		public void deleteWebpage(String url){
			invertedIndex.delete(url);
		}
		
		public void syncStore(){
			store.sync();
		}
}

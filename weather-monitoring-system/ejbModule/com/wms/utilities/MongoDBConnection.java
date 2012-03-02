package com.wms.utilities;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Mongo m;
		try {
			m = new Mongo();
			DB db = m.getDB("test");

			//Get All Collection Names
//			Set<String> colls = db.getCollectionNames();
//
//			for (String s : colls) {
//				System.out.println(s);
//			}
			
			DBCollection testColl=db.getCollection("testColl_1");
			System.out.println(""+testColl.findOne());

			BasicDBObject name=new BasicDBObject();
			name.put("name0", "Nilay");
			name.put("name1", "Pray");
			name.put("name2", "Binit");
			
			BasicDBObject info=new BasicDBObject();
			info.put("namesNew", name);
			
			//testColl.update(new BasicDBObject().append("names", "{name:binit}"), info);
			//testColl.insert(info);
			//testColl.insert(new BasicDBObject().append("i", 2));
			testColl.save(name);
		
			//testColl.
			
			System.out.println(""+testColl.findOne());
			//testColl.insert(info);
			
			
//			Set<String> colls = db.getCollectionNames();
//
//			for (String s : colls) {
//				System.out.println(s);
//			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

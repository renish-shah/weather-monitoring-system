package com.wms.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class FileRead {

	private static final String WMS_TEMPORAL = "wmsTemporal";
	static DB db = null;

	public void setupDB() {

		try {
			Mongo m = new Mongo();
			db = m.getDB("test");
		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
	}

	public void retrieveData() {

		BasicDBObject time = new BasicDBObject();
		time.put("time.HHMM", "1830");
		time.put("YYMMDD", "20120201");

		DBCollection coll = db.getCollection(WMS_TEMPORAL);
		DBCursor cur = coll.find(time);

		FileOutputStream fo = setupEnvToWrite();
		PrintStream ps = new PrintStream(fo);

		while (cur.hasNext()) {
			ps.println(cur.next());
		}

		try {
			ps.close();
			fo.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public FileOutputStream setupEnvToWrite() {

		String envVar = System.getenv("JAVA_HOME");
		String dir = envVar + "/WMS_Output";

		if (!(new File(dir)).exists())
			new File(dir).mkdirs();

		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(envVar + "/WMS_Output/outputJson.out");
		} catch (FileNotFoundException e) {
			System.out.println("Exception :" + e);
			e.printStackTrace();
		}

		return fo;
	}

	public void insertIntoDB() {

		try {
			
			setupDB();

			// Open the file
			FileInputStream fstream = new FileInputStream(
					"F:\\SJSU_aCAdEMICS\\CMPE_275_JohnGash\\Data\\mesowest\\mesowest.out");

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			String pattern = "\\s{1,}";

			// Read File Line By Line
			for (int i = 0; i < 3; i++) {
				strLine = br.readLine();
			}

			strLine = br.readLine();
			strLine = strLine.replaceAll(pattern, "|");
			System.out.println("" + strLine);
			String headers[] = strLine.split("\\|");

			DBCollection coll = db.getCollection("WMS_TEMPORAL");
			coll.remove(new BasicDBObject());

			long initTime = System.currentTimeMillis();
			System.out.println("Init TIme :" + initTime);

			while ((strLine = br.readLine()) != null) {

				strLine = strLine.replaceAll(pattern, "|");
				String data[] = strLine.split("\\|");
				String[] timeData = data[2].split("/");
				String[] dateTimeHeader = headers[2].split("/");

				// db.createCollection(headers[1], data[1]);
				BasicDBObject date = new BasicDBObject();
				date.put(dateTimeHeader[0], timeData[0]); // YYMMDD/HHMM
															// 20120201/1900

				BasicDBObject time = new BasicDBObject();
				time.put(dateTimeHeader[1], timeData[1]);

				BasicDBObject stationId = new BasicDBObject();
				stationId.put(headers[1], data[1]);

				BasicDBObject stationData = new BasicDBObject();
				for (int i = 3; i < data.length; i++) {
					stationData.put(headers[i], data[i]);
				}

				stationId.put("stationData", stationData);
				time.put("stationId", stationId);
				date.put("time", time);

				coll.insert(date);

			}
			long endTime = System.currentTimeMillis();
			System.out.println("Total Time :" + (endTime - initTime));
			System.out.println("Inserted Count:" + coll.count());

			// Close the input stream
			in.close();

		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
	}

	public static void main(String[] args) {

		new FileRead().insertIntoDB();
		
		// Sample JSON

		/*
		 * { "_id" : ObjectId("4f503a79d6ad1608554584f2"), "YYMMDD" :
		 * "20120201", "time" : { "HHMM" : "1900", "stationId" : { "STN" :
		 * "ABAUT", "stationData" : { "MNET" : "8.00", "SLAT" : "37.84", "SLON"
		 * : "-109.46", "SELV" : "3453.00", "TMPF" : "19.33", "SKNT" : "10.01",
		 * "DRCT" : "207.00", "GUST" : "15.74", "PMSL" : "-9999.00", "ALTI" :
		 * "-9999.00", "DWPF" : "3.65", "RELH" : "49.74", "WTHR" : "-9999.00",
		 * "P24I" : "-9999.00" } } }
		 * 
		 * }
		 */
		
		// Find through Console (Shell)
		// msTemporal.find({"YYMMDD":"20120201","time.HHMM":"1815","time.HHMM":"1830"});

	}
}
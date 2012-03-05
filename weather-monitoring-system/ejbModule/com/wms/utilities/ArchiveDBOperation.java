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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ArchiveDBOperation {

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

	public void testFunctionality() {
		setupDB();

		if (!db.collectionExists(WMS_TEMPORAL))
			db.createCollection("WMS_TEMPORAL", null);

		// DBCollection coll = db.getCollection("WMS_TEMPORAL");
		DBCollection testRenish = db.getCollection("testRenish_1");

		BasicDBObject name = new BasicDBObject();
		name.put("name", "pray");
		name.put("name", "binit");

		testRenish.insert(name);

	}

	public void retrieveData(String startDate, String endDate,
			String startTime, String endTime) {

		setupDB();
		
		DateTimeUtility dateTimeUtility = new DateTimeUtility();
		
		List<String> listOfDates = new ArrayList<String>();
		
		
		if (null != startDate && startDate != "" && null != endDate
				&& endDate != "") {
			if (!startDate.equals(endDate)) // both dates are different
			{
				listOfDates = dateTimeUtility
						.getListOfDates(startDate, endDate);
			} else {
				listOfDates.add(startDate);
			}
		}
		
		// 2012-02-01 to 2012-03-04
		BasicDBObject time = new BasicDBObject();
		// time.put("time.HH", "03");
		// time.put("time.MM", "43");
		// time.put("time.HHMM", "0400");
		if(null==startTime || startTime.length()==0)
			time.put("YYMMDD", new BasicDBObject("$in", listOfDates));
		else if(null != endTime && endTime.length()>0)
		{
			List<String> listOfHours = new ArrayList<String>();
			listOfHours=dateTimeUtility.getListOfHours(Integer.parseInt(startTime), Integer.parseInt(endTime));
			time.put("time.HH", new BasicDBObject("$in", listOfHours));
			time.put("YYMMDD", new BasicDBObject("$in", listOfDates));
			
			
		}
		// time.put("YYMMDD", "20120201");

		DBCollection coll = db.getCollection(WMS_TEMPORAL);
		DBCursor cur = coll.find(time);

		FileOutputStream fo = setupEnvToWrite();
		PrintStream ps = new PrintStream(fo);

		// Sample Output
		// {
		// "output1": {},
		// "output2": {},
		// "output0": ""
		// }

		ps.print('{');
		int count = 1;
		while (cur.hasNext()) {
			ps.println("\"output" + count + "\":" + cur.next() + ",");
			count++;
		}
		ps.println("\"output0\": \"\"}");

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
					"F:\\SJSU_aCAdEMICS\\CMPE_275_JohnGash\\Data\\8.26\\mesowest.out\\mesowest.out");

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

			// if(!db.collectionExists(WMS_TEMPORAL))
			// db.createCollection("WMS_TEMPORAL", null);

			DBCollection coll = db.getCollection(WMS_TEMPORAL);
			// coll.remove(new BasicDBObject());

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

				BasicDBObject timeHour = new BasicDBObject();
				timeHour.put("HH", timeData[1].substring(0, 2));
				timeHour.put("MM", timeData[1].substring(2, 4));

				// BasicDBObject timeMinute = new BasicDBObject();
				// timeMinute.put("MM", timeData[1].substring(2, 4));

				BasicDBObject stationId = new BasicDBObject();
				stationId.put(headers[1], data[1]);

				BasicDBObject stationData = new BasicDBObject();
				for (int i = 3; i < data.length; i++) {
					stationData.put(headers[i], data[i]);
				}

				stationId.put("stationData", stationData);
				// timeMinute.put("stationId", stationId);
				// timeHour.put("minute", timeMinute);
				timeHour.put("stationId", stationId);
				date.put("time", timeHour);

				// BasicDBObject outputData = new BasicDBObject();
				// outputData.put("outputData", date);

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

		// new
		// ArchiveDBOperation().retrieveData("20120304","20120304","0300","0800");
		// new FileRead().testFunctionality();
		// new ArchiveDBOperation().insertIntoDB();
		new ArchiveDBOperation().retrieveData("20120304", "20120304", "03",
				"08");
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
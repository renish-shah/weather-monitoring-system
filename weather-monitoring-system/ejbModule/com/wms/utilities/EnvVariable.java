package com.wms.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EnvVariable {

	public static void main(String args[]) {
		try {

			String dateTime = "20120201/1900";
			String[] tokensDateTime = dateTime.split("/");

			String year = tokensDateTime[0].substring(0, 4);
			System.out.println("Year is :" + year);

			String month = tokensDateTime[0].substring(4, 6);
			System.out.println("Month:" + month);

			String day = tokensDateTime[0].substring(6, 8);
			System.out.println("Day :" + day);

			SimpleDateFormat df = new SimpleDateFormat("HHmm");
			Date date = df.parse(tokensDateTime[1]);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, -15);
			
			StringBuilder fileName=new StringBuilder();
			fileName.append(""+cal.get(Calendar.HOUR_OF_DAY)+cal.get(Calendar.MINUTE));
			
			cal.add(Calendar.MINUTE, 30);
			
			fileName.append("-"+cal.get(Calendar.HOUR_OF_DAY)+cal.get(Calendar.MINUTE));
			
			System.out.println("FileName :"+fileName);
			
			String environmentVariable = System.getenv(args[0]);
			// String environmentVariable="C:/Program Files/Java/jdk1.7.0";
			System.out.print("Environment Variable are =>"
					+ environmentVariable);

			String dir = environmentVariable + "/test/test23";
			if (!(new File(dir)).exists())
				new File(dir).mkdirs();

		} catch (Exception e) {
			System.out.println("Exception caught =" + e.getMessage());
		}
	}
}

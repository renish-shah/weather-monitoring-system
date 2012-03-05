package com.wms.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// new DateTimeUtility().getListOfDates("20120304", "20120306");
		new DateTimeUtility().getListOfHours(7, 15);

	}

	@SuppressWarnings("deprecation")
	public List<String> getListOfHours(int startTime, int endTime) {

		Date endDate = new Date(2012, 01, 01, endTime, 00);

		Calendar startCalendar = Calendar.getInstance();
		// Set the time for the startCalendar with the startDate
		startCalendar.set(2012, 01, 01, startTime, 00);

		List<String> listOfHours = new ArrayList<String>();

		while (startCalendar.get(Calendar.HOUR_OF_DAY) != endDate.getHours()) {
			// Add 1 to the startCalendar's HOUR.

			String hour;
			if (("" + startCalendar.get(Calendar.HOUR_OF_DAY)).length() == 1)
				hour = "0" + startCalendar.get(Calendar.HOUR_OF_DAY);
			else
				hour = "" + startCalendar.get(Calendar.HOUR_OF_DAY);

			listOfHours.add(hour);
			startCalendar.add(Calendar.HOUR_OF_DAY, 1);
		}

		for (int i = 0; i < listOfHours.size(); i++) {
			// System.out.println("" + listOfHours.get(i).getHours());
			System.out.println("" + listOfHours.get(i));
		}
		return listOfHours;

	}

	@SuppressWarnings("deprecation")
	public List<String> getListOfDates(String startDate1, String endDate1) {

		Date endDate = new Date(Integer.parseInt(endDate1.substring(0, 4)),
				Integer.parseInt(endDate1.substring(4, 6)),
				Integer.parseInt(endDate1.substring(6, 8)));

		Calendar startCalendar = Calendar.getInstance();

		// Set the time for the startCalendar with the startDate
		startCalendar.set(Integer.parseInt(startDate1.substring(0, 4)),
				Integer.parseInt(startDate1.substring(4, 6)),
				Integer.parseInt(startDate1.substring(6, 8)));

		List<String> dates = new ArrayList<String>();
		dates.add(startDate1);

		while (startCalendar.get(Calendar.YEAR) != endDate.getYear()
				|| startCalendar.get(Calendar.MONTH) != endDate.getMonth()
				|| startCalendar.get(Calendar.DATE) != endDate.getDate()) {

			// Add 1 to the startCalendar's DATE.
			startCalendar.add(Calendar.DATE, 1);
			String month, date;

			if (("" + startCalendar.get(Calendar.MONTH)).length() == 1)
				month = "0" + startCalendar.get(Calendar.MONTH);
			else
				month = "" + startCalendar.get(Calendar.MONTH);

			if (("" + startCalendar.get(Calendar.DATE)).length() == 1)
				date = "0" + startCalendar.get(Calendar.DATE);
			else
				date = "" + startCalendar.get(Calendar.DATE);

			// 20120304 - YYYYMMDD
			dates.add(startCalendar.get(Calendar.YEAR) + month + date);

		}

		for (int i = 0; i < dates.size(); i++) {
			System.out.println("" + dates.get(i));
		}
		return dates;

	}
}

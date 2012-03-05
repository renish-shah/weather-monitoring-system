package com.wms.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateManipulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 new DateManipulation().dateOperation();
		//new DateManipulation().timeOperation();

		// List<Date> dates = new ArrayList<Date>();
		//
		// Calendar ca1 = Calendar.getInstance();
		// ca1.set(2009, 05, 25);
		//
		// @SuppressWarnings("deprecation")
		// Date endDate = new Date(2012, 06, 27);
		//
		// // Calendar ca2 = Calendar.getInstance();
		// // ca2.set(2012, 06, 27);
		//
		// // Addition of date in java
		// // ca1.add(Calendar.DATE, 23); // Add 23 days in Dates in Calendar
		// // ca1.add(Calendar.MONTH, 2); // Add 2 Month in Date in Calendar
		// // ca1.add(Calendar.YEAR, 4); // Add 4 Year in Date in Calendar
		//
		// while (
		//
		// ca1.get(Calendar.YEAR) != endDate.getYear()
		// || ca1.get(Calendar.MONTH) != endDate.getMonth()
		// || ca1.get(Calendar.DATE) != endDate.getDate()) {
		// ca1.add(Calendar.DATE, 1);
		//
		// dates.add(new Date(ca1.get(Calendar.YEAR), ca1.get(Calendar.MONTH),
		// ca1.get(Calendar.DATE)));
		// // dates.add(calNew);
		// }
		//
		// /*
		// * Subtracting Date in Calendar
		// *
		// * ca1.add(Calendar.DATE, -23); // Subtracting 23 days from date
		// * //ca1.add(Calendar.MONTH, -2); // Subtracting 2 Month in Date in
		// * Calendar //ca1.add(Calendar.YEAR, -4); // Subtracting 4 Year in
		// Date
		// * in Calendar
		// */
		//
		// for (Date ca : dates) {
		// System.out.println("Year:" + ca.getYear() + " Month:"
		// + ca.getMonth() + " Date:" + ca.getDate());
		// }

		// System.out.println("Date :" + ca1.get(Calendar.DATE));
		// System.out.println("Month :" + ca1.get(Calendar.MONTH));
		// System.out.println("Year :" + ca1.get(Calendar.YEAR));

	}

	@SuppressWarnings("deprecation")
	public void timeOperation() {
		Date startDate = new Date(20120, 01, 01, 19, 00);
		System.out.println("hour is:" + startDate.getHours());
		// startDate.setHours(19);
		// startDate.setMinutes(05);

		// System.out.println("Year :" + startDate.getYear());

		Date endDate = new Date(2012, 01, 01, 24, 00);
		// startDate.setHours(24);
		// startDate.setMinutes(00);

		Calendar startCalendar = Calendar.getInstance();
		// Set the time for the startCalendar with the startDate
		startCalendar.set(2012, 01, 01, startDate.getHours(), 00);

		System.out.println("Hour is :"
				+ startCalendar.get(Calendar.HOUR_OF_DAY));

		// System.out.println("" + startCalendar.get(Calendar.YEAR));

		List<Date> dates = new ArrayList<Date>();

		while (startCalendar.get(Calendar.HOUR_OF_DAY) != endDate.getHours()) {
			// Add 1 to the startCalendar's HOUR.
			startCalendar.add(Calendar.HOUR_OF_DAY, 1);
			dates.add(new Date(startCalendar.get(Calendar.YEAR), startCalendar
					.get(Calendar.MONTH), startCalendar.get(Calendar.DATE),
					startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar
							.get(Calendar.MINUTE)));
		}
		// System.out.println("Date :"+dates.get);

		for (int i = 0; i < dates.size(); i++) {
			System.out.println("" + dates.get(i).getHours());
		}

	}

	@SuppressWarnings("deprecation")
	public void dateOperation() {
		Date startDate = new Date(2012, 04, 27, 19, 00);

		System.out.println("Year :" + startDate.getYear());

		Date endDate = new Date(2012, 05, 27, 24, 00);

		Calendar startCalendar = Calendar.getInstance();
		// Set the time for the startCalendar with the startDate
		startCalendar.set(startDate.getYear(), startDate.getMonth(),
				startDate.getDate());

		System.out.println("" + startCalendar.get(Calendar.YEAR));

		List<Date> dates = new ArrayList<Date>();

		while (startCalendar.get(Calendar.YEAR) != endDate.getYear()
				|| startCalendar.get(Calendar.MONTH) != endDate.getMonth()
				|| startCalendar.get(Calendar.DATE) != endDate.getDate()
				|| startCalendar.get(Calendar.HOUR_OF_DAY) != endDate
						.getHours()) {
			// Add 1 to the startCalendar's DATE.
		
			startCalendar.add(Calendar.HOUR_OF_DAY, 1);
			dates.add(new Date(startCalendar.get(Calendar.YEAR), startCalendar
					.get(Calendar.MONTH), startCalendar.get(Calendar.DATE),
					startCalendar.get(Calendar.HOUR_OF_DAY), startCalendar
							.get(Calendar.MINUTE)));
		}
		// System.out.println("Date :"+dates.get);

		for (int i = 0; i < dates.size(); i++) {
			System.out.println("" + dates.get(i).getYear() + " "
					+ (dates.get(i).getMonth() + 1) + " "
					+ dates.get(i).getDate()+" "+dates.get(i).getHours());
		}

	}
}

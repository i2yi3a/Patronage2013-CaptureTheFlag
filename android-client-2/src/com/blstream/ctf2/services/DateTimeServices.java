package com.blstream.ctf2.services;

import java.util.Calendar;

import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * @author Lukasz Dmitrowski
 */

public class DateTimeServices {
	public final static String dateTimeToString(TimePicker timeP, DatePicker dateP) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		timeP.setIs24HourView(true);
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH);
		int nowDay = c.get(Calendar.DAY_OF_MONTH) + 1;
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		int nowMinute = c.get(Calendar.MINUTE);
		int year = dateP.getYear();
		int month = dateP.getMonth();
		int day = dateP.getDayOfMonth();
		int hour = timeP.getCurrentHour();
		int minute = timeP.getCurrentMinute();

		String dateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";

		if (year > nowYear)
			return dateTime;
		if (year < nowYear)
			return "";
		if (month > nowMonth)
			return dateTime;
		if (month < nowMonth)
			return "";
		if (day > nowDay)
			return dateTime;
		if (day < nowDay)
			return "";
		if (hour > nowHour)
			return dateTime;
		if (hour < nowHour)
			return "";
		if (minute > nowMinute)
			return dateTime;
		else
			return "";
	}
	
	public final static void setDefaultTime(String dateTime, DatePicker dateP,TimePicker timeP){
		int []timeArray = new int[5];
		timeArray[0] = Integer.parseInt(dateTime.substring(0, dateTime.indexOf("-")));
		timeArray[1] = Integer.parseInt(dateTime.substring(dateTime.indexOf("-") + 1, dateTime.lastIndexOf("-")));
		timeArray[2] = Integer.parseInt(dateTime.substring(dateTime.lastIndexOf("-") + 1,dateTime.indexOf(" ")));
		timeArray[3] = Integer.parseInt(dateTime.substring(dateTime.lastIndexOf(" ") + 1, dateTime.indexOf(":")));
		timeArray[4] = Integer.parseInt(dateTime.substring(dateTime.indexOf(":") + 1, dateTime.lastIndexOf(":")));
		
		dateP.init(timeArray[0], timeArray[1]-1, timeArray[2], null);
		timeP.setIs24HourView(true);
		timeP.setCurrentHour(timeArray[3]);
		timeP.setCurrentMinute(timeArray[4]);
	}
}

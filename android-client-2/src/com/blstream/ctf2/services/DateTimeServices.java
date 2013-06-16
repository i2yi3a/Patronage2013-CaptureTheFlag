package com.blstream.ctf2.services;

import java.util.Calendar;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * @author Lukasz Dmitrowski
 */

public class DateTimeServices {
	public final static String dateToString(DatePicker dateP) {
		Calendar c = Calendar.getInstance();
		//c.add(Calendar.MONTH, -1);
		int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH);
		int nowDay = c.get(Calendar.DAY_OF_MONTH);
		int year = dateP.getYear();
		int month = dateP.getMonth() + 1;
		String misiac = "" + month;
		Log.i("Miesiac ustawiany: ",misiac );
		int day = dateP.getDayOfMonth();
		String date;
		
		if (month<10) date = year + "-" + "0" +  month + "-" + day + " ";
		else  date = year + "-" + month + "-" + day + " ";

		if (year > nowYear)
			return date;
		if (year < nowYear)
			return "";
		if (month > nowMonth)
			return date;
		if (month < nowMonth)
			return "";
		if (day > nowDay)
			return date;
		if (day < nowDay)
			return "";
		else
			return "";
	}

	public final static String timeToString(TimePicker timeP) {
		Calendar c = Calendar.getInstance();
		timeP.setIs24HourView(true);
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		int nowMinute = c.get(Calendar.MINUTE);
		int hour = timeP.getCurrentHour();
		int minute = timeP.getCurrentMinute();
		
		String time = hour + ":" + minute + ":00";
		
		
		if (hour > nowHour)
			return time;
		if (hour < nowHour)
			return "";
		if (minute > nowMinute)
			return time;
		else
			return "";
	}

	public final static void setDefaultDate(String date, DatePicker dateP) {
		Calendar c = Calendar.getInstance();
		int day = 0;
		int month = 0;
		int year = 0;
		year = Integer.parseInt(date.substring(0,4));
		month = Integer.parseInt(date.substring(5,7));
		day = Integer.parseInt(date.substring(8,10));
		
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.MONTH, -1);
		dateP.init(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH), null);
	}

	public final static void setDefaultTime(String time, TimePicker timeP) {
		int hour = 0;
		int minute = 0;
		hour = Integer.parseInt(time.substring(0,2));
		minute = Integer.parseInt(time.substring(3,5));

		timeP.setCurrentHour(hour);
		timeP.setCurrentMinute(minute);
	}
}

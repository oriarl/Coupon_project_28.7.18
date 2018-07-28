package com.example.sidekicks;

import java.util.Calendar;
import java.util.Date;

public class DateMaker {

	/**
	 * Method that fix dates values
	 * @param year
	 * @param month
	 * @param day
	 * @return fix date
	 */
	public static Date fixDate(int year , int month, int day)
	{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(year, month , day);
		date = cal.getTime();
		
		return date;
	}

}

package com.dev.appxpress.gps.android_pc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author NThusitha
 *
 */
public class DateTimeUtil {

	/**
	 * @return - UTC Date/time
	 */
	public static String nowUTCString(){
		
		Calendar calendar = Calendar.getInstance();
		TimeZone timeZone = calendar.getTimeZone();
		
		long currentTime = System.currentTimeMillis();
		int offsetWithUTC = timeZone.getOffset(currentTime);
		long utcTime = currentTime - offsetWithUTC;
		Date now = new Date(utcTime);
		SimpleDateFormat df = new SimpleDateFormat();
		return df.format(now);
		
	}
	
	/**
	 * @return - Long version of time.
	 */
	public static long nowUTC(){
		
		Calendar calendar = Calendar.getInstance();
		TimeZone timeZone = calendar.getTimeZone();
		
		long currentTime = System.currentTimeMillis();
		int offsetWithUTC = timeZone.getOffset(currentTime);
		long utcTime = currentTime - offsetWithUTC;
		
		return utcTime;
		
	}
	
}

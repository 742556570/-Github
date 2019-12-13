package com.jeeplus.modules.app.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.jeeplus.common.utils.DateUtils;

public class TTLUtils {
	
	public static int secondsFromNowToToday(){
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return (int) (todayEnd.getTime().getTime() - new Date().getTime()) / 1000;
	}
	
	
	public static int secondsFromNowToThisWeekend(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return (int) (currentDate.getTime().getTime() - new Date().getTime()) / 1000;
	}
	
	public static String todayStart() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 00);
		todayStart.set(Calendar.MINUTE, 00);
		todayStart.set(Calendar.SECOND, 00);
		todayStart.set(Calendar.MILLISECOND, 000);
		String startStr = DateUtils.formatDate(todayStart.getTime(), "yyyy-MM-dd HH:mm:ss.SSS");
		return startStr;
	}
	
	public static String todayEnd() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		String endStr = DateUtils.formatDate(todayEnd.getTime(), "yyyy-MM-dd HH:mm:ss.SSS");
		return endStr;
	}
	
	
	public static String thisWeekStart(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 00);
		currentDate.set(Calendar.MINUTE, 00);
		currentDate.set(Calendar.SECOND, 00);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return DateUtils.formatDate(currentDate.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String thisWeekEnd(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 00);
		currentDate.set(Calendar.MINUTE, 00);
		currentDate.set(Calendar.SECOND, 00);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return DateUtils.formatDate(currentDate.getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static void main(String[] args){
		System.out.println(thisWeekEnd());
		
	}

}

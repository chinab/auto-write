package com.autowrite.common.framework.entity;

import java.util.Calendar;

public class CommonEntity {
	public String getWriteBoardDateTime(String write_datetime) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		String monthStr = null;
		String dayStr = null;
		
		if ( month < 10 ) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		
		if ( day < 10 ) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		
		String dbDate = write_datetime.substring(0, 10);
		String sysDate = year + "-" + monthStr + "-" + dayStr;
		
		if ( dbDate.equals(sysDate) ) {
			return write_datetime.substring(11, 16);
		} else {
			if ( new Integer(write_datetime.substring(0, 4)) == year ) {
				return write_datetime.substring(5, 10);
			} else {
				return dbDate;
			}
		}
	}
	
	public String nvl(String value){
		if ( value == null ) {
			return "";
		} else {
			return value;
		}
	}
}

package com.wsd.android.utils;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.ocpsoft.prettytime.PrettyTime;

public class WSDDate {
	public static String humanize(Date date) {
		return humanize(date, new Locale("es", "ES"));
	}
	
	public static String humanize(Date date, Locale locale) {
//		return naturalTime(date, new Date(0), locale);
		
		PrettyTime p = new PrettyTime(locale);
		
		if ((date.getTime() - new Date().getTime()) < TimeUnit.MICROSECONDS.convert(1, TimeUnit.DAYS)) {
			return p.format(date);
		}
		
		return p.format(date);
	}
}

package com.android.nobug.util;

import android.content.Context;

import com.android.kreators.tortoise.R;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;

public class CalculationUtil {

	//  ======================================================================================

	public static boolean getOverNumber(float value, float base) {
		return value > base;
	}

	public static boolean getUnderNumber(float value, float base) {
		if( (int)value == 0 )
			return true;
		return value < base;
	}


	//  ====================================================================================

	public static String getTime(Context context, Calendar calendar) {
		String am = calendar.get(Calendar.AM_PM) == 0 ? context.getString(R.string.am) : context.getString(R.string.pm);
		String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + "\n" + am;
		return time;
	}

	public static String getDate(Calendar calendar) {
		//  String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		String date = getMonth(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
		return date;
	}

	private static String getMonth(int num) {
		String month = "";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		if ( num < 12 ) {
			month = months[num];
		}

		if( month.length() > 3 ) {
			return month.substring(0, 3);
		}
		return month;
	}

	//  ======================================================================================

	public static boolean isHoliday(Calendar calendar) {
		try {
			return isWeekend(calendar);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isWeekend(Calendar calendar) throws ParseException {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	public static String getClearDate(Calendar calendar) {
		int day = 0;
		int weekend = 0;
		int count = 0;
		int plus = 3;

		while ( !(count == plus) ) {
			calendar.add(Calendar.DATE, count);

			if( CalculationUtil.isHoliday(calendar) ) {
				weekend++;
			} else {
				day++;
			}
			count++;
		}
		calendar.add(Calendar.DATE, weekend);
		return CalculationUtil.getDate(calendar);
	}


}

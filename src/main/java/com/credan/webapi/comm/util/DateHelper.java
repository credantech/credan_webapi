/**
 * @Project: credan_common @(#) DateHelper.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年5月23日 下午4:20:39 $
 */
public class DateHelper extends DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy-MM", "yyyy/MM/dd HH:mm" };

	/**
	 * 当前时间
	 *
	 * @return Date
	 */
	public static Date getCurrentTime() {
		return new Date();
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * <<<<<<< HEAD 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
	 * "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd
	 * HH:mm" } ======= 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
	 * "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd
	 * HH:mm" } >>>>>>> ef1ca06298e0655f57c4a703c328862d04762d83
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	public static Date getDateStart(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 判断字符串是否是日期
	 * 
	 * @param timeString
	 * @return
	 */
	public static boolean isDate(String timeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			format.parse(timeString);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化时间
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String dateFormat(Date timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(timestamp);
	}

	/**
	 * 获取系统时间Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getSysTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 生成时间随机数
	 * 
	 * @return
	 */
	public static String getDateRandom() {
		String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return s;
	}

	/**
	 * 计算月份差
	 * 
	 * @param dataFrom
	 * @param dataTo
	 * @return
	 * @throws Exception
	 */
	public static int getMonthsBetween(String dataFrom, String dataTo) throws Exception {

		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal.setTime(parseDate(dataFrom));
		cal2.setTime(parseDate(dataTo));

		int result = (cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) * 12
				+ (cal2.get(Calendar.MONTH) - cal.get(Calendar.MONTH));
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 计算月份差，与上面相同，只是参数类型不同
	 * 
	 * @param dataFrom
	 * @param dataTo
	 * @return
	 * @throws Exception
	 */
	public static int getMonthsBetween(Date dataFrom, Date dataTo) throws Exception {

		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal.setTime(dataFrom);
		cal2.setTime(dataTo);

		int result = (cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) * 12
				+ (cal2.get(Calendar.MONTH) - cal.get(Calendar.MONTH));
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 获取月份
	 * 
	 * @param data
	 * @return
	 */
	public static int getMonth(String date) {
		Date parseDate = parseDate(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static String getDateWithoutDay() {
		return getDate("yyyy-MM");
	}

	/**
	 * 获取下月日期
	 * 
	 * @return
	 */
	public static Date getNextMonthDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getMonthDate(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, i);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getMonthDate(int i, String formate) {
		Date monthDate = getMonthDate(i);
		if (StringUtils.isBlank(formate)) {
			formate = "yyyy-MM-dd";
		}
		String formatDate = formatDate(monthDate, formate);
		return parseDate(formatDate);
	}

	public static int compare(Date firstDate, Date secondDate) {
		Calendar firstCalendar = null;
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}
		Calendar secondCalendar = null;
		if (secondDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}
		try {
			return firstCalendar.compareTo(secondCalendar);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(Date date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(Date date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 根据所给日期返回两日期相差的秒数
	 * 
	 * @param d1
	 * @param d2
	 * @return 返回两个日期间隔的毫秒数
	 */
	public static long getSecond(Date d1, Date d2) {
		long a1 = d1.getTime();
		long a2 = d2.getTime();
		long a3 = (a1 - a2) / 1000;
		return a3;
	}

	/***
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDays(Date d1, Date d2) {
		long a1 = d1.getTime();
		long a2 = d2.getTime();
		long a3 = ((a1 - a2) / 1000) / (60 * 60 * 24);
		return Integer.valueOf(a3 + "");
	}

	/**
	 * 获取n天后发日期
	 * 
	 * @param gapDays
	 * @return
	 */
	public static String getDateAfterDays(int gapDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, gapDays);
		String date = formatDate(calendar.getTime());
		return date;
	}


	
}

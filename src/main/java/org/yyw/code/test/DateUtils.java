package org.yyw.code.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * <pre>
 *  日期工具类
 * </pre>
 * 
 * @author developer developer@midea.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class DateUtils {
	// 预设日期格式
		private static final String DEFAULTDATEPATTERN = "yyyy/MM/dd";

		// 预设日期时间格式
		private static final String DEFAULTDATETIMEPATTERN = "yyyy/MM/dd HH:mm:ss";

		/**
		 * 获得当前时间，格式 yyyy/MM/dd HH:mm:ss
		 * 
		 * @return
		 */
		public static String getCurrDateTime() {
			return format(Calendar.getInstance().getTime(), DEFAULTDATETIMEPATTERN);
		}

		/**
		 * 根据时间格式获得当前时间
		 */
		public static String getCurrDateTime(String partten) {
			return format(Calendar.getInstance().getTime(), partten);
		}

		/**
		 * 根据默认格式获得格式化的时间,格式：yyyy/MM/dd HH:mm:ss
		 * 
		 * @param date
		 * @return
		 */
		public static String formatDateTime(Date date) {
			return format(date, DEFAULTDATETIMEPATTERN);
		}

		/**
		 * 根据默认格式获得格式化的时间,格式：yyyy/MM/dd HH:mm:ss
		 * 
		 * @param millis
		 * @return
		 */
		public static String formatDateTime(long millis) {
			return format(millis, DEFAULTDATETIMEPATTERN);
		}

		/**
		 * 根据默认格式获得格式化的日期，格式:yyyy/MM/dd
		 * 
		 * @param date
		 * @return
		 */
		public static String formatDate(Date date) {
			return format(date, DEFAULTDATEPATTERN);
		}

		/**
		 * 根据默认格式获得格式化的日期，格式:yyyy/MM/dd
		 * 
		 * @param millis
		 * @return
		 */
		public static String formatDate(long millis) {
			return format(millis, DEFAULTDATEPATTERN);
		}

		/**
		 * 获得格式化的时间
		 * 
		 * @param date
		 * @param pattern
		 * @return
		 */
		public static String format(Date date, String pattern) {
			return format(date.getTime(), pattern);
		}

		/**
		 * 获得格式化的时间
		 * 
		 * @param millis
		 * @param pattern
		 * @return
		 */
		public static String format(long millis, String pattern) {
			return DateFormatUtils.format(millis, pattern);
		}

		/**
		 * 增加天数
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addDays(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addDays(date, amount);
		}

		/**
		 * 增加小时
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addHours(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addHours(date, amount);
		}

		/**
		 * 增加毫秒
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addMilliseconds(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addMilliseconds(date,
					amount);
		}

		/**
		 * 增加分钟
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addMinutes(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addMinutes(date, amount);
		}

		/**
		 * 增加月
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addMonths(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addMonths(date, amount);
		}

		/**
		 * 增加秒
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addSeconds(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addSeconds(date, amount);
		}

		/**
		 * 增加周
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addWeeks(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addWeeks(date, amount);
		}

		/**
		 * 增加年
		 * 
		 * @param date
		 * @param amount
		 * @return
		 */
		public static Date addYears(Date date, int amount) {
			return org.apache.commons.lang.time.DateUtils.addYears(date, amount);
		}

		/**
		 * 判断是否是同一时间
		 * 
		 * @param cal1
		 * @param cal2
		 * @return
		 */
		public static boolean isSameDay(Calendar cal1, Calendar cal2) {
			return org.apache.commons.lang.time.DateUtils.isSameDay(cal1, cal2);
		}

		/**
		 * 判断是否是同一时间
		 * 
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static boolean isSameDay(Date date1, Date date2) {
			return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
		}

		/**
		 * 按照要求精度截取时间 比如输入日期为2008/05/12 13:10:30 ,要求精度为小时，则返回2008/05/12 13:00:00
		 * 要求的精度为月，则返回2008/05/01 00:00:00
		 * 
		 * @param date
		 * @param field
		 *            Calendar中的属性
		 * @return
		 */
		public static Calendar truncate(Calendar date, int field) {
			return org.apache.commons.lang.time.DateUtils.truncate(date, field);
		}

		/**
		 * 按照要求精度截取时间 比如输入日期为2008/05/12 13:10:30 ,要求精度为小时，则返回2008/05/12 13:00:00
		 * 要求的精度为月，则返回2008/05/01 00:00:00
		 * 
		 * @param date
		 * @param field
		 *            Calendar中的属性
		 * @return
		 */
		public static Date truncate(Date date, int field) {
			return org.apache.commons.lang.time.DateUtils.truncate(date, field);
		}

		/**
		 * 获得两个时间的间隔,可以按秒、分钟、小时、天来获取
		 * 
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static int elapsed(Date date1, Date date2, int field) {
			long elapsed = date2.getTime() - date1.getTime();

			switch (field) {
			case Calendar.SECOND:
				return Long.valueOf(elapsed / 1000).intValue();
			case Calendar.MINUTE:
				return Long.valueOf(elapsed / (1000 * 60)).intValue();
			case Calendar.HOUR:
				return Long.valueOf(elapsed / (1000 * 60 * 60)).intValue();
			case Calendar.DATE:
				return Long.valueOf(elapsed / (1000 * 60 * 60 * 24)).intValue();
			}

			return Long.valueOf(elapsed / (1000 * 60 * 60 * 24)).intValue();
		}

		/**
		 * 使用参数Format将字符串转为Date
		 * 
		 * @param strDate
		 * @param pattern
		 * @return
		 * @throws ParseException
		 */
		public static Date parse(String strDate, String pattern)
				throws ParseException {
			return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(
					pattern).parse(strDate);
		}

		/**
		 * 按照默认格式（YYYY/MM/DD）将字符串转为Date
		 * 
		 * @param strDate
		 * @return
		 * @throws ParseException
		 */
		public static Date parse(String strDate) throws ParseException {
			return parse(strDate, DEFAULTDATEPATTERN);
		}

		/**
		 * 获取传入月份的第一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getMinDateOfMonth(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return calendar.getTime();
		}

		/**
		 * 获取传入月份的最大天数
		 * 
		 * @param date
		 * @return
		 */
		public static Date getMaxDateOfMonth(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			return calendar.getTime();
		}

		/**
		 * 获取两个日期的间隔天数
		 * 
		 * @param startDate
		 * @param endDate
		 * @return
		 */
		public static long getDaysBetween(Date startDate, Date endDate) {
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTime(startDate);
			fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
			fromCalendar.set(Calendar.MINUTE, 0);
			fromCalendar.set(Calendar.SECOND, 0);
			fromCalendar.set(Calendar.MILLISECOND, 0);

			Calendar toCalendar = Calendar.getInstance();
			toCalendar.setTime(endDate);
			toCalendar.set(Calendar.HOUR_OF_DAY, 0);
			toCalendar.set(Calendar.MINUTE, 0);
			toCalendar.set(Calendar.SECOND, 0);
			toCalendar.set(Calendar.MILLISECOND, 0);

			return (toCalendar.getTime().getTime() - fromCalendar.getTime()
					.getTime()) / (1000 * 60 * 60 * 24);
		}
}

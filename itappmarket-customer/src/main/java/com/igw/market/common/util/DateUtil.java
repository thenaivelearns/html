package com.igw.market.common.util;

import com.microsoft.sqlserver.jdbc.StringUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	private static Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * 日期相比较，入参日期大，则返回true
	 * 
	 * @param date
	 * @return
	 */
	public static boolean compareDate(String date, String weekDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		boolean before = true;
		try {
			if (StringUtils.isEmpty(date)) {
				return false;
			}
			Date date1 = format.parse(weekDate);
			Date date2 = format.parse(date);
			before = date1.before(date2);
		} catch (ParseException e) {
			logger.error("比较日期异常", e);
		}
		return before;
	}


	/**
	 * 秒转换成 HH:MM:SS格式
	 * 
	 * @param time
	 * @return
	 */
	public static String transfomHMS(final int time) {
		int hh = time / 3600;
		int mm = (time % 3600) / 60;
		int ss = (time % 3600) % 60;
		return (hh < 10 ? ("0" + hh) : hh) + ":" + (mm < 10 ? ("0" + mm) : mm) + ":" + (ss < 10 ? ("0" + ss) : ss);
	}
	/**
	 *  HH:MM:SS转换成秒
	 * 
	 * @param time
	 * @return
	 */
	public static String transfomS(final String time) {
		String[] arr =time.split(":");
		int i =0;
		 i =Integer.parseInt(arr[0])*3600+Integer.parseInt(arr[1])*60+Integer.parseInt(arr[2]);
	
		return String.valueOf(i);
	}

	/**
	 * 传入数字格式string，返回当前时间的string
	 * 
	 * @throws Exception
	 */
	public static String getTimeString(String formatString) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		return format.format(date);
	}

	/**
	 * 小时分钟 格式 HH:mm
	 * 
	 * @return HH:mm
	 * @throws Exception
	 */
	public static String getHHmm() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(date);
	}

	/**
	 * 小时 格式 HH
	 * 
	 * @return HH:mm
	 * @throws Exception
	 */
	public static String getHH() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return format.format(date);
	}

	/**
	 * 月日 格式为 MMdd
	 * 
	 * @return MMDD
	 * @throws Exception
	 */
	public static String getDateMMDD() throws Exception {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MMdd");
		return format.format(date);
	}
	
	/**
	 * 月日 格式为 MMdd
	 * 
	 * @return MMDD
	 * @throws Exception
	 */
	public static String getMMDD() throws Exception {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		return format.format(date);
	}

	/**
	 * 年月日 格式为 YYMMdd
	 * 
	 * @return YYMMDD
	 */
	public static String getDateYYMMDD() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		return format.format(date);
	}

	/**
	 * 获取当前月
	 * 
	 * @return
	 */
	public static String getMM() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return format.format(c.getTime());
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static String getYYYY() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYYMMDD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMMDD() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYYMM
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMM() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYYMMDD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMMDDHHMMSS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * 
	 * @param nowTime
	 *            当前时间
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static boolean isEffectiveDate(String nowTime, String startTime, String endTime) throws Exception {
		return isEffectiveDate(getYYYYMMDDHHMMss(nowTime), getYYYYMMDDHHMMss(startTime), getYYYYMMDDHHMMss(endTime));
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * 
	 * @param nowTime
	 *            当前时间
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * 
	 * @param nowTime
	 *            当前时间
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static boolean isEffectiveDate(Date nowTime, Date endTime) {
		if (nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 年月日 格式 YYYYMMDD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getHHMMSS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYYMMDD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMMDD(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYYMMDD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getHHMMSS() {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		return format.format(new Date());
	}

	/**
	 * 年月日 格式 YYYY年MM月DD日
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMMDDCh(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYY年MM月DD日
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMMDDCh() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	/**
	 * 年月日 格式 YYYY年M月D日
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getYYYYMDCh() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
		return format.format(date);
	}

	/**
	 * 年月日 时分秒 格式 YYYY-MM-DD HH:MM:SS
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getDateYMDHMS() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 年月日 时分秒 格式 YYYY-MM-DD HH:MM
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getDateYMDHM() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(date);
	}
	/**
	 * 时分秒 格式 HH:MM:SS
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getDateHMS() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 英文时间格式 ：dd/MMM/yyyy
	 * 
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static String getEnglishDate(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
		return sdf.format(date).toString();
	}

	/**
	 * yyyy-MM-dd 转 yyyyMMdd
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeyyyyMMdd(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date).toString();
	}

	/**
	 * yyyyMMdd 转 yyyy-MM-dd
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date).toString();
	}

	/**
	 * yyyy-MM-dd 转 yyyyMMdd
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD2(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date).toString();
	}

	/**
	 * yyyyMMdd 转 yyyy年MM月dd日
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD3(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date).toString();
	}

	/**
	 * yyyy/MM/dd 转 yyyyMMdd
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD4(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date).toString();
	}

	/**
	 * yyyyMMdd 转 yyyy/MM/dd
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD5(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(date).toString();
	}

	/**
	 * yyyy-MM-dd 转 yyyy年MM月dd日
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD6(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date).toString();
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 转 yyyyMMdd
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD7(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = format.parse(d);
		return sdf.format(date).toString();
	}

	/**
	 *   yyyyMMdd 转 yyyy-MM-dd HH:mm:ss
	 *
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeYMD8(String d) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(d);
		return format.format(date).toString();
	}

 public static void main(String args[]) throws Exception {
	 String a = getChangeYMD8("20210913");
	 System.out.println(a);
 }
	/**
	 * HHMM
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getDateHHMM() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HHmm");
		return format.format(date);
	}

	/**
	 * YYYY-MM-DD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getDateYMD() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 得到自然日 昨天
	 * 
	 * @return
	 */
	public static String getYester(String format) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date d = cal.getTime();
		SimpleDateFormat sp = new SimpleDateFormat(format);
		return sp.format(d);
	}

	/**
	 * 得到某字符串日的 昨天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getYester(String dateString, String format) throws ParseException {
		SimpleDateFormat sp = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sp.parse(dateString));
		cal.add(Calendar.DATE, -1);
		Date d = cal.getTime();
		return sp.format(d);
	}

	/**
	 * 获取年月日时分秒 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getYMDHMS() {
		// 得到long类型当前时间
		long l = System.currentTimeMillis();
		Date date = new Date(l);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 日期对比:yyyyMMddHHmmss
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getDateContrast(String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d1 = sdf.parse(date1);
		Date d2 = sdf.parse(date2);
		if (d2.getTime() <= d1.getTime()) {
			return "1";
		} else {
			return "2";
		}

	}

	/**
	 * 计算天数
	 * 
	 * @param strBegDate
	 * @param strEndDate
	 * @return 天数
	 */
	public static String getCalculateDays(String strBegDate, String strEndDate) {
		// 天数
		int days = 0;
		try {
			// 时间转换类
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(strBegDate);
			Date date2 = sdf.parse(strEndDate);
			// 将转换的两个时间对象转换成Calendard对象
			Calendar can1 = Calendar.getInstance();
			can1.setTime(date1);
			Calendar can2 = Calendar.getInstance();
			can2.setTime(date2);
			// 拿出两个年份
			int year1 = can1.get(Calendar.YEAR);
			int year2 = can2.get(Calendar.YEAR);

			Calendar can = null;
			// 如果can1 < can2
			// 减去小的时间在这一年已经过了的天数
			// 加上大的时间已过的天数
			if (can1.before(can2)) {
				days -= can1.get(Calendar.DAY_OF_YEAR);
				days += can2.get(Calendar.DAY_OF_YEAR);
				can = can1;
			} else {
				days -= can2.get(Calendar.DAY_OF_YEAR);
				days += can1.get(Calendar.DAY_OF_YEAR);
				can = can2;
			}
			for (int i = 0; i < Math.abs(year2 - year1); i++) {
				// 获取小的时间当前年的总天数
				days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
				// 再计算下一年。
				can.add(Calendar.YEAR, 1);
			}
			// System.out.println("天数差："+days);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(days);
	}

	/**
	 * 日期相减
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getDateSubtract(String strBegDate, String strEndDate) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Long c = null;
		try {
			c = sf.parse(strEndDate).getTime() - sf.parse(strBegDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long d = c / 1000 / 60 / 60 / 24;// 天
		// System.out.println(d+"天");
		return String.valueOf(d);
	}

	/**
	 * （判断规则用到）日期相减
	 * 
	 * @param strBegDate
	 * @return
	 */
	public static String getRuleDate(String strBegDate, String strEndDate) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// System.err.println(strBegDate+"------------"+strEndDate);
		Long c = null;
		try {
			c = sf.parse(strEndDate).getTime() - sf.parse(strBegDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long d = c / 1000 / 60 / 60 / 24;// 天
		// System.out.println(d+"天");
		return String.valueOf(d);
	}

	/**
	 * （展期规则使用）日期相减
	 * 
	 * @param strBegDate
	 * @return
	 */
	public static String getposRuleDate(String strBegDate, String strEndDate) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// System.err.println(strBegDate+"------------"+strEndDate);
		Long c = null;
		try {
			c = sf.parse(strEndDate).getTime() - sf.parse(strBegDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long d = c / 1000 / 60 / 60 / 24;// 天
		// System.out.println(d+"天");
		return String.valueOf(d + 1L);
	}

	/**
	 * 判断 字符串strDate 是否为YYYY-MM-dd 的日期格式
	 * 
	 * @param strDate
	 * @author yangwenbo
	 * @return
	 */
	public static boolean isYMDDate(String strDate) {
		boolean result = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(strDate);
			if (date != null) {
				result = true;
			}
		} catch (ParseException e) {
		}
		return result;
	}

	/**
	 * yyyyMMdd格式字符串转换为日期
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date toDate(String strDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			System.out.println(e);
		}
		return date;
	}	

	/**
	 * 获取当天日期的i天后的日期
	 * 
	 * @param thatDay
	 * @param i
	 * @return
	 * @throws ParseException
	 */
	public static String getDaysAfter(String thatDay, int i) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date utilDate = sdf.parse(thatDay);
		Calendar cl = Calendar.getInstance();
		cl.setTime(utilDate);
		cl.set(Calendar.DATE, cl.get(Calendar.DATE) + i);
		String nextDay = sdf.format(cl.getTime());
		return nextDay;
	}

	/**
	 * 获取指定日期的上周五时间
	 * 
	 * @param thatDay
	 *            当前日期
	 * @return
	 * @throws ParseException
	 */
	public static String getLastFriday(String thatDay) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date thisDay = sdf.parse(thatDay);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDay);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int offset = 7 - dayOfWeek;
		calendar.add(Calendar.DATE, offset - 9);
		String formatLastFriday = sdf.format(calendar.getTime());
		return formatLastFriday;

	}

	/**
	 * 获取指定日期6个自然月后的日期
	 * 
	 * @param day
	 *            指定日期 例：20190122--20190722
	 * @return
	 * @throws ParseException
	 */
	public static String getLastSixMonthDay(String day) throws ParseException {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(day);// 初始日期
		c.setTime(date);// 设置日历时间
		c.add(Calendar.MONTH, 6);// 在日历的月份上增加6个月
		String sixMonthDay = sdf.format(c.getTime());// 得到6个月后的日期
		return sixMonthDay;
	}

	/**
	 * yyyyMMdd M月d日
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static String getChangeMD(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = format.parse(d);
		SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
		return sdf.format(date).toString();
	}

	/**
	 * 上个月的第一天
	 * 
	 * @return
	 */
	public static String getLastMonthFirstDay() {
		Calendar caFirst = Calendar.getInstance();
		caFirst.add(Calendar.MONTH, -1);
		caFirst.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(caFirst.getTime());
	}

	/**
	 * 上个月的最后一天
	 * 
	 * @return
	 */
	public static String getLastMonthLastDay() {
		Calendar caLast = Calendar.getInstance();
		caLast.add(Calendar.MONTH, -1);
		caLast.set(Calendar.DAY_OF_MONTH, caLast.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		return format2.format(caLast.getTime());
	}

	/**
	 * 获得当前时间的String类型（yyyyMMddHHmmss）
	 * 
	 * @return
	 */
	public static String getDateyyyyMMddHHmmss() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return simpleDateFormat.format(date);
	}

	/**
	 * 年月日 格式 YYYYMMDD
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getYYYYMMDDHHMM(String date) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.parse(date);
	}

	/**
	 * 年月日 格式 YYYYMMDDss
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getYYYYMMDDHHMMss(String date) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(date);
	}

	/**
	 * Date类型转换为yyyyMMdd的string类型
	 */
	public static String getDateToString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		return simpleDateFormat.format(date);
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long dateTimeStamp(String date_str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.parse(date_str).getTime() / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取指定日期的前N天(包含指定日期的这一天)
	 */
	public static String getBeforeDateString(String date, String day) throws ParseException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate parse = LocalDate.parse(date, dateTimeFormatter);
		return parse.plusDays(-(Integer.parseInt(day)) + 1).format(dateTimeFormatter);
	}

	/**
	 * 获取一周前日期
	 * 
	 * @return
	 */
	public static String getWeekYMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去七天
		c.setTime(new Date());
		c.add(Calendar.DATE, -7);
		Date d = c.getTime();
		String day = format.format(d);
		System.out.println("过去七天：" + day);
		return day;
	}

	/**
	 * 获取一个月前日期
	 * 
	 * @return
	 */
	public static String getMonthYMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去一月
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		System.out.println("过去一个月：" + mon);
		return mon;
	}

	/**
	 * 获取三个月前日期
	 * 
	 * @return
	 */
	public static String getMonth3YMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去三个月
		c.setTime(new Date());
		c.add(Calendar.MONTH, -3);
		Date m3 = c.getTime();
		String mon3 = format.format(m3);
		System.out.println("过去三个月：" + mon3);
		return mon3;
	}

	/**
	 * 获取六个月前日期
	 * 
	 * @return
	 */
	public static String getMonth6YMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去六个月
		c.setTime(new Date());
		c.add(Calendar.MONTH, -6);
		Date m6 = c.getTime();
		String mon6 = format.format(m6);
		System.out.println("过去六个月：" + mon6);

		return mon6;
	}
	
	/**
	 * 获取当前日期n年后的日期
	 * @return
	 */
	public static String getYearYMDN(String d, int n) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(d);
		Calendar c = Calendar.getInstance();
		// 过去一年
		c.setTime(date);
		c.add(Calendar.YEAR, n);
		Date y = c.getTime();
		String year = format.format(y);
		return year;
	}


	/**
	 * 获取一年前日期
	 * 
	 * @return
	 */
	public static String getYearYMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去一年
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);
		System.out.println("过去一年：" + year);
		return year;
	}

	/**
	 * 获取两年前日期
	 * 
	 * @return
	 */
	public static String getYear2YMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去两年
		c.setTime(new Date());
		c.add(Calendar.YEAR, -2);
		Date y2 = c.getTime();
		String year2 = format.format(y2);
		System.out.println("过去两年：" + year2);
		return year2;
	}

	/**
	 * 获取三年前日期
	 * 
	 * @return
	 */
	public static String getYear3YMD() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 过去三年
		c.setTime(new Date());
		c.add(Calendar.YEAR, -3);
		Date y3 = c.getTime();
		String year3 = format.format(y3);
		System.out.println("过去三年：" + year3);
		return year3;
	}
	public static String getLastDayMonth(String monthTime) {
		//获取年月
        int year = Integer.parseInt(monthTime.substring(0, 4));
        int month = Integer.parseInt(monthTime.substring(4, 6));
        //获取 日历 对象
        Calendar calendar = Calendar.getInstance() ;
        //填充年
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.YEAR , year);
        //填充月 calenda里的月份是0-11

        calendar.set(Calendar.MONTH , month - 1);
        //获取2020-02日历的最大字段，也就是最大多少天

        String maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
       
        return  monthTime + maxDay;
	}
	

}

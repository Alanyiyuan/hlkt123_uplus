package com.hlkt123.uplus.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作的帮助工具类
 * 
 * @author lyy
 * @date 2014-04-22
 * 
 */
public class DateUtil {

	/**
	 * 日期1 变化days天数后得日期字符串， days正负数都行，返回的日期字符串格式为“yyyy-MM-dd HH:mm:ss”
	 * 
	 * @param d1
	 *            Date类型 输入日期
	 * @param days
	 *            int 变化天数
	 * @return
	 */
	public static String addDays(Date d1, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d1);
		rightNow.add(Calendar.DAY_OF_YEAR, days);
		Date d2 = rightNow.getTime();
		String retStr = sdf.format(d2);
		LogUplus.upLog_i("DateUtil",retStr);
		return retStr;
	}

	/**
	 * 返回 增加天数后的  yyyy-MM-dd类型的日期
	 * @param d1
	 * @param days
	 * @return
	 */
	public static String addDays_date(Date d1, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d1);
		rightNow.add(Calendar.DAY_OF_YEAR, days);
		Date d2 = rightNow.getTime();
		String retStr = sdf.format(d2);
		LogUplus.upLog_i("DateUtil",retStr);
		return retStr;
	}
	
	/**
	 * 返回今天的日期格式化后的： X月X日  的字符串
	 * @param d1
	 * @param days
	 * @return
	 */
	public static String addDays_MMdd(Date d1, int days) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d1);
		rightNow.add(Calendar.DAY_OF_YEAR, days);
		Date d2 = rightNow.getTime();
		String retStr = sdf2.format(d2);
		LogUplus.upLog_i("DateUtil",retStr);
		return retStr;
	}

	/**
	 * 判断 date1 是否为小于今天的日期
	 * 
	 * @param d1
	 * @return 小于返回true,否则为false
	 */
	public static boolean lessThenToday(Date d1) {
		String d2Str = addDays(new Date(), -1);
		try {
			Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(d2Str);
			if (d1.getTime() <= d2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 比较两个字符串类型的日期大小
	 * 
	 * @param d1
	 *            yyyy-MM-dd
	 * @param d2
	 * @return 前面的大 返回1，后面的大返回-1，相同返回0
	 */
	@SuppressWarnings("deprecation")
	public static int compare2Date(String d1, String d2) {
		try {
			Date dd1 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
			Date dd2 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);

			if (dd1.getTime() > dd2.getTime()) {
				return 1;
			} else if (dd1.getTime() == dd2.getTime()) {
				return 0;
			} else {
				return -1;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 判断d1 和今天的日期对比
	 * @param d1
	 * @return
	 */
	public static int compare2Today(String d1) {
		try {
			Date dd1 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
			Date dd2 = new Date();
			dd2 = new SimpleDateFormat("yyyy-MM-dd")
					.parse(new SimpleDateFormat("yyyy-MM-dd").format(dd2));

			if (dd1.getTime() > dd2.getTime()) {
				return 1;
			} else if (dd1.getTime() == dd2.getTime()) {
				return 0;
			} else {
				return -1;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 判断当前时间是否为夜间23~清晨8点之间
	 * 
	 * @return
	 */
	public static boolean isNight23_2_Morning8() {
		Calendar c = Calendar.getInstance();
		if (c.get(Calendar.HOUR_OF_DAY) >= 23
				|| c.get(Calendar.HOUR_OF_DAY) < 8) {
			return true;
		}
		return false;
	}

	/**
	 * 比较 两个双精度数据的大小，小于返回-1，=0,大于返回1
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compare2double(double d1, double d2) {
		BigDecimal data1 = new BigDecimal(d1);
		BigDecimal data2 = new BigDecimal(d2);
		return data1.compareTo(data2);
	}
	
	/**
	 * 计算传入时间，与当前时间的时间差；返回天数或者小时分钟数
	 * @param endDate
	 * @return
	 */
	public static String getLeftDayandHHMM(String endDate)
	{
		if(endDate==null||endDate.equals(""))
		{
			return "已到期";
		}
		try {
			Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
			long period=end.getTime()-System.currentTimeMillis();
			if(period<=0)
			{
				return "已到期";
			}
			period=period/1000;
			long day = period / (24 * 60 * 60);
			
			String dayStr = String.valueOf(day);
			if(day>=1)
			{
				return dayStr+"天";
			}
			else
			{
				long hours = (period % (24 * 60 * 60)) / (60 * 60);
				long minutes = ((period % (24 * 60 * 60)) % (60 * 60)) / 60;
				return hours+"小时"+minutes+"分钟";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "已到期";
	}

}

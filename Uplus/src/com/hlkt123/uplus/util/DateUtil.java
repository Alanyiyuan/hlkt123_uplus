package com.hlkt123.uplus.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ���ڲ����İ���������
 * 
 * @author lyy
 * @date 2014-04-22
 * 
 */
public class DateUtil {

	/**
	 * ����1 �仯days������������ַ����� days���������У����ص������ַ�����ʽΪ��yyyy-MM-dd HH:mm:ss��
	 * 
	 * @param d1
	 *            Date���� ��������
	 * @param days
	 *            int �仯����
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
	 * ���� �����������  yyyy-MM-dd���͵�����
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
	 * ���ؽ�������ڸ�ʽ����ģ� X��X��  ���ַ���
	 * @param d1
	 * @param days
	 * @return
	 */
	public static String addDays_MMdd(Date d1, int days) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("M��d��");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(d1);
		rightNow.add(Calendar.DAY_OF_YEAR, days);
		Date d2 = rightNow.getTime();
		String retStr = sdf2.format(d2);
		LogUplus.upLog_i("DateUtil",retStr);
		return retStr;
	}

	/**
	 * �ж� date1 �Ƿ�ΪС�ڽ��������
	 * 
	 * @param d1
	 * @return С�ڷ���true,����Ϊfalse
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
	 * �Ƚ������ַ������͵����ڴ�С
	 * 
	 * @param d1
	 *            yyyy-MM-dd
	 * @param d2
	 * @return ǰ��Ĵ� ����1������Ĵ󷵻�-1����ͬ����0
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
	 * �ж�d1 �ͽ�������ڶԱ�
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
	 * �жϵ�ǰʱ���Ƿ�Ϊҹ��23~�峿8��֮��
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
	 * �Ƚ� ����˫�������ݵĴ�С��С�ڷ���-1��=0,���ڷ���1
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
	 * ���㴫��ʱ�䣬�뵱ǰʱ���ʱ��������������Сʱ������
	 * @param endDate
	 * @return
	 */
	public static String getLeftDayandHHMM(String endDate)
	{
		if(endDate==null||endDate.equals(""))
		{
			return "�ѵ���";
		}
		try {
			Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);
			long period=end.getTime()-System.currentTimeMillis();
			if(period<=0)
			{
				return "�ѵ���";
			}
			period=period/1000;
			long day = period / (24 * 60 * 60);
			
			String dayStr = String.valueOf(day);
			if(day>=1)
			{
				return dayStr+"��";
			}
			else
			{
				long hours = (period % (24 * 60 * 60)) / (60 * 60);
				long minutes = ((period % (24 * 60 * 60)) % (60 * 60)) / 60;
				return hours+"Сʱ"+minutes+"����";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "�ѵ���";
	}

}

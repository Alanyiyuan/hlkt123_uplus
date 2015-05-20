package com.hlkt123.uplus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ��֤���򹤾���
 * 
 * @ClassName: Validation
 * @Description: TODO(������)
 * @author ��׿
 * @date 2014-5-16 ����3:43:48
 * 
 */
public class Validation {

	/**
	 * 18λ����15λ���֤��֤ 18λ�����һλ��������ĸx
	 * 
	 * @param text
	 * @return
	 */
	public static boolean personIdValidation(String text) {
		String regx = "[0-9]{17}[Xx]";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		boolean flag = text.matches(regx) || text.matches(reg1)
				|| text.matches(regex);
		return flag;
	}

	/**
	 * ��֤�ֻ���ʽ
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * �ƶ���134��135��136��137��138��139��150��151��157(TD)��158��159��187��188
		 * ��ͨ��130��131��132��152��155��156��185��186 ���ţ�133��153��180��189����1349��ͨ��
		 * �ܽ��������ǵ�һλ�ض�Ϊ1���ڶ�λ�ض�Ϊ3��5��8������λ�õĿ���Ϊ0-9 ������170��176,177�Ŷε���֤
		 */
		String telRegex = "[1][34587]\\d{9}";// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
		String tai = "[0][0][8][8][6]\\d{10}";
		String gangao = "[0][0][8][5][23]\\d{8}";
		if (mobiles == null || "".equals(mobiles))
			return false;
		else
			return mobiles.matches(telRegex) || mobiles.matches(tai)
					|| mobiles.matches(gangao);
	}

	
	/**
	 * ��֤�������볤�� (6-18λ)
	 * 
	 * @param ����֤���ַ���
	 * @return ����Ƿ��ϸ�ʽ���ַ���,���� <b>true </b>,����Ϊ <b>false </b>
	 */
	public static boolean IsPassword(String str) {
		String regex = "^[A-Za-z_0-9]\\w{5,17}$";
		return str.matches(regex);
	}

	/**
	 * ��֤�̻���ʽ
	 */
	public static boolean isPhoneNO(String mobiles) {
		String telRegex = "^([0][0-9]{2}-?[0-9]{8})|([0][0-9]{3}-?[0-9]{7})|([0][0-9]{3}-?[0-9]{8})$";
		if (mobiles == null || "".equals(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// �ж�email��ʽ�Ƿ���ȷ
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * ��֤������ַ����Ƿ�Ϊ��ͨ�����ĳ��ƺ� �� ��A00188
	 * 
	 * @param carnum
	 * @return
	 */
	public static boolean isCarNumber(String carnum) {
		if (carnum.contains("��")) {
			carnum = carnum.replace("��", "");
		}
		String format = "^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$";
		Pattern p = Pattern.compile(format);
		Matcher m = p.matcher(carnum);
		return m.matches();
	}

	/**
	 * ��֤�û��������Ƿ�������µĹؼ���
	 * sorry,reiteration,relogin,exception,exceed5,invalid,loginonly,keyword
	 * 
	 * @param value
	 *            ����֤���ַ���
	 * @return true ��false��
	 */
	public static boolean hasKeywords(String value) {
		if (value == null || value.length() == 0) {
			return false;
		}
		String[] keywords = { "sorry", "reiteration", "relogin", "exception",
				"exceed5", "invalid", "loginonly", "keyword" };
		value = value.toLowerCase();
		for (int i = 0; i < keywords.length; i++) {
			if (value.contains(keywords[i])) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

}

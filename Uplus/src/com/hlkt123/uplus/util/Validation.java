package com.hlkt123.uplus.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证正则工具类
 * 
 * @ClassName: Validation
 * @Description: TODO(类描述)
 * @author 赵卓
 * @date 2014-5-16 下午3:43:48
 * 
 */
public class Validation {

	/**
	 * 18位或者15位身份证验证 18位的最后一位可以是字母x
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
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 新增：170，176,177号段的验证
		 */
		String telRegex = "[1][34587]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String tai = "[0][0][8][8][6]\\d{10}";
		String gangao = "[0][0][8][5][23]\\d{8}";
		if (mobiles == null || "".equals(mobiles))
			return false;
		else
			return mobiles.matches(telRegex) || mobiles.matches(tai)
					|| mobiles.matches(gangao);
	}

	
	/**
	 * 验证输入密码长度 (6-18位)
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsPassword(String str) {
		String regex = "^[A-Za-z_0-9]\\w{5,17}$";
		return str.matches(regex);
	}

	/**
	 * 验证固话格式
	 */
	public static boolean isPhoneNO(String mobiles) {
		String telRegex = "^([0][0-9]{2}-?[0-9]{8})|([0][0-9]{3}-?[0-9]{7})|([0][0-9]{3}-?[0-9]{8})$";
		if (mobiles == null || "".equals(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	// 判断email格式是否正确
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * 验证输入的字符串是否为普通车辆的车牌号 如 浙A00188
	 * 
	 * @param carnum
	 * @return
	 */
	public static boolean isCarNumber(String carnum) {
		if (carnum.contains("挂")) {
			carnum = carnum.replace("挂", "");
		}
		String format = "^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$";
		Pattern p = Pattern.compile(format);
		Matcher m = p.matcher(carnum);
		return m.matches();
	}

	/**
	 * 验证用户的输入是否包含如下的关键字
	 * sorry,reiteration,relogin,exception,exceed5,invalid,loginonly,keyword
	 * 
	 * @param value
	 *            待验证的字符串
	 * @return true 有false无
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

package com.hlkt123.uplus.util;


public class CityUtil {

	/**
	 * 从省市组合信息中提取出省,市的信息
	 * 
	 * @param inStr
	 * @return
	 */
	public static String[] getProvinceName(String inStr) {
		String[] ret = { "北京市", "" };
		if (inStr == null || inStr.equals("")) {
			return ret;
		}

		if (inStr.contains("省")) {
			ret[0] = inStr.substring(0, inStr.indexOf("省") + 1);
			ret[1] = inStr.substring(inStr.indexOf("省") + 1);
			return ret;
		} else if (inStr.contains("内蒙古")) {
			ret[0] = "内蒙古";
			ret[1] = inStr.replace("内蒙古", "");
		} else if (inStr.contains("西藏自治区")) {
			ret[0] = "西藏";
			ret[1] = inStr.replace("西藏自治区", "");
		} else if (inStr.contains("新疆自治区")) {
			ret[0] = "新疆";
			ret[1] = inStr.replace("新疆自治区", "");
		}

		if (inStr.contains("北京市") || inStr.contains("重庆市")
				|| inStr.contains("天津市") || inStr.contains("上海市")
				|| inStr.contains("香港") || inStr.contains("澳门")) {
			if (inStr.contains("市市")) {
				inStr = inStr.replace("市市", "市");
			}
			ret[0] = inStr.replace("市辖区", "").replace("县", "");
			ret[1] = "";
		}
		return ret;
	}



	/**
	 * 判断某个省是否为直辖市
	 * 
	 * @param cityName
	 * @return
	 */
	public static boolean isDerectCity(String cityName) {
		if (cityName != null && !cityName.equals("")) {
			if (cityName.equals("北京市") || cityName.equals("上海市")
					|| cityName.equals("天津市") || cityName.equals("重庆市")
					|| cityName.equals("010") || cityName.equals("021")
					|| cityName.equals("022") || cityName.equals("023")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对直辖市进行字符截取，值返回城市名字，不带区县名字
	 * 
	 * @param names
	 * @return
	 */
	public static String getDerectCityWithChild(String names) {
		if (names == null || names.equals("")) {
			return names;
		} else if (names.startsWith("北京市") || names.startsWith("天津市")
				|| names.startsWith("上海市") || names.startsWith("重庆市")) {
			return names.substring(0, 3);
		}

		return names;
	}

	/**
	 * 将区县字符串 进行空字符串的过滤处理，“不限，市辖区，县”转为空，其他的字符串不变
	 * 
	 * @param str
	 * @return
	 */
	public static String cityNameTrim(String str) {
		if (str == null || str.equals("不限") || str.equals("市辖区")
				|| str.equals("县") || str.equals("全市") || str.equals("全省")) {
			return "";
		}
		return str;
	}

	

}

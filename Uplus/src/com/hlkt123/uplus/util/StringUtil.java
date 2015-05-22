package com.hlkt123.uplus.util;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun  String 操作的工具类
 */
public class StringUtil {

	/**
	 * 判断一个字符串是否为 null或 空字符串
	 * @param string
	 * @return
	 */
	public static boolean isNull(String string)
	{
		if(string==null||string.equals(""))
		{
			return true;
		}
		return false;
	}

}

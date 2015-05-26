package com.hlkt123.uplus.util;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun  String �����Ĺ�����
 */
public class StringUtil {

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ null�� ���ַ���
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

	/**
	 * �ַ���ת����ֵ
	 * @param b
	 * @return ת���쳣����false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}

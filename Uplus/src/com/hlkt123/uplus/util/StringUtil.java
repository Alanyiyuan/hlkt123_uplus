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

}

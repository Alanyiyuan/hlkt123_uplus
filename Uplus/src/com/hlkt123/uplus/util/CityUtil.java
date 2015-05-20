package com.hlkt123.uplus.util;


public class CityUtil {

	/**
	 * ��ʡ�������Ϣ����ȡ��ʡ,�е���Ϣ
	 * 
	 * @param inStr
	 * @return
	 */
	public static String[] getProvinceName(String inStr) {
		String[] ret = { "������", "" };
		if (inStr == null || inStr.equals("")) {
			return ret;
		}

		if (inStr.contains("ʡ")) {
			ret[0] = inStr.substring(0, inStr.indexOf("ʡ") + 1);
			ret[1] = inStr.substring(inStr.indexOf("ʡ") + 1);
			return ret;
		} else if (inStr.contains("���ɹ�")) {
			ret[0] = "���ɹ�";
			ret[1] = inStr.replace("���ɹ�", "");
		} else if (inStr.contains("����������")) {
			ret[0] = "����";
			ret[1] = inStr.replace("����������", "");
		} else if (inStr.contains("�½�������")) {
			ret[0] = "�½�";
			ret[1] = inStr.replace("�½�������", "");
		}

		if (inStr.contains("������") || inStr.contains("������")
				|| inStr.contains("�����") || inStr.contains("�Ϻ���")
				|| inStr.contains("���") || inStr.contains("����")) {
			if (inStr.contains("����")) {
				inStr = inStr.replace("����", "��");
			}
			ret[0] = inStr.replace("��Ͻ��", "").replace("��", "");
			ret[1] = "";
		}
		return ret;
	}



	/**
	 * �ж�ĳ��ʡ�Ƿ�ΪֱϽ��
	 * 
	 * @param cityName
	 * @return
	 */
	public static boolean isDerectCity(String cityName) {
		if (cityName != null && !cityName.equals("")) {
			if (cityName.equals("������") || cityName.equals("�Ϻ���")
					|| cityName.equals("�����") || cityName.equals("������")
					|| cityName.equals("010") || cityName.equals("021")
					|| cityName.equals("022") || cityName.equals("023")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ֱϽ�н����ַ���ȡ��ֵ���س������֣�������������
	 * 
	 * @param names
	 * @return
	 */
	public static String getDerectCityWithChild(String names) {
		if (names == null || names.equals("")) {
			return names;
		} else if (names.startsWith("������") || names.startsWith("�����")
				|| names.startsWith("�Ϻ���") || names.startsWith("������")) {
			return names.substring(0, 3);
		}

		return names;
	}

	/**
	 * �������ַ��� ���п��ַ����Ĺ��˴��������ޣ���Ͻ�����ء�תΪ�գ��������ַ�������
	 * 
	 * @param str
	 * @return
	 */
	public static String cityNameTrim(String str) {
		if (str == null || str.equals("����") || str.equals("��Ͻ��")
				|| str.equals("��") || str.equals("ȫ��") || str.equals("ȫʡ")) {
			return "";
		}
		return str;
	}

	

}

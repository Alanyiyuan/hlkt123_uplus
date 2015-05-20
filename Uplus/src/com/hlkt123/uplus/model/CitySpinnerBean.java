package com.hlkt123.uplus.model;

import java.io.Serializable;


public class CitySpinnerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371721952403644279L;
	public String code;
	public String name;
	
	/**
	 * ʡ����������������ģ��
	 * @author Lyy
	 *
	 */
	public CitySpinnerBean(String _code,String _name){
		code=_code;
		name=_name;
	}
	
	public String getCode(){
		return code;
	}
	
	public String toString(){
		return name.trim().toString();
	}
	
	/**
	 * �������ַ��� ���п��ַ����Ĺ��˴��������ޣ���Ͻ�����ء�תΪ�գ��������ַ�������
	 * @param str
	 * @return
	 */
	public static String toEmptyString(String str){
		if(str.equals("����")||str.equals("��Ͻ��")||str.equals("��")||str.equals("ȫ��")||str.equals("ȫʡ")){
			return "";
		}
		return str;
	}
}

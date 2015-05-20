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
	 * 省市县三级数据联动模型
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
	 * 将区县字符串 进行空字符串的过滤处理，“不限，市辖区，县”转为空，其他的字符串不变
	 * @param str
	 * @return
	 */
	public static String toEmptyString(String str){
		if(str.equals("不限")||str.equals("市辖区")||str.equals("县")||str.equals("全市")||str.equals("全省")){
			return "";
		}
		return str;
	}
}

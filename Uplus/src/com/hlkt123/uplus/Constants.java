package com.hlkt123.uplus;

import android.os.Environment;

/**
 * ϵͳ�����ļ�
 * @author Dkalan
 * @Date   2015-05-19
 * 
 */
public class Constants {
	
	
	
	/**
	 * ģʽ������־��test:true��release:false�������־
	 */
	public static final boolean IS_DEBUG = true;
	
	//������־�ļ�����·��
	public static String LOG_PATH=Environment
			.getExternalStorageDirectory() + "/uplus/errorlog/";
	
	/**
	 * app����������ܴ�����־�������ַ
	 */
	public final static String EXCEPTION_EXIT_EMAILTO_ADDR = "liuyiyuan@hlkt123.com ";

}

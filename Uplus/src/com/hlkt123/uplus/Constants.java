package com.hlkt123.uplus;

import android.os.Environment;

/**
 * 系统常量文件
 * @author Dkalan
 * @Date   2015-05-19
 * 
 */
public class Constants {
	
	
	
	/**
	 * 模式输入日志，test:true，release:false不输出日志
	 */
	public static final boolean IS_DEBUG = true;
	
	//错误日志文件保存路径
	public static String LOG_PATH=Environment
			.getExternalStorageDirectory() + "/uplus/errorlog/";
	
	/**
	 * app意外崩溃接受错误日志的邮箱地址
	 */
	public final static String EXCEPTION_EXIT_EMAILTO_ADDR = "liuyiyuan@hlkt123.com ";

}

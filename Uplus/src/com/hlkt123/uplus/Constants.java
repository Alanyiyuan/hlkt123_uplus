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
	
	/**
	 * <p>
	 * SUCC: 接口1结果请求成功；
	 * <p>
	 * SUCC_INTERFACE_2 :接口2 结果请求成功；
	 * <p>
	 * SUCC_INTERFACE_3 :接口3结果请求成功；
	 * <p>
	 * GET_P1_SUCC :listview 翻页接口获取第一页数据成功；
	 * <p>
	 * GET_PN_SUCC: listView 翻页接口获取第N页数据成功，N>1;
	 * <p>
	 * RELOGIN:登录过期，需要重新登录；
	 * <p>
	 * NO_REC: 返回记录为0，针对列表分页；
	 * <p>
	 * EXCEPTION :服务器端异常；
	 * <p>
	 * NET_ERR: 网络错误；
	 * <p>
	 * OTHERS : 其他未知类型的消息。
	 * </P>
	 */
	public static final int MSG_WHAT_SUCC = 4000, 
			MSG_WHAT_SUCC_INTERFACE_2 = 4001,
			MSG_WHAT_SUCC_INTERFACE_3 = 4002,
			MSG_WHAT_GET_P1_SUCC =4003,
			MSG_WHAT_GET_PN_SUCC =4004,
			MSG_WHAT_RELOGIN = 4005, 
			MSG_WHAT_NO_REC= 4006,
			MSG_WHAT_EXCEPTION = 4007,
			MSG_WHAT_NET_ERR= 4008,
			MSG_WHAT_OTHERS = 4099;

}

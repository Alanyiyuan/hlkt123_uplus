package com.hlkt123.uplus.util;

import java.util.List;

import org.apache.http.NameValuePair;

import android.util.Log;

import com.hlkt123.uplus.Constants;

public class LogUtil {

	/**
	 * 输出接口请求或者返回参数标准日志
	 * 
	 * @param tag
	 *            log标志
	 * @param action
	 *            接口名
	 * @param params
	 *            输入参数或者返回结果
	 */
	public static void upWebLog(String tag, String action, Object params) {
		if (!Constants.IS_DEBUG) {
			return;
		}
		if (action != null) {
			action = action.replace(" ", "");
		}

		if (params instanceof String) {
			Log.i(tag + "_wzWebLog", "接口名：" + action + ";\n" + "返回结果:" + params);
		} else if (params instanceof List) // 如数参数的类型是 ArrayList类型的
		{
			List<NameValuePair> list = (List<NameValuePair>) params;
			String inStr = "";
			if (list != null && list.size() > 0) {
				for (NameValuePair nvp : list) {
					inStr += nvp.getName() + "=" + nvp.getValue() + "\n";
				}
			}
			Log.i(tag + "_wzWebLog", "接口名：" + action + ";\n" + "输入参数:\n"
					+ inStr);
		}
	}

	/**
	 * 自定义log info格式输出，如果不输出日志，则直接返回
	 * 
	 * @param tag
	 * @param content
	 */
	public static void upLog_i(String tag, String content) {
		if (!Constants.IS_DEBUG) {
			return;
		}
		Log.i(tag, content);
	}

	/**
	 * 自定义log debug格式输出，如果不输出日志，则直接返回
	 * 
	 * @param tag
	 * @param content
	 */
	public static void upLog_d(String tag, String content) {
		if (!Constants.IS_DEBUG) {
			return;
		}
		Log.d(tag, content);
	}

	/**
	 * 自定义log error格式输出，如果不输出日志，则直接返回
	 * 
	 * @param tag
	 * @param content
	 */
	public static void upLog_e(String tag, String content) {
		if (!Constants.IS_DEBUG) {
			return;
		}
		Log.e(tag, content);
	}

	/**
	 * 自定义log verbose格式输出，如果不输出日志，则直接返回
	 * 
	 * @param tag
	 * @param content
	 */
	public static void upLog_v(String tag, String content) {
		if (!Constants.IS_DEBUG) {
			return;
		}
		Log.v(tag, content);
	}

}

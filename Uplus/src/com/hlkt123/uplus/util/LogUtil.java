package com.hlkt123.uplus.util;

import java.util.List;

import org.apache.http.NameValuePair;

import android.util.Log;

import com.hlkt123.uplus.Constants;

public class LogUtil {

	/**
	 * ����ӿ�������߷��ز�����׼��־
	 * 
	 * @param tag
	 *            log��־
	 * @param action
	 *            �ӿ���
	 * @param params
	 *            ����������߷��ؽ��
	 */
	public static void upWebLog(String tag, String action, Object params) {
		if (!Constants.IS_DEBUG) {
			return;
		}
		if (action != null) {
			action = action.replace(" ", "");
		}

		if (params instanceof String) {
			Log.i(tag + "_wzWebLog", "�ӿ�����" + action + ";\n" + "���ؽ��:" + params);
		} else if (params instanceof List) // ���������������� ArrayList���͵�
		{
			List<NameValuePair> list = (List<NameValuePair>) params;
			String inStr = "";
			if (list != null && list.size() > 0) {
				for (NameValuePair nvp : list) {
					inStr += nvp.getName() + "=" + nvp.getValue() + "\n";
				}
			}
			Log.i(tag + "_wzWebLog", "�ӿ�����" + action + ";\n" + "�������:\n"
					+ inStr);
		}
	}

	/**
	 * �Զ���log info��ʽ���������������־����ֱ�ӷ���
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
	 * �Զ���log debug��ʽ���������������־����ֱ�ӷ���
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
	 * �Զ���log error��ʽ���������������־����ֱ�ӷ���
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
	 * �Զ���log verbose��ʽ���������������־����ֱ�ӷ���
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

package com.hlkt123.uplus.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	/**
	 * 长提醒对话框
	 * @Author lyy
	 * @Date 2014-03-25
	 * @param mCnt
	 *            Context 上下文环境
	 * @param info
	 *            String 提示文字
	 */
	public static void disToastLong(Context mCnt, String info) {

		if (mCnt != null) {
			Toast.makeText(mCnt, info, Toast.LENGTH_LONG).show();
		}

	}
	
	/**
	 * 短暂的提醒消息
	 * @Author lyy
	 * @Date 2014-03-25
	 * @param mCnt
	 *            Context 上下文环境
	 * @param info
	 *            String 提示文字
	 */
	public static void disToastShort(Context mCnt, String info) {

		if (mCnt != null) {
			Toast.makeText(mCnt, info, Toast.LENGTH_SHORT).show();
		}

	}
}

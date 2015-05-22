package com.hlkt123.uplus.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	/**
	 * �����ѶԻ���
	 * @Author lyy
	 * @Date 2014-03-25
	 * @param mCnt
	 *            Context �����Ļ���
	 * @param info
	 *            String ��ʾ����
	 */
	public static void disToastLong(Context mCnt, String info) {

		if (mCnt != null) {
			Toast.makeText(mCnt, info, Toast.LENGTH_LONG).show();
		}

	}
	
	/**
	 * ���ݵ�������Ϣ
	 * @Author lyy
	 * @Date 2014-03-25
	 * @param mCnt
	 *            Context �����Ļ���
	 * @param info
	 *            String ��ʾ����
	 */
	public static void disToastShort(Context mCnt, String info) {

		if (mCnt != null) {
			Toast.makeText(mCnt, info, Toast.LENGTH_SHORT).show();
		}

	}
}

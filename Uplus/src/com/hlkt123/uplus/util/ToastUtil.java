package com.hlkt123.uplus.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	/**
	 * �Զ�����ʾ�Ի���
	 * @Author lyy 
	 * @Date   2014-03-25
	 * @param mCnt   Context �����Ļ���
	 * @param info   String ��ʾ����
	 * @param digType 
	 *         int 1 ������ʾ��2����ʾ�Ի���
	 */
	public static void showToast_S1L2(Context mCnt,String info,int digType)
	{
		if(digType==1){
			try {
				if(mCnt!=null){
					Toast.makeText(mCnt, info,Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		else{
			Toast.makeText(mCnt, info,Toast.LENGTH_LONG).show();
		}
	}
}

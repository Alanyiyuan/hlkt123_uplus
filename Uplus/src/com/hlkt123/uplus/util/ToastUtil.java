package com.hlkt123.uplus.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	/**
	 * 自定义提示对话框，
	 * @Author lyy 
	 * @Date   2014-03-25
	 * @param mCnt   Context 上下文环境
	 * @param info   String 提示文字
	 * @param digType 
	 *         int 1 短暂提示框，2长显示对话框
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

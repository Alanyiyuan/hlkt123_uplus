package com.hlkt123.uplus;

import android.app.Application;

import com.hlkt123.uplus.util.CrashHandler;
import com.hlkt123.uplus.util.LogUtil;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun APPȫ�ֱ����洢�࣬��Ҫ��ҳ����ڵı������Դ洢�ڸ�����,�����õı�����Ҫ���ڸ��ļ��ڣ�����̫��ᵼ���ڴ濪������
 */
public class GlobalApplication extends Application {

	static GlobalApplication mGapp;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LogUtil.upLog_i("Gapp", "create ..");
		// ��ӱ�����������
		CrashHandler catchHandler = CrashHandler.getInstance();
		catchHandler.init(getApplicationContext());

		mGapp = this;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}

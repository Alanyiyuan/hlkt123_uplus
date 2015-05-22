package com.hlkt123.uplus;

import android.app.Application;

import com.hlkt123.uplus.util.CrashHandler;
import com.hlkt123.uplus.util.LogUtil;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun APP全局变量存储类，需要跨页面存在的变量可以存储在该类中,不常用的变量不要存在该文件内，变量太多会导致内存开销过大
 */
public class GlobalApplication extends Application {

	static GlobalApplication mGapp;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LogUtil.upLog_i("Gapp", "create ..");
		// 添加崩溃错误拦截
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

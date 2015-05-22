package com.hlkt123.uplus;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.hlkt123.uplus.util.LogUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun APP有所Activity的公共父类，APP类所有的Activiity均需要继承本类
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		LogUtil.upLog_i("BaseActivity", "活动类名：" + getClass().getSimpleName());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// askNetwork();
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// askNetwork();
		MobclickAgent.onPause(this);
	}

	/**
	 * 防止后期有一些统一的方法需要添加，比如登录检查等操作
	 * 
	 * @return
	 */
	public boolean isLogined() {
		return false;
	}

}

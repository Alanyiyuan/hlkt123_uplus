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
 * @fun APP����Activity�Ĺ������࣬APP�����е�Activiity����Ҫ�̳б���
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

		LogUtil.upLog_i("BaseActivity", "�������" + getClass().getSimpleName());
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
	 * ��ֹ������һЩͳһ�ķ�����Ҫ��ӣ������¼���Ȳ���
	 * 
	 * @return
	 */
	public boolean isLogined() {
		return false;
	}

}

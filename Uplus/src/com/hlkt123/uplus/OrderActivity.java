package com.hlkt123.uplus;

import android.os.Bundle;
import android.view.Window;

public class OrderActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_activity);
		//add by lyy 05-27
	}
}

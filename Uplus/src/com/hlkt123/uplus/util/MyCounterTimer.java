package com.hlkt123.uplus.util;

import com.hlkt123.uplus.R;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCounterTimer extends CountDownTimer{

	public static final int TIME_COUNT = 60000; //这里要多加1秒，防止从59秒开始显示
	private TextView btn;
	private int endStrRid;
	private int normalColor, timingColor;
	/*
	 * millisInFuture:倒计时总时间
	 * countDownInterval:渐变时间
	 */
	public MyCounterTimer(long millisInFuture, long countDownInterval, TextView btn, int endStrRid) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		
		this.btn = btn;
		this.endStrRid = endStrRid;
	}

	public MyCounterTimer(TextView btn, int endStrRid) {
		super(TIME_COUNT, 1000);
		this.btn = btn;
		this.endStrRid = endStrRid;
	}
	
	public MyCounterTimer(TextView btn) {
		super(TIME_COUNT, 1000);
		this.btn = btn;
		this.endStrRid = R.string.txt_getMsgCode_validate;
	}
	
	public MyCounterTimer(TextView tv_varify, int normalColor, int timingColor){
		this(tv_varify);
		this.normalColor = normalColor;
		this.timingColor = timingColor;
	}
	
	
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		if(normalColor > 0){
			btn.setTextColor(normalColor);
		}
		btn.setText(endStrRid);
		btn.setEnabled(true);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		if(timingColor > 0){
			btn.setTextColor(timingColor);
		}
		btn.setEnabled(false);
		btn.setText(millisUntilFinished / 1000 + "s");
	}

}

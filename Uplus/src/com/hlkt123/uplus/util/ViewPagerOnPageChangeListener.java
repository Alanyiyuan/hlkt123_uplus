package com.hlkt123.uplus.util;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerOnPageChangeListener implements OnPageChangeListener {

	private TextView tabTV0, tabTV1;
	private ImageView iconIV0, iconIV1;
	private Context mCnt;

	/**
	 * ViewPager tab子页滑动事件监听器，适用于只有两个tab页的 ViewPgaer,顶部的文字也会跟着动态切换
	 * 
	 * @param _mCnt
	 *            上下文环境
	 * @param _text0
	 *            tab0 textView
	 * @param _text1
	 *            tab1 textView
	 * @param _icon0
	 *            tab0 ImageView
	 * @param _icon1
	 *            tab1 ImageView
	 */
	public ViewPagerOnPageChangeListener(Context _mCnt, TextView _text0,
			TextView _text1, ImageView _icon0, ImageView _icon1) {
		tabTV0 = _text0;
		tabTV1 = _text1;
		iconIV0 = _icon0;
		iconIV1 = _icon1;
		mCnt = _mCnt;
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int arg0) {

		if (arg0 == 0) {
//			tabTV0.setTextColor(mCnt.getResources().getColor(
//					R.color.main_orange));
//			tabTV1.setTextColor(mCnt.getResources().getColor(R.color.gray));
//			iconIV0.setVisibility(View.VISIBLE);
//			iconIV1.setVisibility(View.INVISIBLE);
		} else {
			// tabTV0.setTextColor(mCnt.getResources().getColor(R.color.gray));
			// tabTV1.setTextColor(mCnt.getResources().getColor(
			// R.color.main_orange));
			// iconIV0.setVisibility(View.INVISIBLE);
			// iconIV1.setVisibility(View.VISIBLE);
		}
	}
}

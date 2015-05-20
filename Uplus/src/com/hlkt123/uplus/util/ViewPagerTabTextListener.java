package com.hlkt123.uplus.util;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * ViewPager tab TextView 的点击事件公共类
 * @author lyy
 *
 */
public class ViewPagerTabTextListener implements OnClickListener {
	private int index = 0;
	private ViewPager vpPager;

	/**
	 * 
	 * @param i  tab对应的页码序号
	 * @param _vp ViewPager对象
	 */
	public ViewPagerTabTextListener(int i, ViewPager _vp) {
		index = i;
		vpPager = _vp;
	}

	public void onClick(View v) {
		vpPager.setCurrentItem(index);
	}

}

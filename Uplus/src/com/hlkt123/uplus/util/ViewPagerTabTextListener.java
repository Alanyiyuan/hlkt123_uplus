package com.hlkt123.uplus.util;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * ViewPager tab TextView �ĵ���¼�������
 * @author lyy
 *
 */
public class ViewPagerTabTextListener implements OnClickListener {
	private int index = 0;
	private ViewPager vpPager;

	/**
	 * 
	 * @param i  tab��Ӧ��ҳ�����
	 * @param _vp ViewPager����
	 */
	public ViewPagerTabTextListener(int i, ViewPager _vp) {
		index = i;
		vpPager = _vp;
	}

	public void onClick(View v) {
		vpPager.setCurrentItem(index);
	}

}

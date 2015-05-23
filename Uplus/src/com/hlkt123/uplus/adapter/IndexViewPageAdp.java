package com.hlkt123.uplus.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

/**
 * Ê×Ò³viewpager ÊÊÅäÆ÷
 * @author liuyiyuan
 * @date 2015-5-23
 * @fun  TODO
 */
public class IndexViewPageAdp extends PagerAdapter {
	private List<View> mListViews;
	private OnPageChangeListener pageChgLis;
	
	public IndexViewPageAdp(List<View> mListViews) {
		this.mListViews = mListViews;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mListViews.get(position), 0);
		return mListViews.get(position);
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
}

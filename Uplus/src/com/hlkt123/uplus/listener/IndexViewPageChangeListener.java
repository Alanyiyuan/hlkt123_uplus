package com.hlkt123.uplus.listener;

import com.hlkt123.uplus.R;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首页ViewPage 页面tab切换事件
 * @author liuyiyuan
 * @date 2015-5-23
 * @fun  TODO
 */
public class IndexViewPageChangeListener implements OnPageChangeListener {

	private LinearLayout menu0, menu1, menu2;
	private TextView text0, text1, text2;
	private ImageView icon0, icon1, icon2;
	
	/**
	 * 上下文环境
	 */
	private Context mCnt;
	
	public IndexViewPageChangeListener(Context _mCnt, LinearLayout _menu0,
			LinearLayout _menu1, LinearLayout _menu2) {
		menu0 = _menu0;
		menu1 = _menu1;
		menu2 = _menu2;
		text0 = (TextView) menu0.findViewById(R.id.text0);
		text1 = (TextView) menu1.findViewById(R.id.text1);
		text2 = (TextView) menu2.findViewById(R.id.text2);

		icon0 = (ImageView) menu0.findViewById(R.id.icon0);
		icon1 = (ImageView) menu1.findViewById(R.id.icon1);
		icon2 = (ImageView) menu2.findViewById(R.id.icon2);
		mCnt=_mCnt;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int newTabId) {
		// TODO Auto-generated method stub
		if (newTabId == 0) {
			text0.setTextColor(mCnt.getResources().getColor(R.color.orange));
			text1.setTextColor(mCnt.getResources().getColor(R.color.white));
			text2.setTextColor(mCnt.getResources().getColor(R.color.white));
			icon0.setImageResource(R.drawable.ic_index_tab0_pressed);
			icon1.setImageResource(R.drawable.ic_index_tab0_normal);
			icon2.setImageResource(R.drawable.ic_index_tab0_normal);
			
		} else if (newTabId == 1)
		{
			text0.setTextColor(mCnt.getResources().getColor(R.color.white));
			text1.setTextColor(mCnt.getResources().getColor(R.color.orange));
			text2.setTextColor(mCnt.getResources().getColor(R.color.white));
			
			icon0.setImageResource(R.drawable.ic_index_tab0_normal);
			icon1.setImageResource(R.drawable.ic_index_tab0_pressed);
			icon2.setImageResource(R.drawable.ic_index_tab0_normal);
		} else {
			text0.setTextColor(mCnt.getResources().getColor(R.color.white));
			text1.setTextColor(mCnt.getResources().getColor(R.color.white));
			text2.setTextColor(mCnt.getResources().getColor(R.color.orange));
			icon0.setImageResource(R.drawable.ic_index_tab0_normal);
			icon1.setImageResource(R.drawable.ic_index_tab0_normal);
			icon2.setImageResource(R.drawable.ic_index_tab0_pressed);
		}

	}

}

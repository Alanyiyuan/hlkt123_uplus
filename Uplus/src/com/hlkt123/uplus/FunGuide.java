package com.hlkt123.uplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hlkt123.uplus.adapter.FunGudeAdapter;
import com.hlkt123.uplus.view.FunGudeGallery;

/**
 * p>功能引导页面</p>
 * 
 * @author Administrator
 * 
 */
public class FunGuide extends Activity {

	private int lastShowPos= 0;
	private int imgSize = 4;

	public FunGudeGallery images_ga;
	private LinearLayout pointLinear;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funguide);
		init();
	}

	private void init() {

		pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		LinearLayout.LayoutParams pointLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		pointLayoutParams.leftMargin = 10;

		for (int i = 0; i < imgSize; i++) {
			ImageView pointView = new ImageView(this);
			pointView.setBackgroundResource(R.drawable.p_off);
			pointLinear.addView(pointView, pointLayoutParams);
		}

		FunGudeAdapter imageAdapter = new FunGudeAdapter(imgSize, this);
		images_ga = (FunGudeGallery) findViewById(R.id.image_wall_gallery);
		images_ga.setAdapter(imageAdapter);

		images_ga.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == imgSize - 1) {
					Intent i = new Intent();
					i.setClass(FunGuide.this, MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					FunGuide.this.finish();
				}
			}
		});
		
		images_ga.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long id) {
				// TODO Auto-generated method stub
				chgCurPoint(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
	}

	/**
	 * 改变选中点点的位置
	 * @param pos
	 */
	public void chgCurPoint(int pos) {
		
		View lastView = pointLinear.getChildAt(lastShowPos);
		View curView = pointLinear.getChildAt(pos);
		if(lastShowPos==pos)
		{
			curView.setBackgroundResource(R.drawable.p_on);
		}
		else
		{
			lastView.setBackgroundResource(R.drawable.p_off);
			curView.setBackgroundResource(R.drawable.p_on);
		}
		lastShowPos=pos;
	}
}

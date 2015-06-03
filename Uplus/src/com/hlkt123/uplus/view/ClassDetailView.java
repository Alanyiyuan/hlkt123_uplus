package com.hlkt123.uplus.view;

import java.util.ArrayList;

import com.hlkt123.uplus.R;
import com.hlkt123.uplus.entity.ClassDetail;
import com.hlkt123.uplus.entity.Orders;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassDetailView extends ViewGroup{
	
	private Orders orders;
	private LayoutInflater layoutInflater;
	private ArrayList<ClassDetail> classDetails;
	private int mMaxChildWidth;
	private int mMaxChildHeight;

	public ClassDetailView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ClassDetailView(Context context, Orders orders) {
		super(context);
		// TODO Auto-generated constructor stub
		this.orders = orders;
		this.classDetails = orders.getClassDetails();
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addOrders();
		addClassDetails();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		mMaxChildWidth = 0;
		mMaxChildHeight = 0;
		
		final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.UNSPECIFIED);
		final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.UNSPECIFIED);
		
		int count = getChildCount();
		if(count == 0) {
			super.onMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
			return;
		}
		
		for(int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if(child.getVisibility() == GONE) {
				continue;
			}
			
			child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
			mMaxChildWidth = Math.max(mMaxChildWidth, child.getMeasuredWidth());
			
			if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
				mMaxChildHeight += child.getMeasuredHeight();
			}else{
				mMaxChildHeight = Math.max(mMaxChildHeight, child.getMeasuredHeight());
			}
		}
		
		if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
			setMeasuredDimension(mMaxChildWidth, mMaxChildHeight);
		} else{
			setMeasuredDimension(resolveSize(mMaxChildWidth, widthMeasureSpec), resolveSize(mMaxChildHeight, heightMeasureSpec));
		}
		
	}
	

	private void addClassDetails() {
		// TODO Auto-generated method stub
		for(int i = 0; i < classDetails.size(); i++) {
			View view = layoutInflater.inflate(R.layout.order2confirm_item, null);
			((TextView)view.findViewById(R.id.order_tv_name)).setText(classDetails.get(i).getName());
			((TextView)view.findViewById(R.id.order_tv_address)).setText(classDetails.get(i).getAddress());
			((TextView)view.findViewById(R.id.order_tv_price)).setText(classDetails.get(i).getPrice());
			((TextView)view.findViewById(R.id.order_tv_time)).setText(classDetails.get(i).getTime());
			((ImageView)view.findViewById(R.id.order_img)).setImageResource(classDetails.get(i).getClassImg());
			addView(view);
		}
	}

	private void addOrders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		int top = 10;
		for(int i = 0; i < count; i++) {
			View child = this.getChildAt(i);
			int childH = child.getMeasuredHeight();
			child.layout(0, top, arg3, top + childH);
			top = top + childH + 1;
		}
	}
	
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	};
}

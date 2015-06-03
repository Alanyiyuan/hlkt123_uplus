package com.hlkt123.uplus;

import com.hlkt123.uplus.view.Order2ConfirmFragment;
import com.hlkt123.uplus.view.Order2PayFragment;
import com.hlkt123.uplus.view.OrderAllFragment;
import com.hlkt123.uplus.view.OrderFinishFragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MyOrderActivity extends FragmentActivity implements OnClickListener {

	public static MyOrderActivity homeActivityInstance = null;
	private LinearLayout tabAll;
	private LinearLayout tab2Pay;
	private LinearLayout tab2Confirm;
	private LinearLayout tabFinish;
	
	private TextView tvAll;
	private TextView tv2Pay;
	private TextView tv2Confirm;
	private TextView tvFinish;
	
	private Fragment fragmentAll;
	private Fragment fragment2Pay;
	private Fragment fragment2Confirm;
	private Fragment fragmentFinish;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_order_activity);
		
		homeActivityInstance = this;
		initView();
		
		initEvent();
		
		setSelect(1);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		tabAll.setOnClickListener(this);
		tab2Pay.setOnClickListener(this);
		tab2Confirm.setOnClickListener(this);
		tabFinish.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		TextView title = (TextView) findViewById(R.id.include_header);
		title.setText(R.string.my_order);
		
		tabAll = (LinearLayout) findViewById(R.id.id_tab_all);
		tab2Pay = (LinearLayout) findViewById(R.id.id_tab_2pay);
		tab2Confirm = (LinearLayout) findViewById(R.id.id_tab_2confirm);
		tabFinish = (LinearLayout) findViewById(R.id.id_tab_finish);
		
		tvAll = (TextView) findViewById(R.id.id_tv_all);
		tv2Pay = (TextView) findViewById(R.id.id_tv_2pay);
		tv2Confirm = (TextView) findViewById(R.id.id_tv_2confirm);
		tvFinish = (TextView) findViewById(R.id.id_tv_finish);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetTag();
		switch (v.getId()) {
		case R.id.id_tab_all:
			setSelect(0);
			break;
		case R.id.id_tab_2pay:
			setSelect(1);
			break;
		case R.id.id_tab_2confirm:
			setSelect(2);
			break;
		case R.id.id_tab_finish:
			setSelect(3);
			break;
		default:
			break;
		}
	}

	private void setSelect(int i) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		
		switch (i) {
		case 0:
			if(fragmentAll == null)
			{
				fragmentAll = new OrderAllFragment();
				transaction.add(R.id.id_myorder, fragmentAll);
			}else{
				transaction.show(fragmentAll);
			}
			tvAll.setTextColor(getResources().getColor(R.color.red));
			break;
		case 1:
			if(fragment2Pay == null)
			{
				fragment2Pay = new Order2PayFragment();
				transaction.add(R.id.id_myorder, fragment2Pay);
			}else{
				transaction.show(fragment2Pay);
			}
			tv2Pay.setTextColor(getResources().getColor(R.color.red));
			break;
		case 2:
			if(fragment2Confirm == null)
			{
				fragment2Confirm = new Order2ConfirmFragment();
				transaction.add(R.id.id_myorder, fragment2Confirm);
			}else{
				transaction.show(fragment2Confirm);
			}
			tv2Confirm.setTextColor(getResources().getColor(R.color.red));
			break;
		case 3:
			if(fragmentFinish == null)
			{
				fragmentFinish = new OrderFinishFragment();
				transaction.add(R.id.id_myorder, fragmentFinish);
			}else{
				transaction.show(fragmentFinish);
			}
			tvFinish.setTextColor(getResources().getColor(R.color.red));
			break;

		default:
			break;
		}
		
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if(fragmentAll != null)
		{
			transaction.hide(fragmentAll);
		}
		if(fragment2Pay != null)
		{
			transaction.hide(fragment2Pay);
		}
		if(fragment2Confirm != null)
		{
			transaction.hide(fragment2Confirm);
		}
		if(fragmentFinish != null)
		{
			transaction.hide(fragmentFinish);
		}
	}

	private void resetTag() {
		// TODO Auto-generated method stub
		tvAll.setTextColor(getResources().getColor(R.color.black));
		tv2Pay.setTextColor(getResources().getColor(R.color.black));
		tv2Confirm.setTextColor(getResources().getColor(R.color.black));
		tvFinish.setTextColor(getResources().getColor(R.color.black));
	}
	
	
}

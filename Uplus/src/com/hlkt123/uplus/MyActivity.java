package com.hlkt123.uplus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyActivity extends BaseActivity implements OnClickListener{
	
	private View img_my_detail;
	private ImageView img_my_suggest;
	private ImageView img_my_call;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_activity);
		
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		img_my_detail = findViewById(R.id.img_detail_arrow);
		img_my_suggest = (ImageView) findViewById(R.id.img_suggest_arrow);
		img_my_call = (ImageView) findViewById(R.id.img_call_arrow);
		
		img_my_detail.setOnClickListener(this);
		img_my_suggest.setOnClickListener(this);
		img_my_call.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_detail_arrow:
			myDetail();
			break;
		case R.id.img_suggest_arrow:
			
			break;
		case R.id.img_call_arrow:
			new AlertDialog.Builder(MyActivity.this).setMessage("联系我们：15088719047")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					callUs();
				}
			}).setNegativeButton("返回", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			break;
			

		default:
			break;
		}
	}

	private void myDetail() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MyActivity.this, MyDetailActivity.class);
		startActivity(intent);
	}

	protected void callUs() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + "15088719047"));
		startActivity(intent);
	}
	
	
}

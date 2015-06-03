package com.hlkt123.uplus;

import org.json.JSONException;
import org.json.JSONObject;

import com.hlkt123.uplus.model.UserBean;
import com.hlkt123.uplus.util.ToastUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener{
	public final static int LOGIN_MAIN= 0x01;
	public final static int RESULT_LOGIN_SUCCESS = 1000;
	public final static int RESULT_NO_USERNAME = 1001;
	public final static int RESULT_ERROR_PSW = 1002;
	
	private AutoCompleteTextView login_account_et;
	private EditText login_psw_et;
	private ProgressDialog mypDialog;
	private Button login_btn;
	private TextView login_findback_tv;
	private TextView login_register_tv;
	
	private String login_account;
	private String login_psw;
	
	private ImageView title_back_img;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			if(mypDialog != null){
				mypDialog.dismiss();
			}
			switch (msg.what) {
			case RESULT_LOGIN_SUCCESS:
//				mypDialog.cancel();
				GlobalApplication ac = (GlobalApplication)getApplication();
				UserBean user = new UserBean();
				user.setAccount(login_account);
				user.setPwd(login_psw);
				user.setRememberMe(true);
				user.setUid(0001);
				ac.saveLoginInfo(user);
				ToastUtil.disToastLong(getApplicationContext(), "登录成功");
				break;
			case RESULT_NO_USERNAME:
				break;
			case RESULT_ERROR_PSW:
				break;
			default:
				break;
			}
//			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		init();
		
	}

	private void init(){
		login_account_et = (AutoCompleteTextView) findViewById(R.id.login_account_input);
		login_psw_et = (EditText) findViewById(R.id.login_psw_input);
		login_btn = (Button)findViewById(R.id.login_btn);
		login_findback_tv = (TextView) findViewById(R.id.login_findback_tv);
		login_register_tv = (TextView) findViewById(R.id.login_register_tv);
		title_back_img = (ImageView) findViewById(R.id.title_back_img);
		
		login_btn.setOnClickListener(this);
		login_findback_tv.setOnClickListener(this);
		login_register_tv.setOnClickListener(this);
		title_back_img.setOnClickListener(this);
		
		
		GlobalApplication ac = (GlobalApplication) getApplication();
		UserBean user = ac.getLoginInfo();
		
		if(user == null || !user.isRememberMe())return;
		if(!user.getAccount().equals("")){
			login_account_et.setText(user.getAccount());
		}
		
		if(!user.getPwd().equals("")){
			login_psw_et.setText(user.getPwd());
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn:
			login();
			break;
		case R.id.login_register_tv:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_findback_tv:
			Intent intent2 = new Intent(LoginActivity.this, FindBackPswActivity.class);
			startActivity(intent2);
			break;
		case R.id.title_back_img:
			finish();
			break;
		default:
			break;
		}
	}
	
	private void login(){
		login_account = login_account_et.getText().toString().trim();
		login_psw = login_psw_et.getText().toString().trim();
		
		if(login_account.length() == 0){
			ToastUtil.disToastLong(LoginActivity.this, "请输入用户名");
			return;
		}
		
		if(login_psw.length() == 0){
			ToastUtil.disToastLong(LoginActivity.this, "请输入密码");
			return;
		}
		 mypDialog = new ProgressDialog(this);
		 mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		 mypDialog.setTitle("正在登录中");
		 mypDialog.setMessage("正在登录中，请稍候......");
		 mypDialog.setCanceledOnTouchOutside(false);
		 mypDialog.setIndeterminate(false);
		 mypDialog.show();
		 getLoginResult();
	}
	
	private void getLoginResult(){
		MThread mThread = new MThread();
		new Thread(mThread).start();
	}
	
	private class MThread implements Runnable {

		@Override
		public void run() {
			String result = null;
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = "{\"resultCode\":\"1000\",\"userId\":1,\"userName\":\"liuyiyuan\",\"userType\":1}";
			
			if(result != null && !result.equals("")){
				JSONObject jo = null;
				String resultCode = null;
				try {
					jo = new JSONObject(result);
					if(jo.has("resultCode")){
						resultCode = jo.getString("resultCode");
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(resultCode != null && resultCode.equals("1000")){
					Message msg = new Message();
					msg.what=1000;
					msg.obj=result;
					mHandler.sendMessage(msg);
				}
				
			}
		}
		
	}
}

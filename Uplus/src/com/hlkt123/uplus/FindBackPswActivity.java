package com.hlkt123.uplus;

import org.json.JSONException;
import org.json.JSONObject;

import com.hlkt123.uplus.util.CyptoUtils;
import com.hlkt123.uplus.util.MyCounterTimer;
import com.hlkt123.uplus.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FindBackPswActivity extends BaseActivity implements OnClickListener{

	public final static int RESULT_FINDBACKPSW_SUCCESS = 3000;
	public final static int RESULT_FINDBACKPSW_ERROR = 3001;
	
	private Button btn_clock;
	private Button btn_confirm;
	private EditText findbackPsw_account_et;
	private EditText findbackPsw_verifycode_et;
	private EditText findbackPsw_psw_et;
	private ImageView title_back_img;
	private ImageView see_forbidden_img;
	private boolean see_forbidden = true;
	private String psw;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RESULT_FINDBACKPSW_SUCCESS:
				String result = msg.obj.toString();
				ToastUtil.disToastLong(FindBackPswActivity.this, "找回密码成功，返回的json为" + result);
				ToastUtil.disToastLong(FindBackPswActivity.this, CyptoUtils.decode("uclassuclass", psw));
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.findback_psw_activity);
		
		init();
	}
	
	private void init(){
		findbackPsw_account_et = (EditText) findViewById(R.id.findbackPsw_account_input);
		findbackPsw_verifycode_et = (EditText) findViewById(R.id.findbackPsw_verifycode);
		findbackPsw_psw_et = (EditText) findViewById(R.id.findbackPsw_psw_input);
		
		btn_clock = (Button) findViewById(R.id.findbackPsw_get_verifycode);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		title_back_img = (ImageView)findViewById(R.id.title_back_img);
		see_forbidden_img = (ImageView)findViewById(R.id.see_forbidden_img);
		btn_clock.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		title_back_img.setOnClickListener(this);
		see_forbidden_img.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.findbackPsw_get_verifycode:
			String account = findbackPsw_account_et.getText().toString();
			if(account.trim().length() != 11){
				ToastUtil.disToastLong(this, "用户名格式不正确，请输入正确的手机号码");
			}else{
				MyCounterTimer timeCount = new MyCounterTimer(btn_clock);
				timeCount.start();
			}
			break;
		case R.id.btn_confirm:
			findbackPsw();
			break;
		case R.id.title_back_img:
			finish();
			break;
		case R.id.see_forbidden_img:
			if(see_forbidden){
				see_forbidden = false;
				see_forbidden_img.setImageResource(R.drawable.et_see);
				findbackPsw_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			}else{
				see_forbidden = true;
				see_forbidden_img.setImageResource(R.drawable.et_see_forbidden);
				findbackPsw_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 找回密码
	 */
	private void findbackPsw(){
		if(findbackPsw_account_et == null || findbackPsw_psw_et == null
				|| findbackPsw_verifycode_et == null){
			ToastUtil.disToastLong(this, "找回密码失败");
		}
		
		String account = findbackPsw_account_et.getText().toString();
		String verifycode = findbackPsw_verifycode_et.getText().toString();
		psw = findbackPsw_psw_et.getText().toString();
		
		if(account.length() == 0){
			ToastUtil.disToastLong(this, "手机号码不能为空");
			return;
		}
		
		if(account.trim().length() != 11){
			ToastUtil.disToastLong(this, "用户名格式不正确，请输入正确的手机号码");
			return;
		}
		
		if(verifycode.trim().length() == 0){
			ToastUtil.disToastLong(this, "验证码不能为空");
			return;
		}
		
		if(psw.trim().length() == 0){
			ToastUtil.disToastLong(this, "密码不能为空");
		}
		
		if(psw.trim().length() < 6 || psw.trim().length() > 16){
			ToastUtil.disToastLong(this, "请输入6位至16位的密码");
			return;
		}
		
		psw = CyptoUtils.encode("uclassuclass", psw);
		
		String message = "{\"sendCode\":\"1000\",\"userName\":\"" + account + "\",\"verifycode\":\""
				+ verifycode + "\",\"psw\":\"" + psw + "\"}";
		ToastUtil.disToastLong(this, message);
		
		getFindbackPswResult();
	}
	
	private void getFindbackPswResult(){
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
			
			result = "{\"resultCode\":\"3000\",\"userName\":\"15088719047\"}";
			
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
				
				if(resultCode != null && resultCode.equals("3000")){
					Message msg = new Message();
					msg.what=3000;
					msg.obj=result;
					mHandler.sendMessage(msg);
				}
			}
		}
		
	}
}

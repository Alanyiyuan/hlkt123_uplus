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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class RegisterActivity extends Activity implements OnClickListener, OnCheckedChangeListener{
	public final static int RESULT_REGISTER_SUCCESS = 2000;
	public final static int RESULT_REGISTER_ERROR = 2001;
	public final static int NONE_INVITATIONCODE = 10000;
	private Button btn_clock;
	private Button btn_register;
	private EditText register_username_et;
	private EditText register_verifycode_et;
	private EditText register_psw_et;
	private EditText register_invitationcode_et;
	private ImageView title_back_img;
	private ImageView see_forbidden_img;
	
//	private CheckBox checkbox;
	
	private String testPsw;
	private boolean see_forbidden = true;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RESULT_REGISTER_SUCCESS:
				String result = msg.obj.toString();
				ToastUtil.disToastLong(RegisterActivity.this, "注册成功，返回的json为" + result);
				ToastUtil.disToastLong(RegisterActivity.this, CyptoUtils.decode("uclassuclass", testPsw));
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
		setContentView(R.layout.register_activity);
		
		init();
		
	}
	
	private void init(){
		btn_clock = (Button) findViewById(R.id.btn_get_verifycode);
		btn_clock.setOnClickListener(this);
		
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);
//		btn_register.setClickable(false);
		
		register_username_et = (EditText) findViewById(R.id.register_username);
		register_verifycode_et = (EditText) findViewById(R.id.register_verifycode);
		register_psw_et = (EditText) findViewById(R.id.register_password);
		register_invitationcode_et = (EditText) findViewById(R.id.register_invitationcode);
//		checkbox = (CheckBox)findViewById(R.id.checkbox);
		
//		checkbox.setOnCheckedChangeListener(this);
		
		title_back_img = (ImageView)findViewById(R.id.title_back_img);
		title_back_img.setOnClickListener(this);
		
		see_forbidden_img = (ImageView)findViewById(R.id.see_forbidden_img);
		see_forbidden_img.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_get_verifycode:
			String username = register_username_et.getText().toString();
			if(username.trim().toString().length() != 11){
				ToastUtil.disToastLong(this, "用户名格式不正确，请输入正确的手机号码");
			}else{
				MyCounterTimer timeCount = new MyCounterTimer(btn_clock);
				timeCount.start();
			}
			break;
		case R.id.btn_register:
			register();
			break;
		case R.id.title_back_img:
			finish();
			break;
		case R.id.see_forbidden_img:
			if(see_forbidden){
				see_forbidden = false;
				see_forbidden_img.setImageResource(R.drawable.et_see);
				register_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			}else{
				see_forbidden = true;
				see_forbidden_img.setImageResource(R.drawable.et_see_forbidden);
				register_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}
			break;
		default:
			break;
		}
		
	}
	
	private void getRegisterResult(){
		MThread mThread = new MThread();
		new Thread(mThread).start();
	}
	
	/**
	 * 注册
	 */
	private void register(){
		//输入框的每个字符串都要判断一下是否符合格式
		
		if(register_username_et == null || register_verifycode_et == null
				|| register_psw_et == null || register_invitationcode_et == null){
			ToastUtil.disToastLong(this, "注册失败");
			return;
		}
		String username = register_username_et.getText().toString();
		String verifycode = register_verifycode_et.getText().toString();
		String psw = register_psw_et.getText().toString();
		String invitationcode = register_invitationcode_et.getText().toString();
		
		if(username.trim().length() == 0){
			ToastUtil.disToastLong(this, "手机号码不能为空");
			return;
		}
		
		if(username.trim().length() != 11){
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
		
		testPsw = CyptoUtils.encode("uclassuclass", psw);
//		testPsw = psw;
		if(invitationcode.trim().length() == 0){
			invitationcode = "" + NONE_INVITATIONCODE;
		}
		
		String message = "{\"sendCode\":\"1000\",\"userName\":\"" + username + "\",\"verifycode\":\""
				+ verifycode + "\",\"psw\":\"" + testPsw + "\",\"invitationcode\":\"" + invitationcode + "\"}";
		ToastUtil.disToastLong(this, message);
		
		getRegisterResult();
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
			
			result = "{\"resultCode\":\"2000\",\"userName\":\"15088719047\"}";
			
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
				
				if(resultCode != null && resultCode.equals("2000")){
					Message msg = new Message();
					msg.what=2000;
					msg.obj=result;
					mHandler.sendMessage(msg);
				}
			}
		}
		
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg1 == true){
			btn_register.setClickable(true);
		}else{
			btn_register.setClickable(false);
		}
	}
}

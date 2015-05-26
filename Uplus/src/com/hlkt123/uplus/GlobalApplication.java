package com.hlkt123.uplus;

import java.util.Properties;

import android.app.Application;

import com.hlkt123.uplus.api.ApiClient;
import com.hlkt123.uplus.app.AppConfig;
import com.hlkt123.uplus.model.UserBean;
import com.hlkt123.uplus.util.CrashHandler;
import com.hlkt123.uplus.util.CyptoUtils;
import com.hlkt123.uplus.util.LogUtil;
import com.hlkt123.uplus.util.StringUtil;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun APPȫ�ֱ����洢�࣬��Ҫ��ҳ����ڵı������Դ洢�ڸ�����,�����õı�����Ҫ���ڸ��ļ��ڣ�����̫��ᵼ���ڴ濪������
 */
public class GlobalApplication extends Application {

	static GlobalApplication mGapp;
	private boolean login = false; //��¼״̬
	private int loginUid = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LogUtil.upLog_i("Gapp", "create ..");
		// ��ӱ�����������
		CrashHandler catchHandler = CrashHandler.getInstance();
		catchHandler.init(getApplicationContext());

		mGapp = this;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
	/**
	 * �û��Ƿ��¼
	 * @return
	 */
	public boolean isLogin() {
		return login;
	}
	
	/**
	 * ��ȡ��¼�û�id
	 * @return
	 */
	public int getLoginUid() {
		return this.loginUid;
	}
	
	public UserBean getLoginInfo() {
		UserBean user = new UserBean();
		user.setUid(0001);
		user.setAccount(getProperty("user.account"));
		user.setPwd(CyptoUtils.decode("uclassuclass", getProperty("user.pwd")));
		user.setRememberMe(StringUtil.toBool(getProperty("user.isRememberMe")));
		return user;
	}

	/**
	 * �û�ע��
	 */
	public void Logout(){
		ApiClient.cleanCookie();
		this.cleanCookie();
		this.login = false;
		this.loginUid = 0;
	}
	
	/**
	 * �����¼��Ϣ
	 * @param user
	 */
	public void saveLoginInfo(final UserBean user) {
		this.loginUid = user.getUid();
		this.login = true;
		setProperties(new Properties(){
			{
				setProperty("user.uid", "0001");
				setProperty("user.account", user.getAccount());
				setProperty("user.pwd", CyptoUtils.encode("uclassuclass", user.getPwd()));
				setProperty("user.isRememberMe", String.valueOf(user.isRememberMe()));
			}
		});
	}
	
	/**
	 * �������Ļ���
	 */
	public void cleanCookie()
	{
		removeProperty(AppConfig.CONF_COOKIE);
	}
	
	public void removeProperty(String...key){
		AppConfig.getAppConfig(this).remove(key);
	}
	
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	
	public void setProperties(Properties ps){
		AppConfig.getAppConfig(this).set(ps);
	}

}

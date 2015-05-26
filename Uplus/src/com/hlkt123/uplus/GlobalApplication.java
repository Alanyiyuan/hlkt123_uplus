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
 * @fun APP全局变量存储类，需要跨页面存在的变量可以存储在该类中,不常用的变量不要存在该文件内，变量太多会导致内存开销过大
 */
public class GlobalApplication extends Application {

	static GlobalApplication mGapp;
	private boolean login = false; //登录状态
	private int loginUid = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LogUtil.upLog_i("Gapp", "create ..");
		// 添加崩溃错误拦截
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
	 * 用户是否登录
	 * @return
	 */
	public boolean isLogin() {
		return login;
	}
	
	/**
	 * 获取登录用户id
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
	 * 用户注销
	 */
	public void Logout(){
		ApiClient.cleanCookie();
		this.cleanCookie();
		this.login = false;
		this.loginUid = 0;
	}
	
	/**
	 * 保存登录信息
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
	 * 清除保存的缓存
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

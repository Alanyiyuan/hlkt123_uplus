package com.hlkt123.uplus.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.content.Context;

/**
 * @author wuhongquan
 * @data 2015-05-26
 *
 */
public class AppConfig {
	
	private final static String APP_CONFIG = "config";
	public final static String CONF_COOKIE = "cookie";
	
	private Context mContext;
	private static AppConfig appConfig;
	
	public static AppConfig getAppConfig(Context context)
	{
		if(appConfig == null){
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}
	
	public void remove(String...key)
	{
		Properties props = get();
		for(String k : key)
			props.remove(k);
		setProps(props);
	}
	
	public String get(String key)
	{
		Properties props = get();
		return (props != null)?props.getProperty(key):null;
	}
	
	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		
		try {
			
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);
			
			props.load(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return props;
	}
	
	private void setProps(Properties p) {
		FileOutputStream fos = null;
		
		try {
			
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);
			
			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void set(Properties ps)
	{
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}
	
	public void set(String key, String value)
	{
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}
}

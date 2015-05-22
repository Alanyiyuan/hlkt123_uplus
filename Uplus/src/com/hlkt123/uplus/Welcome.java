package com.hlkt123.uplus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.hlkt123.uplus.sqlite.DaoSQL;
import com.hlkt123.uplus.util.HttpUtil;
import com.hlkt123.uplus.util.LogUtil;

/**
 * 
 * @author liuyiyuan
 * @date 2015-5-22
 * @fun 欢迎界面
 */
public class Welcome extends BaseActivity {

	// private static final String TAG = "WelcomeActivity";
	/**
	 * sqlite库在手机里的路径
	 */
	private String DATABASE_PATH = "/data/data/@packageName/databases/";
	private static final String DATABASE_NAME = "uplus.db"; //需要复制的数据库文件名字
	private final int DATABASE_VERSION=1; //需要复制的数据库版本号
	
	private int dbResID = R.raw.uplus;
	private DaoSQL dbhelper = null;
	private int lastApkVersionCode = 0;
	private int currVersionCode = 0;
	private GlobalApplication gapp = null;
	private static final int NET_WORK_COLSED = 1001;// 登录完毕

	/**
	 * 是否展示功能引导页面 sqlite库复制的时候，表示版本更新或者第一次安装，此时需要展示功能引导页
	 */
	private boolean showFunGuide = false;

	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case NET_WORK_COLSED: // 网络关闭2s后进入首页
				Intent intent = new Intent();
				intent.setClass(Welcome.this, MainActivity.class);
				startActivity(intent);
				Welcome.this.finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		gapp = (GlobalApplication) getApplication();
		
		initMsgDB("user.db",1);
		initSQLite();// 初始化数据库
		if (HttpUtil.isOpenNetwork(Welcome.this)) {
			gotoMainPage();
		} else {
			mHandler.sendEmptyMessageDelayed(NET_WORK_COLSED, 2000);
		}
	}

//	private void appNameAnim() {
//		TextView appName = (TextView) findViewById(R.id.appNameTV);
//		ObjectAnimator scaleAnim = ObjectAnimator.ofFloat(appName, "scaleX",
//				1.0f, 2.0f);
//		ObjectAnimator scaleAnim2 = ObjectAnimator.ofFloat(appName, "scaleY",
//				1.0f, 2.0f);
//		ObjectAnimator transAnim = ObjectAnimator.ofFloat(appName,
//				"translationY", 0f, -120f);
//		ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(appName, "alpha", 0f,
//				1f);
//		AnimatorSet animatorSet = new AnimatorSet();
//		animatorSet.playTogether(scaleAnim, scaleAnim2, transAnim, alphaAnim);
//		animatorSet.setDuration(2000);
//		animatorSet.start();
//	}

	/**
	 * 进入首页
	 * 
	 * @param version
	 */
	private void gotoMainPage() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!showFunGuide) // 进入首页
				{
					Intent intent = new Intent();
					intent.setClass(Welcome.this, MainActivity.class);
					startActivity(intent);
					Welcome.this.finish();
				} else // 展示功能介绍页
				{
					Intent intent = new Intent();
					intent.setClass(Welcome.this, FunGuide.class);
					startActivity(intent);
					Welcome.this.finish();
				}

			}
		});
		t.start();

	}

	/**
	 * 初始化SQLite数据，如果不存在就复制，如果版本升级了，也需要把sqlite库替换为新版的数据库， 版本无变化，则不动
	 */
	public void initSQLite() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					dbhelper = new DaoSQL(DATABASE_NAME,DATABASE_VERSION);
					// 动态修改sqlite 数据库文件的路径
					DATABASE_PATH = DATABASE_PATH.replace("@packageName",
							gapp.getPackageName());
					try {
						currVersionCode = getPackageManager().getPackageInfo(
								gapp.getPackageName(), 0).versionCode;
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}

					boolean dbExist = checkDataBase();
					if (!dbExist)// 不存在就把raw里的数据库写入手机
					{
						try {
							copyDataBase();
						} catch (IOException e) {
							throw new Error("Error copying database");
						}
						// 将第一个版本号插入数据库中
						dbhelper.updateLastApkVersionCode(
								getApplicationContext(), currVersionCode);
						LogUtil.upLog_i("update versionCode", " succ");
						showFunGuide = true;

					} else // 如果存在，取出数据库的版本号，版本号和当前APK的版本号不一致，也将数据库替换
					{
						lastApkVersionCode = dbhelper
								.getLastApkVersionCode(getApplicationContext());
						// 如果当前数据库中的APK的版本号小于目前APK的版本号，则将原来的数据库用自带的数据库替换掉
						if (lastApkVersionCode < currVersionCode) {
							try {
								copyDataBase();
								// 数据库复制完毕后，将当前APK的版本号更新到数据库中
								dbhelper.updateLastApkVersionCode(
										getApplicationContext(),
										currVersionCode);
								LogUtil.upLog_i("WelcomeActivity",
										"cpoy database succ");
							} catch (IOException e) {
								throw new Error("Error copying database");
							}
							showFunGuide = true;
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		t.start();

	}

	/**
	 * 创建动态存储用户信息的数据库，
	 * @param String _dbName 要创建的数据库名字；
	 * @param int _dbVersino 数据库的版本号
	 */
	public void initMsgDB(String _dbName,int _dbVersion) {
		DaoSQL dbhelper = new DaoSQL(_dbName,_dbVersion);
		dbhelper.initMsgDB(this);
	}

	/**
	 * 判断数据库是否存在
	 * 
	 * @return false or true
	 */
	public boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		boolean exist = false;
		try {
			String databaseFilename = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(databaseFilename, null,
					SQLiteDatabase.OPEN_READONLY);
			return true;
		} catch (SQLiteException e) {
			e.printStackTrace();
			exist = false;
		} finally {
			try {
				if (checkDB != null) {
					checkDB.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			checkDB = null;
		}
		return exist;
	}

	/**
	 * 复制数据库到手机指定文件夹下
	 */
	public void copyDataBase() throws IOException {
		String databaseFilenames = DATABASE_PATH + DATABASE_NAME;

		File dir = new File(DATABASE_PATH);
		if (!dir.exists())// 判断文件是否存在，不存在就新建一个
			dir.mkdir();
		FileOutputStream os = null;

		synchronized (this) {
			try {
				os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			InputStream is = Welcome.this.getResources()
					.openRawResource(dbResID);// 得到数据库文件的数据流
			byte[] buffer = new byte[1024];
			int count = 0;
			try {

				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
					os.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

}

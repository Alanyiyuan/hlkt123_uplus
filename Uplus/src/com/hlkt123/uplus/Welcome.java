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
 * @fun ��ӭ����
 */
public class Welcome extends BaseActivity {

	// private static final String TAG = "WelcomeActivity";
	/**
	 * sqlite�����ֻ����·��
	 */
	private String DATABASE_PATH = "/data/data/@packageName/databases/";
	private static final String DATABASE_NAME = "uplus.db"; //��Ҫ���Ƶ����ݿ��ļ�����
	private final int DATABASE_VERSION=1; //��Ҫ���Ƶ����ݿ�汾��
	
	private int dbResID = R.raw.uplus;
	private DaoSQL dbhelper = null;
	private int lastApkVersionCode = 0;
	private int currVersionCode = 0;
	private GlobalApplication gapp = null;
	private static final int NET_WORK_COLSED = 1001;// ��¼���

	/**
	 * �Ƿ�չʾ��������ҳ�� sqlite�⸴�Ƶ�ʱ�򣬱�ʾ�汾���»��ߵ�һ�ΰ�װ����ʱ��Ҫչʾ��������ҳ
	 */
	private boolean showFunGuide = false;

	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case NET_WORK_COLSED: // ����ر�2s�������ҳ
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
		initSQLite();// ��ʼ�����ݿ�
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
	 * ������ҳ
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

				if (!showFunGuide) // ������ҳ
				{
					Intent intent = new Intent();
					intent.setClass(Welcome.this, MainActivity.class);
					startActivity(intent);
					Welcome.this.finish();
				} else // չʾ���ܽ���ҳ
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
	 * ��ʼ��SQLite���ݣ���������ھ͸��ƣ�����汾�����ˣ�Ҳ��Ҫ��sqlite���滻Ϊ�°�����ݿ⣬ �汾�ޱ仯���򲻶�
	 */
	public void initSQLite() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					dbhelper = new DaoSQL(DATABASE_NAME,DATABASE_VERSION);
					// ��̬�޸�sqlite ���ݿ��ļ���·��
					DATABASE_PATH = DATABASE_PATH.replace("@packageName",
							gapp.getPackageName());
					try {
						currVersionCode = getPackageManager().getPackageInfo(
								gapp.getPackageName(), 0).versionCode;
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}

					boolean dbExist = checkDataBase();
					if (!dbExist)// �����ھͰ�raw������ݿ�д���ֻ�
					{
						try {
							copyDataBase();
						} catch (IOException e) {
							throw new Error("Error copying database");
						}
						// ����һ���汾�Ų������ݿ���
						dbhelper.updateLastApkVersionCode(
								getApplicationContext(), currVersionCode);
						LogUtil.upLog_i("update versionCode", " succ");
						showFunGuide = true;

					} else // ������ڣ�ȡ�����ݿ�İ汾�ţ��汾�ź͵�ǰAPK�İ汾�Ų�һ�£�Ҳ�����ݿ��滻
					{
						lastApkVersionCode = dbhelper
								.getLastApkVersionCode(getApplicationContext());
						// �����ǰ���ݿ��е�APK�İ汾��С��ĿǰAPK�İ汾�ţ���ԭ�������ݿ����Դ������ݿ��滻��
						if (lastApkVersionCode < currVersionCode) {
							try {
								copyDataBase();
								// ���ݿ⸴����Ϻ󣬽���ǰAPK�İ汾�Ÿ��µ����ݿ���
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
	 * ������̬�洢�û���Ϣ�����ݿ⣬
	 * @param String _dbName Ҫ���������ݿ����֣�
	 * @param int _dbVersino ���ݿ�İ汾��
	 */
	public void initMsgDB(String _dbName,int _dbVersion) {
		DaoSQL dbhelper = new DaoSQL(_dbName,_dbVersion);
		dbhelper.initMsgDB(this);
	}

	/**
	 * �ж����ݿ��Ƿ����
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
	 * �������ݿ⵽�ֻ�ָ���ļ�����
	 */
	public void copyDataBase() throws IOException {
		String databaseFilenames = DATABASE_PATH + DATABASE_NAME;

		File dir = new File(DATABASE_PATH);
		if (!dir.exists())// �ж��ļ��Ƿ���ڣ������ھ��½�һ��
			dir.mkdir();
		FileOutputStream os = null;

		synchronized (this) {
			try {
				os = new FileOutputStream(databaseFilenames);// �õ����ݿ��ļ���д����
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			InputStream is = Welcome.this.getResources()
					.openRawResource(dbResID);// �õ����ݿ��ļ���������
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

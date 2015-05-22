package com.hlkt123.uplus.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.hlkt123.uplus.util.LogUtil;

public class DatabaseManage {

	private final static String TAG = "DatabaseManage";
	private Context objContext = null;
	private DatabaseHelper objDbHelper = null;
	private SQLiteDatabase objSqlLiteDb = null;
	private SQLiteCursor cursor = null;

	public DatabaseManage(Context context, String strDbName, int dbVersion) {
		objContext = context;
		objDbHelper = new DatabaseHelper(objContext, strDbName, null, dbVersion);
		objSqlLiteDb = objDbHelper.getWritableDatabase();
	}

	/*
	 * ��ȡ���ݿ����
	 */
	public SQLiteDatabase getSQLiteDatabase() {
		return objSqlLiteDb;
	}

	/*
	 * ִ����ɾ�ģ�����true��ʾִ�гɹ���false��ʾִ��ʧ��
	 */
	public boolean SqlExec(String sql) {
		if (sql.length() < 3)// ���ж�SQL���ĺϷ���
			return false;
		try {
			objSqlLiteDb.execSQL(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * ���ز�ѯ�������cursor��������select
	 */
	public SQLiteCursor SqlGet(String sql) {

		if (sql.length() < 3)// ���ж�SQL���ĺϷ���
		{
			return null;
		}
		try {
			cursor = (SQLiteCursor) objSqlLiteDb.rawQuery(sql, null);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return cursor;

	}

	/*
	 * �ж���ǰ���ݿ��Ƿ���� tabName�����ڷ���true,�����ڷ���false
	 */
	public boolean tabIsExist(String tabName) {
		boolean result = false;
		if (tabName == null) {
			return false;
		}
		Cursor cursor = null;
		try {

			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ tabName.trim() + "' ";
			cursor = (SQLiteCursor) objSqlLiteDb.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
				cursor.close();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public void closeDB() {
		if (objSqlLiteDb.isOpen()) {
			try {
				objSqlLiteDb.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
	}

	public class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context, String name,
				CursorFactory cursorFactory, int version) {
			super(context, name, cursorFactory, version);
		}

		public void onCreate(SQLiteDatabase db) {
			// �������ݿ�󣬶����ݿ�Ĳ���
			// ����ʱ�䣺���ݿ��һ�δ���ʱonCreate()�����ᱻ����

			// onCreate������һ�� SQLiteDatabase������Ϊ������������Ҫ�������������ͳ�ʼ������
			// �����������Ҫ��ɴ������ݿ������ݿ�Ĳ���

			LogUtil.upLog_d(TAG, "DatabaseHelper onCreate");

			// �����������SQL��䣨���Դ�SQLite Expert���ߵ�DDLճ�������ӽ�StringBuffer�У�
			String msg_tab_sql = "CREATE TABLE [t_user] ([_id] INTEGER NOT NULL ON CONFLICT ROLLBACK PRIMARY KEY, [memberid] varchar(10) NOT NULL, [name] varchar(20), [state] SMALLINT DEFAULT 0, [createTime] DATETIME);";

			// ִ�д������SQL���
			db.execSQL(msg_tab_sql);
			// ��������޸��������У�ֻҪ���ݿ��Ѿ����������Ͳ����ٽ������onCreate����
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// ����ʱ�䣺���DATABASE_VERSIONֵ����Ϊ�����,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade

			// onUpgrade����������������һ�� SQLiteDatabase����һ���ɵİ汾�ź�һ���µİ汾��
			// ����汾�ŷ����˱仯����ԭ��������Ϊ temp������һ���±�����ʱ������ݸ��Ƶ��´����ı��У��������ɾ����ʱ��
			LogUtil.upLog_i(TAG,"sqlite ��ʼ��������");
			if (newVersion > oldVersion) {
				db.execSQL("alter table [t_user] rename to [t_user_temp]");
				onCreate(db);
				Cursor c = db
						.rawQuery(
								"select memberid,name,state,createTime from t_wazemsg_temp order by createTime desc limit 0,50",
								null);
				if (c != null && c.moveToFirst()) {

					do {
						String insertSql="insert into [t_user](memberid,name,state,createTime)"
						        +" values("+c.getString(0)+",'"+c.getString(1)+"',"+c.getInt(2)+",'"+c.getString(3)+"')";
						db.execSQL(insertSql);
						LogUtil.upLog_i(TAG,"insert sql="+insertSql);
					} while (c.moveToNext());
					
					c.close();
				}
				
				db.execSQL("drop table if exists t_user_temp");
			}
			LogUtil.upLog_d(TAG, "DatabaseHelper onUpgrade");
			// db.execSQL("DROP TABLE IF EXISTS t_wazemsg" );
			// onCreate(db);
			// һ����ʵ����Ŀ���ǲ�����ô���ģ���ȷ���������ڸ������ݱ�ṹʱ����Ҫ�����û���������ݿ��е����ݲ���ʧ
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			// �����ݿ�����ȱ�ִ��
			super.onOpen(db);
			LogUtil.upLog_d(TAG, "DatabaseHelper onOpen");
		}
	}
}

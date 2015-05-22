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
	 * 获取数据库对象
	 */
	public SQLiteDatabase getSQLiteDatabase() {
		return objSqlLiteDb;
	}

	/*
	 * 执行增删改，返回true表示执行成功，false表示执行失败
	 */
	public boolean SqlExec(String sql) {
		if (sql.length() < 3)// 简单判断SQL语句的合法性
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
	 * 返回查询结果集的cursor对象，用于select
	 */
	public SQLiteCursor SqlGet(String sql) {

		if (sql.length() < 3)// 简单判断SQL语句的合法性
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
	 * 判定当前数据库是否存在 tabName表，存在返回true,不存在返回false
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
			// 创建数据库后，对数据库的操作
			// 调用时间：数据库第一次创建时onCreate()方法会被调用

			// onCreate方法有一个 SQLiteDatabase对象作为参数，根据需要对这个对象填充表和初始化数据
			// 这个方法中主要完成创建数据库后对数据库的操作

			LogUtil.upLog_d(TAG, "DatabaseHelper onCreate");

			// 构建创建表的SQL语句（可以从SQLite Expert工具的DDL粘贴过来加进StringBuffer中）
			String msg_tab_sql = "CREATE TABLE [t_user] ([_id] INTEGER NOT NULL ON CONFLICT ROLLBACK PRIMARY KEY, [memberid] varchar(10) NOT NULL, [name] varchar(20), [state] SMALLINT DEFAULT 0, [createTime] DATETIME);";

			// 执行创建表的SQL语句
			db.execSQL(msg_tab_sql);
			// 即便程序修改重新运行，只要数据库已经创建过，就不会再进入这个onCreate方法
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// 调用时间：如果DATABASE_VERSION值被改为别的数,系统发现现有数据库版本不同,即会调用onUpgrade

			// onUpgrade方法的三个参数，一个 SQLiteDatabase对象，一个旧的版本号和一个新的版本号
			// 如果版本号发生了变化，将原表重命名为 temp表，创建一个新表；把临时表的数据复制到新创建的表中，复制完毕删除临时表
			LogUtil.upLog_i(TAG,"sqlite 开始升级操作");
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
			// 一般在实际项目中是不能这么做的，正确的做法是在更新数据表结构时，还要考虑用户存放于数据库中的数据不丢失
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			// 打开数据库后首先被执行
			super.onOpen(db);
			LogUtil.upLog_d(TAG, "DatabaseHelper onOpen");
		}
	}
}

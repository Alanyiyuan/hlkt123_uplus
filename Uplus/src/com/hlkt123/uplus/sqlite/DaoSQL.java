package com.hlkt123.uplus.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;

import com.hlkt123.uplus.model.CitySpinnerBean;
import com.hlkt123.uplus.model.InputModel;

public class DaoSQL {
	public String DATABASE_NAME = ""; // 定义数据库名称
	public int DATABASE_VERSION = 1;// 定义数据库版本
	public SQLiteCursor cursor = null; // 存储结果集的游标
	public String sqlStr = ""; // 查询数据库的sql语句变量
	public DatabaseManage dbManage = null;

	public DaoSQL(String _dbName, int _dbVersion) {
		DATABASE_NAME = _dbName;
		DATABASE_VERSION = _dbVersion;
	}

	/**
	 * 初始化网泽推送的数据库
	 * 
	 * @param context
	 */
	public void initMsgDB(Context context) {
		dbManage = new DatabaseManage(context, DATABASE_NAME, DATABASE_VERSION);
		dbManage.closeDB();
	}

	public int getLastApkVersionCode(Context context) {
		int versionCode = 1;
		dbManage = new DatabaseManage(context, DATABASE_NAME, DATABASE_VERSION);
		// select top 1 * ...SQLlite不支持
		sqlStr = "select max(versionCode) from t_apkVersionInfo";

		SQLiteCursor c = null;
		try {
			c = dbManage.SqlGet(sqlStr);
			if (c.moveToFirst()) {
				versionCode = c.getInt(0);
			}
			c.close();// 关闭游标
			dbManage.closeDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public void updateLastApkVersionCode(Context context, int newVersionCode) {

		dbManage = new DatabaseManage(context, DATABASE_NAME, DATABASE_VERSION);
		// select top 1 * ...SQLlite不支持

		sqlStr = "update t_apkVersionInfo set versionCode=" + newVersionCode;
		dbManage.SqlExec(sqlStr);
		dbManage.closeDB();
	}

	/**
	 * 通过城市等级和父编码获取城市code-name list
	 * 
	 * @param mCnt
	 * @param lv
	 *            城市等级 1,2,3 省市区
	 * @param fatherID
	 *            父类ID
	 * @return
	 */
	public List<CitySpinnerBean> getCityListByLv(Context mCnt, int lv,
			String fatherID) {
		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);
		if (lv == 1) {
			sqlStr = "select provinceID code ,province name from t_province";
		} else if (lv == 2) {
			sqlStr = "select cityID code ,city name from t_city where father='"
					+ fatherID + "' order by cityID ";
		} else {
			sqlStr = "select areaID code ,area name from t_area where father='"
					+ fatherID + "' order by areaID";
		}

		SQLiteCursor c = null;
		List<CitySpinnerBean> list = new ArrayList<CitySpinnerBean>();
		try {
			c = dbManage.SqlGet(sqlStr);
			if (c != null && c.moveToFirst()) {
				while (!c.isAfterLast()) {
					CitySpinnerBean bean = new CitySpinnerBean(c.getString(0),
							c.getString(1));
					list.add(bean);
					c.moveToNext();
				}
				c.close();
			} else {
				if (c != null) {
					c.close();
				}
				dbManage.closeDB();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dbManage.closeDB();
		return list;
	}

	/**
	 * 通过城市名数组，查询格式化后 的省市名称,多个城市用逗号隔开
	 * 
	 * @param mCnt
	 * @param names
	 * @return
	 */
	public String getProvinceCityNamByCityName(Context mCnt, String[] names) {
		String result = "";
		if (names == null || names.length == 0) {
			return "";
		}
		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);
		SQLiteCursor c = null;
		for (String name : names) {
			if (name.equals("北京") || name.equals("天津") || name.equals("上海")
					|| name.equals("重庆") || name.equals("北京市")
					|| name.equals("天津市") || name.equals("上海市")
					|| name.equals("重庆市")) {
				result += name.replace("市", "") + "市" + ",";
			} else // 非直辖市的，先去城市表，省表中查询出城市名和省名称
			{
				sqlStr = "select province,city from t_city t1 left join t_province t2 on t1.[father]=t2.[provinceID] where city like '%"
						+ name + "%'";
				c = dbManage.SqlGet(sqlStr);
				if (c != null && c.moveToFirst()) {
					result += c.getString(0) + c.getString(1) + ",";
				}
			}
		}
		if (c != null) {
			c.close();
		}
		dbManage.closeDB();
		if (result.contains(",")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * 通过类型名，获取类型ID
	 * 
	 * @param mCnt
	 * @param type
	 *            1 表示货物类型，2表示车辆类型
	 * @param name
	 *            为空时，返回ID为1的记录
	 * @return
	 */
	public int getwebIDbyName(Context mCnt, int type, String name) {
		if (name == null || name.equals("")) {
			return 1;
		}
		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);
		sqlStr = "select webID from t_goods_car_type where infoType=" + type
				+ " and typeName='" + name + "'";
		Cursor c = dbManage.SqlGet(sqlStr);
		if (c != null && c.moveToFirst()) {
			int ret = c.getInt(0);
			c.close();
			dbManage.closeDB();
			return ret;
		}
		dbManage.closeDB();
		return 1;
	}

	/**
	 * 通过 类型ID，获取本地保存的 货物、车辆类型
	 * 
	 * @param mCnt
	 * @param type
	 *            1 表示货物类型，2表示车辆类型
	 * @param typeID
	 *            货源类型id或者 车辆类型id
	 * @return
	 */
	public String getNamebyID(Context mCnt, int type, int typeID) {
		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);
		sqlStr = "select typeName from t_goods_car_type where infoType=" + type
				+ " and webID=" + typeID + "";
		Cursor c = dbManage.SqlGet(sqlStr);
		if (c != null && c.moveToFirst()) {
			String ret = c.getString(0);
			c.close();
			dbManage.closeDB();
			return ret;
		}
		dbManage.closeDB();
		return "普通";
	}

	/**
	 * 获取获取类型的条数
	 * 
	 * @param mCnt
	 * @param type
	 * @return
	 */
	public int getTypeInfoCount(Context mCnt, int type) {

		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);
		sqlStr = "select count(webID) from t_goods_car_type where infoType="
				+ type + " ";
		Cursor c = dbManage.SqlGet(sqlStr);
		if (c != null && c.moveToFirst()) {
			int ret = c.getInt(0);
			c.close();
			dbManage.closeDB();
			return ret;
		}
		dbManage.closeDB();
		return 0;
	}

	/**
	 * 通过关键字 查询本地缓存库中 最新的10条记录
	 * 
	 * @param mCnt
	 * @param type
	 *            1 货物名，2 用户名；3 地址信息
	 * @param keywords
	 * @return
	 */
	public ArrayList<InputModel> getMemoList(Context mCnt, int type,
			String keywords) {
		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);

		sqlStr = "select _id,type,content from t_memo_input where type= "
				+ type
				+ (keywords == null || keywords.equals("") ? ""
						: " and content like '%" + keywords + "%'")
				+ " order by createTime desc limit 0,10 ";
		Cursor c = dbManage.SqlGet(sqlStr);
		if (c != null && c.moveToFirst()) {
			ArrayList<InputModel> list = new ArrayList<InputModel>();
			while (!c.isAfterLast()) {
				InputModel mib = new InputModel(type, c.getString(2));
				mib.set_id(c.getInt(0));
				list.add(mib);
				c.moveToNext();
			}
			c.close();
			dbManage.closeDB();
			return list;
		}
		dbManage.closeDB();
		return null;
	}

	

	/**
	 * 通过文字内容判断是否有相同的内容存在，存在就不存储
	 * 
	 * @param dbManage
	 * @param mib
	 * @return
	 */
	public boolean existContent(DatabaseManage dbManage, InputModel mib) {
		sqlStr = " select content from t_memo_input where content='"
				+ mib.getContent() + "' and type=" + mib.getType();
		Cursor c = dbManage.SqlGet(sqlStr);
		if (c != null && c.getCount() > 0) {
			c.close();
			return true;
		}
		if (c != null) {
			c.close();
		}
		return false;
	}

	/**
	 * 去数据中获取除去 出发地以外的热门城市
	 * 
	 * @param mCnt
	 * @param fromCity
	 *            地级市的名称，不带省的名称
	 * @return
	 */
	public ArrayList<String> getHotCitys(Context mCnt, String fromCity) {
		if (fromCity == null)
			return null;
		dbManage = new DatabaseManage(mCnt, DATABASE_NAME, DATABASE_VERSION);
		sqlStr = "select province,city from t_city c left join t_province p on c.father=p.provinceID where isHot =1 and city <> '"
				+ fromCity + "' ";
		Cursor c = dbManage.SqlGet(sqlStr);
		if (c != null && c.moveToFirst()) {
			ArrayList<String> list = new ArrayList<String>();
			while (!c.isAfterLast()) {
				try {
					list.add(c.getString(0)
							+ (c.getString(1).equals("全市") ? "" : c
									.getString(1)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				c.moveToNext();
			}
			c.close();
			dbManage.closeDB();
			return list;
		}
		dbManage.closeDB();
		return null;
	}

}

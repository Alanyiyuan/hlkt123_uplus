package com.hlkt123.uplus.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;

import com.hlkt123.uplus.model.CitySpinnerBean;
import com.hlkt123.uplus.model.InputModel;

public class DaoSQL {
	public String DATABASE_NAME = ""; // �������ݿ�����
	public int DATABASE_VERSION = 1;// �������ݿ�汾
	public SQLiteCursor cursor = null; // �洢��������α�
	public String sqlStr = ""; // ��ѯ���ݿ��sql������
	public DatabaseManage dbManage = null;

	public DaoSQL(String _dbName, int _dbVersion) {
		DATABASE_NAME = _dbName;
		DATABASE_VERSION = _dbVersion;
	}

	/**
	 * ��ʼ���������͵����ݿ�
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
		// select top 1 * ...SQLlite��֧��
		sqlStr = "select max(versionCode) from t_apkVersionInfo";

		SQLiteCursor c = null;
		try {
			c = dbManage.SqlGet(sqlStr);
			if (c.moveToFirst()) {
				versionCode = c.getInt(0);
			}
			c.close();// �ر��α�
			dbManage.closeDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public void updateLastApkVersionCode(Context context, int newVersionCode) {

		dbManage = new DatabaseManage(context, DATABASE_NAME, DATABASE_VERSION);
		// select top 1 * ...SQLlite��֧��

		sqlStr = "update t_apkVersionInfo set versionCode=" + newVersionCode;
		dbManage.SqlExec(sqlStr);
		dbManage.closeDB();
	}

	/**
	 * ͨ�����еȼ��͸������ȡ����code-name list
	 * 
	 * @param mCnt
	 * @param lv
	 *            ���еȼ� 1,2,3 ʡ����
	 * @param fatherID
	 *            ����ID
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
	 * ͨ�����������飬��ѯ��ʽ���� ��ʡ������,��������ö��Ÿ���
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
			if (name.equals("����") || name.equals("���") || name.equals("�Ϻ�")
					|| name.equals("����") || name.equals("������")
					|| name.equals("�����") || name.equals("�Ϻ���")
					|| name.equals("������")) {
				result += name.replace("��", "") + "��" + ",";
			} else // ��ֱϽ�еģ���ȥ���б�ʡ���в�ѯ����������ʡ����
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
	 * ͨ������������ȡ����ID
	 * 
	 * @param mCnt
	 * @param type
	 *            1 ��ʾ�������ͣ�2��ʾ��������
	 * @param name
	 *            Ϊ��ʱ������IDΪ1�ļ�¼
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
	 * ͨ�� ����ID����ȡ���ر���� �����������
	 * 
	 * @param mCnt
	 * @param type
	 *            1 ��ʾ�������ͣ�2��ʾ��������
	 * @param typeID
	 *            ��Դ����id���� ��������id
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
		return "��ͨ";
	}

	/**
	 * ��ȡ��ȡ���͵�����
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
	 * ͨ���ؼ��� ��ѯ���ػ������ ���µ�10����¼
	 * 
	 * @param mCnt
	 * @param type
	 *            1 ��������2 �û�����3 ��ַ��Ϣ
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
	 * ͨ�����������ж��Ƿ�����ͬ�����ݴ��ڣ����ھͲ��洢
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
	 * ȥ�����л�ȡ��ȥ ��������������ų���
	 * 
	 * @param mCnt
	 * @param fromCity
	 *            �ؼ��е����ƣ�����ʡ������
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
							+ (c.getString(1).equals("ȫ��") ? "" : c
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

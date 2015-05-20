package com.hlkt123.uplus.util;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

import com.hlkt123.uplus.model.CitySpinnerBean;

/**
 * ��ȡ�ֻ�ͨѶ¼�Ĺ�����
 * 
 * @author liu yiyuan
 * @date 2014-5-15
 * @email lyy@wazert.com
 * @company zhejiang wazert technology company
 */
public class ContactsUtil {

	/** �õ��ֻ�ͨѶ¼��ϵ����Ϣ **/
	
	@SuppressLint("InlinedApi")
	public static ArrayList<CitySpinnerBean> getPhoneContacts(Context mCnt,
			String keywords) {
		ArrayList<CitySpinnerBean> list = new ArrayList<CitySpinnerBean>();
		ContentResolver resolver = mCnt.getContentResolver();
		int sdkVersion=getAndroidSDKVersion();
		Cursor phoneCursor;
		if (keywords == null || keywords.equals("")) {
			 if(sdkVersion>=11)
			 {
				 phoneCursor = resolver.query(Phone.CONTENT_URI,
						 new String[] {
						 Phone.DISPLAY_NAME, Phone.NUMBER}, null,
						 null,Phone.SORT_KEY_PRIMARY);
			 }
			 else
			 {
				 phoneCursor = resolver.query(Phone.CONTENT_URI,
						 new String[] {
						 Phone.DISPLAY_NAME, Phone.NUMBER}, null,
						 null,null);
			 }

		} else {
			 if(sdkVersion>=11)
			 {
			 phoneCursor = resolver.query(Phone.CONTENT_URI,
					 new String[] {
					 Phone.DISPLAY_NAME, Phone.NUMBER},
					 Phone.DISPLAY_NAME+" like ? or "+Phone.NUMBER+" like ?", new
					 String[]{"%"+keywords+"%","%"+keywords+"%"},Phone.SORT_KEY_PRIMARY);
			 }
			 else
			 {
				 phoneCursor = resolver.query(Phone.CONTENT_URI,
						 new String[] {
						 Phone.DISPLAY_NAME, Phone.NUMBER},
						 Phone.DISPLAY_NAME+" like ? or "+Phone.NUMBER+" like ?", new
						 String[]{"%"+keywords+"%","%"+keywords+"%"},null); 
			 }
		}

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(1);
				// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				else {
					list.add(new CitySpinnerBean(phoneNumber,phoneCursor.getString(0)));
				}
			}
			phoneCursor.close();
		} else {
			return null;
		}
		return list;
	}
	
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		} catch (NumberFormatException e) {
		}
		return version;
	}


	public static ArrayList<CitySpinnerBean> getSimContacts(Context mCnt,
			String keywords) {
		ArrayList<CitySpinnerBean> list = new ArrayList<CitySpinnerBean>();
		ContentResolver resolver = mCnt.getContentResolver();
		// ��ȡSims����ϵ��
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor;
		if (keywords == null || keywords.equals("")) {
			phoneCursor = resolver.query(uri, new String[] {
					Phone.DISPLAY_NAME, Phone.NUMBER }, null, null,
					Phone.DISPLAY_NAME);
		} else {
			phoneCursor = resolver.query(Phone.CONTENT_URI, new String[] {
					Phone.DISPLAY_NAME, Phone.NUMBER }, Phone.DISPLAY_NAME
					+ " like ? or " + Phone.NUMBER + " like ?", new String[] {
					"%" + keywords + "%", "%" + keywords + "%" },
					Phone.DISPLAY_NAME + " ASC");
		}

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// �õ��ֻ�����
				String phoneNumber = phoneCursor.getString(1);
				// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				else {
					list.add(new CitySpinnerBean(phoneNumber, phoneCursor
							.getString(0)));
				}
			}
			phoneCursor.close();
		} else {
			return null;
		}
		return list;
	}

}

package com.hlkt123.uplus.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * ��Ŀ���ƣ�ChuangyunTool
 * 
 * �����ƣ�MobileState
 * 
 * �������� �ֻ��豸���飺����״̬���������͡�Sd����Sim�����ֻ����š�Sim����
 * 
 * �����ˣ�������
 * 
 * ����ʱ�䣺2013-6-25 ����11:53:07
 * 
 * �޸��ˣ������� �޸�ʱ�䣺2013-6-25 ����11:53:07
 * 
 * �޸ı�ע��
 * 
 * @version
 * 
 */
public class MobileState {

	public static final String WifiNetWork = "WIFI"; // Wifi����
	public static final String LostNetWork = "LOSTNETWORK"; // ����������
	public static final String MoblieOrCMwap = "MOBILEORCMWAP"; // CMwap CMnet����

	/**
	 * �ж��ֻ������Ƿ����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		return info.isConnected();

	}

	/**
	 * �ж��������ͣ�CMWAP CMNET WIFI ���� ���Ե� ConnectivityManager:���ӹ��������������ӣ��������ӵ�
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String isCMWAP(Context context) throws Exception {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			return LostNetWork;
		} else if (info.getTypeName().equalsIgnoreCase("wifi")) {
			return WifiNetWork;
		} else if (info.getTypeName() != null
				&& info.getTypeName().equalsIgnoreCase("mobile")
				&& info.getExtraInfo() != null
				&& info.getExtraInfo().equalsIgnoreCase("cmwap")) {
			return MoblieOrCMwap;
		}
		return LostNetWork;
	}

	/**
	 * �ж��ֻ��Ƿ����SD��
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {
		String t = android.os.Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(t);
	}

	/**
	 * �ж�sim���Ƿ���ڻ��߿���
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSimExist(Context context) {
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(Service.TELEPHONY_SERVICE);
		int simStatic = mTelephonyManager.getSimState();
		if (simStatic == TelephonyManager.SIM_STATE_READY) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ�ֻ���������
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		String retVal = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			retVal = telephonyManager.getDeviceId();
			if (retVal == null)
				retVal = "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retVal;
	}

	/**
	 * ��ȡ�ֻ�SIM�������к�
	 * 
	 * @param context
	 * @return
	 */
	public static String getSIM(Context context) {
		String retVal = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			retVal = telephonyManager.getSimSerialNumber();
			if (retVal == null)
				retVal = "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retVal;
	}

	/**
	 * ��������Ƿ����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetworking(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nwi = cm.getActiveNetworkInfo();
		if (nwi != null) {
			return nwi.isConnected();
		}
		return false;
	}

	/**
	 * ��ȡ�û���ǰ���ӳɹ�������ʹ�õ�����
	 * 
	 * @param context
	 * @return
	 */
	public static String getOpenNetType(Context context) {
		State wifiState = null;
		State mobileState = null;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			if(cm!=null)
			{
				wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState();
				mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.getState();
			}
			
			if (mobileState != null && State.CONNECTED == mobileState) {
				// �ֻ��������ӳɹ�
				return "3G";
			}
			if (wifiState != null && State.CONNECTED == wifiState) {
				// �����������ӳɹ�
				return "WIFI";
			}

			if (wifiState != null && mobileState != null
					&& State.CONNECTED != wifiState
					&& State.CONNECTED != mobileState) {
				// �ֻ�û���κε�����
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * �ж��ʺ�������Ӫ��
	 * 
	 * @param aCtx
	 * @return
	 */
	public static String whichOperator(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")) {
				return "1";// �ƶ�
			} else if (operator.equals("46001")) {
				return "2";// ��ͨ
			} else if (operator.equals("46003")) {
				return "3";// ����
			} else {
				return "1";// ����Ҳ�����Ϣ���͵����ƶ�������
			}
		}
		return null;
	}

	/**
	 * ��ȡAndroid sdk�İ汾��
	 * @return
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * ��ȡapp �İ汾��
	 * 
	 * @param context
	 * @param pkgName
	 * @return
	 */
	public static int getAppVersionCode(Context context, String pkgName) {
		int versionCode = 1;
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(pkgName, 0);
			versionCode = packageInfo.versionCode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * ��ȡ�ֻ����ֻ��ͺ�
	 * 
	 * @return
	 */
	public static String getPhoneMODEL() {

		Build bd = new Build();
		// �ֻ���Ʒ�ƣ�
		// Build.MODEL // android.os.Build.MODEL
		try {
			return bd.MODEL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ж��ֻ� �Ƿ�Ϊ�Ƚ���ҡ�ε��ֻ� Ŀǰ С�ס�����G3�Ƚ���ҡ���� ҡ������ֵ ��Ҫ����Ϊ10
	 * 
	 * @param model
	 * @return
	 */
	public static boolean hardShakeMobileModel(String model) {
		if (model == null) {
			return false;
		}
		// MI 2s,HUAWEI,SCH-I939D Ŀǰ��֪�⼸���ֻ��Ƚ���ҡ����ҡ��������ֵ��Ҫ����Ϊ 10
		if (model.startsWith("MI") || model.startsWith("SCH-I939D")) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ�ֻ���IP��ַ
	 * 
	 * @param mCnt
	 * @return
	 */
	public static String getMoibileIPaddr(Context mCnt) {
		WifiManager wifiManager = (WifiManager) mCnt
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();

			// ��ʽ��IP address�����磺��ʽ��ǰ��1828825280����ʽ����192.168.1.109
			String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
					(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
					(ipAddress >> 24 & 0xff));
			return ip;
		} else {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf
							.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							return inetAddress.getHostAddress().toString();
						}
					}
				}
			} catch (SocketException ex) {
				return "";
			}
		}

		return "";

	}

}

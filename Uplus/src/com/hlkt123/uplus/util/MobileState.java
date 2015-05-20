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
 * 项目名称：ChuangyunTool
 * 
 * 类名称：MobileState
 * 
 * 类描述： 手机设备详情：网络状态、网络类型、Sd卡、Sim卡、手机串号、Sim串号
 * 
 * 创建人：王龙能
 * 
 * 创建时间：2013-6-25 下午11:53:07
 * 
 * 修改人：王龙能 修改时间：2013-6-25 下午11:53:07
 * 
 * 修改备注：
 * 
 * @version
 * 
 */
public class MobileState {

	public static final String WifiNetWork = "WIFI"; // Wifi网络
	public static final String LostNetWork = "LOSTNETWORK"; // 无网络类型
	public static final String MoblieOrCMwap = "MOBILEORCMWAP"; // CMwap CMnet网络

	/**
	 * 判断手机网络是否存在
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
	 * 判断网络类型，CMWAP CMNET WIFI 蓝牙 电脑等 ConnectivityManager:连接管理器。网络连接，蓝牙连接等
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
	 * 判断手机是否存在SD卡
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {
		String t = android.os.Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(t);
	}

	/**
	 * 判断sim卡是否存在或者可用
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
	 * 获取手机机器串号
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
	 * 获取手机SIM卡的序列号
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
	 * 检查网络是否可用
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
	 * 获取用户当前连接成功并正在使用的网络
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
				// 手机网络连接成功
				return "3G";
			}
			if (wifiState != null && State.CONNECTED == wifiState) {
				// 无线网络连接成功
				return "WIFI";
			}

			if (wifiState != null && mobileState != null
					&& State.CONNECTED != wifiState
					&& State.CONNECTED != mobileState) {
				// 手机没有任何的网络
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 判断适合网络运营商
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
				return "1";// 移动
			} else if (operator.equals("46001")) {
				return "2";// 联通
			} else if (operator.equals("46003")) {
				return "3";// 电信
			} else {
				return "1";// 如果找不到信息，就当作移动来处理
			}
		}
		return null;
	}

	/**
	 * 获取Android sdk的版本号
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
	 * 获取app 的版本号
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
	 * 获取手机的手机型号
	 * 
	 * @return
	 */
	public static String getPhoneMODEL() {

		Build bd = new Build();
		// 手机的品牌：
		// Build.MODEL // android.os.Build.MODEL
		try {
			return bd.MODEL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断手机 是否为比较难摇晃的手机 目前 小米、三星G3比较难摇动， 摇动下限值 需要设置为10
	 * 
	 * @param model
	 * @return
	 */
	public static boolean hardShakeMobileModel(String model) {
		if (model == null) {
			return false;
		}
		// MI 2s,HUAWEI,SCH-I939D 目前已知这几款手机比较难摇动，摇动的下限值需要设置为 10
		if (model.startsWith("MI") || model.startsWith("SCH-I939D")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取手机的IP地址
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

			// 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
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

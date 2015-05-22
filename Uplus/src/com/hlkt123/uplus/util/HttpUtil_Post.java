package com.hlkt123.uplus.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.util.Log;

/**
 * 
 * @author lyy
 * @date 2014-03-26
 * @Fun http ����İ�����
 * 
 */
public class HttpUtil_Post {

	private String urlStr;
	private String charSet;

	/**
	 * ���캯��
	 * 
	 * @param _url
	 *            �����url��ַ
	 * @param _charSet
	 *            �������Ӧ�����ʽ
	 */
	public HttpUtil_Post(String _url, String _charSet) {
		this.urlStr = _url;
		this.charSet = _charSet;
	}

	/**
	 * post ��ʽ����������
	 * 
	 * @param params String ���͵������������ URLencode�������
	 *  args1=value1&args2=value2...
	 *    
	 */
	public String post(String params) {
		String result = null;
		BufferedReader reader = null;
		HttpURLConnection conn=null;
		try {
			URL url = new URL(urlStr);
			conn= (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=" + charSet);
			conn.setConnectTimeout(5* 1000);

			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			
			out.writeBytes(params);
			out.flush();
			out.close();

			// ��ȡ��Ӧ
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuffer sb = new StringBuffer("");
			String line;
			while ((line = reader.readLine()) != null) {
				line = new String(line.getBytes(), charSet);
				sb.append(line);
			}
			//Log.i("Action:"+urlStr.substring(urlStr.lastIndexOf("/")+1),"Web Ret:"+sb);
			result = sb.toString();
			reader.close();
			conn.disconnect();
			conn = null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null)
			{
				conn.disconnect();
				conn=null;
			}
		}

		return result;
	}

	/**
	 * �Դ���������� URLencode ת��
	 * 
	 * @param map
	 */
	public static String paramsURLencode(Map<String, Object> map, String charSet) {
		StringBuilder params=new StringBuilder();
		if (map == null) {
			return "";
		}

		for (String key : map.keySet()) {
			try {
				if(map.get(key)!=null)
				{
					params.append(key).append("=") 
						.append(java.net.URLEncoder.encode(map.get(key).toString(),
									charSet)).append("&");
				}
				else
				{
					params.append(key).append("=") 
					.append(java.net.URLEncoder.encode("",charSet)).append("&");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ret=params.toString();
		if (!ret.equals("")) {
			ret=params.substring(0, params.length() - 1);
		}
		return ret;
	}

	/**
	 *
	 * post �����������URL ת�ƣ� ת�ƺ�ò���ĩ�� ��һ��"&" ���ţ�ע�⴦��
	 * ��Ҫ��������Դ�ύͼƬʱר��
	 * @param map
	 */
	public static StringBuilder paramsURLencode_sb(Map<String, Object> map, String charSet) {
		StringBuilder params=new StringBuilder();
		if (map == null) {
			return params;
		}

		for (String key : map.keySet()) {
			try {
				if(map.get(key)!=null)
				{
					params.append(key).append("=") 
						.append(java.net.URLEncoder.encode(map.get(key).toString(),
									charSet)).append("&");
				}
				else
				{
					params.append(key).append("=") 
					.append(java.net.URLEncoder.encode("",charSet)).append("&");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return params;
	}
	
}

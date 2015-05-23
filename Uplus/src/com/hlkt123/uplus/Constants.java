package com.hlkt123.uplus;

import android.os.Environment;

/**
 * ϵͳ�����ļ�
 * @author Dkalan
 * @Date   2015-05-19
 * 
 */
public class Constants {
	
	
	
	/**
	 * ģʽ������־��test:true��release:false�������־
	 */
	public static final boolean IS_DEBUG = true;
	
	//������־�ļ�����·��
	public static String LOG_PATH=Environment
			.getExternalStorageDirectory() + "/uplus/errorlog/";
	
	/**
	 * app����������ܴ�����־�������ַ
	 */
	public final static String EXCEPTION_EXIT_EMAILTO_ADDR = "liuyiyuan@hlkt123.com ";
	
	/**
	 * <p>
	 * SUCC: �ӿ�1�������ɹ���
	 * <p>
	 * SUCC_INTERFACE_2 :�ӿ�2 �������ɹ���
	 * <p>
	 * SUCC_INTERFACE_3 :�ӿ�3�������ɹ���
	 * <p>
	 * GET_P1_SUCC :listview ��ҳ�ӿڻ�ȡ��һҳ���ݳɹ���
	 * <p>
	 * GET_PN_SUCC: listView ��ҳ�ӿڻ�ȡ��Nҳ���ݳɹ���N>1;
	 * <p>
	 * RELOGIN:��¼���ڣ���Ҫ���µ�¼��
	 * <p>
	 * NO_REC: ���ؼ�¼Ϊ0������б��ҳ��
	 * <p>
	 * EXCEPTION :���������쳣��
	 * <p>
	 * NET_ERR: �������
	 * <p>
	 * OTHERS : ����δ֪���͵���Ϣ��
	 * </P>
	 */
	public static final int MSG_WHAT_SUCC = 4000, 
			MSG_WHAT_SUCC_INTERFACE_2 = 4001,
			MSG_WHAT_SUCC_INTERFACE_3 = 4002,
			MSG_WHAT_GET_P1_SUCC =4003,
			MSG_WHAT_GET_PN_SUCC =4004,
			MSG_WHAT_RELOGIN = 4005, 
			MSG_WHAT_NO_REC= 4006,
			MSG_WHAT_EXCEPTION = 4007,
			MSG_WHAT_NET_ERR= 4008,
			MSG_WHAT_OTHERS = 4099;

}

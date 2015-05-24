package com.hlkt123.uplus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Commone Handler to deal web return data; User need to realize
 * dealSucc,dealChangePageSucc methods; The class will deal net
 * error,relogin,sorry,no record,change page and so on error code
 * 
 * @author liu yiyuan
 * @date 2014-7-18
 * @email lyy@wazert.com
 * @company zhejiang wazert technology company
 */
public abstract class UplusHandler extends Handler {

	private Context mCnt;
	private ProgressDialog dig;
	private String remindKey;

	/**
	 * Commone Handler to deal web return data; User need to realize
	 * dealSucc,dealChangePageSucc methods; The class will deal net
	 * error,relogin,sorry,exist and so on error code
	 * 
	 * @param _mCnt
	 *            Activity Context
	 * @param _dig
	 *            remind diglog
	 * @param remindKey
	 *            main action keywords,like "����"������Դ����
	 */
	public UplusHandler(Context _mCnt, ProgressDialog _dig) {
		mCnt = _mCnt;
		dig = _dig;
	}

	/**
	 * <p>
	 * user realize method, deal web return datas
	 * <p>
	 * 
	 * @param msg
	 *            handler message
	 */
	public abstract void succ(Message msg);

	/**
	 * �ӿ�2 �������ݳɹ�
	 * 
	 * @param msg
	 */
	public abstract void succ2(Message msg);

	/**
	 * �ӿ�3 �������ݳɹ�
	 * 
	 * @param msg
	 */
	public abstract void succ3(Message msg);

	/**
	 * <p>
	 * user realize method, deal change page and get web return datas
	 * <p>
	 * 
	 * @param msg
	 *            handler message
	 */
	public abstract void succ_p1(Message msg);

	/**
	 * ��ȡ��Nҳ��¼�ɹ�
	 * 
	 * @param msg
	 */
	public abstract void succ_pn(Message msg);

	/**
	 * <p>
	 * user realize method, deal other message ,which not in Constants class
	 * memo
	 * <p>
	 * 
	 * @param msg
	 *            handler message
	 */
	public abstract void otherBis(Message msg);

	public void handleMessage(Message msg) {
		if (dig != null) {
			dig.dismiss();
		}
		switch (msg.what) {
		case Constants.MSG_WHAT_RELOGIN: {
			Toast.makeText(mCnt, "��¼����", Toast.LENGTH_SHORT).show();

			break;
		}
		case Constants.MSG_WHAT_NO_REC: {
			Toast.makeText(mCnt, "���޼�¼", Toast.LENGTH_SHORT).show();
			break;
		}
		case Constants.MSG_WHAT_EXCEPTION: {
			Toast.makeText(mCnt, "��������æ���Ժ�����", Toast.LENGTH_SHORT).show();
			break;
		}
		case Constants.MSG_WHAT_NET_ERR: {
			Toast.makeText(mCnt, "���粻����", Toast.LENGTH_SHORT).show();
			break;
		}

		case Constants.MSG_WHAT_SUCC: {
			succ(msg);
			break;
		}
		case Constants.MSG_WHAT_SUCC_INTERFACE_2: {
			succ2(msg);
			break;
		}
		case Constants.MSG_WHAT_SUCC_INTERFACE_3: {
			succ3(msg);
			break;
		}
		case Constants.MSG_WHAT_GET_P1_SUCC: {
			succ_p1(msg);
			break;
		}
		case Constants.MSG_WHAT_GET_PN_SUCC: {
			succ_pn(msg);
			break;
		}
		default: {
			otherBis(msg);
			break;
		}
		}
	}

}

package com.hlkt123.uplus.model;


/**
 * ���ػ��� �û����õ�������Ϣ��ÿ����ౣ��10��
 * @author   liu yiyuan
 * @date     2014-8-15
 * @email    lyy@wazert.com
 * @company  zhejiang wazert technology company
 */
public class InputModel {

	public int _id;       //sqlite���е���ˮ��
	public int type;          // 1 ��Դ���ƣ� 2 �û����û�����3  �ջ���ַ���߷�����ַ
	public String content;    //������Ϣ����
	
	public InputModel(int _type,String _cnt)
	{
		type=_type;
		content=_cnt;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}

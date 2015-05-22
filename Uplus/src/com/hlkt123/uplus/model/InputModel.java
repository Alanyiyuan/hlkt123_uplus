package com.hlkt123.uplus.model;


/**
 * 本地缓存 用户常用的输入信息，每类最多保留10条
 * @author   liu yiyuan
 * @date     2014-8-15
 * @email    lyy@wazert.com
 * @company  zhejiang wazert technology company
 */
public class InputModel {

	public int _id;       //sqlite库中的流水号
	public int type;          // 1 货源名称， 2 用户的用户名，3  收货地址或者发货地址
	public String content;    //缓存消息内容
	
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

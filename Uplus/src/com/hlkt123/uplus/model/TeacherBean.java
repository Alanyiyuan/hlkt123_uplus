package com.hlkt123.uplus.model;

/**
 * ��ʦ��Ϣģ���ļ�
 * @author liuyiyuan
 * @date 2015-5-23
 * @fun  TODO
 */
public class TeacherBean {

	private String name;  //����
	private int teachAge;   //����
	private String remarkPercent;  //�����ʣ�90%��
	private String logoUrl;  //��ʦͷ���ַ
	private int userLv;       //��ʦ�ȼ�
	private String userLvStr;  //��ʦ�ȼ�����
	private int studyCount;    //�Ϲ��γ̵�����
	private float fee;    // ��ʱ����
	private float dis;    //��ʦ��ͥ���û���ǰλ�õľ���
	private String feature1,feature2;  //��ʦ����1����ʦ����2
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTeachAge() {
		return teachAge;
	}
	public void setTeachAge(int teachAge) {
		this.teachAge = teachAge;
	}
	public String getRemarkPercent() {
		return remarkPercent;
	}
	public void setRemarkPercent(String remarkPercent) {
		this.remarkPercent = remarkPercent;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public int getUserLv() {
		return userLv;
	}
	public void setUserLv(int userLv) {
		this.userLv = userLv;
	}
	public String getUserLvStr() {
		return userLvStr;
	}
	public void setUserLvStr(String userLvStr) {
		this.userLvStr = userLvStr;
	}
	public int getStudyCount() {
		return studyCount;
	}
	public void setStudyCount(int studyCount) {
		this.studyCount = studyCount;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	public float getDis() {
		return dis;
	}
	public void setDis(float dis) {
		this.dis = dis;
	}
	public String getFeature1() {
		return feature1;
	}
	public void setFeature1(String feature1) {
		this.feature1 = feature1;
	}
	public String getFeature2() {
		return feature2;
	}
	public void setFeature2(String feature2) {
		this.feature2 = feature2;
	}
	
	
}

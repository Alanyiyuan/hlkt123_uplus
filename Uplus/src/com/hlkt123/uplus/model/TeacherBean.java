package com.hlkt123.uplus.model;

/**
 * 教师信息模型文件
 * @author liuyiyuan
 * @date 2015-5-23
 * @fun  TODO
 */
public class TeacherBean {

	private String name;  //名字
	private int teachAge;   //教龄
	private String remarkPercent;  //好评率，90%等
	private String logoUrl;  //教师头像地址
	private int userLv;       //教师等级
	private String userLvStr;  //教师等级名称
	private int studyCount;    //上过课程的人数
	private float fee;    // 课时费用
	private float dis;    //教师家庭到用户当前位置的距离
	private String feature1,feature2;  //教师特征1，教师特征2
	
	
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

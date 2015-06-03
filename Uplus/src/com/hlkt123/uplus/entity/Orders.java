package com.hlkt123.uplus.entity;

import java.util.ArrayList;

public class Orders {
	private String teacher_name;
	private ArrayList<ClassDetail> classDetails;
	private String num;
	private String pay;
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public ArrayList<ClassDetail> getClassDetails() {
		return classDetails;
	}
	public void setClassDetails(ArrayList<ClassDetail> classDetails) {
		this.classDetails = classDetails;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	
	
}

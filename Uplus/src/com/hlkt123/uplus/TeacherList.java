package com.hlkt123.uplus;

import java.util.ArrayList;
import java.util.List;

import com.hlkt123.uplus.adapter.TeachListAdp;
import com.hlkt123.uplus.model.TeacherBean;

import android.os.Bundle;
import android.widget.ListView;

public class TeacherList extends BaseActivity {

	private ListView listView;
	private List<TeacherBean> list; //��ʦ�����б�
	
	private TeachListAdp adp=null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_list);
		findView();
		initData();
		adp=new TeachListAdp(this, list);
		listView.setAdapter(adp);
	}
	
	private void findView()
	{
		listView=(ListView)findViewById(R.id.listView);
	}
	
	private void initData()
	{
		list=new ArrayList<TeacherBean>();
		TeacherBean tb=new TeacherBean();
		tb.setName("����Բ");
		tb.setDis(2);
		tb.setFeature1("�Ͱ�");
		tb.setFeature2("ѧʶԨ��");
		tb.setFee(159f);
		tb.setRemarkPercent("96%");
		tb.setStudyCount(208);
		tb.setUserLvStr("�м���ʦ");
		tb.setUserLv(2);
		list.add(tb);
		list.add(tb);
		list.add(tb);
		list.add(tb);
		list.add(tb);
	}
}

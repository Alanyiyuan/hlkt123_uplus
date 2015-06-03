package com.hlkt123.uplus.view;

import java.util.ArrayList;

import com.hlkt123.uplus.MyOrderActivity;
import com.hlkt123.uplus.R;
import com.hlkt123.uplus.entity.ClassDetail;
import com.hlkt123.uplus.entity.Orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Order2ConfirmFragment extends Fragment {
	
	private View rootView;
	private ArrayList<Orders> orderList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		orderList = new ArrayList<Orders>();
		getData();
		
	}
	
	private void getData() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 5; i++) {
			Orders orders = new Orders();
			orders.setNum("num_" + i);
			orders.setPay("pay_" + i);
			orders.setTeacher_name("teacher_name_" + i);
			ArrayList<ClassDetail> classDetailList = new ArrayList<ClassDetail>();
			for(int j = 1; j < i+2 ; j++){
				ClassDetail classDetail = new ClassDetail();
				classDetail.setAddress("address_" + j);
				classDetail.setClassImg(R.drawable.temp_icon);
				classDetail.setName("name_" + j);
				classDetail.setPrice("price" + j);
				classDetail.setTime("time_" + j);
				classDetailList.add(classDetail);
			}
			orders.setClassDetails(classDetailList);
			orderList.add(orders);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.order2confirm, null);
		LinearLayout scrollView = (LinearLayout)rootView.findViewById(R.id.order2confirm_scroll);
		for(int i = 0; i < orderList.size(); i++) {
			ClassDetailView cdv = new ClassDetailView(MyOrderActivity.homeActivityInstance, orderList.get(i));
			
		}
		return inflater.inflate(R.layout.order_tab_2confirm, container, false);
	}
}

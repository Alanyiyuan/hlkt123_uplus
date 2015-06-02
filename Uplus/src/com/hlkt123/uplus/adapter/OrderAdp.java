package com.hlkt123.uplus.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hlkt123.uplus.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdp extends BaseAdapter{

	private LayoutInflater mInflater;
	
	View[] itemViews;
	
	ViewHolder holder;
	
	List<String> name_List = new ArrayList<String>();
	List<String> time_List = new ArrayList<String>();
	List<String> address_List = new ArrayList<String>();
	List<String> price_List = new ArrayList<String>();
	List<String> num_List = new ArrayList<String>();
	List<String> reduce_List = new ArrayList<String>();
	List<String> pay_List = new ArrayList<String>();
	List<Integer> img_List = new ArrayList<Integer>();
	
	public OrderAdp(Context context, String[] nameList, String[] timeList, String[] addressList, String[] priceList,
			String[] numList, String[] reduceList, String[] payList, int[] imgList){
		this.mInflater = LayoutInflater.from(context);
		for(String name : nameList){
			name_List.add(name);
		}
		
		for(String time : timeList){
			time_List.add(time);
		}
		
		for(String address : addressList){
			address_List.add(address);
		}
		
		for(String price : priceList){
			price_List.add(price);
		}
		
		for(String num : numList){
			num_List.add(num);
		}
		
		for(String reduce : reduceList){
			reduce_List.add(reduce);
		}
		
		for(String pay : payList){
			pay_List.add(pay);
		}
		
		for(int img : imgList){
			img_List.add(img);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name_List.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			
			convertView = mInflater.inflate(R.layout.order_item, null);
			holder.order_tv_name = (TextView) convertView.findViewById(R.id.order_tv_name);
			holder.order_tv_time = (TextView) convertView.findViewById(R.id.order_tv_time);
			holder.order_tv_address = (TextView) convertView.findViewById(R.id.order_tv_address);
			holder.order_tv_price = (TextView) convertView.findViewById(R.id.order_tv_price);
			holder.order_tv_num = (TextView) convertView.findViewById(R.id.order_tv_num);
			holder.order_tv_reduce = (TextView) convertView.findViewById(R.id.order_tv_reduce);
			holder.order_tv_pay = (TextView) convertView.findViewById(R.id.order_tv_pay);
			holder.order_img = (ImageView) convertView.findViewById(R.id.order_img);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
			holder.order_img.setBackgroundResource(R.drawable.temp_icon);
		}
		
		holder.order_tv_name.setText(name_List.get(position));
		holder.order_tv_time.setText(time_List.get(position));
		holder.order_tv_address.setText(address_List.get(position));
		holder.order_tv_price.setText(price_List.get(position));
		holder.order_tv_num.setText(num_List.get(position));
		holder.order_tv_reduce.setText(reduce_List.get(position));
		holder.order_tv_pay.setText(reduce_List.get(position));
		holder.order_img.setImageResource(img_List.get(position));
		return convertView;
	}
	
	public final class ViewHolder{
		public View order_view;
		public TextView order_tv_name;
		public TextView order_tv_time;
		public TextView order_tv_address;
		public TextView order_tv_price;
		public TextView order_tv_num;
		public TextView order_tv_reduce;
		public TextView order_tv_pay;
		public ImageView order_img;
	}

}

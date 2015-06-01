package com.hlkt123.uplus;

import java.util.ArrayList;
import java.util.List;

import com.hlkt123.uplus.R.drawable;
import com.hlkt123.uplus.adapter.OrderAdp;
import com.hlkt123.uplus.util.ToastUtil;
import com.hlkt123.uplus.view.PickerView;
import com.hlkt123.uplus.view.PickerView.onSelectListener;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends BaseActivity implements OnClickListener{
	
	private PickerView pv;
	private ListView order_listView;
	private String[] nameList;
	private String[] timeList;
	private String[] addressList;
	private String[] priceList;
	private String[] numList;
	private String[] reduceList;
	private String[] payList;
	private int[] imgList;
	private View linearLayout_view;
	private View frameLayout_view;
	private Button cancel_pay;
	private Button pay;
	private Button finish;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_activity);
		
		//
		
		TextView title = (TextView) findViewById(R.id.include_header);
		title.setText("订单详情");
		
		nameList = new String[2];
		timeList = new String[2];
		addressList = new String[2];
		priceList = new String[2];
		numList = new String[2];
		reduceList = new String[2];
		payList = new String[2];
		imgList = new int[2];
		
		
		for(int i = 0; i <= 1 ; i++){
			nameList[i] = "name_" + i;
			timeList[i] = "time_" + i;
			addressList[i] = "address_" + i;
			priceList[i] = "price_" + i;
			numList[i] = "num_" + i;
			reduceList[i] = "reduce_" + i;
			payList[i] = "pay_" + i;
			imgList[i] = R.drawable.temp_icon;
		}
		
		order_listView = (ListView) findViewById(R.id.order_listView);
		order_listView.setDividerHeight(0);
		order_listView.setAdapter(new OrderAdp(this, nameList, timeList, addressList, priceList, numList, reduceList, payList, imgList));
		setListViewHeight(order_listView);
		
		pv = (PickerView) findViewById(R.id.pv);
		List<String> data = new ArrayList<String>();
		data.add("拍错/多拍/不想要");
		data.add("教师未确认课程");
		data.add("协商一致退款");
		data.add("其他");
		
		pv.setData(data);
		pv.setOnSelectListener(new onSelectListener() {
			
			@Override
			public void onSelect(String text) {
				// TODO Auto-generated method stub
				ToastUtil.disToastLong(getApplicationContext(), text);
			}
		});
		
		linearLayout_view = findViewById(R.id.pv_layout);
		frameLayout_view = findViewById(R.id.framelayout);
		
		cancel_pay = (Button) findViewById(R.id.cancel);
		pay = (Button) findViewById(R.id.confirm);
		finish = (Button) findViewById(R.id.finish);
		
		back = (ImageView) findViewById(R.id.title_back_img);
		
		frameLayout_view.setOnClickListener(this);
		cancel_pay.setOnClickListener(this);
		pay.setOnClickListener(this);
		finish.setOnClickListener(this);
		back.setOnClickListener(this);
	}
	
	public void setListViewHeight(ListView listView){
		ListAdapter listAdapter = listView.getAdapter();
		
		if(listAdapter == null) {
			return;
		}
		
		int totalHeight = 0;
		for(int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.framelayout:
			linearLayout_view.setVisibility(View.GONE);
			frameLayout_view.setVisibility(View.GONE);
			break;
		case R.id.cancel:
			linearLayout_view.setVisibility(View.VISIBLE);
			frameLayout_view.setVisibility(View.VISIBLE);
			break;
		case R.id.finish:
			linearLayout_view.setVisibility(View.GONE);
			frameLayout_view.setVisibility(View.GONE);
			break;
		case R.id.title_back_img:
			finish();
			break;
		default:
			break;
		}
	}
}

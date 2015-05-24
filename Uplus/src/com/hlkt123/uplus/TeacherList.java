package com.hlkt123.uplus;

import java.util.ArrayList;
import java.util.List;

import com.hlkt123.uplus.adapter.TeachListAdp;
import com.hlkt123.uplus.model.TeacherBean;
import com.hlkt123.uplus.util.ToastUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class TeacherList extends BaseActivity {

	private ListView listView;
	private List<TeacherBean> list; // 教师数据列表

	private TeachListAdp adp = null;

	private int pageIndex = 1; // 当前页码记录
	private final static int PAGESIZE = 6;// 每页记录数

	/**
	 * listview 页脚变量
	 */
	private View footerView = null;
	private ProgressBar footerPB = null;
	private TextView footerTV = null;

	private boolean hasMore = true; // 是否有更多数据
	private boolean isLoading = false; // 是否正在加载数据,默认false

	private UplusHandler mHandler = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_list);
		findView();
		initFooter();
		initHandler();
		mHandler.sendEmptyMessage(Constants.MSG_WHAT_GET_P1_SUCC);
	}

	/**
	 * 初始化handler
	 */
	private void initHandler() {
		mHandler = new UplusHandler(this, null) {

			@Override
			public void succ(Message msg) {
				// TODO Auto-generated method stub

			}

			@Override
			public void succ2(Message msg) {
				// TODO Auto-generated method stub

			}

			@Override
			public void succ3(Message msg) {
				// TODO Auto-generated method stub

			}

			@Override
			public void succ_p1(Message msg) {
				// TODO Auto-generated method stub
				initData();
				if (list != null && list.size() > 0) {
					if (list.size() >= PAGESIZE) {
						listView.addFooterView(footerView);
					}
					adp = new TeachListAdp(TeacherList.this, list);
					listView.setAdapter(adp);
					listView.setOnScrollListener(new MyOnScrollListener());
				} else {
					listView.setAdapter(null);
					ToastUtil.disToastShort(TeacherList.this, "暂无教师");
				}
			}

			@Override
			public void succ_pn(Message msg) {
				// TODO Auto-generated method stub
				// 翻页成功后，更新UI
				isLoading=false;
				List<TeacherBean> more=addData();
				if(more!=null&&more.size()>0)
				{
					if(more.size()<PAGESIZE)
					{
						footerPB.setVisibility(View.GONE);
						footerTV.setText("没有更多");
						adp.addItems(more);
						adp.notifyDataSetChanged();
						hasMore=false;
					}
				}
				else
				{
					hasMore=false;
					footerPB.setVisibility(View.GONE);
					footerTV.setText("没有更多");
				}
			}

			@Override
			public void otherBis(Message msg) {
				// TODO Auto-generated method stub

			}

			
		};
	}

	
	private void findView() {
		listView = (ListView) findViewById(R.id.listView);
	}

	// 初始化页脚
	private void initFooter() {
		footerView = LayoutInflater.from(this).inflate(
				R.layout.loading_bar_listview, null);
		footerPB = (ProgressBar) footerView.findViewById(R.id.progressBar);
		footerTV = (TextView) footerView.findViewById(R.id.text);
	}

	private void initData() {
		list = new ArrayList<TeacherBean>();
		TeacherBean tb = new TeacherBean();
		tb.setName("刘义圆");
		tb.setDis(2);
		tb.setFeature1("和蔼");
		tb.setFeature2("学识渊博");
		tb.setFee(159f);
		tb.setRemarkPercent("96%");
		tb.setStudyCount(208);
		tb.setUserLvStr("中级名师");
		tb.setUserLv(2);
		list.add(tb);
		list.add(tb);
		list.add(tb);
		list.add(tb);
		list.add(tb);
		list.add(tb);
	}

	//add datas to list
	private ArrayList<TeacherBean> addData() {
		ArrayList<TeacherBean> more = new ArrayList<TeacherBean>();
		TeacherBean tb = new TeacherBean();
		tb.setName("吴晓晓");
		tb.setDis(2);
		tb.setFeature1("甜美可爱");
		tb.setFeature2("学识渊博");
		tb.setFee(159f);
		tb.setRemarkPercent("92%");
		tb.setStudyCount(208);
		tb.setUserLvStr("中级名师");
		tb.setUserLv(2);
		more.add(tb);
		more.add(tb);
		return more;
	}

	/***
	 * listView滚动监听器
	 */
	class MyOnScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView arg0, int firstVisItem,
				int visItemCount, int totalItemCount) {
			// TODO Auto-generated method stub

			if (firstVisItem + visItemCount == totalItemCount
					&& hasMore) {
				if (!isLoading) {
					isLoading = true;
					// 显示底部进度框，两个tab中的都会显示出来
					footerPB.setVisibility(View.VISIBLE);
					footerTV.setText("加载中...");

					mHandler.sendEmptyMessageDelayed(
							Constants.MSG_WHAT_GET_PN_SUCC, 1000);
				}
			}

		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	};
}

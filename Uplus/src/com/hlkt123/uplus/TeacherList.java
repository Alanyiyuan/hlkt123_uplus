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
	private List<TeacherBean> list; // ��ʦ�����б�

	private TeachListAdp adp = null;

	private int pageIndex = 1; // ��ǰҳ���¼
	private final static int PAGESIZE = 6;// ÿҳ��¼��

	/**
	 * listview ҳ�ű���
	 */
	private View footerView = null;
	private ProgressBar footerPB = null;
	private TextView footerTV = null;

	private boolean hasMore = true; // �Ƿ��и�������
	private boolean isLoading = false; // �Ƿ����ڼ�������,Ĭ��false

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
	 * ��ʼ��handler
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
					ToastUtil.disToastShort(TeacherList.this, "���޽�ʦ");
				}
			}

			@Override
			public void succ_pn(Message msg) {
				// TODO Auto-generated method stub
				// ��ҳ�ɹ��󣬸���UI
				isLoading=false;
				List<TeacherBean> more=addData();
				if(more!=null&&more.size()>0)
				{
					if(more.size()<PAGESIZE)
					{
						footerPB.setVisibility(View.GONE);
						footerTV.setText("û�и���");
						adp.addItems(more);
						adp.notifyDataSetChanged();
						hasMore=false;
					}
				}
				else
				{
					hasMore=false;
					footerPB.setVisibility(View.GONE);
					footerTV.setText("û�и���");
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

	// ��ʼ��ҳ��
	private void initFooter() {
		footerView = LayoutInflater.from(this).inflate(
				R.layout.loading_bar_listview, null);
		footerPB = (ProgressBar) footerView.findViewById(R.id.progressBar);
		footerTV = (TextView) footerView.findViewById(R.id.text);
	}

	private void initData() {
		list = new ArrayList<TeacherBean>();
		TeacherBean tb = new TeacherBean();
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
		list.add(tb);
	}

	//add datas to list
	private ArrayList<TeacherBean> addData() {
		ArrayList<TeacherBean> more = new ArrayList<TeacherBean>();
		TeacherBean tb = new TeacherBean();
		tb.setName("������");
		tb.setDis(2);
		tb.setFeature1("�����ɰ�");
		tb.setFeature2("ѧʶԨ��");
		tb.setFee(159f);
		tb.setRemarkPercent("92%");
		tb.setStudyCount(208);
		tb.setUserLvStr("�м���ʦ");
		tb.setUserLv(2);
		more.add(tb);
		more.add(tb);
		return more;
	}

	/***
	 * listView����������
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
					// ��ʾ�ײ����ȿ�����tab�еĶ�����ʾ����
					footerPB.setVisibility(View.VISIBLE);
					footerTV.setText("������...");

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

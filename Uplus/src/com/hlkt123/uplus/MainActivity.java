package com.hlkt123.uplus;

import java.util.ArrayList;

import com.hlkt123.uplus.adapter.IndexViewPageAdp;
import com.hlkt123.uplus.listener.IndexViewPageChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager indexVP;
	// tab ��ҳ��ͼ
	private View tab0, tab1, tab2;
	private ArrayList<View> views;
	/**
	 * �ײ������˵�LL��ͼ
	 */
	private LinearLayout navLL0, navLL1, navLL2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_view_pager);
		findViewPageCompent();
	}

	/**
	 * ����viewpageҳ������
	 */
	public void findViewPageCompent() {
		indexVP = (ViewPager) findViewById(R.id.vPager);
		navLL0 = (LinearLayout) findViewById(R.id.navLL0);
		navLL1 = (LinearLayout) findViewById(R.id.navLL1);
		navLL2 = (LinearLayout) findViewById(R.id.navLL2);

		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		tab0 = inflater.inflate(R.layout.index_tab0, null);
		tab1 = inflater.inflate(R.layout.index_tab1, null);
		tab2 = inflater.inflate(R.layout.index_tab2, null);
		views.add(tab0);
		views.add(tab1);
		views.add(tab2);

		indexVP.setAdapter(new IndexViewPageAdp(views));
		indexVP.setCurrentItem(0);
		
		//����tabҳ���л�������
		indexVP.setOnPageChangeListener(new IndexViewPageChangeListener(
				MainActivity.this, navLL0, navLL1, navLL2));
		setLis();
		initTitle("��ҳ","�γ̱�","��");
	}
	
	/**
	 * ��ʼ����������
	 * @param title0
	 * @param title1
	 * @param title2
	 */
	private void initTitle(String title0,String title1,String title2)
	{
		TextView titleTV0=(TextView)tab0.findViewById(R.id.titleTV);
		TextView titleTV1=(TextView)tab1.findViewById(R.id.titleTV);
		TextView titleTV2=(TextView)tab2.findViewById(R.id.titleTV);
		titleTV0.setText(title0);
		titleTV1.setText(title1);
		titleTV2.setText(title2);
	}
	
	//menu ����¼�
	private void setLis()
	{
		navLL0.setOnClickListener(new MenuClickListener(0));
		navLL1.setOnClickListener(new MenuClickListener(1));
		navLL2.setOnClickListener(new MenuClickListener(2));
	}

	/**
	 * �ײ�menu �����˵�����¼�
	 */
	private class MenuClickListener implements OnClickListener {
		// viewPager��ǰѡ�е�ҳ�����
		private int index = 0;

		public MenuClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			indexVP.setCurrentItem(index);
		}

	}

}

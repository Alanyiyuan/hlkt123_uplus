package com.hlkt123.uplus.adapter;

import java.util.List;

import com.hlkt123.uplus.R;
import com.hlkt123.uplus.adapter.FunGudeAdapter.ViewHolder;
import com.hlkt123.uplus.model.TeacherBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 教师信息列表适配器
 * 
 * @author liuyiyuan
 * @date 2015-5-23
 * @fun TODO
 */
public class TeachListAdp extends BaseAdapter {

	private List<TeacherBean> list;
	private Context mCnt;
	private LayoutInflater mInflater;

	public TeachListAdp(Context _cnt, List<TeacherBean> _list) {
		list = _list;
		mCnt = _cnt;
		this.mInflater = LayoutInflater.from(_cnt);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.teacher_list_item, null);
			holder.logo = (ImageView) convertView.findViewById(R.id.logoIV);
			holder.lvIV0 = (ImageView) convertView.findViewById(R.id.lvIV0);
			holder.lvIV1 = (ImageView) convertView.findViewById(R.id.lvIV1);
			holder.lvIV2 = (ImageView) convertView.findViewById(R.id.lvIV2);
			holder.lvIV3 = (ImageView) convertView.findViewById(R.id.lvIV3);

			holder.name = (TextView) convertView.findViewById(R.id.nameTV);
			holder.remarkPercent = (TextView) convertView
					.findViewById(R.id.remarkTV);
			holder.lvStr = (TextView) convertView.findViewById(R.id.lvStrTV);

			holder.dis = (TextView) convertView.findViewById(R.id.disTV);
			holder.studyCount = (TextView) convertView
					.findViewById(R.id.studyCountTV);
			holder.feature1 = (TextView) convertView
					.findViewById(R.id.feature1TV);
			holder.feature2 = (TextView) convertView
					.findViewById(R.id.feature2TV);
			holder.fee = (TextView) convertView.findViewById(R.id.feeTV);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(list.get(position).getName());
		holder.remarkPercent.setText("好评"
				+ list.get(position).getRemarkPercent());
		holder.lvStr.setText(list.get(position).getUserLvStr());
		holder.dis.setText("<" + list.get(position).getDis() + "km");
		holder.studyCount.setText(list.get(position).getStudyCount()+"");
		holder.feature1.setText(list.get(position).getFeature1());
		holder.feature2.setText(list.get(position).getFeature2());
		holder.fee.setText(list.get(position).getFee() + "");

		return convertView;
	}

	class ViewHolder {
		private ImageView logo; // 头像
		private ImageView lvIV0, lvIV1, lvIV2, lvIV3; // 等级皇冠图标
		/**
		 * 名字，好评率，等级名称，距离，交出的学生人数，特征1，特征2，课时费用
		 */
		private TextView name, remarkPercent, lvStr, dis, studyCount, feature1,
				feature2, fee;

	}
}

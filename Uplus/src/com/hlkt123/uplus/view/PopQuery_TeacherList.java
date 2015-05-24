package com.hlkt123.uplus.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hlkt123.uplus.R;
import com.hlkt123.uplus.model.CitySpinnerBean;

public class PopQuery_TeacherList {
	private Context context;
	private PopupWindow popupWindow;
	private ListView listView;
	private List<CitySpinnerBean> conditionList;

	// private OnItemClickListener listener;

	public PopQuery_TeacherList(Context context,
			List<CitySpinnerBean> _conditions) {
		// TODO Auto-generated constructor stub
		this.context = context;

		conditionList = _conditions;

		View view = LayoutInflater.from(context).inflate(
				R.layout.teacher_search_pop_win, null);

		// 设置 listview
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		
		popupWindow = new PopupWindow(view, context.getResources()
				.getDimensionPixelSize(R.dimen.pop_win_teacherlist_width),
				LayoutParams.WRAP_CONTENT);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	// 设置菜单项点击监听器
	public void setOnItemClickListener(OnItemClickListener listener) {
		// this.listener = listener;
		listView.setOnItemClickListener(listener);
	}

	
	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		
		popupWindow.showAsDropDown(parent);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(false);
		// 刷新状态
		popupWindow.update();
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	public PopupWindow getPopWin() {
		return popupWindow;
	}
	
	// 适配器
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return conditionList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return conditionList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.pop_win_item, null);
				holder = new ViewHolder();

				convertView.setTag(holder);

				holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.code = (TextView) convertView.findViewById(R.id.code);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(conditionList.get(position).toString());
			holder.code.setText(conditionList.get(position).getCode());
			
			return convertView;
		}

		private final class ViewHolder {
			TextView text;
			TextView code;
		}
	}
}

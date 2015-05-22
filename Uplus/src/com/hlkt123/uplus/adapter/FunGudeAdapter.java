package com.hlkt123.uplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hlkt123.uplus.R;

public class FunGudeAdapter extends BaseAdapter{  
    private int funsize;        
    private LayoutInflater mInflater;  
    private Context context;
   
    public FunGudeAdapter(int _funsize, Context context)
    {  
        this.funsize = _funsize;  
        this.mInflater=LayoutInflater.from(context);  
        this.context=context;
    }  
  
    @Override
	public int getCount() {  
    	return funsize;  
    }  
  
    @Override
	public long getItemId(int position) {  
        return position;  
    }  
  
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {  
  
    	ViewHolder holder = null;
    	if(convertView == null){ 
    		holder=new ViewHolder(); 
    		convertView = mInflater.inflate(R.layout.image_item, null);
    		holder.clipImg=(ImageView)convertView.findViewById(R.id.gallery_image);
            convertView.setTag(holder);
    	}
    	else {
			holder = (ViewHolder)convertView.getTag();
		}
    	
        ImageView clipIV = (ImageView) convertView.findViewById(R.id.gallery_image);  
        if(position==0)
        {
        	clipIV.setImageResource(R.drawable.fun1);
        }
        else if(position==1)
        {
        	clipIV.setImageResource(R.drawable.fun1);
        }
        else if(position==2)
        {
        	clipIV.setImageResource(R.drawable.fun1);
        }
        else
        {
        	clipIV.setImageResource(R.drawable.fun4);
        }

        return convertView;  
    }  
    
    public final class ViewHolder
    {
		public ImageView clipImg;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
  
}  

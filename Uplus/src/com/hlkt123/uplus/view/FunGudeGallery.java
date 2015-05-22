package com.hlkt123.uplus.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * <p>功能引导页面Gallery</p>
 * @author Administrator
 *
 */
public class FunGudeGallery extends Gallery 
{

	public FunGudeGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public FunGudeGallery(Context context,AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public FunGudeGallery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
        if(isScrollingLeft(e1, e2))
        { //Check if scrolling left
          kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        }
        else
        { //Otherwise scrolling right
          kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null); 
        return true;  
	}
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2)
	{
        return e2.getX() > e1.getX();
    }
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) 
	{
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

}

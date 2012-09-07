package com.yuco;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class mygallery extends Gallery {
	static String TAG = "mygallery";
    public mygallery(Context ctx, AttributeSet attrSet) {
        super(ctx,attrSet);
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean onDown(MotionEvent e) {
     

        //Log.d(TAG, "Coords: x=" + e.getX() + ",y=" + e.getY());
        return super.onDown(e);
        
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
      /*int kEvent;
      if(isScrollingLeft(e1, e2)){ //Check if scrolling left
        kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
      }
      else{ //Otherwise scrolling right
        kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
      }
      onKeyDown(kEvent, null);*/
    	return false;//super.onFling(e1, e2, 10, velocityY);

     // return true;  
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
    	return false;
    }

}

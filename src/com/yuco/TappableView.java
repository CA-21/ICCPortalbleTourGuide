package com.yuco;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class TappableView extends ImageView {
	private ArrayList<TapListener> listeners=new ArrayList<TapListener>();
	private GestureDetector gesture=null;
	public TappableView(Context context) {
		super(context);
		this.setBackgroundResource(R.drawable.screen_saver);
		// TODO Auto-generated constructor stub
	}
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction()==MotionEvent.ACTION_UP) {
			gestureListener.onSingleTapUp(event);
		}
		
		return(true);
	}

	public void addTapListener(TapListener l) {
		listeners.add(l);
	}

	public void removeTapListener(TapListener l) {
		listeners.remove(l);
	}

	private GestureDetector.SimpleOnGestureListener gestureListener=
		new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			for (TapListener l : listeners) {
				l.onTap(e);
			}
			
			return(true);
		}
	};

	public interface TapListener {
		void onTap(MotionEvent event);
	}

}

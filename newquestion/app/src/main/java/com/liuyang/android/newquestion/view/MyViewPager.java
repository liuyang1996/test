package com.liuyang.android.newquestion.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author zsj 重写ViewPager禁止其左右滑动
 */
public class MyViewPager extends ViewPager {

	private float x;
	private boolean isComplete = false;

	public void isCompleteable(boolean iscomplete) {
		this.isComplete = iscomplete;
	}

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getX(); // 在手指按下的时获取X轴的坐标
			break;
		case MotionEvent.ACTION_MOVE:
			float mLastMotionX = ev.getX() - x;
			if (!isComplete) {
				if (mLastMotionX < 0) {
					return false;
				}
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}


}

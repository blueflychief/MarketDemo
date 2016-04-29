package com.example.administrator.market.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class UnblockViewPager extends ViewPager {

	public UnblockViewPager(Context context) {
		super(context);
	}

	public UnblockViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		// 这句话就是解决问题的关键。意思是自己处理触摸事件不需要父布局（listview）的相同事件覆盖
		return super.dispatchTouchEvent(ev);
	}
}
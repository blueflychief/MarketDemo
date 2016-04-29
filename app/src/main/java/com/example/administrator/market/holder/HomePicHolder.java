package com.example.administrator.market.holder;
/**
 * 广告轮播图的类，需要与InterceptorFrame（轮播图的点）类及点的布局indicator.xml使用，
 */

import android.annotation.SuppressLint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.imageload.ImageLoader;
import com.example.administrator.market.widget.IndicatorView;
import com.example.administrator.market.widget.UnblockViewPager;

import java.util.List;


public class HomePicHolder extends BaseHolder<List<String>> {

	private LayoutParams rl;
	private UnblockViewPager mViewPager;
	private List<String> appInfos;
	private IndicatorView indicatorView;
	private RelativeLayout.LayoutParams mParams;
	private AutoPlayTask autoPlayTask;
	public boolean canscoll = false;

	@SuppressLint("NewApi")
	@Override
	protected View initView() {

		// 第一步、初始化包裹轮播图最外面的布局*************************************** 
		RelativeLayout mHeadView = new RelativeLayout(UIUtils.getContext());
		//*****此处必须使用ListView的父类AbsListView设置宽高，因为只能通过父类设置宽高，当前RelativeLayout是ListView的一个子控件，ListView与当前需要添加的RelativeLayout都是同一个父控件
		rl = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,    
				UIUtils.getDimens(R.dimen.list_header_hight));
		//将包裹轮播图的布局的宽高设置进去
		mHeadView.setLayoutParams(rl);
		
		// 第二步、初始化轮播图控件，然后设置数据***************************************
		mViewPager = new UnblockViewPager(UIUtils.getContext());
		//轮播图控件的宽高参数，此处必须使用ViewPager的父类控件RelativeLayout设置宽高
		mParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		ViewPageAdapter adapter = new ViewPageAdapter();  //ViewPage适配器
		mViewPager.setAdapter(adapter);			//ViewPage监听器
		mViewPager.setLayoutParams(mParams);	// 初始化轮播图的宽和高
	
		// 第三步、初始化轮播图的点***************************************
		indicatorView = new IndicatorView(UIUtils.getContext());
		// 设置点的背景选择器
		indicatorView.setIndicatorDrawable(UIUtils
				.getDrawable(R.drawable.indicator));

		// new RelativeLayout(UIUtils.getContext());

		mParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 设置到父控件的右边
		mParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// 设置到父控件的下边
		mParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// 设置左上右下的距离
		mParams.setMargins(0, 0, 20, 20);
		// 设置点的位置从0开始
		indicatorView.setSelection(0);
		// 把宽和高丢到布局里面
		indicatorView.setLayoutParams(mParams);
	
//设置ViewPager页面改变监听器，主要是让点与图片一起动
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				
				indicatorView.setSelection(arg0 % getData().size());
				
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		autoPlayTask = new AutoPlayTask();
		autoPlayTask.start();
		
////设置ViewPager页面触摸时停止播放，不触摸自动播放
//		mViewPager.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					autoPlayTask.stop();
//				} else if (event.getAction() == MotionEvent.ACTION_UP) {
//					autoPlayTask.start();
//				}
//				return true;
//			}
//		});
		
		// 把轮播图和点加入到头文件里面。需要注意：先加载轮播图，然后再加载点
		mHeadView.addView(mViewPager);
		mHeadView.addView(indicatorView);
		return mHeadView;
	}
	
//图片自动播放的方法
	private class AutoPlayTask implements Runnable {
		private int auto_play_time = 5000;  // 自动跳的时间
		private boolean is_auto_play = false;	//是否自动播放标志

		@Override
		public void run() {     //此run()方法是实现Runnable接口的方法
			if (is_auto_play) {
				UIUtils.removeCallbacks(this);//******由于会多次调用重新start()与run()方法，runnable对象也会多次重复发送给主线程，所以在重新启动start()与run()前必须删除之前的runnable任务
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);  //播放下一张
				UIUtils.postDelayed(this, auto_play_time);
			}
		}
		public void start() {
			if (!is_auto_play) {
				is_auto_play = true;
				UIUtils.removeCallbacks(this);   //******由于会多次调用重新start()与run()方法，runnable对象也会多次重复发送给主线程，所以在重新启动start()与run()前必须删除之前的runnable任务
				//使用handle
				UIUtils.getHandler().postDelayed(this, auto_play_time);
			}
		}
		public void stop() {
			if (is_auto_play) {
				UIUtils.removeCallbacks(this);
				is_auto_play = false;
			}
		}
	}

	@Override
	public void refreshView() {
		appInfos = getData();
		//设置点的个数
		indicatorView.setCount(appInfos.size());
		// 默认设置viewpager的位置
		mViewPager.setCurrentItem(5000, false);
		//******开启轮播图的播放，在当前页面刷新数据的时候就让轮播图开始播放
//		autoPlayTask.start();
	}

	private class ViewPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// appInfos.size()
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(UIUtils.getContext());
			// 注意：如果只要是从网络获取图片的话，就必须设置这个属性。
			ImageLoader.load(imageView,
					appInfos.get(position % getData().size()));
			//此方法设置图片适应控件大小
			imageView.setScaleType(ScaleType.FIT_XY);
			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	
	}

}

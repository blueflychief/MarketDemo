package com.example.administrator.market.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.manager.ThreadManager;


//此处使用是FrameLayout，因为要展示3个状态界面，3个界面都是叠加在一起的
public abstract class LoadingPager extends FrameLayout {

	// 加载默认的状态
	private static final int START_UNLOADING = 0;
	// 加载的状态
	private static final int START_LOADING = 1;
	// 失败的状态
	private static final int START_ERROR = 3;
	// 加载空的状态
	private static final int START_EMPTY = 4;
	// 加载成功的状态
	private static final int START_SUCCESS = 5;

	private int mState;
	private View loadingView;
	private View emptyView;
	private View errorView;
	private View successView;

	public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LoadingPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPager(Context context) {
		super(context);
		init();
	}

	private void init() {
		// 初始化 默认的状态
		mState = START_UNLOADING;
		/**
		 * 以下是在初始化时创建出三个状态的界面，调用ViewGroup的addView()方法全部都添加到帧布局中
		 */
		loadingView = createLoadingView();
		if (null != loadingView) {
			addView(loadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		errorView = createErrorView();
		if (null != errorView) {
			addView(errorView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		emptyView = createEmptyView();
		if (null != emptyView) {
			addView(emptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}

		// 显示出界面
		showSafePagerView();
	}

	// 第一步，判断当前的状态。此方法暴露给其他类调用
	public void show() {
		//如果当前状态是错误或者为空，则将状态置为没有加载内容START_UNLOADING
		if (mState == START_ERROR || mState == START_EMPTY) {
			mState = START_UNLOADING;
		}
		//如果当前状态为没有加载内容START_UNLOADING，则将状态置为开始加载START_LOADING
		if (mState == START_UNLOADING) {
			mState = START_LOADING;
			
//方法一、使用此方法的性能比较差		
//			new Thread(){
//				public void run() {
//					
//				};
//			}.start();
//方法二、此方法已经在manager包中进行了封装
//			TaskRunable taskRunable=new TaskRunable();
//			ExecutorService executorService=Executors.newFixedThreadPool(5);
//			executorService.execute(taskRunable);
			
			//开启线程池从服务器获取数据，将会调用子类的实现当前抽象load()方法，返回当前页面将要加载的内容页状态值
			ThreadManager manager = new ThreadManager();
			manager.getLongPool().execute(new TaskRunable());
		}
		//根据从服务器获取到的数据以及页面状态值进行内容显示
		showSafePagerView();
	}
	
	// 第二步、每个页面根据自己的数据内容进行数据的显示
	protected abstract LoadResult loadData();

	// 第三步、在主线程中调用加载界面showPageView()方法
	private void showSafePagerView() {
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				showPageView();
			}
		});
	}

	// 第四步
	protected void showPageView() {
		// 如果三个界面都已经初始化出来了，则根据当前的状态值进行界面的显示
		if (null != loadingView) {
			loadingView.setVisibility(mState == START_LOADING
					|| mState == START_UNLOADING ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (null != errorView) {
			errorView.setVisibility(mState == START_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (null != emptyView) {
			emptyView.setVisibility(mState == START_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}
		
		// 》先走这步。    因为加载成功后显示的界面都不一样，所以留给子类去实现具体内容
		if (null == successView && mState == START_SUCCESS) {
			successView = createContentView();
			addView(successView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		//》》再走这步》》    当成功创建了successView页面，里面有了内容，再将其显示出来，顺便不能错。
		if (null != successView) {
			successView.setVisibility(mState == START_SUCCESS ? View.VISIBLE
					: View.INVISIBLE);
		}

	}

	// 第五步、因为每个页面需要显示的具体内容不一样，所以需要子类根据自身情况进行页面内容的创建
		protected abstract View createContentView();

	/**
	 * 获取数据的接口
	 * 
	 * @author xml_tech
	 * 
	 */
	private class TaskRunable implements Runnable {
		@Override
		public void run() {
			//子类实现抽象方法load()返回枚举类型的状态值，
			final LoadResult result = loadData();

			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					//根据服务器获取数据后返回的状态值进行界面内容的显示
					mState = result.getValue();
					showPageView();
				}
			});
		}

	}

	// 枚举出结果
	public enum LoadResult {
		ERROR(3), EMPTY(4), SUCCESS(5);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	// 创建空的界面
	private View createEmptyView() {
		return UIUtils.inflate(R.layout.loading_page_empty);
	}

	// 创建错误界面
	private View createErrorView() {
		return UIUtils.inflate(R.layout.loading_page_error);
	}

	// 创建加载界面
	private View createLoadingView() {
		return UIUtils.inflate(R.layout.loading_page_loading);
	}

}

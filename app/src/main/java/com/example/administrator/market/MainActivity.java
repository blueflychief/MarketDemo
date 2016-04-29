package com.example.administrator.market;

import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.fragment.BaseFragment;
import com.example.administrator.market.fragment.FragmentFactory;
import com.example.administrator.market.widget.PagerTab;

import java.util.List;



public class MainActivity extends BaseActivity implements OnPageChangeListener {

	private PagerTab tabs;
	private ViewPager pager;
	private ViewPageAdapeter adapter;
	private DrawerLayout drawer_layout;
	private ActionBar mActionBar;
	private FrameLayout left_drawer;
	private ListView lv_left_menu;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private List<AppInfo> mDatas;
	private TextView left_tv;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		// 整个抽屉菜单的布局，包括内容导航菜单条、内容页面、抽屉菜单
		drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 内容导航菜单条
		tabs = (PagerTab) findViewById(R.id.tabs);

		// 左侧菜单left_drawer
		left_drawer = (FrameLayout) findViewById(R.id.left_drawer);
		initLeftMenu();
		
		// 内容页面
		pager = (ViewPager) findViewById(R.id.pager);

		// 设置抽屉菜单的监听器
		MyDrawerListener listener = new MyDrawerListener();
		drawer_layout.setDrawerListener(listener);

		// 设置内容导航菜单条监听事件
		tabs.setOnPageChangeListener(this);

		// 设置内容页面的数据适配器
		adapter = new ViewPageAdapeter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		// 这一句必须放在最后。横着滚动的title和下面的fragement绑定在一起
		tabs.setViewPager(pager);

	}

	private void initLeftMenu() {
		//tab_names = UIUtils.getStringArray(R.array.tab_names);
		
		TextView leftTextView=new TextView(getApplicationContext());
		FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity=Gravity.CENTER;
		leftTextView.setText("这是左侧菜单");
		leftTextView.setTextColor(Color.RED);
		leftTextView.setTextSize(30);
		leftTextView.setLayoutParams(params);
		left_drawer.addView(leftTextView);
	}

	/**
	 * 第一步、初始化actionBar
	 */
	@Override
	protected void initActionbar() {
		// 为了向下兼容低版本的手机。所以必须使用V7扩展包里面的actionbar
		mActionBar = getSupportActionBar();
		// V4包需要设置以下参数，V7包不需要设置***************************************
		//mActionBar标题
		mActionBar.setTitle(R.string.app_name);
		// 设置actionbar可点击
		mActionBar.setDisplayShowHomeEnabled(true);
		// 设置左上角的抽屉菜单开关图标可点击
		mActionBar.setHomeButtonEnabled(true);
		// 给左上角图标的左边加上一个抽屉菜单开关图标可点击
		mActionBar.setDisplayHomeAsUpEnabled(true);
		// V4包需要设置以上参数，V7包不需要设置***************************************
		// mActionBar左侧的滑动开关-----参数1：activity 参数2：DrawerLayout对象 参数3：抽屉菜单开关的小图标        参数4、5：打开、关闭时文字，不重要
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout,
				R.drawable.ic_drawer_am, R.string.drawer_open,
				R.string.drawer_close);
		// 让滑动开关和滑动菜单关联到一起，默认是不在一起的
		actionBarDrawerToggle.syncState();
	}

	/**
	 * 第二步、如果想是实现抽屉菜单可以滑动，必须实现如下方法
	 * 
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return actionBarDrawerToggle.onOptionsItemSelected(item)
				|| super.onOptionsItemSelected(item);
	}

	// 第三步、抽屉菜单的监听器
	private class MyDrawerListener implements DrawerListener {
		// 抽屉菜单关闭方法
		@Override
		public void onDrawerClosed(View arg0) {
			// 表示抽屉菜单关闭时抽屉开关也关闭
			actionBarDrawerToggle.onDrawerClosed(arg0);
		}

		// 抽屉菜单打开方法
		@Override
		public void onDrawerOpened(View arg0) {
			actionBarDrawerToggle.onDrawerOpened(arg0);
		}

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			actionBarDrawerToggle.onDrawerSlide(arg0, arg1);
		}

		// 抽屉菜单状态改变方法
		@Override
		public void onDrawerStateChanged(int arg0) {
			actionBarDrawerToggle.onDrawerStateChanged(arg0);
		}
	}

	@Override
	protected void initData() {
		
	}

	

	// ViewPage的适配器
	private class ViewPageAdapeter extends FragmentStatePagerAdapter {

		// title 的名字
		private String[] tab_names;

		// ViewPageAdapeter构造方法
		public ViewPageAdapeter(FragmentManager fm) {
			super(fm);
			// 从string.xml文件中获取到PageTab的title数组
			tab_names = UIUtils.getStringArray(R.array.tab_names);
		}

		// 根据每个ViewPage返回对应的PageTab的title数组内容
		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}

		// 通过工厂类返回Fragment对象
		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return tab_names.length;
		}
	}
	
	//以下是三个OnPageChangeListener接口中的方法
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	
	//左右滑动的监听
	@Override
	public void onPageSelected(int position) {
		BaseFragment fragment = FragmentFactory.createFragment(position);
		fragment.show();
	}

}

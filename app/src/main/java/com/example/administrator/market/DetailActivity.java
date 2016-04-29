package com.example.administrator.market;


import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.example.administrator.market.application.utils.StringUtils;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.holder.DetailInfoHolder;
import com.example.administrator.market.holder.DetailSafeHolder;
import com.example.administrator.market.holder.DetailScreenHolder;
import com.example.administrator.market.protocol.DetailProtocol;
import com.example.administrator.market.widget.LoadingPager;


public class DetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo appInfo;
	//根据父类BaseActivity的构造方法，执行init()、initView()、initActionbar()方法
	@Override
	protected void initData() {
		// 获取到上一个页面传过来的包名
		Intent intent = getIntent();
		if (null != intent) {// packageName
			packageName = intent.getStringExtra("packageName");
		}
	}
//使用框架页面LoadingPager类加载当前页面
	@Override
	protected void initView() {
		//LoadingPager构造方法初始化界面，并实现父类的抽象方法createLoadedView()、loadDataWithCache()
		LoadingPager mContentView = new LoadingPager(UIUtils.getContext()) {
			@Override
			protected LoadResult loadData() {
				return DetailActivity.this.load();   //调用自己的的方法
			}

			@Override
			protected View createContentView() {		//调用自己的的方法
				return DetailActivity.this.createLoadedView();
			}
		};
		setContentView(mContentView);  //设置当前的页面内容
		mContentView.show();			//将当前页面显示出来
	}

	protected View createLoadedView() {

		// 详情的基本信息的整个界面，每个小的块对应一个holder，如果不需要哪个块，在此处直接注释相应的代码块就行
		View view = UIUtils.inflate(R.layout.activity_detail);
		
		// APP描述块
		FrameLayout detail_info = (FrameLayout) view
				.findViewById(R.id.detail_info);
		DetailInfoHolder detailInfoHolder = new DetailInfoHolder();   //DetailInfoHolder父类BaseHolder只对View进行了初始化，没有对数据进行初始化
		detailInfoHolder.setDate(appInfo);     //DetailInfoHolder进行数据的填充
		detail_info.addView(detailInfoHolder.getRootView());   //DetailInfoHolder调用父类BaseHolder的getRootView()方法返回View
		
		//APP安全信息描述块
		FrameLayout detail_safe = (FrameLayout) view
				.findViewById(R.id.detail_safe);
		DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
		detailSafeHolder.setDate(appInfo);
		detail_safe.addView(detailSafeHolder.getRootView());

		//APP滚动截图块
		HorizontalScrollView detail_screen = (HorizontalScrollView) view
				.findViewById(R.id.detail_screen);
		DetailScreenHolder detailScreenHolder = new DetailScreenHolder();
		detailScreenHolder.setDate(appInfo);
		detail_screen.addView(detailScreenHolder.getRootView());
		
		//详细描述块省略了
		
		return view;
	}
	
	//拿到服务器的数据
	protected LoadingPager.LoadResult load() {
		//使用DetailProtocol类从服务器获取数据
		DetailProtocol protocol = new DetailProtocol();
		protocol.setPackageName(packageName);    
		appInfo = protocol.loadDataWithCache(0);   //调用DetailProtocol父类BaseProtocol的load方法
		if (appInfo == null || StringUtils.isEmpty(packageName)) {
			return LoadingPager.LoadResult.ERROR;
		}
		return LoadingPager.LoadResult.SUCCESS;
	}

	@Override
	protected void initActionbar() {

	}

}

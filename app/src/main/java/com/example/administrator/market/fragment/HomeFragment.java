package com.example.administrator.market.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.market.adapter.MyBaseAdapter;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.holder.BaseHolder;
import com.example.administrator.market.holder.HomeHolder;
import com.example.administrator.market.holder.HomePicHolder;
import com.example.administrator.market.protocol.HomeProtocol;
import com.example.administrator.market.widget.LoadingPager.LoadResult;
import java.util.List;


public class HomeFragment extends BaseFragment {
	
	//从服务器获取到的APP信息的List
	private List<AppInfo> mDatas;
	//从服务器获取到轮播图的url地址
	private List<String> pictureListUrls;

	
	
	@Override
	protected View createChildView() {
		//ViewPage中ListView每个Item的信息
		ListView mListView = new ListView(UIUtils.getContext());
//*******以下部分是在首页ListView头部添加一个轮播图，如不需要，直接注释掉即可*************************
		if (pictureListUrls != null) {
			HomePicHolder homePicHolder = new HomePicHolder();
			homePicHolder.setDate(pictureListUrls);
			mListView.addHeaderView(homePicHolder.getRootView());
		}
//*******以上部分是在首页ListView头部添加一个轮播图****************************************************
		

		
		HomeAdapter adapter = new HomeAdapter(mDatas);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(UIUtils.getContext(), position+"被点击了", 0).show();
				
			}
		});
		mListView.setAdapter(adapter);
		return mListView;
	}
//加载服务器传来的数据
	@Override
	protected LoadResult loadDataFromNet() {
		// HttpResult result = HttpHelper.get("xxxx");
		// String json = result.getString();
		// mDatas = new ArrayList<String>();
		// for (int i = 0; i < 100; i++) {
		// mDatas.add("我是item" + i);
		// }
		HomeProtocol protocol = new HomeProtocol();
//		mDatas是获取到的已解析后的APP信息的Json数据，load后面是索引值
		mDatas = protocol.loadDataWithCache(0);
		
		//从服务器获取到首页轮播图的图片
		pictureListUrls = protocol.getPictureUrl();
		return checkData(mDatas);
	}

	private class HomeAdapter extends MyBaseAdapter<AppInfo> {
		
		public HomeAdapter(List<AppInfo> mDatas) {
			//加载继承类的构造方法，获取到服务器的数据
			super(mDatas);
		}
			//使用Holder类进行页面及数据的填充
		@Override
		protected BaseHolder getHolder() {
			return new HomeHolder();
		}

		//加载更多
		@Override
		protected List onLoadmore() {
			HomeProtocol protocol = new HomeProtocol();
			return protocol.loadDataWithCache(getData().size());
		}

	}

	// private class ViewHolder extends BaseHolder<String> {
	//
	// TextView tv;
	//
	// @Override
	// protected View initView() {
	// // 创建并返回布局文件
	// tv = new TextView(UIUtils.getContext());
	// return tv;
	// }
	//
	// @Override
	// public void refreshView() {
	// tv.setText(getData());
	// }
	// }
}

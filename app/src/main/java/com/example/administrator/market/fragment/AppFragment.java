package com.example.administrator.market.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.administrator.market.DetailActivity;
import com.example.administrator.market.adapter.MyBaseAdapter;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.holder.AppHolder;
import com.example.administrator.market.holder.BaseHolder;
import com.example.administrator.market.protocol.AppProtocol;
import com.example.administrator.market.widget.LoadingPager;

import java.util.List;


public class AppFragment extends BaseFragment {

	private List<AppInfo> mDatas;

//加载页面的数据
	@Override
	protected View createChildView() {
		ListView mListView = new ListView(UIUtils.getContext());
		AppAdapter adapter = new AppAdapter(mListView, mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}
	
//获取服务器数据
	@Override
	protected LoadingPager.LoadResult loadDataFromNet() {
		AppProtocol protocol = new AppProtocol();
		mDatas = protocol.loadDataWithCache(0);
		return checkData(mDatas);
	}
	
//设置页面中List的适配器
	private class AppAdapter extends MyBaseAdapter<AppInfo> implements
			OnItemClickListener {

		public AppAdapter(ListView mListView, List<AppInfo> mDatas) {
			super(mDatas);
			mListView.setOnItemClickListener(this);
		}

		@Override
		protected BaseHolder getHolder() {
			return new AppHolder();
		}

		@Override
		protected List onLoadmore() {
			AppProtocol protocol = new AppProtocol();
			return protocol.loadDataWithCache(getData().size());
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 获取到app的唯一值.包名，详情页根据包名显示数据，从父类BaseFragment中使用getData()方法取得所有服务器返回的数据
			AppInfo appInfo = getData().get(position);
			String packageName = appInfo.getPackageName();
			Intent intent = new Intent(UIUtils.getContext(),
					DetailActivity.class);
			intent.putExtra("packageName", packageName);    //将包名携带到详情页面去
			UIUtils.startActivity(intent);
		}

	}
}

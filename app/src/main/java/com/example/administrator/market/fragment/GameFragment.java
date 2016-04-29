package com.example.administrator.market.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.administrator.market.adapter.MyBaseAdapter;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.holder.BaseHolder;
import com.example.administrator.market.holder.GameHolder;
import com.example.administrator.market.protocol.GameProtocol;
import com.example.administrator.market.widget.LoadingPager.LoadResult;

import java.util.List;

public class GameFragment extends BaseFragment {

	private List<AppInfo> mDatas;

	@Override
	protected View createChildView() {
		ListView mListView = new ListView(UIUtils.getContext());
		GameAdapter adapter = new GameAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}

	@Override
	protected LoadResult loadDataFromNet() {
		GameProtocol protocol = new GameProtocol();
		mDatas = protocol.loadDataWithCache(0);
		return checkData(mDatas);
	}

	private class GameAdapter extends MyBaseAdapter<AppInfo> {

		public GameAdapter(List<AppInfo> mDatas) {
			super(mDatas);
		}

		@Override
		protected BaseHolder getHolder() {
			return new GameHolder();
		}

		@Override
		protected List onLoadmore() {
			GameProtocol protocol = new GameProtocol();
			return protocol.loadDataWithCache(getData().size());
		}

	}
}

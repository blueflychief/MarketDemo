package com.example.administrator.market.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.administrator.market.adapter.MyBaseAdapter;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.SubjectInfo;
import com.example.administrator.market.holder.BaseHolder;
import com.example.administrator.market.holder.SubjectHolder;
import com.example.administrator.market.protocol.SubjectProtocol;
import com.example.administrator.market.widget.LoadingPager;

import java.util.List;


public class SubjectFragment extends BaseFragment {
	private List<SubjectInfo> mDatas;

	@Override
	protected View createChildView() {

		ListView mListView = new ListView(UIUtils.getContext());
		SubjectAdapter adapter = new SubjectAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}

	@Override
	protected LoadingPager.LoadResult loadDataFromNet() {
		SubjectProtocol protocol = new SubjectProtocol();
		mDatas = protocol.loadDataWithCache(0);
		return checkData(mDatas);
	}

	private class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

		public SubjectAdapter(List<SubjectInfo> mDatas) {
			super(mDatas);
		}

		@Override
		protected BaseHolder getHolder() {
			// TODO Auto-generated method stub
			return new SubjectHolder();
		}

		@Override
		protected List onLoadmore() {
			SubjectProtocol protocol = new SubjectProtocol();
			return protocol.loadDataWithCache(getData().size());
		}

	}
}

package com.example.administrator.market.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.market.adapter.MyBaseAdapter;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.CategoryInfo;
import com.example.administrator.market.holder.BaseHolder;
import com.example.administrator.market.holder.CategoryHolder;
import com.example.administrator.market.holder.CategoryTitleHolder;
import com.example.administrator.market.protocol.CategoryProtocol;
import com.example.administrator.market.widget.LoadingPager.LoadResult;

import java.util.List;

public class CategoryFragment extends BaseFragment {

	private List<CategoryInfo> mDatas;

	@Override
	protected LoadResult loadDataFromNet() {
		CategoryProtocol protocol = new CategoryProtocol();
		mDatas = protocol.loadDataWithCache(0);
		return checkData(mDatas);
	}

	@Override
	protected View createChildView() {
		ListView mListView = new ListView(UIUtils.getContext());
		CategoryAdapter adapter = new CategoryAdapter(mDatas);
		mListView.setAdapter(adapter);
		return mListView;
	}

	private class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {

		public CategoryAdapter(List<CategoryInfo> lists) {
			super(lists);
		}

		int position = 0;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			this.position = position;
			return super.getView(position, convertView, parent);
		}

		@Override
		protected BaseHolder getHolder() {
			if (getData().get(position).isTitle()) {
				return new CategoryTitleHolder();
			} else {
				return new CategoryHolder();
			}
		}

		@Override
		public int getInnerItemViewType(int position) { // getItemViewType
			if (getData().get(position).isTitle()) {
				return super.getInnerItemViewType(position) + 1; // getItemViewType
			} else {
				return super.getInnerItemViewType(position); // getItemViewType
			}
		}

		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() + 1;
		}

		@Override
		protected List onLoadmore() {
			return null;
		}

		@Override
		public boolean hasmore() {
			return false;
		}
	}
}

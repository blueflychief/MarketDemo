package com.example.administrator.market.fragment;
//package com.itheima45.fragment;
//
//import java.util.ArrayList;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.itheima45.application.utils.UIUtils;
//import com.itheima45.holder.BaseHolder;
//import com.mwqi.ui.widget.LoadingPager.LoadResult;
//
//public class HomeFragment1 extends BaseFragment {
//
//	private ArrayList<String> mDatas;
//
//	@Override
//	protected View createContentView() {
//
//		ListView mListView = new ListView(UIUtils.getContext());
//		HomeAdapter adapter = new HomeAdapter();
//		mListView.setAdapter(adapter);
//		return mListView;
//	}
//
//	@Override
//	protected LoadResult loadDataFromNet() {
//
//		mDatas = new ArrayList<String>();
//		for (int i = 0; i < 100; i++) {
//			mDatas.add("我是item" + i);
//		}
//		// if (mDatas == null) {
//		// return LoadResult.ERROR;
//		// } else if (mDatas.size() == 0) {
//		// return LoadResult.EMPTY;
//		// } else {
//		// return LoadResult.SUCCESS;
//		// }
//
//		return checkData(mDatas);
//	}
//
//	private class HomeAdapter extends BaseAdapter {
//
//		private ViewHolder holder;
//
//		@Override
//		public int getCount() {
//			return mDatas.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//
//			if (convertView == null) {
//				holder = new ViewHolder();
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			holder.setDate(mDatas.get(position));
//			return holder.getRootView();
//		}
//	}
//
//	private class ViewHolder extends BaseHolder<String> {
//
//		TextView tv;
//
//		@Override
//		protected View initView() {
//			// 创建并返回布局文件
//			tv = new TextView(UIUtils.getContext());
//			return tv;
//		}
//
//		@Override
//		protected void refreshView() {
//			tv.setText(getData());
//		}
//	}
// }

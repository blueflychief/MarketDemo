package com.example.administrator.market.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.holder.BaseHolder;
import com.example.administrator.market.holder.MoreHolder;
import com.example.administrator.market.manager.ThreadManager;

import java.util.List;

/**
 * 
* @Title: MyBaseAdapter.java 
* @Package com.itheima45.adapter 
* @Description: 此类是ViewPage中ListView的适配器 
* @author Infinite 
* @date 2015-6-11 上午12:22:16 
* @version V1.0
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public List<T> mDatas;
	private BaseHolder holder;

	public MyBaseAdapter(List<T> mDatas) {
		setData(mDatas);
	}

	public void setData(List<T> mDatas) {
		this.mDatas = mDatas;
	}

	public List<T> getData() {
		return mDatas;
	}

	@Override
	public int getCount() {
		// 需要加载更多的数据 加载中。。。。。
		return mDatas.size() + 1;
	}
 
	/**
	 * 注意：加载普通的数据类型和加载更多的数据类型不要写反了
	 */
	// 加载普通的数据类型
	private final int ITEM_VIEW_TYPE = 1;//
	// 加载更多的数据类型
	private final int MORE_VIEW_TYPE = 0;
	private MoreHolder moreHolder;

	@Override
	public int getItemViewType(int position) {
		// 当position的位置等于20的时候，那么就要出现加载更多的数据类型
		if (position == getCount() - 1) {
			return MORE_VIEW_TYPE;
		} else {
			return getInnerItemViewType(position);
		}
	}

	public int getInnerItemViewType(int position) {
		return ITEM_VIEW_TYPE;
	}

	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount() + 1;
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null && convertView.getTag() instanceof BaseHolder) {
			holder = (BaseHolder) convertView.getTag();
		} else {
			// 判断当前的值是否是加载更多的数据类型。如果表示更多的数据类型。那么就需要加载更多的holder也就是转圈的view
			if (MORE_VIEW_TYPE == getItemViewType(position)) {
				holder = getMoreHolder();
			} else {
				holder = getHolder();
			}
		}
		// 如果当position的位置等于前面20条数据的时候。那么就需要设置数据。第21条数据表示加载更多
		if (ITEM_VIEW_TYPE == getItemViewType(position)) {
			holder.setDate(mDatas.get(position));
		}
		return holder.getRootView();
	}

	/**
	 * 如果能走到这里。默认情况下。表示有更多的数据类型
	 * 
	 * @return
	 */
	private BaseHolder getMoreHolder() {

		if (moreHolder == null) {
			moreHolder = new MoreHolder(this, hasmore());
		}
		return moreHolder;
	}

	public boolean hasmore() {
		return true;
	}

	protected abstract BaseHolder getHolder();

	public boolean is_loading = false;

	/**
	 * 加载更多的数据
	 */
	public void loadMore() {
		// 开启子线程去加载服务器发送过来的数据
		if (!is_loading) {
			is_loading = true;
			//开启线程池去执行在主界面加载更多的数据
			ThreadManager.getLongPool().execute(new Runnable() {

				@Override
				public void run() {
					//从服务器获取到数据
					final List list = onLoadmore();
					//页面加载操作运行在主线程中
					UIUtils.runInMainThread(new Runnable() {

						@Override
						public void run() {
							if (null == list) {  //如果服务器数据返回的是空，则说明获取数据失败，将服务器数据状态置为ERROR
								getMoreHolder().setDate(MoreHolder.ERROR);
							} else if (list.size() < 20) {//此数量根据业务需求可变更，如果服务器返回数据小于20条，则表示没有更多数据了
								getMoreHolder().setDate(MoreHolder.NO_MORE);
							} else {//此处说明后面还有更多的数据
								getMoreHolder().setDate(MoreHolder.HAS_MORE);
							}

							// 把从服务器返回的数据追加到之前的集合里面
							if (list != null) {
								if (mDatas != null) {
									mDatas.addAll(list);
								} else {
									setData(list);
								}
							}
							notifyDataSetChanged();
							is_loading = false;
						}
					});
				}
			});
		}
	}

	// 加载更多
	protected abstract List onLoadmore();

}

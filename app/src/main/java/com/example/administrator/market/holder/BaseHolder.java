package com.example.administrator.market.holder;

import android.view.View;

public abstract class BaseHolder<T> {

	private View view;

	private T data;

	public BaseHolder() {
		view = initView();
		view.setTag(this);
	}

	protected abstract View initView();

	public View getRootView() {
		return view;
	}

	// 设置数据填充的方法
	public void setDate(T data) {
		this.data = data;
		refreshView();
	}

	public abstract void refreshView();  //让子类去实现数据填充

	// 得到数据的方法
	public T getData() {
		return data;
	}
}

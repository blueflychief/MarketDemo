package com.example.administrator.market.holder;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.CategoryInfo;


public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tv_title;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.category_item_title);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView() {
		CategoryInfo categoryInfo = getData();
		tv_title.setText(categoryInfo.getTitle());
	}

}

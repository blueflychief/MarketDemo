package com.example.administrator.market.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.SubjectInfo;
import com.example.administrator.market.imageload.ImageLoader;


public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView item_icon;
	private TextView item_tx;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.subject_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_tx = (TextView) view.findViewById(R.id.item_txt);
		return view;
	}

	@Override
	public void refreshView() {
		SubjectInfo subjectInfo = getData();
		ImageLoader.load(item_icon, subjectInfo.getUrl());
		item_tx.setText(subjectInfo.getDes());

	}

}

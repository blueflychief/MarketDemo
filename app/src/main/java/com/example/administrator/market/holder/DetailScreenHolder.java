package com.example.administrator.market.holder;

import android.view.View;
import android.widget.ImageView;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.imageload.ImageLoader;


public class DetailScreenHolder extends BaseHolder<AppInfo> {

	private ImageView[] imageViews;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_screen);
		imageViews = new ImageView[5];  //设置最多显示5张图片
		imageViews[0] = (ImageView) view.findViewById(R.id.screen_1);
		imageViews[1] = (ImageView) view.findViewById(R.id.screen_2);
		imageViews[2] = (ImageView) view.findViewById(R.id.screen_3);
		imageViews[3] = (ImageView) view.findViewById(R.id.screen_4);
		imageViews[4] = (ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();   //在DetailScreenHolder中调用父类BaseHolder的getData()方法
		for (int i = 0; i < imageViews.length; i++) {
			if (appInfo != null) {
				ImageLoader.load(imageViews[i], appInfo.getScreen().get(i));
				imageViews[i].setVisibility(View.VISIBLE);
			} else {
				imageViews[i].setVisibility(View.GONE);
			}
		}

	}

}

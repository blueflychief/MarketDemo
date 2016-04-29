package com.example.administrator.market.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.StringUtils;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.imageload.ImageLoader;


public class DetailInfoHolder extends BaseHolder<AppInfo> {

	private ImageView item_icon;
	private TextView item_title;
	private RatingBar item_rating;
	private TextView item_download;
	private TextView item_version;
	private TextView item_date;
	private TextView item_size;
	
	
//设置界面
	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_info);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_download = (TextView) view.findViewById(R.id.item_download);
		item_version = (TextView) view.findViewById(R.id.item_version);
		item_date = (TextView) view.findViewById(R.id.item_date);
		item_size = (TextView) view.findViewById(R.id.item_size);
		return view;
	}
//填充数据
	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		ImageLoader.load(item_icon, appInfo.getIconUrl());   //使用自定义工具类中的方法加载图片到ImageView
		item_title.setText(appInfo.getName());
		item_rating.setRating(appInfo.getStars());
		item_download.setText(appInfo.getDownloadNum());
		item_version.setText(appInfo.getVersion());
		item_date.setText(appInfo.getDate());
		item_size.setText(StringUtils.formatFileSize(appInfo.getSize()));
	}
}

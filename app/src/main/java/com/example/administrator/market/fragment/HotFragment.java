package com.example.administrator.market.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.administrator.market.widget.LoadingPager.LoadResult;
import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.DrawableUtils;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.protocol.HotProtocol;
import com.example.administrator.market.widget.FlowLayout;

import java.util.List;
import java.util.Random;


public class HotFragment extends BaseFragment {

	private List<String> mDatas;
	private TextView textView;
	private int red;
	private int green;
	private int blue;
	private int color;
	private GradientDrawable drawable;
	private FlowLayout flowLayout;
	private Random random;
	private ScrollView scrollView;

	@Override
	protected View createChildView() {
		scrollView=new ScrollView(UIUtils.getContext());
		
		flowLayout = new FlowLayout(UIUtils.getContext());

		random = new Random();

		int spacing = UIUtils.dip2px(13);
		// 设置间距
		flowLayout.setVerticalSpacing(spacing);
		flowLayout.setHorizontalSpacing(spacing);
		// 设置左上右下的间距
		flowLayout.setPadding(spacing, spacing, spacing, spacing);

		for (int i = 0; i < mDatas.size(); i++) {

			textView = new TextView(UIUtils.getContext());

			red = 20 + random.nextInt(220);
			green = 20 + random.nextInt(220);
			blue = 20 + random.nextInt(220);
			color = Color.rgb(red, green, blue);

			drawable = new DrawableUtils().createDrawable(color, color, 5);

			//设置文字居中
			textView.setGravity(Gravity.CENTER);
			// 设置字体的数据
			textView.setText(mDatas.get(i));
			textView.setPadding(3, 3, 3, 3);
			// 设置字体的颜色
			textView.setTextColor(UIUtils.getColor(R.color.white));
			// 这个地方一定要使用过时的，不然没办法向下兼容
			textView.setBackgroundDrawable(drawable);
			// 设置字体的大小，单位是dip，如果控件是包括内容的话，就需要使用dip，不能使用sp
			textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			flowLayout.addView(textView);
			mDatas.get(i);
		}
		//设置scrollView没有垂直滚动条
		scrollView.setVerticalScrollBarEnabled(false);
		
//		将flowLayout布局添加到scrollView中，这样flowLayout就可以滚动
		scrollView.addView(flowLayout);
		return scrollView;
	}

	@Override
	protected LoadResult loadDataFromNet() {
		HotProtocol protocol = new HotProtocol();
		mDatas = protocol.loadDataWithCache(0);
		return checkData(mDatas);
	}

}

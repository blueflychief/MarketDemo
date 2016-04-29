package com.example.administrator.market.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.market.R;
import com.example.administrator.market.application.utils.UIUtils;
import com.example.administrator.market.bean.AppInfo;
import com.example.administrator.market.imageload.ImageLoader;

import java.util.List;

public class DetailSafeHolder extends BaseHolder<AppInfo> implements
		OnClickListener {

	private LinearLayout mSafeContent;
	private ImageView safe_arrow;
	private ImageView[] ivs;
	private ImageView[] des_ivs;
	private TextView[] des_tvs;
	private LinearLayout[] des_layouts;

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		// 对应着官方。安全。无广告等的图片下载地址
		List<String> safeUrl = appInfo.getSafeUrl();
		// 获取到勾勾图片
		List<String> safeDesUrl = appInfo.getSafeDesUrl();
		// 获取到勾勾旁边的描述信息
		List<String> safeDes = appInfo.getSafeDes();
		// 获取到安全信息的颜色
		List<Integer> safeDesColor = appInfo.getSafeDesColor();

		for (int i = 0; i < 4; i++) {
			// 注意视频中的这个地方有问题，不能写成i <safeUrl.size()，而要写成i<4，因为勾选框和描述是固定的，只是根据标签有无设置可见不可见，所以一定要循环遍历4个；这是第一种解决方法
			// 第二种方案：在xml布局文件中把des_layout_1，des_layout_2，des_layout_3，des_layout_4全部设置为android:visibility="gone"
			// if (safeUrl != null && safeDesUrl != null && safeDes != null
			// && safeDesColor != null) {
			if (i < safeUrl.size() && i < safeDesUrl.size()
					&& i < safeDes.size() && i < safeDesColor.size()) {
				ImageLoader.load(ivs[i], safeUrl.get(i));        	//标签
				ImageLoader.load(des_ivs[i], safeDesUrl.get(i));	//勾选框
				des_tvs[i].setText(safeDes.get(i));					//描述

				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0);
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				des_tvs[i].setTextColor(color);					//设置描述文本颜色
				ivs[i].setVisibility(View.VISIBLE);
				des_layouts[i].setVisibility(View.VISIBLE);
			} else {
				ivs[i].setVisibility(View.GONE);
				des_layouts[i].setVisibility(View.GONE);
			}
		}

	}

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_safe);   //将布局文件转换成View对象
		// 给安全界面设置点击事件
		RelativeLayout mSafeLayout = (RelativeLayout) view		//找到需要关注的控件
				.findViewById(R.id.safe_layout);
		mSafeLayout.setOnClickListener(this);
		
		// 设置tag的两种状态。默认情况下是向下的。点击就向上
		safe_arrow = (ImageView) view.findViewById(R.id.safe_arrow);
		safe_arrow.setTag(false);   //默认箭头是关闭的
		
		// 默认情况下。内容页面的高度是没有的，所以设置为0
		mSafeContent = (LinearLayout) view.findViewById(R.id.safe_content);
		mSafeContent.getLayoutParams().height = 0;

		ivs = new ImageView[4];    //一直显示的4个标签

		ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
		ivs[3] = (ImageView) view.findViewById(R.id.iv_4);

		des_ivs = new ImageView[4];     //4个标签内容详情的勾选框
		des_ivs[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		des_ivs[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		des_ivs[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		des_ivs[3] = (ImageView) view.findViewById(R.id.des_iv_4);

		des_tvs = new TextView[4];		//4个标签内容详情的描述
		des_tvs[0] = (TextView) view.findViewById(R.id.des_tv_1);
		des_tvs[1] = (TextView) view.findViewById(R.id.des_tv_2);
		des_tvs[2] = (TextView) view.findViewById(R.id.des_tv_3);
		des_tvs[3] = (TextView) view.findViewById(R.id.des_tv_4);

		des_layouts = new LinearLayout[4];   //勾选框与描述组成的布局数组
		des_layouts[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		des_layouts[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		des_layouts[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		des_layouts[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

		return view;
	}

	@Override
	public void onClick(View v) {
		// 默认获取到标记的值
		boolean flag = (Boolean)safe_arrow.getTag();
		final LayoutParams params = mSafeContent.getLayoutParams();
		int height = mSafeContent.getMeasuredHeight();   //内容隐藏时的高度
		int tagetHeight;
		if (flag) {
			safe_arrow.setTag(false);
			tagetHeight = 0;// 别写错了 不要写成height=0
		} else {
			safe_arrow.setTag(true);
			tagetHeight = measureSafeContentHeight();  //测量内容显示后的高度
		}

		// 为了避免程序多次点击的anr异常，播放动画时所以必须把mSafeContent设置为不可点击状态
		mSafeContent.setEnabled(false);

		// 播放属性动画,从默认的高度高目标高度的一个变化的过程 0 1000
		ValueAnimator va = ValueAnimator.ofInt(height, tagetHeight);  //动画从默认高度到目标高度
		//添加一个动画更新的监听器
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				params.height = (Integer) arg0.getAnimatedValue();   //动画更新时实时获得当前的其实高度
				// 这个地方少写一句话，没有把修改后的参数重新设置给mSafeContent
				mSafeContent.setLayoutParams(params);    //实时显示控件的高度
			}
		});
		
		//动画的监听器。设置动画结束时mSafeContent可以点击，因为在动画播放时设置了mSafeContent不可点击
		va.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// 当动画结束的时候，控件就可以点击了
				mSafeContent.setEnabled(true);
				//设置箭头的状态
				boolean tag = (Boolean) safe_arrow.getTag();
				safe_arrow.setImageResource(tag ? R.drawable.arrow_up
						: R.drawable.arrow_down);
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});
		
		// 设置动画的时间
		va.setDuration(500);
		// 开启动画
		va.start();
	}

	/**
	 * 测量内容页面的高度
	 * 
	 * @return
	 */
	private int measureSafeContentHeight() {
		int width = mSafeContent.getWidth();
		mSafeContent.getLayoutParams().height = LayoutParams.WRAP_CONTENT;   //设置高度为包裹内容
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);   //由于宽度是固定的，所有使用精确值模式EXACTLY
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000,
				MeasureSpec.AT_MOST);	//参数1：期望值，可以随便写；高度是不固定的，所以采用最大值模式
		mSafeContent.measure(widthMeasureSpec, heightMeasureSpec);    //进行宽度和高度的测量
		return mSafeContent.getMeasuredHeight();
	}
}

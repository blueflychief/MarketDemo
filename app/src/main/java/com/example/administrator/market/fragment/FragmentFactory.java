package com.example.administrator.market.fragment;

import java.util.HashMap;

public class FragmentFactory {

	// 首页
	private static final int TAB_HOME = 0;
	// 应用
	private static final int TAB_APP = 1;
	// 游戏
	private static final int TAB_GAME = 2;
	// 专题
	private static final int TAB_SUBJECT = 3;
	// 推荐
	private static final int TAB_RECOMMENTED = 4;
	// /分类
	private static final int TAB_CATEGORY = 5;
	// 热门
	private static final int TAB_HOT = 6;

	//将位置和对应的Fragment保存，之后可根据保存的位置获取到Fragment
	private static HashMap<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position) {
		// BaseFragment fragment = null;
		// 自出利用了缓存Fragment机制，为的是减少每次切换一个Fragment都需要重新创建一个Fragment。从缓存中取出
		BaseFragment fragment = mFragments.get(position);
		if (null == fragment) {
			switch (position) {
			case TAB_HOME:
				fragment = new HomeFragment();
				break;
			case TAB_APP:
				fragment = new AppFragment();
				break;
			case TAB_GAME:
				fragment = new GameFragment();
				break;
			case TAB_SUBJECT:
				fragment = new SubjectFragment();
				break;
			case TAB_RECOMMENTED:
				fragment = new RecommendedFragment();
				break;
			case TAB_CATEGORY:
				fragment = new CategoryFragment();
				break;
			case TAB_HOT:
				fragment = new HotFragment();
				break;

			default:
				break;
			}
			// 把frament加入到缓存中
			mFragments.put(position, fragment);
		}
		return fragment;
	}
}

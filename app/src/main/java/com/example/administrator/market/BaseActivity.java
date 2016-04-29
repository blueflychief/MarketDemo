package com.example.administrator.market;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

	// ��ȡ��ǰ̨���̵�Activity
	private static Activity mForegroundActivity = null;

//	@TargetApi(21)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
//		if (Build.VERSION.SDK_INT >= 21) {
//			requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//			getWindow().setAllowEnterTransitionOverlap(true);
//			getWindow().setAllowReturnTransitionOverlap(true);
//		}
		super.onCreate(savedInstanceState);
		initData();
		initView();
		initActionbar();
	}


	protected abstract void initActionbar();


	protected abstract void initView();


	protected abstract void initData();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mForegroundActivity = this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.mForegroundActivity = null;
	}

	public static Activity getForegroundActivity() {
		return mForegroundActivity;
	}
}

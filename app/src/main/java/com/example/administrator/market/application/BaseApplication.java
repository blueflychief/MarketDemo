package com.example.administrator.market.application;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class BaseApplication extends Application {

	// ��ȡ�����̵߳�������
	private static BaseApplication mContext = null;
	// ��ȡ�����̵߳�handler
	private static Handler mMainThreadHandler = null;

	// ��ȡ�����߳�
	private static Thread mMainThread = null;
	// ��ȡ�����̵߳�id
	private static int mMainThreadId;
	// ��ȡ�����̵߳�looper
	private static Looper mMainThreadLooper = null;

	@Override
	public void onCreate() {
		super.onCreate();
		this.mContext = this;
		this.mMainThreadHandler = new Handler();
		this.mMainThread = Thread.currentThread();
		this.mMainThreadId = android.os.Process.myTid();
		this.mMainThreadLooper = getMainLooper();
	}

	// ���Ⱪ¶������
	public static BaseApplication getApplication() {
		return mContext;
	}

	// ���Ⱪ¶���̵߳�handler
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	// ���Ⱪ¶���߳�
	public static Thread getMainThread() {
		return mMainThread;
	}

	// ���Ⱪ¶���߳�id
	public static int getMainThreadId() {
		return mMainThreadId;
	}

	// ���Ⱪ¶���̵߳�looper
	public static Looper getMainThreadLooper() {
		return mMainThreadLooper;
	}
}

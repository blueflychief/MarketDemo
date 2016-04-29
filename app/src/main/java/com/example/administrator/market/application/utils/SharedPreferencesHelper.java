package com.example.administrator.market.application.utils;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


 
/**
 * 软件参数设置器 默认全部使用String类型存储数据，如有其他类型数据请自行转换。
 * 
 */
public class SharedPreferencesHelper {

	/** SharedPreferences xml 名称 */
	private static final String APP_SHARED_STR = "myself_sp";
	public static final String PREFERENTIAL_CACHE = "preferential_cache";
	public static final String MY_ACTIVITY = "myactivity_cache";

	/**
	 * 设置参数
	 * 
	 * @param activity
	 * @param date
	 * @return
	 */
	public static void setMap(Context context, Map<String, String> date) {
		Editor editor = getSP(context).edit();
		for (String key : date.keySet()) {
			editor.putString(key, date.get(key));
		}
		editor.commit();
	}

	/**
	 * 设置字符串
	 * 
	 * @param activity
	 * @param date
	 * @return
	 */
	public static void setString(Context context, String key, String val) {
		Editor editor = getSP(context).edit();
		editor.putString(key, val);
		editor.commit();
	}

	/**
	 * 设置Long
	 * 
	 * @param activity
	 * @param date
	 * @return
	 */
	public static void setLong(Context context, String key, long val) {
		Editor editor = getSP(context).edit();
		editor.putLong(key, val);
		editor.commit();
	}

	public static void removeValuesForKey(Context context, String key) {
		Editor editor = getSP(context).edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 获取String类型参数
	 * 
	 * @param activity
	 * @param key
	 * @return
	 */
	public static String getStringValue(Context context, String key) {
		return getSP(context).getString(key, "");
	}

	/**
	 * 获取Long类型参数
	 * 
	 * @param activity
	 * @param key
	 * @return
	 */
	public static long getLongValue(Context context, String key) {
		return getSP(context).getLong(key, 0);
	}

	/**
	 * 从SharedPreferences 获取一个boolean值，默认为false
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		return getSP(context).getBoolean(key, false);
	}

	public static int getInt(Context context, String key) {
		return getSP(context).getInt(key, -1);
	}

	public static void setInt(Context context, String key, int val) {
		getSP(context).edit().putInt(key, val).commit();
	}

	public static SharedPreferences getSP(Context context) {
		return context.getSharedPreferences(APP_SHARED_STR, Context.MODE_PRIVATE);
	}
	public static SharedPreferences getPreSP(Context context) {
		return context.getSharedPreferences(PREFERENTIAL_CACHE, Context.MODE_PRIVATE);
	}
	public static SharedPreferences getActivitySP(Context context) {
		return context.getSharedPreferences(MY_ACTIVITY, Context.MODE_PRIVATE);
	}

	/**
	 * 设置 一个boolean 值到SharedPreferences
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static void setBoolean(Context context, String key, boolean value) {
		getSP(context).edit().putBoolean(key, value).commit();
	}
	
}

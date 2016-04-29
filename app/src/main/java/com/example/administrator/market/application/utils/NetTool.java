package com.example.administrator.market.application.utils;


import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetTool {
	
	/**
	 * 检查网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}
	
	/**
	 * 检查是否有网且在线
	 * @return
	 */
	public static boolean isOnline(Application app){
		boolean flag = true;
		if(!isNetworkAvailable(app)){
			return false;
		}
//		if (app.getAccountManager() != null ) {
//			if(app.getAccountManager().isOffline()){
//				flag = false;
//			}
//		}else{
//			flag = false;
//		}		
		
		return flag;
	}
	
}

package com.example.administrator.market.application.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * 获取手机中所有的三方分享APP
 */
public class ShareAppUtil {
	 
//获取到所有的三方分享APP	
	public static List<ShareAppInfo> getShareAppList(Context ctx) {   
        List<ShareAppInfo> shareAppInfos = new ArrayList<ShareAppInfo>();  
        PackageManager packageManager = ctx.getPackageManager();  
        List<ResolveInfo> resolveInfos = getShareApps(ctx);  
        if (null == resolveInfos) {  
            return null;  
        } else {  //com.tencent.mm  com.sina.weibo    com.qzone
            for (ResolveInfo resolveInfo : resolveInfos) {  
            	if(!resolveInfo.activityInfo.processName.equals("com.tencent.mm") &&
            			!resolveInfo.activityInfo.processName.equals("com.sina.weibo")&&
            			!resolveInfo.activityInfo.processName.equals("com.qzone") &&
            			!resolveInfo.activityInfo.processName.equals("com.tencent.mobileqq")){
            		continue;
            	}
            	if (resolveInfo.activityInfo.processName.equals("com.tencent.mobileqq") && 
            			!resolveInfo.activityInfo.name.equals("com.tencent.mobileqq.activity.JumpActivity")) {
					continue;
				}
            	if (resolveInfo.activityInfo.name.equals("com.tencent.mobileqq.activity.JumpActivity")) {
            		resolveInfo.activityInfo.name = "com.tencent.mobileqq.activity.ForwardRecentActivity";
				}
                ShareAppInfo appInfo = new ShareAppInfo();  
                appInfo.setAppPkgName(resolveInfo.activityInfo.packageName);  
//              showLog_I(TAG, "pkg>" + resolveInfo.activityInfo.packageName + ";name>" + resolveInfo.activityInfo.name);  
                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);  
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());  
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));  
                shareAppInfos.add(appInfo);  
            }  
        }         
        return shareAppInfos;  
        
    }  
	
	//查询手机内所有支持分享的应用 
	private static List<ResolveInfo> getShareApps(Context context) {    
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();  
        Intent intent = new Intent(Intent.ACTION_SEND, null);  
        intent.addCategory(Intent.CATEGORY_DEFAULT);  
        intent.setType("image/*");
//      intent.setType("*/*");  
        PackageManager pManager = context.getPackageManager();  
        mApps = pManager.queryIntentActivities(intent,   
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);  
        return mApps; 
        
    }  
	
//	public static List<ResolveInfo> getShareApps(Context context){  
//	    List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();    
//	    Intent intent=new Intent(Intent.ACTION_SEND,null);    
//	    intent.addCategory(Intent.CATEGORY_DEFAULT);    
//	    intent.setType("text/plain");    
//	    PackageManager pManager = context.getPackageManager();  
//	    mApps = pManager.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);    
//	      
//	    return mApps;    
//	}  
	
	
	//ShareAppInfo的Bean
	public final static class ShareAppInfo implements Serializable{
		private static final long serialVersionUID = 1L;
		
		String appPkgName;
		String appLauncherClassName;
		String appName;
		Drawable appIcon;
		
		public String getAppPkgName() {
			return appPkgName;
		}
		public void setAppPkgName(String appPkgName) {
			this.appPkgName = appPkgName;
		}
		public String getAppLauncherClassName() {
			return appLauncherClassName;
		}
		public void setAppLauncherClassName(String appLauncherClassName) {
			this.appLauncherClassName = appLauncherClassName;
		}
		public String getAppName() {
			return appName;
		}
		public void setAppName(String appName) {
			this.appName = appName;
		}
		public Drawable getAppIcon() {
			return appIcon;
		}
		public void setAppIcon(Drawable appIcon) {
			this.appIcon = appIcon;
		}
		
		
	}
	
	
}

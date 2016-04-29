package com.example.administrator.market.application.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

public class DialogUtil {
	private static ProgressDialog progressDialog;
	public static boolean isDialogShow() {
		if (progressDialog != null && progressDialog.isShowing()) {
			return true;
		}
		return false;
	}
	
	//打开进度框
		public static void showProgress(Context context, boolean cancelable) {
			showProgress(context, "正在加載...", cancelable, false);
		}

		/**
		 * 
		 * @param context
		 * @param 显示的信息
		 * @param 可取消
		 * @param 点击外部可取消
		 */
		public static void showProgress(Context context, String text,
				boolean cancelable, boolean outCancelable) {
			try {
				progressDialog = new ProgressDialog(context);
				if (!(context instanceof Activity)) {
					progressDialog.getWindow().setType(
							WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
					progressDialog.getWindow().setType(
							WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				}
				progressDialog.setMessage(text);
				progressDialog.setCancelable(cancelable);
				progressDialog.setCanceledOnTouchOutside(outCancelable);
				progressDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 关闭进度框
		 * 
		 * @param context
		 */
		public static void diessProgerss(Context context) {
			try {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog.cancel();
				}
				progressDialog = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

package com.example.administrator.market.application.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.market.application.BaseApplication;


/**
 * Created by Administrator on 4/15/2016.
 */
public class ToastUtils {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(String s) {
        showToast(BaseApplication.getApplication(), s);
    }

    public static void showToast(int resId) {
        showToast(BaseApplication.getApplication(), BaseApplication.getApplication().getString(resId));
    }

    public static void showToast(Context context, String s) {
        if (TextUtils.isEmpty(s)) {
            s = "";
        }
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
//                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}

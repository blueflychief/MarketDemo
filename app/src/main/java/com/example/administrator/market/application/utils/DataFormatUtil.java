package com.example.administrator.market.application.utils;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormatUtil {
	//格式化日期MM月dd日HH:mm
		public static String getActivityCommentDate(String str) {
			if (TextUtils.isEmpty(str)) {
				return "";
			}
			SimpleDateFormat dataFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			try {
				date = dataFormat.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
			SimpleDateFormat dataFormat1 = new SimpleDateFormat("MM月dd日HH:mm");
			String dateStr = dataFormat1.format(date);
			return dateStr;
		}
		
		//格式化日期为指定格式
		public static String formatDate(String time, String format){
			if (TextUtils.isEmpty(time)) {
				return "";
			}
			SimpleDateFormat dataFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			try {
				date = dataFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
			SimpleDateFormat dataFormat1 = new SimpleDateFormat(format);
			String dateStr = dataFormat1.format(date);
			return dateStr;
			
		}
}

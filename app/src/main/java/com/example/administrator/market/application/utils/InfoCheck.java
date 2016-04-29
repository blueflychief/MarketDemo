package com.example.administrator.market.application.utils;

import android.text.TextUtils;

public class InfoCheck {
	/**
	 * 检测电话号码是否合法
	 */
	public static boolean checkIsPhoneNum(String phoneNo) {
		 if(TextUtils.isEmpty(phoneNo)){
			 return false;
		 }
		//String pattern = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		String pattern = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		
		return phoneNo.matches(pattern);
	}
	/**
	 * 检测手机号是否合法
	 */
	public static boolean checkPhoneNum(String phoneNum) {
		String pattern = "1[3458][0-9]{9}";
		return phoneNum.matches(pattern) || TextUtils.isEmpty(phoneNum);
	}
	
	/**
	 * 检测固话号是否合法
	 */
	public static boolean checkLandLine(String phoneNo) {
		String pattern = "^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})+(-[0-9]{1,4})?";
		return phoneNo.matches(pattern) || TextUtils.isEmpty(phoneNo);
	}
	
	/**
	 * 检测邮箱是否合法
	 */
	public static boolean checkEmail(String emailName) {
		String pattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return emailName.matches(pattern) || TextUtils.isEmpty(emailName);
	}
}

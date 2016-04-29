package com.example.administrator.market.application.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Md5Util {

	// 计算文件的MD5值，
	public static String getFileMD5(String appPath) throws Exception {
		// 创建 MessageDigest对象 实例
		MessageDigest digest = MessageDigest.getInstance("MD5");
		File file = new File(appPath);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;
		// 循环读取文件全部内容并用update方法向已初始化的MessageDigest对象提供要计算的数据
		while ((len = fis.read(buffer)) != -1) {
			digest.update(buffer, 0, len);
		}
		// 计算摘要,此处获得的数组是带有符号的数字，需要装换成16进制的字符串
		byte[] result = digest.digest();
		for (byte b : result) {
			System.out.print(b + " ");
		}
		// 转换成16进制的字符串，每个字符数组进行16进制FF进行&运算
		StringBuilder sb = new StringBuilder();
		for (byte b : result) {
			int number = 0xff & b;
			String hex = Integer.toHexString(number);
			// 有些数组获得的字符串只有一个字符，需要在前面补0
			if (hex.length() == 1) {
				sb.append("0" + hex);
			} else {
				sb.append(hex);
			}
		}
		return sb.toString();
	}

	// 计算字符串的MD5值，
	public static String getStringMD5(String string) throws Exception {
		// 创建 MessageDigest对象 实例
		MessageDigest digest = MessageDigest.getInstance("MD5");
		// 计算摘要,此处获得的数组是带有符号的数字，需要装换成16进制的字符串
		byte[] result = digest.digest(string.getBytes());
		for (byte b : result) {
			System.out.print(b + " ");
		}
		// 转换成16进制的字符串，每个字符数组进行16进制FF进行&运算
		StringBuilder sb = new StringBuilder();
		for (byte b : result) {
			int number = 0xff & b;
			String hex = Integer.toHexString(number);
			// 有些数组获得的字符串只有一个字符，需要在前面补0
			if (hex.length() == 1) {
				sb.append("0" + hex);
			} else {
				sb.append(hex);
			}
		}
		return sb.toString();
	}
}

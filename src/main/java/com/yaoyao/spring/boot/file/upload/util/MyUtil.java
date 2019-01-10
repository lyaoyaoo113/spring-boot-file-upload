package com.yaoyao.spring.boot.file.upload.util;

import java.util.UUID;

public class MyUtil {

	/**
	 * 生成UUID（小写)
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 生成UUID
	 * 
	 * @param upperCase
	 * 是否大写
	 * @return
	 */
	public static String getUUID(boolean upperCase) {
		String UUIDText = getUUID();
		return upperCase ? UUIDText.toUpperCase() : UUIDText;
	}

	/**
	 * 文件大小转换
	 * 
	 * @param fileSizeBytes
	 * 文件大小
	 * @return
	 */
	public static String fileSizeConversion(long fileSizeBytes) {
		double fileSizeMessage = fileSizeBytes;
		// 文件小于1KB
		if (fileSizeMessage < 1024) {
			return fileSizeMessage + "B";
		}
		// 文件小于1MB
		fileSizeMessage = fileSizeMessage / 1024;
		if (fileSizeMessage < 1024) {
			return fileSizeMessage + "KB";
		}
		// 文件小于1GB
		fileSizeMessage = fileSizeMessage / 1024;
		if (fileSizeMessage < 1024) {
			return fileSizeMessage + "MB";
		}
		// 文件小于1TB
		fileSizeMessage = fileSizeMessage / 1024;
		if (fileSizeMessage < 1024) {
			return fileSizeMessage + "GB";
		}
		return "";
	}
}

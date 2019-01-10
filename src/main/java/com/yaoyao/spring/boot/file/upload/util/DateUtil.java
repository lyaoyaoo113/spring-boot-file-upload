package com.yaoyao.spring.boot.file.upload.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 获取日期
	 * 
	 * @param pattern
	 * 指定格式
	 * @return
	 */
	public static String getDate(String pattern) {
		// 设置日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(new Date());
	}

	public static String getDateTime() {
		// 设置日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}

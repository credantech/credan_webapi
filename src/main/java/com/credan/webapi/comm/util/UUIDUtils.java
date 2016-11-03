/**
 * @Project: credan_common @(#) UUIDUtils.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年5月23日 下午4:18:40 $
 */
public class UUIDUtils {

	/**
	 * 获取唯一字符串 当前时间+UUID
	 */
	public static String getUniqueUUID() {
		String uuid = UUID.randomUUID().toString();
		String currTime = getCurrentTime();
		StringBuffer uniqueBuffer = new StringBuffer();
		String uniqueString = uniqueBuffer.append(currTime).append(uuid).toString().replaceAll("-", "");
		return uniqueString;
	}

	/**
	 * 获取当前时间字符串yyyyMMddHHmmss
	 */
	public static String getCurrentTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTimeString = simpleDateFormat.format(new Date());
		return currTimeString;
	}
	
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		String uniqueString = uuid.replaceAll("-", "");
		return uniqueString;
	}
}

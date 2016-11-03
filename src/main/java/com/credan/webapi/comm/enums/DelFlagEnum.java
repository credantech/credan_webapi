/**
 * @(#) DelFlagEnum.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm.enums;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年8月17日 上午11:26:59 $
 */
public enum DelFlagEnum {

	TRUE("1", "已删除"), FALSE("0", "未删除");

	private final String code;
	private final String name;

	private DelFlagEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}

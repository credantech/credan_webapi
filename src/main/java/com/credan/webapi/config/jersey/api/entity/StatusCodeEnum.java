/**
 * @(#) StatusCodeEnum.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.entity;

/**
 * 返回前端状态码
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月4日 下午4:43:53 $
 */
public enum StatusCodeEnum {

	SUCCESS(200, "操作成功"), 
	FAIL(201, "操作失败"),
	WRONG_PARAM(7000,"参数错误或格式异常"),;

	private final int code;
	private final String msg;

	private StatusCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}

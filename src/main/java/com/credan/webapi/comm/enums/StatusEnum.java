/**
 * @(#) StatusEnum.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm.enums;

/**
 * 响应结果统一处理状态码
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:16:45 $
 */
public enum StatusEnum {

	SUCCESS(200, "成功"), 
	FAIL(201,"接口调用失败"),
	UN_AUTHORIZED(101, "无权访问"),
	INVALID(102, "无效访问"),
	PROPERTY_REQUIRED(103, "[%s]字段为必填字段"),
	PROPERTY_LENGTH_ERROR(104, "[%s]字段长度与规则不符"),
	PARAM_FORMAT_ERROR(105, "数据规则校验失败"),
	SYSTEM_ERROR(106, "系统内部错误"),
	SYSTEM_USELESS(107, "系统暂时不可用"),
	CONNECTOR_DEV(108,"接口开发中,暂时不可用")
	;

	private final int code;

	private final String msg;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	private StatusEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}

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

	SUCCESS("200", "操作成功"), 
	FAILD("4000", "操作失败"),
	WRONG_PARAM("7000","参数错误或格式异常"),
	FILE_UPLODED("7007","上传完成"),
	FILE_UNUPLODED("7008","上传失败"),
	;

	private final String code;
	private final String msg;

	private StatusCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}

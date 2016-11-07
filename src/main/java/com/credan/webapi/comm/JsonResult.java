/**
 * @Project: credan_model @(#) JsonResult.java
 * 
 * Copyright (c) 2016, Credan-版权所有
 * 
 */
package com.credan.webapi.comm;

import java.io.Serializable;

import com.credan.webapi.comm.enums.StatusCodeEnum;
import com.google.common.base.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class <code>JsonResult</code>
 * 
 * @Time 2016年5月17日 下午1:18:44
 * @author: Mond
 * @version 1.0.0 ,2016年5月17日
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JsonResult implements Serializable {

	/**
	 * serialVersionUID TODO
	 * 
	 * @since 1.0.0
	 */

	private static final long serialVersionUID = 8445854052401550639L;

	/* 执行操作是否成功 */
	private boolean success;

	/* 返回消息实体 */
	private String msg;

	// 承载数据
	private Object data;

	private int statusCode;

	/**
	 * Constructs an <code>JsonResult</code> .
	 */
	public JsonResult(boolean success, String msg) {
	}

	/**
	 * Constructs an <code>JsonResult</code> .
	 */
	public JsonResult(boolean success) {
		this(success, null, null);
	}

	/**
	 * Constructs an <code>JsonResult</code> .
	 */
	public JsonResult(boolean success, String msg, Object data) {
		StatusCodeEnum statusCodeEnum = success ? StatusCodeEnum.SUCCESS : StatusCodeEnum.FAIL;
		this.success = success;
		this.msg = Strings.isNullOrEmpty(msg) ? statusCodeEnum.getMsg() : msg;
		this.data = data;
		this.statusCode = statusCodeEnum.getCode();
	}

}

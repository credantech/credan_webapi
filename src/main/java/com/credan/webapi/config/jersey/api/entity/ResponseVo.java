/**
 * @(#) ResponseVo.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.entity;

import java.io.Serializable;

import com.credan.webapi.comm.util.Json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午7:00:08 $
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4302839630506006244L;

	/** 响应错误码 */
	private Integer errorCode;

	/** 请求类型 */
	private String txnCode;

	/** 错误消息 */
	private String message;

	/** yyyy-MM-dd HH:mm:ss时间戳 */
	private String timestamp;

	/** 响应数据 */
	private Object data;

	/** 请求参数，响应结果是否加密 */
	private String resultEncrypt;

	/** 加密签名 */
	private String sign;

	@Override
	public String toString() {
		return Json.ObjectMapper.writeValue(this);
	}

}

/**
 * @(#) RequestVo.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.entity;

import java.io.Serializable;

import com.credan.webapi.comm.util.Json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求参数
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月3日 下午6:59:28 $
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167917180598326843L;

	/** 商户ID */
	private String merchantId;

	/** 交易类型 */
	private String txnCode;

	/** 请求发起时间戳 yyyy-MM-dd HH:mm:ss格式 */
	private String timestamp;

	/** 请求参数 */
	private Object data;

	/** 请求参数加密签名 */
	private String sign;

	/** 响应结果是否加密 1：加密、0：不加密 */
	private String resultEncrypt;

	@Override
	public String toString() {
		return Json.ObjectMapper.writeValue(this);
	}
}

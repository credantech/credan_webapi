/**
 * @(#) ResultVo.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm;

import java.io.Serializable;
import java.util.Map;

import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.comm.util.Json;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午1:13:18 $
 */
@Data
@NoArgsConstructor
public class ResultVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4003910983377916796L;

	/** 成功标识 */
	private boolean isSuccess;
	/** 错误代码 */
	private int errorCode;
	/** 消息 */
	private String message;
	/** 响应的结果 */
	private final Map<String, Object> data = Maps.newHashMap();

	public ResultVo(boolean isSuccess, String message) {
		this.isSuccess = isSuccess;
		StatusEnum statusEnum = isSuccess ? StatusEnum.SUCCESS : StatusEnum.FAIL;
		this.errorCode = isSuccess ? statusEnum.getCode() : statusEnum.getCode();
		this.message = Strings.isNullOrEmpty(message) ? statusEnum.getMsg() : message;
	}

	public ResultVo(boolean isSuccess) {
		this(isSuccess, null);
	}

	public void putValue(String key, Object value) {
		data.put(key, value);
	}

	public void putValue(Map<String, Object> map) {
		data.putAll(map);
	}

	@Override
	public String toString() {
		return Json.ObjectMapper.writeValue(this);
	}

}

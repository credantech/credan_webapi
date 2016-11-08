/**
 * @(#) ParamException.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.api.exception;

import com.credan.webapi.config.exception.CustomException;
import com.credan.webapi.config.jersey.api.entity.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 参数异常
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:43:35 $
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ParamException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487515556761659359L;

	private StatusEnum statusEnum;
	private String message;

	/**
	 * 参数异常
	 * 
	 * @param statusEnum
	 * @param placeholders 需要占位符
	 */
	public ParamException(StatusEnum statusEnum, Object... placeholders) {
		this.statusEnum = statusEnum;
		this.message = String.format(statusEnum.getMsg(), placeholders);
	}

	public ParamException(StatusEnum statusEnum) {
		this(statusEnum, "");
	}

	public ParamException() {
		this(StatusEnum.PROPERTY_REQUIRED, "");
	}

	public ParamException(StatusEnum statusEnum, String message) {
		super();
		this.statusEnum = statusEnum;
		this.message = message;
	}

	public ParamException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.statusEnum = StatusEnum.PROPERTY_REQUIRED;
		this.message = arg1.getMessage();
	}

	public ParamException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.statusEnum = StatusEnum.PROPERTY_REQUIRED;
		this.message = arg1.getMessage();
	}

	public ParamException(String arg0) {
		super(arg0);
		this.statusEnum = StatusEnum.PROPERTY_REQUIRED;
		this.message = arg0;
	}

	public ParamException(Throwable arg0) {
		super(arg0);
		this.statusEnum = StatusEnum.PROPERTY_REQUIRED;
		this.message = arg0.getMessage();
	}

}

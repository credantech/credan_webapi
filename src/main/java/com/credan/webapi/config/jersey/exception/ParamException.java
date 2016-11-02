/**
 * @(#) ParamException.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.exception;

import com.credan.webapi.comm.enums.StatusEnum;
import com.credan.webapi.config.exception.CustomException;

/**
 * 参数异常
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:43:35 $
 */
public class ParamException extends CustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487515556761659359L;

	private StatusEnum statusEnum;

	public ParamException(StatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}
	
	public StatusEnum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(StatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}

}

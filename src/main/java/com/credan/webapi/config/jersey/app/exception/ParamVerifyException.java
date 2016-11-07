/**
 * @(#) ParamVerifyException.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.jersey.app.exception;

/**
 * 参数校验错误
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年9月23日 上午11:35:16 $
 */
public class ParamVerifyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8635479054288061363L;

	public ParamVerifyException() {
		super();
	}

	public ParamVerifyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ParamVerifyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParamVerifyException(String arg0) {
		super(arg0);
	}

	public ParamVerifyException(Throwable arg0) {
		super(arg0);
	}

}

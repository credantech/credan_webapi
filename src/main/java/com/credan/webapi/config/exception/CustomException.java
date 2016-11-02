/**
 * @(#) CustomException.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.config.exception;

/**
 * 项目基础异常
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 上午11:54:44 $
 */
public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7768123974948412983L;

	public CustomException() {
		super();
	}

	public CustomException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CustomException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CustomException(String arg0) {
		super(arg0);
	}

	public CustomException(Throwable arg0) {
		super(arg0);
	}

}

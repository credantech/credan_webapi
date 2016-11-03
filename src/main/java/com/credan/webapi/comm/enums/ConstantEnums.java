/**
 * @(#) ConstantEnums.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.comm.enums;

/**
 * 常量枚举类
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月2日 下午5:56:46 $
 */
public interface ConstantEnums {

	/**
	 * 期数单位
	 * 
	 */
	public enum TermUnitEnum {
		M, D
	}
	
	/**
	 * 回调响应结果状态
	 *
	 */
	public enum CallBackResultEnum {
		SUCCESS, FAIL
	}

}

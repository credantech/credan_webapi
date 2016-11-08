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

	/**
	 * 回调通知类型
	 * 
	 * @author Mond
	 *
	 */
	public enum NotifySubTypeEnum {
		PAID_SUCCESS("0001", "用户支付完成");

		private final String code;
		private final String msg;

		private NotifySubTypeEnum(String code, String msg) {
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

	/**
	 * 状态(启用：ENABLED、禁用：DISABLE)
	 * 
	 * @author Mond
	 *
	 */
	public enum SwitchEnum {
		ENABLED, DISABLE;
	}
	/**
	 * 
	 * @author Mond
	 *
	 */
	public enum FileClassifyEnum{
		//手持身份证
		HANDHELD_ID;
	}
}
